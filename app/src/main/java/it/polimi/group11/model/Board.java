package it.polimi.group11.model;

import android.content.res.Resources;
import android.content.res.TypedArray;

import java.util.Objects;

/**
 * Created by Lale on 13/11/2015.
 */
public class Board {

    private Resources res = getResources();

    private TypedArray VERTICAL = res.obtainTypedArray(android.R.array.bool);

    private boolean[][] VERTICALe = new boolean[7][9];

    private boolean[][] HORIZONTAL = new boolean[7][9];

    private int[] verticalPosition=new int[7];

    private int[] horizontalPosition=new int[7];

    private Cell[][] grid=new Cell[7][7];

    private Match match;

    private Cell cell;

    private Match match;

    private GameComands gameComands;


    public void move(String arg) {

        char orientation = arg.charAt(0);
        int num=Integer.parseInt(arg.substring(1, 1));
        char movement=arg.charAt(2);
        int set=0;

        if (Objects.equals(orientation, "h")){
            if (Objects.equals("o", movement)) {
                set=horizontalPosition[num]+1;
            }
            if (Objects.equals(movement,"i")){
                set=horizontalPosition[num]-1;
            }
            setRow(num,set);
        };
        if (Objects.equals(orientation, "v")){
            if (Objects.equals("o", movement)) {
                set=verticalPosition[num]+1;
            }
            if (Objects.equals(movement,"i")){
                set=verticalPosition[num]-1;
            }
            setColumn(num, set);
        }
        return;
    };

    private void prepareGrid(){
        for (int i=0;i<7;i++){
            setRow(i,horizontalPosition[i]);
            setColumn(i,verticalPosition[i]);
        }
    }

    private void setRow(int row, int set){
      for(int i=0;i<7;i++){
          grid[row][i].horizontalBar=HORIZONTAL[row][i+set];
        }
    };

    private void setColumn(int column, int set){
        for(int i=0;i<7;i++){
            grid[column][i].verticalBar=VERTICAL[column][i+set];
        }

    };

    public Resources getResources() {
        return resources;
    }
}