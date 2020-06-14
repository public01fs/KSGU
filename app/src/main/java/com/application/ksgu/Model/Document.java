package com.application.ksgu.Model;

import com.google.gson.annotations.SerializedName;

public class Document{

	@SerializedName("Tingkat_Revisi")
	private String tingkatRevisi;

	@SerializedName("Seksi_Note")
	private String seksiNote;

	@SerializedName("Bidang_Note")
	private String bidangNote;

	@SerializedName("Bidang")
	private String bidang;

	@SerializedName("Dokumen")
	private String dokumen;

	@SerializedName("Bidang_Id")
	private int bidangId;

	@SerializedName("Seksi")
	private String seksi;

	@SerializedName("dokumen_id")
	private int dokumenId;

	@SerializedName("Nomor_Dokumen")
	private String nomorDokumen;

	@SerializedName("file")
	private String file;

	@SerializedName("seksi_id")
	private int seksiId;

	@SerializedName("Tanggal_Mulai_Berlaku")
	private String tanggalMulaiBerlaku;

	@SerializedName("Seksi_id")
	private int seksiId1;

	@SerializedName("Id")
	private int id;

	@SerializedName("bidang_id")
	private int bidangId1;

	public void setTingkatRevisi(String tingkatRevisi){
		this.tingkatRevisi = tingkatRevisi;
	}

	public String getTingkatRevisi(){
		return tingkatRevisi;
	}

	public void setSeksiNote(String seksiNote){
		this.seksiNote = seksiNote;
	}

	public String getSeksiNote(){
		return seksiNote;
	}

	public void setBidangNote(String bidangNote){
		this.bidangNote = bidangNote;
	}

	public String getBidangNote(){
		return bidangNote;
	}

	public void setBidang(String bidang){
		this.bidang = bidang;
	}

	public String getBidang(){
		return bidang;
	}

	public void setDokumen(String dokumen){
		this.dokumen = dokumen;
	}

	public String getDokumen(){
		return dokumen;
	}

	public void setBidangId(int bidangId){
		this.bidangId = bidangId;
	}

	public int getBidangId(){
		return bidangId;
	}

	public void setSeksi(String seksi){
		this.seksi = seksi;
	}

	public String getSeksi(){
		return seksi;
	}

	public void setDokumenId(int dokumenId){
		this.dokumenId = dokumenId;
	}

	public int getDokumenId(){
		return dokumenId;
	}

	public void setNomorDokumen(String nomorDokumen){
		this.nomorDokumen = nomorDokumen;
	}

	public String getNomorDokumen(){
		return nomorDokumen;
	}

	public void setFile(String file){
		this.file = file;
	}

	public String getFile(){
		return file;
	}

	public void setSeksiId(int seksiId){
		this.seksiId = seksiId;
	}

	public int getSeksiId(){
		return seksiId;
	}

	public void setTanggalMulaiBerlaku(String tanggalMulaiBerlaku){
		this.tanggalMulaiBerlaku = tanggalMulaiBerlaku;
	}

	public String getTanggalMulaiBerlaku(){
		return tanggalMulaiBerlaku;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"Document{" + 
			"tingkat_Revisi = '" + tingkatRevisi + '\'' + 
			",seksi_Note = '" + seksiNote + '\'' + 
			",bidang_Note = '" + bidangNote + '\'' + 
			",bidang = '" + bidang + '\'' + 
			",dokumen = '" + dokumen + '\'' + 
			",bidang_Id = '" + bidangId + '\'' + 
			",seksi = '" + seksi + '\'' + 
			",dokumen_id = '" + dokumenId + '\'' + 
			",nomor_Dokumen = '" + nomorDokumen + '\'' + 
			",file = '" + file + '\'' + 
			",seksi_id = '" + seksiId + '\'' + 
			",tanggal_Mulai_Berlaku = '" + tanggalMulaiBerlaku + '\'' + 
			",seksi_id = '" + seksiId + '\'' + 
			",id = '" + id + '\'' + 
			",bidang_id = '" + bidangId + '\'' + 
			"}";
		}
}