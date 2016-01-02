package it.polimi.group11.model;

/**
 * Created by Lale on 02/01/2016.
 */
public class Game2 {

    private Board2 board = new Board2();
    private int[] playerOrder;
    int numPlayers;

    public void randomizePlayerOrder(){
        playerOrder = new int[numPlayers];
        playerOrder[0]=(int) (Math.random()*numPlayers);
        int playersOrdered=1;
        while (playersOrdered<numPlayers){
            int temp=(int) (Math.random()*numPlayers);
            boolean check=true;
            for (int i=0;i<playersOrdered;i++)
                if (playerOrder[i]==temp)
                    check=false;
            if (check) {
                playerOrder[playersOrdered] = temp;
                playersOrdered++;
            }
        }
    }

    public int getPlayer(int position){
        return playerOrder[position];
    }

    public int getNumPlayers(){
        return numPlayers;
    }
    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }
}