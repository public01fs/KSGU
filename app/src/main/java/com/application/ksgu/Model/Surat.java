package com.application.ksgu.Model;

import com.google.gson.annotations.SerializedName;

public class Surat{

	@SerializedName("KAPAL_NAME")
	private String kAPALNAME;

	@SerializedName("SURAT_ID")
	private String sURATID;

	@SerializedName("JENIS_NAME")
	private String jENISNAME;

	@SerializedName("STATUS_NAME")
	private String sTATUSNAME;

	@SerializedName("StatusNotaDesc")
	private String statusNotaDesc;

	@SerializedName("SURAT_INPUT")
	private String sURATINPUT;

	@SerializedName("KAPAL_PEMILIK")
	private String kAPALPEMILIK;

	@SerializedName("SURAT_INPUTTGL")
	private String sURATINPUTTGL;

	@SerializedName("SURAT_STATUS")
	private String sURATSTATUS;

	public void setKAPALNAME(String kAPALNAME){
		this.kAPALNAME = kAPALNAME;
	}

	public String getKAPALNAME(){
		return kAPALNAME;
	}

	public void setSURATID(String sURATID){
		this.sURATID = sURATID;
	}

	public String getSURATID(){
		return sURATID;
	}

	public void setJENISNAME(String jENISNAME){
		this.jENISNAME = jENISNAME;
	}

	public String getJENISNAME(){
		return jENISNAME;
	}

	public void setSTATUSNAME(String sTATUSNAME){
		this.sTATUSNAME = sTATUSNAME;
	}

	public String getSTATUSNAME(){
		return sTATUSNAME;
	}

	public void setStatusNotaDesc(String statusNotaDesc){
		this.statusNotaDesc = statusNotaDesc;
	}

	public String getStatusNotaDesc(){
		return statusNotaDesc;
	}

	public void setSURATINPUT(String sURATINPUT){
		this.sURATINPUT = sURATINPUT;
	}

	public String getSURATINPUT(){
		return sURATINPUT;
	}

	public void setKAPALPEMILIK(String kAPALPEMILIK){
		this.kAPALPEMILIK = kAPALPEMILIK;
	}

	public String getKAPALPEMILIK(){
		return kAPALPEMILIK;
	}

	public void setSURATINPUTTGL(String sURATINPUTTGL){
		this.sURATINPUTTGL = sURATINPUTTGL;
	}

	public String getSURATINPUTTGL(){
		return sURATINPUTTGL;
	}

	public void setSURATSTATUS(String sURATSTATUS){
		this.sURATSTATUS = sURATSTATUS;
	}

	public String getSURATSTATUS(){
		return sURATSTATUS;
	}

	@Override
 	public String toString(){
		return 
			"Surat{" + 
			"kAPAL_NAME = '" + kAPALNAME + '\'' + 
			",sURAT_ID = '" + sURATID + '\'' + 
			",jENIS_NAME = '" + jENISNAME + '\'' + 
			",sTATUS_NAME = '" + sTATUSNAME + '\'' + 
			",statusNotaDesc = '" + statusNotaDesc + '\'' + 
			",sURAT_INPUT = '" + sURATINPUT + '\'' + 
			",kAPAL_PEMILIK = '" + kAPALPEMILIK + '\'' + 
			",sURAT_INPUTTGL = '" + sURATINPUTTGL + '\'' + 
			",sURAT_STATUS = '" + sURATSTATUS + '\'' + 
			"}";
		}
}