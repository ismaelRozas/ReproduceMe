package com.example.reproduceMe;



public class Song {
	
	private String image;
	private String author;
	private String title;
	private int score;
	
	Song(String ti, String au, int sc){
		this.author = au;
		this.title = ti;
		this.score = sc;
	}
	
	public void setImage(String img){
		this.image = img;
	}
	
	public void setAuthor(String auth){
		this.author = auth;
	}
	
	public void setTitle(String tit){
		this.title = tit;
	}
	
	public void setScore(int sc){
		this.score = sc;
	}
	
	public String getImage(){
		return this.image;
	}
	
	public String getAuthor(){
		return this.author;
	}

	public String getTitle(){
		return this.title;
	}
	
	public int getScore(){
		return this.score;
	}
	
}
