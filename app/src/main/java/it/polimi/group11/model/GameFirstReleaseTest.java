package it.polimi.group11.model;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * A game session
 */

public class GameFirstReleaseTest {
    private boolean movesFinished=false;

    private String MOVE_NOT_VALID = "error: la mossa non è valida";

    private String lastPlayer;
    /**
     * The game is paired with the boardFirstReleaseTests instantiated in this game session.
     */
    private BoardFirstReleaseTests boardFirstReleaseTests = new BoardFirstReleaseTests();

    /**
     * List of the players that will play this game.
     * @see Player
     */
    private List<Player> players = new ArrayList<>();

    /**
     * Iterator used to iterate the ArrayList players
     */
    private ListIterator<Player> iterator;

    /**
     * It is the player that should execute the current move.
     * @see Player
     */
    private Player currentMovingPlayer;

    /**
     * The list of the moves done by all the players.
     * @see Move
     */
    private ArrayList<Move> movesList = new ArrayList<>();

    /**
     * Number of the players that are still alive.
     */
    private int alivePlayers;

    /**
     * Boolean that indicates if the game is over or not,
     * True if the game is finished,
     * False if the game is not finished.
     */
    private boolean gameOver;

    /**
     * String that represent the error message related to each possible wrong move condition.
     */
    private String error;

    /**
     * Boolean that represent the validity of a move.
     * true if the move is valid,
     * false if the move is not valid.
     */
    private boolean validity = true;


    // Constructor

    /**
     * Constructor of the class.
     * It also calls definePlayers.
     * @param playerNumber number of the players that play the game
     * @see GameFirstReleaseTest#definePlayers(int)
     */
    private int playerNumber;

    public GameFirstReleaseTest(int playerNum){
        alivePlayers = playerNum;
        playerNumber=playerNum;
        definePlayers(playerNumber);
    }


    // Getters and Setters

    /**
     * @return {@link GameFirstReleaseTest#boardFirstReleaseTests}
     */
    public BoardFirstReleaseTests getBoardFirstReleaseTests(){ return boardFirstReleaseTests; }

    /**
     * @return {@link GameFirstReleaseTest#players}
     */
    public List<Player> getPlayers(){
        return players;
    }

    /**
     * @return {@link GameFirstReleaseTest#gameOver}
     */
    public boolean getGameOver(){
        return gameOver;
    }

    /**
     * @return {@link GameFirstReleaseTest#validity}
     */
    public boolean getValidity(){
        return validity;
    }

    /**
     * @return {@link GameFirstReleaseTest#error}
     */
    public String getError(){
        return error;
    }

    /**
     * @return alivePlayers {@link GameFirstReleaseTest#alivePlayers}
     */
    public int getAlivePlayers(){
        return alivePlayers;
    }

    public void setMovesList(ArrayList<Move> moves){
        this.movesList = moves;
    }

    /**
     * @return error
     */
    public String getMoveNotValid(){
        return MOVE_NOT_VALID;
    }

    // Methods

    /**
     * It instantiate a {@link Player} and adds it in {@link GameFirstReleaseTest#players}
     * @param playerNumber number of the players that play the game
     */
    private void definePlayers(int playerNumber){
        for (int i=1; i<=playerNumber; i++){
                players.add(new Player(i));
        }
        iterator = players.listIterator();
    }

    /**
     * Check the life status of all the players next to the player's id input, until the iterator reaches the current id player again.
     * @param newBeadsPosition {@link BoardFirstReleaseTests#newBeadsPosition(String, String)}
     */
    private void checkLives(String newBeadsPosition) {
        for (int i=0;i<playerNumber;i++){
            checkLife(newBeadsPosition, currentMovingPlayer.getId());
            iteratorNext();
        }
    }

    /**
     * Checks in the beadsStatus input if the player associated with the id input has beads in it.
     * @param beadsStatus return of {@link BoardFirstReleaseTests#newBeadsPosition(String, String)}
     * @param id {@link Player#id}
     */
    private void checkLife(String beadsStatus, String id) {
        if (currentMovingPlayer.getStatus()){
            int i = 0;
            while(!beadsStatus.substring(i, i+1).equals(id) && i < (beadsStatus.length()-1)){
                i++;
            }
            if (i == beadsStatus.length()-1 && !beadsStatus.substring(i, i+1).equals(id)){
                currentMovingPlayer.setStatus(false);
                System.out.println("player " + id + " is dead");
                alivePlayers--;
                checkVictory();
            }
        }
        checkVictory();
    }

    /**
     * Checks if the game is over.
     */
    private void checkVictory() {
        if (alivePlayers == 1){ //The game ends when only one player is alive
            System.out.println("GG");
            gameOver = true;}
    } // If a player moves, eliminates all the players and dies, he will results as the winner thanks to checkLives method's rules.


    /**
     * Selects the next indexed element in {@link GameFirstReleaseTest#players}.
     * If the {@link GameFirstReleaseTest#iterator} arrives to the end of the array, it restarts from the first element.
     */
    private void iteratorNext(){
        if(!iterator.hasNext())
            iterator = players.listIterator();
        currentMovingPlayer = iterator.next();
    }

    /**
     * Verify if the input move sets the selected bar in an allowed position.
     * @param move String that represents the move to check {@link Move#moveId}
     * @return {@link GameFirstReleaseTest#validity}
     */
    public boolean checkBoundsValidity(String move) {

        char orientation = move.charAt(0); // Orientation of the bar: vertical (v) or horizontal (h)
        int number = Character.getNumericValue(move.charAt(1))-1; //Number of the bar
        char movement = move.charAt(2); //Slide movement of the bar: inward (i) or outward (o)

        /*
          The first three if conditions check that the move is sintactically correct
          the orientation can be only "h" or "v"
          the number can be between 0 and 6
          the movement can be only "o" or "i"
         */
        if ((orientation == 'h') || (orientation == 'v')){
            if((number >= 0) && (number <= 6)){
                if((movement == 'o') || (movement == 'i')){

                    // Now it checks all possible combinations
                    if (orientation == 'h') {
                        if (movement == 'o') {
                            // Horizontal + Outward
                            if(boardFirstReleaseTests.getHorizontalBarPosition(number) == 2){
                                validity = false;
                                error = "the horizontal bar number "+(number+1)+" can not be pushed out";}
                        }
                        else if (movement == 'i') {
                            //Horizontal + Inward
                            if(boardFirstReleaseTests.getHorizontalBarPosition(number) == 0){
                                validity = false;
                                error = "the horizontal bar number "+(number+1)+" can not be pushed in";}
                        }
                    }
                    else if (orientation == 'v') {
                        if (movement == 'o') {
                            //Vertical + Outward
                            if (boardFirstReleaseTests.getVerticalBarPosition(number) == 2){
                                error = "the vertical bar number "+(number+1)+" can not be pushed out";
                                validity = false;}
                        }
                        else if (movement == 'i') {
                            //Vertical + Inward
                            if (boardFirstReleaseTests.getVerticalBarPosition(number) == 0){
                                error = "the vertical bar number "+(number+1)+" can not be pushed in";
                                validity = false;}
                        }
                    }}
                // End of the possible valid moves combinations.
                else{
                    error = "invalid input, the third character must be 'o' or 'i'";
                    validity = false;
                }
            }else{
                error = "invalid input, the second character must be a number between 1 and 7.";
                validity = false;
            }
        }else{
            error = "invalid input, the first character must be 'h' or 'v'";
            validity = false;
        }
        // Each of the previous "else" condition outputs the invalidity message
        // relative to the "move" character checked by its "if" condition
        return validity;
    }

    /**
     * Checks that the current player is not moving the same bar
     * that has been already moved by a different player in the same turn.
     *
     * @param moveToCheck The {@link Move} that has to be checked
     * @return {@link GameFirstReleaseTest#validity}
     *
     */
    public boolean checkMove(Move moveToCheck){
        int movesToCheck = players.size()-1;
        /*
            The first two "if" conditions check that the ArrayList of the moves is not empty
            and that its size is greater than the number of moves to check.
            If those are true, starting from the end of the list, it check that
            the moveId of the last moves is not equal to the moveToCheck.
         */
        if (!movesList.isEmpty()){
            if (movesList.size() > movesToCheck){
                for (int i=movesList.size(); i>movesList.size()-movesToCheck; i--){
                    if (movesList.get(i-1).getMoveId().substring(0, 2).equals(moveToCheck.getMoveId().substring(0, 2))){
                        error = "a player cannot move a bar already moved in this turn";
                        return validity = false;
                    }
                }
            /*
                If the second "if" condition is false the cycle starts from the beginning of the list
                and go through all the moves already done.
             */
            }else{
                for(int i=0; i<movesList.size(); i++){
                    if (movesList.get(i).getMoveId().substring(0, 2).equals(moveToCheck.getMoveId().substring(0, 2))){
                        return validity = false;
                    }
                }
            }
        }
        return validity;
    }

    /**
     * Checks the extra rule for two players.
     * When only two players are left, a player
     * cannot slide the same bar for more than two
     * consecutive turns.
     *
     * @param moveToCheck {@link Move#moveId}
     * @return {@link GameFirstReleaseTest#validity}
     */
    public boolean checkMoveTwoPlayers(Move moveToCheck){

        int sizeL = movesList.size();
        int contMoves = 0; //counts the move made by the same player

        if (players.get(Integer.parseInt(moveToCheck.getPlayerId())-1).getMovesNumber() >= 2){
            /*
                While the moves checked are less than two,
                it checks that the id of the player is the same,
                and then that the id of the move is equal.
             */
            while (contMoves < 2){
                if (movesList.get(sizeL-1).getPlayerId().equals(moveToCheck.getPlayerId())){
                    if(movesList.get(sizeL-1).getMoveId().substring(0, 2).equals(moveToCheck.getMoveId().substring(0, 2))){
                        sizeL--; // Decrease the size to scan all the List backward
                        contMoves++; //If it has checked a move increase the variable
                    }else{
                        return validity;
                    }
                }else{
                    sizeL--; // Decrease the size to scan all the List backward
                }
            }
            error = "a player cannot move for more than two turns the same bar when two players remain";
            return validity = false;
        }
        return validity;
    }

    /**
     * Calls the methods that check different rules and decides if the moves is valid or not
     * @param moveCheck {@link Move}
     * @return {@link GameFirstReleaseTest#validity}
     */
    public boolean generalMoveCheck(Move moveCheck){

        validity = checkBoundsValidity(moveCheck.getMoveId()) && checkMove(moveCheck);

        if( alivePlayers == 2){
            boolean validityFlagTwo = checkMoveTwoPlayers(moveCheck);

            return validity && validityFlagTwo;
        }

        return validity;
    }

    /**
     * Manages the relation between a move and the player doing it.
     * @param move {@link Move#moveId}
     * @param beadsStatus {@link BoardFirstReleaseTests#newBeadsPosition(String, String)}
     * @return the player who executed the input move or an error if the move is not valid
     */
    public String currentPlayer(String move, String beadsStatus){

        if (!gameOver){
            iteratorNext(); //goes to the player who has to move
            if(currentMovingPlayer.getStatus()){ //if he is alive
                Move moveToCheck = new Move(move, currentMovingPlayer.getId());
                // the validity of the input move is checked
                if(generalMoveCheck(moveToCheck)){ //if the move is valid
                    currentMovingPlayer.makeMove(moveToCheck.getMoveId());
                    System.out.println("the move > " + move + " < is valid");//the player makes the move
                    System.out.println("giocatore che muove " + currentMovingPlayer.getId());
                    currentMovingPlayer.setMovesNumber(currentMovingPlayer.getMovesNumber() + 1); //increases the number of moves made by him
                    checkLives(boardFirstReleaseTests.newBeadsPosition(boardFirstReleaseTests.checkGrid(), beadsStatus)); //checks the life status of the other player
                    movesList.add(moveToCheck); //adds the move to the list of completed moves

                    if (movesFinished && !gameOver){
                       return getNextPlayer();
                    }
                    if (gameOver){
                        return currentMovingPlayer.getId();
                    }

                    if (!gameOver){
                        return currentMovingPlayer.getId();

                    }else{
                        System.out.println("gg");
                        return lastPlayer;
                    }
                }else{

                    return MOVE_NOT_VALID; //if the move is not valid, the validation method will return the correct error
                }
            }else{
                Move deadMove = new Move("RIP", currentMovingPlayer.getId());
                movesList.add(deadMove); //if the player iterated is dead it inserts a mock move in the moves list
                return currentPlayer(move, beadsStatus); //and reiterates itself
            }
        }else{
            iteratorNext(); //if the game is ended, the "moving player" set in the final configuration is the one who won
            return currentMovingPlayer.getId();
        }
    }

    /**
     * Returns the player able to do the next move.
     * @return {@link Player#id}
     */



    public void setFirstPlayer(String firstPlayer){
        if (firstPlayer.equals("2")) {
            iteratorNext();
        }
        if (firstPlayer.equals("3")){
            iteratorNext();
            iteratorNext();
        }
        if (firstPlayer.equals("4")){
            iteratorNext();
            iteratorNext();
            iteratorNext();}
        }

    public String getNextPlayer(){
        iteratorNext();
        while (!currentMovingPlayer.getStatus()){
            iteratorNext();
        }
        return currentMovingPlayer.getId();
    }

    public void setMovesFinished(boolean b) {
        movesFinished=b;
    }
}