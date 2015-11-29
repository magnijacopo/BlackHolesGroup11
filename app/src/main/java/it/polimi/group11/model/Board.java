package it.polimi.group11.model;

/**
 * Describe the situation of the game board,
 * define the structure of vertical and horizontal bars and their position on the board,
 * finally contains methods to change the setting of the board
 */

public class Board {
    /**
     *  Non mutable holes configuration in the vertical bars
     */
    private final boolean[][] VERTICALHOLES = {
            {true, false, false, false, false, true, false, true, true },
            {true, false, false, false, true, true, false, false, true },
            {true, false, true, false, false, true, false, true, true },
            {true, false, false, true, true, false, false, false, true },
            {true, true, false, false, false, true, false, true, true },
            {true, true, false, false, false, false, false, true, true },
            {true, false, false, true, false, false, true, false, true }
    };

    /**
     *  Non mutable holes configuration in the horizontal bars
     */
    private final boolean[][] HORIZONTALHOLES = { //
            {true, false, true, false, true, false, true, false, true},
            {true, false, false, true, false, false, true, false, true},
            {true, false, false, false, true, false, false, false, true},
            {true, false, true, false, true, false, true, false, true},
            {true, false, false, false, false, false, false, false, true},
            {true, true, false, false, false, true, false, true, true},
            {true, false, false, true, false, true, false, true, true}
    };

    /**
     *  distribution of the vertical bar's holes in the board grid
     */
    private boolean[][] gridY = new boolean[7][7];

    /**
     *  distribution of the horizontal bar's holes in the board grid
     */
    private boolean[][] gridX = new boolean[7][7];

    /**
     *  position of the vertical bars on the board
     */
    private int[] verticalBarsPosition = new int[7];

    /**
     *  position of the horizontal bars on the board
     */
    private int[] horizontalBarsPosition = new int[7];


    // Constructors
    /**
     *
     */
    private static Board board;

    /**
     *  private constructor
     */
    private Board(){}

    /**
     * The board is a singleton.
     *
     * @return it returns an instance of the class Board, if it isn't already been created, {@link Board#board}
      */
    public static Board getInstance(){
        if (board == null){
            board = new Board();
        }
        return board;
    }


    // Getters and Setters

    /**
     * Return the positions all of the horizontal bars.
     *
     * @return {@link Board#horizontalBarsPosition}
     */
    public int[] getHorizontalBarsPosition(){
        return horizontalBarsPosition;
    }

    /**
     * Return the positions all of the vertical bars.
     *
     * @return {@link Board#verticalBarsPosition}
     */
    public int[] getVerticalBarsPosition(){
        return verticalBarsPosition;
    }

    /**
     * Return the position of a specific horizontal bars.
     *
     * @param position the specific bar
     * @return {@link Board#horizontalBarsPosition}
     */
    public int getHorizontalBarPosition(int position){
        return horizontalBarsPosition[position];
    }

    /**
     * Return the position of a specific vertical bars.
     *
     * @param position the specific bar
     * @return {@link Board#horizontalBarsPosition}
     */
    public int getVerticalBarPosition(int position){
        return verticalBarsPosition[position];
    }

    /**
     * Sets the position of the horizontal bars.
     *
     * @param horizontalPosition the position which will take the bar
     * @param position the specific bar
     */
    public void setHorizontalBarPosition(int horizontalPosition, int position){
        this.horizontalBarsPosition[position] = horizontalPosition;
    }

    /**
     * Sets the position of the vertical bars.
     *
     * @param verticalPosition the position which will take the bar
     * @param position the specific bar
     */
    public void setVerticalBarPosition(int verticalPosition, int position){
        this.verticalBarsPosition[position] = verticalPosition;
    }

    /**
     * Sets the position of the horizontal bars.
     *
     * @param horizontalPositions the position which will take the bars
     */
    public void setHorizontalBarsPosition(int[] horizontalPositions){
        this.horizontalBarsPosition = horizontalPositions;
    }

    /**
     * Sets the position of the vertical bars.
     *
     * @param verticalPositions the position which will take the bars
     */
    public void setVerticalBarsPosition(int[] verticalPositions){
        this.verticalBarsPosition = verticalPositions;
    }

    /**
     * Set the position of all the bars in the board's grid.
     *
     * @see Board#setRow(int, int)
     * @see Board#setColumn(int, int)
     */
    public void prepareGrid() {
        for (int i=0; i<7; i++) {
            setRow(i, horizontalBarsPosition[i]);
            setColumn(i, verticalBarsPosition[i]);
        }
    }

    /**
     * Updates the row of the horizontal bar's hole distribution indicated by the input.
     *
     * @param row the row currently considered
     * @param barPosition {@link Board#horizontalBarsPosition}
     * @see Board#gridX
     */
    public void setRow(int row, int barPosition) {
        for (int i=0; i<7; i++) {
            gridX[row][i] = HORIZONTALHOLES[row][i+barPosition];
        }
    }

    /**
     * Updates the column of the vertical bar's holes distribution indicated by the input.
     *
     * @param column the column currently considered
     * @param barPosition {@link Board#verticalBarsPosition}
     * @see Board#gridY
     */
    public void setColumn(int column, int barPosition) {
        for (int i=0; i<7; i++) {
            gridY[column][i] = VERTICALHOLES[column][i+barPosition];
        }
    }


    // Methods

    /**
     * Overlaps the vertical and the horizontal holes distribution grids.
     *
     * @return a string mapping the condition of each cell
     * @see Board#gridX
     * @see Board#gridY
     */
    public String checkGrid() {
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

    /**
     * Updates the position of the beads on the board based on,
     * a configuration of the bars and the latest beads positions inputs.
     *
     * @param grid the return of the method {@link Board#checkGrid()}
     * @param beadsPosition the current positions of the beads
     * @return it returns a string representing the new position of each beads
     */
    public String newBeadsPosition(String grid, String beadsPosition) {
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


}