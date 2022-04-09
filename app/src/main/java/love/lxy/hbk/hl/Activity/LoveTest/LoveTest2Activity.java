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

import love.lxy.hbk.hl.MyView.HeartClickLayout;
import love.lxy.hbk.hl.MyView.HeartView;
import love.lxy.hbk.hl.R;
import love.lxy.hbk.hl.Util.Util;


public class LoveTest2Activity extends AppCompatActivity implements View.OnClickListener {

    private Typeface kai_ti_typeface, typeface;

    private TextView title_tv, question_tv, answerA_tv, answerB_tv, answerC_tv, answerD_tv;

    private String question, answerA, answerB, answerC, answerD,
            dialog_titleA, dialog_titleB, dialog_titleC, dialog_titleD;

    private AlertDialog dialog = null;
    private TextView dialog_answer_title, dialog_answer_analyze;
    private HeartView dialog_heart_view = null;
    private boolean isStart = false;

    private HeartClickLayout layout = null;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love_test2);

        // 加载字体
        AssetManager manager = getAssets();
        typeface = Typeface.createFromAsset(manager, "fonts/huawenxingkai.ttf");
        kai_ti_typeface = Typeface.createFromAsset(manager, "fonts/kaiti.ttf");

        InitString();
        InitControl();

    }

    private void InitControl() {

        gestureDetector = new GestureDetector(this, new onGestureListener());
        layout = findViewById(R.id.love_test2_heart_click_layout);

        findViewById(R.id.love_test2_answerA_layout).setOnClickListener(this);
        findViewById(R.id.love_test2_answerB_layout).setOnClickListener(this);
        findViewById(R.id.love_test2_answerC_layout).setOnClickListener(this);
        findViewById(R.id.love_test2_answerD_layout).setOnClickListener(this);
        findViewById(R.id.love_test2_last_question_iv).setOnClickListener(this);
        findViewById(R.id.love_test2_next_question_iv).setOnClickListener(this);

        title_tv = findViewById(R.id.love_test2_title_tv);
        question_tv = findViewById(R.id.love_test2_question_tv);
        answerA_tv = findViewById(R.id.love_test2_answerA_tv);
        answerB_tv = findViewById(R.id.love_test2_answerB_tv);
        answerC_tv = findViewById(R.id.love_test2_answerC_tv);
        answerD_tv = findViewById(R.id.love_test2_answerD_tv);

        title_tv.setTypeface(typeface);
        question_tv.setTypeface(kai_ti_typeface);
        answerA_tv.setTypeface(kai_ti_typeface);
        answerB_tv.setTypeface(kai_ti_typeface);
        answerC_tv.setTypeface(kai_ti_typeface);
        answerD_tv.setTypeface(kai_ti_typeface);

        question_tv.setText(question);

    }

    private void InitString() {

        question = "四款同价格的泰迪熊，上面刺着不同的字样，你只能买一只，你会买哪一只？";
        dialog_titleA = "选「友情」的你会希望跟他一起成长让对方才有变聪明的幸福。幸福指数55%:";
        answerA = "你觉得两个人在一起除了甜言蜜语之外，还会把情人当成自己最好的朋友，希望双方一起" +
                "去学习一些课程， 例如心灵课程或绘画等等,你觉得这种交往过程会让双方更甜蜜而且可以一起成长。";

        dialog_titleB = "选「祝福」的你，太包容对方，以放纵的方式让对方享受自由的幸福。幸福指数80%:";
        answerB = "你很小孩子气，当你爱上一个人时会自动把眼睛弄瞎， 会对对方非常包容和放纵，只要" +
                "对方快乐就好，即使自己牺牲也无所谓。";

        dialog_titleC = "选「挚爱」的你，把对方伺候的无微不至，让对方以为在幸福天堂里。幸福指数99%:";
        answerC = "你只要恋爱对象是自己非常爱的人时就会无怨无悔的付出，再加上你很喜欢照顾对方," +
                "会由内到外把对方打理的很好，不但自己有一种成就感，对方也会有一种宛若天堂的感觉，觉得跟他谈恋爱实在" +
                "太幸福了。";

        dialog_titleD = "选「谢谢」的你，太孩子气偶尔还会闹脾气让对方觉得不太幸福喔。幸福指数20%:";
        answerD = "你会让跟你谈恋爱的对象觉得很头痛，一点都没有幸福的感觉，永远像是要哄一个小孩子" +
                "或泼猴， 永远都搞不定你。";


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.love_test2_answerA_layout:
                ShowResult(dialog_titleA, answerA, false);
                break;
            case R.id.love_test2_answerB_layout:
                ShowResult(dialog_titleB, answerB, false);
                break;
            case R.id.love_test2_answerC_layout:
                ShowResult(dialog_titleC, answerC, true);
                Util.loveTest[1] = true;
                break;
            case R.id.love_test2_answerD_layout:
                ShowResult(dialog_titleD, answerD, false);
                break;
            case R.id.love_test2_last_question_iv:
                startActivity(new Intent(LoveTest2Activity.this, LoveTest1Activity.class));
                // 两个参数：第一个进入效果，第二个退出效果
                overridePendingTransition(R.anim.back_in_left,
                        R.anim.back_out_right);
                finish();
                break;
            case R.id.love_test2_next_question_iv:
                startActivity(new Intent(LoveTest2Activity.this, LoveTest3Activity.class));
                finish();
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
