package love.lxy.hbk.hl.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import love.lxy.hbk.hl.MyView.HeartClickLayout;
import love.lxy.hbk.hl.MyView.HeartPathView;
import love.lxy.hbk.hl.MyView.HeartView;
import love.lxy.hbk.hl.R;
import love.lxy.hbk.hl.Service.MusicService;
import love.lxy.hbk.hl.Util.GestureListener;
import love.lxy.hbk.hl.Util.Util;

public class LoveTimeActivity extends AppCompatActivity {

    private Typeface typeface, kai_ti_typeface;

    private HeartView heartView = null;

    private TextView love_you_tv;
    private TextView love_time_year_tv, love_time_month_tv, love_time_day_tv, love_time_hour_minute_sec_tv;

    private int loved_year, loved_month, loved_day, loved_hour, loved_minute, loved_second;
    private int current_year;

    private boolean isRunnable = true;

    private List<TextView> textViews = new ArrayList<>();

    private HeartClickLayout layout = null;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love_time);

        gestureDetector = new GestureDetector(this, new onGestureListener());

        // 加载字体
        AssetManager manager = getAssets();
        typeface = Typeface.createFromAsset(manager, "fonts/huawenxingkai.ttf");
        kai_ti_typeface = Typeface.createFromAsset(manager, "fonts/kaiti.ttf");

        InitControl();

        InitAnimator();   // 初始化activity界面
        InitTime();    // 初始化计时器
        startTextViewAnimator();

    }

    private void InitControl() {

        layout = findViewById(R.id.love_time_heart_clicked_layout);
        HeartPathView heartPathView = findViewById(R.id.love_time_heart_path_view);
        heartPathView.setBackgroundID(LoveTimeActivity.this, R.drawable.pigeons_bg);

        if (Util.background_heart) {
            heartView = findViewById(R.id.love_time_heart_view);
            heartView.start(this);
        }

        TextView love_time_tv1 = findViewById(R.id.love_time_tv1);
        TextView love_time_tv2 = findViewById(R.id.love_time_tv2);
        love_you_tv = findViewById(R.id.love_time_love_you_tv);
        love_time_year_tv = findViewById(R.id.love_time_year_tv);
        love_time_month_tv = findViewById(R.id.love_time_month_tv);
        love_time_day_tv = findViewById(R.id.love_time_day_tv);
        TextView year_tv = findViewById(R.id.year_tv);
        TextView month_tv = findViewById(R.id.month_tv);
        TextView day_tv = findViewById(R.id.day_tv);
        love_time_hour_minute_sec_tv = findViewById(R.id.love_time_hour_minute_sec_tv);
        TextView love_time_tv3 = findViewById(R.id.love_time_tv3);

        love_time_tv1.setAlpha(0);
        love_time_tv2.setAlpha(0);
        love_you_tv.setAlpha(0);
        love_time_year_tv.setAlpha(0);
        love_time_month_tv.setAlpha(0);
        love_time_day_tv.setAlpha(0);
        year_tv.setAlpha(0);
        month_tv.setAlpha(0);
        day_tv.setAlpha(0);
        love_time_hour_minute_sec_tv.setAlpha(0);
        love_time_tv3.setAlpha(0);

        love_time_tv1.setTypeface(kai_ti_typeface);
        love_time_tv2.setTypeface(kai_ti_typeface);
        love_you_tv.setTypeface(typeface);
        love_time_year_tv.setTypeface(typeface);
        love_time_month_tv.setTypeface(typeface);
        love_time_day_tv.setTypeface(typeface);
        year_tv.setTypeface(kai_ti_typeface);
        month_tv.setTypeface(kai_ti_typeface);
        day_tv.setTypeface(kai_ti_typeface);
        love_time_hour_minute_sec_tv.setTypeface(kai_ti_typeface);
        love_time_tv3.setTypeface(kai_ti_typeface);

        caculateTime();
        updateTextView();

        textViews.clear();
        textViews.add(love_time_tv1);
        textViews.add(love_time_tv2);
        textViews.add(love_you_tv);
        textViews.add(love_time_year_tv);
        textViews.add(year_tv);
        textViews.add(love_time_month_tv);
        textViews.add(month_tv);
        textViews.add(love_time_day_tv);
        textViews.add(day_tv);
        textViews.add(love_time_hour_minute_sec_tv);
        textViews.add(love_time_tv3);

    }

    private void startTextViewAnimator() {
        if (textViews.size() == 0)
            return;
        Util.valueAlphaAnimation(textViews.get(0));
        for (int i = 1; i < textViews.size(); i++) {
            handler_text_view.sendEmptyMessageDelayed(i, 1500 * i);
        }
    }

    // 初始化activity界面
    private void InitAnimator() {
        // 界面标题栏初始化，包括设置TextView的字体格式、启动动画
        ImageView love_time_title_iv = findViewById(R.id.love_time_title_iv);
        TextView love_time_title_tv = findViewById(R.id.love_time_title_tv);
        love_time_title_tv.setTypeface(typeface);
        Util.startHeartJumpAnimator(love_time_title_iv);
    }

    // 计时初始化
    private void InitTime() {

        try {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    post();
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 根据当前时间计算与相恋日期距离的时间
    private void caculateTime() {

        int love_year = 2017, love_month = 11, love_day = 18;

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        loved_hour = calendar.get(Calendar.HOUR_OF_DAY);
        loved_minute = calendar.get(Calendar.MINUTE);
        loved_second = calendar.get(Calendar.SECOND);

        current_year = year;

        loved_day = day - love_day;
        if (loved_day < 0) {
            loved_day += getDaysOfYearMonth(year, month - 1);
            month -= 1;
        }

        loved_month = month - love_month;
        if (loved_month < 0) {
            year -= 1;
            loved_month += 12;
        }
        loved_year = year - love_year;
    }

    // 求某一年某一月有多少天
    private int getDaysOfYearMonth(int year, int month) {
        int days;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);//注意一定要写5，不要写6！Calendar.MONTH是从0到11的！
        days = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        Log.i("titi", year + "年" + month + "月有" + days + "天");
        return days;
    }

    // 每一秒接收一次消息，然后更新一次界面
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    updateLoveTime();
                    LoveTimeActivity.this.post();
                    break;
            }
        }
    };

    // 发送时钟信号
    private void post() {
        if (isRunnable) {
            handler.sendEmptyMessageDelayed(1, 1000);
        }
    }

    // 更新相恋时间
    private void updateLoveTime() {

        loved_second++;
        if (loved_second >= 60) {
            loved_minute++;
            loved_second = 0;
        }
        if (loved_minute >= 60) {
            loved_hour++;
            loved_minute = 0;
        }
        if (loved_hour >= 24) {
            loved_day++;
            loved_hour = 0;
        }
        int day_of_current_month = getDaysOfYearMonth(current_year, loved_month + 1);
        if (loved_day > day_of_current_month) {
            loved_month++;
            loved_day = 1;
        }
        if (loved_month >= 12) {
            loved_year++;
            loved_month = 0;
        }
        updateTextView();
    }

    // 更新界面显示的内容
    private void updateTextView() {

        String hour_minute_sec = loved_hour + "小时" + loved_minute + "分钟" + loved_second + "秒";
        love_time_year_tv.setText(String.valueOf(loved_year));
        love_time_month_tv.setText(String.valueOf(loved_month));
        love_time_day_tv.setText(String.valueOf(loved_day));
        love_time_hour_minute_sec_tv.setText(hour_minute_sec);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        caculateTime();    // 重新计算时间
        isRunnable = true; // 发送时钟信号是否正在运行
        if (heartView != null) {
            heartView.updateHearts();
        }
        Intent intent = new Intent(LoveTimeActivity.this, MusicService.class);
        startService(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        isRunnable = false;
        if (heartView != null) {
            heartView.cancelAnimator();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunnable = false;
        if (heartView != null) {
            heartView.cancelAnimator();
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler_text_view = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Util.valueAlphaAnimation(textViews.get(msg.what));
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
