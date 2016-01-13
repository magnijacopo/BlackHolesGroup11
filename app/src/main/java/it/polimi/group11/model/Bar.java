package it.polimi.group11.model;

import android.util.Log;

/**
 * Created by Lale on 29/12/2015.
 */
public class Bar {
    private int position;
    private boolean[] composition=new boolean[9];
    private String id="";

    public void setPosition(int position){
        this.position=position;
        Log.i("Bar ", "STO SETTANDO LA BARRA");
    }

    public int getPosition() {
        return position;
    }

    public void setComposition(boolean[] composition) {
        this.composition = composition;
    }

    public boolean getValue(int i){
        return composition[i];
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}