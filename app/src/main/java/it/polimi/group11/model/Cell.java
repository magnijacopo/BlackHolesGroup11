package it.polimi.group11.model;

/**
 * Created by Lale on 29/12/2015.
 */
public class Cell {


    private boolean horizontal;
    private boolean vertical;
    private boolean bead;


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

}
