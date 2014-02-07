package com.example.reproduceMe;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ScoresDataSource {
	
	private SQLiteDatabase database;
	private ScoresDb dbHelper;
	
	public ScoresDataSource(Context context){
		dbHelper = new ScoresDb(context);
	}
	
	public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	}

	public void close() {
	    dbHelper.close();
	}
	
	public int searchSong(String song){
		
		Integer songId = -1;
		open();
		//search the song to test if it has been introduced
		Cursor cursor = database.query(ScoresDb.TABLE_NAME,
		        new String[]{ScoresDb.COLUMN_SCORE} , ScoresDb.COLUMN_SONGNAME + " = \"" + song + "\"", null,
		        null, null, null);
		
		//if cursor is not null, return the _id from the song we have requested 
		if ((cursor!= null)&&(cursor.moveToFirst())){
			songId = cursor.getInt(0);
		}
		
		close();
				
		return songId;
	}
		
	
	public void insertScore(String name, Integer sc) {
		
		open();
	    ContentValues values = new ContentValues();
	    values.put(ScoresDb.COLUMN_SONGNAME, name);
	    values.put(ScoresDb.COLUMN_SCORE, sc);
	    
	    long insertId = database.insert(ScoresDb.TABLE_NAME, null,
	        values);
	    
	    close();
	  }

}
