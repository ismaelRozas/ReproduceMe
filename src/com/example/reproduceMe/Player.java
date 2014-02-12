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
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Player extends ListActivity {
	
	public ArrayList<Song> songList = new ArrayList<Song>();
	private SongsAdapter adapter;
	ListView songListView; 
	ScoresDataSource sds;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_player);
		songListView = (ListView) findViewById(android.R.id.list);
		
		//DataSource to obtain access to database (create and open)
		sds = new ScoresDataSource(Player.this);
		sds.open();
		
		
		searchSongs();		
		initPlayer();				
	}
	
	protected void onResume(){
		super.onResume();
		Toast.makeText(Player.this , "onResume", 5).show();	
	}
	
	protected void onRestart(){
		super.onRestart();
		Toast.makeText(Player.this , "onRestart", 5).show();
		searchSongs();		
		initPlayer();
	}
	
	protected void onStart(){
		super.onStart();
		Toast.makeText(Player.this , "onStart", 5).show();
	}
	
	protected void onDestroy(){
		super.onDestroy();
		sds.close();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.player, menu);
		return true;
	}
	
	private void searchSongs(){
		
		//if the list is not empty, trash the previous songs
		songList.clear();
		
		//Content resolver to read media files from phone
		ContentResolver contentResolver = getContentResolver();
		Uri uri = android.provider.MediaStore.Audio.Media.INTERNAL_CONTENT_URI ;

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
		       String thisAuthor = cursor.getString(authorColumn);
		       
		       //search song in the database. If does not exist, add a row with the default score		       
		       int songScore = sds.searchScoreSong(thisTitle, thisAuthor);
		       
		       if (songScore == -1){
		    	   songScore = 1;
		    	   sds.insertScore(thisTitle, thisAuthor, songScore);
		       }
		       		       
		       // creating the song and adding it to array
		       songList.add(new Song(thisTitle, thisAuthor, songScore));
		       
		    } while (cursor.moveToNext());
		}
		
		cursor.close();
		
	}
	
	private void initPlayer(){
		adapter = new SongsAdapter(this, songList);
		songListView.setAdapter(adapter);
//		songListView.setOnItemClickListener(listener);
		
		
		songListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {        
				
				    //find the title and author song
					TextView sn = (TextView) v.findViewById(R.id.titleSong);
					TextView sa = (TextView) v.findViewById(R.id.authorSong );
					String songName = sn.getText().toString();
					String author = sa.getText().toString();
					
					//increase the song score
				    sds.updateScore(songName, author, "up");
				    ((BaseAdapter) songListView.getAdapter()).notifyDataSetChanged();
					songListView.invalidate();
				   				     
					Intent intent = new Intent(Player.this, ListenSong.class);
	                intent.putExtra("position", position);
	                startActivity(intent);
			}
        });
		

		
	}

}
