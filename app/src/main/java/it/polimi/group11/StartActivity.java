package it.polimi.group11;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;

public class StartActivity extends Activity implements MediaPlayer.OnErrorListener {
    MediaPlayer backgroundMusic;
    private int length = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        toggleHideyBar();
        setContentView(R.layout.activity_start);

        backgroundMusic = MediaPlayer.create(this, R.raw.somuchlove);
        backgroundMusic.setOnErrorListener(this);
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(60, 60);
        backgroundMusic.start();
    }

    @Override
    protected void onPause(){
        super.onPause();
        backgroundMusic.pause();
        length = backgroundMusic.getCurrentPosition();
    }

   @Override
    public boolean onError(final MediaPlayer mp, final int what, final int extra) {
        Log.e(getPackageName(), String.format("Error(%s%s)", what, extra));
        mp.reset();
        return true;
    }

    @Override
    public void onDestroy ()
    {
        super.onDestroy();
        if(backgroundMusic != null)
        {
            try{
                backgroundMusic.stop();
                backgroundMusic.release();
            }finally {
                backgroundMusic = null;
            }
        }
    }

    protected void onResume(){
        super.onResume();
        if(!backgroundMusic.isPlaying()){
            backgroundMusic.seekTo(length);
            backgroundMusic.start();
        }
    }

    public void restart(){

    }

    public void toggleHideyBar() {
        int newUiOptions = getWindow().getDecorView().getSystemUiVisibility();

        // Navigation bar hiding:  Backwards compatible to ICS.
        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        // Immersive mode: Backward compatible to KitKat.
        // Note that this flag doesn't do anything by itself, it only augments the behavior
        // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
        // all three flags are being toggled together.
        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }

        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }

}
