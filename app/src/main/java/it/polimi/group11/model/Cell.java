package it.polimi.group11.model;

import it.polimi.group11.enumeration.Owner;

/**
 * Created by Jacopo Magni on 13/11/2015.
 */
public class Cell {

    private boolean horizontalBar;
    private boolean verticalBar;
    private Owner beadOwner;

    /**
     * The constructor of the class
     *
     * @param horizontalBar
     * @param vertcalBar
     * @param bead
     */
    public Cell(boolean horizontalBar, boolean vertcalBar, Owner bead) {
        horizontalBar = false;
        vertcalBar = false;
        beadOwner = Owner.NONE;
    }

    /**
     *
     * @param HBar
     */
    public void setHorizontalBar(boolean HBar){

        horizontalBar = HBar;

    }

    /**
     *
     * @return
     */
    public boolean getHorizontalBar(){

        return horizontalBar;

    }

    /**
     *
     * @param VBar
     */
    public void setVerticalBar(boolean VBar){

        verticalBar = VBar;

    }

    /**
     *
     * @return
     */
    public boolean getVerticalBar(){

        return verticalBar;

    }

    /**
     *
     * @param player
     */
    public void setBead(Owner player){

        beadOwner = player;

    }

    /**
     *
     * @return
     */
    public Owner getBeadOwner(){

        return beadOwner;

    }

}
