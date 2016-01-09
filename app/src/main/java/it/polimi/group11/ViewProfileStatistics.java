package it.polimi.group11;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import it.polimi.group11.database.DatabaseHelper;

public class ViewProfileStatistics extends AppCompatActivity {

    TextView textViewProfileName;
    ImageView imageViewPropic;
    Button buttonDelete;

    String namePlayer;
    String imageUriString;
    Uri imageUri;


    DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile_statistics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textViewProfileName = (TextView) findViewById(R.id.TextViewProfileName);
        imageViewPropic = (ImageView) findViewById(R.id.imageViewPropic);
        buttonDelete = (Button) findViewById(R.id.buttonDeleteProfile);

        textViewProfileName.setText(getNamePlayerFromCursor(cursor));
        imageViewPropic.setImageURI(getImagePlayerFromCursor(cursor));

        dbHelper = new DatabaseHelper(getApplicationContext());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    Intent intent = getIntent();

    Bundle extras = intent.getExtras();

    int playerID = extras.getInt("KEY_EXTRA_CONTACT_ID");

    final Cursor cursor = dbHelper.getProfile(playerID);


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

    public Uri getImagePlayerFromCursor(Cursor cursor) {
        if(cursor.moveToFirst()){
            imageUriString = cursor.getString(cursor.getColumnIndex(dbHelper.PLAYER_COLUMN_IMAGE));
        }
        imageUri = Uri.parse(imageUriString);
        return imageUri;
    }







}
