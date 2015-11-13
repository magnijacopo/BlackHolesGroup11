package it.polimi.group11.model;

import android.content.res.Resources;
import android.content.res.TypedArray;

import java.util.Objects;

import it.polimi.group11.R;

/**
 * Created by Lale on 13/11/2015.
 */
public class Board {

    private Resources res = getResources();

    public TypedArray vertical = res.obtainTypedArray(R.array.vertical_position);

    public TypedArray horizontal = res.obtainTypedArray(R.array.horizontal_position);

    private boolean[][] VERT = new boolean[7][9];

    private boolean[][] HORIZ = new boolean[7][9];

    private int[] verticalPosition=new int[7];

    private int[] horizontalPosition=new int[7];

    private Cell[][] grid=new Cell[7][7];

    private Match match;

    private Cell cell;

    private Match match;

    private GameComands gameComands;

    public void fillVertical(TypedArray vertical){
        for(int i=0; i<7; i++){
            for (int k=0; k<7; k++){
                VERT[i][k] = vertical.getBoolean((i * k + k), true);
            }
        }

    }
    public void fillHorizontal(TypedArray horizontal){
        for(int i=0; i<7; i++){
            for (int k=0; k<7; k++){
                VERT[i][k] = horizontal.getBoolean((i * k + k), true);
            }
        }

    }


    public void move(String arg) {

        char orientation = arg.charAt(0);
        int num=Integer.parseInt(arg.substring(1, 1));
        char movement=arg.charAt(2);
        int set=0;

        if (Objects.equals(orientation, "h")){
            if (Objects.equals("o", movement)) {
                horizontalPosition[num]++;
            }
            if (Objects.equals(movement,"i")){
                horizontalPosition[num]--;
            }
            setRow(num,horizontalPosition[num]);
        };
        if (Objects.equals(orientation, "v")){
            if (Objects.equals("o", movement)) {
                verticalPosition[num]++;
            }
            if (Objects.equals(movement,"i")){
                verticalPosition[num]--;
            }
            setColumn(num, verticalPosition[num]);
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
          grid[row][i].horizontalBar=HORIZ[row][i+set];
        }
    };


    private void setColumn(int column, int set){
        for(int i=0;i<7;i++){
            grid[column][i].verticalBar=VERT[column][i+set];
        }

    };

    public Resources getResources() {
        return resources;
    }
}