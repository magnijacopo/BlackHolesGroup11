package it.polimi.group11.model;

/**
 * Created by Lale on 13/11/2015.
 */
public class Board {

    private boolean[][] VERTICAL = new boolean[7][9]={
            {true,},
            {},
            {},
            {},
            {},
            {},
            {},
    };

    private boolean[][] HORIZONTAL= new boolean[7][9];

    private Position[] verticalPosition;

    private Position[] horizontal;

    private Cell[][] grid;

    private Match match;

    private Cell cell;

    private Match match;

    private GameComands gameComands;

}

