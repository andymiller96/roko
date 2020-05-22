package com.kendrick.angularspringboot.roko.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


//Series contains multiple Anime (multiple "seasons")
//Ideally, this will be what is displayed on the home screen, not the individual seasons (ex Darker than black season 1 and season 2 showing up as separate results)
@Entity
@Table(name = "anime_series")
public class AnimeSeries {

	//id that will be used to link anime to the series, auto generated
	private long id;

	//contains all of the anime relating to this series, will not have movies or OVAs listed unless they are specified as sequels
	//Can add seasons automatically, and re-order them via drag and drop?
	//Would iterate over the seasons array within the page of the series, displaying them each in an accordion like array
	//private Anime_[] seasons;
	
	
	private List<Anime> seasons;
	
	private String seriesName;
	
	//Thumbnail of Season 1 to Represent the Entire Series
	private String malThumbnail;
	
	private int watchStatus;
	
		
	@OneToMany(
			mappedBy="series",
	        cascade = CascadeType.ALL
	        )
	public List<Anime> getSeasons() {
		return seasons;
	}

	public void setSeasons(List<Anime> seasons) {
		this.seasons = seasons;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name="series_name")
	public String getSeriesName() {
		return seriesName;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}
	
	@Column(name="mal_thumbnail")
	public String getMalThumbnail() {
		return malThumbnail;
	}
	
	public void setMalThumbnail(String malThumbnail) {
		this.malThumbnail = malThumbnail;
	}
	
	@Column(name="watch_status")
	public int getWatchStatus() {
		return this.watchStatus;
	}
	
	public void setWatchStatus(int watchStatus) {
		this.watchStatus = watchStatus;
	}
	
	
	public String toString() {
		return "Name: " + this.getSeriesName() + "seasons: " + this.getSeasons().toString();
	}
	
	
}
