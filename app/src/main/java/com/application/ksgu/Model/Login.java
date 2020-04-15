package com.application.ksgu.Model;

import com.google.gson.annotations.SerializedName;

public class Login{

	@SerializedName("keterangan")
	private String keterangan;

	@SerializedName("img")
	private String img;

	@SerializedName("agen")
	private Agen agen;

	@SerializedName("address")
	private Address address;

	@SerializedName("ktp")
	private String ktp;

	@SerializedName("api_token")
	private String apiToken;

	@SerializedName("npwp")
	private String npwp;

	@SerializedName("telepon")
	private String telepon;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("agen_id")
	private String agenId;

	@SerializedName("aktif")
	private String aktif;

	@SerializedName("alamat")
	private String alamat;

	@SerializedName("confirm")
	private String confirm;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("confirm_code")
	private Object confirmCode;

	@SerializedName("confirm_date")
	private Object confirmDate;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("daerah_id")
	private int daerahId;

	@SerializedName("email")
	private String email;

	@SerializedName("error")
	private String error;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public void setKeterangan(String keterangan){
		this.keterangan = keterangan;
	}

	public String getKeterangan(){
		return keterangan;
	}

	public void setImg(String img){
		this.img = img;
	}

	public String getImg(){
		return img;
	}

	public void setAgen(Agen agen){
		this.agen = agen;
	}

	public Agen getAgen(){
		return agen;
	}

	public void setAddress(Address address){
		this.address = address;
	}

	public Address getAddress(){
		return address;
	}

	public void setKtp(String ktp){
		this.ktp = ktp;
	}

	public String getKtp(){
		return ktp;
	}

	public void setApiToken(String apiToken){
		this.apiToken = apiToken;
	}

	public String getApiToken(){
		return apiToken;
	}

	public void setNpwp(String npwp){
		this.npwp = npwp;
	}

	public String getNpwp(){
		return npwp;
	}

	public void setTelepon(String telepon){
		this.telepon = telepon;
	}

	public String getTelepon(){
		return telepon;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setAgenId(String agenId){
		this.agenId = agenId;
	}

	public String getAgenId(){
		return agenId;
	}

	public void setAktif(String aktif){
		this.aktif = aktif;
	}

	public String getAktif(){
		return aktif;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}

	public void setConfirm(String confirm){
		this.confirm = confirm;
	}

	public String getConfirm(){
		return confirm;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setConfirmCode(Object confirmCode){
		this.confirmCode = confirmCode;
	}

	public Object getConfirmCode(){
		return confirmCode;
	}

	public void setConfirmDate(Object confirmDate){
		this.confirmDate = confirmDate;
	}

	public Object getConfirmDate(){
		return confirmDate;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setDaerahId(int daerahId){
		this.daerahId = daerahId;
	}

	public int getDaerahId(){
		return daerahId;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	@Override
 	public String toString(){
		return 
			"Login{" + 
			"keterangan = '" + keterangan + '\'' + 
			",img = '" + img + '\'' + 
			",agen = '" + agen + '\'' + 
			",address = '" + address + '\'' + 
			",ktp = '" + ktp + '\'' + 
			",api_token = '" + apiToken + '\'' + 
			",npwp = '" + npwp + '\'' + 
			",telepon = '" + telepon + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",agen_id = '" + agenId + '\'' + 
			",aktif = '" + aktif + '\'' + 
			",alamat = '" + alamat + '\'' + 
			",confirm = '" + confirm + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",confirm_code = '" + confirmCode + '\'' + 
			",confirm_date = '" + confirmDate + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",daerah_id = '" + daerahId + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}