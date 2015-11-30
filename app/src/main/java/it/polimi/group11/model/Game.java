package it.polimi.group11.model;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


/**
 * Current game session
 */

public class Game {

    /**
     * The game is paired with the board instantiated in this game session.
     */
    Board board = Board.getInstance();

    /**
     * List of the players that will play this game.
     * @see Player
     */
    private List<Player> players = new ArrayList<>();

    /**
     * Iterator for iterate the ArrayList players
     */
    private ListIterator<Player> iterator;

    /**
     * It is the player that should be the next to move the bars.
     * @see Player
     */
    private Player nextMoving;

    /**
     * ArrayList with the list of the moves done by the players.
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
     * String that represent the error message related to some error during the game.
     */
    private String error;

    /**
     * Boolean that represent the validity of the moves,
     * True if the move is valid,
     * False if the move is not valid.
     */
    private boolean validity = true;


    // Constructor

    /**
     * Constructor of the class
     * @param playerNumber number of the players that play the game
     */
    public Game(int playerNumber){
        alivePlayers = playerNumber;
        definePlayers(playerNumber);
    }


    // Getters and Setters

    /**
     *
     * @return {@link Game#players}
     */
    public List<Player> getPlayers(){
        return players;
    }

    /**
     *
     * @return {@link Game#gameOver}
     */
    public boolean getGameOver(){
        return gameOver;
    }

    /**
     *
     * @return {@link Game#validity}
     */
    public boolean getValidity(){
        return validity;
    }

    /**
     *
     * @return {@link Game#error}
     */
    public String getError(){
        return error;
    }

    /**
     *
     * @return alivePlayers {@link Game#alivePlayers}
     */
    public int getAlivePlayers(){
        return alivePlayers;
    }


    // Methods

    /**
     * It adds the player in the ArrayList of the players.
     * @param playerNumber number of the players that play the game
     * @see Game#players
     */
    private void definePlayers(int playerNumber){
        for (int i=1; i<=playerNumber; i++){
                players.add(new Player(i));
        }
        iterator = players.listIterator();
    }

    /**
     *
     * @param newBeadsPosition {}
     * @param id
     */
    private void checkLives(String newBeadsPosition, String id) {
        iteratorNext();
        while(!id.equals(nextMoving.getId())){
            checkLife(newBeadsPosition, nextMoving.getId());
            iteratorNext();
        }
    }

    /**
     *
     * @param beadsStatus
     * @param id
     */
    private void checkLife(String beadsStatus, String id) {
        if (nextMoving.getStatus()){
            int i = 0;
            while(!beadsStatus.substring(i, i+1).equals(id) && i < (beadsStatus.length()-1)){
                i++;
            }
            if (i == beadsStatus.length()-1 && !beadsStatus.substring(i, i+1).equals(id)){
                nextMoving.setStatus(false);
                System.out.println("player "+id+" is dead");
                alivePlayers--;
                checkVictory();
            }
        }
        checkVictory();
    }

    /**
     * It checks if the game is over.
     * The game ends when only one player is alive.
     */
    private void checkVictory() {
        if (alivePlayers == 1)
            gameOver = true;
    }


    /**
     * ??
     */
    private void iteratorNext(){
        if(!iterator.hasNext())
            iterator = players.listIterator();
        nextMoving = iterator.next();
    }

    /**
     *
     * @param move String that represent the move to check {@link Move#moveId}
     * @return validity {@link Game#validity}
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
                            if(board.getHorizontalBarPosition(number) == 2){
                                validity = false;
                                error = "the horizontal bar number "+(number+1)+" can not be pushed out";}
                        }
                        else if (movement == 'i') {
                            //Horizontal + Inward
                            if(board.getHorizontalBarPosition(number) == 0){
                                validity = false;
                                error = "the horizontal bar number "+(number+1)+" can not be pushed in";}
                        }
                    }
                    else if (orientation == 'v') {
                        if (movement == 'o') {
                            //Vertical + Outward
                            if (board.getVerticalBarPosition(number) == 2){
                                error = "the vertical bar number "+(number+1)+" can not be pushed out";
                                validity = false;}
                        }
                        else if (movement == 'i') {
                            //Vertical + Inward
                            if (board.getVerticalBarPosition(number) == 0){
                                error = "the vertical bar number "+(number+1)+" can not be pushed in";
                                validity = false;}
                        }
                    }}
                // End of the possible combination. Else for possible error in the input
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
        return validity;
    }

    /**
     * It checks that the player is not moving the same bar
     * that is been already moved by a different player in the same turn.
     *
     * @param moveToCheck The move that has to be checked {@link Move}
     * @return {@link Game#validity}
     *
     */
    public boolean checkMove(Move moveToCheck){

        int movesToCheck = players.size()-1;
        /*
            The first two if check that the list of the moves is not empty
            and that its size is greater than the number of moves to check.
            If it's true starting from the end of the list it check that
            the movesId of the last moves is not equal to the moveToCheck.
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
                If the two if are false it starts from the beginning of the list
                and go through all the moves already done.
             */
            }else{
                for(int i=0; i<movesList.size(); i++){
                    if (movesList.get(i).getMoveId().substring(0, 2).equals(moveToCheck.getMoveId().substring(0, 2))){
                        error = "a player cannot move a bar already moved in this turn";
                        return validity = false;
                    }
                }
            }
        }
        /*
            DA CREARE UN METODO A PARTE GENERALCHECK CHE CHIAMA PRIMA CHECKMOVE E CHE POI CHECKMOVE PER DUE PLAYER!

            Checks the number of player alive, if they are two call the method for check the extra rule
         */
        if (alivePlayers == 2){
            if(!checkMoveTwoPlayers(moveToCheck)){
                error = "when two players remain, a player cannot move the same bar for more than two consecutive turns";
                return validity = false;
            }
        }
        return validity;
    }

    /**
     * This method checks the extra rule for two players.
     * When only two players are left, a player
     * cannot slide the same bar for more than two
     * consecutive turns.
     *
     *
     * @param moveToCheck {@link Move#moveId}
     * @return {@link Game#validity}
     */
    public boolean checkMoveTwoPlayers(Move moveToCheck){

        int sizeL = movesList.size(); //
        int contMoves = 0; // contMoves count the move made by the same player

        if (players.get(Integer.parseInt(moveToCheck.getPlayerId())-1).getMovesNumber() >= 2){
            /*
                While the moves checked are less than two
                it check that the id of the player it is the same,
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
            return validity = false;
        }
        return validity;
    }

    /**
     *
     * @param move
     * @param beadsStatus
     * @return
     */
    public String nextPlayer(String move, String beadsStatus){
        if (!gameOver){
            iteratorNext();
            if(nextMoving.getStatus()){
                Move moveToCheck = new Move(move, nextMoving.getId());
                if(checkMove(moveToCheck) && checkBoundsValidity(moveToCheck.getMoveId())){
                    nextMoving.makeMove(moveToCheck.getMoveId());
                    nextMoving.setMovesNumber(nextMoving.getMovesNumber()+1);
                    checkLives(board.newBeadsPosition(board.checkGrid(), beadsStatus), nextMoving.getId());
                    System.out.println("the move > "+move+" < is valid");
                    movesList.add(moveToCheck);
                    return nextMoving.getId();
                }else{
                    return null;
                }
            }else{
                Move deadMove = new Move("RIP", nextMoving.getId());
                movesList.add(deadMove);
                return nextPlayer(move, beadsStatus);
            }
        }else{
            iteratorNext();
            System.out.println("player "+nextMoving.getName()+" won the match!");
            return nextMoving.getId();
        }
    }

    /**
     *
     * @return
     */
    public String getLastPlayer(){
        iteratorNext();
        while (!nextMoving.getStatus()){
            iteratorNext();
        }
        return nextMoving.getId();
    }
}