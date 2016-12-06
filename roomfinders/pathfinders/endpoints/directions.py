import json
from flask import Response, request, abort
from pathfinders import app
from pathfinders.models import *
from pathfinders.astar import *

def enum(**enums):
    return type('Enum', (), enums)

DirectionAction = enum(TurnLeft=0, TurnRight=1, ContinueStraight=2, Upstairs=3, Downstairs=4, Arrived=5)
    
@app.route('/directions', methods=['GET'])
def get_directions():
    building = request.args.get('building') # building id, clough is 25
    entrance = request.args.get('entrance') # entrance node id
    room = request.args.get('room') # room node id
    
    if not building or not entrance or not room:
        abort(400)
    
    db_building = Building.query.filter_by(id=building).first()
    if not db_building:
        print 'Unknown building'
        abort(412)
    
    db_room = Room.query.filter_by(node=room).first()
    if not db_room:
        print 'Unknown room'
        abort(412)
    
    db_entrance = Entrance.query.filter_by(node=entrance).first()
    if not db_entrance:
        print 'Unknown entrance'
        abort(412)
    
    response = {
        'buildingEntrance': db_entrance.to_dict(),
        'destinationBuilding': db_building.to_dict(),
        'destinationRoom': db_room.to_dict()
    }
    
    with open('sources/buildings.json') as data_file:    
        buildings = json.load(data_file)
    
    building = None
    for b in buildings:
        if b["name"] == db_building.name:
            building = b
            break
    if not building:
        print 'Could not find building'
        abort(412)
    
    entrances = building["entrances"]
    nodes = building["nodes"]
    edges = building["edges"]
    
    graph = Graph.Graph(1)
    
    for roomID in nodes:
        lat = nodes[roomID]["latitude"]
        lon = nodes[roomID]["longitude"]
        floor = nodes[roomID]["floor"]
        try:
            rooms = nodes[roomID]["rooms"]
            graph.add_vertex(roomID, lat, lon, floor, rooms)
            for room in rooms:
                graph.add_room(room, graph.get_vertex(roomID))
        except:
            graph.add_vertex(roomID, lat, lon, floor)
    
    for edge in edges:
        node1 = edge["node1"]
        node2 = edge["node2"]
        edge_type = edge["type"]
        if graph.get_vertex(node1) == None: print node1
        if graph.get_vertex(node2) == None: print node2
        graph.add_edge(graph.get_vertex(node1), graph.get_vertex(node2), edge_type)
    
    vertices, edges = graph.get_path(entrance, room)
    
    previousStep = None
    previousEdge = None
    total_distance = 0
    steps = []
    vertex = vertices[0]
    i = 0
    while vertex != None:
        step = {}
        starting_node_dict = nodes[vertex.id]
        starting_node_dict['id'] = vertex.id
        step['startingNode'] = starting_node_dict
        
        nextVertex = vertices[i + 1] if i + 1 < len(vertices) else None
        
        distance = 0
        if nextVertex:
            ending_node_dict = nodes[nextVertex.id]
            ending_node_dict['id'] = nextVertex.id
            step['endingNode'] = ending_node_dict
            
            edge = edges[i] if i < len(edges) else None
            
            if vertex.floor < nextVertex.floor:
                step['action'] = DirectionAction.Upstairs
                distance = 4
            elif vertex.floor > nextVertex.floor:
                step['action'] = DirectionAction.Downstairs
                distance = 4
            else:
                if room == nextVertex.id:
                    # Last vertex
                    step['action'] = DirectionAction.Arrived
                else:
                    if not previousStep or not previousEdge:
                        step['action'] = DirectionAction.ContinueStraight
                    else:
                        angle = edge.get_angle()
                        previous_angle = previousEdge.get_angle()
                        
                        degree_difference = ((angle - previous_angle) + 180) % 360 - 180
                        if (abs(degree_difference) < 40):
                            step['action'] = DirectionAction.ContinueStraight
                        elif degree_difference < 0:
                            step['action'] = DirectionAction.TurnLeft
                        else:
                            step['action'] = DirectionAction.TurnRight
                    
                distance = edge.get_distance()
            
            step['distance'] = distance
            total_distance += distance
            
            steps.append(step)
        
            previousEdge = edge
            previousStep = step
        
        i += 1
        vertex = vertices[i] if i < len(vertices) else None
    
    response['totalDistance'] = total_distance
    response['estimatedTravelTime'] = max((total_distance / 5000) * 60, 1) # Travelling (in minutes) at 5kmh
    response['steps'] = steps
    
    return Response(json.dumps(response), mimetype='application/json')
