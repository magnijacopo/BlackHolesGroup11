package it.polimi.group11.model;

import android.util.Log;


/**
 * Created by Lale on 29/12/2015.
 */
public class Board2 {

    public Bar[] horizontalBar = new Bar[7];
    public Bar[] verticalBar = new Bar[7];
    private Cell[][] grid = new Cell[7][7];
    Composition composition = new Composition();

   public Board2(){
       for(int i=0;i<7;i++) {
           horizontalBar[i] = new Bar();
           verticalBar[i] = new Bar();
           for (int j = 0; j < 7; j++)
               grid[i][j] = new Cell();
       }
       generateBoard();
   }

    private void setHolesOfBars() {
        for (int i = 0; i < 7; i++) {
            horizontalBar[i].setComposition(composition.getHorizontalComposition(i));
            verticalBar[i].setComposition(composition.getVerticalComposition(i));
        }
    }

    private void setInitialPositions() {
        for (int i = 0; i < 7; i++) {
            horizontalBar[i].setPosition(getRandomNumber());
            verticalBar[i].setPosition(getRandomNumber());
        }
    }

    private int getRandomNumber() {
            return (int)(Math.random()*3);
        }

    public void setRow(int row){
        for (int i=0;i<7;i++)
            grid[row][i].setHorizontal(horizontalBar[row].getValue(i + horizontalBar[row].getPosition()));
        updateRowBeadInCellStatus(row);
    }

    public void setColumn(int column){
        for (int i=0;i<7;i++)
            grid[i][column].setVertical(verticalBar[column].getValue(i + verticalBar[column].getPosition()));
        updateColumnBeadInCellStatus(column);
    }

    private void prepareGrid(){
        for (int i=0;i<7;i++) {
        setRow(i);
        setColumn(i);
        }
    }

    public String getCheckGrid() {
        String checkGrid = "";
        String temp="";
        for (int i=0; i<7; i++) {
            for (int j = 0; j < 7; j++) {
                if ((grid[i][j].getHorizontal()) && (grid[i][j].getVertical()))
                    temp = "3";
                if ((!grid[i][j].getHorizontal()) && (grid[i][j].getVertical()))
                    temp = "2";
                if ((grid[i][j].getHorizontal()) && (!grid[i][j].getVertical()))
                    temp = "1";
                if ((!grid[i][j].getHorizontal()) && (!grid[i][j].getVertical()))
                    temp = "0";
                checkGrid = (checkGrid+temp);
            }
        }
        return checkGrid;
    }

    public void setIdsCells(){
        int id;
        for (int i=0;i<7;i++)
            for(int j=0;j<7;j++)
                grid[i][j].setId("cell"+Integer.toString(i*7+j));
    }

    public void setIdsHorizontalBars(){

        for (int i=0;i<7;i++)
                horizontalBar[i].setId("horizontalbar"+i);
    }

    public void setIdsVerticalBars(){

        for (int i=0;i<7;i++)
            horizontalBar[i].setId("verticalbar"+i);
    }


    public void generateBoard(){

        setIdsCells();
        setIdsHorizontalBars();
        setIdsVerticalBars();
        setHolesOfBars();
        setInitialPositions();
        prepareGrid();
    }


    public boolean checkBoundsValidity (String move) {

        char orientation = move.charAt(0); // Orientation of the bar: vertical (v) or horizontal (h)
        int number = Character.getNumericValue(move.charAt(1))-1; //Number of the bar
        char movement = move.charAt(2); //Slide movement of the bar: inward (i) or outward (o)

        if ((orientation == 'h') || (orientation == 'v')){
            if((number >= 0) && (number <= 6)){
                if((movement == 'o') || (movement == 'i')){

                    // Now it checks all possible combinations
                    if (orientation == 'h') {
                        if (movement == 'o') {
                            // Horizontal + Outward
                            if(horizontalBar[number].getPosition() < 2){
                                return true;
                                //error = "the horizontal bar number "+(number+1)+" can not be pushed out";
                            }
                        }
                            else if (movement == 'i') {
                                //Horizontal + Inward
                                if(horizontalBar[number].getPosition() > 0){
                                    return true;
                                    //error = "the horizontal bar number "+(number+1)+" can not be pushed in";
                                }
                            }
                        }
                    else if (orientation == 'v') {
                        if (movement == 'o') {
                            //Vertical + Outward
                            if (verticalBar[number].getPosition() < 2){
                                //error = "the vertical bar number "+(number+1)+" can not be pushed out";
                                return true;
                            }
                        }
                            else if (movement == 'i') {
                                //Vertical + Inward
                                if (verticalBar[number].getPosition() > 0){
                                    //error = "the vertical bar number "+(number+1)+" can not be pushed in";
                                    return true;
                                }
                            }
                    }
                }
                        // End of the possible valid moves combinations.
                else{
                    //error = "invalid input, the third character must be 'o' or 'i'";
                    return false;
                }
            }else{
                //error = "invalid input, the second character must be a number between 1 and 7.";
                return false;
            }
        }else{
            // error = "invalid input, the first character must be 'h' or 'v'";
            return false;
        }
    return false;
    }


    public void moveBar(String move){
        if (checkBoundsValidity((move))){

            char orientation = move.charAt(0); // Orientation of the bar: vertical (v) or horizontal (h)
            int number = Character.getNumericValue(move.charAt(1))-1; //Number of the bar
            char movement = move.charAt(2); //Slide movement of the bar: inward (i) or outward (o)

            if(orientation=='h'){ //muovo orizzontale
                if(movement=='o')
                    horizontalBar[number].setPosition(horizontalBar[number].getPosition()+1);
                else
                    horizontalBar[number].setPosition(horizontalBar[number].getPosition()-1);
                setRow(number);
            }
            else{  //muovo verticale
                if (movement=='o')
                    verticalBar[number].setPosition(verticalBar[number].getPosition()+1);
                else
                    verticalBar[number].setPosition(verticalBar[number].getPosition()-1);
                setColumn(number);
            }
        }
    }

    public String getBeadsPosition(){
        String beadsPosition="";
        for(int i=0; i < 7;i++) {
            for (int j=0; j < 7; j++) {
                if (grid[i][j].getBead())
                    beadsPosition=beadsPosition + grid[i][j].getOwner();
                else
                    beadsPosition=beadsPosition+"0";
            }
        }
        return beadsPosition;
    }

    public String getBarStatus(){
        String barStatus="";
        for(int i=0;i<7;i++)
            barStatus=barStatus+String.valueOf(horizontalBar[i].getPosition());
        for(int i=0;i<7;i++)
            barStatus=barStatus+String.valueOf(verticalBar[i].getPosition());
        return barStatus;}


    public void updateBeadsCellStatus(){
        String checkGrid=getCheckGrid();
        String beadsPosition=getBeadsPosition();

        for (int i=0;i<7;i++) {
            for (int j = 0; i < 7; j++) {
                if (checkGrid.charAt(i * 7 + j) == '0') {
                    grid[i][j].setBead(false);
                    grid[i][j].setOwner("0");
                }
            }
        }
    }

    public void updateRowBeadInCellStatus(int row){
        for (int i=0;i<7;i++){
            if (!grid[row][i].getHorizontal()&&!grid[row][i].getVertical()&&grid[row][i].getBead()) {
                grid[row][i].setBead(false);
                grid[row][i].setOwner("0");
            }
        }
    }

    public void updateColumnBeadInCellStatus(int column){
        for (int i=0;i<7;i++){
            if (!grid[i][column].getHorizontal()&&!grid[i][column].getVertical()&&grid[i][ column].getBead()) {
                grid[i][column].setBead(false);
                grid[i][column].setOwner("0");
            }
        }
    }

    public String getCurrentBeadsPosition(){
        String checkGrid=getCheckGrid();
        String beadsPosition=getBeadsPosition();
        String currentBeadsPosition = "";
        for (int i=0;i<7;i++) {
            for (int j = 0; j < 7;j++) {
                if (checkGrid.charAt(i*7+j) == '0') {
                    currentBeadsPosition = currentBeadsPosition + "0"; //if the cell checked is a hole, updates there can not be a bead on it
                    grid[i][j].setBead(false);
                    grid[i][j].setOwner("0");
                } else {
                    currentBeadsPosition = currentBeadsPosition + beadsPosition.charAt(i*7+j); //if the cell checked is filled by a bar, the old bead position valued is maintained
                }
            }
        }
            return currentBeadsPosition;
    }

    public Cell getCell(int i, int j) {
        return grid[i][j];
    }

    public void setCell(Cell grid, int i, int j) {
        this.grid[i][j] = grid;
    }
}
