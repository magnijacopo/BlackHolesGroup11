package it.polimi.group11.helper;

/**
 * Created by Alessandro on 06/01/2016.
 */
public class Guest {
    String name;
    int image;
    int id_;

    public Guest(String name, int image, int id_){
        this.name = name;
        this.image = image;
        this.id_ = id_;
    }

    public String getName(){
        return name;
    }

    public int getImage(){
        return image;
    }

    public int getId_(){
        return id_;
    }
}
