package com.kendrick.angularspringboot.roko.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.kendrick.angularspringboot.roko.model.Anime;
import com.kendrick.angularspringboot.roko.model.SearchResult;
import com.kendrick.angularspringboot.roko.repository.AnimeRepository;
import com.kendrick.angularspringboot.roko.shared.ProcessRunner;


import lombok.extern.log4j.Log4j2;

import com.kendrick.angularspringboot.roko.helper.MALHelper;
import com.kendrick.angularspringboot.roko.helper.TVDBHelper;


//TODO: Review all of the mappings, make sure they are best practice / make sense

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
@Log4j2
public class AnimeController {
	
	@Autowired
	private AnimeRepository animeRepo;
	
    @GetMapping("/anime")
    public List<Anime> getAllAnime() {
        return animeRepo.findAll();
    }
    
    @GetMapping("anime/{id}")
    public ResponseEntity<Anime> getAnimeById(@PathVariable(value = "id") final Long animeId)
            throws ResourceNotFoundException {
        Anime anime = animeRepo.findById(animeId)
          .orElseThrow(() -> new ResourceNotFoundException("Anime not found for this id :: " + animeId));
        return ResponseEntity.ok().body(anime);
    }
    
    //Fetch Anime information from MAL
    @GetMapping("searchMAL/{name}")
    public ArrayList<SearchResult> searchAnimeFromMAL(@PathVariable(value = "name") String fetchName)
            throws ResourceNotFoundException {
    	
    	
		//And is at least 3 characters
		fetchName = fetchName.trim();
		fetchName = fetchName.replaceAll("\\s", "%20");
		
		System.out.println("Anime to fetch: " + fetchName);
    	
		//TODO: Move this into a function inside MALHelper? Make it it's own API?
		//Commands to run scrapy
		String[] commands = {"cmd.exe", "/c", "cd \"G:\\Workspace\\roko\\malscraper\" && scrapy crawl search -a anime=\"" + fetchName + "\" -t json -o - > testmyscrape.json"};
		//Run process builder to run scrapy commands defined above, which will generate a file "testmyjsonscrape"
		ProcessRunner.runProcessBuilder(commands);
		
    	//Parse the resulting json file that was created after running scrapy python script
		ArrayList<SearchResult> results = MALHelper.getMALSearchResults();
        
        
        return results;
    }
    
    //Fetch Anime information from TVDB
    //Will return a JSON response
    @GetMapping("searchTVDB/{name}")
    public ArrayList<SearchResult> searchAnimeFromTVDB(@PathVariable(value = "name") String fetchName)
            throws ResourceNotFoundException {

    	
		//And is at least 3 characters
		fetchName = fetchName.trim();
		fetchName = fetchName.replaceAll("\\s", "%20");

    	TVDBHelper tvdb = new TVDBHelper();
    	
    	//Token / login will work for 24hrs unless refreshed. 
    	//TODO: Find way to check if it's been 24hrs / have a test ping and login if it returns error
    	tvdb.login();
    	//Fetch anime name, return SearchResult
    	ArrayList<SearchResult> results = tvdb.searchForAnime(fetchName);
        
        return results;
    }
    
    
    @PostMapping("anime")
    // When Spring Boot finds an argument annotated with @Valid, it
    // automatically bootstraps the default JSR 380
    // implementation — Hibernate Validator — and validates the argument.
    public Anime createAnime(@Valid @RequestBody final Anime anime) {
        return animeRepo.save(anime);

    }
    
    @PutMapping("addMAL")
    //Add anime from the search list to the catalog
    public Anime addMALAnime(@Valid @RequestBody final SearchResult animeResult) {
    	Anime anime = null;
    	
    	//will generate the JSON file
    	MALHelper.runAnimeScrape(animeResult);
    	
    	//ParseJSON file and add info to anime object
    	anime = MALHelper.readJSONAnime();
    	anime.setMalUrl(animeResult.getUrl());
    	anime.setMalThumbnail(animeResult.getThumbnail());
    	anime.setName_english(animeResult.getName());
    	
    	//save new anime object
    	return animeRepo.save(anime);
    }
    
    
    @PutMapping("addTVDB")
    //Add anime from the search list to the catalog
    public Anime addTVDBAnime(@Valid @RequestBody final SearchResult animeResult) {
    	//TODO: Create TVDB adder using API
    	Anime anime = TVDBHelper.getAnimeFromTVDB(animeResult);
    	
    	//save new anime object
    	return animeRepo.save(anime);
    }
    
    @PutMapping("anime/{id}")
    public ResponseEntity<Anime> updateAnime(@PathVariable(value = "id") final Long animeId,
            @Valid @RequestBody final Anime animeDetails) throws ResourceNotFoundException {
        Anime anime = animeRepo.findById(animeId)
            .orElseThrow(() -> new ResourceNotFoundException("Anime not found for this id :: " + animeId));        
        
        anime.setName_english(animeDetails.getName_english());
        anime.setName_japanese(animeDetails.getName_japanese());
        anime.setRating(animeDetails.getRating());
        anime.setYear(animeDetails.getYear());
        anime.setEpisodes(animeDetails.getEpisodes());
        anime.setDescription(animeDetails.getDescription());
        anime.setSeasons(animeDetails.getSeasons());
        anime.setStatus(animeDetails.getStatus());
        anime.setWatchStatus(animeDetails.getWatchStatus());
        
        final Anime updatedAnime = animeRepo.save(anime);
        return ResponseEntity.ok(updatedAnime);
    }
    
    @DeleteMapping("anime/{id}")
    public Map<String, Boolean> deleteAnime(@PathVariable(value = "id") Long animeId) 
        throws ResourceNotFoundException {
            Anime anime = animeRepo.findById(animeId).orElseThrow(() -> new ResourceNotFoundException("Anime not found for this id :: " + animeId));
            animeRepo.delete(anime);
            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);
            return response;
    }

    
}





































