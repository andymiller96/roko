package com.kendrick.angularspringboot.roko.helper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.ibm.icu.impl.Utility;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.kendrick.angularspringboot.roko.model.Anime;
import com.kendrick.angularspringboot.roko.model.AnimeSeries;
import com.kendrick.angularspringboot.roko.model.SearchResult;
import com.kendrick.angularspringboot.roko.shared.ProcessRunner;

//This class holds functions to help scrape the Search Results page when looking for an Anime
public class MALHelper {
	
	static ArrayList<SearchResult> results = null;
	static HashMap<String, SearchResult> map = null;

	
	//TODO: Revisit this at some point and figure out how to fix encoding issues (Like Candy Boy)
	public static String decodeUTF8(String encodedString) {
		
    	//Unicode Tester
    	/*
    	String s2 = "\\xe2\\x90\\xa3";
    	String utf8_str2 = Utility.unescape(s2);
    	byte[] b2 = utf8_str2.getBytes("ISO-8859-1");
    	String java_utf_str2 = new String(b2, "UTF-8");
    	System.out.println(java_utf_str2);
    	*/
		
		String utf8String = "";
		byte[] b;
		String decodedUtf8 = "";
		
		try {
	    	utf8String = Utility.unescape(encodedString);
	    	b = utf8String.getBytes("ISO-8859-1");
	    	decodedUtf8 = new String(b, "UTF-8");
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return decodedUtf8;
		
	}
	
	
	//This function will read the JSON that was created as well as the name file (for ordering purposes, for relevance)
	public static ArrayList<SearchResult> getMALSearchResults() {
		map = new HashMap<String, SearchResult>();
		results = new ArrayList<SearchResult>();
		SearchResult sr = null;
		
		
		JSONArray animeList = JSONHelper.parseJSONFile("malscraper/testmyscrape.json");
        //Iterate over anime json list
        for(int i = 0; i < animeList.size(); i++) {
        	sr = JSONHelper.parseMALObject( (JSONObject) animeList.get(i));
        	map.put(sr.getName(), sr); 
        }
        results = sortAnime(map);
        
		return results;
	}
	
	
    private static ArrayList<SearchResult> sortAnime(HashMap<String, SearchResult> map) {
    	BufferedReader reader;
    	String name = null;
    	ArrayList<SearchResult> sortedList = new ArrayList<SearchResult>();
    	
    	
		try {
			reader = new BufferedReader(new FileReader("malscraper/search_names.txt"));
			String line = reader.readLine();
			while (line != null) {
				
				name = decodeUTF8(line);
				name = name.substring(2, name.length()-1);
				//System.out.println("Name: " + name);
				
				sortedList.add(map.get(name));
				
				
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		return sortedList;
		
		
    }
    
    
	//Run the python script that pulls in the Anime Data
	public static void runAnimeScrape(SearchResult anime) {
		ProcessRunner.runProcess("python animescrape.py " + anime.getUrl());
    	System.out.println("JSON file as been generated.");

	}
	
	
	//Read the file generated from runScript
	//TODO: Organize file structure of all of the python scripts
	public static Anime readJSONAnime() {
		JSONParser jsonParser = new JSONParser();
		
		Anime anime = null;
		
        try (FileReader reader = new FileReader("anime.json")) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
 
            JSONObject animeList = (JSONObject) obj;

            //Iterate over anime json list
            for(int i = 0; i < animeList.size(); i++) {
            	anime = parseAnimeObject(animeList);
            }
 
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

        
        return anime;
	}
	
	
    private static Anime parseAnimeObject(JSONObject anime) {

    	Anime newAnime = new Anime();
    	
    	System.out.println("JSONObject : " + anime);
    	
    	String animeNameJap = (String) anime.get("name_japanese");
    	
    	//"Uknown" or int
    	String episodesJSON = (String) anime.get("episodes");
    	
    	System.out.println("episodesJSON : " + episodesJSON);
    	
    	int episodes = 0;
    	
    	if(!episodesJSON.equals("Unknown")) {
    		episodes = Integer.parseInt(episodesJSON);
    	}
    	
    	//"Ongoing or "Finished Airing"
    	String statusJSON = (String) anime.get("status");
    	//0 means anime is finished
    	int status = 0;
    	
    	if(statusJSON.equals("Ongoing")) {
    		status = 1;
    	}
    	
    	//Season and Year, think I just want year
    	String premieredJSON = (String) anime.get("premiered");
    	int premiered = Integer.parseInt(premieredJSON.substring(premieredJSON.length()-4, premieredJSON.length()));
    	
    	
    	String synopsis = (String) anime.get("synopsis");
    	
    	//Float or "N/A"
    	String ratingJSON = (String) anime.get("rating");
    	float rating = (float) 0.0;
    	
    	if(!ratingJSON.equals("N/A")) {
    		rating = Float.parseFloat(ratingJSON);
    	}
    	
    	int seasons = 0;
    	//{"anime": [{ treasure in the world, One Piece. It was this revelation that brought about the Grand Age of Pirates, men who dreamed of finding One Piece\u2014which promises an unlimited amount of riches and fame\u2014and quite possibly the pinnacle of glory and the title of the Pirate King.\n\r\nEnter Monkey D. Luffy, a 17-year-old boy who defies your standard definition of a pirate. Rather than the popular persona of a wicked, hardened, toothless pirate ransacking villages for fun, Luffy\u2019s reason for being a pirate is one of pure wonder: the thought of an exciting adventure that leads him to intriguing people and ultimately, the promised treasure. Following in the footsteps of his childhood hero, Luffy and his crew travel across the Grand Line, experiencing crazy adventures, unveiling dark mysteries and battling strong enemies, all in order to reach the most coveted of all fortunes\u2014One Piece.\n\r\n[Written by MAL Rewrite] ",
    	//"rating": "8.48"}]}
    	
    	newAnime.setName_japanese(animeNameJap);
    	newAnime.setDescription(synopsis);
    	newAnime.setEpisodes(episodes);
    	newAnime.setOngoing(status);
    	newAnime.setRating(rating);
    	newAnime.setYear(premiered);
    	newAnime.setSeasons(seasons);
    	
    	
    	return newAnime;
    }

    
    
    //// For Anime Series ////
    ////				  ////
    
    
	//TODO: Scrape an anime and get all prequels and sequels. Get All Information
	//cases: first one (no prequels) middle one (sequels and prequels) last one (only prequels)
	public static void runAnimeSeriesScrape(SearchResult series) {
		
		String[] commands = {"cmd.exe", "/c", "cd \"G:\\Workspace\\roko\\malscraper\" && scrapy crawl series -a anime=\"" + series.getUrl() + "\" -t json -o - > series.json"};
		//Run process builder to run scrapy commands defined above, which will generate a file "series"
		//which contains all of the anime in the sequel / prequel lineage
		ProcessRunner.runProcessBuilder(commands);
		
	}
    
	public static ArrayList<Anime> readJSONAnimeSeries(String file) {
		JSONParser jsonParser = new JSONParser();
		
		AnimeSeries series = new AnimeSeries();
		Anime anime = null;
		ArrayList<Anime> animeList = new ArrayList<Anime>();
		
        try (FileReader reader = new FileReader(file)) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
 
            JSONArray seriesList = (JSONArray) obj;

            //Iterate over anime json list
            for(int i = 0; i < seriesList.size(); i++) {
            	anime = parseAnimeObject((JSONObject) seriesList.get(i));
            	
            	//TODO: Store anime in Anime array for AnimeSeries to use later
            	animeList.add(anime);
            } 
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

        
        return animeList;
	}
    
	/*
    private static Anime parseAnimeSeriesObject(JSONObject series) {
    	System.out.println("In the Parse: " + series);
    	
    	
    	return newAnime;
    }
    */
    
}





















