package it.polimi.group11;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

public class InstructionsActivity extends AppCompatActivity {

    TextView textViewInstructions;

    String eng = "textViewInstructions.setText(Html.fromHtml(\"<html>\\n\"" +
            "                \"<body>\\n\"" +
            "                \"<html>\\n\"" +
            "                \"<body>\\n\"" +
            "                \"<h2>How To Play:</h2>\\n\"" +
            "                \"<h4> Beads setting: </h4>\\n\"" +
            "                \"<p> In his turn a player places his bead, this turn are repeated five times, one for each bead of the player. </p>\\n\"" +
            "                \"<h4> Game: </h4>\\n\" " +
            "                \"<p> The goal is to drop all the opponents beads into the black hole. To do so the players can move the bars. Each bar can be moved\\n\"" +
            "                \"    only one position, for example if the bar is completely to the right can be moved only to the center and not completely to the left.\\n\"" +
            "                \"    The players can not move the bar that are previously moved by the opponents in the same turn. If there are only two players, one player can\\n\"" +
            "                \"    not move the same bar for more than two consecutive times. </p>\\n\"" +
            "                \"<h4> Win: </h4>\\n\" " +
            "                \"<p> The game ends when only one player has beads on the board. </p>\\n\"" +
            "                \"</body>\\n\" " +
            "                \"</html>\" ";

    String ita = "\"<html>\\n\" " +
            "                <body>\\n\" +\n" +
            "                \"<html>\\n\" " +
            "                \"<body>\\n\" " +
            "                \"<h2>Come giocare:</h2>\\n\" " +
            "                \"<h4> Posizionare i bead: </h4>\\n\" " +
            "                \"<p> In questo turno ogni giocatore piazza i propri bead, l'azione si ripete cinque volte perchè il player piazza un solo bead per volta.</p>\\n\" " +
            "                \"<h4> Gioco: </h4>\\n\" " +
            "                \"<p> L'obiettio del gioco è far cadere tutti i bead avversari nei 'Black Holes'. Per farli cadere i giocatori possono muovere le barre, ogni barra può essere mossa solo di una posizione alla volta.\\n\" +\n" +
            "                \"    Se per esempio la barra è completamente a destra non potrà essere spostata completamente a sinistra con una sola mossa.\\n\"" +
            "                \"    I giocatori non possono muovere le barre già mosse da giocatori avversari durante lo stesso giro. Nel caso ci siano due giocatori c'è una regola aggiuntiva,\\n\"" +
            "                \"    un giocatore non può muovere la stessa barra per più di due turni consecutivi. </p>\\n\"" +
            "                \"<h4> Vittoria: </h4>\\n\" " +
            "                \"<p> Il vincitore è il giocatore che riesce a far cadere tutti i bead avversari facendo sopravvivere i suoi. </p>\\n\"" +
            "                \"</body>\\n\" " +
            "                \"</html>\" ";

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

    public void changeLanguageIta(View view){
        textViewInstructions = (TextView) findViewById(R.id.textViewInstructions);
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/SignPainter-HouseScript.ttf");
        textViewInstructions.setTypeface(myTypeface);
        textViewInstructions.setText(Html.fromHtml(ita));
    }

    public void changeLanguageEng(View view){
        textViewInstructions = (TextView) findViewById(R.id.textViewInstructions);
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/SignPainter-HouseScript.ttf");
        textViewInstructions.setTypeface(myTypeface);
        textViewInstructions.setText(Html.fromHtml(eng));
    }


    public void goToMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
