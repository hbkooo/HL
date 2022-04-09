package love.lxy.hbk.hl.Activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import love.lxy.hbk.hl.MyView.HeartView;
import love.lxy.hbk.hl.R;
import love.lxy.hbk.hl.Service.MusicService;
import love.lxy.hbk.hl.Util.Util;

public class WelcomeActivity extends AppCompatActivity {

    private ImageView guide1, guide2,guide3,guide4,guide5,guide6,guide7,guide8;
    private List<ImageView> guides = new ArrayList<>();
    private List<View> list = new ArrayList<>();
    private int[] image = {R.drawable.pink_dot, R.drawable.white_dot};

    private ViewPager viewPager;
    private ImageView welcome_love_iv;
    private TextView welcome_tv;

    private Typeface typeface;

    private ImageView fly_kiss_iv1, fly_kiss_iv2, fly_kiss_iv3, fly_kiss_iv4, fly_kiss_iv5, fly_kiss_iv6;
    private DisplayMetrics metrics; // 屏幕大小信息

    private HeartView last_page_heart_view = null;

    // 所有的动画效果
    private List<ObjectAnimator> animatorList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        AssetManager manager = getAssets();
        typeface = Typeface.createFromAsset(manager,"fonts/huawenxingkai.ttf");

        welcome_love_iv = (ImageView) findViewById(R.id.welcome_love_iv);

        animatorList.addAll(Util.startHeartJumpAnimator(welcome_love_iv));

        welcome_tv = (TextView) findViewById(R.id.welcome_tv);
        welcome_tv.setTypeface(typeface);

        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        startService(new Intent(WelcomeActivity.this, MusicService.class));

        InitControl();


    }

    private void InitControl() {
        guide1 = (ImageView) findViewById(R.id.guide1);
        guide2 = (ImageView) findViewById(R.id.guide2);
        guide3 = (ImageView) findViewById(R.id.guide3);
        guide4 = (ImageView) findViewById(R.id.guide4);
        guide5 = (ImageView) findViewById(R.id.guide5);
        guide6 = (ImageView) findViewById(R.id.guide6);
        guide7 = (ImageView) findViewById(R.id.guide7);
        guide8 = (ImageView) findViewById(R.id.guide8);

        guides.add(guide1);
        guides.add(guide2);
        guides.add(guide3);
        guides.add(guide4);
        guides.add(guide5);
        guides.add(guide6);
        guides.add(guide7);
        guides.add(guide8);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        LayoutInflater inflater = getLayoutInflater();


        View view1 = inflater.inflate(R.layout.start_item, null, false);
        ((ImageView) view1.findViewById(R.id.image_item))
                .setImageResource(R.drawable.process2_1);
        TextView tv1 = (TextView) view1.findViewById(R.id.welcome_text_tv);
        tv1.setText("大");
        tv1.setTypeface(typeface);

        View view2 = inflater.inflate(R.layout.start_item, null, false);
        ((ImageView) view2.findViewById(R.id.image_item))
                .setImageResource(R.drawable.process10_1);
        TextView tv2 = (TextView) view2.findViewById(R.id.welcome_text_tv);
        tv2.setText("傻");
        tv2.setTypeface(typeface);

        View view3 = inflater.inflate(R.layout.start_item, null, false);
        ((ImageView) view3.findViewById(R.id.image_item))
                .setImageResource(R.drawable.process11_1);
        TextView tv3 = (TextView) view3.findViewById(R.id.welcome_text_tv);
        tv3.setText("傻");
        tv3.setTypeface(typeface);
        // 添加一个引导页

        View view4 = inflater.inflate(R.layout.start_item, null, false);
        ((ImageView) view4.findViewById(R.id.image_item))
                .setImageResource(R.drawable.process14_2);
        TextView tv4 = (TextView) view4.findViewById(R.id.welcome_text_tv);
        tv4.setText("七");
        tv4.setTypeface(typeface);


        View view5 = inflater.inflate(R.layout.start_item, null, false);
        ((ImageView) view5.findViewById(R.id.image_item))
                .setImageResource(R.drawable.process21_1);
        TextView tv5 = (TextView) view5.findViewById(R.id.welcome_text_tv);
        tv5.setText("夕");
        tv5.setTypeface(typeface);


        View view6 = inflater.inflate(R.layout.start_item, null, false);
        ((ImageView) view6.findViewById(R.id.image_item))
                .setImageResource(R.drawable.process22_1);
        TextView tv6 = (TextView) view6.findViewById(R.id.welcome_text_tv);
        tv6.setText("快");
        tv6.setTypeface(typeface);


        View view7 = inflater.inflate(R.layout.start_item, null, false);
        ((ImageView) view7.findViewById(R.id.image_item))
                .setImageResource(R.drawable.process32_1);
        TextView tv7 = (TextView) view7.findViewById(R.id.welcome_text_tv);
        tv7.setText("乐");
        tv7.setTypeface(typeface);

        View view8 = inflater.inflate(R.layout.start_last, null, false);
//        ((ImageView) view8.findViewById(R.id.image_item))
//                .setImageResource(R.drawable.login_bg);
        last_page_heart_view = view8.findViewById(R.id.welcome_heart_view);
        fly_kiss_iv1 = view8.findViewById(R.id.fly_kiss_iv1);
        fly_kiss_iv2 = view8.findViewById(R.id.fly_kiss_iv2);
        fly_kiss_iv3 = view8.findViewById(R.id.fly_kiss_iv3);
        fly_kiss_iv4 = view8.findViewById(R.id.fly_kiss_iv4);
        fly_kiss_iv5 = view8.findViewById(R.id.fly_kiss_iv5);
        fly_kiss_iv6 = view8.findViewById(R.id.fly_kiss_iv6);
        TextView tv8 = view8.findViewById(R.id.welcome_text_tv);
        tv8.setText("biubiu~");
        tv8.setTypeface(typeface);
        TextView welcome_love_speech_tv = view8.findViewById(R.id.welcome_love_speech_tv);
        String love_speech = "        傻猪猪，七夕节快乐呐，爱你么么哒。不过对不起，我没有在你身边陪着你。" +
                "想给你买礼物你不想让我花钱，所以之前你让" +
                "我买了那个包，你说省着钱以后有计划，但是我想在情人节当天给你准备另外一个礼物。想了好几天，想用" +
                "我自己学的东西为你准备，所以为你开发这款APP，可能做的东西不太完美，但是希望你能喜欢。";
        welcome_love_speech_tv.setTypeface(typeface);
        welcome_love_speech_tv.setText(love_speech);
        TextView start_app_tv = view8.findViewById(R.id.welcome_start_app_tv);
        start_app_tv.setTypeface(typeface);
        start_app_tv.setOnClickListener(new mClick());


        list.add(view1);
        list.add(view2);
        list.add(view3);
        list.add(view4);
        list.add(view5);
        list.add(view6);
        list.add(view7);
        list.add(view8);

        ViewPagerAdapter adapter = new ViewPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new PagerChangeListener());
        viewPager.setCurrentItem(0);
    }

    private class ViewPagerAdapter extends android.support.v4.view.PagerAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position));
            return list.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private class mClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.welcome_start_app_tv:
                    SharedPreferences preferences = getSharedPreferences("isFirst",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("isFirstIn",false);
                    editor.apply();
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                    finish();
                    break;
            }
        }
    }

    private class PagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            for(ImageView guide : guides) {
                guide.setImageResource(image[1]);
            }
            switch (position) {
                case 0:
                    guide1.setImageResource(image[0]);
                    break;
                case 1:
                    guide2.setImageResource(image[0]);
                    break;
                case 2:
                    guide3.setImageResource(image[0]);
                    break;
                case 3:
                    guide4.setImageResource(image[0]);
                    break;
                case 4:
                    guide5.setImageResource(image[0]);
                    break;
                case 5:
                    guide6.setImageResource(image[0]);
                    break;
                case 6:
                    guide7.setImageResource(image[0]);
                    break;
                case 7:
                    guide8.setImageResource(image[0]);
                    fly_kiss(fly_kiss_iv1);
                    handler.sendEmptyMessageDelayed(2,100);
                    handler.sendEmptyMessageDelayed(3,200);
                    handler.sendEmptyMessageDelayed(4,300);
                    handler.sendEmptyMessageDelayed(5,400);
                    handler.sendEmptyMessageDelayed(6,500);

                    if (last_page_heart_view != null && !last_page_heart_view.isRunning()) {
                        last_page_heart_view.start(WelcomeActivity.this);
                    }

//                    Toast.makeText(WelcomeActivity.this, metrics.densityDpi + "," + fly_kiss_iv1.getWidth(), Toast.LENGTH_LONG).show();

                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private void fly_kiss(ImageView fly_kiss_iv) {

        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(fly_kiss_iv,
                "translationX", 0,
                metrics.widthPixels - fly_kiss_iv.getWidth()*2*metrics.densityDpi/160)
                .setDuration(1000);
        objectAnimatorX.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimatorX.start();

        // y =  y = -150. * x * (x-540.) / (270*270)
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(fly_kiss_iv,
                "translationY", -10.9053497942f,
        -21.3991769547f, -31.4814814815f, -41.1522633745f, -50.4115226337f, -59.2592592593f,
        -67.695473251f, -75.7201646091f, -83.3333333333f, -90.5349794239f, -97.3251028807f,
        -103.703703704f, -109.670781893f, -115.226337449f, -120.37037037f, -125.102880658f,
        -129.423868313f, -133.333333333f, -136.83127572f, -139.917695473f, -142.592592593f,
        -144.855967078f, -146.70781893f, -148.148148148f, -149.176954733f, -149.794238683f,
        -150.0f, -149.794238683f,
        -149.176954733f, -148.148148148f, -146.70781893f, -144.855967078f, -142.592592593f,
        -139.917695473f, -136.83127572f, -133.333333333f, -129.423868313f, -125.102880658f,
        -120.37037037f, -115.226337449f, -109.670781893f, -103.703703704f, -97.3251028807f,
        -90.5349794239f, -83.3333333333f, -75.7201646091f, -67.695473251f, -59.2592592593f,
        -50.4115226337f, -41.1522633745f, -31.4814814815f, -21.3991769547f, -10.9053497942f,
                0).setDuration(1000);
        objectAnimatorY.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimatorY.start();

        animatorList.add(objectAnimatorX);
        animatorList.add(objectAnimatorY);

        Log.i("fly_kiss: ", String.valueOf(metrics.widthPixels - fly_kiss_iv.getWidth()*2*metrics.densityDpi/160));
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    fly_kiss(fly_kiss_iv2);
                    break;
                case 3:
                    fly_kiss(fly_kiss_iv3);
                    break;
                case 4:
                    fly_kiss(fly_kiss_iv4);
                    break;
                case 5:
                    fly_kiss(fly_kiss_iv5);
                    break;
                case 6:
                    fly_kiss(fly_kiss_iv6);
                    break;
            }
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        last_page_heart_view.cancelAnimator();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        last_page_heart_view.cancelAnimator();
        for (ObjectAnimator animator : animatorList) {
            animator.cancel();
        }
    }

}
