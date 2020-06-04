package com.application.ksgu.Model;

import com.google.gson.annotations.SerializedName;

public class SuratDetail{

	@SerializedName("kontak")
	private String kontak;

	@SerializedName("created")
	private String created;

	@SerializedName("pemohon")
	private String pemohon;

	@SerializedName("catatan")
	private String catatan;

	@SerializedName("tanggal")
	private String tanggal;

	@SerializedName("perihal")
	private String perihal;

	@SerializedName("nomor")
	private String nomor;

	@SerializedName("updated")
	private String updated;

	@SerializedName("status")
	private String status;

	public void setKontak(String kontak){
		this.kontak = kontak;
	}

	public String getKontak(){
		return kontak;
	}

	public void setCreated(String created){
		this.created = created;
	}

	public String getCreated(){
		return created;
	}

	public void setPemohon(String pemohon){
		this.pemohon = pemohon;
	}

	public String getPemohon(){
		return pemohon;
	}

	public void setCatatan(String catatan){
		this.catatan = catatan;
	}

	public String getCatatan(){
		return catatan;
	}

	public void setTanggal(String tanggal){
		this.tanggal = tanggal;
	}

	public String getTanggal(){
		return tanggal;
	}

	public void setPerihal(String perihal){
		this.perihal = perihal;
	}

	public String getPerihal(){
		return perihal;
	}

	public void setNomor(String nomor){
		this.nomor = nomor;
	}

	public String getNomor(){
		return nomor;
	}

	public void setUpdated(String updated){
		this.updated = updated;
	}

	public String getUpdated(){
		return updated;
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
			"SuratDetail{" + 
			"kontak = '" + kontak + '\'' + 
			",created = '" + created + '\'' + 
			",pemohon = '" + pemohon + '\'' + 
			",catatan = '" + catatan + '\'' + 
			",tanggal = '" + tanggal + '\'' + 
			",perihal = '" + perihal + '\'' + 
			",nomor = '" + nomor + '\'' + 
			",updated = '" + updated + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}