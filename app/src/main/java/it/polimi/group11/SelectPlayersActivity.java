package it.polimi.group11;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class SelectPlayersActivity extends AppCompatActivity {

    private int varProva = 2;
    private TextView provaprova;
    private static final String MAX_PLAYER = "You have already add the max number of players";
    private static final String ADD_PLAYER = "Player added";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_players);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        provaprova = (TextView) findViewById(R.id.textViewProva);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getVarProva() < 4) {
                    Snackbar.make(view, ADD_PLAYER, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    incrementNumberPlayers();
                    provaprova.setText(Integer.toString(getVarProva()));
                } else {
                    Snackbar.make(view, MAX_PLAYER, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    goToChoosePlayerType();
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_select_players, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_play){
            Intent intent = new Intent(this, PlayGameActivity.class);
            startActivity(intent);
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }

    public int getVarProva(){
        return this.varProva;
    }

    public void incrementNumberPlayers(){
        if(varProva < 4){
        varProva++;
        }else {
            varProva = 100;
        }
    }


    public void goToChoosePlayerType() {
        Intent intent = new Intent(this, ChoosePlayerTypeActivity.class);
        startActivity(intent);
    }
}
