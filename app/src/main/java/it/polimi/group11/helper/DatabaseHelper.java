package it.polimi.group11.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String MATCHPLAYED = "MATCHPLAYED";
    public static final String MATCHWON = "MATCHWON";
    public static final String MINMOVES = "MINMOVES";
    public static final String VINTE = "VINTE";
    public static final String MAXID = "MAXID";

    //Database name and version
    public static final String DATABASE_NAME = "Database.db";
    private static final int DATABASE_VERSION = 1;

    //Table player
    public static final String PLAYER_TABLE_NAME = "player";
    public static final String PLAYER_COLUMN_ID = "_id";
    public static final String PLAYER_COLUMN_NAME = "name";
    public static final String PLAYER_COLUMN_IMAGE = "image";
    public static final String PLAYER_COLUMN_CREATEDAT = "dateCreation";

    //Table MatchMaking
    public static final String MATCHMAKING_TABLE_NAME = "matchmaking";
    public static final String MATCHMAKING_COLUMN_ID = "_id";
    public static final String MATCHMAKING_COLUMN_PLAYERID = "playerId";
    public static final String MATCHMAKING_COLUMN_MATCHID = "matchId";

    //Table Match
    public static final String MATCH_TABLE_NAME = "match";
    public static final String MATCH_COLUMN_ID = "_id";
    public static final String MATCH_COLUMN_DATE = "date";
    public static final String MATCH_COLUMN_DURATION = "duration";
    public static final String MATCH_COLUMN_WINNER = "winnerId";
    public static final String MATCH_COLUMN_MOVESNUMBER = "numberOfMoves";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // Creation table.
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + PLAYER_TABLE_NAME + "(" +
                PLAYER_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                PLAYER_COLUMN_NAME + " TEXT UNIQUE NOT NULL, " + PLAYER_COLUMN_IMAGE + " TEXT, " +
                PLAYER_COLUMN_CREATEDAT + " DATETIME DEFAULT CURRENT_TIMESTAMP)");

        db.execSQL("CREATE TABLE " + MATCHMAKING_TABLE_NAME + "(" +
                MATCHMAKING_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                MATCHMAKING_COLUMN_PLAYERID + " INTEGER, " +
                MATCHMAKING_COLUMN_MATCHID + " INTEGER)");

        db.execSQL("CREATE TABLE " + MATCH_TABLE_NAME + "(" +
                MATCH_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                MATCH_COLUMN_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP, " + MATCH_COLUMN_DURATION + " TEXT, " +
                MATCH_COLUMN_WINNER + " INTEGER, " +
                MATCH_COLUMN_MOVESNUMBER + " INTEGER)");

        ContentValues guest1 = new ContentValues();
        guest1.put(PLAYER_COLUMN_NAME, "guest1");
        db.insert(PLAYER_TABLE_NAME, null, guest1);

        ContentValues guest2 = new ContentValues();
        guest2.put(PLAYER_COLUMN_NAME, "guest2");
        db.insert(PLAYER_TABLE_NAME, null, guest2);

        ContentValues guest3 = new ContentValues();
        guest3.put(PLAYER_COLUMN_NAME, "guest3");
        db.insert(PLAYER_TABLE_NAME, null, guest3);

        ContentValues guest4 = new ContentValues();
        guest4.put(PLAYER_COLUMN_NAME, "guest4");
        db.insert(PLAYER_TABLE_NAME, null, guest4);

        ContentValues cpu1 = new ContentValues();
        cpu1.put(PLAYER_COLUMN_NAME, "cpu1");
        db.insert(PLAYER_TABLE_NAME, null, cpu1);

        ContentValues cpu2 = new ContentValues();
        cpu2.put(PLAYER_COLUMN_NAME, "cpu2");
        db.insert(PLAYER_TABLE_NAME, null, cpu2);

        ContentValues cpu3 = new ContentValues();
        cpu3.put(PLAYER_COLUMN_NAME, "cpu3");
        db.insert(PLAYER_TABLE_NAME, null, cpu3);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + PLAYER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MATCHMAKING_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MATCH_TABLE_NAME);

        onCreate(db);
    }

    public void deleteDatabase(){
        this.deleteDatabase();
    }


    //--------------- "player" table methods ---------------//

    /**
     * Insert a profile into the database.
     * @param name
     * @return
     */
    public boolean insertProfile(String name) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(PLAYER_COLUMN_NAME, name);
        try{
        db.insertOrThrow(PLAYER_TABLE_NAME, null, contentValues);
        return true;} catch(SQLiteException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get an existing profile from the DB by its id.
     * @param id
     * @return
     */
    public Cursor getProfile(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery( "SELECT * FROM " + PLAYER_TABLE_NAME + " WHERE " +
                PLAYER_COLUMN_ID + "=?", new String[] { Integer.toString(id) } );

        return res;
    }

    /**
     * Get an existing profile from the DB by its name.
     * @param name
     * @return
     */
    public Cursor getProfile(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + PLAYER_TABLE_NAME + " WHERE " +
                PLAYER_COLUMN_NAME + "=?", new String[]{name});
        return res;
    }

    /**
     * Get all the existing profiles from the DB.
     * @return
     */
    public Cursor getAllProfiles() {

        SQLiteDatabase db = this.getReadableDatabase();
        String guest1 = "guest1";
        String guest2 = "guest2";
        String guest3 = "guest3";
        String guest4 = "guest4";
        String cpu1 = "cpu1";
        String cpu2 = "cpu2";
        String cpu3 = "cpu3";

        Cursor res = db.rawQuery( "SELECT * FROM " + PLAYER_TABLE_NAME + " WHERE NOT " + PLAYER_COLUMN_NAME + "=?" +
                " AND NOT " + PLAYER_COLUMN_NAME + "=?" + " AND NOT " + PLAYER_COLUMN_NAME + "=?" +
                " AND NOT " + PLAYER_COLUMN_NAME + "=?" + " AND NOT " + PLAYER_COLUMN_NAME + "=?" +
                " AND NOT " + PLAYER_COLUMN_NAME + "=?" + " AND NOT " + PLAYER_COLUMN_NAME + "=?" ,
                new String[] { guest1, guest2, guest3, guest4, cpu1, cpu2, cpu3  }, null );
        return res;
    }

    /**
     * Delete from the DB a profile.
     * @param id
     * @return
     */
    public Integer deleteProfile(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(PLAYER_TABLE_NAME,
                PLAYER_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }

    //--------------- "matchmaking" table methods ---------------//

    /**
     * Insert a matchMaking into the database.
     * @param idPlayer
     * @param idMatch
     * @return
     */
    public boolean insertMatchMaking(int idPlayer, int idMatch) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(MATCHMAKING_COLUMN_PLAYERID, Integer.toString(idPlayer));
        contentValues.put(MATCHMAKING_COLUMN_MATCHID, Integer.toString(idMatch));

        db.insert(MATCHMAKING_TABLE_NAME, null, contentValues);
        return true;
    }

    /**
     * Get a Matchmaking from the database.
     * @param id
     * @return
     */
    public Cursor getMatchMaking(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM " + MATCHMAKING_TABLE_NAME + " WHERE " +
                MATCHMAKING_COLUMN_ID + "=?", new String[]{Integer.toString(id)});
        return res;
    }

    //--------------- "match" table methods ---------------//

    /**
     * Insert a match into the db.
     * @param idWinner
     * @param moves
     */
    public void insertMatch(int idWinner, int moves) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(MATCH_COLUMN_WINNER, idWinner);
        contentValues.put(MATCH_COLUMN_MOVESNUMBER, moves);

        db.insert(MATCH_TABLE_NAME, null, contentValues);
    }

    /**
     * Get a match from its id.
     * @param id
     * @return
     */
    public Cursor getMatch(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery( "SELECT * FROM " + MATCH_TABLE_NAME + " WHERE " +
                MATCH_COLUMN_ID + "=?", new String[] { Integer.toString(id) } );
        return res;
    }

    /**
     * Get the Last match inserted into the database.
     * @return
     */
    public Cursor getLastMatch(){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("SELECT MAX(" + MATCH_COLUMN_ID + ") AS " + MAXID + " FROM " + MATCH_TABLE_NAME, new String[] {});
        return res;
    }


    //--------------- various queries methods ---------------//

    /**
     * Get the match played.
     * @param idPlayer player of which the match played
     * @return
     */
    public Cursor getMatchPlayed(int idPlayer){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery( "SELECT COUNT(*) AS " +  MATCHPLAYED + " FROM " + MATCHMAKING_TABLE_NAME + " WHERE " +
                MATCHMAKING_COLUMN_PLAYERID + "=?", new String[] { Integer.toString(idPlayer) } );

        return res;
    }

    /**
     * Get the match won.
     * @param idPlayer
     * @return
     */
    public Cursor getMatchWon(int idPlayer){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery( "SELECT COUNT(*) AS " + MATCHWON + " FROM " + MATCH_TABLE_NAME + " WHERE " + MATCH_COLUMN_WINNER + "=?",
                new String[] { Integer.toString(idPlayer) } );
        return res;
    }

    /**
     * Get the minimum number of moves.
     * @param idPlayer
     * @return
     */
    public Cursor getMinNumberMoves(int idPlayer){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery( "SELECT MIN(" + MATCH_COLUMN_MOVESNUMBER + ") AS " + MINMOVES + " FROM " + MATCH_TABLE_NAME + " WHERE " +
                MATCH_COLUMN_WINNER + "=?", new String[] { Integer.toString(idPlayer) } );
        return res;
    }

    /*
    public Cursor getShortestMatch(int idPlayer){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery( "SELECT * FROM " + MATCH_TABLE_NAME + " WHERE " +
                MATCH_COLUMN_DURATION + " = (SELECT MIN(" + MATCH_COLUMN_DURATION + ") FROM " + MATCH_TABLE_NAME + " WHERE "
                + MATCH_COLUMN_WINNER + "=?" , new String[] { Integer.toString(idPlayer) } );
        return res;
    }

    public Cursor getLongestMatch(int idPlayer){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery( "SELECT * FROM " + MATCH_TABLE_NAME + " WHERE " +
                MATCH_COLUMN_DURATION + " = (SELECT MAX(" + MATCH_COLUMN_DURATION + ") FROM " + MATCH_TABLE_NAME + " WHERE "
                + MATCH_COLUMN_WINNER + "=?" , new String[] { Integer.toString(idPlayer) } );
        return res;
    }
    */

        /**
         * From the cursor it gets the name of the profile.
         * @param cursor that has done the query.
         * @return the name of the player.
         */
    public String getNamePlayerFromCursor(Cursor cursor){
        String namePlayer = null;
        if(cursor.moveToFirst()){
            namePlayer = cursor.getString(cursor.getColumnIndex(PLAYER_COLUMN_NAME));
        }
        return namePlayer;
    }

    /**
     * From the cursor it gets the id of the profile.
     * @param cursor that has done the query.
     * @return the id of the player.
     */
    public int getIdPlayerFromCursor(Cursor cursor){
        int idPlayer = 0;
        if(cursor.moveToFirst()){
            idPlayer = cursor.getInt(cursor.getColumnIndex(PLAYER_COLUMN_ID));
        }
        return idPlayer;
    }

    /**
     * From the cursor it gets the number of match played by the player.
     * @param cursor that has done the query.
     * @return the match played.
     */
   public int getNumberMatchPlayedFromCursor(Cursor cursor) {
       int mp = 0;
       if(cursor.moveToFirst()) {
           mp = cursor.getInt(cursor.getColumnIndex(MATCHPLAYED));
       }
       return mp;
   }

    /**
     * From the cursor it gets the match won by the player.
     * @param cursor that has done the query.
     * @return the number of match won.
     */
    public int getNumberMatchWonFromCursor(Cursor cursor) {
        int mw = 0;
        if(cursor.moveToFirst()) {
            mw = cursor.getInt(cursor.getColumnIndex(MATCHWON));
        }
        return mw;
    }

    /**
     * From the cursor it gets the minimum number of moves.
     * @param cursor that has done the query.
     * @return number of moves.
     */
    public int getNumberMinMovesFromCursor(Cursor cursor) {
        int mm = 0;
        if(cursor.moveToFirst()) {
            mm = cursor.getInt(cursor.getColumnIndex(MINMOVES));
        }
        return mm;
    }

    /**
     * From the cursor it gets the id of the last match.
     * @param cursor that has done the query.
     * @return the id of the match.
     */
    public int getIdLastMatchFromCursor(Cursor cursor) {
        int idL = 0;
        if(cursor.moveToFirst()) {
            idL = cursor.getInt(cursor.getColumnIndex(MAXID));
        }
        return idL;
    }

}