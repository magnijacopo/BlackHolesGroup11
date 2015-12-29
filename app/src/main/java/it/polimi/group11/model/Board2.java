package it.polimi.group11.model;

/**
 * Created by Lale on 29/12/2015.
 */
public class Board2 {

    Bar[] horizontalBar = new Bar[7];
    Bar[] verticalBar = new Bar[7];
    Cell[][] grid = new Cell[7][7];
    Composition composition;

    private void setHolesOfBars() {
        for (int i = 0; i < 7; i++) {
            horizontalBar[i].setComposition(composition.getHorizontalComposition(i));
            verticalBar[i].setComposition(composition.getVerticalComposition(i));
        }
    }

    private void setInitialPositions() {
        for (int i = 0; i < 7; i++) {
            horizontalBar[i].setPosition((int)( Math.random() * 3 - 1));
            verticalBar[i].setPosition((int)( Math.random() * 3 - 1));
        }
    }

    public void setRow(int row){
        for (int i=0;i<7;i++)
            grid[row][i].setHorizontal(horizontalBar[row].getValue(i + horizontalBar[row].getPosition()));
    }

    public void setColumn(int column){
        for (int i=0;i<7;i++)
            grid[i][column].setVertical(verticalBar[column].getValue(i + verticalBar[column].getPosition()));
    }

    private void prepareGrid(){
        for (int i=0;i<7;i++) {
        setRow(i);
        setColumn(i);
        }
    }

    public void generateBoard(){
        setHolesOfBars();
        setInitialPositions();
        prepareGrid();
    }

}