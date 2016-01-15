package it.polimi.group11.helper;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.internal.ParcelableSparseArray;

/**
 * Created by Alessandro on 06/01/2016.
 */
public class Guest implements Parcelable {
    String name;
    int icon;
    String color;
    int id_;

    public Guest(){

    }

    public Guest(String name, int image, String color, int id_){
        this.name = name;
        this.icon = image;
        this.color = color;
        this.id_ = id_;
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags){
        out.writeString(name);
        out.writeInt(icon);
        out.writeString(color);
        out.writeInt(id_);
    }

    public static final Parcelable.Creator<Guest> CREATOR = new Parcelable.Creator<Guest>(){
        public Guest createFromParcel(Parcel in){
            return new Guest(in);
        }

        public Guest[] newArray(int size){
            return new Guest[size];
        }
    };

    private Guest(Parcel in){
        name = in.readString();
        icon = in.readInt();
        color = in.readString();
        id_ = in.readInt();

    }

    public String getName(){
        return name;
    }

    public int getIcon(){
        return icon;
    }

    public String getColor() { return color; }

    public int getId_(){
        return id_;
    }

    public void setName(String name) { this.name = name; }
}
