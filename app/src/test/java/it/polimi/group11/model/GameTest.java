package it.polimi.group11.model;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import it.polimi.group11.model.Board;
import it.polimi.group11.model.Game;
import it.polimi.group11.model.Move;
import it.polimi.group11.model.Player;
/**
 * Created by Jacopo Magni on 30/11/2015.
 */
public class GameTest {

    //a move shouldn't push a bar in outer position
    @Test
    public void testCheckBoundsValidity_ReturnFalse() throws Exception {
        Game game = new Game(3);
        game.getBoard().setHorizontalBarPosition(2, 0);
        assertFalse(game.checkBoundsValidity("h1o"));
    }

    //a move should be able to pull a bar in central position
    @Test
    public void testCheckBoundsValidity_ReturnTrue() throws Exception {
        Game game=new Game(3);
        game.getBoard().setHorizontalBarPosition(1, 6);
        assertTrue(game.checkBoundsValidity("h7i"));
    }

    //a serie of all different moves should be considered valid
    @Test
    public void testCheckMove_ReturnTrue() throws Exception {
        Game gameTest = new Game(3);
        ArrayList<Move> movesList = new ArrayList<>();
        Move move0 = new Move("h3i", "1");
        Move move1 = new Move("h7o", "2");
        Move move2 = new Move("v3i", "3");
        Move move3 = new Move("v5o", "1");
        Move move4 = new Move("h2i", "2");
        Move move5 = new Move("v6i", "3");
        movesList.add(0, move0);
        movesList.add(1, move1);
        movesList.add(2, move2);
        movesList.add(3, move3);
        movesList.add(4, move4);
        movesList.add(5, move5);
        gameTest.setMovesList(movesList);
        Move moveToCheck = new Move("h4o", "1");
        assertEquals(true, gameTest.checkMove(moveToCheck));
    }

    //a move equal to the one before shouldn't be considered valid
    @Test
    public void testCheckMove_ReturnFalse() throws Exception {
        Game gameTest = new Game(2);
        ArrayList<Move> movesList = new ArrayList<>();
        Move move0 = new Move("h3i", "1");
        Move move1 = new Move("h7o", "2");
        Move move2 = new Move("v3i", "1");
        movesList.add(0, move0);
        movesList.add(1, move1);
        movesList.add(2, move2);
        gameTest.setMovesList(movesList);
        Move moveToCheck = new Move("v3i", "2");
        assertEquals(false, gameTest.checkMove(moveToCheck));
    }

    //when two players are alive a bar can be used for two consecutive times
    @Test
    public void testCheckMoveTwoPlayers_ReturnTrue() throws Exception {
        Game gameTest = new Game(2);
        ArrayList<Move> movesList = new ArrayList<>();
        Move move0 = new Move("h3i", "1");
        Move move1 = new Move("h7o", "2");
        Move move2 = new Move("h2i", "1");
        Move move3 = new Move("v3o", "2");
        Move move4 = new Move("h2o", "1");
        Move move5 = new Move("v6i", "2");
        Move move6 = new Move("h7i", "1");
        movesList.add(0, move0);
        movesList.add(1, move1);
        movesList.add(2, move2);
        movesList.add(3, move3);
        movesList.add(4, move4);
        movesList.add(5, move5);
        movesList.add(6, move6);
        gameTest.setMovesList(movesList);
        Move moveTest = new Move("v6o", "2");
        assertTrue(gameTest.checkMoveTwoPlayers(moveTest));
    }

    // if a move is valid the method should return the id of the player associated with the move in the Move instance
    @Test
    public void testCurrentPlayer_ValidMove() throws Exception {
        Game gameTest = new Game(3);
        gameTest.getBoard();
        Move moveTest = new Move ("h4o", "1");
        String beadsTest = "0000000"
                         + "0000000"
                         + "0000000"
                         + "0000000"
                         + "0000000"
                         + "0000000"
                         + "0001020";
        assertEquals("1", gameTest.currentPlayer(moveTest.getMoveId(), beadsTest));
    }

    //if a game has come to an end, the last player to make a move is should be returned
    @Test
    public void testCurrentPlayer_GameOver() throws Exception {
        Game gameTest = new Game(2);
        gameTest.getBoard();
        Move moveTest = new Move ("h7o", "1");
        String beadsTest = "0000000"
                + "0100000"
                + "0000000"
                + "0000000"
                + "0000000"
                + "0000000"
                + "0000020";
        assertEquals("1", gameTest.currentPlayer(moveTest.getMoveId(), beadsTest));
    }
}