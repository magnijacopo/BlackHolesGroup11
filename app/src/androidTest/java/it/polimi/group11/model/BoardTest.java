package it.polimi.group11.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by aless on 30/11/2015.
 */
public class BoardTest {

    @Test
    public void testCheckGrid() throws Exception {
        Board board=new Board();
        int set[]={0,0,0,0,0,0,0};
        board.setHorizontalBarsPosition(set);
        board.setVerticalBarsPosition(set);
        String grid = board.checkGrid();
        String expectedGrid="" +
                "3232323" +
                "1001221" +
                "1020100" +
                "1012103" +
                "1202000" +
                "3320210" +
                "1001012";
        assertEquals(expectedGrid,grid);
    }

    @Test
    public void testNewBeadsPosition() throws Exception {
        Board board = new Board();
        String currentBeadsPosition="" +
                "0000001" +
                "0000200" +
                "0000000" +
                "0000000" +
                "0001000" +
                "0100000" +
                "0000200";
        String grid="" +
                "0022330" +
                "2001100" +
                "1200230" +
                "1222000" +
                "1120023" +
                "2231100" +
                "2331100";
        String expectedBeads="" +
                "0000000" +
                "0000200" +
                "0000000" +
                "0000000" +
                "0000000" +
                "0100000" +
                "0000200";
        String nextBeadsPosition;
        nextBeadsPosition = board.newBeadsPosition(grid, currentBeadsPosition);
        assertEquals(expectedBeads,nextBeadsPosition);
    }
}