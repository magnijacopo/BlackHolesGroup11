package it.polimi.group11.model;

/**
 * Created by Lale on 29/12/2015.
 */
public class Composition {

    boolean[][] horizontalComposition={
            {true, false, true, false, true, false, true, false, true},
            {true, false, false, true, false, false, true, false, true},
            {true, false, false, false, true, false, false, false, true},
            {true, false, true, false, true, false, true, false, true},
            {true, false, false, false, false, false, false, false, true},
            {true, true, false, false, false, true, false, true, true},
            {true, false, false, true, false, true, false, true, true}
    };

    boolean[][] verticalComposition={
            {true, false, false, false, false, true, false, true, true },
            {true, false, false, false, true, true, false, false, true },
            {true, false, true, false, false, true, false, true, true },
            {true, false, false, true, true, false, false, false, true },
            {true, true, false, false, false, true, false, true, true },
            {true, true, false, false, false, false, false, true, true },
            {true, false, false, true, false, false, true, false, true }
    };

    public boolean[] getHorizontalComposition(int i){
        return horizontalComposition[i];
    }

    public boolean[] getVerticalComposition(int i){
        return verticalComposition[i];
    }
}
