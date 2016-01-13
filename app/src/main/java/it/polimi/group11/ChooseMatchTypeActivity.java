package it.polimi.group11;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ChooseMatchTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_match_type);

    }


    public void goToMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void goToSelectPlayers(View view) {
        Intent intent = new Intent(this, SelectPlayersActivity.class);
        startActivity(intent);
    }
    public void goToMakeYourBars(View view) {
        Intent intent = new Intent(this, SetYourBarsActivity.class);
        startActivity(intent);
    }
}
