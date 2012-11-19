package com.android.maxsec;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.StringTokenizer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.widget.Toast;

public class Tres extends BroadcastReceiver{//SE CREA OTRO RECIEVER DEBIDO A QUE UNA ACTIVITY NO PUEDE MANEJAR EL MISMORECIEVER PARA 2 TIEMPOS DIFERENTES

	
	private static Camara camara;
	private static String verificador;
	private String mail;
	private static double latitude,longitude;
	private static String address;
	
	public void onReceive(Context context, Intent intent) { 
		
		mail = readFile();
		if(verificador.equals("five")){//MANEJA EL EVENTO PARA PODER CERRAR LA CAMARA DESPUES DE UN LAPSO DE TIEMPO
		    
			Mail m = new Mail(mail,latitude,longitude,address);
			m.start();
		}
	} 

	public static void setActivityCamara(Camara camara2){
		camara = camara2;
	}
	
	

	public static void setVerificador(String verificador1) {
		verificador = verificador1;
	}
	public static void setLatitude(double latitude1) {
		latitude = latitude1;
	}
	public static void setLongitude(double longitude1) {
		longitude = longitude1;
	}
	public static void setAddress(String address1) {
		address = address1;
	}
	
	private String readFile(){
		File tarjeta,file;
		FileReader fr;
		StringTokenizer token;
		String mail=null,line=null;
		BufferedReader br;
		
		tarjeta = Environment.getExternalStorageDirectory();
		file = new File(tarjeta.getAbsolutePath(),"maxSec.txt");
		try{
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			while((line = br.readLine())!=null){
				token = new StringTokenizer(line,",");
				mail = token.nextToken();
				
			}
			fr.close();
			return mail;
		}
		catch(Exception ex){
			return null;
		}
	}
	
}

