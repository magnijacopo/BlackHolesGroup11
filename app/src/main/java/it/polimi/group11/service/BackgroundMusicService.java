package it.polimi.group11.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;

import it.polimi.group11.R;

public class BackgroundMusicService extends Service implements MediaPlayer.OnErrorListener{
    private final IBinder binder = new ServiceBinder();
    MediaPlayer backgroundMusic;
    int length = 0;

    public class ServiceBinder extends Binder {
        public BackgroundMusicService getService(){
            return BackgroundMusicService.this;
        }
    }

    @Override
    public IBinder onBind(Intent arg0){return binder;}

    @Override
    public void onCreate(){
        super.onCreate();
        backgroundMusic = MediaPlayer.create(this, R.raw.somuchlove);
        backgroundMusic.setOnErrorListener(this);
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(60, 60);
    }

    @Override
    public int onStartCommand (Intent intent, int flags, int startId){
        backgroundMusic.start();
        return START_STICKY;
    }

    public void pauseMusic(){
        if(backgroundMusic.isPlaying()){
            backgroundMusic.pause();
            length=backgroundMusic.getCurrentPosition();

        }
    }

    public void resumeMusic(){
        if(!backgroundMusic.isPlaying()){
            backgroundMusic.seekTo(length);
            backgroundMusic.start();
        }
    }

    public void stopMusic(){
        backgroundMusic.stop();
        backgroundMusic.release();
        backgroundMusic = null;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(backgroundMusic != null){
            stopMusic();
        }
    }

    @Override
    public boolean onError(final MediaPlayer backgroundMusic, final int what, final int extra){
        Log.e(getPackageName(), String.format("Error(%s%s)", what, extra));
        backgroundMusic.reset();
        return true;
    }
}
