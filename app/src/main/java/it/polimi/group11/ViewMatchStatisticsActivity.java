package it.polimi.group11;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import it.polimi.group11.helper.DatabaseHelper;

public class ViewMatchStatisticsActivity extends AppCompatActivity {

    TextView textViewId;
    TextView textViewDate;
    TextView textViewWinner;
    TextView textViewMoves;

    DatabaseHelper dbHelper;

    int prova;

    int idMatch;
    int numberMoves;
    int idWinner;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_match_statistics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        Bundle extras = intent.getExtras();

        prova = extras.getInt("Prova");


        textViewId = (TextView) findViewById(R.id.TextViewId);
        textViewDate = (TextView) findViewById(R.id.TextViewDate);
        textViewWinner = (TextView) findViewById(R.id.TextViewWinner);
        textViewMoves = (TextView) findViewById(R.id.TextViewMoves);

        dbHelper = new DatabaseHelper(getApplicationContext());
        final Cursor cursor = dbHelper.getMatch(prova);

        textViewId.setText(getIdMatchFromCursor(cursor));

    }

    public int getIdMatchFromCursor(Cursor cursor){
        if(cursor.moveToFirst()){
            idMatch = cursor.getInt(cursor.getColumnIndex(dbHelper.MATCH_COLUMN_ID));
        }
        return idMatch;
    }

    /*
    public String getDateMatchFromCursor(Cursor cursor){
        if(cursor.moveToFirst()){
            namePlayer = cursor.getString(cursor.getColumnIndex(dbHelper.PLAYER_COLUMN_NAME));
        }
        return namePlayer;
    }

    public String getWinnerIdFromCursor(Cursor cursor){
        if(cursor.moveToFirst()){
            namePlayer = cursor.getString(cursor.getColumnIndex(dbHelper.PLAYER_COLUMN_NAME));
        }
        return namePlayer;
    }

    public String getMovesNumberFromCursor(Cursor cursor){
        if(cursor.moveToFirst()){
            namePlayer = cursor.getString(cursor.getColumnIndex(dbHelper.PLAYER_COLUMN_NAME));
        }
        return namePlayer;
    }
    */

}
