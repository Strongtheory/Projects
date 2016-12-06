import json
from flask import request, Response
from pathfinders import app
from pathfinders.models import *

@app.route('/updateBuildings', methods=['GET'])
def update_buildings():
    print 'Updating buildings...'
    # db.engine.execute("DROP TABLE IF EXISTS room, building, entrance CASCADE")
    db.create_all()
    
    with open('sources/buildings.json') as buildings_file:    
        buildings = json.load(buildings_file)
    
    for building in buildings:
        db_building = Building.dict_to_building(building)
        db.session.add(db_building)
    
    db.session.commit()
    
    return 'Successfully updated buildings.'

@app.route('/reset', methods=['GET'])
def reset():
    print 'Resetting...'
    db.drop_all()
    db.create_all()
    
    update_buildings()
    return 'Reset complete.'
