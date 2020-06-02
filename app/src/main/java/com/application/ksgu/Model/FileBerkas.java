package com.application.ksgu.Model;

import com.google.gson.annotations.SerializedName;

public class FileBerkas {

	@SerializedName("SERVER_FILE")
	private String sERVERFILE;

	@SerializedName("FILE_SIZE")
	private int fILESIZE;

	@SerializedName("FILE_EXTENSION")
	private String fILEEXTENSION;

	@SerializedName("FILE_NAME")
	private String fILENAME;

	public void setSERVERFILE(String sERVERFILE){
		this.sERVERFILE = sERVERFILE;
	}

	public String getSERVERFILE(){
		return sERVERFILE;
	}

	public void setFILESIZE(int fILESIZE){
		this.fILESIZE = fILESIZE;
	}

	public int getFILESIZE(){
		return fILESIZE;
	}

	public void setFILEEXTENSION(String fILEEXTENSION){
		this.fILEEXTENSION = fILEEXTENSION;
	}

	public String getFILEEXTENSION(){
		return fILEEXTENSION;
	}

	public void setFILENAME(String fILENAME){
		this.fILENAME = fILENAME;
	}

	public String getFILENAME(){
		return fILENAME;
	}

	@Override
 	public String toString(){
		return 
			"File{" + 
			"sERVER_FILE = '" + sERVERFILE + '\'' + 
			",fILE_SIZE = '" + fILESIZE + '\'' + 
			",fILE_EXTENSION = '" + fILEEXTENSION + '\'' + 
			",fILE_NAME = '" + fILENAME + '\'' + 
			"}";
		}
}