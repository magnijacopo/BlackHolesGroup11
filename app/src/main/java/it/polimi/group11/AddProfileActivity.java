package it.polimi.group11;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import it.polimi.group11.database.DatabaseHelper;

public class AddProfileActivity extends AppCompatActivity {


    private DatabaseHelper dbHelper;
    private Uri imageUri;
    private String imageUriString;

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
                return;
            }

        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     *
     */
    public void persistPerson() {

        if(dbHelper.insertProfile(editTextProfileName.getText().toString(),
                    imageUriString)){
                Toast.makeText(getApplicationContext(), "Person Inserted", Toast.LENGTH_SHORT).show();
        }
        else{
                Toast.makeText(getApplicationContext(), "Could not Insert person", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
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
                imageViewInsertPropic.setImageURI(data.getData());
                imageUri = data.getData();
                imageUriString = imageUri.toString();
            }
        }
    }
}

