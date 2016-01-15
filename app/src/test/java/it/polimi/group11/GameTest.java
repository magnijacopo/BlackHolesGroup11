package it.polimi.group11;

import java.util.ArrayList;

import org.junit.Test;
import static org.junit.Assert.*;

import it.polimi.group11.model.BoardFirstReleaseTests;
import it.polimi.group11.model.GameFirstReleaseTest;
import it.polimi.group11.model.Move;

/**
 * Created by Jacopo Magni on 30/11/2015.
 */
public class GameTest {

    @Test
    public void testCheckBoundsValidity_ReturnFalse() throws Exception {
        BoardFirstReleaseTests boardFirstReleaseTests = new BoardFirstReleaseTests();
        GameFirstReleaseTest gameFirstReleaseTest =new GameFirstReleaseTest(3);
        boardFirstReleaseTests.setHorizontalBarPosition(0, 2);
        assertEquals(false, gameFirstReleaseTest.checkBoundsValidity("h0o"));
    }

    @Test
    public void testCheckMove_ReturnTrue() throws Exception {

        GameFirstReleaseTest gameFirstReleaseTestTest = new GameFirstReleaseTest(3);
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

        gameFirstReleaseTestTest.setMovesList(movesList);

        Move moveToCheck = new Move("h4o", "1");
        assertEquals(true, gameFirstReleaseTestTest.checkMove(moveToCheck));

    }

    @Test
    public void testCheckMove_ReturnFalse() throws Exception {

        GameFirstReleaseTest gameFirstReleaseTestTest = new GameFirstReleaseTest(2);
        ArrayList<Move> movesList = new ArrayList<>();

        Move move0 = new Move("h3i", "1");
        Move move1 = new Move("h7o", "2");
        Move move2 = new Move("v3i", "1");

        movesList.add(0, move0);
        movesList.add(1, move1);
        movesList.add(2, move2);

        gameFirstReleaseTestTest.setMovesList(movesList);

        Move moveToCheck = new Move("v3i", "2");
        assertEquals(false, gameFirstReleaseTestTest.checkMove(moveToCheck));
    }

    @Test
    public void testCheckMoveTwoPlayers_ReturnTrue() throws Exception {

        GameFirstReleaseTest gameFirstReleaseTestTest = new GameFirstReleaseTest(2);
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

        gameFirstReleaseTestTest.setMovesList(movesList);

        Move moveToCheck = new Move("h4i", "2");
        assertEquals(true, gameFirstReleaseTestTest.checkMoveTwoPlayers(moveToCheck));

    }


    @Test
    public void testNextPlayer() throws Exception {



    }
}
