package it.polimi.group11;

import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import it.polimi.group11.helper.DatabaseHelper;

public class OptionsActivity extends AppCompatActivity implements ConfirmDeletionDialog.DialogListener {


    public static boolean backgroundMusicCheck = true;
    CheckBox backgroundMusicCheckbox;

    public static boolean fxSoundsCheck = true;
    CheckBox fxSoundsCheckbox;

    Button buttonDeleteData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/SignPainter-HouseScript.ttf");

        backgroundMusicCheckbox = (CheckBox) findViewById(R.id.background_music_checkbox);
        backgroundMusicCheckbox.setTypeface(myTypeface);
        fxSoundsCheckbox = (CheckBox) findViewById(R.id.fx_sounds_checkbox);
        fxSoundsCheckbox.setTypeface(myTypeface);
        buttonDeleteData = (Button) findViewById(R.id.button_delete_data);
        buttonDeleteData.setTypeface(myTypeface);

        backgroundMusicCheckbox = (CheckBox) findViewById(R.id.background_music_checkbox);

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
        fxSoundsCheckbox = (CheckBox) findViewById(R.id.fx_sounds_checkbox);
        fxSoundsCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (fxSoundsCheckbox.isChecked()) {
                    fxSoundsCheck = true;
                } else {
                    fxSoundsCheck = false;
                }
            }
        });
    }

    public void setCheck(){
        fxSoundsCheckbox = (CheckBox) findViewById(R.id.fx_sounds_checkbox);
        if(!fxSoundsCheck){
            fxSoundsCheckbox.setChecked(true);
        } else {
            fxSoundsCheckbox.setChecked(false);
        }
    }

    public void deleteDatabase(){
        getApplicationContext().deleteDatabase(DatabaseHelper.DATABASE_NAME);
        Toast.makeText(getApplicationContext(), "All data deleted", Toast.LENGTH_SHORT).show();
    }

    public void goToMainActivity(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void showAlert(View view){
        DialogFragment dialog = new ConfirmDeletionDialog();
        Bundle args = new Bundle();
        args.putString("title", "Alert");
        args.putString("message", "Delete everything?");
        dialog.setArguments(args);
        dialog.show(getFragmentManager(), "tag");
    }

    public void onOkClicked(){
        deleteDatabase();
    }
}
