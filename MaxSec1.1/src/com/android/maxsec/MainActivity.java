package com.android.maxsec;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

private Button run, options,exit;
private double latitude,longitude;
private String address;
private LocationManager location;
private LocationListener location_listener;

private int verificador_gps = 0;

	//onBackPressed
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);//CON ESTO RELACIONAMOS NUESTRA ACTIVITY CON EL ARCHIVO XML "Activity_Main", LA R NOS SIRVE DE PUENTE PARA PODERNOS CONECTAR CON ESTE ARCHIVO XML
		accesToGps();
		run = (Button)findViewById(R.id.play_button);//COMO EL LABEL YA SE ENCUENTAR CREADO LO QUE HACEMOS EN ESTE METODO ES SOLO REFERENCIARLO ALL DEL ARCHIVO XML
	    run.setOnClickListener(new ManejoEvento(this));
		options = (Button)findViewById(R.id.options_button);
		options.setOnClickListener(new ManejoEvento(this));
	    exit = (Button)findViewById(R.id.exit_button);
	    exit.setOnClickListener(new ManejoEvento(this));
	    
	 }
	@Override
    protected void onDestroy() {
        super.onPause();
        if(location!=null &&  location_listener !=null ){
			location.removeUpdates(location_listener);
		}              
    }
	
	
	class ManejoEvento implements OnClickListener {

		private MainActivity principalActivity;
		
		public ManejoEvento(MainActivity principalActivity){
			this.principalActivity = principalActivity;
		}
		public void onClick(View v) {
			if(v.equals(exit)){
				principalActivity.finish();}
			if(v.equals(run)){
				Play.startActivityMain(principalActivity);
				Intent intent = new Intent(principalActivity,Play.class);
		        startActivity(intent);}
			if(v.equals(options)){
				Intent intent = new Intent(principalActivity,Options.class);
		        startActivity(intent);}
		 }
	
	}
	
	public void accesToGps(){
		// Usamos la LocationManager para acceder a la localización o uso del GPS 
		 location = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		 location_listener = new MiLocationListener();
		 location.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 50, location_listener);
	}
	public void findStreet(double latitude,double longitude){
		Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
		
		try {
			List<Address> addresses; addresses = geoCoder.getFromLocation(latitude,  longitude, 1);
		 
		    address = "";
		    if (addresses.size() > 0) {
		        for (int i = 0; i < addresses.get(0).getMaxAddressLineIndex(); i++)
		           address += addresses.get(0).getAddressLine(i) + "\n";
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
	}
	
    class MiLocationListener implements LocationListener {
		
		public void onLocationChanged(Location loc)
		{
			latitude = loc.getLatitude();
			longitude = loc.getLongitude();
			findStreet(latitude,longitude);
			//Toast.makeText( getApplicationContext(),+latitude+" "+longitude+" "+address,Toast.LENGTH_LONG ).show();
			
			/*if(verificador_gps==0&&latitude ==0&&longitude==0){
				latitude = loc.getLatitude();
				longitude = loc.getLongitude();
				findStreet();
				Toast.makeText( getApplicationContext(),address,Toast.LENGTH_LONG ).show();
				verificador_gps++;
			}*/
			
			
		}
	
		public void onProviderDisabled(String provider){
	        
			Toast.makeText( getApplicationContext(),"Attention This application requires that you turn on your gps ",Toast.LENGTH_LONG ).show();
		}
	
		public void onProviderEnabled(String provider){}
		public void onStatusChanged(String provider, int status, Bundle extras){}
	}
    
    public String getAddress(){
    	return address;
    }
    
    public double getLatitud(){
    	return latitude;
    }
    
    public double getLongitude(){
    	return longitude;
    }

}
