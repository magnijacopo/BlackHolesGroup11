package it.polimi.group11.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    //Database name and version
    public static final String DATABASE_NAME = "Database.db";
    private static final int DATABASE_VERSION = 1;

    //Table player
    public static final String PLAYER_TABLE_NAME = "player";
    public static final String PLAYER_COLUMN_ID = "_id";
    public static final String PLAYER_COLUMN_NAME = "name";
    public static final String PLAYER_COLUMN_IMAGE = "image";
    public static final String PLAYER_COLUMN_CREATEDAT = "date";

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
     * Insert a new profile into the DB.
     * @param name
     * @return
     */
    public boolean insertProfile(String name, String image) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(PLAYER_COLUMN_NAME, name);
        contentValues.put(PLAYER_COLUMN_IMAGE, image);

        try{
            db.insertOrThrow(PLAYER_TABLE_NAME, null, contentValues);
            return true;} catch(SQLiteException e){
            e.printStackTrace();
            return false;}
    }

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
        Cursor res = db.rawQuery( "SELECT * FROM " + PLAYER_TABLE_NAME + " WHERE " +
                PLAYER_COLUMN_NAME + "=?", new String[] { name } );
        return res;
    }

    /**
     * Get all the existing profiles from the DB.
     * @return
     */
    public Cursor getAllProfiles() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + PLAYER_TABLE_NAME, null );
        return res;
    }

    /*
    SERVE DAVVERO ?
    /**
     * Update an existing profile.
     * @param id
     * @param name
     * @return

    public boolean updatePerson(Integer id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PLAYER_COLUMN_NAME, name);
        db.update(PLAYER_TABLE_NAME, contentValues, PLAYER_COLUMN_ID + " = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    */
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

    public boolean insertMatchMaking(int idPlayer, int idMatch) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(MATCHMAKING_COLUMN_PLAYERID, Integer.toString(idPlayer));
        contentValues.put(MATCHMAKING_COLUMN_MATCHID, Integer.toString(idMatch));

        db.insert(MATCHMAKING_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getMatchMaking(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM " + MATCHMAKING_TABLE_NAME + " WHERE " +
                MATCHMAKING_COLUMN_ID + "=?", new String[]{Integer.toString(id)});
        return res;
    }


    //--------------- "match" table methods ---------------//

    public boolean insertMatch(int idWinner, int moves) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(MATCH_COLUMN_WINNER, idWinner);
        contentValues.put(MATCH_COLUMN_MOVESNUMBER, moves);

        db.insert(PLAYER_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getMatch(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery( "SELECT * FROM " + MATCH_TABLE_NAME + " WHERE " +
                MATCH_COLUMN_ID + "=?", new String[] { Integer.toString(id) } );
        return res;
    }

    public Cursor getLastFiveMatches(int id){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery( "SELECT match.* FROM matchMaking JOIN players ON matchMaking.playerId = players._id" +
                "JOIN match ON matchMaking.matchId = match.id WHERE players.id = ? LIMIT 5", new String[] { Integer.toString(id)});
        return res;
    }

    //--------------- various queries methods ---------------//

    public Cursor getMatchPlayed(int idPlayer){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery( "SELECT COUNT(*) FROM " + MATCHMAKING_TABLE_NAME + " WHERE " +
                MATCHMAKING_COLUMN_PLAYERID + "=?", new String[] { Integer.toString(idPlayer) } );

        return res;
    }

    public Cursor getMatchWon(int idPlayer){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery( "SELECT COUNT(*) FROM " + MATCHMAKING_TABLE_NAME + " WHERE " +
                        MATCHMAKING_COLUMN_PLAYERID + "=? AND " + MATCH_COLUMN_WINNER + "=?",
                new String[] { Integer.toString(idPlayer), Integer.toString(idPlayer) } );

        return res;
    }

    public Cursor getMinNumberMoves(int idPlayer){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery( "SELECT MIN(" + MATCH_COLUMN_MOVESNUMBER + ") FROM " + MATCH_TABLE_NAME + " WHERE " +
                MATCH_COLUMN_ID + "=?", new String[] { Integer.toString(idPlayer) } );
        return res;
    }

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
}