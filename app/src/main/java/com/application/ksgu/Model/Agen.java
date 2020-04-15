package com.application.ksgu.Model;

import com.google.gson.annotations.SerializedName;

public class Agen{

	@SerializedName("agen")
	private Agen agen;

	@SerializedName("ALAMAT")
	private String aLAMAT;

	@SerializedName("KOTA")
	private String kOTA;

	@SerializedName("TELP")
	private Object tELP;

	@SerializedName("IDAGEN")
	private int iDAGEN;

	@SerializedName("EMAIL")
	private Object eMAIL;

	@SerializedName("NAMA_AGEN")
	private String nAMAAGEN;

	@SerializedName("CP")
	private Object cP;

	@SerializedName("IdentitasPrsh")
	private Object identitasPrsh;

	public void setAgen(Agen agen){
		this.agen = agen;
	}

	public Agen getAgen(){
		return agen;
	}

	public void setALAMAT(String aLAMAT){
		this.aLAMAT = aLAMAT;
	}

	public String getALAMAT(){
		return aLAMAT;
	}

	public void setKOTA(String kOTA){
		this.kOTA = kOTA;
	}

	public String getKOTA(){
		return kOTA;
	}

	public void setTELP(Object tELP){
		this.tELP = tELP;
	}

	public Object getTELP(){
		return tELP;
	}

	public void setIDAGEN(int iDAGEN){
		this.iDAGEN = iDAGEN;
	}

	public int getIDAGEN(){
		return iDAGEN;
	}

	public void setEMAIL(Object eMAIL){
		this.eMAIL = eMAIL;
	}

	public Object getEMAIL(){
		return eMAIL;
	}

	public void setNAMAAGEN(String nAMAAGEN){
		this.nAMAAGEN = nAMAAGEN;
	}

	public String getNAMAAGEN(){
		return nAMAAGEN;
	}

	public void setCP(Object cP){
		this.cP = cP;
	}

	public Object getCP(){
		return cP;
	}

	public void setIdentitasPrsh(Object identitasPrsh){
		this.identitasPrsh = identitasPrsh;
	}

	public Object getIdentitasPrsh(){
		return identitasPrsh;
	}

	@Override
 	public String toString(){
		return 
			"Agen{" + 
			"agen = '" + agen + '\'' + 
			",aLAMAT = '" + aLAMAT + '\'' + 
			",kOTA = '" + kOTA + '\'' + 
			",tELP = '" + tELP + '\'' + 
			",iDAGEN = '" + iDAGEN + '\'' + 
			",eMAIL = '" + eMAIL + '\'' + 
			",nAMA_AGEN = '" + nAMAAGEN + '\'' + 
			",cP = '" + cP + '\'' + 
			",identitasPrsh = '" + identitasPrsh + '\'' + 
			"}";
		}
}