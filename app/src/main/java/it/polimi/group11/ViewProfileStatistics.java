package it.polimi.group11;

import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import it.polimi.group11.helper.DatabaseHelper;

public class ViewProfileStatistics extends AppCompatActivity implements ConfirmDeletionDialog.DialogListener {

    // Variable for the view.
    TextView textViewProfileName;
    TextView textViewMatchPlayed;
    TextView textViewMatchWon;
    TextView textViewMinNumberMoves;
    Button buttonDelete;
    ListView listViewMatches;

    // Variable for the database.
    DatabaseHelper dbHelper;
    int playerID;
    String namePlayer;

    // String for the Extra of the Intent.
    public final String KEY_EXTRA_PROVA = "Prova";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile_statistics);

        // Import of the font.
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/SignPainter-HouseScript.ttf");

        // Setting the fonts on the text in the view.
        textViewProfileName = (TextView) findViewById(R.id.TextViewProfileName);
        textViewMatchPlayed = (TextView) findViewById(R.id.textViewMatchPlayed);
        textViewMatchWon = (TextView) findViewById(R.id.textViewMatchWon);
        textViewMinNumberMoves = (TextView) findViewById(R.id.textViewMinNumberMoves);
        buttonDelete = (Button) findViewById(R.id.buttonDeleteProfile);

        // Getting the information from the Intent of the previous activity (ViewProfilesListActivity.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        playerID = extras.getInt("KEY_EXTRA_CONTACT_ID");


        // Getting the data from the Database.
        dbHelper = new DatabaseHelper(getApplicationContext());
        final Cursor cursor = dbHelper.getProfile(playerID);
        //final Cursor cursorMatches = dbHelper.getLastFiveMatches(playerID);
        final Cursor cursorMatchPlayed = dbHelper.getMatchPlayed(playerID);
        final Cursor cursorMatchWon = dbHelper.getMatchWon(playerID);
        final Cursor cursorMinMoves = dbHelper.getMinNumberMoves(playerID);


        // Setting the Name of the player and his statistics
        textViewProfileName.setText(dbHelper.getNamePlayerFromCursor(cursor));

        textViewProfileName.setTypeface(myTypeface);

        textViewMatchPlayed.setText("MATCH PLAYED " + dbHelper.getNumberMatchPlayedFromCursor(cursorMatchPlayed));
        textViewMatchWon.setText("MATCH WON " + dbHelper.getNumberMatchWonFromCursor(cursorMatchWon));
        textViewMinNumberMoves.setText("MIN MOVES " + dbHelper.getNumberMinMovesFromCursor(cursorMinMoves));

    /*
        // Setting the Last Five Matches
        String [] columns = new String[] {
                DatabaseHelper.PLAYER_COLUMN_NAME
        };
        int [] widgets = new int[] {
                R.id.player_name
        };

        listViewMatches = (ListView) findViewById(R.id.listViewMatches);
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.player_info,
                cursorMatches, columns, widgets, 0);
        listViewMatches = (ListView)findViewById(R.id.listViewMatches);
        listViewMatches.setAdapter(cursorAdapter);
        listViewMatches.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView listView, View view,
                                    int position, long id) {
                Cursor itemCursor = (Cursor) ViewProfileStatistics.this.listViewMatches.getItemAtPosition(position);
                int playerID = itemCursor.getInt(itemCursor.getColumnIndex(DatabaseHelper.PLAYER_COLUMN_ID));
                Intent intent = new Intent(ViewProfileStatistics.this, ViewMatchStatisticsActivity.class);
                intent.putExtra(KEY_EXTRA_PROVA, playerID);
                startActivity(intent);
            }
        });
        */
}

    /**
     * Alert Dialog that ask to the user if he is sure to delete the profile.
     * @param view
     */
    public void showAlert(View view){
        DialogFragment dialog = new ConfirmDeletionDialog();
        Bundle args = new Bundle();
        args.putString("title", "Alert");
        args.putString("message", "Delete profile?");
        dialog.setArguments(args);
        dialog.show(getFragmentManager(), "tag");
    }

    /**
     * If the user click Ok in the alert dialog
     * the method deleteProfile is called.
     */
    public void onOkClicked(){
        deleteProfile();
    }

    /**
     * Delete this profile from the Database. After deleting the profile
     * returns to the ViewProfileListActivity.
     */
    public void deleteProfile(){
        dbHelper.deleteProfile(playerID);
        Intent intent = new Intent(getApplicationContext(), ViewProfileListActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ViewProfileListActivity.class);
        startActivity(intent);
    }

}
