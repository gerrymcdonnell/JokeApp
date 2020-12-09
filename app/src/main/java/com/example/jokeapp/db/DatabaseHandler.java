package com.example.jokeapp.db;

import java.util.ArrayList;
import java.util.List;

import com.example.jokeapp.business.Joke;
 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class DatabaseHandler extends SQLiteOpenHelper {
 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 4;
 
    // Database Name
    private static final String DATABASE_NAME = "jokes";
 
    // Contacts table name
    private static final String TABLE_JOKES = "joke_table";
 
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String FIELD_JOKE= "joke";
    private static final String FIELD_JOKE_PUNCHLINE= "punchline";
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_JOKES+ "("
                + KEY_ID + " INTEGER PRIMARY KEY," 
        		+ FIELD_JOKE + " TEXT, "  
                + FIELD_JOKE_PUNCHLINE + " TEXT " +
        		")";
        Log.v("sql",CREATE_TABLE);
        db.execSQL(CREATE_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOKES);
 
        // Create tables again
        onCreate(db);
    }
 
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
 
    public boolean addJoke(Joke j) {
    	
    	try{
	        SQLiteDatabase db = this.getWritableDatabase();
	 
	        ContentValues values = new ContentValues();
	        values.put(FIELD_JOKE, j.getsJoke()); 
	         
	        // Inserting Row
	        db.insert(TABLE_JOKES, null, values);
	        db.close(); // Closing database connection
	        return true;
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return false;
    	}    	
    }
    

    public boolean addJoke(String sJoke) {
    	
    	try{
	        SQLiteDatabase db = this.getWritableDatabase();
	 
	        ContentValues values = new ContentValues();
	        values.put(FIELD_JOKE, sJoke); 
	         
	        // Inserting Row
	        db.insert(TABLE_JOKES, null, values);
	        db.close(); // Closing database connection
	        return true;
    	}
    	catch(Exception e)
    	{
    		//dupe bookmark
    		e.printStackTrace();
    		return false;
    	}    	
    }
 
    /*
     String getBookMark(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_JOKES, new String[] { KEY_ID,
                FIELD_JOKE}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        String sFilePath = cursor.getString(0);
        return sFilePath;
    }
    */
    

    /*
    // Getting All
    public List<String> getAllJokes() {
        List<String> jokeList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_JOKES;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String s=new String();
            	s=cursor.getString(1);
                // Adding contact to list
                jokeList.add(s);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return jokeList;
    }
    */
    
    // Getting All
    public void getAllJokes(ArrayList<Joke> jokeList) {
        
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_JOKES;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Joke j=new Joke();  
                //set joke object
                j.setsJoke(cursor.getString(1));
                
                
                // Adding to list
                jokeList.add(j);
            } while (cursor.moveToNext());
        } 

        
    }
    
    
    
    public void deleteAllJokes() {
		String sSql = "Delete from " + TABLE_JOKES;
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Log.v("SQL", sSql);
			db.execSQL(sSql);

		} catch (Exception e) {
			Log.v("deleteAllQuestions " + sSql, e.getMessage());
		}
	}
 
    /*
    // Updating single contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_IMG_PATH, contact.getName());
        values.put(KEY_PH_NO, contact.getPhoneNumber());
 
        // updating row
        return db.update(TABLE_BOOKMARKS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }
 */
    
    /*
    // Deleting single contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BOOKMARKS, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
        db.close();
    }
 */
 
    // Getting contacts Count
    public int getJokeCount() {
    	try{
	        String countQuery = "SELECT * FROM " + TABLE_JOKES;
	        int n=-1;
	        
	        SQLiteDatabase db = this.getReadableDatabase();
	        Cursor cursor = db.rawQuery(countQuery, null);
	        n=cursor.getCount();
	        cursor.close(); 
	        // return count
	        return n;
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		Log.v("error: getJokeCount()",e.getMessage());
    		return -1;
    	}
    }
 
}

