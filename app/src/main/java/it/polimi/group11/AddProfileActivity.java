package it.polimi.group11;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import it.polimi.group11.helper.DatabaseHelper;
import it.polimi.group11.helper.GuestData;

public class AddProfileActivity extends AppCompatActivity {

    // Variable for the View.
    Button buttonSaveProfile;
    EditText editTextProfileName;

    // Variable for the Database.
    public DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile);

        //Import of the font.
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/SignPainter-HouseScript.ttf");

        //Setting the fonts on the text in the view.
        buttonSaveProfile = (Button) findViewById(R.id.buttonSaveProfile);
        buttonSaveProfile.setTypeface(myTypeface);
        editTextProfileName = (EditText) findViewById(R.id.editTextProfileName);
        editTextProfileName.setTypeface(myTypeface);

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

    /**
     *  It is the method that save the profile into the database
     *  and throw a toast to let know to the user if the insert has succeed.
     */
    public void persistPerson() {
        db = new DatabaseHelper(getApplicationContext());
        boolean success = db.insertProfile(editTextProfileName.getText().toString());

        if(success){
            Toast.makeText(getApplicationContext(), "Person Inserted", Toast.LENGTH_SHORT).show();
            GuestData.nameArray[GuestData.cardPosition] = editTextProfileName.getText().toString();
            Intent intent = new Intent(this, SelectPlayersActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(), "Could not Insert person", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to navigate to other activity
    public void goToChoosePlayer(View view){
        Intent intent = new Intent(this, ChoosePlayerTypeActivity.class);
        startActivity(intent);
    }

}

