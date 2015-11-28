package it.polimi.group11.enumeration;

public enum PlayerID {
    PLAYER1(1),
    PLAYER2(2),
    PLAYER3(3),
    PLAYER4(4);

    private int value;

    private PlayerID(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}


