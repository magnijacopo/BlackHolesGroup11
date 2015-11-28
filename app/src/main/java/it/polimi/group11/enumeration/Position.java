package it.polimi.group11.enumeration;

/**
 * Position that the bars can be set to.
 * Depending on the bar's position the holes correspond to 7 of 9 slots in the bar.
 */
public enum Position {

    /**
     * The first 7 slots
     */
    INNER,

    /**
     * The last 7 slots
     */
    CENTRAL,

    /**
     * The middle 7 slots
     */
    OUTER;

    private static Position[] values = values();

    public Position next(){
        return values[(this.ordinal()+1)%values.length];
    }

    public Position previous(){
        return values[(this.ordinal()-1)%values.length];
    }

}

