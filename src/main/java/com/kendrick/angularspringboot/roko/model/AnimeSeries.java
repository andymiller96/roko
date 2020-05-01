package com.kendrick.angularspringboot.roko.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


//Series contains multiple Anime (multiple "seasons")
//Ideally, this will be what is displayed on the home screen, not the individual seasons (ex Darker than black season 1 and season 2 showing up as separate results)
@Entity
@Table(name = "anime_series")
public class AnimeSeries {

	//id that will be used to link anime to the series, auto generated
	private long seriesId;

	//contains all of the anime relating to this series, will not have movies or OVAs listed unless they are specified as sequels
	//Can add seasons automatically, and re-order them via drag and drop?
	//Would iterate over the seasons array within the page of the series, displaying them each in an accordion like array
	//private Anime_[] seasons;
	
	private int[] idSeasons;
	
	private String seriesName;
	
	/*
	//Name of the series overall, if each season has a specific, unique name, take the common name or just the name of the first season.
	private String name;
	
	//Link to the MAL thumbnail for the season
	private String malThumbnail;
	
	//Link to the TVDB thumbnail image for the series
	private String tvdbThumbnail;
	*/
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getSeriesId() {
		return seriesId;
	}

	public void setSeriesId(long seriesId) {
		this.seriesId = seriesId;
	}

	@Column(name="id_seasons")
	public int[] getIdSeasons() {
		return idSeasons;
	}

	public void setIdSeasons(int[] idSeasons) {
		this.idSeasons = idSeasons;
	}
	
	@Column(name="series_name")
	public String getSeriesName() {
		return seriesName;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}
	
	
}
