package it.polimi.group11.model;

import it.polimi.group11.enumeration.PlayerID;

public class Player {
    private String name = new String(); //name chosen for the player
    private String id = new String(); //player identifier variable (should be int?)
    private boolean status; //true means the player is alive, false means eliminated
    private String colour = new String(); //should be a Colour enumeration
    private Profile profile; //will become an Android persistent class, the user will be associated with its own profile
    private int movesNumber;

    Board board = Board.getInstance(); //the player is paired with the board instantiated in this game session

    public Player(int id){ //constructor used for testing purposed
        this.setName(Integer.toString(id));
        this.setId(Integer.toString(id));
        this.setStatus(true); //the player is alive when created
    }

    public Player(String playerName, PlayerID playerID, String colour){ //full constructor
        this.setName(playerName);
        this.setId(playerID.toString());
        this.setStatus(true);
        this.setColour(colour);
        //some lines should be added to check if the player is a new one or a profile owner one (has already played once)
    }

    public void makeMove(String input) { //based on the input move the method rearrange the position of the bars
        //the input move is sliced in three parts:
        char orientation = input.charAt(0); //vertical or horizontal bar
        int number = Character.getNumericValue(input.charAt(1))-1; //number of the bar
        char movement = input.charAt(2); //inward or outward slide movement

        if (orientation == 'h') { //horizontal
            if (movement == 'o') { //outward
                board.setHorizontalBarPosition(board.getHorizontalBarPosition(number)+1, number);
            }
            else if (movement == 'i') { //inward
                board.setHorizontalBarPosition(board.getHorizontalBarPosition(number)-1, number);
            }
            board.setRow(number, board.getHorizontalBarPosition(number));
        }
        else if (orientation == 'v') { //vertical
            if (movement == 'o') {
                board.setVerticalBarPosition(board.getVerticalBarPosition(number)+1, number);
            }
            else if (movement == 'i') {
                board.setVerticalBarPosition(board.getVerticalBarPosition(number)-1, number);
            }
            board.setColumn(number, board.getVerticalBarPosition(number));
        }else{
            System.out.println("wrong input");
        }
        return; //manca un messaggio che quando tiri una barra già in posizione inward ti dice che è wrong
    }

    //getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public int getMovesNumber() {
        return movesNumber;
    }

    public void setMovesNumber(int movesNumber) {
        this.movesNumber = movesNumber;
    }
}