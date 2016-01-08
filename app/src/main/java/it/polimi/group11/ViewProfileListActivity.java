package it.polimi.group11;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import it.polimi.group11.database.DatabaseHelper;

public class ViewProfileListActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        dbHelper = new DatabaseHelper(this);

        final Cursor cursor = dbHelper.getAllProfiles();
        String [] columns = new String[] {
                DatabaseHelper.PLAYER_COLUMN_NAME
        };
        int [] widgets = new int[] {
                R.id.playerName
        };


        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.player_info,
                cursor, columns, widgets, 0);
        listView = (ListView)findViewById(R.id.listView1);
        listView.setAdapter(cursorAdapter);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
