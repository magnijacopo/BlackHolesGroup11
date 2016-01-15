package it.polimi.group11;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import it.polimi.group11.helper.Guest;

public class ChoosePlayerTypeActivity extends AppCompatActivity {

    Button newProfileButton;
    Button existingProfileButton;
    Button artificialIntelligenceButton;

    public final String KEY_PASS_GUEST = "KEY_PASS_GUEST";
    Guest guest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_player_type);

        // Processing the intent from the previous actvity.
        Intent i = getIntent();
        guest = i.getParcelableExtra("KEY_EXTRA_GUEST");

        // Setting the font to buttons.
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/SignPainter-HouseScript.ttf");
        newProfileButton = (Button) findViewById(R.id.new_profile_button);
        newProfileButton.setTypeface(myTypeface);
        existingProfileButton = (Button) findViewById(R.id.existing_profile_button);
        existingProfileButton.setTypeface(myTypeface);
        artificialIntelligenceButton = (Button) findViewById(R.id.artificial_intelligence_button);
        artificialIntelligenceButton.setTypeface(myTypeface);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, SelectPlayersActivity.class);
        startActivity(intent);
    }

    // Methods to navigate to other activities.

    public void goToAddProfile(View view){
        Intent intent = new Intent(this, AddProfileActivity.class);
        intent.putExtra(KEY_PASS_GUEST, guest);
        startActivity(intent);
    }

    public void goToViewProfilesSelection(View view) {
        Intent intent = new Intent(this, ViewProfilesSelectionActivity.class);
        intent.putExtra(KEY_PASS_GUEST, guest);
        startActivity(intent);
    }

    public void goToArtificialIntelligence(View view) {
        Toast.makeText(getApplicationContext(), "COMING SOON", Toast.LENGTH_SHORT).show();
    }

    public void goToSelectPlayers(View view) {
        Intent intent = new Intent(this, SelectPlayersActivity.class);
        startActivity(intent);
    }

}
