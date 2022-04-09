package love.lxy.hbk.hl.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Calendar;

import love.lxy.hbk.hl.R;
import love.lxy.hbk.hl.Util.Util;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private static int MAIN = 1;
    private static int WELCOME = 0;

    private boolean is_in_main = true;

    private int year, month, day;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (is_in_main) {
                        startMain();
                    }
                    break;
                case 0:
                    startWelcome();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Calendar calendar  =Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH) + 1;

        ImageView pig_iv = findViewById(R.id.start_pig_iv);
        Util.startHeartJumpAnimator(pig_iv);
        pig_iv.setOnClickListener(this);


        // 2019年8月7日情人节
        if (Util.isQiXi()) {
            Toast.makeText(this,"我的傻猪猪，七夕节快乐！爱你呦！！",
                Toast.LENGTH_LONG).show();
            handler.sendEmptyMessageDelayed(WELCOME,3000);
        } else {
            handler.sendEmptyMessageDelayed(MAIN,3000);
        }

    }

    // 不是第一次打开APP，则直接进入
    private void startMain(){
        startActivity(new Intent(StartActivity.this,LoginActivity.class));
//        startActivity(new Intent(StartActivity.this,MainActivity.class));
        finish();
    }

    // 第一次进入APP则先进入欢迎界面
    private void startWelcome(){
        startActivity(new Intent(StartActivity.this,WelcomeActivity.class));
        finish();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.start_pig_iv) {
            if (Util.isQiXi()){
                return;
            }
            if (!Util.checkIsReachTime(false)) {
                is_in_main = true;
            } else {
                is_in_main = false;
                startWelcome();
            }
        }
    }
}
