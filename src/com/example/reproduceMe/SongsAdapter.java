package com.example.reproduceMe;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SongsAdapter extends ArrayAdapter<Object> {
	Context context;
	private ArrayList<Song> songList;
	
	public SongsAdapter(Context ctx, ArrayList<Song> songs) {
		super(ctx, R.layout.song);
		this.context = ctx;
		this.songList = songs;
	}
	
	@Override
	public int getCount() {
		return songList.size();
	}
	
	private static class PlaceHolder {

        TextView title;
        TextView author;
        TextView score;
        ImageView star1;
        ImageView star2;
        ImageView star3;
        ImageView star4;
        ImageView star5;
        
        public static PlaceHolder generate(View convertView) {
                PlaceHolder placeHolder = new PlaceHolder();
                placeHolder.title = (TextView) convertView
                                .findViewById(R.id.titleSong);
                placeHolder.author = (TextView) convertView
                                .findViewById(R.id.authorSong);
                placeHolder.score = (TextView) convertView
                				.findViewById(R.id.scoreSong);
                placeHolder.star1 = (ImageView) convertView.
                		findViewById(R.id.imageStar1);
                placeHolder.star2 = (ImageView) convertView.
                		findViewById(R.id.imageStar2);
                placeHolder.star3 = (ImageView) convertView.
                		findViewById(R.id.imageStar3);
                placeHolder.star4 = (ImageView) convertView.
                		findViewById(R.id.imageStar4);
                placeHolder.star5 = (ImageView) convertView.
                		findViewById(R.id.imageStar5);
                
                return placeHolder;
        }

	}
	
    public View getView(int position, View convertView, ViewGroup parent) {
        PlaceHolder placeHolder;
        if (convertView == null) {
                convertView = View.inflate(context, R.layout.song, null);
                placeHolder = PlaceHolder.generate(convertView);
                convertView.setTag(placeHolder);
        } else {
                placeHolder = (PlaceHolder) convertView.getTag();
                placeHolder.star1.setImageResource(R.drawable.star_icon2);
                placeHolder.star2.setImageResource(R.drawable.star_icon2);
                placeHolder.star3.setImageResource(R.drawable.star_icon2);
                placeHolder.star4.setImageResource(R.drawable.star_icon2);
                placeHolder.star5.setImageResource(R.drawable.star_icon2);
                
        }
        placeHolder.title.setText(songList.get(position).getTitle());
        placeHolder.author.setText(songList.get(position).getAuthor());
        placeHolder.score.setText(String.valueOf(songList.get(position).getScore()));
        
        // Fill the stars according the song score
        int score = songList.get(position).getScore();
        
        if (score>0){
        	placeHolder.star1.setImageResource(R.drawable.star_icon1);
        }
        if (score>1){
        	placeHolder.star2.setImageResource(R.drawable.star_icon1);
        }
        if (score>2){
        	placeHolder.star3.setImageResource(R.drawable.star_icon1);
        }
        if (score>3){
        	placeHolder.star4.setImageResource(R.drawable.star_icon1);
        }
        if (score>4){
        	placeHolder.star5.setImageResource(R.drawable.star_icon1);
        }
        return (convertView);
    }
    

}
