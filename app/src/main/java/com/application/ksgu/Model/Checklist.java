package com.application.ksgu.Model;

import com.google.gson.annotations.SerializedName;

public class Checklist{

	@SerializedName("CEKLIST_NOTE")
	private String cEKLISTNOTE;

	@SerializedName("JENIS_NOTE")
	private String jENISNOTE;

	@SerializedName("JENISCEKLIST_ID")
	private int jENISCEKLISTID;

	@SerializedName("JENIS_GRUP")
	private String jENISGRUP;

	@SerializedName("CEKLIST_NAME")
	private String cEKLISTNAME;

	@SerializedName("MAX_DAY")
	private String mAXDAY;

	@SerializedName("JENIS_SHORT")
	private String jENISSHORT;

	@SerializedName("STATUS")
	private int sTATUS;

	@SerializedName("CEKLIST_ID")
	private int cEKLISTID;

	@SerializedName("JENIS_ID")
	private int jENISID;

	@SerializedName("REQUIRE_CHECK")
	private String rEQUIRECHECK;

	@SerializedName("CEKLIST_SORT")
	private String cEKLISTSORT;

	@SerializedName("JENIS_NAME")
	private String jENISNAME;

	public void setCEKLISTNOTE(String cEKLISTNOTE){
		this.cEKLISTNOTE = cEKLISTNOTE;
	}

	public String getCEKLISTNOTE(){
		return cEKLISTNOTE;
	}

	public void setJENISNOTE(String jENISNOTE){
		this.jENISNOTE = jENISNOTE;
	}

	public String getJENISNOTE(){
		return jENISNOTE;
	}

	public void setJENISCEKLISTID(int jENISCEKLISTID){
		this.jENISCEKLISTID = jENISCEKLISTID;
	}

	public int getJENISCEKLISTID(){
		return jENISCEKLISTID;
	}

	public void setJENISGRUP(String jENISGRUP){
		this.jENISGRUP = jENISGRUP;
	}

	public String getJENISGRUP(){
		return jENISGRUP;
	}

	public void setCEKLISTNAME(String cEKLISTNAME){
		this.cEKLISTNAME = cEKLISTNAME;
	}

	public String getCEKLISTNAME(){
		return cEKLISTNAME;
	}

	public void setMAXDAY(String mAXDAY){
		this.mAXDAY = mAXDAY;
	}

	public String getMAXDAY(){
		return mAXDAY;
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

	public void setCEKLISTID(int cEKLISTID){
		this.cEKLISTID = cEKLISTID;
	}

	public int getCEKLISTID(){
		return cEKLISTID;
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

	public void setCEKLISTSORT(String cEKLISTSORT){
		this.cEKLISTSORT = cEKLISTSORT;
	}

	public String getCEKLISTSORT(){
		return cEKLISTSORT;
	}

	public void setJENISNAME(String jENISNAME){
		this.jENISNAME = jENISNAME;
	}

	public String getJENISNAME(){
		return jENISNAME;
	}

	@Override
 	public String toString(){
		return 
			"Checklist{" + 
			"cEKLIST_NOTE = '" + cEKLISTNOTE + '\'' + 
			",jENIS_NOTE = '" + jENISNOTE + '\'' + 
			",jENISCEKLIST_ID = '" + jENISCEKLISTID + '\'' + 
			",jENIS_GRUP = '" + jENISGRUP + '\'' + 
			",cEKLIST_NAME = '" + cEKLISTNAME + '\'' + 
			",mAX_DAY = '" + mAXDAY + '\'' + 
			",jENIS_SHORT = '" + jENISSHORT + '\'' + 
			",sTATUS = '" + sTATUS + '\'' + 
			",cEKLIST_ID = '" + cEKLISTID + '\'' + 
			",jENIS_ID = '" + jENISID + '\'' + 
			",rEQUIRE_CHECK = '" + rEQUIRECHECK + '\'' + 
			",cEKLIST_SORT = '" + cEKLISTSORT + '\'' + 
			",jENIS_NAME = '" + jENISNAME + '\'' + 
			"}";
		}
}