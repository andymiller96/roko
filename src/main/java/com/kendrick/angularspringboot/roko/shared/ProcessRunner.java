package com.kendrick.angularspringboot.roko.shared;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.kendrick.angularspringboot.roko.helper.JSONHelper;

public class ProcessRunner {

	public static String runProcess(String process) {
    	String s = null;
    	String firstLine = null;
    	try {
	    	//Run webscraper to get info, save in JSON file
	    	Process p = Runtime.getRuntime().exec(process);
	        
	        
	    	BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
	    	BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

	    	
            System.out.println("Here is the standard output of the process:\n");
            firstLine = stdInput.readLine();
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
            
            // read any errors from the attempted command
            System.out.println("Here is the standard error of the process (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
    	}
    	catch(Exception e) {
    		System.out.println("exception occurred: ");
            e.printStackTrace();
    	}
    	
    	return firstLine;
	}
	
	
	public static String runProcessBuilder(String[] processes) {
    	//run python script here
    	String s = null;
    	try {
    		//TODO: Not sure if this is the best way to do it, but scrapy MUST execute in the 
    		//malscraper dir (one below working dir)
	    	ProcessBuilder builder = new ProcessBuilder(processes);
	    	
	    	builder.redirectErrorStream(true);
	        Process pr = builder.start();
	        
	        
	    	BufferedReader stdInput = new BufferedReader(new InputStreamReader(pr.getInputStream()));
	    	BufferedReader stdError = new BufferedReader(new InputStreamReader(pr.getErrorStream()));

	        while ((s = stdInput.readLine()) != null) {
	            System.out.println("The output: " + s);
	        }
	        pr.waitFor();
	        System.out.println("Waiting ...");

	        // read any errors from the attempted command
	        System.out.println("Here is the standard error of the command (if any):\n");
	        while ((s = stdError.readLine()) != null) {
	            System.out.println(s);
	        }
	        
    	}
    	catch (Exception e) {
            System.out.println("exception occurred: ");
            e.printStackTrace();
        }
		
		
		return s;
	}
	
	
	
	
}
