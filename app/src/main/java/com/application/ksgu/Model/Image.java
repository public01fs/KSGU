package com.application.ksgu.Model;

import com.google.gson.annotations.SerializedName;

public class Image{

	@SerializedName("link_visible")
	private String linkVisible;

	@SerializedName("link_rating")
	private int linkRating;

	@SerializedName("link_rel")
	private String linkRel;

	@SerializedName("link_updated")
	private String linkUpdated;

	@SerializedName("link_description")
	private String linkDescription;

	@SerializedName("link_id")
	private int linkId;

	@SerializedName("link_image")
	private String linkImage;

	@SerializedName("link_owner")
	private int linkOwner;

	@SerializedName("link_target")
	private String linkTarget;

	@SerializedName("link_url")
	private String linkUrl;

	@SerializedName("link_notes")
	private String linkNotes;

	@SerializedName("link_name")
	private String linkName;

	@SerializedName("link_rss")
	private String linkRss;

	public void setLinkVisible(String linkVisible){
		this.linkVisible = linkVisible;
	}

	public String getLinkVisible(){
		return linkVisible;
	}

	public void setLinkRating(int linkRating){
		this.linkRating = linkRating;
	}

	public int getLinkRating(){
		return linkRating;
	}

	public void setLinkRel(String linkRel){
		this.linkRel = linkRel;
	}

	public String getLinkRel(){
		return linkRel;
	}

	public void setLinkUpdated(String linkUpdated){
		this.linkUpdated = linkUpdated;
	}

	public String getLinkUpdated(){
		return linkUpdated;
	}

	public void setLinkDescription(String linkDescription){
		this.linkDescription = linkDescription;
	}

	public String getLinkDescription(){
		return linkDescription;
	}

	public void setLinkId(int linkId){
		this.linkId = linkId;
	}

	public int getLinkId(){
		return linkId;
	}

	public void setLinkImage(String linkImage){
		this.linkImage = linkImage;
	}

	public String getLinkImage(){
		return linkImage;
	}

	public void setLinkOwner(int linkOwner){
		this.linkOwner = linkOwner;
	}

	public int getLinkOwner(){
		return linkOwner;
	}

	public void setLinkTarget(String linkTarget){
		this.linkTarget = linkTarget;
	}

	public String getLinkTarget(){
		return linkTarget;
	}

	public void setLinkUrl(String linkUrl){
		this.linkUrl = linkUrl;
	}

	public String getLinkUrl(){
		return linkUrl;
	}

	public void setLinkNotes(String linkNotes){
		this.linkNotes = linkNotes;
	}

	public String getLinkNotes(){
		return linkNotes;
	}

	public void setLinkName(String linkName){
		this.linkName = linkName;
	}

	public String getLinkName(){
		return linkName;
	}

	public void setLinkRss(String linkRss){
		this.linkRss = linkRss;
	}

	public String getLinkRss(){
		return linkRss;
	}

	@Override
 	public String toString(){
		return 
			"Image{" + 
			"link_visible = '" + linkVisible + '\'' + 
			",link_rating = '" + linkRating + '\'' + 
			",link_rel = '" + linkRel + '\'' + 
			",link_updated = '" + linkUpdated + '\'' + 
			",link_description = '" + linkDescription + '\'' + 
			",link_id = '" + linkId + '\'' + 
			",link_image = '" + linkImage + '\'' + 
			",link_owner = '" + linkOwner + '\'' + 
			",link_target = '" + linkTarget + '\'' + 
			",link_url = '" + linkUrl + '\'' + 
			",link_notes = '" + linkNotes + '\'' + 
			",link_name = '" + linkName + '\'' + 
			",link_rss = '" + linkRss + '\'' + 
			"}";
		}
}