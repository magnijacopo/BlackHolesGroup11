package it.polimi.group11;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

public class OptionsActivity extends AppCompatActivity {

    public static boolean backgroundMusicCheck = true;
    CheckBox backgroundMusicCheckbox;

    public static boolean fxSoundsCheck;
    CheckBox fxSoundsCheckbox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        backgroundMusicCheckbox = (CheckBox) findViewById(R.id.background_music_checkbox);
        backgroundMusicCheck = load();

        setCheck();
        checkedChanged();

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
        return sharedPreferences.getBoolean("check", true);
    }

    public void checkedChanged(){
        fxSoundsCheckbox = (CheckBox) findViewById(R.id.fxSoundsCheckbox);
        fxSoundsCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (fxSoundsCheckbox.isChecked()) {
                    fxSoundsCheck = false;
                    Toast.makeText(getApplicationContext(), "FX Disabled", Toast.LENGTH_SHORT).show();
                } else {
                    fxSoundsCheck = true;
                    Toast.makeText(getApplicationContext(), "FX Enabled", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setCheck(){
        fxSoundsCheckbox = (CheckBox) findViewById(R.id.fxSoundsCheckbox);
        if(!fxSoundsCheck){
            fxSoundsCheckbox.setChecked(true);
        } else {
            fxSoundsCheckbox.setChecked(false);
        }
    }

    public void goToMainActivity(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
