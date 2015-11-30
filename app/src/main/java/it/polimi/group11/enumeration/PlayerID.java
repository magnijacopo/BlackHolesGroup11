package it.polimi.group11.enumeration;

// Enumeration implemented but still not used

/**
 *
 */
public enum PlayerID {

    /**
     *
     */
    PLAYER1(1),

    /**
     *
     */
    PLAYER2(2),

    /**
     *
     */
    PLAYER3(3),

    /**
     *
     */
    PLAYER4(4);

    /**
     *
     */
    private int value;

    /**
     *
     * @param value
     */
    private PlayerID(int value){
        this.value = value;
    }

    /**
     *
     * @return
     */
    public int getValue(){
        return value;
    }
}


