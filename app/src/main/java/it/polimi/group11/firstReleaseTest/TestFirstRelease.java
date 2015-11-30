package it.polimi.group11.firstReleaseTest;

import java.util.LinkedList;
import java.util.Queue;

import it.polimi.group11.model.Board;
import it.polimi.group11.model.Game;


public class TestFirstRelease {

    /**
     *
     */
    private Board board;

    /**
     *
     */
    private Game game;

    /**
     *
     */
    int playersNumber;

    /**
     *
     */
    String movingPlayer;

    /**
     *
     */
    String horizontalBarsPosition;

    /**
     *
     */
    String verticalBarsPosition ;

    /**
     *
     */
    String beadsPosition;

    /**
     *
     */
    boolean checkConfigurationPlayers = true;

    /**
     *
     */
    boolean checkConfigurationMovingPlayer = true;

    /**
     *
     */
    boolean checkConfigurationBars = true;

    /**
     *
     */
    boolean checkConfigurationBeads = true;

    /**
     *
     */
    String lastConfiguration;

    /**
     *
     */
    String output;

    /**
     *
     */
    String move;

    /**
     *
     */
    Queue<String> moves = new LinkedList<>();

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
                        if (checkInputLength(test)){
                            int numMoves = ((test.length()-65)/3);
                            enqueueMove(test, numMoves);

                            String configuration = test.substring(0, 65);
                            output = finalConfiguration(configuration, moves.remove());
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

    /**
     *
     * @param configuration
     * @param mossa
     * @return
     */
    private String finalConfiguration(String configuration, String mossa){
        movingPlayer = game.currentPlayer(mossa, configuration.substring(16, 65));
        if (game.getValidity()){
            setBars();
            beadsPosition = setBeads(configuration.substring(16, 65));

            StringBuilder stringBuilder = new StringBuilder();
            playersNumber = game.getPlayers().size();
            stringBuilder.append(playersNumber).append(movingPlayer);
            configuration = stringBuilder+horizontalBarsPosition+verticalBarsPosition+beadsPosition;
            System.out.println("  move "+configuration+" "+mossa);
            if (moves.peek() != null && !(game.getGameOver())){
                finalConfiguration(configuration, moves.remove());
            }else{
                lastConfiguration = configuration;
                lastConfiguration = lastConfiguration.substring(0, 1)+game.getNextPlayer()+lastConfiguration.substring(2, lastConfiguration.length());
            }
            return lastConfiguration;
        }return "";
    }

    /**
     * Sets the bars
     */
    private void setBars(){
        horizontalBarsPosition = "";
        verticalBarsPosition = "";
        for (int i=0; i<7; i++){
            horizontalBarsPosition += board.getHorizontalBarPosition(i);
            verticalBarsPosition += board.getVerticalBarPosition(i);
        }
    }

    /**
     * Sets the beads.
     * @param input
     * @return
     */
    private String setBeads(String input){
        String checkGrid = board.checkGrid();
        return board.newBeadsPosition(checkGrid, input);
    }

    /**
     *  Checks that the lenght of the input string is correct.
     *  Longer than 65 characters and that after that it grows as a multiple of 3.
     * @param input the String used as String test
     * @return boolean, true if che lenght is correct, false otherwise.
     */
    private boolean checkInputLength(String input){
        return ((input.length() > 65) && ((input.length()-65)%3 == 0));
    }

    /**
     * From the String used as a test it gets the position of the bars and check if there is some error.
     * @param test The string used as a test.
     */
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

    /**
     * Breaks the input string in blocks of three characters (one move)
     * and creates a queue with these blocks.
     * @param test
     * @param numMoves
     */
    private void enqueueMove(String test, int numMoves){
        for (int i=0; i<numMoves; i++){
            move = test.substring(65+i*3, 68+i*3);
            moves.add(move);
        }
    }

    /**
     * From the String used as a test it gets the numbers of players and create a game with that players.
     * @param input
     */
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

    /**
     * From the String used as a test it gets and sets the first player to make a move.
     * @param input
     */
    private void setMovingPlayer(String input){
        movingPlayer = Character.toString((input.charAt(1)));
        if ((Character.getNumericValue(input.charAt(1)) > playersNumber) || (Character.getNumericValue(input.charAt(1)) < 1)){
            checkConfigurationMovingPlayer = false;
            output = ("error: the moving player must be between 1 and the number of players");
        }
    }

    /**
     * From the String used as a test it gets and sets the configuration of the beads on the board.
     * It checks also that the number of the beads it is not bigger than 5 for each player.
     * @param test
     */
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