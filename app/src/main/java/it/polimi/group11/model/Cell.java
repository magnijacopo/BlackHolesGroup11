package it.polimi.group11.model;

import android.util.Log;
import android.view.View;

/**
 * Created by Lale on 29/12/2015.
 */
public class Cell {

    /**
     * Attribute declaration
     */

    private boolean horizontal=false;
    private boolean vertical=false;
    private boolean bead=false;
    private String owner="0";
    private String id="";
    private float centreX;
    private float centreY;
    private float centre[]= new float[2];

    /**
     * This metod find the centre of the cell on the screen
     * @param cell
     * @param x
     * @param y
     */

    public void setCentre(View cell, float x,float y)
    {

        int[] locationOnScreen = new int[2];
        cell.getLocationOnScreen(locationOnScreen);
        double addXPX = (cell.getWidth()-x)/2;
        double subYPX = ((y/2)+(cell.getHeight()-y)/2)+(60*cell.getResources().getDisplayMetrics().density);

        setCentreX(locationOnScreen[0] + (int) addXPX);
        setCentreY(locationOnScreen[1] - (int) subYPX);
        centre[0] = getCentreX();
        centre[1] = getCentreY();

    }

    public float[] getCentre()
    {
        return centre;
    }

    public float getCentreX() {
        return centreX;
    }

    public void setCentreX(float centreX) {
        this.centreX = centreX;
    }

    public float getCentreY() {
        return centreY;
    }

    public void setCentreY(float centreY) {
        this.centreY = centreY;
    }


    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }

    public boolean getHorizontal(){
        return horizontal;
    }

    public void setVertical(boolean vertical) {
        this.vertical = vertical;
    }

    public boolean getVertical(){
        return vertical;
    }

    public void setBead(boolean bead) {
        this.bead = bead;
    }

    public boolean getBead(){
        return bead;
    }

    public void setOwner(String owner){
        this.owner=owner;
    }
    public String getOwner(){
        return owner;
    }

    public void setId(String id){
        this.id=id;
    }
    public String getId(){
        return id;
    }

}
