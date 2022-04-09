package love.lxy.hbk.hl.MyView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Map;

import love.lxy.hbk.hl.R;

/**
 * Created by 19216 on 2019/8/2.
 */

public class HeartPathView extends SurfaceView implements SurfaceHolder.Callback {
    SurfaceHolder surfaceHolder;
    private Canvas surface_canvas = null;
    int offsetX;
    int offsetY;
    private int width;
    private int height;
    private boolean isDrawing = false;
    private Bitmap surface_view_bitmap;
    private Canvas canvas;
    private int heartRadio = 1;

    private Bitmap bg_bitmap = null;
    private int bg_color = R.color.myWhite;

    private Context context;

    private int[] hearts = {R.drawable.fly_heart0, R.drawable.fly_heart1, R.drawable.fly_heart2,
            R.drawable.fly_heart3, R.drawable.fly_heart4};

    private Map<Point, Bitmap> path_hearts = new HashMap<>();

    public HeartPathView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public HeartPathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
    }

    public Point getHeartPoint(float angle) {
        float t = (float) (angle / Math.PI);
        float x = (float) (heartRadio * (16 * Math.pow(Math.sin(t), 3)));
        float y = (float) (-heartRadio * (13 * Math.cos(t) - 5 * Math.cos(2 * t) - 2 * Math.cos(3 * t) - Math.cos(4 * t)));

        return new Point(offsetX + (int) x, offsetY + (int) y);
    }

    //绘制列表里所有的心
    private void drawHeart() {

        canvas.drawBitmap(bg_bitmap, 0, 0, null);
        for (Point p : path_hearts.keySet()) {
            canvas.drawBitmap(path_hearts.get(p), p.x, p.y, null);
        }

        try {
            surface_canvas = surfaceHolder.lockCanvas();
            surface_canvas.drawBitmap(surface_view_bitmap, 0, 0, null);
        } catch (Exception e) {
        } finally {
            if (surface_canvas != null) {
                surfaceHolder.unlockCanvasAndPost(surface_canvas);
            }
        }

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Log.i("surfaceview", "draw: ");

    }

    //开启一个新线程绘制
    private void drawOnNewThread() {
        new Thread() {
            @Override
            public void run() {
                if (isDrawing) return;
                isDrawing = true;

                float angle = 10;
                while (true) {

                    addOneHeart(angle);

                    if (angle >= 30) {
                        break;
                    } else {
                        angle += 0.2;
                    }
                    drawHeart();
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                isDrawing = false;
            }
        }.start();
    }

    // 获取一个新的点
    private void addOneHeart(float angle) {
        Point p = getHeartPoint(angle);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                hearts[(int) (Math.random() * 10) % hearts.length]);
        path_hearts.put(p, bitmap);
    }

    // 设置背景图片
    public void setBackgroundID(Context context, int resId) {
        bg_bitmap = BitmapFactory.decodeResource(context.getResources(), resId);
    }

    public void setBackgroundColor(int resourceColorId) {
        this.bg_color = resourceColorId;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        Log.i("hbk ", "surfaceChanged: " + width + ", " + height);
        Log.i("hbk ", "surfaceChanged: " + getMeasuredWidth() + ", " + getMeasuredHeight());
        Log.i("hbk ", "surfaceChanged: " + getWidth() + ", " + getHeight());

        path_hearts.clear();

        this.width = width;
        this.height = height;
        //我的手机宽度像素是1080，发现参数设置为30比较合适，这里根据不同的宽度动态调整参数
        heartRadio = width * 30 / 1080;

        offsetX = width / 2;
        offsetY = height / 2 - 55;
        surface_view_bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        canvas = new Canvas(surface_view_bitmap);
        if (bg_bitmap != null) {
            bg_bitmap = Bitmap.createScaledBitmap(bg_bitmap, getMeasuredWidth(), getHeight(), true);
        } else {
            bg_bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            bg_bitmap.eraseColor(getResources().getColor(bg_color));
        }
        drawOnNewThread();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

}
