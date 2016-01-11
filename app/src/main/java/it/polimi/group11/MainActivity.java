package it.polimi.group11;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    Button play_button;
    Button credits_button;
    Button profiles_button;
    Button leaderboard_button;
    Button options_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/SignPainter-HouseScript.ttf");

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
