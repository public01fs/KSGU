package com.application.ksgu.Model;

import com.google.gson.annotations.SerializedName;

public class Count{

	@SerializedName("request")
	private int request;

	@SerializedName("proses")
	private int proses;

	@SerializedName("dikembalikan")
	private int dikembalikan;

	public void setRequest(int request){
		this.request = request;
	}

	public int getRequest(){
		return request;
	}

	public void setProses(int proses){
		this.proses = proses;
	}

	public int getProses(){
		return proses;
	}

	public void setDikembalikan(int dikembalikan){
		this.dikembalikan = dikembalikan;
	}

	public int getDikembalikan(){
		return dikembalikan;
	}

	@Override
 	public String toString(){
		return 
			"Count{" + 
			"request = '" + request + '\'' + 
			",proses = '" + proses + '\'' + 
			",dikembalikan = '" + dikembalikan + '\'' + 
			"}";
		}
}