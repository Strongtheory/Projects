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
    
    new_building = {
        'name': name,
        'latitude': latitude,
        'longitude': longitude,
        'address': address,
        'imageURL': imageURL,
    }
    
    if not rooms:
        new_rooms = []
        num_rooms = randint(3, 50)
        for i in range(0, num_rooms):
            room_number = str(randint(100, 4000))
            new_rooms.append(room_number)
        new_rooms.sort(key=int)
        
        possible_endings = ['', '', '', '', '', '', '', '', '', '', '', 'A', 'B', 'C']
        for i in range(0, len(new_rooms)):
            new_rooms[i] = new_rooms[i] + possible_endings[randint(0, len(possible_endings) - 1)]
        
        new_building['rooms'] = new_rooms
    
    new_buildings.append(new_building)

new_buildings = sorted(new_buildings, key=lambda b: b['name'])

with open(BUILDINGS_NEW_SOURCE_FILE_PATH, 'w') as outfile:
    json.dump(new_buildings, outfile, sort_keys=True, indent=4, separators=(',', ': '))
