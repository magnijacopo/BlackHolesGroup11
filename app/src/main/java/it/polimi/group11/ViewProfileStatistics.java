package it.polimi.group11;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import it.polimi.group11.helper.DatabaseHelper;

public class ViewProfileStatistics extends AppCompatActivity {

    TextView textViewProfileName;
    ImageView imageViewPropic;
    Button buttonDelete;

    String namePlayer;
    String imageUriString;
    Uri imageUri;

    DatabaseHelper dbHelper;
    int playerID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile_statistics);

        textViewProfileName = (TextView) findViewById(R.id.TextViewProfileName);
        imageViewPropic = (ImageView) findViewById(R.id.imageViewPropic);
        buttonDelete = (Button) findViewById(R.id.buttonDeleteProfile);

        Intent intent = getIntent();

        Bundle extras = intent.getExtras();

        playerID = extras.getInt("KEY_EXTRA_CONTACT_ID");
        dbHelper = new DatabaseHelper(getApplicationContext());
        final Cursor cursor = dbHelper.getProfile(playerID);

        textViewProfileName.setText(getNamePlayerFromCursor(cursor));
        //imageViewPropic.setImageURI(getImagePlayerFromCursor(cursor));

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


    public void deleteProfile(View view){
        dbHelper.deleteProfile(playerID);
        Intent intent = new Intent(getApplicationContext(), ViewProfileListActivity.class);
        startActivity(intent);
    }

    public void goToMatchStatistics(View view){
        Intent intent = new Intent(ViewProfileStatistics.this, ViewMatchStatisticsActivity.class);
        intent.putExtra("Prova", 0);
        startActivity(intent);
    }




}
