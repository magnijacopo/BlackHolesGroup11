package it.polimi.group11.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

    //given a chosen configuration, the board grid should updates itself with the correct holes and fills values
    @Test
    public void testCheckGrid() throws Exception {
        BoardFirstReleaseTests boardFirstReleaseTests =new BoardFirstReleaseTests();
        int set[]={0,0,0,0,0,0,0};
        boardFirstReleaseTests.setHorizontalBarsPosition(set);
        boardFirstReleaseTests.setVerticalBarsPosition(set);
        boardFirstReleaseTests.prepareGrid();
        String grid= boardFirstReleaseTests.checkGrid();
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

    //given an updated grid holes distribution and a beads configuration the method should returns the new beads configuration
    @Test
    public void testNewBeadsPosition() throws Exception {
        BoardFirstReleaseTests boardFirstReleaseTests = new BoardFirstReleaseTests();
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
        nextBeadsPosition = boardFirstReleaseTests.newBeadsPosition(grid, currentBeadsPosition);
        assertEquals(expectedBeads,nextBeadsPosition);
    }
}