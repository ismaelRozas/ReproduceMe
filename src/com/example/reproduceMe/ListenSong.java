package com.example.reproduceMe;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.Toast;

public class ListenSong extends Activity{
	
	private static MediaPlayer mp = new MediaPlayer();
	private MediaController mc;
	private Handler handler = new Handler();
	private Integer position = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_listen_song);
		
		//stop button listener
		ImageButton btnStop = (ImageButton)findViewById(R.id.stopBtn);
		btnStop.setOnClickListener(
				
				new OnClickListener() {
		             @Override
		             public void onClick(View v) {
		                  //call to pause
		            	 mp.pause();
		             }
		        }
		);
		
		//play button listener
		ImageButton btnPlay = (ImageButton)findViewById(R.id.playBtn);
		btnPlay.setOnClickListener(
				
				new OnClickListener() {
		             @Override
		             public void onClick(View v) {
		                  //call to pause
		            	 mp.start();
		             }
		        }
		);
		
		//next button listener
		ImageButton btnNext = (ImageButton)findViewById(R.id.nextBtn);
		btnNext.setOnClickListener(
				
				new OnClickListener() {
					@Override
		             public void onClick(View v) {
		            	 position = position+1;
		            	 mp.reset();
		            	 playSong();
		             }
		        }
		);
				
		//previous button listener
		ImageButton btnPrev = (ImageButton)findViewById(R.id.previousBtn);
		btnPrev.setOnClickListener(
				
				new OnClickListener() {
					@Override
		             public void onClick(View v) {
		            	 position = position-1;
		            	 mp.reset();
		            	 playSong();
		             }
		        }
		);
				
		//return MediaPlayer to initial state
	    mp.reset();
	    mc = new MediaController(this);
		
		//receive the position of song to be played through the bundle
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		position = (Integer) bundle.get("position");
		playSong();
		
		
		
	}
	
	public void playSong(){
		//Content resolver to read media files from phone
		ContentResolver contentResolver = getContentResolver();
		Uri uri = android.provider.MediaStore.Audio.Media.INTERNAL_CONTENT_URI ;

		Cursor cursor = contentResolver.query(uri, null, null, null, null);
		
		if (cursor == null){
			//handle error (get back)
		}
		else{
			cursor.moveToPosition(position);
			
			//Select the column from the content provider with the song
			int songColumn=cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
			
		    //mp.reset();
		    try
		    {
		        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
		        mp.setDataSource(cursor.getString(songColumn));
		        mp.prepare();
		        mp.start();
		    }
		    catch (Exception e) {
		        // TODO: handle exception
		        e.printStackTrace();
		    }
			
		}	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.listen_song, menu);
		return true;
	}

}
