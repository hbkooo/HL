package love.lxy.hbk.hl.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.MobSDK;

import java.util.Calendar;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import love.lxy.hbk.hl.MyView.HeartClickLayout;
import love.lxy.hbk.hl.R;
import love.lxy.hbk.hl.Service.MusicService;
import love.lxy.hbk.hl.Util.Util;

import static love.lxy.hbk.hl.Util.Util.LOGIN_DATA;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean canGetCode = true;  // 用户是否已经点击获取验证码按钮
    private int num = 60;               // 用户下次可以点击获取验证码的剩余时间

    private boolean last_is_remember_pwd = false;
    private String last_save_pwd = "";
    private String last_save_username = "";

    private EditText et_tel, et_code;
    private Button btn_code;
    private CheckBox remember_pwd_cb;

    private Typeface kaiti_typeface;
    private EventHandler eventHandler;

    private HeartClickLayout layout = null;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AssetManager manager = getAssets();
        Typeface typeface = Typeface.createFromAsset(manager, "fonts/huawenxingkai.ttf");
        kaiti_typeface = Typeface.createFromAsset(manager, "fonts/kaiti.ttf");

        TextView login_tv = (TextView) findViewById(R.id.login_tv);
        ImageView login_love_iv = (ImageView) findViewById(R.id.login_love_iv);


        // 2019年8月7日情人节之后显示登陆动画
        if (Util.checkIsReachTime(true)) {
            login_tv.setTypeface(typeface);
            Util.startHeartJumpAnimator(login_love_iv);
            Intent intent = new Intent(LoginActivity.this, MusicService.class);
            startService(intent);
        } else {
            login_love_iv.setVisibility(View.GONE);
            login_tv.setVisibility(View.GONE);
        }

        Init();

    }

    private void Init() {

        gestureDetector = new GestureDetector(this, new onGestureListener());
        layout = findViewById(R.id.login_heart_click_layout);
        et_tel = findViewById(R.id.et_tel);
        et_code = findViewById(R.id.et_code);
        btn_code = findViewById(R.id.btn_code);
        Button btn_login = findViewById(R.id.btn_login);

        remember_pwd_cb = findViewById(R.id.remember_pwd_cb);
        TextView remember_pwd_tv = findViewById(R.id.remember_pwd_tv);

        remember_pwd_tv.setOnClickListener(this);

        btn_code.setOnClickListener(this);
        btn_login.setOnClickListener(this);

        et_tel.setTypeface(kaiti_typeface);
        et_code.setTypeface(kaiti_typeface);
        btn_code.setTypeface(kaiti_typeface);
        btn_login.setTypeface(kaiti_typeface);

        remember_pwd_tv.setTypeface(kaiti_typeface);

        InitMob();

        SharedPreferences preferences = getSharedPreferences(LOGIN_DATA, MODE_PRIVATE);
        Boolean isRemember = preferences.getBoolean("isRemember", false);
        last_is_remember_pwd = isRemember;
        if (isRemember) {
            last_save_pwd = preferences.getString("password", "");
            last_save_username = preferences.getString("username", "");
            et_tel.setText(last_save_username);
            et_code.setText(last_save_pwd);
            remember_pwd_cb.setChecked(true);
        }

    }

    // 初始化获取验证码的SDK
    private void InitMob() {

        MobSDK.init(this);  // 初始化MobSDK

        SMSSDK.setAskPermisionOnReadContact(true);

        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                // afterEvent会在子线程被调用，因此如果后续有UI相关操作，需要将数据发送到UI线程
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                new Handler(Looper.getMainLooper(), new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        int event = msg.arg1;
                        int result = msg.arg2;
                        Object data = msg.obj;
                        if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                // TODO
                                // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                                updateCodeBtn();  //已发送验证码，更新界面显示
                                Toast.makeText(LoginActivity.this, "爱的密码已经发送啦...",
                                        Toast.LENGTH_LONG).show();

                            } else {
                                ((Throwable) data).printStackTrace();
                                Log.e("SMSSDK:", "验证码未发送，出错" + data);
                                Toast.makeText(LoginActivity.this,
                                        "不开心，爱的密码没有发送成功\n大傻傻检查一下网络是不是可以用哦~",
                                        Toast.LENGTH_LONG).show();
                            }
                        } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                checkCodeSuccess(); //用户验证成功
                            } else {
                                ((Throwable) data).printStackTrace();
                                Toast.makeText(LoginActivity.this, "大傻傻，爱的密码输错了呦~",
                                        Toast.LENGTH_LONG).show();
                                Log.e("SMSSDK:", "验证码处理错误，出错" + data);
                            }
                        }
                        return false;
                    }
                }).sendMessage(msg);
            }
        };
        // 注册一个事件回调，用于处理SMSSDK接口请求的结果
        SMSSDK.registerEventHandler(eventHandler);

    }

    @SuppressLint("ShowToast")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_code:

                // 2019年8月7日情人节
                if (!Util.checkIsReachTime(true)) {
                    Toast.makeText(this, "还没有到时间呦\n，宝宝再等一下", Toast.LENGTH_LONG).show();
                    return;
                }

                canGetCode = false;
                String tel = et_tel.getText().toString().trim();
                if ("".equals(tel)) {
                    Toast.makeText(this,
                            "傻猪猪要先输入自己的手机号哦~", Toast.LENGTH_LONG).show();
                    return;
                }
//                if (!Util.MY_DEAR_PHONE.equals(tel)) {
//                    Toast.makeText(this,
//                            "傻宝宝自己的手机号都不记得了呐（偷笑）", Toast.LENGTH_LONG).show();
//                    return;
//                }
                SMSSDK.getVerificationCode("86", tel);
                last_is_remember_pwd = false; // 重新获取验证码，上次密码失效

                break;
            case R.id.btn_login:
                String input_code = et_code.getText().toString();
                String tel1 = et_tel.getText().toString().trim();
                if ("".equals(tel1)) {
                    Toast.makeText(this,
                            "傻猪猪要先输入自己的手机号哦~", Toast.LENGTH_LONG).show();
                    return;
                }
//                if (!Util.MY_DEAR_PHONE.equals(tel1)) {
//                    Toast.makeText(this,
//                            "傻宝宝自己的手机号都不记得了呐（偷笑）", Toast.LENGTH_LONG).show();
//                    return;
//                }
                if ("".equals(input_code)) {
                    // 密码为空
                    Toast.makeText(LoginActivity.this, "小傻瓜，爱的密码不能为空的呦",
                            Toast.LENGTH_LONG).show();
                } else {
                    if ("20171118".equals(input_code)) {
                        // 直接静态验证，便于验证码无法发送成功时能够登录成功
                        checkCodeSuccess();
                        return;
                    }
//                    checkCodeSuccess();
                    if (last_is_remember_pwd) {
                        // 从上次记住的密码验证
//                        if (!et_tel.getText().toString().equals(Util.MY_DEAR_PHONE)) {
//                            Toast.makeText(this,
//                                    "傻宝宝自己的手机号都不记得了呐（偷笑）", Toast.LENGTH_LONG).show();
//                            return;
//                        }
                        if (!tel1.equals(last_save_username)) {
                            Toast.makeText(this, "手机号已更换\n请重新获取爱的密码", Toast.LENGTH_SHORT)
                                    .show();
                            return;
                        }
                        if (input_code.equals(last_save_pwd)) {
                            checkCodeSuccess();
                        } else {
                            Toast.makeText(LoginActivity.this, "大傻傻，爱的密码输错了呦~",
                                    Toast.LENGTH_LONG).show();
                        }
                    } else {
                        // 验证码验证
                        SMSSDK.submitVerificationCode("86", et_tel.getText().toString(), input_code);
                    }
                }
                break;
            case R.id.remember_pwd_tv:
                if (remember_pwd_cb.isChecked()) {
                    remember_pwd_cb.setChecked(false);
                } else {
                    remember_pwd_cb.setChecked(true);
                }
                break;
        }
    }

    // 服务器发送验证码，更新界面btn的显示
    private void updateCodeBtn() {

        num = 60;
        Thread thread = new Thread() {
            @Override
            public void run() {
                if (!canGetCode) {
                    while (num > 0) {
                        num--;
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                btn_code.setText("获取爱的密码（" + num + "）");
                                btn_code.setClickable(false);
                            }
                        });
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
                canGetCode = true;
                num = 60;
                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btn_code.setText("获取爱的密码");
                        btn_code.setClickable(true);
                    }
                });

            }
        };
        thread.start();

    }

    // 服务器验证成功，更新内存变量，这是在子线程中，界面的更新需要转到UI线程
    private void checkCodeSuccess() {
        num = 0;
        Toast.makeText(LoginActivity.this, "傻猪猪爱的密码输入正确啦~",
                Toast.LENGTH_LONG).show();
        checkToSavePWD();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }


    private void checkToSavePWD() {
        SharedPreferences.Editor editor = getSharedPreferences(LOGIN_DATA, MODE_PRIVATE).edit();

        if (remember_pwd_cb.isChecked()) {
            editor.putBoolean("isRemember", true);
            editor.putString("username", et_tel.getText().toString());
            editor.putString("password", et_code.getText().toString());
            editor.apply();
        } else {
            editor.clear();
            editor.apply();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // 2019年8月7日情人节之后播放背景音乐
        if (Util.checkIsReachTime(true)) {
            Intent intent = new Intent(LoginActivity.this, MusicService.class);
            startService(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
        Intent intent = new Intent(LoginActivity.this, MusicService.class);
        stopService(intent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }

    class onGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (Util.background_click_heart) {
                layout.addLoveView(e.getRawX(), e.getRawY());
            }
            return super.onDoubleTap(e);
        }
    }
}
