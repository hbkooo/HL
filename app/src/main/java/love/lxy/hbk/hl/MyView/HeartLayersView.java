package love.lxy.hbk.hl.MyView;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import love.lxy.hbk.hl.R;

/**
 * Created by 19216 on 2020/1/9.
 */

public class HeartLayersView extends View{

    private String TAG = "HeartLayersView";

    SurfaceHolder surfaceHolder;
    private Context context;

    private int offsetX;
    private int offsetY;
    private int heartRadio = 1;

    private ValueAnimator valueAnimator = null;

    private List<Point> maxPath = new ArrayList<>();
    private List<Point> minPath = new ArrayList<>();


    public HeartLayersView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public HeartLayersView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public HeartLayersView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public HeartLayersView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        init();
    }

    private void init() {
        Log.i(TAG, "init: view");

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //我的手机宽度像素是1080，发现参数设置为30比较合适，这里根据不同的宽度动态调整参数
        heartRadio = w * 30 / 1080; // 心的尺度大小

        offsetX = w / 2;
        offsetY = h / 2; // -55

        Log.i(TAG, "onSizeChanged: heartRadio " + heartRadio);
        Log.i(TAG, "onSizeChanged: offsetX " + offsetX);
        Log.i(TAG, "onSizeChanged: offsetY " + offsetY);

        maxPath.clear();
        minPath.clear();

        int maxRadio = heartRadio;
        int minRadio = w * 25 / 1080;

        float angle = 10;
        while (angle < 180) {
            maxPath.add(getHeartPoint(angle, maxRadio, offsetX, offsetY));
            minPath.add(getHeartPoint(angle, minRadio, offsetX, offsetY));
            angle += 0.5;
        }

        Log.i(TAG, "onSizeChanged: path point size : " + maxPath.size());

    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.i(TAG, "onDraw: ");

        @SuppressLint("DrawAllocation") Paint paint = new Paint();
        paint.setColor(R.color.myColor);
        paint.setStrokeWidth(2);

        @SuppressLint("DrawAllocation") Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.fly_heart3);

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        for (int i = 0; i < maxPath.size(); i++) {
            Point p = maxPath.get(i);
            canvas.drawPoint(p.x, p.y, paint);
            p = minPath.get(i);
            canvas.drawPoint(p.x, p.y, paint);
            if (Math.random() > 0.4) {
                canvas.drawBitmap(bitmap, p.x - w / 2, p.y - h / 2, null);

            }

        }


    }

    // 获取坐标点

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

    public void draw() {

        valueAnimator = ValueAnimator.ofFloat(0,1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Log.i(TAG, " ----- onAnimationUpdate -------");




            }
        });


    }

    public Bitmap thumbImageWithMatrix(float destWidth, float destHeight) {
        Bitmap bitmapOrg = BitmapFactory.decodeResource(context.getResources(), R.drawable.one_love);
        float bitmapOrgW = bitmapOrg.getWidth();
        float bitmapOrgH = bitmapOrg.getHeight();

        float bitmapNewW = (int) destWidth;
        float bitmapNewH = (int) destHeight;

        Matrix matrix = new Matrix();
        matrix.postScale(bitmapNewW / bitmapOrgW, bitmapNewH / bitmapOrgH);
        bitmapOrg.recycle();
        return Bitmap.createBitmap(bitmapOrg, 0, 0, (int) bitmapOrgW, (int) bitmapOrgH, matrix, true);
    }

}
