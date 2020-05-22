package com.kendrick.angularspringboot.roko.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kendrick.angularspringboot.roko.model.AnimeSeries;
import com.kendrick.angularspringboot.roko.repository.AnimeSeriesRepository;
import com.kendrick.angularspringboot.roko.model.Anime;

@Service
public class AnimeSeriesService {

	@Autowired
	private AnimeSeriesRepository animeSeriesRepo;
	
	
	public static int checkWatchStatus(AnimeSeries series) {
		
		List<Anime> anime = (List<Anime>) series.getSeasons();
		int watched = 0;
		int inProgress = 0;
		
		//"Unwatched" = 0,
		//"Unfinished" = 1,
		//"Watched" = 2,
		int code = 0;
		
		for(int i = 0; i < anime.size(); i++) {
			if(anime.get(i).getWatchStatus() == 2) {
				watched++;
			}
			if(anime.get(i).getWatchStatus() == 1) {
				inProgress++;
			}
			
			
		}
		
		
		//series has been watched completely / up to date
		if(watched == anime.size()) {
			code = 2;
		}
		
		//only partially watched, unfinished
		else if(watched < anime.size() && inProgress > 0) {
			code = 1;
		}
		
		System.out.println("Code: " + code);
		
		return code;
	}
	
	
	public void test() {
		if(animeSeriesRepo != null) {
			animeSeriesRepo.findById((long) 109);
			System.out.println("Gotem");
		}
		
	}
	
}

































