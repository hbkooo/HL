package love.lxy.hbk.hl.Activity.LoveTest;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import love.lxy.hbk.hl.MyView.HeartClickLayout;
import love.lxy.hbk.hl.MyView.HeartView;
import love.lxy.hbk.hl.R;
import love.lxy.hbk.hl.Service.MusicService;
import love.lxy.hbk.hl.Util.Util;

public class LoveTest1Activity extends AppCompatActivity implements View.OnClickListener,
        View.OnTouchListener {

    private Typeface typeface;

    private ImageView left_pigeon_iv, right_pigeon_iv;
    private TextView question_title_tv;
    private Button not_love_btn, love_btn;

    private int not_love_btn_touch_num = 0;
    private int[] last_location = {0, 0};

    private String not_love_string = "不爱";
    private String love_string = "爱";

    // 弹出对话框布局信息
    private HeartView dialog_heart_view = null;
    private View dialog_view = null;
    private AlertDialog dialog = null;

    private HeartClickLayout layout = null;
    private GestureDetector gestureDetector;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love_test1);
        AssetManager manager = getAssets();
        typeface = Typeface.createFromAsset(manager, "fonts/huawenxingkai.ttf");
        InitControl();
        startPigeonAnimator();
    }

    private void InitControl() {

        gestureDetector = new GestureDetector(this, new onGestureListener());
        layout = findViewById(R.id.love_test1_heart_click_layout);

        findViewById(R.id.love_test1_last_question_iv).setOnClickListener(this);
        findViewById(R.id.love_test1_next_question_iv).setOnClickListener(this);

        left_pigeon_iv = findViewById(R.id.love_test_left_pigeon_iv);
        right_pigeon_iv = findViewById(R.id.love_test_right_pigeon_iv);
        question_title_tv = findViewById(R.id.question_title_tv);
        love_btn = findViewById(R.id.love_btn);
        not_love_btn = findViewById(R.id.not_love_btn);

        question_title_tv.setTypeface(typeface);

        love_btn.setOnClickListener(this);
        not_love_btn.setOnClickListener(this);

        not_love_btn.setOnTouchListener(this);
        love_btn.setOnTouchListener(this);

//        HeartPathView heartPathView = findViewById(R.id.surface_view);
//        heartPathView.setBackgroundID(LoveTest1Activity.this,R.drawable.hbk_self);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.love_btn:
                Log.i("touch", "love btn onClick: ");
                if (love_string.equals(love_btn.getText().toString())) {
                    // 文字是我爱你
                    rightLoveYou();
                }
                break;
            case R.id.not_love_btn:
                Log.i("touch", "not love btn onClick: ");
                if (love_string.equals(not_love_btn.getText().toString())) {
                    // 文字是我爱你
                    rightLoveYou();
                }
                break;
            case R.id.love_test1_last_question_iv:
//                startActivity(new Intent(LoveTest1Activity.this, LoveTest1Activity.class));
//                finish();
                break;
            case R.id.love_test1_next_question_iv:
                startActivity(new Intent(LoveTest1Activity.this, LoveTest2Activity.class));
                finish();
                break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Log.i("hbk touch", "onTouch event: ");
        switch (view.getId()) {
            case R.id.not_love_btn:

                if (!love_string.equals(not_love_btn.getText().toString())) {
                    // 文字不是我爱你
                    if (motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN) {
                        not_love_btn_touch_num++;
                        int[] location = new int[2];
                        not_love_btn.getLocationOnScreen(location);
                        Log.i("hbk touch", "onTouch: x : " + location[0] + ", y : " + location[1]);
                        translateBtn(not_love_btn);
                    }
                }
                break;
            case R.id.love_btn:
                if (!love_string.equals(love_btn.getText().toString())) {
                    // 文字不是我爱你
                    not_love_btn.setText(not_love_string);
                    love_btn.setText(love_string);
                    not_love_btn.setBackgroundResource(R.drawable.heart_broken);
                    love_btn.setBackgroundResource(R.drawable.heart);
                }
                break;
        }
        return false;
    }

    private void translateBtn(Button button) {

        switch (not_love_btn_touch_num % 5) {
            case 1:
                translate(not_love_btn, last_location[0], button.getWidth(),
                        last_location[1], -button.getHeight());
                break;
            case 2:
                translate(not_love_btn, last_location[0], 0,
                        last_location[1], 0);
                break;
            case 3:
                translate(not_love_btn, last_location[0], 0,
                        last_location[1], -button.getHeight());
                break;
            case 4:
                translate(not_love_btn, last_location[0], 0,
                        last_location[1], 0);
                break;
            case 0:
                not_love_btn.setText(love_string);
                love_btn.setText(not_love_string);
                not_love_btn.setBackgroundResource(R.drawable.heart);
                love_btn.setBackgroundResource(R.drawable.heart_broken);
                break;
        }

    }

    private void translate(Button button, int fromX, int toX, int fromY, int toY) {

        Toast.makeText(LoveTest1Activity.this, "想点我，想都不要想，哼╭(╯^╰)╮",
                Toast.LENGTH_SHORT).show();
        last_location[0] = toX;
        last_location[1] = toY;

        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(button,
                "translationX", fromX, toX)
                .setDuration(100);
        objectAnimatorX.start();

        // y =  y = -150. * x * (x-540.) / (270*270)
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(button,
                "translationY", fromY, toY).setDuration(100);
        objectAnimatorY.start();
    }

    // 最后点击我爱你
    private void rightLoveYou() {

        Util.loveTest[0] = true;
        Toast.makeText(LoveTest1Activity.this, "我也爱你", Toast.LENGTH_LONG).show();

        if (dialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoveTest1Activity.this);

            dialog_view = getLayoutInflater().inflate(R.layout.dialog_love_me, null, false);
            builder.setView(dialog_view);
            builder.setCancelable(false);
            dialog = builder.show();

            String love_speech = "        遇见你是我一生最幸福的事，我爱你，我的傻宝宝，forever！" +
                    "爱情是甜美的，跟你在一块更是非常甜。你知道吗，甜有100种方式，吃糖、蛋糕，还有每天98次想你。" +
                    "而我现在正在不想吃糖和蛋糕，只剩下一种甜的方式:";

            ((TextView) dialog_view.findViewById(R.id.dialog_love_me_tv)).setTypeface(typeface);
            ((TextView) dialog_view.findViewById(R.id.dialog_love_me_tv)).setText(love_speech);

            if (Util.background_heart) {
                dialog_heart_view = dialog_view.findViewById(R.id.dialog_love_me_heart_view);
                dialog_heart_view.start(LoveTest1Activity.this);
            }

            Button confirm = dialog_view.findViewById(R.id.dialog_confirm_btn);
            confirm.setTypeface(typeface);
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.hide();
                }
            });
        } else {
            dialog.show();
        }


    }

    // 两个鸽子飞舞动画
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
    protected void onRestart() {
        super.onRestart();
        if (dialog_heart_view != null) {
            dialog_heart_view.updateHearts();
        }
        Intent intent = new Intent(LoveTest1Activity.this, MusicService.class);
        startService(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (dialog_heart_view != null) {
            dialog_heart_view.cancelAnimator();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog_heart_view != null) {
            dialog_heart_view.cancelAnimator();
        }
        if (dialog != null) {
            dialog.dismiss();
        }
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
