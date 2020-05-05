package com.application.ksgu.Model;

import com.google.gson.annotations.SerializedName;

public class Save{

	@SerializedName("surat_id")
	private long suratId;

	@SerializedName("status")
	private boolean status;

	public void setSuratId(long suratId){
		this.suratId = suratId;
	}

	public long getSuratId(){
		return suratId;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"Save{" + 
			"surat_id = '" + suratId + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}