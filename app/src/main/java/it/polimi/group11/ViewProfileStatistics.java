package it.polimi.group11;

import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import it.polimi.group11.helper.DatabaseHelper;

public class ViewProfileStatistics extends AppCompatActivity implements ConfirmDeletionDialog.DialogListener {

    TextView textViewProfileName;
    TextView textViewMatchPlayed;
    TextView textViewMatchWon;
    TextView tectViewMinNumberMoves;
    ImageView imageViewPropic;
    Button buttonDelete;

    String namePlayer;
    String imageUriString;
    Uri imageUri;

    DatabaseHelper dbHelper;
    int playerID;

    ListView listViewMatches;

    public final String KEY_EXTRA_PROVA = "Prova";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile_statistics);

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/SignPainter-HouseScript.ttf");

        textViewProfileName = (TextView) findViewById(R.id.TextViewProfileName);
        textViewMatchPlayed = (TextView) findViewById(R.id.textViewMatchPlayed);
        textViewMatchWon = (TextView) findViewById(R.id.textViewMatchWon);
        tectViewMinNumberMoves = (TextView) findViewById(R.id.textViewMinNumberMoves);
        //imageViewPropic = (ImageView) findViewById(R.id.imageViewPropic);
        buttonDelete = (Button) findViewById(R.id.buttonDeleteProfile);

        Intent intent = getIntent();

        Bundle extras = intent.getExtras();

        playerID = extras.getInt("KEY_EXTRA_CONTACT_ID");
        dbHelper = new DatabaseHelper(getApplicationContext());
        final Cursor cursor = dbHelper.getProfile(playerID);

        textViewProfileName.setText(getNamePlayerFromCursor(cursor));
        textViewProfileName.setTypeface(myTypeface);

        final Cursor cursorMatches = dbHelper.getAllProfiles();

        String [] columns = new String[] {
                DatabaseHelper.PLAYER_COLUMN_NAME
        };
        int [] widgets = new int[] {
                R.id.player_name
        };

        /*
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


    /*
    String [] columns = new String[] {
            ,
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
    */

    public String getNamePlayerFromCursor(Cursor cursor){
        if(cursor.moveToFirst()){
            namePlayer = cursor.getString(cursor.getColumnIndex(dbHelper.PLAYER_COLUMN_NAME));
        }
        return namePlayer;
    }

    /*
    public Uri getImagePlayerFromCursor(Cursor cursor) {
        if(cursor.moveToFirst()){
            imageUriString = cursor.getString(cursor.getColumnIndex(dbHelper.PLAYER_COLUMN_IMAGE));
        }
        imageUri = Uri.parse(imageUriString);
        return imageUri;
    }
    */

    public void showAlert(View view){
        DialogFragment dialog = new ConfirmDeletionDialog();
        Bundle args = new Bundle();
        args.putString("title", "Alert");
        args.putString("message", "Delete profile?");
        dialog.setArguments(args);
        dialog.show(getFragmentManager(), "tag");
    }

    public void onOkClicked(){
        deleteProfile();
    }


    public void deleteProfile(){
        dbHelper.deleteProfile(playerID);
        Intent intent = new Intent(getApplicationContext(), ViewProfileListActivity.class);
        startActivity(intent);
    }

}
