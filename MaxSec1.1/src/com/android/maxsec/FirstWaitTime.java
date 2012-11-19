package com.android.maxsec;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Spinner;
import android.widget.Toast;

public class FirstWaitTime extends BroadcastReceiver{

	private static Play play;
	private static Password password;
	private static Camara camara;
	private static String verificador;
	private static Spinner sound;
	private static boolean parar=false;
	
	public void onReceive(Context context, Intent intent) { 
		if(verificador.equals("first")){

			MySensor sensor =  new MySensor(play,context,sound,Play.getPasswordTxt());
			
		}
		if(verificador.equals("second")){
			if(!parar){//MANEJA EL EVENTO SOLO PARA CUANDO HAN PASADO LOS SEGUNDOS Y NADIE A COLOCADO UN PASSWORD
				password.throwAlarm();
				
			}}
		if(verificador.equals("third")){//MANEJA EL EVENTO PARA PODER TOMARA LA FOTO DES PUES DE CIERTO TIEMPO
			camara.takePhoto();
			
			
		}
		
		if(verificador.equals("fourd")){//MANEJA EL EVENTO PARA PODER CERRAR LA CAMARA DESPUES DE UN LAPSO DE TIEMPO
			camara.finalizar();
			
		}
	} 
	
	public static void setSpinnerSound(Spinner spinner2){
		sound = spinner2;
	}
	
	public static void setActivity(Play play2){
		play = play2;
	}
	
	public static void setActivityPassword(Password password2){
		password = password2;
	}
	
	public static void setActivityCamara(Camara camara2){
		camara = camara2;
	}
	
	public static void setVerificador(String verificador1){
		verificador= verificador1;
	}
	
	public static void setVerificadorParada(boolean verificadorParada){
		parar= verificadorParada;
	}
}
