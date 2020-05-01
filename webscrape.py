# -*- coding: utf-8 -*-
"""
Created on Thu Mar 26 11:26:10 2020

@author: Kendrick
"""

from bs4 import BeautifulSoup
import requests
import sys
#import pandas as pd

#take this var from command line (coming from Spring Boot)
anime = sys.argv[1];
search_url = "https://myanimelist.net/anime.php?q=" + anime

#Must have at least 3 byte characters to search. <-- ensure arg[1] is at least 3 characters
#print(sys.stdout.encoding)

#print(search_url)


search_results = {}

names = []
urls = []
thumbnails = []
#print(sys.stdout.encoding)
 
#Query the search
#Store all of the search items in an array with name / id / thumbnail information and pass it back to angular app
#Go back to WebApp with the suggestions for user to select
#Return here with user input (this time will have ID with anime so we know which one user needs, exactly)
 
page = requests.get(search_url)
soup = BeautifulSoup(page.content, 'html.parser')
 
table = soup.findAll('table')[2]
table_rows = table.findAll('tr')
 
# =============================================================================
# count = 0;
# table_row = table.findAll('tr')
# for row in table_row:
#     cell = row.findAll('td')
#     for c in cell:
#         #print(c)
#         images = c.findAll('img')
#         for img in images:
#             temp = img['data-srcset'].split()
#             thumbnails.append(temp[2])
#         links = c.findAll('a', href=True, class_="hoverinfo_trigger fw-b fl-l")
#         for link in links:
#             count = count + 1
#             sname = link.findAll('strong')
#             name = [i.text for i in sname]
#             #s = name[0].encode(encoding='UTF-8')
#             search_results[name[0].encode('utf-8') = link['href']
#             names.append(name[0].encode('utf-8'))
#             #names.append(link['href'])
#             if(count < 50):
#                 names.append("--")
# =============================================================================


# =============================================================================
# for tr in table_rows:
#     td = tr.findAll('strong')
#     name = [i.text for i in td]
#     search_results.append(name)
#     print(name)
# =============================================================================
 

#Try and get the big thumbnail from the home page of each anime
# =============================================================================
# def getLargeThumbnail(animeURL):
#     #From the animeURL, parse that page and get the big thumbnail
#     animePage = requests.get(animeURL)
#     animeSoup = BeautifulSoup(animePage.content, 'html.parser')
#     table = animeSoup.findAll('table')[0]
#     table_img = table.findAll('img')[0]
#     thumbnails.append(table_img['data-src'].encode('utf-8'))
# =============================================================================
    
#This is used to get the MAL url of the search results as well as the name and cram em into a dictionary
    #with format: Anime Name : Anime MAL URl
    #Also add in logic to return the thumbnail URL
    #There are two thumbnail URLs, the second one is bigger so we're extracting it
# =============================================================================
# image_thumbnails = table.findAll('img')
# for image in image_thumbnails:
#     temp = image['data-srcset'].split()
#     thumbnails.append(temp[2].encode('utf-8'))
#     #print(temp[2])
#     #print("___________________________")
# =============================================================================

count = 0
links = table.findAll('a', href=True, class_="hoverinfo_trigger fw-b fl-l")
for link in links:
    count = count + 1
    #Add links to list
    #print(link['href'])
    sname = link.findAll('strong')
    name = [i.text for i in sname]
    #print(name[0])
    #search_results[name[0].encode('utf-8')] = link['href']
    #names.append("\"" + str(name[0].encode("utf-8")) + "\"")
    names.append(name[0].encode("utf-8"))
    urls.append(link['href'].encode("utf-8"))
    #getLargeThumbnail(link['href'])
    if(count < 50):
        names.append("-_--")
        urls.append("-_--")
    #print("________________")

   
#Dictionary
#print(search_results)   
    
#Array
print(names)
print(urls)

#print(len(search_results))
#print(len(thumbnails))

#return_result = []
#return_result.append(search_results)
#return_result.append(thumbnails)


#Using Pandas to work with HTML Tables
# =============================================================================
# dfs = pd.read_html(search_url)
# for df in dfs:
#     search_results.append(df)
#     
# print(search_results[2])
# =============================================================================

