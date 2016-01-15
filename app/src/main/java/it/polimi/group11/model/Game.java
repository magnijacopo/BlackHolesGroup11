package it.polimi.group11.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Lale on 02/01/2016.
 */
public class Game {

    public Board board = new Board();
    int alivePlayers;
    private int playersNumber;
    private Player currentMovingPlayer;
    private ListIterator<Player> iterator;
    public List<Player> players = new ArrayList<>();
    private int firstPlayer=0;
    private boolean gameOver=false;
    private String lastPlayer;
    private boolean validity=true;
    private boolean movesFinished=false;
    private ArrayList<Move> movesList = new ArrayList<>();
    private String error="";
    private String MOVE_NOT_VALID = "error: la mossa non è valida";
    private String movingPlayer;
    private int totalBeadsInBoard;
    private int numTotalMoves;

    public void randomFirstPlayer(){
        firstPlayer = (int) Math.floor((Math.random() * playersNumber) + 1);
        setFirstPlayer(String.valueOf(firstPlayer));
    }

    public String getFirstPlayer(){
        return getCurrentPlayer();
    }

    public String getError(){
        return error;
    }
    public boolean getValidity(){
        return validity;
    }


    public Game(int playerNum){
        alivePlayers = playerNum;
        playersNumber=playerNum;
        definePlayers(playersNumber);
        for(int i=0;i<playerNum;i++) {
            this.getCurrentMovingPlayer().setGame(this);
            this.getCurrentMovingPlayer().setBoard(board);
            iteratorNext();
        }
        setNumMovesTotal(0);
        totalBeadsInBoard = 0;
    }

    public int getPlayerNum(){
        return playersNumber;
    }

    public void checkRowBeadsLife(int row) {
        iteratorNext();
        row=row-1;
        for (int i = 0; i < playersNumber; i++) {//per tutti i giocatori
           if(alivePlayers>1) {//se c'è più di un giocatore vivo (partita non finita)
               if (currentMovingPlayer.getStatus()) {          //se è vivo il giocatore
                   for (int j = 0; j < 5; j++) {                   // per tutti i bead del giocatore
                       if (currentMovingPlayer.getBead(j).getLife()) {             //e il bead e vivo
                           if (currentMovingPlayer.getBead(j).getRowPosition() == row) {    //guarda se il bead è nella barra mossa
                               for (int k = 0; k < 7; k++) {      //per tutte le celle di quella riga
                                   if (!board.getCell(row, k).getBead() && currentMovingPlayer.getBead(j).getColumnPosition() == k) {  //verifica se la cella è bucata e ha su un bead
                                       currentMovingPlayer.getBead(j).setLife(false); //se è così, lo uccidi.
                                       currentMovingPlayer.setBeadsInBoard(currentMovingPlayer.getBeadsInBoard() - 1);//decremento num bead del player
                                       if (currentMovingPlayer.getBeadsInBoard() == 0) {
                                           currentMovingPlayer.setStatus(false);
                                           alivePlayers--;
                                       }
                                   }
                               }
                           }
                       }
                   }
               }
           }
            iteratorNext();
        }
        iteratorPrevious(playersNumber);
        if (alivePlayers==1){
            gameOver=true;
        }

    }

    public void checkColumnBeadsLife(int column) {
        iteratorNext();
        column=column-1;
        for (int i = 0; i < playersNumber; i++) {   //per tutti i giocatori
            if(alivePlayers>1) {
                if (currentMovingPlayer.getStatus()) {          //se è vivo il giocatore
                    for (int j = 0; j < 5; j++) {                   // per tutti i bead del giocatore
                        if (currentMovingPlayer.getBead(j).getLife()) {             //e il bead e vivo
                            if (currentMovingPlayer.getBead(j).getColumnPosition() == column) {    //guarda se il bead è nella barra mossa
                                for (int k = 0; k < 7; k++) {      //per tutte le celle di quella riga
                                    if (!board.getCell(k, column).getBead() && currentMovingPlayer.getBead(j).getRowPosition() == k) {  //verifica se la cella è bucata e ha su un bead
                                        currentMovingPlayer.getBead(j).setLife(false); //se è così, lo uccidi.
                                        currentMovingPlayer.setBeadsInBoard(currentMovingPlayer.getBeadsInBoard() - 1);
                                        if (currentMovingPlayer.getBeadsInBoard() == 0) {
                                            currentMovingPlayer.setStatus(false);
                                            alivePlayers--;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            iteratorNext();
        }
        iteratorPrevious(playersNumber);
        if (alivePlayers==1){
            gameOver=true;
        }
    }

    public void iteratorNext(){
        if(!iterator.hasNext())
            iterator = players.listIterator();
        currentMovingPlayer = iterator.next();
    }

    private void definePlayers(int playerNumber) {
        for (int i = 1; i <= playerNumber; i++) {
            players.add(new Player(i));
        }
        iterator = players.listIterator();
        currentMovingPlayer = iterator.next();
    }

    private void checkLives(String newBeadsPosition) {
        for (int i=0;i<playersNumber;i++){
            checkLife(newBeadsPosition, currentMovingPlayer.getId());
            iteratorNext();
        }
    }

    private void checkLife(String beadsStatus, String id) {
        if (currentMovingPlayer.getStatus()){
            int i = 0;
            while(!beadsStatus.substring(i, i+1).equals(id) && i < (beadsStatus.length()-1)){
                i++;
            }
            if (i == beadsStatus.length()-1 && !beadsStatus.substring(i, i+1).equals(id)){
                currentMovingPlayer.setStatus(false);
                System.out.println("player "+id+" is dead");
                alivePlayers--;
                checkVictory();
            }
        }
        checkVictory();
    }

    private void checkVictory() {
        if (alivePlayers == 1){ //The game ends when only one player is alive
            System.out.println("GG");
            gameOver = true;}
    }

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
                    Log.i("Game","for");
                    if (movesList.get(i).getMoveId().substring(0, 2).equals(moveToCheck.getMoveId().substring(0, 2))){
                        Log.i("Game","if");
                        return validity = false;
                    }
                }
            }
        }
        return validity;
    }


    public boolean generalMoveCheck(Move moveCheck){

        validity = board.checkBoundsValidity(moveCheck.getMoveId()) && checkMove(moveCheck);

        if( alivePlayers == 2){
            boolean validityFlagTwo = checkMoveTwoPlayers(moveCheck);

            return validity && validityFlagTwo;
        }
        return validity;
    }

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
            iteratorNext();
        }
    }

    public String getNextPlayer(){
        iteratorNext();
        while (!currentMovingPlayer.getStatus()){
            iteratorNext();
        }
        return currentMovingPlayer.getId();
    }

    public String currentPlayer(String move){

        if (!gameOver){ //goes to the player who has to move
            if(currentMovingPlayer.getStatus()){ //if he is alive
                Move moveToCheck = new Move(move, currentMovingPlayer.getId());
                // the validity of the input move is checked
                if(generalMoveCheck(moveToCheck)){ //if the move is valid
                    board.moveBar(moveToCheck.getMoveId());
                    System.out.println("the move > " + move + " < is valid");//the player makes the move
                    System.out.println("giocatore che muove " + currentMovingPlayer.getId());
                    currentMovingPlayer.setMovesNumber(currentMovingPlayer.getMovesNumber() + 1); //increases the number of moves made by him
                    checkLives(board.getCurrentBeadsPosition()); //checks the life status of the other player
                    movesList.add(moveToCheck); //adds the move to the list of completed moves
                    iteratorNext();
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
                return currentPlayer(move); //and reiterates itself
            }
        }else{
            iteratorNext(); //if the game is ended, the "moving player" set in the final configuration is the one who won
            return currentMovingPlayer.getId();
        }
    }

    public String getCurrentPlayer(){
        return currentMovingPlayer.getId();
    }



    public Player getCurrentMovingPlayer() {
        return currentMovingPlayer;
    }


    public int getTotalBeadsInBoard(){
        return totalBeadsInBoard;
    }
    public void setTotalBeadsInBoard(int totalBeadsInBoard){
        this.totalBeadsInBoard=totalBeadsInBoard;
    }

    public void iteratorPrevious(int numPlayers){
        for (int i=0;i<numPlayers-1;i++)
            iteratorNext();
    }

    public void setValidity(boolean validity) {
        this.validity = validity;
    }

    public ArrayList<Move> getMovesList() {
        return movesList;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public int getNumMovesTotal() {
        return numTotalMoves;
    }

    public void setNumMovesTotal(int numMovesTotal) {
        this.numTotalMoves = numMovesTotal;
    }

}
