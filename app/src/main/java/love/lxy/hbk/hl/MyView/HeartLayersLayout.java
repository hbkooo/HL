package love.lxy.hbk.hl.MyView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import love.lxy.hbk.hl.R;
import love.lxy.hbk.hl.Util.Util;

/**
 * Created by 19216 on 2020/1/12.
 */

public class HeartLayersLayout extends FrameLayout {

    private String TAG = "HeartLayersLayout";

    public boolean stop = true;

    private Context context;

    private int offsetX;
    private int offsetY;
    private int heartRadio = 1;

    private int minDis = 20, maxDis = 32;  // 心形路径最内部和最外部
    private float maxScale = 5f;           // 心图片到达最外部时相比于原始心图的大小倍数
    private float angleDis = 0.3f;         // 取心形路径点的间隔
    private float randomRate = 0.3f;       // 心形路径上显示小心图片的概率
    private int animatorTime = 3000;       // 心图片从最内部移动到最外部的时间间隔
    private int maxAllHearts = 1200;       // 整个屏幕中心最大个数
    private int currentAllHeart = 0;       // 当前屏幕中心的个数

    private List<Point> maxPath = new ArrayList<>();
    private List<Point> minPath = new ArrayList<>();

    private LayoutParams params = null;


    public HeartLayersLayout(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public HeartLayersLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public HeartLayersLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public HeartLayersLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
    }


    /**
     * 根据角度获取爱心的某一个点坐标
     * @param angle 待获取的该角度对应的点
     * @param heartRadio 心的尺度大小
     * @param offsetX 心相对于中心的X偏移，一般取画布宽的一半
     * @param offsetY 心相对于中心的Y偏移，一般取画布高的一半
     * @return
     */
    public Point getHeartPoint(float angle, int heartRadio, int offsetX, int offsetY) {
        float t = (float) (angle / Math.PI);
        float x = (float) (heartRadio * (16 * Math.pow(Math.sin(t), 3)));
        float y = (float) (-heartRadio * (13 * Math.cos(t) - 5 * Math.cos(2 * t)
                - 2 * Math.cos(3 * t) - Math.cos(4 * t)));
        return new Point(offsetX + (int) x, offsetY + (int) y);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        maxPath = Util.getHeartPath(w, h, maxDis, angleDis);
        minPath = Util.getHeartPath(w, h, minDis, angleDis);

        Log.i(TAG, "onSizeChanged: path point size : " + maxPath.size());
        params = new LayoutParams(20, 20);

    }

    public void start() {

        Log.i(TAG, "start: ");

        if(!stop) return;
        stop = false;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!stop) {

                    handler.sendEmptyMessage(1);
//                    Log.i(TAG, "run: " + Math.random());

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Log.e(TAG, "run: 线程睡眠出错" );
                    }


                }
            }
        }).start();

        Log.i(TAG, "run: ******  stop  ***********");

    }

    private void addViews() {

        for(int index = 0; index < maxPath.size(); index++) {

            if (Math.random() < randomRate) {

                Point p = minPath.get(index);

                final ImageView imageView = new ImageView(context);
                imageView.setLayoutParams(params);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.one_love));

                imageView.setX(p.x - imageView.getWidth()/2);
                imageView.setY(p.y - imageView.getHeight()/2);

                addView(imageView);
                currentAllHeart++;

                AnimatorSet animatorSet = getAnimatorSet(imageView, index);
                animatorSet.start();
                animatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        removeView(imageView);
                        currentAllHeart--;
                        Log.i(TAG, "onAnimationEnd: remove imageview, 剩余心数目 : " + currentAllHeart);
                    }
                });
            }
        }

    }

    private AnimatorSet getAnimatorSet(ImageView imageView, int index) {

        // 透明度动画
        ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView, "alpha", 1f, 0f);
        //缩放动画
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(imageView, "scaleX", 0f, maxScale);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(imageView, "scaleY", 0f, maxScale);

//        alpha.setRepeatCount(ValueAnimator.INFINITE);
//        scaleX.setRepeatCount(ValueAnimator.INFINITE);
//        scaleY.setRepeatCount(ValueAnimator.INFINITE);

        long time = animatorTime;
        double random = Math.random();
        if (random >= 0.5) {
            time *= random;
        }

        AnimatorSet set = new AnimatorSet();
        set.playTogether(alpha, scaleX, scaleY);
        set.setDuration(time);

        // 移动动画
        ValueAnimator transform = getTransformAnimator(imageView, index);
        AnimatorSet set1 = new AnimatorSet();
        set1.playTogether(set, transform);
        set1.setTarget(imageView);

        return set1;
    }

    private ValueAnimator getTransformAnimator(final ImageView imageView, int index) {

        final Point minP = minPath.get(index);
        final Point maxP = maxPath.get(index);

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();

                float x = (maxP.x - minP.x) * value + minP.x - params.width/2;
                float y = (maxP.y - minP.y) * value + minP.y - params.height/2;

                imageView.setX(x);
                imageView.setY(y);

            }
        });

        valueAnimator.setTarget(imageView);

        long time = animatorTime;
        double random = Math.random();
        if (random >= 0.5) {
            time *= random;
        }

        valueAnimator.setDuration(time);

        return valueAnimator;

    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (currentAllHeart <= maxAllHearts){
                addViews();
            }
        }
    };

    public void STOP() {
        this.stop = true;
    }


}
