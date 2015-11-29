package it.polimi.group11.model;

import it.polimi.group11.enumeration.PlayerID;


/**
 * Player class. Represent an instance of a player that is playing a game.
 * Define the attributes of a player and contains method to change or read this attributes,
 * and method to make moves.
 */
public class Player {

    /**
     * Name chosen for the player by the user
     */
    private String name;

    /**
     * Player identifier variable inside a single game
     */
    private String id;

    /**
     * The status of the player during the game.
     * True means the player is alive, False means eliminated
     */
    private boolean status;

    /**
     * The players can choose the colour of their beads
     */
    private String colour; //should be a Colour enumeration

    /**
     * The user will be associated with its own profile
     * In this way a user can have statistics and preferences.
     */
    private Profile profile; //will become an Android persistent class

    /**
     * The number of the moves that a player has done in the game.
     */
    private int movesNumber;

    /**
     * The player is paired with the board instantiated in this game session
     */
    Board board = Board.getInstance();


    //Constructors

    /**
     * Constructor used for testing purposed.
     * The name is set equal to the id, because the players have no name in the test.
     *
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
        //some lines should be added to check if the player is a new one or a profile owner one (has already played once)
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
     * makeMove allows players to make moves.
     * It checks the input and rearrange the position of the bars.
     *
     * @param input the String that represents the move. {@link Move#moveId}
     */
    public void makeMove(String input) {

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
        return; //manca un messaggio che quando tiri una barra già in posizione inward ti dice che è wrong
    }

}