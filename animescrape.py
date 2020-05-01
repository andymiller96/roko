# -*- coding: utf-8 -*-
"""
Created on Thu Apr  9 16:33:12 2020

@author: Kendrick
"""

from bs4 import BeautifulSoup
import requests
import sys
import json


data = {}


anime_url = sys.argv[1];
#anime_url = "https://myanimelist.net/anime/20/Naruto"

page = requests.get(anime_url)
soup = BeautifulSoup(page.content, 'html.parser')



#items to extract from the anime

title_english = "NOT FOUND"
title_japanese = "NOT FOUND"
thumbnail = "NOT FOUND"
episodes = "NOT FOUND"
status = "NOT FOUND"
premiered = "NOT FOUND"
synopsis = "NOT FOUND"
rating = "N/A"


##Find Vars
title_english = soup.findAll("div", class_="spaceit_pad")[0].text.strip()
title_english = title_english.replace('English: ', '')   


title_japanese = soup.findAll("div", class_="spaceit_pad")[2].text.strip()
title_japanese = title_japanese.replace('Japanese: ', '')     

episodes = soup.findAll("div", class_="spaceit")[0].text.strip()
episodes = episodes.replace('\n','')
episodes = episodes.replace('Episodes:  ', '')

if(episodes == 'Unknown'):
    status = "Ongoing"
else:
    status = "Finished Airing"


premiered = soup.findAll("span", class_="information season")[0].text


synopsis = soup.findAll("span", itemprop="description")[0].text


rating = soup.findAll("div", class_="score-label")[0].text


data = {
    'name_english': title_english,
    'name_japanese': title_japanese,
    'episodes': episodes,
    'status': status,
    'premiered': premiered,
    'synopsis': synopsis,
    'rating': rating
}
    
#print(data)
    
with open('anime.json', 'w') as outfile:
    json.dump(data, outfile)