package it.polimi.group11.firstReleaseTest;

import java.util.LinkedList;
import java.util.Queue;

import it.polimi.group11.model.BoardFirstReleaseTests;
import it.polimi.group11.model.GameFirstReleaseTest;


public class TestFirstRelease {

    /**
     * {@link BoardFirstReleaseTests}
     */
    private BoardFirstReleaseTests boardFirstReleaseTests;
    /**
     * {@link GameFirstReleaseTest}
     */
    private GameFirstReleaseTest gameFirstReleaseTest;
    /**
     * Number of players
     */
    int playersNumber;
    /**
     * Indicates the player of the current round
     */
    String movingPlayer;
    /**
     * {@link BoardFirstReleaseTests#horizontalBarsPosition}
     */
    String horizontalBarsPosition;
    /**
     * {@link BoardFirstReleaseTests#verticalBarsPosition}
     */
    String verticalBarsPosition;
    /**
     * The current positions of the beads
     */
    String beadsPosition;
    /**
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
     * Indicates the last Configuration of the boardFirstReleaseTests , including setting of the bars,
     * position of the beads, and situation of the players before the current move
     */
    String lastConfiguration;
    /**
     * Indicates the configuration of the BoardFirstReleaseTests after the current move including setting of the bars,
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
    Queue<String> moves = new LinkedList<>();

    public String moveTest(String test) {
        setPlayersNumber(test);
        setMovingPlayer(test);
        setBarsPosition(test);
        setConfigurationBeads(test);

        boardFirstReleaseTests.prepareGrid();

        if (checkConfigurationPlayers) {
            if (checkConfigurationMovingPlayer) {
                if (checkConfigurationBars) {
                    if (checkConfigurationBeads) {
                        if (checkInputLength(test)) {
                            int numMoves = ((test.length() - 65) / 3);
                            enqueueMove(test, numMoves);
                            String configuration = test.substring(0, 65);
                            output = finalConfiguration(configuration, moves.remove());
                        }

                        if (!gameFirstReleaseTest.getValidity()) {
                            output = ("error: " + gameFirstReleaseTest.getError());
                        }
                    } else {
                        output = "error : move doesn't work";
                    }
                }
            }
        }
        return output;
    }

    /**
     * @param configuration
     * @param mossa
     * @return
     */
    private String finalConfiguration(String configuration, String mossa) {

        if (moves.size() == 0) {
            gameFirstReleaseTest.setMovesFinished(true);
        }

        movingPlayer = gameFirstReleaseTest.currentPlayer(mossa, configuration.substring(16, 65));

        if (movingPlayer.equals(gameFirstReleaseTest.getError())) {

            lastConfiguration = "error: the move is not valid";

        } else {

            if (gameFirstReleaseTest.getValidity()) {
                setBars();
                beadsPosition = setBeads(configuration.substring(16, 65));

                StringBuilder stringBuilder = new StringBuilder();
                playersNumber = gameFirstReleaseTest.getPlayers().size();
                stringBuilder.append(playersNumber).append(movingPlayer);
                configuration = stringBuilder + horizontalBarsPosition + verticalBarsPosition + beadsPosition;
                System.out.println("  move " + configuration);
                if (moves.peek() != null && !(gameFirstReleaseTest.getGameOver())) {
                    finalConfiguration(configuration, moves.remove());
                } else {
                    lastConfiguration = configuration;
                }
                return lastConfiguration;
            }
        }
            return "";
    }

    /**
     * Sets the bars
     */
    private void setBars(){
        horizontalBarsPosition = "";
        verticalBarsPosition = "";
        for (int i=0; i<7; i++){
            horizontalBarsPosition += boardFirstReleaseTests.getHorizontalBarPosition(i);
            verticalBarsPosition += boardFirstReleaseTests.getVerticalBarPosition(i);
        }
    }

    /**
     * Sets the beads.
     * @param input
     * @return
     */
    private String setBeads(String input){
        String checkGrid = boardFirstReleaseTests.checkGrid();
        return boardFirstReleaseTests.newBeadsPosition(checkGrid, input);
    }

    /**
     *  Checks that the length of the input string is correct.
     *  Longer than 65 characters and that after that it grows as a multiple of 3.
     * @param input the String used as String test
     * @return boolean, true if che length is correct, false otherwise.
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
                boardFirstReleaseTests.setHorizontalBarPosition(Character.getNumericValue((test.charAt(i+2))), i);
            }else{
                output="error :horizontal and vertical bar must be setted from 0 to 2";
                checkConfigurationBars=false;
            }
            if((Integer.parseInt(test.substring(i+9, i+10)) >= 0) && (Integer.parseInt(test.substring(i+9, i+10)) <= 2)){
                boardFirstReleaseTests.setVerticalBarPosition(Integer.parseInt(test.substring(i+9, i+10)), i);
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
     * From the String used as a test it gets the numbers of players and create a gameFirstReleaseTest with that players.
     * @param input
     */
    private void setPlayersNumber(String input){
        playersNumber = Character.getNumericValue(input.charAt(0));
        if(playersNumber > 1 && playersNumber < 5){
            gameFirstReleaseTest = new GameFirstReleaseTest(playersNumber);
            boardFirstReleaseTests = gameFirstReleaseTest.getBoardFirstReleaseTests();
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
        }else{
            gameFirstReleaseTest.setFirstPlayer(movingPlayer);
        }
    }


    /**
     * From the String used as a test it gets and sets the configuration of the beads on the boardFirstReleaseTests.
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