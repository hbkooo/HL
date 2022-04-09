package love.lxy.hbk.hl.Activity.LoveTest;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import love.lxy.hbk.hl.MyView.HeartClickLayout;
import love.lxy.hbk.hl.MyView.HeartView;
import love.lxy.hbk.hl.R;
import love.lxy.hbk.hl.Util.Util;

public class LoveTestActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "LoveTestActivity";

    private Typeface kai_ti_typeface;

    private HeartView heartView;

    private ImageView test1_iv, test2_iv, test3_iv;
    private TextView test1_tv, test2_tv, test3_tv;

    private List<ImageView> imageViews = new ArrayList<>();
    private List<TextView> textViews = new ArrayList<>();

    private HeartClickLayout layout = null;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love_test);

        gestureDetector = new GestureDetector(this, new onGestureListener());

        // 加载字体
        AssetManager manager = getAssets();

        kai_ti_typeface = Typeface.createFromAsset(manager, "fonts/kaiti.ttf");

        InitControl();

        updateView();
    }

    private void InitControl() {

        layout = findViewById(R.id.love_test_heart_click_layout);

        if (Util.background_heart) {
            heartView = findViewById(R.id.love_test_heart_view);
            heartView.start(this);
        }

        test1_iv = findViewById(R.id.love_test1_iv);
        ((LinearLayout) findViewById(R.id.love_test1_layout)).setOnClickListener(this);
        test1_tv = findViewById(R.id.love_test1_tv);
        test1_tv.setTypeface(kai_ti_typeface);

        test2_iv = findViewById(R.id.love_test2_iv);
        ((LinearLayout) findViewById(R.id.love_test2_layout)).setOnClickListener(this);
        test2_tv = findViewById(R.id.love_test2_tv);
        test2_tv.setTypeface(kai_ti_typeface);

        test3_iv = findViewById(R.id.love_test3_iv);
        ((LinearLayout) findViewById(R.id.love_test3_layout)).setOnClickListener(this);
        test3_tv = findViewById(R.id.love_test3_tv);
        test3_tv.setTypeface(kai_ti_typeface);

        imageViews.add(test1_iv);
        imageViews.add(test2_iv);
        imageViews.add(test3_iv);
        textViews.add(test1_tv);
        textViews.add(test2_tv);
        textViews.add(test3_tv);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.love_test1_layout:
                startActivityForResult(new Intent(LoveTestActivity.this,
                        LoveTest1Activity.class), 0);
                break;
            case R.id.love_test2_layout:
                startActivityForResult(new Intent(LoveTestActivity.this,
                        LoveTest2Activity.class), 0);
                break;
            case R.id.love_test3_layout:
                startActivityForResult(new Intent(LoveTestActivity.this,
                        LoveTest3Activity.class), 0);
                break;
        }
    }

    private void updateView() {

        for (int i = 0;i< Util.loveTest.length; i++) {
            if (Util.loveTest[i]) {
                imageViews.get(i).setImageResource(R.drawable.bright_heart);
                textViews.get(i).setTextColor(getResources().getColor(R.color.myColor));
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        updateView();
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onRestart() {
        if (heartView != null) {
            heartView.updateHearts();
        }
        super.onRestart();
    }

    @Override
    protected void onStop() {
        if (heartView != null) {
            heartView.cancelAnimator();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (heartView != null) {
            heartView.cancelAnimator();
        }
        super.onDestroy();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        Log.i(TAG, "onTouchEvent: ");
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
