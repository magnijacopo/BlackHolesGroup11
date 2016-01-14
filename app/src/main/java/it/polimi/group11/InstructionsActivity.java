package it.polimi.group11;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

public class InstructionsActivity extends AppCompatActivity {

    TextView textViewInstructions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        textViewInstructions = (TextView) findViewById(R.id.textViewInstructions);
        //Import of the font.
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/SignPainter-HouseScript.ttf");

        //Setting the fonts on the buttons.
        textViewInstructions.setTypeface(myTypeface);
        textViewInstructions.setText(Html.fromHtml("<html>\n" +
                "<body>\n" +
                "<html>\n" +
                "<body>\n" +
                "<h2>How To Play:</h2>\n" +
                "<h4> Beads setting: </h4>\n" +
                "<p> In his turn a player places his bead, this turn are repeated five times, one for each bead of the player. </p>\n" +
                "<h4> Game: </h4>\n" +
                "<p> The goal is to drop all the opponents beads into the black hole. To do so the players can move the bars. Each bar can be moved\n" +
                "    only one position, for example if the bar is completely to the right can be moved only to the center and not completely to the left.\n" +
                "    The players can not move the bar that are previously moved by the opponents in the same turn. If there are only two players, one player can\n" +
                "    not move the same bar for more than two consecutive times. </p>\n" +
                "<h4> Win: </h4>\n" +
                "<p> The game ends when only one player has beads on the board. </p>\n" +
                "</body>\n" +
                "</html>"));
    }
}
