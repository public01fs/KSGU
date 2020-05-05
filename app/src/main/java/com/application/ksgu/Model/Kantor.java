package com.application.ksgu.Model;

import com.google.gson.annotations.SerializedName;

public class Kantor{

	@SerializedName("UNIT")
	private String uNIT;

	@SerializedName("KODE_KANTOR")
	private String kODEKANTOR;

	@SerializedName("TEMPAT_KANTOR")
	private String tEMPATKANTOR;

	@SerializedName("ALAMAT_KANTOR")
	private Object aLAMATKANTOR;

	@SerializedName("KET_KANTOR")
	private Object kETKANTOR;

	@SerializedName("IDKANTOR")
	private int iDKANTOR;

	@SerializedName("KANTOR")
	private String kANTOR;

	public void setUNIT(String uNIT){
		this.uNIT = uNIT;
	}

	public String getUNIT(){
		return uNIT;
	}

	public void setKODEKANTOR(String kODEKANTOR){
		this.kODEKANTOR = kODEKANTOR;
	}

	public String getKODEKANTOR(){
		return kODEKANTOR;
	}

	public void setTEMPATKANTOR(String tEMPATKANTOR){
		this.tEMPATKANTOR = tEMPATKANTOR;
	}

	public String getTEMPATKANTOR(){
		return tEMPATKANTOR;
	}

	public void setALAMATKANTOR(Object aLAMATKANTOR){
		this.aLAMATKANTOR = aLAMATKANTOR;
	}

	public Object getALAMATKANTOR(){
		return aLAMATKANTOR;
	}

	public void setKETKANTOR(Object kETKANTOR){
		this.kETKANTOR = kETKANTOR;
	}

	public Object getKETKANTOR(){
		return kETKANTOR;
	}

	public void setIDKANTOR(int iDKANTOR){
		this.iDKANTOR = iDKANTOR;
	}

	public int getIDKANTOR(){
		return iDKANTOR;
	}

	public void setKANTOR(String kANTOR){
		this.kANTOR = kANTOR;
	}

	public String getKANTOR(){
		return kANTOR;
	}

	@Override
 	public String toString(){
		return 
			"Kantor{" + 
			"uNIT = '" + uNIT + '\'' + 
			",kODE_KANTOR = '" + kODEKANTOR + '\'' + 
			",tEMPAT_KANTOR = '" + tEMPATKANTOR + '\'' + 
			",aLAMAT_KANTOR = '" + aLAMATKANTOR + '\'' + 
			",kET_KANTOR = '" + kETKANTOR + '\'' + 
			",iDKANTOR = '" + iDKANTOR + '\'' + 
			",kANTOR = '" + kANTOR + '\'' + 
			"}";
		}
}