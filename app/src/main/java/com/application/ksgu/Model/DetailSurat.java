package com.application.ksgu.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DetailSurat{

	@SerializedName("SuratLampiran")
	private List<Object> suratLampiran;

	@SerializedName("SuratListOne")
	private SuratListOne suratListOne;

	@SerializedName("Disposisi")
	private List<Object> disposisi;

	public void setSuratLampiran(List<Object> suratLampiran){
		this.suratLampiran = suratLampiran;
	}

	public List<Object> getSuratLampiran(){
		return suratLampiran;
	}

	public void setSuratListOne(SuratListOne suratListOne){
		this.suratListOne = suratListOne;
	}

	public SuratListOne getSuratListOne(){
		return suratListOne;
	}

	public void setDisposisi(List<Object> disposisi){
		this.disposisi = disposisi;
	}

	public List<Object> getDisposisi(){
		return disposisi;
	}

	@Override
 	public String toString(){
		return 
			"DetailSurat{" + 
			"suratLampiran = '" + suratLampiran + '\'' + 
			",suratListOne = '" + suratListOne + '\'' + 
			",disposisi = '" + disposisi + '\'' + 
			"}";
		}
}