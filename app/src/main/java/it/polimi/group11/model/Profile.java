package it.polimi.group11.model;


import android.net.Uri;

/**
 *  User profile.
 *
 *  DA IMPLEMENTARE
 */
public class Profile {

    /**
     *
     */
    private String name;

    /**
     *
     */
    private Uri imageUri;

    public Profile(String name, Uri imageUri){
        this.name = name;
        this.imageUri = imageUri;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }
}
