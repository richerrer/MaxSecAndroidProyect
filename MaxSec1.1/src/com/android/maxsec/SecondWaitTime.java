package com.android.maxsec;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class SecondWaitTime extends BroadcastReceiver{//SE CREA OTRO RECIEVER DEBIDO A QUE UNA ACTIVITY NO PUEDE MANEJAR EL MISMORECIEVER PARA 2 TIEMPOS DIFERENTES

	
	private static Camara camara;
	private static String verificador;
	public void onReceive(Context context, Intent intent) { 
		
		if(verificador.equals("fourd")){//MANEJA EL EVENTO PARA PODER CERRAR LA CAMARA DESPUES DE UN LAPSO DE TIEMPO
			
			camara.lanzarPantallaBloqueo();
			camara.finalizar();
		}
		
	} 
	
	
	public static void setActivityCamara(Camara camara2){
		camara = camara2;
	}
	
	public static void setVerificador(String verificador1){
		verificador= verificador1;
	}
	
	
}
