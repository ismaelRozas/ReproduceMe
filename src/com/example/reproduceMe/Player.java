package com.example.reproduceMe;

import java.util.ArrayList;


import android.net.Uri;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class Player extends ListActivity {
	
	public ArrayList<Song> songList = new ArrayList<Song>();
	private SongsAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_player);
		
		searchSongs();
		
		initPlayer();				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.player, menu);
		return true;
	}
	
	private void searchSongs(){
		
		//Content resolver to read media files from phone
		ContentResolver contentResolver = getContentResolver();
		Uri uri = android.provider.MediaStore.Audio.Media.INTERNAL_CONTENT_URI ;
		//DataSource to obtain access to database
		ScoresDataSource scoresDataSource = new ScoresDataSource(Player.this);

		Cursor cursor = contentResolver.query(uri, null, null, null, null);
		
		if (cursor == null) {
		  // query failed, handle error.
		} else if (!cursor.moveToFirst()) {
		  // no media on the device
		} else {
		    int titleColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE);
		    int idColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media._ID);
		    int authorColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.ARTIST );
		    
		    do {
		       long thisId = cursor.getLong(idColumn);
		       String thisTitle = cursor.getString(titleColumn);
		       String thisArtist = cursor.getString(authorColumn);
		       
		       //search song in the database. If does not exist, add a row with the default score
		       ScoresDataSource sds = new ScoresDataSource(Player.this);
		       sds.open();
		       int songScore = sds.searchSong(thisTitle);
		       
		       if (songScore == -1){
		    	   songScore = 1;
		    	   sds.insertScore(thisTitle, songScore);
		       }
		       
		       sds.close();
		       
		       // creating the song and adding it to array
		       songList.add(new Song(thisArtist, thisTitle, songScore));
		       
		    } while (cursor.moveToNext());
		}
		
		cursor.close();
		
	}
	
	private void initPlayer(){
		ListView songListView = (ListView) findViewById(android.R.id.list);
		adapter = new SongsAdapter(this, songList);
		songListView.setAdapter(adapter);
//		songListView.setOnItemClickListener(listener);
		
		
		songListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {                
					Intent intent = new Intent(Player.this, ListenSong.class);
	                intent.putExtra("position", arg2);
	                startActivity(intent);
			}
        });
		

		
	}

}
