package com.application.ksgu.Model;

import com.google.gson.annotations.SerializedName;

public class Kapal{

	@SerializedName("CALLSIGN")
	private String cALLSIGN;

	@SerializedName("BenderaAsal")
	private String benderaAsal;

	@SerializedName("TANDA_DAFTAR")
	private String tANDADAFTAR;

	@SerializedName("id")
	private int id;

	@SerializedName("label")
	private String label;

	@SerializedName("value")
	private String value;

	@SerializedName("NAMA_PEMILIK")
	private String nAMAPEMILIK;

	@SerializedName("ISI_KOTOR")
	private String iSIKOTOR;

	public void setCALLSIGN(String cALLSIGN){
		this.cALLSIGN = cALLSIGN;
	}

	public String getCALLSIGN(){
		return cALLSIGN;
	}

	public void setBenderaAsal(String benderaAsal){
		this.benderaAsal = benderaAsal;
	}

	public String getBenderaAsal(){
		return benderaAsal;
	}

	public void setTANDADAFTAR(String tANDADAFTAR){
		this.tANDADAFTAR = tANDADAFTAR;
	}

	public String getTANDADAFTAR(){
		return tANDADAFTAR;
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

	public void setNAMAPEMILIK(String nAMAPEMILIK){
		this.nAMAPEMILIK = nAMAPEMILIK;
	}

	public String getNAMAPEMILIK(){
		return nAMAPEMILIK;
	}

	public void setISIKOTOR(String iSIKOTOR){
		this.iSIKOTOR = iSIKOTOR;
	}

	public String getISIKOTOR(){
		return iSIKOTOR;
	}

	@Override
 	public String toString(){
		return 
			"Kapal{" + 
			"cALLSIGN = '" + cALLSIGN + '\'' + 
			",benderaAsal = '" + benderaAsal + '\'' + 
			",tANDA_DAFTAR = '" + tANDADAFTAR + '\'' + 
			",id = '" + id + '\'' + 
			",label = '" + label + '\'' + 
			",value = '" + value + '\'' + 
			",nAMA_PEMILIK = '" + nAMAPEMILIK + '\'' + 
			",iSI_KOTOR = '" + iSIKOTOR + '\'' + 
			"}";
		}
}