package it.polimi.group11;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import it.polimi.group11.helper.DatabaseHelper;
import it.polimi.group11.helper.Guest;
import it.polimi.group11.helper.GuestData;

public class AddProfileActivity extends AppCompatActivity {

    // Variable for the View.
    ImageView imageViewInsertPropic;
    Button buttonSaveProfile;
    EditText editTextProfileName;

    // Variable for the Database.
    public DatabaseHelper db;
    private Uri imageUri;
    private String imageUriString;
    private String uri;

    public final String KEY_RETURN_NAME = "KEY_RETURN_NAME";
    Guest guest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile);

        //Import of the font.
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/SignPainter-HouseScript.ttf");

        //Setting the fonts on the text in the view.
        imageViewInsertPropic = (ImageView) findViewById(R.id.imageViewInsertPropic);
        buttonSaveProfile = (Button) findViewById(R.id.buttonSaveProfile);
        buttonSaveProfile.setTypeface(myTypeface);
        editTextProfileName = (EditText) findViewById(R.id.editTextProfileName);
        editTextProfileName.setTypeface(myTypeface);

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
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ChoosePlayerTypeActivity.class);
        startActivity(intent);
    }

    public void persistPerson() {
        db = new DatabaseHelper(getApplicationContext());
        boolean success = db.insertProfile(editTextProfileName.getText().toString(), uri);

        if(success){
            Toast.makeText(getApplicationContext(), "Person Inserted", Toast.LENGTH_SHORT).show();
            //guest.setName(editTextProfileName.getText().toString());
            GuestData.nameArray[GuestData.cardPosition] = editTextProfileName.getText().toString();
            Intent intent = new Intent(this, SelectPlayersActivity.class);
            //intent.putExtra(KEY_RETURN_NAME, editTextProfileName.getText().toString());
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


    public void goToChoosePlayer(View view){
        Intent intent = new Intent(this, ChoosePlayerTypeActivity.class);
        startActivity(intent);
    }

}

