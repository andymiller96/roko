# -*- coding: utf-8 -*-
import scrapy
from bs4 import BeautifulSoup
import requests
import logging

class SearchSpider(scrapy.Spider):
    name = "search"

        
    #response.css('img').xpath('@data-src')[0].get()
    #scrapy crawl search -a anime=Boruto -t json -o - > testmyscrape.json
    
    
    
    

    def start_requests(self):
        
        logger = logging.getLogger('testlogger')
        logger.error("I LOG SOMETHING")
        
        start_urls = []
        anime = self.anime
        search_url = "https://myanimelist.net/anime.php?q=" + anime
        
        page = requests.get(search_url)
        soup = BeautifulSoup(page.content, 'html.parser')
         
        table = soup.findAll('table')[2]
        
        names = []
        #urls = []
        
        links = table.findAll('a', href=True, class_="hoverinfo_trigger fw-b fl-l")
        for link in links:
            #Add links to list
            sname = link.findAll('strong')
            name = [i.text for i in sname]
            names.append(name[0].encode("utf-8"))
            #urls.append(link['href'].encode("utf-8"))
            start_urls.append(link['href'])
        
        with open('search_names.txt', 'w') as f:
            for item in names:
                f.write("%s\n" % item)
        
        for url in start_urls:
            yield scrapy.Request(url=url, callback=self.parse)
                
                


    def parse(self, response):
        
        yield {
            'name' : response.css('span::text')[1].get(),
            'url': response.request.url,
            'thumbnail' : response.css('img').xpath('@data-src')[0].get()
        }
            
            
            
            
            

            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            