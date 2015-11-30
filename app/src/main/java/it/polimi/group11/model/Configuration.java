package it.polimi.group11.model;

public class Configuration {
   /* private Board board;
    private Game game;

    private int playersNumber;
    private String movingPlayer;
    private String horizontalBarsPosition;
    private String verticalBarsPosition ;
    private String beadsPosition;

    boolean checkConfigurationPlayers = true;
    boolean checkConfigurationMovingPlayer = true;
    boolean checkConfigurationBars = true;
    boolean checkConfigurationBeads = true;

    private void setPlayersNumber(String input){
        playersNumber = Character.getNumericValue(input.charAt(0));
        if(playersNumber > 1 && playersNumber < 5){
            game = new Game(playersNumber);
            board = game.getBoard();
        }else{
            checkConfigurationPlayers = false;
            output = ("error: the number of players is invalid, it must be from 2 to 4");
        }
    }

    private void setMovingPlayer(String input){
        movingPlayer = Character.toString((input.charAt(1)));
        if ((Character.getNumericValue(input.charAt(1)) > playersNumber) || (Character.getNumericValue(input.charAt(1)) < 1)){
            checkConfigurationMovingPlayer = false;
            output = ("error: the moving player must be between 1 and the number of players");
        }
    }

    private void setConfigurationBeads(String test){
        int numBeads;
        for(int j=0; j<playersNumber; j++){
            numBeads = 0;
            for (int i=0; i<49; i++){
                if( (Character.getNumericValue(test.charAt(16+i))) == (j+1) ){
                    numBeads++;
                }
            }
            if (numBeads > 5){
                checkConfigurationBeads = false;
                output = "error: each player can set max 5 beads";
            }
        }
    }

    private void setBars(){
        horizontalBarsPosition = "";
        verticalBarsPosition = "";
        for (int i=0; i<7; i++){
            horizontalBarsPosition += board.getHorizontalBarPosition(i);
            verticalBarsPosition += board.getVerticalBarPosition(i);
        }
    }

    private String setBeads(String input){
        String checkGrid = board.checkGrid();
        return board.newBeadsPosition(checkGrid, input);
    }
    */
}
