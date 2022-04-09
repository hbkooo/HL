package love.lxy.hbk.hl.Beans;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import love.lxy.hbk.hl.Activity.BirthdayActivity;

/**
 * Created by 19216 on 2019/8/2.
 */

public class AlarmBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("com.example.alarmandnotice_android.DCAT".equals(intent.getAction())){
            Log.i("hbklxy", "onReceive: ");
            Intent intent1=new Intent(context,BirthdayActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
        }
    }
}
