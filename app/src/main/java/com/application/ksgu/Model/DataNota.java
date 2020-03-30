package com.application.ksgu.Model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class DataNota{

	@SerializedName("PELRA")
	private Object pELRA;

	@SerializedName("NOTA_SORT")
	private String nOTASORT;

	@SerializedName("JENIS_NOTE")
	private String jENISNOTE;

	@SerializedName("JENIS_GRUP")
	private Object jENISGRUP;

	@SerializedName("CHILD_ID")
	private String cHILDID;

	@SerializedName("MAX_DAY")
	private Object mAXDAY;

	@SerializedName("NOTA_NOTE")
	private String nOTANOTE;

	@SerializedName("PARENT_ID")
	private Object pARENTID;

	@SerializedName("NOTA_NAME")
	private String nOTANAME;

	@SerializedName("AKTIF")
	private int aKTIF;

	@SerializedName("JENIS_SHORT")
	private String jENISSHORT;

	@SerializedName("STATUS")
	private int sTATUS;

	@SerializedName("JENIS_ID")
	private int jENISID;

	@SerializedName("REQUIRE_CHECK")
	private String rEQUIRECHECK;

	@SerializedName("JENISNOTA_ID")
	private int jENISNOTAID;

	@SerializedName("JENIS_NAME")
	private String jENISNAME;

	@SerializedName("NOTA_ID")
	private int nOTAID;

	public void setPELRA(Object pELRA){
		this.pELRA = pELRA;
	}

	public Object getPELRA(){
		return pELRA;
	}

	public void setNOTASORT(String nOTASORT){
		this.nOTASORT = nOTASORT;
	}

	public String getNOTASORT(){
		return nOTASORT;
	}

	public void setJENISNOTE(String jENISNOTE){
		this.jENISNOTE = jENISNOTE;
	}

	public String getJENISNOTE(){
		return jENISNOTE;
	}

	public void setJENISGRUP(Object jENISGRUP){
		this.jENISGRUP = jENISGRUP;
	}

	public Object getJENISGRUP(){
		return jENISGRUP;
	}

	public void setCHILDID(String cHILDID){
		this.cHILDID = cHILDID;
	}

	public String getCHILDID(){
		return cHILDID;
	}

	public void setMAXDAY(Object mAXDAY){
		this.mAXDAY = mAXDAY;
	}

	public Object getMAXDAY(){
		return mAXDAY;
	}

	public void setNOTANOTE(String nOTANOTE){
		this.nOTANOTE = nOTANOTE;
	}

	public String getNOTANOTE(){
		return nOTANOTE;
	}

	public void setPARENTID(Object pARENTID){
		this.pARENTID = pARENTID;
	}

	public Object getPARENTID(){
		return pARENTID;
	}

	public void setNOTANAME(String nOTANAME){
		this.nOTANAME = nOTANAME;
	}

	public String getNOTANAME(){
		return nOTANAME;
	}

	public void setAKTIF(int aKTIF){
		this.aKTIF = aKTIF;
	}

	public int getAKTIF(){
		return aKTIF;
	}

	public void setJENISSHORT(String jENISSHORT){
		this.jENISSHORT = jENISSHORT;
	}

	public String getJENISSHORT(){
		return jENISSHORT;
	}

	public void setSTATUS(int sTATUS){
		this.sTATUS = sTATUS;
	}

	public int getSTATUS(){
		return sTATUS;
	}

	public void setJENISID(int jENISID){
		this.jENISID = jENISID;
	}

	public int getJENISID(){
		return jENISID;
	}

	public void setREQUIRECHECK(String rEQUIRECHECK){
		this.rEQUIRECHECK = rEQUIRECHECK;
	}

	public String getREQUIRECHECK(){
		return rEQUIRECHECK;
	}

	public void setJENISNOTAID(int jENISNOTAID){
		this.jENISNOTAID = jENISNOTAID;
	}

	public int getJENISNOTAID(){
		return jENISNOTAID;
	}

	public void setJENISNAME(String jENISNAME){
		this.jENISNAME = jENISNAME;
	}

	public String getJENISNAME(){
		return jENISNAME;
	}

	public void setNOTAID(int nOTAID){
		this.nOTAID = nOTAID;
	}

	public int getNOTAID(){
		return nOTAID;
	}

	@Override
 	public String toString(){
		return 
			"DataNota{" + 
			"pELRA = '" + pELRA + '\'' + 
			",nOTA_SORT = '" + nOTASORT + '\'' + 
			",jENIS_NOTE = '" + jENISNOTE + '\'' + 
			",jENIS_GRUP = '" + jENISGRUP + '\'' + 
			",cHILD_ID = '" + cHILDID + '\'' + 
			",mAX_DAY = '" + mAXDAY + '\'' + 
			",nOTA_NOTE = '" + nOTANOTE + '\'' + 
			",pARENT_ID = '" + pARENTID + '\'' + 
			",nOTA_NAME = '" + nOTANAME + '\'' + 
			",aKTIF = '" + aKTIF + '\'' + 
			",jENIS_SHORT = '" + jENISSHORT + '\'' + 
			",sTATUS = '" + sTATUS + '\'' + 
			",jENIS_ID = '" + jENISID + '\'' + 
			",rEQUIRE_CHECK = '" + rEQUIRECHECK + '\'' + 
			",jENISNOTA_ID = '" + jENISNOTAID + '\'' + 
			",jENIS_NAME = '" + jENISNAME + '\'' + 
			",nOTA_ID = '" + nOTAID + '\'' + 
			"}";
		}
}