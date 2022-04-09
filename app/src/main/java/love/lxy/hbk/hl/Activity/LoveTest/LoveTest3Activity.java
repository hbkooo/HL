package love.lxy.hbk.hl.Activity.LoveTest;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import love.lxy.hbk.hl.MyView.HeartClickLayout;
import love.lxy.hbk.hl.MyView.HeartView;
import love.lxy.hbk.hl.R;
import love.lxy.hbk.hl.Util.Util;

public class LoveTest3Activity extends AppCompatActivity implements View.OnClickListener {

    private Typeface kai_ti_typeface, typeface;

    private TextView title_tv, question_tv, answerA_tv, answerB_tv, answerC_tv, answerD_tv;

    private String question, answerA, answerB, answerC, answerD;

    private AlertDialog dialog = null;
    private TextView dialog_answer_title, dialog_answer_analyze;
    private HeartView dialog_heart_view = null;
    private boolean isStart = false;

    private HeartClickLayout layout = null;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love_test3);

        // 加载字体
        AssetManager manager = getAssets();
        typeface = Typeface.createFromAsset(manager, "fonts/huawenxingkai.ttf");
        kai_ti_typeface = Typeface.createFromAsset(manager, "fonts/kaiti.ttf");

        InitString();
        InitControl();

    }

    private void InitControl() {

        gestureDetector = new GestureDetector(this, new onGestureListener());
        layout = findViewById(R.id.love_test3_heart_click_layout);

        findViewById(R.id.love_test3_answerA_layout).setOnClickListener(this);
        findViewById(R.id.love_test3_answerB_layout).setOnClickListener(this);
        findViewById(R.id.love_test3_answerC_layout).setOnClickListener(this);
        findViewById(R.id.love_test3_answerD_layout).setOnClickListener(this);
        findViewById(R.id.love_test3_last_question_iv).setOnClickListener(this);
        findViewById(R.id.love_test3_next_question_iv).setOnClickListener(this);

        title_tv = findViewById(R.id.love_test3_title_tv);
        question_tv = findViewById(R.id.love_test3_question_tv);
        answerA_tv = findViewById(R.id.love_test3_answerA_tv);
        answerB_tv = findViewById(R.id.love_test3_answerB_tv);
        answerC_tv = findViewById(R.id.love_test3_answerC_tv);
        answerD_tv = findViewById(R.id.love_test3_answerD_tv);

        title_tv.setTypeface(typeface);
        question_tv.setTypeface(kai_ti_typeface);
        answerA_tv.setTypeface(kai_ti_typeface);
        answerB_tv.setTypeface(kai_ti_typeface);
        answerC_tv.setTypeface(kai_ti_typeface);
        answerD_tv.setTypeface(kai_ti_typeface);

        question_tv.setText(question);

    }

    private void InitString() {

        question = "男朋友马上要出国了，你和他最后一次相聚，他一定要带走你的一样东西作为纪念，" +
                "你会让他带走什么？";
        answerA = "你仍然放不下你的初恋，青葱的岁月在你的记忆里留下了不能抹去的印记，那些美好的如童话般" +
                "的日子是你闭上眼就能浮现起的画面，你固执的守候着当初信誓旦旦的诺言，一遍又一的诵读，" +
                "演着一个人从一而终的独角戏。你多想那些回忆给你一些勇气，让你拨通一个号码，听着渐渐陌生的声音，" +
                "强忍着泪水，轻问一声“HI，是我 ，你最近还好吗？";
        answerB = "至少在此时，你是一个落了单却异常孑然的人，你的生活充实而绚丽多姿，每一天都上演着精彩," +
                "纷繁的尘世变换着它的姿态，你也在随之跟上自己的脚步，只要是有意思的事情，你都会去尝试，" +
                "时刻主宰着自己的人生。所以，爱情成了你生命中最“其次”的组成部分，守候着自己，便是你今生" +
                "最大的快乐。";
        answerC = "不能否认你的暗恋情怀，而他就在你接触最频繁的朋友之中，你们亲密无间，但恋人未满，他" +
                "的一举一动都成了你行为的脚本，而你的嘘寒问暖却总不能让他心知肚明。你怪自己的害羞也" +
                "怪他的迟钝，可你怎么也踏不出关键的一步。也许这个世界上就是有这么一个人，让你乐意去关注，甘愿去默" +
                "默地守候。";
        answerD = "没有谁的守候能比你更幸福，你拥有了共度今生的人，也获得了真挚的情感。你看着爱人熟睡后" +
                "的轮廓傻笑，你听着爱人对你的叮嘱而窝心，不论何时何地，你的心里总是有着一份牵挂，那" +
                "是最甜蜜的负担。 今生今世，你和他一起守护着对方，坚信着幸福和快乐是结局！";


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.love_test3_answerA_layout:
                ShowResult(answerA_tv.getText().toString(), answerA, false);
                break;
            case R.id.love_test3_answerB_layout:
                ShowResult(answerB_tv.getText().toString(), answerB, false);
                break;
            case R.id.love_test3_answerC_layout:
                ShowResult(answerC_tv.getText().toString(), answerC, false);
                break;
            case R.id.love_test3_answerD_layout:
                ShowResult(answerD_tv.getText().toString(), answerD, true);
                Util.loveTest[2] = true;
                break;
            case R.id.love_test3_last_question_iv:
                startActivity(new Intent(LoveTest3Activity.this, LoveTest2Activity.class));
                // 两个参数：第一个进入效果，第二个退出效果
                overridePendingTransition(R.anim.back_in_left,
                        R.anim.back_out_right);
                finish();
                break;
            case R.id.love_test3_next_question_iv:
                Toast.makeText(LoveTest3Activity.this, "当前是最后一个测试了，再稍等一下...",
                        Toast.LENGTH_LONG).show();
                break;


        }
    }

    private void ShowResult(String title, String analyze, boolean showHeart) {

        if (dialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = getLayoutInflater().inflate(R.layout.dialog_question_answer, null, false);
            dialog_answer_title = view.findViewById(R.id.dialog_question_answer_title);
            dialog_answer_analyze = view.findViewById(R.id.dialog_question_answer_analyze);
            dialog_heart_view = view.findViewById(R.id.dialog_question_answer_heart_view);
            dialog_answer_title.setTypeface(kai_ti_typeface);
            dialog_answer_analyze.setTypeface(kai_ti_typeface);
            builder.setView(view);
            dialog = builder.show();
            if (showHeart) {
                dialog_heart_view.start(this);
                isStart = true;
            }
        }

        if (showHeart) {
            dialog_heart_view.setVisibility(View.VISIBLE);
            if (isStart) {
                dialog_heart_view.cancelAnimator();
                dialog_heart_view.updateDialogHeart(10);
            } else {
                dialog_heart_view.start(this);
            }
        } else {
            dialog_heart_view.setVisibility(View.INVISIBLE);
            dialog_heart_view.cancelAnimator();
        }


        dialog_answer_title.setText(title);
        dialog_answer_analyze.setText(analyze);
        dialog.show();

    }

    @Override
    protected void onDestroy() {
        if (dialog_heart_view != null) {
            dialog_heart_view.cancelAnimator();
        }
        super.onDestroy();
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
