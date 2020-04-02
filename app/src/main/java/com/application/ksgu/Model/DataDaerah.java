package com.application.ksgu.Model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import ir.mirrajabi.searchdialog.core.Searchable;

@Generated("com.robohorse.robopojogenerator")
public class DataDaerah implements Searchable {

	@SerializedName("provinsi")
	private String provinsi;

	@SerializedName("nama")
	private String nama;

	@SerializedName("id")
	private int id;

	@SerializedName("status")
	private String status;

	public void setProvinsi(String provinsi){
		this.provinsi = provinsi;
	}

	public String getProvinsi(){
		return provinsi;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"DataDaerah{" + 
			"provinsi = '" + provinsi + '\'' + 
			",nama = '" + nama + '\'' + 
			",id = '" + id + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}

	@Override
	public String getTitle() {
		return nama;
	}
}