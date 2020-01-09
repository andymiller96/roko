package com.kendrick.angularspringboot.roko.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "anime")
public class Anime {
	private long id;
	private String name_english;
	private String name_japanese;
	private float rating;
	private int year;
	private int episodes;
	private String description;
	private int seasons;
	private int ongoing;
	
	public Anime() {
		
	}
	
	public Anime(String name_english) {
		this.name_english = name_english;
	}
	
	public Anime(String name_english, String name_japanese, 
			float rating, int year, int episodes, String description, 
			int seasons, int ongoing) {
		
		this.name_english = name_english;
		this.name_japanese = name_japanese;
		this.rating = rating;
		this.year = year;
		this.episodes = episodes;
		this.description = description;
		this.seasons = seasons;
		this.ongoing = ongoing;
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

	@Column(name="ongoing")
	public int getOngoing() {
		return ongoing;
	}

	public void setOngoing(int ongoing) {
		this.ongoing = ongoing;
	}
	
    @Override
    public String toString() {
        return "Anime [id=" + id + ", English Name=" + name_english + "]";
    }

}































