package com.kendrick.angularspringboot.roko.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kendrick.angularspringboot.roko.Listeners.AnimeListener;
import com.kendrick.angularspringboot.roko.model.AnimeSeries;

@Entity
@Table(name = "anime")
//@EntityListeners(AnimeListener.class)
public class Anime {
	private long id;
	private long tvdbId;
	private String name_english;
	private String name_japanese;
	private float rating;
	private int year;
	private int episodes;
	private String description;
	private int seasons;
	private String status;
	private String malUrl;
	private String malThumbnail;
	private String tvdbUrl;
	private String tvdbThumbnail;
	
	//green - watched / complete
	//yellow - watching / in progress
	private int watchStatus;
	
	private AnimeSeries series;
	

	
	//Workflow:
	//Anime gets added via TVDB, if it exists there.
	//If no season 2, etc shows up, search using MAL and add season 2, 3 etc
	//Create functionality to link prequels and sequels together in the update page (drop down box showing name / thumbnail of show?)
	
	@JsonIgnore 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public AnimeSeries getSeries() {
		return this.series;
	}

	public void setSeries(AnimeSeries series) {
		this.series = series;
	}

	public Anime() {
		
	}
	
	public Anime(String name_english) {
		this.name_english = name_english;
	}

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	
	
	@Column(name="name_english")
	public String getName_english() {
		return name_english;
	}

	public void setName_english(String name_english) {
		this.name_english = name_english;
	}

	
	
	@Column(name="name_japanese")
	public String getName_japanese() {
		return name_japanese;
	}

	public void setName_japanese(String name_japanese) {
		this.name_japanese = name_japanese;
	}

	
	
	@Column(name="rating")
	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	
	
	@Column(name="year")
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	
	
	@Column(name="episodes")
	public int getEpisodes() {
		return episodes;
	}

	public void setEpisodes(int episodes) {
		this.episodes = episodes;
	}

	
	
	@Column(name="description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	
	@Column(name="seasons")
	public int getSeasons() {
		return seasons;
	}

	public void setSeasons(int seasons) {
		this.seasons = seasons;
	}

	
	
	@Column(name="status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	@Column(name="malUrl")
	public String getMalUrl() {
		return malUrl;
	}

	public void setMalUrl(String malUrl) {
		this.malUrl = malUrl;
	}
	
	
	@Column(name="malThumbnail")
	public String getMalThumbnail() {
		return malThumbnail;
	}

	public void setMalThumbnail(String malThumbnail) {
		this.malThumbnail = malThumbnail;
	}
	
	
	@Column(name="tvdbUrl")
	public String getTvdbUrl() {
		return tvdbUrl;
	}

	public void setTvdbUrl(String tvdbUrl) {
		this.tvdbUrl = tvdbUrl;
	}
	
	

	@Column(name="tvdbThumbnail")
	public String getTvdbThumbnail() {
		return tvdbThumbnail;
	}

	public void setTvdbThumbnail(String tvdbThumbnail) {
		this.tvdbThumbnail = tvdbThumbnail;
	}
	
	
	@Column(name="tvdbId")
	public long getTvdbId() {
		return tvdbId;
	}
	public void setTvdbId(long tvdbId) {
		this.tvdbId = tvdbId;
	}
	
	@Column(name="watch_status")
	public int getWatchStatus() {
		return watchStatus;
	}

	public void setWatchStatus(int watchStatus) {
		this.watchStatus = watchStatus;
	}
	
	
    @Override
    public String toString() {
        return "Anime [id=" + id + ", English Name=" + name_english + "]";
    }

}































