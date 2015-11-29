package it.polimi.group11.model;


/**
 * Class move
 *
 */

public class Move {

    /**
     * Id of the move.
     * It's a three characters string.
     * First represent the position of the bar, horizontal or vertical,
     * Second represent the number of the bar, 1-7,
     * Third represent the movement of the bar, inward or outward.
     */
    private String moveId;

    /**
     * Id of the Player that has done this move.
     *
     * @see Player#id
     */
    private String playerId;


    //Constructor

    /**
     * Move Constructor
     *
     * @param move
     * @param player
     *
     * @see Move#moveId
     * @see Move#playerId
     */
    public Move(String move, String player){
        this.moveId = move;
        this.playerId = player;
    }


    // Getter

    /**
     *
     * @return playerId
     * @see Move#playerId
     */
    public String getPlayerId(){
        return playerId;
    }

    /**
     *
     * @return moveId
     * @see Move#moveId
     */
    public String getMoveId(){
        return moveId;
    }
}
