package com.kendrick.angularspringboot.roko.model;

public class SearchResult {
	private String name;
	private String url;
	private String thumbnail;
	
	//This is mostly for TVDB. Would need some extra work to get it for MAL, but doesn't seem necessary.
	private long animeId;
	
	
	public SearchResult() {
		
	}
	
	public SearchResult(String name, String url, String thumbnail) {
		this.name = name;
		this.url = url;
		this.thumbnail = thumbnail;
	}
	
	
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	
	public long getAnimeId() {
		return animeId;
	}
	public void setAnimeId(long animeId) {
		this.animeId = animeId;
	}
	
	
	public String toString() {
		String s = "Name: " + this.name + " URL: " + this.url + " Thumbnail: " + this.thumbnail;
		return s;
	}
	
	
}
