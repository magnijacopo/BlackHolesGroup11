package it.polimi.group11;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ChooseMatchTypeActivity extends AppCompatActivity {

    // Buttons
    Button buttonQuickGame;
    Button buttonCustomizedGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_match_type);

        // Import of the font. Setting the font to the button.
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/SignPainter-HouseScript.ttf");
        buttonQuickGame = (Button) findViewById(R.id.button_quick_game);
        buttonQuickGame.setTypeface(myTypeface);
        buttonCustomizedGame = (Button) findViewById(R.id.button_customized_game);
        buttonCustomizedGame.setTypeface(myTypeface);
    }

    // Method to navigate to other activities.

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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
