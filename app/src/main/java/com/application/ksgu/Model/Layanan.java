package com.application.ksgu.Model;

import com.google.gson.annotations.SerializedName;

import ir.mirrajabi.searchdialog.core.Searchable;

public class Layanan implements Searchable {

	@SerializedName("JENIS_SHORT")
	private String jENISSHORT;

	@SerializedName("STATUS")
	private int sTATUS;

	@SerializedName("JENIS_NOTE")
	private String jENISNOTE;

	@SerializedName("JENIS_GRUP")
	private Object jENISGRUP;

	@SerializedName("JENIS_ID")
	private int jENISID;

	@SerializedName("REQUIRE_CHECK")
	private String rEQUIRECHECK;

	@SerializedName("JENIS_NAME")
	private String jENISNAME;

	@SerializedName("MAX_DAY")
	private String mAXDAY;

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

	public void setJENISNAME(String jENISNAME){
		this.jENISNAME = jENISNAME;
	}

	public String getJENISNAME(){
		return jENISNAME;
	}

	public void setMAXDAY(String mAXDAY){
		this.mAXDAY = mAXDAY;
	}

	public String getMAXDAY(){
		return mAXDAY;
	}

	@Override
 	public String toString(){
		return 
			"Layanan{" + 
			"jENIS_SHORT = '" + jENISSHORT + '\'' + 
			",sTATUS = '" + sTATUS + '\'' + 
			",jENIS_NOTE = '" + jENISNOTE + '\'' + 
			",jENIS_GRUP = '" + jENISGRUP + '\'' + 
			",jENIS_ID = '" + jENISID + '\'' + 
			",rEQUIRE_CHECK = '" + rEQUIRECHECK + '\'' + 
			",jENIS_NAME = '" + jENISNAME + '\'' + 
			",mAX_DAY = '" + mAXDAY + '\'' + 
			"}";
		}

	@Override
	public String getTitle() {
		return jENISNOTE + "-" + jENISNAME;
	}
}