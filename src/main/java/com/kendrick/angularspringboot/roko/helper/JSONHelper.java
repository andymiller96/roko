package com.kendrick.angularspringboot.roko.helper;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.kendrick.angularspringboot.roko.model.Anime;
import com.kendrick.angularspringboot.roko.model.SearchResult;
import com.kendrick.angularspringboot.roko.shared.ProcessRunner;

//Class purpose is to contain most / all of the JSON related reusable functions
public class JSONHelper {

	
	
	//This function will read the JSON that was created as well as the name file (for ordering purposes, for relevance)
	public static JSONArray parseJSONFile(String relativeFilePath) {
		JSONParser jsonParser = new JSONParser();
		JSONArray jsonList = null;
		
        try (FileReader reader = new FileReader(relativeFilePath)) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
 
            jsonList = (JSONArray) obj;
 
        } 
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } 
        catch (IOException e) {
            e.printStackTrace();
        } 
        catch (ParseException e) {
            e.printStackTrace();
        }
		
        
        
		return jsonList;
	}
    
    
    //Takes in a string and return a JSON Object
    public static JSONObject parseJSONString(String jsonString) {
    	JSONParser parser = new JSONParser();
		JSONObject json = null;
		
		
		try {
			json = (JSONObject) parser.parse(jsonString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return json;
    	
    }
    
    //Takes in a string that contains multiple JSONObjects
    public static JSONArray parseMultipleJSONString(String jsonString) {
    	JSONParser parser = new JSONParser();
    	JSONArray jsonList = null;
		JSONObject json = null;
		
		
		try {
			jsonList = (JSONArray) parser.parse(jsonString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return jsonList;
    	
    }
    
	
	
    public static SearchResult parseMALObject(JSONObject jsonObj) {

    	String animeName = (String) jsonObj.get("name");
    	System.out.println("Anime Name: " + animeName);
    	
    	String animeURL = (String) jsonObj.get("url");
    	System.out.println("Anime URL: " + animeURL);
    	
    	String animeThumbnail = (String) jsonObj.get("thumbnail");
    	System.out.println("Anime Thumbnail: " + animeThumbnail);
    	
    	
    	return new SearchResult(animeName, animeURL, animeThumbnail);
    }
    
    
    //This one seemingly requires special attention since the full thumbnail and series url's are not given
    public static SearchResult parseTVDBSearchObject(JSONObject jsonObj, String seriesBaseUrl, String imageBaseUrl) {

    	SearchResult anime = new SearchResult();
    	
    	String animeName = (String) jsonObj.get("seriesName");
    	System.out.println("Anime Name: " + animeName);
    	
    	String animeURL = (String) jsonObj.get("slug");
    	System.out.println("Anime URL: " + animeURL);
    	
    	String animeThumbnail = (String) jsonObj.get("image");

    	long id = (long) jsonObj.get("id");
    	
    	anime.setName(animeName);
    	anime.setUrl(seriesBaseUrl + animeURL);
    	anime.setThumbnail(imageBaseUrl + animeThumbnail);
    	anime.setAnimeId(id);
    	
    	return anime;
    }
    
    
    public static Anime parseTVDBObject(JSONObject jsonObj) {
    	Anime a = new Anime();

    	//year the anime aired
    	String yearString = jsonObj.get("firstAired").toString().substring(0, 4);
    	int year = Integer.parseInt(yearString);
    	
    	//anime description
    	String description = (String) jsonObj.get("overview");
    	
    	//number of seasons in the anime
    	String seasonsString = (String) jsonObj.get("season");
    	int seasons = Integer.parseInt(seasonsString);
    	
    	//is the anime ongoing or ended?
    	String ongoingString = (String) jsonObj.get("status");
    	int ongoing = 1;
    	if(ongoingString.equals("Ended")) {
    		ongoing = 0;
    	}
    	
    	
    	a.setYear(year);
		a.setDescription(description);
		a.setSeasons(seasons);
		a.setOngoing(ongoing);
		
		
		return a;
    	
    	
    }

    
    
    
	public static Object processQuery(String queryUrl) {
		String result = ProcessRunner.runProcess(queryUrl);
		
		JSONObject obj = JSONHelper.parseJSONString(result);
		//Sometimes a JSON array, sometimes a JSON object
		Object arr = (Object) obj.get("data");
		
		
		
		return arr;
	}
	
	

}
