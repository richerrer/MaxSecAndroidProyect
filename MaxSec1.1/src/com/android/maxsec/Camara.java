package com.android.maxsec;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.maxsec.MainActivity.ManejoEvento;

public class Camara extends Activity  {

	private Camera camera;
	private Preview preview;
	private static Play play;
	private static MainActivity main;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.camara);//CON ESTO RELACIONAMOS NUESTRA ACTIVITY CON EL ARCHIVO XML "Activity_Main", LA R NOS SIRVE DE PUENTE PARA PODERNOS CONECTAR CON ESTE ARCHIVO XML
		
		camera = getInstance();
		preview = new Preview(this,camera);
		FrameLayout ventana_camara = (FrameLayout)findViewById(R.id.frame);
		ventana_camara.addView(preview);
		
		waitTakePhoto();
		closeCamera();
		
	}
	
	public static Camera getInstance(){
		Camera c = null;
		try{
			c = Camera.open();
			c.setDisplayOrientation(90);
		}
		catch(Exception ex){
			
		}return c;
	}
	
	private PictureCallback mPicture = new PictureCallback() {

        public void onPictureTaken(byte[] data, Camera camera) {

        	FileOutputStream outStream = null;
			try {
				
				outStream = new FileOutputStream(String.format("/sdcard/ric.jpg", System.currentTimeMillis()));
				outStream.write(data);
				outStream.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
			
			
		}
    };
    
    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();              
    }

    public void finalizar(){
    	
        int segundos =4;
    	Tres.setLatitude(play.getLatitud());
    	Tres.setLongitude(play.getLongitude());
    	Tres.setAddress(play.getAddress());
    	Tres.setVerificador("five");
    	Tres.setActivityCamara(this);
    	AlarmManager alarmManager3 = (AlarmManager)getSystemService(ALARM_SERVICE);//Esta clase proporciona acceso a los servicios del sistema de alarma. Estas te permiten programar la aplicación para que se ejecute en algún momento en el futuro.
        Intent intent3  = new Intent(this,Tres.class);//Creamos un Intent que “apunta” a nuestro BroadcastReceiver
        PendingIntent pIntent3 = PendingIntent.getBroadcast(this, DEFAULT_KEYS_SHORTCUT, intent3,PendingIntent.FLAG_CANCEL_CURRENT);//pasamos el contexto en el que este objeto va a ejecutar la acción de tipo broadcast, 
		alarmManager3.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() +segundos * 1000, pIntent3);
   
    	this.finish();
    	play.finish();
    	main.finish();
    	
    }
   

    private void releaseCamera(){
        if (camera != null){
            camera.release();        // release the camera for other applications
            camera = null;
        }
    }
    
    public void takePhoto(){
    	 camera.takePicture(null, null, mPicture);
    }
    
    public void waitTakePhoto(){
    	
    	int segundos = 2;
        
    	FirstWaitTime.setVerificador("third");
    	FirstWaitTime.setActivityCamara(this);
    	
    	AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);//Esta clase proporciona acceso a los servicios del sistema de alarma. Estas te permiten programar la aplicación para que se ejecute en algún momento en el futuro.
        Intent intent  = new Intent(this,FirstWaitTime.class);//Creamos un Intent que “apunta” a nuestro BroadcastReceiver
        PendingIntent pIntent = PendingIntent.getBroadcast(this, DEFAULT_KEYS_SHORTCUT, intent,PendingIntent.FLAG_CANCEL_CURRENT);//pasamos el contexto en el que este objeto va a ejecutar la acción de tipo broadcast, 
		alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() +segundos * 1000, pIntent);
  
    }
    
 public void closeCamera(){
    	
    	int segundos =3;
    	
    	SecondWaitTime.setVerificador("fourd");
    	SecondWaitTime.setActivityCamara(this);
    	AlarmManager alarmManager2 = (AlarmManager)getSystemService(ALARM_SERVICE);//Esta clase proporciona acceso a los servicios del sistema de alarma. Estas te permiten programar la aplicación para que se ejecute en algún momento en el futuro.
        Intent intent2  = new Intent(this,SecondWaitTime.class);//Creamos un Intent que “apunta” a nuestro BroadcastReceiver
        PendingIntent pIntent2 = PendingIntent.getBroadcast(this, DEFAULT_KEYS_SHORTCUT, intent2,PendingIntent.FLAG_CANCEL_CURRENT);//pasamos el contexto en el que este objeto va a ejecutar la acción de tipo broadcast, 
		alarmManager2.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() +segundos * 1000, pIntent2);
   
		
    }
 
 public void onBackPressed(){//DESACTIVA EL BOTON DE REGRSO
		
	}

public static void setActivityPlay(Play play2) {
	play = play2;
	main = play.getActivityMain();
}
public static Play getPlayActivity(){
	return play;
}
 
 


 
}
