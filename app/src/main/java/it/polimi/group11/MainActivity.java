package it.polimi.group11;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnErrorListener {
    MediaPlayer backgroundMusic;
    int length = 0;
    private boolean lastBackgroundMusicCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lastBackgroundMusicCheck = OptionsActivity.backgroundMusicCheck;
        if(lastBackgroundMusicCheck){
            backgroundMusic = MediaPlayer.create(this, R.raw.somuchlove);
            backgroundMusic.setOnErrorListener(this);
            backgroundMusic.setLooping(true);
            backgroundMusic.setVolume(60, 60);
            backgroundMusic.start();
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        if(lastBackgroundMusicCheck) {
            backgroundMusic.pause();
            length = backgroundMusic.getCurrentPosition();
        }
        OptionsActivity.backgroundMusicCheck = true;
    }

    @Override
    public boolean onError(final MediaPlayer backgroundMusic, final int what, final int extra){
        Log.e(getPackageName(), String.format("Error(%s%s)", what, extra));
        backgroundMusic.reset();
        return true;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(backgroundMusic != null){
            try{
                backgroundMusic.stop();
                backgroundMusic.release();
            }finally {
                backgroundMusic = null;
            }
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(lastBackgroundMusicCheck) {
            if (!backgroundMusic.isPlaying()) {
                backgroundMusic.seekTo(length);
                backgroundMusic.start();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void goToCredits(View view){
        Intent intent = new Intent(this, CreditsActivity.class);
        startActivity(intent);
    }

    public void goToSelectPlayers(View view){
        Intent intent = new Intent(this, SelectPlayersActivity.class);
        startActivity(intent);
    }

    public void goToOptions(View view){
        Intent intent = new Intent(this, OptionsActivity.class);
        startActivity(intent);
    }

    public void goToViewProfileList(View view){
        Intent intent = new Intent(this, ViewProfileListActivity.class);
        startActivity(intent);
    }

    public void goToLeaderboard(View view) {
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
    }
}
