package com.kendrick.angularspringboot.roko.helper;

import com.kendrick.angularspringboot.roko.model.Anime;
import com.kendrick.angularspringboot.roko.model.SearchResult;
import com.kendrick.angularspringboot.roko.shared.ProcessRunner;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class TVDBHelper {

	private static String apiKey = "";
	private static String username = "";
	private static String userKey = "";
	private static String loginUrl = "https://api.thetvdb.com/login";
	
	//Not sure if want these here or in another class
	private static String imageBaseUrl = "https://artworks.thetvdb.com";
	private static String seriesBaseUrl = "https://thetvdb.com/series/";
	
	private static String jwtoken = "";
	
	private static String loginCommand = "curl -s -X POST --header \"Content-Type: application/json\" --header \"Accept: application/json\" -d \"{ "
			+ "\\\"apikey\\\" : \\\""+ apiKey + "\\\", "
			+ "\\\"username\\\" : \\\"" + username + "\\\", "
			+ "\\\"userkey\\\": \\\"" + userKey + "\\\""
			+ "}\"";
	
	private static String baseCommand = "curl -X GET --header \"Accept: application/json\" --header \"Authorization: Bearer ";
	
	//Should only need to be called every 24 hours
	public void login() {
		
		//login string
		
		String login = loginCommand + " \"" + loginUrl + "\"";
		System.out.println("Login Query: " + login);
		
		String result = ProcessRunner.runProcess(login);
		//parse the JSON to get the token
		//Image for the show always seems to be: https://artworks.thetvdb.com/banners/XXXXX in which XXXXX can be extracted from the JSON "image" key
		
		//Pass the returned json string in and get an object back
		JSONObject json = JSONHelper.parseJSONString(result);
		String token = (String) json.get("token");
		//Add the token in so we can run the rest api
		TVDBHelper.setJwtoken(token);
		
	}
	
	
	public ArrayList<SearchResult> searchForAnime(String anime) {
		ArrayList<SearchResult> sr = new ArrayList<SearchResult>();
		
		//This will search TVDB and return JSON list of search results
		String searchQuery = baseCommand + jwtoken + "\" \"https://api.thetvdb.com/search/series?name=" + anime + "\"";

		JSONArray arr = (JSONArray) JSONHelper.processQuery(searchQuery);
		
		for(int i = 0; i < arr.size(); i++) {
			sr.add(JSONHelper.parseTVDBSearchObject( (JSONObject) arr.get(i), seriesBaseUrl, imageBaseUrl));
		}
		
		
		return sr;
	}



	public static Anime getAnimeFromTVDB(SearchResult anime) {
		String pullSeries = baseCommand + jwtoken + "\" \"https://api.thetvdb.com/series/" + anime.getAnimeId() + "\"";

		JSONObject obj = (JSONObject) JSONHelper.processQuery(pullSeries);
		
		Anime a = JSONHelper.parseTVDBObject(obj);
		
		//From the search result, we already have this info so dump it back into the anime object
    	a.setName_english(anime.getName());
    	a.setTvdbUrl(anime.getUrl());
    	a.setTvdbThumbnail(anime.getThumbnail());
    	a.setTvdbId(anime.getAnimeId());
		
		
		return a;
	}

	
	
	
	
	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		TVDBHelper.username = username;
	}

	public static String getJwtoken() {
		return jwtoken;
	}

	public static void setJwtoken(String jwtoken) {
		TVDBHelper.jwtoken = jwtoken;
	}
	
	public static String getApiKey() {
		return apiKey;
	}

	public static void setApiKey(String apiKey) {
		TVDBHelper.apiKey = apiKey;
	}

	public static String getUserKey() {
		return userKey;
	}

	public static void setUserKey(String userKey) {
		TVDBHelper.userKey = userKey;
	}

	public static String getLoginUrl() {
		return loginUrl;
	}

	public static void setLoginUrl(String loginUrl) {
		TVDBHelper.loginUrl = loginUrl;
	}

}
