package com.kendrick.angularspringboot.roko.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kendrick.angularspringboot.roko.exception.ResourceNotFoundException;
import com.kendrick.angularspringboot.roko.helper.MALHelper;
import com.kendrick.angularspringboot.roko.model.Anime;
import com.kendrick.angularspringboot.roko.model.AnimeSeries;
import com.kendrick.angularspringboot.roko.model.SearchResult;
import com.kendrick.angularspringboot.roko.repository.AnimeSeriesRepository;
import com.kendrick.angularspringboot.roko.service.AnimeSeriesService;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class AnimeSeriesController {

	@Autowired
	private AnimeSeriesRepository animeSeriesRepo;
	
	
    @GetMapping("/series")
    public List<AnimeSeries> getAllAnimeSeries() {
        return animeSeriesRepo.findAll();
    }
    
    @GetMapping("series/{id}")
    public ResponseEntity<AnimeSeries> getAnimeById(@PathVariable(value = "id") final Long seriesId)
            throws ResourceNotFoundException {
        AnimeSeries series = animeSeriesRepo.findById(seriesId)
          .orElseThrow(() -> new ResourceNotFoundException("Anime not found for this id :: " + seriesId));
        return ResponseEntity.ok().body(series);
    }
    
    @PostMapping("/series")
    // When Spring Boot finds an argument annotated with @Valid, it
    // automatically bootstraps the default JSR 380
    // implementation — Hibernate Validator — and validates the argument.
    public AnimeSeries createAnimeSeries(@Valid @RequestBody final AnimeSeries anime) {
        return animeSeriesRepo.save(anime);
    }

    
    @DeleteMapping("series/{id}")
    public Map<String, Boolean> deleteAnimeSeries(@PathVariable(value = "id") Long seriesId) 
        throws ResourceNotFoundException {
    	AnimeSeries series = animeSeriesRepo.findById(seriesId).orElseThrow(() -> new ResourceNotFoundException("Anime not found for this id :: " + seriesId));
            animeSeriesRepo.delete(series);
            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);
            return response;
    }
    
    @PutMapping("series/{id}")
    public ResponseEntity<AnimeSeries> updateAnimeSeries(@PathVariable(value = "id") final Long seriesId,
            @Valid @RequestBody final AnimeSeries seriesDetails) throws ResourceNotFoundException {
    	AnimeSeries series = animeSeriesRepo.findById(seriesId)
            .orElseThrow(() -> new ResourceNotFoundException("Anime not found for this id :: " + seriesId));    
    	
    	
    	//System.out.println("The anime titles: " + seriesDetails.getSeasons());
    	ArrayList<Anime> animeList = (ArrayList<Anime>) seriesDetails.getSeasons();
        
    	for(int i = 0; i < animeList.size(); i++) {
    		animeList.get(i).setSeries(series);
    	}
    	series.setSeasons(animeList);
    	series.setWatchStatus(AnimeSeriesService.checkWatchStatus(series));
    	
    	
        final AnimeSeries updatedSeries = animeSeriesRepo.save(series);
        System.out.println("Seasons: " + updatedSeries.getSeasons());
        
        
        return ResponseEntity.ok(updatedSeries);
    }
    
    
	//TODO: Not sure, for the animeList and adding Anime as well as AnimeSeries, if it's better to have two repos function in this method
    //TODO: Or to make an rest api call to the create Anime in MALHelper when they're being processed from the JSON
    @PostMapping("addMALSeries")
    //Add anime from the search list to the catalog
    public AnimeSeries addMALAnimeSeries(@Valid @RequestBody final SearchResult searchResult) {
    	AnimeSeries series = new AnimeSeries();
    	ArrayList<Anime> animeList = null;
    	
    	System.out.println(searchResult);
    	//Fake a series for now:
    	//int[] seasons = {31,32};
    	
    	//series.setIdSeasons(seasons);
    	//series.setSeriesName("Test Name");
    	
    	//will generate the JSON file with all of the series related to the anime
    	MALHelper.runAnimeSeriesScrape(searchResult);
    	
    	//Parse JSON file and get a list of all of the Anime
    	animeList = MALHelper.readJSONAnimeSeries("malscraper/series.json");
    	//Assign Season 1 name as the name of the series
    	series.setSeriesName(animeList.get(0).getName_english());
    	//Assign Season 1 thumbnail as thumbnail for series
    	series.setMalThumbnail(animeList.get(0).getMalThumbnail());
    	
    	for(int i = 0; i < animeList.size(); i++) {
    		animeList.get(i).setSeries(series);
    	}
    	series.setSeasons(animeList);
    	//save the Animes
    	//TODO: Need to make the JSON that gets parsed above to have all of the Anime data, so will need to use scrapy to get.
    	//animeList = (ArrayList<Anime>) animeRepo.saveAll(animeList);
    	
    	
    	//save new anime object
    	System.out.println("Series: " + series);
    	
    	
    	
    	//return anime;
    	return animeSeriesRepo.save(series);
    }
    
	
}
