package com.android.maxsec;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Options extends Activity{
	
	private EditText mailtxt,old_password,new_password;
    private Button aceptar , cancelar;
	private String oldPassword;
    
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.options);
		referenciarBotones();
	}
	
	private void referenciarBotones(){
		mailtxt = (EditText)findViewById(R.id.mail_text2);
		mailtxt.setText(readFile());
		old_password = (EditText)findViewById(R.id.password_text_old);
		new_password =  (EditText)findViewById(R.id.password_text_new);
		
		aceptar = (Button)findViewById(R.id.button_aceptar2);
		aceptar.setOnClickListener(new ManejadorEvento(this));
		cancelar = (Button)findViewById(R.id.button_cancelar2);
		cancelar.setOnClickListener(new ManejadorEvento(this));
	}
	
	private String readFile(){
		File tarjeta,file;
		FileReader fr;
		StringTokenizer token;
		String mail=null,password=null,line=null;
		BufferedReader br;
		
		tarjeta = Environment.getExternalStorageDirectory();
		file = new File(tarjeta.getAbsolutePath(),"maxSec.txt");
		try{
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			while((line = br.readLine())!=null){
				token = new StringTokenizer(line,",");
				mail = token.nextToken();
				password = token.nextToken();
			}
			oldPassword = password;
			fr.close();
			return mail;
		}
		catch(Exception ex){
			return null;
		}
	}
	
	private Boolean correctPassword(String oldPassword,String newPassword){
		
		if(validationText(newPassword)){
			if(oldPassword.equals(this.oldPassword)){
				Toast.makeText(this, "The password has been changed", Toast.LENGTH_SHORT).show();
				return true;}
			else{
				Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show();
			    return false;}
		}else{
			return false;
		}
	}
		
	
	private Boolean validationText(String newPassword){
		
		try{
			Integer.parseInt(newPassword);
			return true;
		}catch(Exception ex){
			Toast.makeText(this, "The password is only a number", Toast.LENGTH_SHORT).show();
		    return false;
		}
	}
	
	private void writeFiles(String newPassword,String mail){
		File tarjeta, file;
		FileWriter fw=null;
		String line = mail+","+newPassword;
		
		tarjeta = Environment.getExternalStorageDirectory();
		file = new File (tarjeta.getAbsolutePath(),"maxSec.txt");
		try{
			fw = new FileWriter(file,false);
			fw.append(line);
			fw.close();
		}catch(Exception ex){
			
		}
		
	}
	
	class ManejadorEvento implements OnClickListener{
		
		private Options options;
		
		public ManejadorEvento(Options options){
			this.options = options;
		}
		public void onClick(View view){
			Button boton = (Button)view;
			if(boton.equals(aceptar)){
				if(correctPassword(old_password.getText().toString(),new_password.getText().toString())){
					writeFiles(new_password.getText().toString(),mailtxt.getText().toString());
				    options.finish();
				}
			}
			else{
				new_password.setText("");
				old_password.setText("");
				mailtxt.setText("");
			}
		}
	}
}
