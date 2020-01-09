package com.kendrick.angularspringboot.roko.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import com.kendrick.angularspringboot.roko.model.Employee;
import com.kendrick.angularspringboot.roko.repository.AnimeRepository;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
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
    
    @PostMapping("/anime")
    // When Spring Boot finds an argument annotated with @Valid, it
    // automatically bootstraps the default JSR 380
    // implementation — Hibernate Validator — and validates the argument.
    public Anime createAnime(@Valid @RequestBody final Anime anime) {
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
        anime.setOngoing(animeDetails.getOngoing());
        
        
        final Anime updatedAnime = animeRepo.save(anime);
        return ResponseEntity.ok(updatedAnime);
    }
    
    @DeleteMapping("/anime/{id}")
    public Map<String, Boolean> deleteAnime(@PathVariable(value = "id") Long animeId) 
        throws ResourceNotFoundException {
            Anime anime = animeRepo.findById(animeId).orElseThrow(() -> new ResourceNotFoundException("Anime not found for this id :: " + animeId));
            animeRepo.delete(anime);
            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);
            return response;
        }
	
}





































