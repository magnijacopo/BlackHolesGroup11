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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        textViewInstructions = (TextView) findViewById(R.id.textViewInstructions);
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/SignPainter-HouseScript.ttf");
        textViewInstructions.setTypeface(myTypeface);
        textViewInstructions.setText(Html.fromHtml("<html>\n" +
                "    <body>\n" +
                "        <h2>\n" +
                "            How to play:\n" +
                "        </h2>\n" +
                "        <h4>\n" +
                "            Game setup:\n" +
                "        </h4>\n" +
                "        <p>\n" +
                "            At the start of a game each player has to place 5 beads on the board in a round robin fashion.\n" +
                "            A bead can be set only over a blue or red bar.\n" +
                "        </p>\n" +
                "         <h4>\n" +
                "            Gameplay:\n" +
                "        </h4>\n" +
                "        <p>\n" +
                "            The goal of the game is to drop the opponents' beads into the black holes.<br>\n" +
                "            On each turn a player can move a bar by one position to the left or to the right if the board's bounds are respected.<br>\n" +
                "            A player is out of the game when he loses all of his beads.\n" +
                "        </p>\n" +
                "        <h4>\n" +
                "            Bar rules:\n" +
                "        </h4>\n" +
                "        <p>\n" +
                "            Sliding a bar that was slid in the previous by one other opponent is forbidden.<br>\n" +
                "            When only two players are on the board, a player cannot slide the same bar for more than two consecutive turns.\n" +
                "        </p>\n" +
                "        <h4>\n" +
                "            Win condition:\n" +
                "        </h4>\n" +
                "        <p>\n" +
                "            The game ends when only one player has beads remaining on the board.\n" +
                "        </p>\n" +
                "    </body>\n" +
                "</html>"));
    }

    public void changeLanguageIta(View view){
        textViewInstructions = (TextView) findViewById(R.id.textViewInstructions);
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/SignPainter-HouseScript.ttf");
        textViewInstructions.setTypeface(myTypeface);
        textViewInstructions.setText(Html.fromHtml("<html>\n" +
                "    <body>\n" +
                "        <h2>\n" +
                "            Come giocare:\n" +
                "        </h2>\n" +
                "        <h4>\n" +
                "            Inizializzazione:\n" +
                "        </h4>\n" +
                "        <p>\n" +
                "            All'inizio di una partita ogni giocatore posiziona, a turno, 5 biglie sulla tavola di gioco.\n" +
                "            Una biglia può essere posizionata solo su una barra blu o una barra rossa.\n" +
                "        </p>\n" +
                "\n" +
                "         <h4>\n" +
                "            Gameplay:\n" +
                "        </h4>\n" +
                "        <p>\n" +
                "            Lo scopo del gioco consiste nel far cadere le biglie degli avversari nei buchi neri.<br>\n" +
                "            Ad ogni turno un giocatore può muovere una barra di una posizione a destra o a sinistra se i limiti della plancia sono      rispettati.\n" +
                "            \n" +
                "        </p>\n" +
                "        <h4>\n" +
                "            Regole delle barre:\n" +
                "        </h4>\n" +
                "        <p>\n" +
                "            Muovere una barra mossa da un altro giocatore nel turno precedente è vietato.<br>\n" +
                "            Quando solo i beads di due giocatori sono presenti sulla tavola di gioco, un giocatore non può muovere la stessa barra per più di due turni consecutivi.\n" +
                "        </p>\n" +
                "        <h4>\n" +
                "            Condizioni di vittoria:\n" +
                "        </h4>\n" +
                "            Il gioco finisce quando solo un giocatore ha biglie rimanenti sulla tavola di gioco.\n" +
                "        </p>\n" +
                "    </body>\n" +
                "</html>"));
    }

    public void changeLanguageEng(View view){
        textViewInstructions = (TextView) findViewById(R.id.textViewInstructions);
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/SignPainter-HouseScript.ttf");
        textViewInstructions.setTypeface(myTypeface);
        textViewInstructions.setText(Html.fromHtml("<html>\n" +
                "    <body>\n" +
                "        <h2>\n" +
                "            How to play:\n" +
                "        </h2>\n" +
                "        <h4>\n" +
                "            Game setup:\n" +
                "        </h4>\n" +
                "        <p>\n" +
                "            At the start of a game each player has to place 5 beads on the board in a round robin fashion.\n" +
                "            A bead can be set only over a blue or red bar.\n" +
                "        </p>\n" +
                "         <h4>\n" +
                "            Gameplay:\n" +
                "        </h4>\n" +
                "        <p>\n" +
                "            The goal of the game is to drop the opponents' beads into the black holes.<br>\n" +
                "            On each turn a player can move a bar by one position to the left or to the right if the board's bounds are respected.<br>\n" +
                "            A player is out of the game when he loses all of his beads.\n" +
                "        </p>\n" +
                "        <h4>\n" +
                "            Bar rules:\n" +
                "        </h4>\n" +
                "        <p>\n" +
                "            Sliding a bar that was slid in the previous by one other opponent is forbidden.<br>\n" +
                "            When only two players are on the board, a player cannot slide the same bar for more than two consecutive turns.\n" +
                "        </p>\n" +
                "        <h4>\n" +
                "            Win condition:\n" +
                "        </h4>\n" +
                "        <p>\n" +
                "            The game ends when only one player has beads remaining on the board.\n" +
                "        </p>\n" +
                "    </body>\n" +
                "</html>"));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
