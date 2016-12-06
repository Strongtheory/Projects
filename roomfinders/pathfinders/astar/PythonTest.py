import sys
import Graph
import Vertex
import json



def main(argv):

    graph = Graph.Graph(1)
    
    #this part parses the json file
    
    #this is nodes
    with open('sources/buildings.json') as data_file:    
        buildings = json.load(data_file)
    num = 0
    for b in buildings:
        if b["name"] == "Clough Undergraduate Learning Commons": break
        num = num +1
    culc = buildings[num]
    entrances = culc["entrances"]
    nodes = culc["nodes"]
    edges = culc["edges"]
    
    for roomID in nodes:
        id = roomID
        
        lat = nodes[id]["latitude"]
        long = nodes[id]["longitude"]
        floor = nodes[id]["floor"]
        try:
            rooms = nodes[id]["rooms"]
            graph.add_vertex(id, lat, long, floor, rooms)
            for room in rooms:
                graph.add_room(room, graph.get_vertex(id))
        except:
            graph.add_vertex(id, lat, long, floor)
            
    for dict in edges:
        node1 = dict["node1"]
        node2 = dict["node2"]
        type = dict["type"]
        if graph.get_vertex(node1) == None: print node1
        if graph.get_vertex(node2) == None: print node2
        graph.add_edge(graph.get_vertex(node1), graph.get_vertex(node2), type)
            
    """
    #Imprtant: the path nodes are in order and the edges corespond to the equivalent path nodes, 
    #but the nodes withing the idividual edges are not in order so use path for nodes and the edges for the type.
    """     
    path, edges = graph.get_path("clough_floor1_north_entrance", "clough_floor5_elevator_hallway_corner") #clough_floor5_elevator_hallway_corner
    for i in range(len(path)):
        print path[i].get_id()
        if i < len(edges):
            print edges[i].get_type()
            print edges[i].get_angle()    
        
    
    
    

   
if __name__ == "__main__":
    main(sys.argv[1:])