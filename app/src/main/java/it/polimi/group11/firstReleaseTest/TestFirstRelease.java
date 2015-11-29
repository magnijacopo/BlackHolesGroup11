package it.polimi.group11.firstReleaseTest;

import java.util.LinkedList;
import java.util.Queue;

import it.polimi.group11.model.Board;
import it.polimi.group11.model.Game;


public class TestFirstRelease {

    Board board = Board.getInstance();
    private Game game;

    int playersNumber;
    String movingPlayer;
    String horizontalBarsPosition = new String();
    String verticalBarsPosition = new String();
    String beadsPosition = new String();

    boolean checkConfigurationPlayers = true;
    boolean checkConfigurationMovingPlayer = true;
    boolean checkConfigurationBars = true;
    boolean checkConfigurationBeads = true;

    String move = new String();
    Queue<String> mosse = new LinkedList<String>();

    String lastConfiguration = new String();
    String output;


    public String moveTest(String test){
        setPlayersNumber(test);
        setMovingPlayer(test);
        setBarsPosition(test);
        setConfigurationBeads(test);

        board.prepareGrid();

        if (checkConfigurationPlayers){
            if (checkConfigurationMovingPlayer){
                if(checkConfigurationBars){
                    if(checkConfigurationBeads){
                        if (checkInputLenght(test)){
                            int numMoves = ((test.length()-65)/3);
                            enqueueMove(test, numMoves);

                            String configuration = test.substring(0, 65);
                            output = finalConfiguration(configuration, mosse.remove());
                        }else{
                            output = "error: input configuration has not a valid length";
                        }

                        if (!game.getValidity()){
                            output = ("error: "+game.getError());}
                    }
                }
            }
        }
        return output;
    }

    private String finalConfiguration(String configuration, String mossa){
        movingPlayer = game.nextPlayer(mossa, configuration.substring(16, 65));
        if (game.getValidity()){
            setBars();
            beadsPosition = setBeads(configuration.substring(16, 65));

            StringBuilder stringBuilder = new StringBuilder();
            playersNumber = game.getPlayers().size();
            stringBuilder.append(playersNumber).append(movingPlayer);
            configuration = stringBuilder+horizontalBarsPosition+verticalBarsPosition+beadsPosition;
            System.out.println("  move "+configuration+" "+mossa);
            if (mosse.peek() != null && !(game.getGameOver())){
                finalConfiguration(configuration, mosse.remove());
            }else{
                lastConfiguration = configuration;
            }
            lastConfiguration = lastConfiguration.substring(0, 1)+game.getLastPlayer()+lastConfiguration.substring(3, lastConfiguration.length());
            return lastConfiguration;
        }return "";
    }

    private void setBars(){
        horizontalBarsPosition = "";
        verticalBarsPosition = "";
        for (int i=0; i<7; i++){
            horizontalBarsPosition += board.getHorizontalBarPosition(i);
            verticalBarsPosition += board.getVerticalBarPosition(i);
        }
        return;
    }

    private String setBeads(String input){
        String checkGrid = board.checkGrid();
        String testBeads = board.newBeadsPosition(checkGrid, input);
        return testBeads;
    }

    private boolean checkInputLenght(String input){
        if ((input.length() > 65) && ((input.length()-65)%3 == 0)){
            return true;
        }else{
            return false;
        }
    }

    private void setBarsPosition(String test){
        for (int i=0; i<7; i++){
            if((Character.getNumericValue((test.charAt(i+2))) >= 0) && (Character.getNumericValue((test.charAt(i+2))) <= 2)){
                board.setHorizontalBarPosition(Character.getNumericValue((test.charAt(i+2))), i);
            }else{
                output="error :horizontal and vertical bar must be setted from 0 to 2";
                checkConfigurationBars=false;
            }
            if((Integer.parseInt(test.substring(i+9, i+10)) >= 0) && (Integer.parseInt(test.substring(i+9, i+10)) <= 2)){
                board.setVerticalBarPosition(Integer.parseInt(test.substring(i+9, i+10)), i);
            }else{
                output = "error :horizontal and vertical bar must be setted from 0 to 2";
                checkConfigurationBars = false;
            }
        }
    }

    private void enqueueMove(String test, int numMoves){
        for (int i=0; i<numMoves; i++){
            move = test.substring(65+i*3, 68+i*3);
            mosse.add(move);
        }
    }

    private void setPlayersNumber(String input){
        playersNumber = Character.getNumericValue(input.charAt(0));
        if(playersNumber > 1 && playersNumber < 5){
            game = new Game(playersNumber);
        }else{
            checkConfigurationPlayers = false;
            output = ("error: the number of players is invalid, it must be from 2 to 4");
        }
        return;
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
}