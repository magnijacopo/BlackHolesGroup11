package it.polimi.group11;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

public class CreditsActivity extends MainActivity {

    TextView textViewCredits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        // Setting fonts.
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/SignPainter-HouseScript.ttf");
        textViewCredits = (TextView) findViewById(R.id.textViewCredits);
        textViewCredits.setText(Html.fromHtml("<html>\n" +
                "                <h4>Developers:</h4>\n" +
                "        <p>Alessandro Fantini</p>\n" +
                "        <p>Alessandro Piola</p>\n" +
                "        <p>Jacopo Magni</p>\n" +
                "        <p>Davide Rezzonico</p>\n" +
                "        </html>"));
        textViewCredits.setTextSize(25);
        textViewCredits.setTypeface(myTypeface);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // Methods to go to the other activity.
    public void goToMainActivity(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
