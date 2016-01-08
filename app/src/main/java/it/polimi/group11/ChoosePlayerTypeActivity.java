package it.polimi.group11;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class ChoosePlayerTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_player_type);
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public void goToAddProfile(View view){
        Intent intent = new Intent(this, AddProfileActivity.class);
        startActivity(intent);
    }

    public void goToViewProfilesSelection(View view) {
        Intent intent = new Intent(this, AddProfileActivity.class);
        startActivity(intent);
    }

    public void goToArtificialIntelligence(View view) {

        Toast.makeText(getApplicationContext(), "COMING SOON", Toast.LENGTH_SHORT).show();

    }

}
