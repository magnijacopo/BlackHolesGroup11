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
    @Test
    public void testCheckBoundsValidity_ReturnFalse() throws Exception {
        Game game=new Game(3);
        game.getBoard().setHorizontalBarPosition(2, 0);
        assertFalse(game.checkBoundsValidity("h1o"));
    }

    @Test
    public void testCheckBoundsValidity_ReturnTrue() throws Exception {
        Game game=new Game(3);
        game.getBoard().setHorizontalBarPosition(1, 6);
        assertTrue(game.checkBoundsValidity("h7i"));
    }

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
    @Test
    public void testCheckMoveTwoPlayers_ReturnTrue() throws Exception {
        Game gameTest = new Game(2);
        ArrayList<Move> movesList = new ArrayList<>();
        Move move0 = new Move("h3i", "1");
        Move move1 = new Move("h7o", "2");
        Move move2 = new Move("h2i", "1");
        Move move3 = new Move("v6o", "2");
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
        Move moveTest = new Move("h4i", "2");
        assertTrue(gameTest.checkMoveTwoPlayers(moveTest));
    }
    @Test
    public void testNextPlayer_ValidMove() throws Exception {
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

    @Test
    public void testCurrentPlayer_GameOver() throws Exception {
        Game gameTest = new Game(3);
        gameTest.getBoard();
        Move moveTest = new Move ("h4o", "1");
        String beadsTest = "0000000"
                + "0000000"
                + "0000000"
                + "0000000"
                + "0000000"
                + "0000000"
                + "0000000";
        assertEquals("1", gameTest.currentPlayer(moveTest.getMoveId(), beadsTest));
    }
}