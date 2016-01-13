package it.polimi.group11.model;

import java.util.LinkedList;
import java.util.Queue;

public class Configuration {

    /**
     * {@link Board}
     */
    public Board2 board;
    /**
     * {@link Game}
     */
    private Game2 game;
    /**
     * Number of players
     */

    String movingPlayer;
    /**
     * {@link Board#horizontalBarsPosition}
     * /**
     * Indicates if the number of players is correct.
     * Remains if the number is more than one and less than five, if not gives false
     */


    private String configuration ="";

    private String getCurrentPlayer() {
        return (game.getCurrentPlayer());
    }

    private String getNumberOfPlayers() {
        return String.valueOf(game.getPlayerNum());
    }

    private String getBarStatus() {
        return board.getBarStatus();
    }

    private String getBeadsPosition() {
        return board.getCurrentBeadsPosition();
    }


    public String getConfiguration(){
        configuration=getNumberOfPlayers() + getCurrentPlayer() + " "+ getBarStatus() +" "+getBeadsPosition();
        return configuration;
    }


    public void setBoard(Board2 board) {
        this.board = board;
    }

    public void setGame2(Game2 game2) {
        this.game = game2;
    }
}