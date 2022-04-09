package love.lxy.hbk.hl.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import love.lxy.hbk.hl.MyView.HeartClickLayout;
import love.lxy.hbk.hl.MyView.HeartLayersLayout;
import love.lxy.hbk.hl.MyView.HeartLayersView;
import love.lxy.hbk.hl.R;
import love.lxy.hbk.hl.Util.Util;

public class MyHeartActivity extends AppCompatActivity {

    private String TAG = "MyHeartActivity";

    private HeartLayersLayout rootLayout = null;

    private HeartClickLayout layout = null;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_heart);

        InitControl();

    }

    private void InitControl() {

        gestureDetector = new GestureDetector(this, new onGestureListener());
        layout = findViewById(R.id.my_heart_click_layout);

        rootLayout = findViewById(R.id.heart_layers_layout);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // 界面布局加载完毕后回调该函数
        if(hasFocus) {
            rootLayout.start();
            Log.i(TAG, "onWindowFocusChanged: ");
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: ");
        rootLayout.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        rootLayout.STOP();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
        rootLayout.STOP();
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
