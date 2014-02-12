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
	
	public int searchScoreSong(String songName, String songAuthor){
		
		Integer songScore = -1;

		//search the song to test if it has been introduced
		Cursor cursor = database.query(ScoresDb.TABLE_NAME,
		                               new String[]{ScoresDb.COLUMN_SCORE} , 
		                               ScoresDb.COLUMN_SONGNAME + " = \"" + songName + "\" AND " + ScoresDb.COLUMN_AUTHOR + " = \"" + songAuthor + "\"", 
		                               null,null, null, null);
		
		//if cursor is not null, return the _id from the song we have requested 
		if ((cursor!= null)&&(cursor.moveToFirst())){
			songScore = cursor.getInt(0);
		}
				
		return songScore;
	}
	
	
	public int searchIdSong(String songName, String songAuthor){
		
		Integer songId = -1;

		//search the song to test if it has been introduced
		Cursor cursor = database.query(ScoresDb.TABLE_NAME,
		                               new String[]{ScoresDb.COLUMN_ID} , 
		                               ScoresDb.COLUMN_SONGNAME + " = \"" + songName + "\" AND " + ScoresDb.COLUMN_AUTHOR + " = \"" + songAuthor + "\"", 
		                               null,null, null, null);
		
		//if cursor is not null, return the _id from the song we have requested 
		if ((cursor!= null)&&(cursor.moveToFirst())){
			songId = cursor.getInt(0);
		}
				
		return songId;
	}
			
	
	public void insertScore(String songName, String author, int sc) {
		
	    ContentValues values = new ContentValues();
	    values.put(ScoresDb.COLUMN_SONGNAME, songName);
	    values.put(ScoresDb.COLUMN_AUTHOR, author);
	    values.put(ScoresDb.COLUMN_SCORE, sc);
	    
	    long insertId = database.insert(ScoresDb.TABLE_NAME, null,
	        values);
	    
	}
	
	public void updateScore(String songName, String author, String trend){
		
		int score = searchScoreSong(songName, author);
		ContentValues values = new ContentValues();
	    values.put(ScoresDb.COLUMN_SONGNAME, songName);
	    values.put(ScoresDb.COLUMN_AUTHOR, author);
	    int id = searchIdSong(songName, author);
	    String where = ScoresDb.COLUMN_ID + "=" + String.valueOf(id);
	    
	    //depending on the trend, increase or decrease the song score
	    if (trend == "up" && score < 5){
	    	values.put(ScoresDb.COLUMN_SCORE, score + 1);
	    	long insertId = database.update(ScoresDb.TABLE_NAME, values, where, null);
	    }
	    
	    if (trend == "down" && score >1){
	    	values.put(ScoresDb.COLUMN_SCORE, score - 1);
	    	long insertId = database.update(ScoresDb.TABLE_NAME, values, where, null);
	    }
		
	}

}
