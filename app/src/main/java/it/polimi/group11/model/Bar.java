package it.polimi.group11.model;

/**
 * Created by Lale on 29/12/2015.
 */
public class Bar {
    private int position;
    private boolean[] composition=new boolean[9];

    public void setPosition(int position){
        this.position=position;
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
}