package com.application.ksgu.Model;

import com.google.gson.annotations.SerializedName;

public class Negara{

	@SerializedName("code")
	private String code;

	@SerializedName("id")
	private int id;

	@SerializedName("label")
	private String label;

	@SerializedName("value")
	private String value;

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return code;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setLabel(String label){
		this.label = label;
	}

	public String getLabel(){
		return label;
	}

	public void setValue(String value){
		this.value = value;
	}

	public String getValue(){
		return value;
	}

	@Override
 	public String toString(){
		return 
			"Negara{" + 
			"code = '" + code + '\'' + 
			",id = '" + id + '\'' + 
			",label = '" + label + '\'' + 
			",value = '" + value + '\'' + 
			"}";
		}
}