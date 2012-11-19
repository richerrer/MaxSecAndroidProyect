package com.android.maxsec;

import android.media.MediaPlayer;

public class Musica extends Thread{

	private String sound;
	private Password password;
	
	public 	Musica(Password password,String sound){
		this.sound = sound;
		this.password = password;
	}
	
	public void run(){
		if(sound.equals("Alert Sound")){
			MediaPlayer mp3 = MediaPlayer.create(password, R.raw.a);
			mp3.start();
		}
	}
}
