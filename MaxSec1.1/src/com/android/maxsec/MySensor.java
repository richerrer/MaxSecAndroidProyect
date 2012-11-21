package com.android.maxsec;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Spinner;
import android.widget.Toast;


public class MySensor implements SensorEventListener{

	private Play play;
	private MySensor sensor;
	private float curX = 0, curY = 0, curZ = 0;
	private Context context;
	float[] masData;
	Intent intent1;
	private String clave;
	
	private Sensor sensorAcelerometro = null;
	private SensorManager sensorManager = null;
	private String sound;
	
	public MySensor(Play play,Context context,Spinner sound,String clave){//CONTEXT EN UNA INTERFACE QUE ENTREGA INFORMACION SOBRE EL AMBIENTE DE LA APLICACION
		this.clave = clave;
		this.context=context;
		this.play = play;
		this.sound = sound.getSelectedItem().toString();
		Password.setVerificador(0);
		Password.setActivityPlay(play);
		intent1 = new Intent(context,Password.class);
		intent1.putExtra("type_of_sound", this.sound);
		intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//CONTROLA COMO VA A ASER MANEJADO ESTE INTENT
        sensorManager =play.getSensorManager();
		sensorAcelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorManager.registerListener(this, sensorAcelerometro, SensorManager.SENSOR_DELAY_NORMAL);
	}	
	
	public void onAccuracyChanged(Sensor sensor,int accuracy){//implementaremos las acciones a realizar cuando se cambia la precisión de un sensor.
    	
    }
    public void onSensorChanged(SensorEvent event){// la cual nos permite implementar las acciones a realizar cuando un sensor registre un cambio.
    	int limit =12;
        float movement;
    	synchronized (this){
    		masData = event.values;
            curX = masData[0];
            curY = masData[1];
            curZ = masData[2];
            
            movement = Math.abs((curX + curY + curZ)) ;
  	      
  	        if ((movement >limit)) {
  	        	 sensorManager.unregisterListener(this);//APAGO EL SENSOR UNA VEZ ESTE DETECTA EL MOVIMIENTO
  	        	 intent1.putExtra("password", clave);
  	        	 context.startActivity(intent1);//LANZO LA SIGUIENTE ACTIVITY
  	        	 } 
  	        }
    	}
    }
