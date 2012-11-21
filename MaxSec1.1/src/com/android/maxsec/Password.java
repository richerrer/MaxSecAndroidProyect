package com.android.maxsec;



import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;



import com.android.maxsec.R.raw;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class Password extends Activity{

	private Button aceptar,cancelar,b1,b2,b3,b4,b5,b6,b7,b8,b9,b0;
	private EditText clave;
	private String sound;
	private static Play play;
	private static int verificador=0;
	private String password;
	

	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.password);
		
		referenciarBotones();
		getStringSonido();	
		getPass();
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//DESACTIVA EL BOTON HOME
		if(verificador ==0){
			lanzarCamara();
		}
		
    }
	public void onBackPressed(){//DESACTIVA EL BOTON DE REGRSO
		
	}
	
	public void lanzarCamara(){
		int segundos =10;
		FirstWaitTime.setActivityPassword(this);
		FirstWaitTime.setVerificador("second");//INDICO QUE REALIZARA LA TAREA NUMERO 2
		
		AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);//Esta clase proporciona acceso a los servicios del sistema de alarma. Estas te permiten programar la aplicación para que se ejecute en algún momento en el futuro.
        Intent intent  = new Intent(this,FirstWaitTime.class);//Creamos un Intent que “apunta” a nuestro BroadcastReceiver
        PendingIntent pIntent = PendingIntent.getBroadcast(this, DEFAULT_KEYS_SHORTCUT, intent,PendingIntent.FLAG_CANCEL_CURRENT);//pasamos el contexto en el que este objeto va a ejecutar la acción de tipo broadcast, 
		alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() +segundos * 1000, pIntent);
	}
	
	public void onAttachedToWindow(){//DESACTIVA EL BOTON DE HOME
	    this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
	    super.onAttachedToWindow();
	}
	
	public void getStringSonido(){
		Bundle boundle = getIntent().getExtras();
	    sound =boundle.getString("type_of_sound");

	}
	public void getPass(){
		Bundle boundle = getIntent().getExtras();
	    password =boundle.getString("password");

	}
	public void referenciarBotones(){
		aceptar = (Button)findViewById(R.id.button_aceptar);
		aceptar.setOnClickListener(new ManejoEventoVerificacion(this));
		cancelar = (Button)findViewById(R.id.button_cancelar);
		cancelar.setOnClickListener(new ManejoEventoVerificacion(this));
		b1 = (Button)findViewById(R.id.button_1);
		b1.setOnClickListener(new ManejoEvento());
		b2 = (Button)findViewById(R.id.button_2);
		b2.setOnClickListener(new ManejoEvento());
		b3 = (Button)findViewById(R.id.button_3);
		b3.setOnClickListener(new ManejoEvento());
		b4 = (Button)findViewById(R.id.button_4);
		b4.setOnClickListener(new ManejoEvento());
		b5 = (Button)findViewById(R.id.button_5);
		b5.setOnClickListener(new ManejoEvento());
		b6 = (Button)findViewById(R.id.button_6);
		b6.setOnClickListener(new ManejoEvento());
		b7 = (Button)findViewById(R.id.button_7);
		b7.setOnClickListener(new ManejoEvento());
		b8 = (Button)findViewById(R.id.button_8);
		b8.setOnClickListener(new ManejoEvento());
		b9 = (Button)findViewById(R.id.button_9);
		b9.setOnClickListener(new ManejoEvento());
		b0 = (Button)findViewById(R.id.button_0);
		b0.setOnClickListener(new ManejoEvento());
		clave = (EditText)findViewById(R.id.editText1);
	}
	
	public void setPassword(String password,String numero){
		String completePassword;
		completePassword = password + numero;
		clave.setText(completePassword);
	}
	
	
	class ManejoEvento implements OnClickListener{
		String numero;
	    String passwordText;
	    
		public void onClick(View view){
		    passwordText = clave.getText().toString();
		    Button boton = (Button) view;
		    numero =boton.getText().toString();	
		    setPassword(passwordText,numero);
		}
	}
	
	public void throwAlarm(){
		//Toast.makeText( getApplicationContext(),play.getAddress(),Toast.LENGTH_LONG).show();
		
		Camara.setActivityPlay(play);
	    makeSound();
		Intent intentCamera = new Intent(this,Camara.class);
		startActivity(intentCamera);
		finish();
	}
	
	public void makeSound(){
		if(sound.equals("City Alarm")){
		   MediaPlayer mp3 = MediaPlayer.create(this, R.raw.a);
		   mp3.start();
		}
		if(sound.equals("Robot Alarm")){
			   MediaPlayer mp3 = MediaPlayer.create(this, R.raw.b);
			   mp3.start();
		}
		if(sound.equals("FireTruck Alarm")){
			   MediaPlayer mp3 = MediaPlayer.create(this, R.raw.c);
			   mp3.start();
		}
		if(sound.equals("Car Alarm")){
			   MediaPlayer mp3 = MediaPlayer.create(this, R.raw.d);
			   mp3.start();
     	}
	}
	
	
	public static void setActivityPlay(Play play2) {
		
		play = play2;
	}
	
public static void setVerificador(int verificador2) {
		
		verificador = verificador2;
	}
	
	class ManejoEventoVerificacion implements OnClickListener{
		
		public  String TAG = null;
		private Password activity_password;
		
		public ManejoEventoVerificacion(Password activity_password){
			 this.activity_password =activity_password;
		}
		
		
		public void onClick(View view){
		    Button boton = (Button) view;
		    String botonText =boton.getText().toString();
		    
		    if(botonText.equals("aceptar")){
		    	 String passwordText = clave.getText().toString();
		    	 if(passwordText.equals(password)){
		    		 FirstWaitTime.setVerificadorParada(true);
		    		 activity_password.finish();	
		    		 Toast.makeText(activity_password, "ALARMA DESACTIVADA", Toast.LENGTH_SHORT).show();
		    	 }
		    	 else{
		    		 if(verificador==0){
		    			 FirstWaitTime.setVerificadorParada(true);//ENVIO TRUE PARA QUE LA CLASE FIRSTWAITTIME NO MAEJE ESTA OPCION
		    			 activity_password.throwAlarm();
		    		 }else{
		    			 clave.setText("");
		    			 Toast.makeText(activity_password, "Contraseña Incorrecta", Toast.LENGTH_SHORT).show();
		    		 }}
		    }
		    if(botonText.equals("cancelar")&&verificador==0){
		    	FirstWaitTime.setVerificadorParada(true);//ENVIO TRUE PARA QUE LA CLASE FIRSTWAITTIME NO MAEJE ESTA OPCION
		    	activity_password.throwAlarm();
		     }
		}
		
	}

	
}

