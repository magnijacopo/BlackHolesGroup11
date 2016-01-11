package it.polimi.group11;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
