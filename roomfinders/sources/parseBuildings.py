import json
import os
from random import randint

current_dir = os.path.dirname(__file__)
BUILDINGS_SOURCE_FILE_PATH = os.path.join(current_dir, 'buildings.json')
BUILDINGS_NEW_SOURCE_FILE_PATH = os.path.join(current_dir, 'buildings_new.json')

with open(BUILDINGS_SOURCE_FILE_PATH) as buildings_file:    
    buildings = json.load(buildings_file)

new_buildings = []
for building in buildings:
    name = building['name']
    latitude = building['latitude']
    longitude = building['longitude']
    address = building['address']
    imageURL = building['imageURL']
    rooms = building.get('rooms')
    entrances = building.get('entrances')
    
    new_building = {
        'name': name,
        'latitude': latitude,
        'longitude': longitude,
        'address': address,
        'imageURL': imageURL,
    }
    
    new_buildings.append(new_building)

new_buildings = sorted(new_buildings, key=lambda b: b['name'])

with open(BUILDINGS_NEW_SOURCE_FILE_PATH, 'w') as outfile:
    json.dump(new_buildings, outfile, sort_keys=True, indent=4, separators=(',', ': '))
