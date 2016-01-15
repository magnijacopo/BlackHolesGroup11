package it.polimi.group11;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import it.polimi.group11.helper.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    // Buttons.
    Button play_button;
    Button credits_button;
    Button profiles_button;
    Button leaderboard_button;
    Button options_button;
    Button instructions_button;

    // Variable for the sounds.
    private SoundPool sounds;
    private int sound1;
    private boolean fxOn;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Import of the font.
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/SignPainter-HouseScript.ttf");

        //Setting the fonts on the buttons.
        play_button = (Button) findViewById(R.id.play_button);
        play_button.setTypeface(myTypeface);
        credits_button = (Button) findViewById(R.id.credits_button);
        credits_button.setTypeface(myTypeface);
        profiles_button = (Button) findViewById(R.id.profiles_button);
        profiles_button.setTypeface(myTypeface);
        leaderboard_button = (Button) findViewById(R.id.leaderboard_button);
        leaderboard_button.setTypeface(myTypeface);
        options_button = (Button) findViewById(R.id.options_button);
        options_button.setTypeface(myTypeface);
        instructions_button = (Button) findViewById(R.id.instructions_button);
        instructions_button.setTypeface(myTypeface);
        //Declaration variable for sound effects
        sounds = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        sound1 = sounds.load(getApplicationContext(), R.raw.button_click_fx, 1);
        fxOn = OptionsActivity.fxSoundsCheck;

        /*
        db = new DatabaseHelper(getApplicationContext());
        db.insertProfile("Guest1");
        db.insertProfile("Guest2");
        */

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // Methods for moving to other Activities.
    public void goToCredits(View view){
        playSoundButton();
        Intent intent = new Intent(this, CreditsActivity.class);
        startActivity(intent);
    }

    public void goToChooseMatchType(View view){
        playSoundButton();
        Intent intent = new Intent(this, SelectPlayersActivity.class);
        startActivity(intent);
    }

    public void goToOptions(View view){
        playSoundButton();
        Intent intent = new Intent(this, OptionsActivity.class);
        startActivity(intent);
    }

    public void goToViewProfileList(View view){
        playSoundButton();
        Intent intent = new Intent(this, ViewProfileListActivity.class);
        startActivity(intent);
    }

    public void goToLeaderboard(View view) {
        playSoundButton();
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
    }

    public void goToInstructions(View view){
        playSoundButton();
        Intent intent = new Intent(this, InstructionsActivity.class);
        startActivity(intent);
    }

    public void playSoundButton(){
        if(fxOn) {
            sounds.play(sound1, 1.0f, 1.0f, 0, 0, 1.5f);
        }
    }
}
