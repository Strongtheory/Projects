import Vertex
import math
from math import cos, sin, atan2, radians, degrees, sqrt

class Edge:
    #type
    #vertex1
    #vertex2
    # @param type, vertex1, vertex2
    def __init__(self, type, vertex1, vertex2):
        self.type = type
        self.vertex1 = vertex1
        self.vertex2 = vertex2

    def get_type(self):
        return self.type

    #returns a list of vertices
    def get_vertices(self):
        vertices = [self.vertex1, self.vertex2]
        return vertices
    
    def get_distance(self):
        """
        Calculates the distance in meters between the 2 vertices
        """
        # approximate radius of earth in km
        R = 6373.0

        
        lat1 = radians(self.vertex1.latitude)
        lon1 = radians(self.vertex1.longitude)
        lat2 = radians(self.vertex2.latitude)
        lon2 = radians(self.vertex2.longitude)

        dlon = lon2 - lon1
        dlat = lat2 - lat1

        a = sin(dlat / 2)**2 + cos(lat1) * cos(lat2) * sin(dlon / 2)**2
        c = 2 * atan2(sqrt(a), sqrt(1 - a))

        distance = R * c * 1000
        
        return distance
    
    def get_angle(self):
        vertex2 = Vertex.Vertex(None, radians(self.vertex2.latitude), radians(self.vertex2.longitude), 1)
        vertex1 = Vertex.Vertex(None, radians(self.vertex1.latitude), radians(self.vertex1.longitude), 1)
                
        deltaL = abs(vertex1.longitude - vertex2.longitude)
        
        x = cos(vertex1.latitude) * sin(deltaL)
        y = cos(vertex2.latitude) * (sin(vertex1.latitude) - sin(vertex2.latitude)) * cos(vertex1.latitude) * cos(deltaL)
        
        angle = atan2(x, y)
        return degrees(angle)
