package love.lxy.hbk.hl.Activity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import love.lxy.hbk.hl.MyView.HeartClickLayout;
import love.lxy.hbk.hl.MyView.HeartView;
import love.lxy.hbk.hl.Beans.LoveProcess;
import love.lxy.hbk.hl.R;
import love.lxy.hbk.hl.Service.MusicService;
import love.lxy.hbk.hl.Util.DataProcessing;
import love.lxy.hbk.hl.Util.LoveProcessAdapter;
import love.lxy.hbk.hl.Util.ProcessDataHelper;
import love.lxy.hbk.hl.Util.Util;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

public class LoveProcessActivity extends AppCompatActivity implements View.OnClickListener {

    private int REQUEST_ADD = 1;
    public static int REQUEST_CHANGE = 2;

    public static SQLiteDatabase processDatabase = null;

    private Typeface typeface, kai_ti_typeface;

    private HeartView heartView = null;

    private TextView title_tv;
    private String title_string, and_string = "&";

    // recycleView 变量
    private RecyclerView process_recycleView;
    private LoveProcessAdapter processAdapter;
    private List<LoveProcess> loveProcessList = new ArrayList<>();

    private String love_speech;

    private FloatingActionButton add_process_fab;

    // 弹出对话框布局信息
    private HeartView dialog_love_forever_heart_view = null;
    private View dialog_view = null;
    private AlertDialog dialog = null;

    private HeartClickLayout layout = null;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love_process);

        // 加载字体
        AssetManager manager = getAssets();
        typeface = Typeface.createFromAsset(manager, "fonts/huawenxingkai.ttf");
        kai_ti_typeface = Typeface.createFromAsset(manager, "fonts/kaiti.ttf");

        InitString();
        InitControl();     // 初始化控件

        Log.i("hbklxy", "onCreate: " + loveProcessList.size());

        processDatabase = new ProcessDataHelper(this,
                "process", null, 1).getWritableDatabase();

    }

    // 初始化情话
    private void InitString() {

        title_string = "❤我们的相识，是爱情的开始❤\n" +
                "❤我们的爱情，是幸福的开始❤\n" +
                "❤我们的幸福，是平平淡淡的陪伴❤";

        love_speech = "亲爱哒，我真的离不开你，离开你的这一段时间，我发现我自己每天都是浑浑噩噩的，" +
                "自己的心就像死了一样。我的心里只有你，你就是我的全部。未来，咱们还有好多事要一块做，逛街、旅游、吃美食、" +
                "看演唱会、在家一块做饭吃饭等等，想想都觉得特别美好，因为跟你在一起的每一刻感觉都是美好，以后不管发生什么事，" +
                "我都会跟你站在一块，以后的生命中，我要陪你一直度过，我不想失去你，我要陪着你，一直陪着你。因为有你，我的世界" +
                "才会变得美丽，因为有你，我的生活才会有意义，因为有你，我所有的一切才会变得甜蜜。刘香玉，我爱你，我不想失去你，" +
                "咱们重归于好好吗？";

        if (processDatabase == null) {
            processDatabase = new ProcessDataHelper(this,
                    "process", null, 1).getWritableDatabase();
        }

        updateProcess();

    }

    // 重新获取历程信息
    private void updateProcess() {

        loveProcessList.clear();

        loveProcessList.addAll(Util.getProcessData());    // 初始化爱情历程数据
        loveProcessList.addAll(DataProcessing.LoadAllProcess(processDatabase));
        loveProcessList.add(Util.getBlankProcess()); // 获取空的历程
        Log.i("", "updateProcess: " + loveProcessList.size());
    }

    // 初始化控件
    private void InitControl() {


        gestureDetector = new GestureDetector(this, new onGestureListener());
        layout = findViewById(R.id.love_process_heart_click_layout);

        if (Util.background_heart) {
            heartView = findViewById(R.id.love_process_heart_view);
            heartView.start(LoveProcessActivity.this);
        }

        title_tv = findViewById(R.id.love_process_title_tv);
        title_tv.setTypeface(typeface);
        title_tv.setText(title_string);
        ((TextView) findViewById(R.id.love_process_time_tv)).setTypeface(typeface);
        ((TextView) findViewById(R.id.love_process_and_tv)).setText(and_string);
        ((TextView) findViewById(R.id.love_process_tv1)).setTypeface(typeface);

        add_process_fab = findViewById(R.id.love_process_add_fab);
        add_process_fab.setOnClickListener(this);

        Button love_you_btn = findViewById(R.id.love_process_love_you_btn);
        love_you_btn.setTypeface(kai_ti_typeface);
        love_you_btn.setOnClickListener(this);


        process_recycleView = findViewById(R.id.love_process_recycle_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        process_recycleView.setLayoutManager(manager);
        processAdapter = new LoveProcessAdapter(loveProcessList, this);
        processAdapter.setTypeface(kai_ti_typeface);
        process_recycleView.setAdapter(processAdapter);
        process_recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == SCROLL_STATE_IDLE) {
                    processAdapter.setScrolling(false);
                    processAdapter.notifyDataSetChanged();
                } else {
                    processAdapter.setScrolling(true);
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.love_process_love_you_btn:
//                Toast.makeText(this, "海誓山盟", Toast.LENGTH_LONG).show();
                loveYouForever();
                break;
            case R.id.love_process_add_fab:
                startActivityForResult(new Intent(LoveProcessActivity.this,
                        AddLoveProcessActivity.class), REQUEST_ADD);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_ADD) {
            if (resultCode == AddLoveProcessActivity.ADD_SUCCESS) {
                boolean isAdd = data.getBooleanExtra("isAdd", false);
                Toast.makeText(this, "add process" + isAdd, Toast.LENGTH_SHORT).show();
                if (isAdd) {
                    updateProcess();
                    processAdapter.notifyDataSetChanged();
                }
            }
        } else if (requestCode == REQUEST_CHANGE) {
            if (resultCode == LoveProcessItemActivity.CHANGE) {
                if (data == null)
                    return;
                boolean ischange = data.getBooleanExtra("ischange", false);
                if (ischange) {
                    updateProcess();
                    processAdapter.notifyDataSetChanged();
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    private void loveYouForever() {

        if (dialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoveProcessActivity.this);

            dialog_view = getLayoutInflater().inflate(R.layout.dialog_love_process_forever, null, false);
            builder.setView(dialog_view);
            builder.setCancelable(false);
            dialog = builder.show();

            ((TextView) dialog_view.findViewById(R.id.dialog_love_process_forever_tv)).setTypeface(typeface);
            ((TextView) dialog_view.findViewById(R.id.dialog_love_process_forever_tv)).setText(Util.love_speech2);

            if (Util.background_heart) {
                dialog_love_forever_heart_view = dialog_view.findViewById(R.id.dialog_love_process_forever_heart_view);
                dialog_love_forever_heart_view.start(LoveProcessActivity.this);
            }

            Button confirm = dialog_view.findViewById(R.id.dialog_confirm_love_process_forever_btn);
            confirm.setTypeface(typeface);
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        } else {
            dialog.show();
        }

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        if (heartView != null) {
            heartView.updateHearts();
        }
        if (dialog_love_forever_heart_view != null)
            dialog_love_forever_heart_view.updateHearts();
        Intent intent = new Intent(LoveProcessActivity.this, MusicService.class);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (heartView != null) {
            heartView.cancelAnimator();
        }
        if (dialog_love_forever_heart_view != null)
            dialog_love_forever_heart_view.cancelAnimator();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (heartView != null) {
            heartView.cancelAnimator();
        }
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
