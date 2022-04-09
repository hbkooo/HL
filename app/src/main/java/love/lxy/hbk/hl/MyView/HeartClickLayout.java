package love.lxy.hbk.hl.MyView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import love.lxy.hbk.hl.Beans.BasEvaluator;
import love.lxy.hbk.hl.R;

/**
 * Created by 19216 on 2020/1/12.
 */

public class HeartClickLayout extends FrameLayout {

    private String TAG = "HeartClickLayout";

    private Context context;
    private Drawable[] icons = new Drawable[6];
    private Interpolator[] interpolators = new Interpolator[4];
    private int mWidth;
    private int mHeight;
    long animatorTime = 2000;

    private LayoutParams params = null;

    public HeartClickLayout(@NonNull Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public HeartClickLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public HeartClickLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    public HeartClickLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        initView();
    }

    private void initView() {

        // 图片资源
        icons[0] = getResources().getDrawable(R.drawable.heart_color1);
        icons[1] = getResources().getDrawable(R.drawable.heart_color2);
        icons[2] = getResources().getDrawable(R.drawable.heart_color3);
        icons[3] = getResources().getDrawable(R.drawable.heart_color4);
        icons[4] = getResources().getDrawable(R.drawable.heart_color5);
        icons[5] = getResources().getDrawable(R.drawable.heart_color6);

        // 插值器
        interpolators[0] = new AccelerateDecelerateInterpolator(); // 在动画开始与结束的地方速率改变比较慢，在中间的时候加速
        interpolators[1] = new AccelerateInterpolator();  // 在动画开始的地方速率改变比较慢，然后开始加速
        interpolators[2] = new DecelerateInterpolator(); // 在动画开始的地方快然后慢
        interpolators[3] = new LinearInterpolator();  // 以常量速率改变

    }

    public void addLoveView(float x, float y) {
        if (x < 100) {
            x = 102;
        }
        if (y < 100) {
            y = 102;
        }
        mWidth = (int) (x - 100);
        mHeight = (int) (y - 100);
        final ImageView iv = new ImageView(context);
        params = new LayoutParams(200, 200);
        iv.setLayoutParams(params);
        iv.setImageDrawable(icons[new Random().nextInt(6)]);
        addView(iv);

        // 开启动画，并且用完销毁
        AnimatorSet set = getAnimatorSet(iv);
        set.start();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // TODO Auto-generated method stub
                super.onAnimationEnd(animation);
                removeView(iv);
            }
        });
    }


    private AnimatorSet getAnimatorSet(ImageView imageView) {

        // 透明度动画
        ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView, "alpha", 0.3f, 1f);
        //缩放动画
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(imageView, "scaleX", 0.2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(imageView, "scaleY", 0.2f, 1f);

//        alpha.setRepeatCount(ValueAnimator.INFINITE);
//        scaleX.setRepeatCount(ValueAnimator.INFINITE);
//        scaleY.setRepeatCount(ValueAnimator.INFINITE);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(alpha, scaleX, scaleY);
        set.setDuration(animatorTime);

        // 移动动画
        ValueAnimator transform = getBzierAnimator(imageView);
        AnimatorSet set1 = new AnimatorSet();
        set1.playTogether(set, transform);
        set1.setTarget(imageView);

        return set1;
    }

    /**
     * 贝塞尔动画
     */
    private ValueAnimator getBzierAnimator(final ImageView iv) {

        // TODO Auto-generated method stub
        PointF[] PointFs = getPointFs(iv); // 4个点的坐标
        BasEvaluator evaluator = new BasEvaluator(PointFs[1], PointFs[2]);
        ValueAnimator valueAnim = ValueAnimator.ofObject(evaluator, PointFs[0], PointFs[3]);
        valueAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                // TODO Auto-generated method stub
                PointF p = (PointF) animation.getAnimatedValue();
                iv.setX(p.x);
                iv.setY(p.y);
                iv.setAlpha(1 - animation.getAnimatedFraction()); // 透明度
            }

        });
        valueAnim.setTarget(iv);
        valueAnim.setDuration(animatorTime);
        valueAnim.setInterpolator(interpolators[new Random().nextInt(4)]);
        return valueAnim;
    }

    private PointF[] getPointFs(ImageView iv) {

        if (mHeight <= 1) mHeight = 2;
        if (mWidth <= 1) mWidth = 2;

        Log.i(TAG, "getPointFs: mHeight : " + mHeight + ", mWidth : " + mWidth);

        // TODO Auto-generated method stub
        PointF[] PointFs = new PointF[4];
        PointFs[0] = new PointF(); // p0
        PointFs[0].x = ((int) mWidth);
        PointFs[0].y = mHeight;
        PointFs[1] = new PointF(); // p1
        PointFs[1].x = new Random().nextInt(mWidth);
        PointFs[1].y = new Random().nextInt(mHeight / 2) + mHeight / 2 + params.height;
        PointFs[2] = new PointF(); // p2
        PointFs[2].x = new Random().nextInt(mWidth);

        PointFs[2].y = new Random().nextInt(mHeight / 2);

        PointFs[3] = new PointF(); // p3

        PointFs[3].x = new Random().nextInt(mWidth);

        PointFs[3].y = 0;

        return PointFs;

    }


}
