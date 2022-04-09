package love.lxy.hbk.hl.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import love.lxy.hbk.hl.R;
import love.lxy.hbk.hl.Util.Util;

public class MusicService extends Service {

    private MediaPlayer mediaPlayer = null;

    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (!Util.background_music) {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            return super.onStartCommand(intent, flags, startId);
        }

        if (mediaPlayer == null) {

            // R.raw.mmp是资源文件，MP3格式的
            mediaPlayer = MediaPlayer.create(this, R.raw.milk_bread);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();

            // 设置播放的音乐的音量大小
            AudioManager audioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            if (audioMgr != null) {
//                audioMgr.setStreamVolume(AudioManager.STREAM_MUSIC, 3,
//                        AudioManager.FLAG_PLAY_SOUND);
                Log.i("background music",
                        "onStartCommand: " + audioMgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            }

        } else {
            if (!mediaPlayer.isPlaying())
                mediaPlayer.start();
        }
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }


}
