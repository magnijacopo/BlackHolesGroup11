package it.polimi.group11.model;

import java.util.LinkedList;
import java.util.Queue;

public class Configuration {

    /**
     * {@link Board}
     */
    private Board2 board;
    /**
     * {@link Game}
     */
    private Game2 game;
    /**
     * Number of players
     */
    int playersNumber;
    /**
     * Indicates the player of the current round
     */


    String movingPlayer;
    /**
     * {@link Board#horizontalBarsPosition}
     * /**
     * Indicates if the number of players is correct.
     * Remains if the number is more thaan one and less than five, if not gives false
     */
    boolean checkConfigurationPlayers = true;
    /**
     * Check the moving player.
     * Remains true if the moving player id is more than one and less than five, if not gives false
     */
    boolean checkConfigurationMovingPlayer = true;
    /**
     * Indicates if the setting of the bars is correct.
     * Remains true if the bars positions is more than zero(included) and less than two(included),
     * if not gives false
     */
    boolean checkConfigurationBars = true;
    /**
     * Check the number of bits for each players.
     * Remains true if it's less than 5(included), if not gives false
     */
    boolean checkConfigurationBeads = true;


    boolean checkMove = true;
    /**
     * Indicates the last Configuration of the board , including setting of the bars,
     * position of the beads, and situation of the players before the current move
     */
    String lastConfiguration;
    /**
     * Indicates the configuration of the Board after the current move including setting of the bars,
     * position of the beads, and situation of the players, but in the case of an
     * irregularity it returns an error
     */
    String output;
    /**
     * Indicates the current move
     */
    String move;

    /**
     *
     */

    private String firstConfiguration = getNumberOfPlayers() + getFirstPlayer() + getBarStatus() + getBeadsPosition();

    private String getFirstPlayer() {
        return String.valueOf(game.getPlayer(0));
    }

    private String getNumberOfPlayers() {
        return String.valueOf(game.getNumPlayers());
    }

    private String getBarStatus() {
        return board.getBarStatus();
    }

    private String getBeadsPosition() {
        return board.getCurrentBeadsPosition();
    }
}
