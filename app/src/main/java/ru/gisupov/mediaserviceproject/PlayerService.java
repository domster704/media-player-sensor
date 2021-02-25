package ru.gisupov.mediaserviceproject;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

public class PlayerService extends Service {
    private static MediaPlayer mPlayer;

    public PlayerService() {}
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Служба создана",
                Toast.LENGTH_SHORT).show();
        mPlayer = MediaPlayer.create(this, R.raw.music);
        mPlayer.setLooping(false);
    }

    public static void pauseMplayer() {
        mPlayer.pause();
    }

    public static void contMplayer() {
        try {
            mPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mPlayer.start();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Служба завершена",
                Toast.LENGTH_SHORT).show();
        mPlayer.stop();
    }
}