package it.polimi.group11;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;

public class OptionsActivity extends AppCompatActivity {
    public static boolean backgroundMusicCheck = false;
    CheckBox backgroundMusicCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        backgroundMusicCheckbox = (CheckBox) findViewById(R.id.background_music_checkbox);
    }

    @Override
    public void onPause(){
        super.onPause();
        save(backgroundMusicCheckbox.isChecked());
    }

    @Override
    public void onResume(){
        super.onResume();
        backgroundMusicCheckbox.setChecked(load());
    }

    @Override
    public void onRestart() {
        super.onRestart();
        backgroundMusicCheckbox.setChecked(load());
    }

    public void backgroundMusic(View view) {
        backgroundMusicCheck = ((CheckBox) view).isChecked();
    }

    private void save(final boolean isChecked) {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("check", isChecked);
        editor.apply();
    }

    private boolean load() {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("check", false);
    }

}
