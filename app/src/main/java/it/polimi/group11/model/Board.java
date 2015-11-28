package it.polimi.group11.model;

public class Board {
    private final boolean[][] VERTICALHOLES = { //non mutable holes configuration in the vertical bars
            {true, false, false, false, false, true, false, true, true },
            {true, false, false, false, true, true, false, false, true },
            {true, false, true, false, false, true, false, true, true },
            {true, false, false, true, true, false, false, false, true },
            {true, true, false, false, false, true, false, true, true },
            {true, true, false, false, false, false, false, true, true },
            {true, false, false, true, false, false, true, false, true }
    };
    private final boolean[][] HORIZONTALHOLES = { //non mutable holes configuration in the horizontal bars
            {true, false, true, false, true, false, true, false, true},
            {true, false, false, true, false, false, true, false, true},
            {true, false, false, false, true, false, false, false, true},
            {true, false, true, false, true, false, true, false, true},
            {true, false, false, false, false, false, false, false, true},
            {true, true, false, false, false, true, false, true, true},
            {true, false, false, true, false, true, false, true, true}
    };

    private boolean[][] gridY = new boolean[7][7]; //distribution of the vertical bar's holes in the board grid
    private boolean[][] gridX = new boolean[7][7]; //distribution of the horizontal bar's holes in the board grid

    private int[] verticalBarsPosition = new int[7]; //position of the vertical bars on the board
    private int[] horizontalBarsPosition = new int[7]; //position of the horizontal bars on the board

    private static Board board;


    private Board(){} //private constructor

    public static Board getInstance(){ //the board is a singleton
        if (board == null){
            board = new Board();
        }
        return board;
    }

    public void prepareGrid() { //set the position of all the bars in the board's grid
        for (int i=0; i<7; i++) {
            setRow(i, horizontalBarsPosition[i]);
            setColumn(i, verticalBarsPosition[i]);
        }
    } //serve veramente questo metodo?

    public void setRow(int row, int barPosition) { //updates the row of the horizontal bar's hole distribution indicated by the input
        for (int i=0; i<7; i++) {
            gridX[row][i] = HORIZONTALHOLES[row][i+barPosition];
        }
    }

    public void setColumn(int column, int barPosition) { //updates the column of the vertical bar's holes distribution indicated by the input
        for (int i=0; i<7; i++) {
            gridY[column][i] = VERTICALHOLES[column][i+barPosition];
        }
    }

    public String checkGrid() { //overlaps the vertical and the horizontal holes distribution grids to output a string mapping the condition of each cell
        String checkGrid = new String();
        String temp = new String();
        for (int i=0; i<7; i++) {
            for (int j=0; j<7; j++) {
                if (gridX[i][j] == true && gridY[j][i] == false){
                    temp = "1"; //the cell checked is covered by a horizontal bar
                }
                else if (gridX[i][j] == false && gridY[j][i] == true){
                    temp = "2"; //the cell checked is covered by a vertical bar
                }
                else if (gridX[i][j] == true && gridY[j][i] == true){
                    temp = "3"; //the cell checked is covered by both bars
                }else{
                    temp = "0"; //the cell checked is a hole
                }
                checkGrid = (checkGrid + temp);
            }
        }
        return checkGrid;
    }

    public String newBeadsPosition(String grid, String beadsPosition) { //updates the position of the beads on the board based on a configuration of the bars and the latest beads positions inputs
        String newBeadsPosition = new String();
        for (int i=0; i<beadsPosition.length(); i++) {
            if (grid.charAt(i) == '0') {
                newBeadsPosition = newBeadsPosition+"0"; //if the cell checked is a hole, updates there can not be a bead on it
            } else {
                newBeadsPosition = newBeadsPosition+beadsPosition.charAt(i); //if the cell checked is filled by a bar, the old bead position valued is maintained
            }
        }
        return newBeadsPosition;
    }

    //getters and setters
    public int[] getHorizontalBarsPosition(){
        return horizontalBarsPosition;
    }

    public int[] getVerticalBarsPosition(){
        return verticalBarsPosition;
    }

    public int getHorizontalBarPosition(int position){
        return horizontalBarsPosition[position];
    }

    public int getVerticalBarPosition(int position){
        return verticalBarsPosition[position];
    }

    public void setHorizontalBarPosition(int horizontalPosition, int position){
        this.horizontalBarsPosition[position] = horizontalPosition;
    }

    public void setVerticalBarPosition(int verticalPosition, int position){
        this.verticalBarsPosition[position] = verticalPosition;
    }

    public void setHorizontalBarsPosition(int[] horizontalPositions){
        this.horizontalBarsPosition = horizontalPositions;
    }

    public void setVerticalBarsPosition(int[] verticalPositions){
        this.verticalBarsPosition = verticalPositions;
    }
}