package love.lxy.hbk.hl.MyView;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import love.lxy.hbk.hl.R;
import love.lxy.hbk.hl.Beans.Heart;

/**
 * Created by 19216 on 2019/8/1.
 */

public class HeartView extends View {

    private Context context;

    private ValueAnimator animator = null;

    private int NUM_HEART = 20;
    private long startTime, prevTime;
    private int heartNums = 0;

    private Matrix matrix = null;

    private List<Heart> hearts = new ArrayList<>();

    public HeartView(Context context) {
        super(context);
        this.context = context;
        Log.i("hbk", " create HeartView: ");

    }

    public HeartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HeartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i("hbk", "-------------  onSizeChanged: ---------");
//        hearts.clear();
//        heartNums = 0;
//        animator.cancel();
//        startTime = System.currentTimeMillis();
//        prevTime = startTime;
//        animator.start();
//        updateHearts();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("hbk", "------- onDraw -------");

        if (matrix == null) {
            matrix = getMatrix();
        }

        for (int i = 0; i < heartNums; i++) {
            Heart heart = hearts.get(i);
            matrix.setTranslate(-heart.width/2, -heart.height/2);
            matrix.postRotate(heart.rotation);
            matrix.postTranslate(heart.width/2 + heart.x, heart.height/2 + heart.y);
            canvas.drawBitmap(heart.bitmap,matrix,null);
        }

//        getHandler().postDelayed()

    }

    public void addHeart(int resId, Context context) {
        Log.i("hbk", "addHeart: " + Math.random());

        Heart heart = Heart.createHeart(resId, context);
        hearts.add(heart);
        heartNums ++;
//        Log.i("hbk", heart1.toString());

    }

    // 开始绘制心
    public void start(Context context) {

        Log.i("hbk", "------- start --------");

        this.context = context;

        updateHearts();

        if (matrix == null) {
            matrix = getMatrix();
        }

        animator = ValueAnimator.ofFloat(0,1);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Log.i("hbk", "------- onAnimationUpdate --------");


                long nowTime = System.currentTimeMillis();
                float secs = (nowTime - prevTime) / 1000f;
                prevTime = nowTime;
                for(int i= 0; i < heartNums; i++) {
                    Heart heart = hearts.get(i);
                    heart.y += (heart.speed * secs);
                    if (heart.y > getHeight()) {
                        heart.y = 0 - heart.height;
                    }
                    heart.rotation = heart.rotation + (heart.rotationSpeed * secs);
                }
                invalidate();

//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//
//
//
//                    }
//                }).start();

            }
        });

        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(800);
        animator.start();

    }

    public boolean isRunning() {
        return animator != null && animator.isRunning();
    }

    public void updateDialogHeart(int num_heart) {
        NUM_HEART = num_heart;
        updateHearts();
    }

    // 更新所有的心
    public void updateHearts() {

        hearts.clear();
        heartNums = 0;

//        addHeart(R.drawable.fly_heart0,context);
//        addHeart(R.drawable.fly_heart2,context);

        for (int i = 0; i < NUM_HEART; i ++) {

            Log.i("hbk", "onCreate: " + Math.random());

            handler.sendEmptyMessageDelayed((int) (Math.random()*10%4),100*i);
        }
        if (animator != null && !animator.isRunning()) {
            animator.start();
        }
    }

    // 停止运行
    public void cancelAnimator() {
        if (animator != null && animator.isRunning()) {
            animator.cancel();
        }
    }

    // 添加不同的心
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    addHeart(R.drawable.fly_heart0,context);
                    addHeart(R.drawable.fly_heart0,context);
                    break;
                case 1:
                    addHeart(R.drawable.fly_heart1,context);
                    addHeart(R.drawable.fly_heart1,context);
                    break;
                case 2:
                    addHeart(R.drawable.fly_heart2,context);
                    addHeart(R.drawable.fly_heart2,context);
                    break;
                case 3:
                    addHeart(R.drawable.fly_heart3,context);
                    addHeart(R.drawable.fly_heart3,context);
                    break;

            }
        }
    };



}
