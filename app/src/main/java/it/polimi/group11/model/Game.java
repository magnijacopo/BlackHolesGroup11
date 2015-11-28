package it.polimi.group11.model;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Game {
    Board board = Board.getInstance();

    private List<Player> players = new ArrayList<Player>();
    private ListIterator<Player> iterator;
    private Player nextMoving;

    /*private final int MAXQUEUEDMOVES = 4;
    private String[] lastMoves = {"xx","xx","xx","xx"};*/
    private ArrayList<Move> movesList = new ArrayList<Move>();

    private int alivePlayers;
    //private int turnToCheck;

    private boolean gameOver;

    private String error = new String();
    private boolean validity = true;

    public Game(int playerNumber){
        alivePlayers = playerNumber;
        definePlayers(playerNumber);
    }

    public List<Player> getPlayers(){
        return players;
    }

    private void definePlayers(int playerNumber){
        for (int i=1; i<=playerNumber; i++){
            if(players.size() < 4){
                players.add(new Player(i));
            }
        }
        iterator = players.listIterator();
    }

	/*public String nextPlayer(String move, String beadsStatus){
		if(!gameOver){
			Board.getInstance(); //what?
			if (checkMove(move) && checkBoundsValidity(move)){
				iteratorNext();
				checkLife(beadsStatus, nextMoving.getId()); //preliminary life check in order to control if the input configuration string has a number of players not corresponding to the beads set
				if (nextMoving.getStatus()){
					nextMoving.makeMove(move);
					checkLives(board.newBeadsPosition(board.checkGrid(), beadsStatus), nextMoving.getId());
					System.out.println("the move > "+move+" < is valid");
					updateLastMoves(move);
					return nextMoving.getId();
				}else{
					return nextPlayer(move, beadsStatus);//the next allowed player makes the input move
				} 
			}else{
				iteratorCurrent(); //se la mossa di un giocatore non è valida, la rifa
				System.out.println("the move > "+move+" < is not valid");
				return nextMoving.getId();
			}
		}else{
			iteratorNext();
			System.out.println("GG");
			return nextMoving.getId();
		}
	}*/

    private void checkLives(String newBeadsPosition, String id) {
        iteratorNext();
        while(id != nextMoving.getId()){
            checkLife(newBeadsPosition, nextMoving.getId());
            iteratorNext();
        }
    }

    private void checkLife(String beadsStatus, String id) {
        if (nextMoving.getStatus()){
            int i = 0;
            while(!beadsStatus.substring(i, i+1).equals(id) && i < (beadsStatus.length()-1)){
                i++;
            }
            if (i == beadsStatus.length()-1){
                nextMoving.setStatus(false);
                System.out.println("player "+id+" is dead");
                alivePlayers--;
                checkVictory();
            }
        }
        checkVictory();
    }

    private void checkVictory() {
        if (alivePlayers == 1)
            gameOver = true;
    }

    public boolean getGameOver(){
        return gameOver;
    }

    private void iteratorNext(){
        if(!iterator.hasNext())
            iterator = players.listIterator();
        nextMoving = iterator.next();
    }

	/*private void iteratorCurrent(){
		if(!iterator.hasNext()){
			iterator.previous();
			nextMoving = iterator.next();
		}else{
			iterator.next();
			nextMoving = iterator.previous();
		}
	}*/

	/*private boolean checkMove(String move){
		turnToCheck = 3;
		while(turnToCheck > (MAXQUEUEDMOVES-alivePlayers)){
			if (!(move.substring(0, 2).equals(lastMoves[turnToCheck].substring(0, 2)))){
				turnToCheck--;
			}else{
				error="normal rule!";
				return false;
			}
		}
		if (alivePlayers == 2){
			if(!checkExtra(move)){
				error="extra rule!";
				return false;
			}else{
				return true;}
		}
		return true;
	}*/

	/*private boolean checkExtra(String move){
		if (!(move.substring(0, 2).equals(lastMoves[2].substring(0, 2))) || (!(move.substring(0, 2).equals(lastMoves[0].substring(0, 2))))){
			return true;
		}else{
			System.out.println("extra rule(only for 2 players): "+move+" is not a valid move");
			return false;	
		}
	}*/

    public int getAlivePlayers(){
        return alivePlayers;
    }

	/*public void updateLastMoves(String move){
		for(int i=0; i<MAXQUEUEDMOVES-1; i++){
			lastMoves[i] = lastMoves[i+1];
		}
		lastMoves[MAXQUEUEDMOVES-1] = move;
	}*/

    public boolean checkBoundsValidity(String move) { //
        char orientation = move.charAt(0); //vertical or horizontal bar
        int number = Character.getNumericValue(move.charAt(1))-1; //number of the bar
        char movement = move.charAt(2); //inward or outward slide movement
        if ((orientation == 'h') || (orientation == 'v')){
            if((number >= 0) && (number <= 6)){
                if((movement == 'o') || (movement == 'i')){
                    if (orientation == 'h') { //horizontal
                        if (movement == 'o') { //outward
                            if(board.getHorizontalBarPosition(number) == 2){
                                validity = false;
                                error = "the horizontal bar number "+(number+1)+" can not be pushed out";}
                        }
                        else if (movement == 'i') { //inward
                            if(board.getHorizontalBarPosition(number) == 0){
                                validity = false;
                                error = "the horizontal bar number "+(number+1)+" can not be pushed in";}
                        }
                    }
                    else if (orientation == 'v') { //vertical
                        if (movement == 'o') {
                            if (board.getVerticalBarPosition(number) == 2){
                                error = "the vertical bar number "+(number+1)+" can not be pushed out";
                                validity = false;}
                        }
                        else if (movement == 'i') {
                            if (board.getVerticalBarPosition(number) == 0){
                                error = "the vertical bar number "+(number+1)+" can not be pushed in";
                                validity = false;}
                        }
                    }}else{
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

    public boolean getValidity(){
        return validity;
    }

    public String getError(){
        return error;
    }

    public ArrayList<Move> getMovesList() {
        return movesList;
    }

    public void setMovesList(ArrayList<Move> movesList) {
        this.movesList = movesList;
    }


    /**
     * BREVE DESCRIZIONE
     *
     * @param moveToCheck mossa da controllare
     * @return BOOLEAN true se la mossa si può fare false se la mossa è sbagliata
     *
     * @see Game#alivePlayers
     * @see Move
     */
    public boolean checkMove(Move moveToCheck){
        int movesToCheck = players.size()-1;
        if (!movesList.isEmpty()){
            if (movesList.size() > movesToCheck){
                for (int i=movesList.size(); i>movesList.size()-movesToCheck; i--){
                    if (movesList.get(i-1).getMoveId().substring(0, 2).equals(moveToCheck.getMoveId().substring(0, 2))){
                        error = "a player cannot move a bar already moved in this turn";
                        validity = false;
                        return false;
                    }
                }
            }else{
                for(int i=0; i<movesList.size(); i++){
                    if (movesList.get(i).getMoveId().substring(0, 2).equals(moveToCheck.getMoveId().substring(0, 2))){
                        error = "a player cannot move a bar already moved in this turn";
                        validity = false;
                        return false;
                    }
                }
            }
        }
        if (alivePlayers == 2){
            if(!checkMoveTwoPlayers(moveToCheck)){
                error = "when two players remain, a player cannot move the same bar for more than two consecutive turns";
                validity = false;
                return false;
            }
        }
        return true;
    }

    public boolean checkMoveTwoPlayers(Move moveToCheck){
        int j = movesList.size();
        int cont = 0;

        if (players.get(Integer.parseInt(moveToCheck.getPlayerId())).getMovesNumber() >= 2){
            while (cont < 2){
                if (movesList.get(j-1).getPlayerId().equals(moveToCheck.getPlayerId())){
                    if(movesList.get(j-1).getMoveId().substring(0, 2).equals(moveToCheck.getMoveId().substring(0, 2))){
                        j--;
                        cont++;
                    }else{
                        return true;
                    }
                }else{
                    j--;
                }
            }
            return false;
        }
        return true;
    }

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
}