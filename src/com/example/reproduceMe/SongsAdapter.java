package com.example.reproduceMe;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

        public static PlaceHolder generate(View convertView) {
                PlaceHolder placeHolder = new PlaceHolder();
                placeHolder.title = (TextView) convertView
                                .findViewById(R.id.titleSong);
                placeHolder.author = (TextView) convertView
                                .findViewById(R.id.authorSong);
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
        }
        placeHolder.title.setText(songList.get(position).getTitle());
        placeHolder.author.setText(String.valueOf(songList.get(position).getScore()));
        return (convertView);
}

	
}
