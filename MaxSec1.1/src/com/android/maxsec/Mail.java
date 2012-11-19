package com.android.maxsec;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import android.util.Log;


public class Mail extends Thread{

	private String myEmail;
	private String yourEmail;
	private String passwordEmail;
	private String address =" ";
	double latitude,longitude;
	
	public Mail(String yourEmail,double latitude,double longitude,String address ){
		myEmail = "maxsec10@gmail.com";//"maxsec10@gmail.com"
		passwordEmail = "maxsecurity";//maxsecurity
		this.yourEmail = yourEmail;
		this.latitude = latitude;
		this.longitude = longitude;
		this.address = address;
     }
	
	public void run(){
		try
		{
			sleep(10000);
			
		}catch(Exception ex){
			
		}
		Properties props = initializedProperties();
        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);
        createMessage(message,latitude,longitude,address);
        sendMessage(message, session);
	}
	
	private Properties initializedProperties(){
		Properties props = new Properties();
		
    	props.setProperty("mail.smtp.host", "smtp.gmail.com");// Nombre del host de correo, es smtp.gmail.com
    	props.setProperty("mail.smtp.starttls.enable", "true");// TLS si está disponible
        props.setProperty("mail.smtp.port","587");// Puerto de gmail para envio de correos
        props.setProperty("mail.smtp.user", myEmail);// Nombre del usuario
        props.setProperty("mail.smtp.auth", "true");// Si requiere o no usuario y password para conectarse.

    	return props;
	}
	
	private void createMessage(Message message,double latitude,double longitude,String address){
		try{
			BodyPart texto = new MimeBodyPart();
	  		texto.setText("La ubicación de la foto es la siguiente: \n Latitud: "+latitude+" \n Longitude: "+longitude+"\n Dirección: "+address);
	  		 
	  		BodyPart adjunto = new MimeBodyPart(); 
	  	    DataSource source = new FileDataSource("/sdcard/ric.jpg"); //nombre del adjunto a enviar
	  	    adjunto.setDataHandler(new DataHandler(source)); 
	  	    adjunto.setFileName("maxSec.jpg"); //nombre del adjunto segun como se va a visualizar
	  	     
	  	    MimeMultipart multiParte = new MimeMultipart();//SE ENCARGA DE UNIR TODO EL MENSAJE TEXTOS Y ADJUNTO

	  	    multiParte.addBodyPart(texto);
	  	    multiParte.addBodyPart(adjunto);
	  	    
	  	    message.setSubject("Alert Message");
	   		 
	   	    message.setContent(multiParte);
	  	}
		catch(Exception ex){
			
		}
	}
	
	public Transport connectToAccount(Session session){

		try{
			Transport t = session.getTransport("smtp");
	        t.connect(myEmail,passwordEmail);
	        return t;
		}
		catch(Exception ex){
			return null;
		}
	}
	
	private void sendMessage(Message message,Session session){
		
		try{
			message.setFrom(new InternetAddress(myEmail));// Quien envia el correo
   		    message.addRecipient(Message.RecipientType.TO, new InternetAddress(yourEmail));// A quien va dirigido
   		    Transport t = connectToAccount(session);
   		    t.sendMessage(message,message.getAllRecipients());
	        t.close();
   	    
   	   }catch(AddressException ex){ }
		catch(MessagingException ex){
   		 Log.e("MailApp", "MessagingException", ex); }
   	    catch(Exception ex){
   		 Log.e("MailApp", "Could not send email", ex);}

	}
}

