import Vertex
import Edge
#import heapq
import math
from collections import defaultdict

class Graph:
    # connection object module
    # @param type
    def __init__(self, type):
        self.type = type
        # this is a dict of vertices. key is the id(node) of the vertex
        self.vertices = {}
        # this is dict of room strings to nodes
        self.roomDict = {}
        # this is the list of edges
        self.network = [] 
        # need to get exact number
        self.ditanceBetweenFloors = 12 

    def __iter__(self):
        return iter(self.vertices_dict.values())
    
    def add_room(self, room, node):
        self.roomDict[room] = node
        
    def get_node_from_room(self, room):
        if room in self.roomDict:
            return self.roomDict[room]
        else: return None
    
    def add_vertex(self, node, lat, long, floor):
        self.vertices[node] = Vertex.Vertex(node, lat, long, floor)
    
    #gets a vertex based on id
    def get_vertex(self, n):
        return self.vertices[n]
    
    #adds an edge
    def add_edge(self, frm, to, type):
        present = False	
        # check if the edge already exists
        for e in self.network:
            vertices = e.get_vertices()
            if (vertices[0].get_id() == frm.get_id() and vertices[1].get_id() == to.get_id()  \
                or vertices[1].get_id() == frm.get_id() and vertices[0].get_id() == to.get_id()):
                present = True
                break
        if not present:
            self.network.append(Edge.Edge(type, frm, to))

    
    def get_vertices(self):
        return self.vertices.keys()

    """
    This is the method that calls A* and returns lists of
    the path and the edges.
    
    Imprtant: the path nodes are in order and the edges corespond to the equivalent path nodes, 
    but the nodes withing the idividual edges are not in order so use path for nodes and the edges for the type.
    """
     
    def get_path(self, init, goal):
        return self.astar(self.get_vertex(init), self.get_vertex(goal), self.network)

    # this returns a path based on network(list of edges) and the list of edges that connect the nodes in the path.
    def astar(self, init, goal, network):
        path = []
        opened = []
        closed = []

        prevNode = {}
        gScore = defaultdict(lambda: float("inf"))
        gScore[init] = 0
        fScore = defaultdict(lambda: float("inf"))
        fScore[init] = self.heuristic(init, goal)
        opened.append(init)

        # make a dict of lists of neighbors for a node
        neighbors = defaultdict(list)
        edgeDict = defaultdict(list)
        for e in network:
            neighbors[e.get_vertices()[0]].append(e.get_vertices()[1])
            neighbors[e.get_vertices()[1]].append(e.get_vertices()[0])
            
            edgeDict[e.get_vertices()[0]].append(e)
            edgeDict[e.get_vertices()[1]].append(e)

        # main loop
        while len(opened) > 0:
            # get current from opened with smallest fScore
            current = opened[0]
            for n in opened:
                if fScore[current] > fScore[n]: current = n
            if current.get_id() == goal.get_id(): 
                path = self.pathReconstruct(prevNode, goal)
                return path, self.getEdgesFromPath(path, edgeDict)

            opened.remove(current)
            closed.append(current)
            for n in neighbors[current]:
                if n in closed: continue
                temp_gScore = gScore[current] + self.distance(current, n)
                if n not in opened: opened.append(n)
                elif temp_gScore >= gScore[n]: continue

                prevNode[n] = current
                gScore[n] = temp_gScore
                fScore[n] = gScore[n] + self.heuristic(n, goal)

        return [] # return empty list if no path.

    def pathReconstruct(self, prevNode, current):
        path = [current]
        while current in prevNode.keys():
            current = prevNode[current]
            path.insert(0,current)
        return path
        
    def getEdgesFromPath(self, path, edgeDict):
        edges = []
        for i in range(len(path)-1):
            v1 = path[i]
            v2 = path[i+1]
            for e in edgeDict[v1]:
                if v2.get_id() == e.get_vertices()[0].get_id() or v2.get_id() == e.get_vertices()[1].get_id():
                    edges.append(e)
                    break
        return edges

    # this computes the heuristic value between two points
    def heuristic(self, source, destination):		
        return self.distance(source, destination) + (math.fabs(source.get_floor() \
        - destination.get_floor()))*self.ditanceBetweenFloors # heuristic 

    #this is computes the distance between two nodes
    def distance(self, vertex1, vertex2):
        return math.sqrt((vertex1.get_location()[0] - vertex2.get_location()[0])**2 + \
            (vertex1.get_location()[1] - vertex2.get_location()[1])**2)


