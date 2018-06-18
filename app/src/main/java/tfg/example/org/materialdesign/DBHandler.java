package tfg.example.org.materialdesign;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteTransactionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Iván on 25/04/2018.
 */

public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "favmoviestable";
    // Contacts table name
    private static final String TABLE_MOVIES = "movies";
    // Shops Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_MOVIE_ID = "movie_id";
    private static final String KEY_NAME = "movie_name";

    public DBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_MOVIES + "("
        + KEY_ID + " INTEGER PRIMARY KEY," + KEY_MOVIE_ID + " TEXT, " + KEY_NAME + " TEXT"+")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        // Creating tables again
        onCreate(db);
    }

    // Adding new shop
    public void addMovie(FavMoviesDB movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, movie.getMovie_name());
        values.put(KEY_MOVIE_ID, movie.getMovie_id());
        // Inserting Row
        db.insert(TABLE_MOVIES, null, values);
        db.close(); // Closing database connection
    }


    // Getting one movie
    public FavMoviesDB getMovie(int id) {
        FavMoviesDB movie;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MOVIES, new String[] { KEY_ID, KEY_MOVIE_ID,
                        KEY_NAME}, KEY_MOVIE_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        try {
            movie = new FavMoviesDB(Integer.parseInt(cursor.getString(0)),
                    Integer.parseInt(cursor.getString(1)), cursor.getString(2));
        } catch(Exception e) {
            return null;
        }
        return movie;
    }

    // Getting All Shops
    public List<FavMoviesDB> getAllFavMovies() {
        List<FavMoviesDB> shopList = new ArrayList<FavMoviesDB>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_MOVIES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                FavMoviesDB shop = new FavMoviesDB(0, 0, null);
                shop.setId(Integer.parseInt(cursor.getString(0)));
                shop.setMovie_id(Integer.parseInt(cursor.getString(1)));
                shop.setMovie_name(cursor.getString(2));

                // Adding contact to list
                shopList.add(shop);
            } while (
                    cursor.moveToNext());
        }
        // return contact list
        return shopList;
    }

    // Getting shops Count
    public int getMoviesCount() {
        String countQuery = "SELECT * FROM " + TABLE_MOVIES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }

    // Updating a shop
    public int updateMovie(FavMoviesDB movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MOVIE_ID, movie.getMovie_id());
        values.put(KEY_NAME, movie.getMovie_name());
        // updating row
        return db.update(TABLE_MOVIES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(movie.getId())});
    }

    // Deleting a shop
    public void deleteMovies(FavMoviesDB movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MOVIES, KEY_ID + " = ?",
                new String[] { String.valueOf(movie.getId()) });
        db.close();
    }
}
