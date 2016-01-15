package it.polimi.group11.model;

public class Configuration {

    /**
     * {@link BoardFirstReleaseTests}
     */
    public Board board;
    /**
     * {@link GameFirstReleaseTest}
     */
    private Game game;
    /**
     * Number of players
     */

    String movingPlayer;
    /**
     * {@link BoardFirstReleaseTests#horizontalBarsPosition}
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


    public void setBoard(Board board) {
        this.board = board;
    }

    public void setGame2(Game game) {
        this.game = game;
    }
}