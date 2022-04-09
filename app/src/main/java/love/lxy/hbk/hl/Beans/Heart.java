package love.lxy.hbk.hl.Beans;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;
import java.util.Map;

import love.lxy.hbk.hl.Util.Util;

/**
 * Created by 19216 on 2019/8/1.
 */

public class Heart {

    public int width, height;
    public float x, y, speed, rotation, rotationSpeed;
    public Bitmap bitmap;

    @SuppressLint("UseSparseArrays")
    private static Map<Integer, Bitmap> bitmapMap = new HashMap<>();

    public Heart() {}

    static public Heart createHeart(int resId, Context context) {
        Heart heart = new Heart();

        Bitmap originalBitmap = BitmapFactory.decodeResource(context.getResources(),resId);
        heart.width = originalBitmap.getWidth();
        heart.height = originalBitmap.getHeight();

        // 初始坐标位置
        heart.x = (float) (Util.getScreenWidth(context) * Math.random() - heart.width);
        heart.y = (heart.height + (float)Math.random() * Util.getScreenHeight(context));
        // 定义下落的速度
        heart.speed = Util.dipToPx(context, 50) + (float) Math.random() * Util.dipToPx(context,50);
        // 随机旋转角度
        heart.rotation = (float) Math.random() * 180 - 90;
        heart.rotationSpeed = (float) Math.random() * 90 - 45;

        // 获取bitmap
        heart.bitmap = bitmapMap.get(resId);
        if (heart.bitmap == null) {
            heart.bitmap = Bitmap.createScaledBitmap(originalBitmap,
                    heart.width, heart.height,true);
            bitmapMap.put(resId, heart.bitmap);
        }

        return heart;
    }

    @Override
    public String toString() {
        return "Heart{" +
                "width=" + width +
                ", height=" + height +
                ", x=" + x +
                ", y=" + y +
                ", speed=" + speed +
                ", rotation=" + rotation +
                ", rotationSpeed=" + rotationSpeed +
                ", bitmap=" + bitmap +
                '}';
    }
}
