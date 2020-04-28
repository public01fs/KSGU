package com.application.ksgu.Model;

import com.google.gson.annotations.SerializedName;

public class PelautService{

	@SerializedName("NamaPelaut")
	private String namaPelaut;

	@SerializedName("StatusPelaut")
	private String statusPelaut;

	@SerializedName("JK")
	private String jK;

	@SerializedName("TempatLahir")
	private String tempatLahir;

	@SerializedName("Sertifikat")
	private String sertifikat;

	@SerializedName("hasile")
	private String hasile;

	@SerializedName("Umur")
	private String umur;

	@SerializedName("IdPelaut")
	private int idPelaut;

	@SerializedName("KodePelaut")
	private String kodePelaut;

	@SerializedName("FotoPelaut")
	private String fotoPelaut;

	@SerializedName("TglLahir")
	private String tglLahir;

	public void setNamaPelaut(String namaPelaut){
		this.namaPelaut = namaPelaut;
	}

	public String getNamaPelaut(){
		return namaPelaut;
	}

	public void setStatusPelaut(String statusPelaut){
		this.statusPelaut = statusPelaut;
	}

	public String getStatusPelaut(){
		return statusPelaut;
	}

	public void setJK(String jK){
		this.jK = jK;
	}

	public String getJK(){
		return jK;
	}

	public void setTempatLahir(String tempatLahir){
		this.tempatLahir = tempatLahir;
	}

	public String getTempatLahir(){
		return tempatLahir;
	}

	public void setSertifikat(String sertifikat){
		this.sertifikat = sertifikat;
	}

	public String getSertifikat(){
		return sertifikat;
	}

	public void setHasile(String hasile){
		this.hasile = hasile;
	}

	public String getHasile(){
		return hasile;
	}

	public void setUmur(String umur){
		this.umur = umur;
	}

	public String getUmur(){
		return umur;
	}

	public void setIdPelaut(int idPelaut){
		this.idPelaut = idPelaut;
	}

	public int getIdPelaut(){
		return idPelaut;
	}

	public void setKodePelaut(String kodePelaut){
		this.kodePelaut = kodePelaut;
	}

	public String getKodePelaut(){
		return kodePelaut;
	}

	public void setFotoPelaut(String fotoPelaut){
		this.fotoPelaut = fotoPelaut;
	}

	public String getFotoPelaut(){
		return fotoPelaut;
	}

	public void setTglLahir(String tglLahir){
		this.tglLahir = tglLahir;
	}

	public String getTglLahir(){
		return tglLahir;
	}

	@Override
 	public String toString(){
		return 
			"PelautService{" + 
			"namaPelaut = '" + namaPelaut + '\'' + 
			",statusPelaut = '" + statusPelaut + '\'' + 
			",jK = '" + jK + '\'' + 
			",tempatLahir = '" + tempatLahir + '\'' + 
			",sertifikat = '" + sertifikat + '\'' + 
			",hasile = '" + hasile + '\'' + 
			",umur = '" + umur + '\'' + 
			",idPelaut = '" + idPelaut + '\'' + 
			",kodePelaut = '" + kodePelaut + '\'' + 
			",fotoPelaut = '" + fotoPelaut + '\'' + 
			",tglLahir = '" + tglLahir + '\'' + 
			"}";
		}
}