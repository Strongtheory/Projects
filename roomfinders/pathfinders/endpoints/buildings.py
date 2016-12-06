import json
from flask import Response, request, abort
from pathfinders import app
from pathfinders.models import *

@app.route('/buildings', methods=['GET'])
@app.route('/buildings/<building_id>', methods=['GET'])
def get_buildings(building_id=None):
    if building_id:
        b = Building.query.filter_by(id=building_id).first()
        return Response(json.dumps(b.to_dict()), mimetype='application/json')
    
    query = request.args.get('q')
    if query:
        buildings_results = Building.query.filter(Building.name.ilike('%' + query + '%')).all()
        buildings = [b.to_dict() for b in buildings_results]
        return Response(json.dumps(buildings), mimetype='application/json')
    
    location = request.args.get('location')
    if location:
        components = location.split(',')
        if not len(components) == 2:
            print "Invalid location format"
            abort(400)
        
        lat = components[0]
        lon = components[1]
        radius = request.args.get('radius', 9999999999)
        limit = request.args.get('limit', 10)
        
        try:
            lat = float(lat)
            lon = float(lon)
            radius = float(int(radius)) / 1000.0 # Convert m to km
            limit = int(limit)
        except ValueError, e:
            print "Invalid parameter types"
            abort(400)
        
        results = db.engine.execute("""SELECT *
            FROM (
                SELECT id, (6371 * acos(cos(radians({0})) * cos(radians(latitude)) * cos(radians(longitude) - radians({1})) + sin(radians({0})) * sin(radians(latitude)))) AS distance
                FROM Building
            ) d
            WHERE distance < {3}
            ORDER BY distance
            LIMIT {2}
            """.format(lat, lon, limit, radius))
        
        identifiers = []
        for row in results:
            (identifier, distance) = row
            identifiers.append(identifier)
        
        # Retreive all the buildings
        db_buildings = Building.query.filter(Building.id.in_(identifiers)).all()
        
        # Since query won't preserve identifiers ordering, sort buildings by identifiers array
        db_buildings = [next(b for b in db_buildings if b.id == identifier) for identifier in identifiers]
        
        # Convert places to dictionary objects
        buildings = [b.to_dict() for b in db_buildings]
        
        return Response(json.dumps(buildings), mimetype='application/json')
    
    buildings = [b.to_dict() for b in Building.query.all()]
    return Response(json.dumps(buildings), mimetype='application/json')

@app.route('/buildings/<building_id>/rooms', methods=['GET'])
def get_building_rooms(building_id=None):
    db_building = Building.query.filter_by(id=building_id).first()
    if not db_building:
        abort(404)
    
    rooms = [r.to_dict() for r in db_building.rooms]
    
    sorted_rooms = sorted(rooms, key=lambda e: e['roomNumber'])
    
    return Response(json.dumps(sorted_rooms), mimetype='application/json')
