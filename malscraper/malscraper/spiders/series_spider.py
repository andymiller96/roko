# -*- coding: utf-8 -*-
"""
Created on Mon Apr 27 17:49:57 2020

@author: Kendrick

This class will take any season within a series and create a series out of it by adding the name, url and thumbnail of each season outputted to a json file, in proper order
"""

import scrapy
import logging

class SeriesSpider(scrapy.Spider):
    name = "series"

    start = False
    #response.css('img').xpath('@data-src')[0].get()
    #scrapy crawl series -a anime="https://myanimelist.net/anime/2025/Darker_than_Black__Kuro_no_Keiyakusha" -t json -o - > series.json
    
    #What we need to href to sequel
    #response.xpath('//tr/td[text()="Sequel:"]/following-sibling::td').get()
    
    start_urls = []

    custom_settings = {
    #    'DEPTH_LIMIT': 10
    }
    

    def start_requests(self):
         
        logger = logging.getLogger('testlogger')
        anime = self.anime
        logger.error("url: " + str(anime))
        self.start_urls.append(anime)
        
        for url in self.start_urls:
            yield scrapy.Request(url=url, callback=self.parse)

        

    



    def parse(self, response):
        
        logger = logging.getLogger('testlogger')
        #If not a prequel, we can start
        prequel = response.xpath('//tr/td[text()="Prequel:"]/following-sibling::td/a/@href').get()
        #If we are at the very beginning / first season in the series
        if not(prequel):
            aname = response.xpath('//div/span[text()="English:"]/following-sibling::text()').get()
            if (aname != None):
                aname = aname.strip()
            else:
                aname = response.xpath('//span[@itemprop="name"]/text()').get().strip()
            
            name_japanese = response.xpath('//div/span[text()="Japanese:"]/following-sibling::text()').get()
            if (name_japanese != None):
                name_japanese = name_japanese.strip()
                
            url = response.url
            thumbnail = response.css('img').xpath('@data-src')[0].get()
            episodes = response.xpath('//div/span[text()="Episodes:"]/following-sibling::text()').get()
            if (episodes != None):
                episodes = episodes.strip()
            
            premiered = response.xpath('//div/span[text()="Premiered:"]/following-sibling::a/text()').get()
            if (not premiered):
                premiered = response.xpath('//div/span[text()="Aired:"]/following-sibling::text()').get()
            
                premiered = premiered.strip()[len(premiered) - 10: len(premiered)]
            else:
                premiered = premiered.strip()[len(premiered) - 4: len(premiered)]
    
            status = response.xpath('//div/span[text()="Status:"]/following-sibling::text()').get()
            if (status != None):
                status = status.strip()
            synopsis = response.xpath('//span[@itemprop="description"]/text()').getall()
            score = response.xpath('//div/span[text()="Score:"]/following-sibling::span/text()').get()        
            
            yield {    
                'name_english' : aname,
                'name_japanese': name_japanese, 
                'url': url,
                'thumbnail' : thumbnail,
                'episodes' : episodes,
                'premiered' : premiered,
                'status' : status,
                'synopsis' : synopsis,
                'score' : score
            }
        
            next_page = response.xpath('//tr/td[text()="Sequel:"]/following-sibling::td/a/@href').get()
            if next_page:
                #url = response.urljoin(response.xpath('//tr/td[text()="Sequel:"]/following-sibling::td/a/text()').get())
                url = "https://myanimelist.net" + str(response.xpath('//tr/td[text()="Sequel:"]/following-sibling::td/a/@href').get())
                logger.error("URL" + str(url))
                yield scrapy.Request(url, callback = self.parseSequel, dont_filter=True)
        #Otherwise keep going to the prequel
        else:
            logger.error("In Else")
            url = response.urljoin(response.xpath('//tr/td[text()="Prequel:"]/following-sibling::td/a/@href').get())
            yield scrapy.Request(url, callback = self.parse)
            
            
            
            
            
    def parseSequel(self, response):
        
        logger = logging.getLogger('testlogger')
        logger.error("In other func")
        
        aname = response.xpath('//div/span[text()="English:"]/following-sibling::text()').get()
        if (aname != None):
            aname = aname.strip()
        else:
            aname = response.xpath('//span[@itemprop="name"]/text()').get().strip()
        
        name_japanese = response.xpath('//div/span[text()="Japanese:"]/following-sibling::text()').get()
        if (name_japanese != None):
            name_japanese = name_japanese.strip()
            
        url = response.url
        thumbnail = response.css('img').xpath('@data-src')[0].get()
        episodes = response.xpath('//div/span[text()="Episodes:"]/following-sibling::text()').get()
        if (episodes != None):
            episodes = episodes.strip()
        
        premiered = response.xpath('//div/span[text()="Premiered:"]/following-sibling::a/text()').get()
        if (not premiered):
            logger.error("NOT PREMIERED")
            premiered = response.xpath('//div/span[text()="Aired:"]/following-sibling::text()').get()
            premiered = premiered.strip()[len(premiered) - 10: len(premiered)]
        else:
            premiered = premiered.strip()[len(premiered) - 4: len(premiered)]

        status = response.xpath('//div/span[text()="Status:"]/following-sibling::text()').get()
        if (status != None):
            status = status.strip()
        synopsis = response.xpath('//span[@itemprop="description"]/text()').getall()
        score = response.xpath('//div/span[text()="Score:"]/following-sibling::span/text()').get()        
        
        yield {    
            'name_english' : aname,
            'name_japanese': name_japanese, 
            'url': url,
            'thumbnail' : thumbnail,
            'episodes' : episodes,
            'premiered' : premiered,
            'status' : status,
            'synopsis' : synopsis,
            'score' : score
        }
        next_page = response.xpath('//tr/td[text()="Sequel:"]/following-sibling::td/a/@href').get()
        if next_page:
            url = response.urljoin(response.xpath('//tr/td[text()="Sequel:"]/following-sibling::td/a/@href').get())
            yield scrapy.Request(url, callback = self.parseSequel, dont_filter=True)
                
                         
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                