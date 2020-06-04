package com.application.ksgu.Model;

import com.google.gson.annotations.SerializedName;

public class Billing{

	@SerializedName("total")
	private String total;

	@SerializedName("kode_billing")
	private String kodeBilling;

	@SerializedName("tglnota")
	private String tglnota;

	@SerializedName("link_kuitansi")
	private String linkKuitansi;

	@SerializedName("link_nota")
	private String linkNota;

	@SerializedName("status")
	private String status;

	public void setTotal(String total){
		this.total = total;
	}

	public String getTotal(){
		return total;
	}

	public void setKodeBilling(String kodeBilling){
		this.kodeBilling = kodeBilling;
	}

	public String getKodeBilling(){
		return kodeBilling;
	}

	public void setTglnota(String tglnota){
		this.tglnota = tglnota;
	}

	public String getTglnota(){
		return tglnota;
	}

	public void setLinkKuitansi(String linkKuitansi){
		this.linkKuitansi = linkKuitansi;
	}

	public String getLinkKuitansi(){
		return linkKuitansi;
	}

	public void setLinkNota(String linkNota){
		this.linkNota = linkNota;
	}

	public String getLinkNota(){
		return linkNota;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"Billing{" + 
			"total = '" + total + '\'' + 
			",kode_billing = '" + kodeBilling + '\'' + 
			",tglnota = '" + tglnota + '\'' + 
			",link_kuitansi = '" + linkKuitansi + '\'' + 
			",link_nota = '" + linkNota + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}