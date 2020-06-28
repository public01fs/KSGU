package com.application.ksgu.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DetailSuratNew{

	@SerializedName("SuratLampiran")
	private List<SuratLampiranItem> suratLampiran;

	@SerializedName("suratListOne")
	private SuratListOne suratListOne;

	@SerializedName("suratDetail")
	private SuratDetail suratDetail;

	@SerializedName("billing")
	private Billing billing;

	@SerializedName("Disposisi")
	private List<Disposisi> disposisi;

	public void setSuratLampiran(List<SuratLampiranItem> suratLampiran){
		this.suratLampiran = suratLampiran;
	}

	public List<SuratLampiranItem> getSuratLampiran(){
		return suratLampiran;
	}

	public void setSuratListOne(SuratListOne suratListOne){
		this.suratListOne = suratListOne;
	}

	public SuratListOne getSuratListOne(){
		return suratListOne;
	}

	public void setSuratDetail(SuratDetail suratDetail){
		this.suratDetail = suratDetail;
	}

	public SuratDetail getSuratDetail(){
		return suratDetail;
	}

	public void setBilling(Billing billing){
		this.billing = billing;
	}

	public Billing getBilling(){
		return billing;
	}

	public void setDisposisi(List<Disposisi> disposisi){
		this.disposisi = disposisi;
	}

	public List<Disposisi> getDisposisi(){
		return disposisi;
	}

	@Override
 	public String toString(){
		return 
			"DetailSuratNew{" + 
			"suratLampiran = '" + suratLampiran + '\'' + 
			",suratListOne = '" + suratListOne + '\'' + 
			",suratDetail = '" + suratDetail + '\'' + 
			",billing = '" + billing + '\'' + 
			",disposisi = '" + disposisi + '\'' + 
			"}";
		}
}