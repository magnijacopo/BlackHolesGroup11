package it.polimi.group11;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import it.polimi.group11.helper.DatabaseHelper;

public class ViewProfileListActivity extends AppCompatActivity {

    public final static String KEY_EXTRA_CONTACT_ID = "KEY_EXTRA_CONTACT_ID";

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
                DatabaseHelper.PLAYER_COLUMN_NAME,
                DatabaseHelper.PLAYER_COLUMN_IMAGE
        };
        int [] widgets = new int[] {
                R.id.playerName,
                R.id.playerImage
        };


        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.player_info,
                cursor, columns, widgets, 0);
        listView = (ListView)findViewById(R.id.listView1);
        listView.setAdapter(cursorAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView listView, View view,
                                    int position, long id) {
                Cursor itemCursor = (Cursor) ViewProfileListActivity.this.listView.getItemAtPosition(position);
                int playerID = itemCursor.getInt(itemCursor.getColumnIndex(DatabaseHelper.PLAYER_COLUMN_ID));
                Intent intent = new Intent(getApplicationContext(), ViewProfileStatistics.class);
                intent.putExtra(KEY_EXTRA_CONTACT_ID, playerID);
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
