package it.polimi.group11;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import it.polimi.group11.helper.DatabaseHelper;

public class CreateMatchActivity extends AppCompatActivity {

    DatabaseHelper db;
    EditText editTextWinner;
    EditText editTextMoves;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_match);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    public void persistMatch(View view) {
        db = new DatabaseHelper(getApplicationContext());

        editTextMoves = (EditText) findViewById(R.id.editTextMoves);
        editTextWinner = (EditText) findViewById(R.id.editTextWinner);
    }
/*
        boolean success = db.insertMatch(Integer.parseInt(editTextWinner.getText().toString()),
                    Integer.parseInt(editTextMoves.getText().toString()));
        if(success){
            Toast.makeText(getApplicationContext(), "Match Inserted", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(), "Could not Insert person", Toast.LENGTH_SHORT).show();
        }
    } */
}
