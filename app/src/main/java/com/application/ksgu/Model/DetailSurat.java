package com.application.ksgu.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DetailSurat{

	@SerializedName("SuratLampiran")
	private List<String> suratLampiran;

	@SerializedName("SuratListOne")
	private SuratListOne suratListOne;

	@SerializedName("Disposisi")
	private List<String> disposisi;

	public void setSuratLampiran(List<String> suratLampiran){
		this.suratLampiran = suratLampiran;
	}

	public List<String> getSuratLampiran(){
		return suratLampiran;
	}

	public void setSuratListOne(SuratListOne suratListOne){
		this.suratListOne = suratListOne;
	}

	public SuratListOne getSuratListOne(){
		return suratListOne;
	}

	public void setDisposisi(List<String> disposisi){
		this.disposisi = disposisi;
	}

	public List<String> getDisposisi(){
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