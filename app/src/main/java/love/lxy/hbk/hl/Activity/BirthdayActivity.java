package love.lxy.hbk.hl.Activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import love.lxy.hbk.hl.MyView.HeartView;
import love.lxy.hbk.hl.R;

public class BirthdayActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    private Notification notification;
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);

        ((HeartView) findViewById(R.id.birthday_heart_view)).start(BirthdayActivity.this);

//        mediaPlayer = MediaPlayer.create(this, R.raw.f);
//        mediaPlayer.start();
        //实例化通知管理器s
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //实例化通知
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
//        builder.setDefaults(NotificationCompat.DEFAULT_ALL);//默认的通知手机声音
//        builder.setContentTitle("闹钟响了!");
//        builder.setSmallIcon(android.R.drawable.ic_lock_idle_alarm);
//        builder.setContentText("记得吃早餐噢!");
//        Intent intent = new Intent(BirthdayActivity.this, LoveTimeActivity.class);
//        intent.setAction("com.example.alarmandnotice_android.DCAT");
//        PendingIntent pendingIntent = PendingIntent.getActivity(BirthdayActivity.this,
//                0x105, intent, 0);
//        builder.setContentIntent(pendingIntent);
//
//        Notification notification = builder.build();
//        //发送通知
//        notificationManager.notify(0x104, notification);

        showNotify();
    }

    public void stop(View view) {
//        mediaPlayer.stop();
        finish();
    }

    //弹出一条通知
    private void showNotify() {
        Intent intent = new Intent(BirthdayActivity.this, LoveTimeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("train", "train");
        intent.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getActivity(BirthdayActivity.this, 0, intent, 0);
        notification = new NotificationCompat.Builder(BirthdayActivity.this)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentTitle("新路线")
                .setContentText("---content" )
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.heart)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.heart_broken))
                .setVibrate(new long[]{0, 1000, 1000, 1000})
                .setLights(Color.GREEN, 1000, 1000)
                .setAutoCancel(true)
                .build();
        notificationManager.notify(1, notification);
    }

}
