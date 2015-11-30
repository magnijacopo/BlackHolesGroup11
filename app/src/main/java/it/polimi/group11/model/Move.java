package it.polimi.group11.model;

/**
 * An instantiation of this class represents a tuple
 * composed of a move and a player possibly executing the move
 */

public class Move {

    /**
     * Id of the move.
     * It's a three characters string.
     * First character represents the position of the bar: horizontal or vertical.
     * Second represents the number of the bar: 1-7.
     * Third represents the movement of the bar: inward or outward.
     */
    private String moveId;

    /**
     * Id of the Player paired with the move.
     *
     * @see Player#id
     */
    private String playerId;


    //Constructor

    /**
     * Move Constructor
     *
     * @param move {@link Move#moveId}
     * @param player {@link Move#playerId}
     */
    public Move(String move, String player){
        this.moveId = move;
        this.playerId = player;
    }


    // Getter

    /**
     * Getter for playerId
     * @return playerId {@link Move#playerId}
     */
    public String getPlayerId(){
        return playerId;
    }

    /**
     * Getter for moveId
     * @return moveId {@link Move#moveId}
     */
    public String getMoveId(){
        return moveId;
    }
}