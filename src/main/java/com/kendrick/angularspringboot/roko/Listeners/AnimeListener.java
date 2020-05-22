package com.kendrick.angularspringboot.roko.Listeners;

import javax.persistence.PostUpdate;

import com.kendrick.angularspringboot.roko.model.Anime;

public class AnimeListener {

	
	@PostUpdate
	void onPostUpdate(Anime anime) {
		System.out.println("MyEntityListener.onPostUpdate(): " + anime);
		System.out.println("Series: " + anime.getSeries().getId());   

		
	}
  
}
