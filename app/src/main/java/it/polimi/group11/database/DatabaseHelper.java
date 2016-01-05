package it.polimi.group11.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    //Database name and version
    public static final String DATABASE_NAME = "Database.db";
    private static final int DATABASE_VERSION = 1;

    //Table player
    public static final String PLAYER_TABLE_NAME = "player";
    public static final String PLAYER_COLUMN_ID = "_id";
    public static final String PLAYER_COLUMN_NAME = "name";
    public static final String PLAYER_COLUMN_CREATEDAT = "date";

    //Table MatchMaking
    private static final String MATCHMAKING_TABLE_NAME = "matchmaking";
    private static final String MATCHMAKING_COLUMN_ID = "_id";
    private static final String MATCHMAKING_COLUMN_PLAYERID = "playerId";
    private static final String MATCHMAKING_COLUMN_MATCHID = "matchId";

    //Table Match
    private static final String MATCH_TABLE_NAME = "match";
    private static final String MATCH_COLUMN_ID = "_id";
    private static final String MATCH_COLUMN_DATE = "date";
    private static final String MATCH_COLUMN_DURATION = "duration";
    private static final String MATCH_COLUMN_WINNER = "winnerId";
    private static final String MATCH_COLUMN_MOVESNUMBER = "numberOfMoves";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + PLAYER_TABLE_NAME + "(" +
                PLAYER_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                PLAYER_COLUMN_NAME + " TEXT, " +
                PLAYER_COLUMN_CREATEDAT + " TEXT)");

        db.execSQL("CREATE TABLE " + MATCHMAKING_TABLE_NAME + "(" +
                MATCHMAKING_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                MATCHMAKING_COLUMN_PLAYERID + " INTEGER, " +
                MATCHMAKING_COLUMN_MATCHID + " INTEGER)");

        db.execSQL("CREATE TABLE " + MATCH_TABLE_NAME + "(" +
                MATCH_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                MATCH_COLUMN_DATE + " TEXT, " + MATCH_COLUMN_DURATION + " TEXT, " +
                MATCH_COLUMN_WINNER + " INTEGER, " +
                MATCH_COLUMN_MOVESNUMBER + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PLAYER_TABLE_NAME);
        onCreate(db);
    }

    //--------------- "player" table methods ---------------//

    /**
     * Insert a new profile into the DB.
     * @param name
     * @param date
     * @return
     */
    public boolean insertProfile(String name, String date) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(PLAYER_COLUMN_NAME, name);
        contentValues.put(PLAYER_COLUMN_CREATEDAT, date);

        db.insert(PLAYER_TABLE_NAME, null, contentValues);
        return true;
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

    /**
     * Update an existing profile.
     * @param id
     * @param name
     * @param createdAt
     * @return
     */
    public boolean updatePerson(Integer id, String name, String createdAt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PLAYER_COLUMN_NAME, name);
        contentValues.put(PLAYER_COLUMN_CREATEDAT, createdAt);
        db.update(PLAYER_TABLE_NAME, contentValues, PLAYER_COLUMN_ID + " = ? ", new String[] { Integer.toString(id) } );
        return true;
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

    public boolean insertMatchMaking(String name, String date) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(PLAYER_COLUMN_NAME, name);
        contentValues.put(PLAYER_COLUMN_CREATEDAT, date);

        db.insert(PLAYER_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getMatchMaking(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM " + MATCHMAKING_TABLE_NAME + " WHERE " +
                MATCHMAKING_COLUMN_ID + "=?", new String[]{Integer.toString(id)});
        return res;
    }


    //--------------- "match" table methods ---------------//

    public boolean insertMatch(String name, String date) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(PLAYER_COLUMN_NAME, name);
        contentValues.put(PLAYER_COLUMN_CREATEDAT, date);

        db.insert(PLAYER_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getMatch(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery( "SELECT * FROM " + MATCH_TABLE_NAME + " WHERE " +
                MATCH_COLUMN_ID + "=?", new String[] { Integer.toString(id) } );
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

        Cursor res = db.rawQuery( "SELECT min( FROM " + MATCHMAKING_TABLE_NAME + " WHERE " +
                MATCHMAKING_COLUMN_PLAYERID + "=?", new String[] { Integer.toString(idPlayer) } );
        return res;
    }
}