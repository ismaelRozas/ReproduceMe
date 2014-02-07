package com.example.reproduceMe;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

public class WelcomeScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_welcome_screen);
		
		ImageButton btnPlay = (ImageButton)findViewById(R.id.playButton);
		btnPlay.setOnClickListener(
				
				new OnClickListener() {
		             @Override
		             public void onClick(View v) {
		                  //Creamos el Intent
		                  Intent intent =
		                          new Intent(WelcomeScreen.this, Player.class);
		 
		                  //Iniciamos la nueva actividad
		                  startActivity(intent);
		             }
		        }
		);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welcome_screen, menu);
		return true;
	}
	
	

}
