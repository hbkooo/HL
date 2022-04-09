package love.lxy.hbk.hl.Activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import love.lxy.hbk.hl.Activity.LoveTest.LoveTest1Activity;
import love.lxy.hbk.hl.Activity.LoveTest.LoveTestActivity;
import love.lxy.hbk.hl.MyView.HeartClickLayout;
import love.lxy.hbk.hl.MyView.HeartView;
import love.lxy.hbk.hl.R;
import love.lxy.hbk.hl.Service.MusicService;
import love.lxy.hbk.hl.Util.Util;

import static love.lxy.hbk.hl.Util.Util.setViewTypeface;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "MainActivity";

    private Typeface kai_ti_typeface;

    private ImageView setting_iv;
    private ImageView left_pigeon_iv, right_pigeon_iv;
    private HeartView heartView = null;

    private PopupMenu popupMenu = null;

    private HeartClickLayout layout = null;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gestureDetector = new GestureDetector(this, new onGestureListener());

        // 加载字体
        AssetManager manager = getAssets();
        kai_ti_typeface = Typeface.createFromAsset(manager, "fonts/kaiti.ttf");

        InitControl();

        Intent intent = new Intent(MainActivity.this, MusicService.class);
        startService(intent);

        startPigeonAnimator();
//        Util.startHeartJumpAnimator(left_pigeon_iv);

    }

    // 初始化控件
    private void InitControl() {

        layout = findViewById(R.id.main_heart_click_layout);
        setting_iv = findViewById(R.id.main_setting_iv);
        left_pigeon_iv = findViewById(R.id.main_left_pigeon_iv);
        right_pigeon_iv = findViewById(R.id.main_right_pigeon_iv);

        ((TextView) findViewById(R.id.main_toolbar_title_tv)).setTypeface(kai_ti_typeface);
        ((TextView) findViewById(R.id.main_love_time_tv)).setTypeface(kai_ti_typeface);
        ((TextView) findViewById(R.id.main_love_test_tv)).setTypeface(kai_ti_typeface);
        ((TextView) findViewById(R.id.main_love_process_tv)).setTypeface(kai_ti_typeface);
        ((TextView) findViewById(R.id.main_love_other_tv)).setTypeface(kai_ti_typeface);

        LinearLayout love_time_layout = findViewById(R.id.love_time_layout);
        LinearLayout love_test_layout = findViewById(R.id.love_test_layout);
        LinearLayout love_process_layout = findViewById(R.id.love_process_layout);
        LinearLayout love_other_layout = findViewById(R.id.love_other_layout);

        love_time_layout.setOnClickListener(this);
        love_test_layout.setOnClickListener(this);
        love_process_layout.setOnClickListener(this);
        love_other_layout.setOnClickListener(this);


       setting_iv.setOnClickListener(this);

//        handler.sendEmptyMessageDelayed(1,500);

//        heartView = findViewById(R.id.main_heart_view);
//        heartView.start(MainActivity.this);


    }

    private void startPigeonAnimator() {

        int distance = Util.getScreenWidth(this) / 2 - Util.dipToPx(this, 86);
        ObjectAnimator objectAnimatorLeft = ObjectAnimator.ofFloat(left_pigeon_iv,
                "translationX", 0, distance)
                .setDuration(2000);
        objectAnimatorLeft.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimatorLeft.start();

        ObjectAnimator objectAnimatorRight = ObjectAnimator.ofFloat(right_pigeon_iv,
                "translationX", 0, -distance)
                .setDuration(2000);
        objectAnimatorRight.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimatorRight.start();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_setting_iv:
                chooseSetting(setting_iv);
                break;
            case R.id.love_time_layout:
                Util.ToastQiXi(MainActivity.this);
                if (Util.checkIsReachTime(false)) {
                    startActivity(new Intent(MainActivity.this, LoveTimeActivity.class));
                } else {
                    Toast.makeText(MainActivity.this,
                            "哈哈哈，傻猪猪这个今天不能看呦，明天再过来看吧（调皮）...",
                            Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.love_test_layout:
                Util.ToastQiXi(MainActivity.this);
                if (!Util.checkIsReachTime(false)){
                    Toast.makeText(MainActivity.this,"大傻傻，这个可以让你提前看呦~",
                            Toast.LENGTH_LONG).show();
                }
                startActivity(new Intent(MainActivity.this, LoveTestActivity.class));
                break;
            case R.id.love_process_layout:
                Util.ToastQiXi(MainActivity.this);
                if (Util.checkIsReachTime(false)) {
                    startActivity(new Intent(MainActivity.this, LoveProcessActivity.class));
                } else {
                    AlertDialog dialog = new AlertDialog.Builder(this)
                            .setTitle("来自二傻傻的提问：")
                            .setMessage("        大傻傻，你爱你的二傻傻吗？(点击爱就可以看呦~)")

                            .setNegativeButton("不爱",new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(MainActivity.this,
                                            "居然不爱我，伤心，蓝瘦香菇...",
                                            Toast.LENGTH_LONG).show();
                                }
                            })
                            .setPositiveButton("爱",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // TODO Auto-generated method stub
                                            Toast.makeText(MainActivity.this,
                                                    "大傻傻，我也爱你！\n不过最好的还要留到明天七夕节再看呦~",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    })
                            .show();
                    setViewTypeface(dialog.getWindow().getDecorView(), kai_ti_typeface);


                }
                break;
            case R.id.love_other_layout:
//                Toast.makeText(this,"大傻傻再等一下呦，你的二傻傻狗狗正在努力开发中...",
//                        Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainActivity.this, MyHeartActivity.class));
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        if (heartView != null)
//            heartView.updateHearts();
        Intent intent = new Intent(MainActivity.this, MusicService.class);
        startService(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        if (heartView != null)
//            heartView.cancelAnimator();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (heartView != null)
            heartView.cancelAnimator();
    }

    // 给出上下文选择器选择图像
    private void chooseSetting(View view) {
        if(popupMenu == null) {
            popupMenu = new PopupMenu(this, view);
            popupMenu.getMenuInflater().inflate(R.menu.main_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.main_menu_bg_heart:
                            if (Util.background_heart) {
                                Util.background_heart = false;
                                item.setTitle("开启背景满屏飞心");
                            } else {
                                Util.background_heart = true;
                                item.setTitle("关闭背景满屏飞心");
                            }
                            break;
                        case R.id.main_menu_bg_music:
                            if (Util.background_music) {
                                Util.background_music = false;
                                item.setTitle("开启背景音乐");
                                startService(new Intent(MainActivity.this, MusicService.class));
                            } else {
                                Util.background_music = true;
                                item.setTitle("关闭背景音乐");
                                startService(new Intent(MainActivity.this, MusicService.class));
                            }
//                            startActivity(new Intent(MainActivity.this, BackMusicActivity.class));
                            break;
                        case R.id.main_menu_bg_click_heart:
                            if (Util.background_click_heart) {
                                Util.background_click_heart = false;
                                item.setTitle("开启背景点击飞心");
                            } else {
                                Util.background_click_heart = true;
                                item.setTitle("关闭背景点击飞心");
                            }
                            break;
                    }
                    return false;
                }
            });
        }

        popupMenu.show();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (heartView != null)
                heartView.start(MainActivity.this);
            startPigeonAnimator();
        }
    };

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
