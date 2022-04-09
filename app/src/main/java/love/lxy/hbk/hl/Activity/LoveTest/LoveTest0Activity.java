package love.lxy.hbk.hl.Activity.LoveTest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import love.lxy.hbk.hl.R;
import love.lxy.hbk.hl.Util.Util;

public class LoveTest0Activity extends AppCompatActivity implements View.OnClickListener {

    private String question1, question2, question3, question4, question5;
    private String answer1_1, answer1_2, answer2_1, answer2_2, answer3_1, answer3_2,
            answer4_1, answer4_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love_test0);

        InitString();
        InitControl();
    }

    private void InitControl() {

        findViewById(R.id.love_test0_last_question_iv).setOnClickListener(this);
        findViewById(R.id.love_test0_next_question_iv).setOnClickListener(this);

    }


    private void InitString() {

        question1 = "去男朋友家的途中，突然有人要上厕所，你知道附近有两处地方有厕所，其中一个厕所很近，但很脏；" +
                "另一处则相对远了点，但是很干净，你会去哪个厕所？";
        answer1_1 = "A你很容易喜欢一个人，很快坠入爱河";
        answer1_2 = "B你对每一段爱情都会考虑很久，不会轻易喜欢一个人";

        question2 = "去完厕所之后，你看见街边有两个小贩，一个是烤新疆羊肉串，一个是炸羊肉串，你自己喜欢吃烤羊肉串，" +
                "而你知道你男朋友喜欢吃炸羊肉串，而现在只让你选一样带回家一起吃，你会选哪一样？";
        answer2_1 = "A烤羊肉串你想对方对爱付出多一点";
        answer2_2 = "B炸羊肉串表示你想为对方付出多一点";
        

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.love_test3_answerA_layout:
                break;
            case R.id.love_test3_answerB_layout:
                break;
            case R.id.love_test3_answerC_layout:
                break;
            case R.id.love_test3_answerD_layout:
                Util.loveTest[2] = true;
                break;
            case R.id.love_test2_last_question_iv:
                startActivity(new Intent(LoveTest0Activity.this, LoveTest1Activity.class));
                finish();
                break;
            case R.id.love_test2_next_question_iv:
                startActivity(new Intent(LoveTest0Activity.this, LoveTest3Activity.class));
                finish();
                break;


        }
    }
}
