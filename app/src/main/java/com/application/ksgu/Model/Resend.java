package com.application.ksgu.Model;

import com.google.gson.annotations.SerializedName;

public class Resend{

	@SerializedName("message")
	private String message;

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"Resend{" + 
			"message = '" + message + '\'' + 
			"}";
		}
}