package it.polimi.group11;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import it.polimi.group11.helper.DatabaseHelper;

public class LeaderboardActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    ListView listViewLeaderboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        dbHelper = new DatabaseHelper(this);
        final Cursor cursor = dbHelper.getLeaderboard();

        dbHelper.insertMatch(1,41);
        dbHelper.insertMatch(2,15);
        dbHelper.insertMatch(2,84);
        dbHelper.insertMatch(1, 523);

        String [] columns = new String[] {
                DatabaseHelper.MATCH_COLUMN_WINNER,
                DatabaseHelper.VINTE
        };
        int [] widgets = new int[] {
                R.id.player_name,
                R.id.wins
        };
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.leaderboard_info,
                cursor, columns, widgets, 0);
        listViewLeaderboard = (ListView)findViewById(R.id.listViewLeaderboard);
        listViewLeaderboard.setAdapter(cursorAdapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToViewProfileLeaderboard(View view) {
        Intent intent = new Intent(this, ViewProfileLeaderboardActivity.class);
        startActivity(intent);
    }

    public void goToMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
