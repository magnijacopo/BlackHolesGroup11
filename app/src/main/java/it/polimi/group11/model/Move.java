package it.polimi.group11.model;

public class Move {
    private String moveId;
    private String playerId;

    public Move(String move, String player){
        this.moveId = move;
        this.playerId = player;
    }

    public String getPlayerId(){
        return playerId;
    }

    public String getMoveId(){
        return moveId;
    }
}
