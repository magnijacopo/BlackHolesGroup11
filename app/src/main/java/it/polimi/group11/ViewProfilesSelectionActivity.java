package it.polimi.group11;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import it.polimi.group11.helper.DatabaseHelper;
import it.polimi.group11.helper.GuestData;

public class ViewProfilesSelectionActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    ListView listView;
    String namePlayer;
    public final String KEY_EXTRA_NAME_PROFILE = "KEY_EXTRA_NAME_PROFILE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profiles_selection);

        dbHelper = new DatabaseHelper(this);
        final Cursor cursor = dbHelper.getAllProfiles();

        String [] columns = new String[] {
                DatabaseHelper.PLAYER_COLUMN_NAME,
        };
        int [] widgets = new int[] {
                R.id.player_name,
        };

        /*
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/SignPainter-HouseScript.ttf");
        textViewPlayerName.setTypeface(myTypeface);
        textViewPlayerName = (TextView) findViewById(R.id.player_name);
        */

        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.player_info,
                cursor, columns, widgets, 0);
        listView = (ListView)findViewById(R.id.listViewProfiles);
        listView.setAdapter(cursorAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView listView, View view, int position, long id) {
                Cursor itemCursor = (Cursor) ViewProfilesSelectionActivity.this.listView.getItemAtPosition(position);
                int playerID = itemCursor.getInt(itemCursor.getColumnIndex(DatabaseHelper.PLAYER_COLUMN_ID));
                finish();
                namePlayer = dbHelper.getNamePlayerFromCursor(dbHelper.getProfile(playerID));
                boolean alreadyInserted = false;
                for(int i = 0; i < GuestData.nameArray.length; i++){
                    if(namePlayer.equals(GuestData.nameArray[i])){
                        alreadyInserted = true;
                    }
                }
                if (alreadyInserted){
                    Toast.makeText(getApplicationContext(), "Player already playing", Toast.LENGTH_SHORT).show();
                }else{
                    GuestData.nameArray[GuestData.cardPosition] = namePlayer;
                }
                Intent intent = new Intent(ViewProfilesSelectionActivity.this, SelectPlayersActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ChoosePlayerTypeActivity.class);
        startActivity(intent);
    }

    public void goToChoosePlayerType(View view) {
        Intent intent = new Intent(this, ChoosePlayerTypeActivity.class);
        startActivity(intent);
    }
}