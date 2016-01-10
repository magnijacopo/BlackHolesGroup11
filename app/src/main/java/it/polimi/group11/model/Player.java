package it.polimi.group11.model;

import it.polimi.group11.enumeration.PlayerID;


/**
 * Player class. Represents an instance of a player that is playing a game.
 * Defines the attributes of a player and contains methods to change or read those attributes
 * and method to make moves.
 */
public class Player {
    /**
     * Name chosen for the player
     */
    private String name;

    private int beadsInBoard=0;

    private Bead[] bead=new Bead[5];

    /**
     * Player identifier variable inside a single game
     */
    private String id;

    /**
     * The status of the player during the game.
     * True means the player is alive, False means eliminated
     */
    private boolean status;

    private Board board;

    private Board2 board2=new Board2();

    /**
     * The players can choose the colour of their beads
     */
    private String colour; //should be a Colour enumeration

    /**
     * The user will be associated with its own profile
     * In this way a user can have access to its statistics and data.
     */
    private Profile profile; //will become an Android persistent class

    /**
     * The number of the moves that a player has done in a game.
     */
    private int movesNumber;


    //Constructors

    /**
     * Constructor used for testing purposes.
     * The name is set equal to the id because there's not the possibility
     * to give a name to a player during the tests.
     * @param id {@link Player#id}
     */
    public Player(int id){ //
        this.setName(Integer.toString(id));
        this.setId(Integer.toString(id));
        this.setStatus(true); //the player is alive when created
    }

    /**
     * Full constructor
     *
     * @param playerName player's name {@link Player#name}
     * @param playerID player's id {@link Player#id}
     * @param colour player's colour {@link Player#colour}
     */
    public Player(String playerName, PlayerID playerID, String colour){
        this.setName(playerName);
        this.setId(playerID.toString());
        this.setStatus(true); //the player is alive when created
        this.setColour(colour);
    }

    // Getters and Setters

    /**
     *
     * @return name {@link Player#name}
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name {@link Player#name}
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return id {@link Player#id}
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id {@link Player#id}
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return status {@link Player#status}
     */
    public boolean getStatus() {
        return status;
    }

    /**
     *
     * @param status {@link Player#status}
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     *
     * @return profile {@link Player#profile}
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     *
     * @param profile {@link Player#profile}
     */
    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    /**
     *
     * @return colour {@link Player#colour}
     */
    public String getColour() {
        return colour;
    }

    /**
     *
     * @param colour {@link Player#colour}
     */
    public void setColour(String colour) {
        this.colour = colour;
    }

    /**
     *
     * @return movesNumber {@link Player#movesNumber}
     */
    public int getMovesNumber() {
        return movesNumber;
    }

    /**
     *
     * @param movesNumber {@link Player#movesNumber}
     */
    public void setMovesNumber(int movesNumber) {
        this.movesNumber = movesNumber;
    }


    //Methods

    /**
     * Allows players to make moves.
     * It checks the input and rearrange the position of the bars.
     *
     * @param /input the String that represents the move. {@link Move#moveId}
     * @param board
     */
     void makeMove(String input, Board board) {
        //the input move is sliced in three parts:
        char orientation = input.charAt(0); //vertical or horizontal bar
        int number = Character.getNumericValue(input.charAt(1))-1; //number of the bar
        char movement = input.charAt(2); //inward or outward slide movement

        if (orientation == 'h') {
            if (movement == 'o') {
                // Horizontal + Outward
                board.setHorizontalBarPosition(board.getHorizontalBarPosition(number)+1, number);
            }
            else if (movement == 'i') {
                // Horizontal + Inward
                board.setHorizontalBarPosition(board.getHorizontalBarPosition(number)-1, number);
            }
            board.setRow(number, board.getHorizontalBarPosition(number));
        }
        else if (orientation == 'v') {
            if (movement == 'o') {
                // Vertical + Outward
                board.setVerticalBarPosition(board.getVerticalBarPosition(number)+1, number);
            }
            else if (movement == 'i') {
                //Vertical + Inward
                board.setVerticalBarPosition(board.getVerticalBarPosition(number)-1, number);
            }
            board.setColumn(number, board.getVerticalBarPosition(number));
        }else{
            System.out.println("wrong input");
        }
    }

    /*public void makeMove2(String move, Board board){
        board.moveBar(move);
    }*/

    private boolean checkPlace(int rowPosition, int columnPosition){
        if (((board2.getCell(rowPosition,columnPosition).getHorizontal())||(board2.getCell(rowPosition,columnPosition).getVertical()))&&(!board2.getCell(rowPosition,columnPosition).getBead()))
            return true;
        else
            return false;
    }

    public boolean placeBead(String owner, int rowPosition, int columnPosition){
        if (beadsInBoard<5){
            if (checkPlace(rowPosition,columnPosition))
            {
                board2.getCell(rowPosition,columnPosition).setBead(true);
                board2.getCell(rowPosition,columnPosition).setOwner(owner);
                bead[beadsInBoard].setColumnPosition(columnPosition);
                bead[beadsInBoard].setRowPosition(rowPosition);
                bead[beadsInBoard].setOwner(owner);
                bead[beadsInBoard].setLife(true);
                beadsInBoard++;
                bead[beadsInBoard].setNumber(beadsInBoard);
                return true;
            }
        }
        return false;
    }
}