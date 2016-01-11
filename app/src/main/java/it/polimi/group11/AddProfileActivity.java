package it.polimi.group11;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import it.polimi.group11.helper.DatabaseHelper;

public class AddProfileActivity extends AppCompatActivity {
    public DatabaseHelper db;
    private Uri imageUri;
    private String imageUriString;
    private String uri;

    ImageView imageViewInsertPropic;
    Button buttonSaveProfile;
    EditText editTextProfileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageViewInsertPropic = (ImageView) findViewById(R.id.imageViewInsertPropic);
        buttonSaveProfile = (Button) findViewById(R.id.buttonSaveProfile);
        editTextProfileName = (EditText) findViewById(R.id.editTextProfileName);

        imageViewInsertPropic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Profile Picture"), 1);
            }
        });

        buttonSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                persistPerson();
            }

        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void persistPerson() {
        db = new DatabaseHelper(getApplicationContext());
        boolean success = db.insertProfile(editTextProfileName.getText().toString(), uri);

        if(success){
            Toast.makeText(getApplicationContext(), "Person Inserted", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, SelectPlayersActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(), "Could not Insert person", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     *
     * @param reqCode
     * @param resCode
     * @param data
     */
    public void onActivityResult(int reqCode, int resCode, Intent data){
        if (resCode == RESULT_OK){
            if (reqCode == 1){

                Uri selectedImageUri = data.getData();
                imageViewInsertPropic.setImageURI(selectedImageUri);
                imageViewInsertPropic.setImageURI(data.getData());
                imageUri = data.getData();
                imageUriString = imageUri.toString();

                /*
                Cursor cursor = MediaStore.Images.Thumbnails.queryMiniThumbnails(getContentResolver(),
                        selectedImageUri, MediaStore.Images.Thumbnails.MICRO_KIND, null);

                if(cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    uri = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA));
                }
                */

            }
        }
    }

    public void goToCreateMatch(View view){
        Intent intent = new Intent(this, CreateMatchActivity.class);
        startActivity(intent);
    }
}

