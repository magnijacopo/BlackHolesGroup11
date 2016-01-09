package it.polimi.group11.model;

/**
 * Created by Lale on 02/01/2016.
 */
public class Bead {
    private boolean life;
    private String owner;
    private int rowPosition;
    private int columnPosition;
    private int number;

    public void setLife(boolean life) {
        this.life = life;
    }

    public boolean getLife(){
        return life;
    }

    public void setOwner(String owner){
        this.owner=owner;
    }

    public String getOwner(){
        return owner;
    }

    public void setRowPosition(int rowPosition){
        this.rowPosition=rowPosition;
    }

    public int getRowPosition(){
        return rowPosition;
    }

    public void setNumber(int number){
        this.number=number;
    }

    public int getNumber(){
        return number;
    }

    public void setColumnPosition(int columnPosition){
        this.columnPosition=columnPosition;
    }

    public int getColumnPosition(){
        return columnPosition;
    }
}
