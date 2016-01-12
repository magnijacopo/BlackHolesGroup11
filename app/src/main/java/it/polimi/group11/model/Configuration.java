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

    private String getFirstPlayer() {
        return String.valueOf(game.getFirstPlayer());
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

    public void setFirstConfiguration(){
        configuration=getNumberOfPlayers() + getFirstPlayer() + getBarStatus() + getBeadsPosition();
    }

    public String getConfiguration(){
        return configuration;
    }

    private String configuration(String move){
        movingPlayer = game.currentPlayer(move);
        if(movingPlayer.equals(game.getError()))
            return "error: the move is not valid";
        if (game.getValidity())
            configuration = getNumberOfPlayers()+movingPlayer+getBarStatus()+getBeadsPosition();
        return configuration;
    }

    public void setBoard(Board2 board) {
        this.board = board;
    }

    public void setGame2(Game2 game2) {
        this.game = game2;
    }
}
