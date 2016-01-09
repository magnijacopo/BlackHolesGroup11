package it.polimi.group11;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import it.polimi.group11.service.BackgroundMusicService;

public class MainActivity extends AppCompatActivity {
    BackgroundMusicService backgroundMusicService;
    boolean serviceBound = false;

    private boolean lastBackgroundMusicCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lastBackgroundMusicCheck = OptionsActivity.backgroundMusicCheck;
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(lastBackgroundMusicCheck) {
            Intent intent = new Intent(this, BackgroundMusicService.class);
            bindService(intent, connection, Context.BIND_AUTO_CREATE);
            startService(intent);
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(lastBackgroundMusicCheck) {
            if (serviceBound) {
                unbindService(connection);
                serviceBound = false;
            }
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(lastBackgroundMusicCheck){
            stopService(new Intent(this, BackgroundMusicService.class));
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        if(lastBackgroundMusicCheck) {
            if (serviceBound) {
                unbindService(connection);
                serviceBound = false;
            }
        }
    }

    private ServiceConnection connection = new ServiceConnection(){
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BackgroundMusicService.ServiceBinder binder = (BackgroundMusicService.ServiceBinder) service;
            backgroundMusicService = binder.getService();
            serviceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
        }
    };

    @Override
    protected void onResume(){
        super.onResume();
        /*if(lastBackgroundMusicCheck) {
            if (!backgroundMusic.isPlaying()) {
                backgroundMusic.seekTo(length);
                backgroundMusic.start();
            }
        }*/
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
