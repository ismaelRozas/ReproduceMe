package com.example.reproduceMe;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class ScoresDb extends SQLiteOpenHelper{
	
	public static final String TABLE_NAME = "Scores";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_SONGNAME = "songName";
	public static final String COLUMN_SCORE = "score";
	public static final String DB_NAME = "Scores.db";
	public static final int DB_VERSION = 1;
	
	public static final String DB_CREATE = "create table "
		      + TABLE_NAME      + "(" + COLUMN_ID + " integer primary key autoincrement, " 
			  + COLUMN_SONGNAME + " text not null, "
		      + COLUMN_SCORE    + " integer);";
	
	public ScoresDb(Context context){
		super(context, DB_NAME, null, DB_VERSION);
	}

	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DB_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	    onCreate(db);
		
	}

}
