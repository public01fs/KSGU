package com.application.ksgu.Model;

import com.google.gson.annotations.SerializedName;

public class SuratLampiranItem{

	@SerializedName("PATH")
	private String pATH;

	@SerializedName("FILE_SIZE")
	private String fILESIZE;

	@SerializedName("SERVER_FILE")
	private String sERVERFILE;

	@SerializedName("TGL_INPUT")
	private String tGLINPUT;

	@SerializedName("SURAT_IDLAMP")
	private int sURATIDLAMP;

	@SerializedName("SURAT_ID")
	private String sURATID;

	@SerializedName("FILE_TYPE")
	private String fILETYPE;

	@SerializedName("CEKLIST_NAME")
	private String cEKLISTNAME;

	@SerializedName("FILE_NAME")
	private String fILENAME;

	@SerializedName("USER_EDIT")
	private String uSEREDIT;

	public void setPATH(String pATH){
		this.pATH = pATH;
	}

	public String getPATH(){
		return pATH;
	}

	public void setFILESIZE(String fILESIZE){
		this.fILESIZE = fILESIZE;
	}

	public String getFILESIZE(){
		return fILESIZE;
	}

	public void setSERVERFILE(String sERVERFILE){
		this.sERVERFILE = sERVERFILE;
	}

	public String getSERVERFILE(){
		return sERVERFILE;
	}

	public void setTGLINPUT(String tGLINPUT){
		this.tGLINPUT = tGLINPUT;
	}

	public String getTGLINPUT(){
		return tGLINPUT;
	}

	public void setSURATIDLAMP(int sURATIDLAMP){
		this.sURATIDLAMP = sURATIDLAMP;
	}

	public int getSURATIDLAMP(){
		return sURATIDLAMP;
	}

	public void setSURATID(String sURATID){
		this.sURATID = sURATID;
	}

	public String getSURATID(){
		return sURATID;
	}

	public void setFILETYPE(String fILETYPE){
		this.fILETYPE = fILETYPE;
	}

	public String getFILETYPE(){
		return fILETYPE;
	}

	public void setCEKLISTNAME(String cEKLISTNAME){
		this.cEKLISTNAME = cEKLISTNAME;
	}

	public String getCEKLISTNAME(){
		return cEKLISTNAME;
	}

	public void setFILENAME(String fILENAME){
		this.fILENAME = fILENAME;
	}

	public String getFILENAME(){
		return fILENAME;
	}

	public void setUSEREDIT(String uSEREDIT){
		this.uSEREDIT = uSEREDIT;
	}

	public String getUSEREDIT(){
		return uSEREDIT;
	}

	@Override
 	public String toString(){
		return 
			"SuratLampiranItem{" + 
			"pATH = '" + pATH + '\'' + 
			",fILE_SIZE = '" + fILESIZE + '\'' + 
			",sERVER_FILE = '" + sERVERFILE + '\'' + 
			",tGL_INPUT = '" + tGLINPUT + '\'' + 
			",sURAT_IDLAMP = '" + sURATIDLAMP + '\'' + 
			",sURAT_ID = '" + sURATID + '\'' + 
			",fILE_TYPE = '" + fILETYPE + '\'' + 
			",cEKLIST_NAME = '" + cEKLISTNAME + '\'' + 
			",fILE_NAME = '" + fILENAME + '\'' + 
			",uSER_EDIT = '" + uSEREDIT + '\'' + 
			"}";
		}
}