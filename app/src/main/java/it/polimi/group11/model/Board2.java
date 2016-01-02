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
            horizontalBar[i].setPosition((int) (Math.random() * 3 - 1));
            verticalBar[i].setPosition((int) (Math.random() * 3 - 1));
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
}