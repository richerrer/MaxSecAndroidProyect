package com.android.maxsec;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;



public class Play extends Activity {

	
	private static PendingIntent pendingIntent;
	private static MainActivity main;
	private Spinner sounds_spinner;
	private ToggleButton run_aplication;
	private SensorManager sensorManager = null;
	private EditText mailtext,passwordtext;
	 
	private static String mail,password;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.play);
	
		this.leerArchivo();
		mailtext = (EditText)findViewById(R.id.mail_text);
		passwordtext = (EditText)findViewById(R.id.password_text);
		
		mailtext.setText(mail);
		passwordtext.setText(password);
		
		sounds_spinner = (Spinner)findViewById(R.id.sound_sppiner);
		ArrayAdapter sounds_adapter = ArrayAdapter.createFromResource(this, R.array.sounds_items,android.R.layout.simple_expandable_list_item_1);
		sounds_spinner.setAdapter(sounds_adapter);
		
		run_aplication = (ToggleButton)findViewById(R.id.turn_on);
		run_aplication.setOnClickListener(new ManejoEvento(this));
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		
	}
	
	public SensorManager getSensorManager(){
		return this.sensorManager;
	}
	
	public void leerArchivo(){
		File tarjeta,file;
		FileReader file_reader;
		BufferedReader br=null;
		StringTokenizer token;
		String linea ,string_mail , string_password;
		
		tarjeta= Environment.getExternalStorageDirectory();
        file = new File(tarjeta.getAbsolutePath(),"maxSec.txt");
		try{
			
	        file_reader = new FileReader(file);
	        br = new BufferedReader(file_reader);
	        
	        while((linea=br.readLine())!=null){
	        	
	        	token = new StringTokenizer(linea,",");
	        	string_mail=token.nextToken();
	        	string_password = token.nextToken();
	        	
	        	mail = string_mail;
	        	password = string_password;
	        }
	        br.close();
	        
	    }
		catch(IOException ex){
			
		}
	}

	class ManejoEvento implements OnClickListener{
		
		
		private Play play;
		private int verificador = 0;
		
		public ManejoEvento(Play play){
			this.play = play;
		}
		
		public void onClick(View view){
			if(run_aplication.isChecked()){
				int segundos = 10;
				verificador = 1; 
				
				FirstWaitTime.setSpinnerSound(sounds_spinner);
				FirstWaitTime.setActivity(play);
				FirstWaitTime.setVerificador("first");
				FirstWaitTime.setVerificadorParada(false);
				
				AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);//Esta clase proporciona acceso a los servicios del sistema de alarma. Estas te permiten programar la aplicación para que se ejecute en algún momento en el futuro.
                Intent intent  = new Intent(play,FirstWaitTime.class);//Creamos un Intent que “apunta” a nuestro BroadcastReceiver
                PendingIntent pIntent = PendingIntent.getBroadcast(play, DEFAULT_KEYS_SHORTCUT, intent,PendingIntent.FLAG_CANCEL_CURRENT);//pasamos el contexto en el que este objeto va a ejecutar la acción de tipo broadcast, 
				alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() +segundos * 1000, pIntent);
		          
		        Toast.makeText(getApplicationContext(), "In a few seconds the application start", Toast.LENGTH_LONG).show();
				play.moveTaskToBack(true);
				
				}
			if(!run_aplication.isChecked()&& verificador ==1){
				play.finish();	
			}
		}
	}
	
	public static String getPasswordTxt(){
		return password;
	}

	public static void startActivityMain(MainActivity main2) {
		main = main2;
		
	}
	
	public static MainActivity getActivityMain() {
		return main;
		
	}
	
	 public String getAddress(){
	    	return main.getAddress();
	  }
	 public double getLatitud(){
	    	return main.getLatitud();
	    }
	    
	    public double getLongitude(){
	    	return main.getLongitude();
	    }

}
