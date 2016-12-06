import sys
import math

# A single Node Object for the graph.
class Vertex:
    # Construct a node with the following properties.
    # id
    # latitude
    # longitude
    # floor
    # array of connections
    # @param id, latitude, longitude, floor
    def __init__(self, node, latitude, longitude, floor, rooms = []):
        self.id = node
        self.latitude = latitude
        self.longitude = longitude
        self.floor = floor
        self.rooms = rooms

    def get_id(self):
        return self.id

    # returns a list of lat and long	
    def get_location(self):
        return [self.latitude, self.longitude]

    def get_floor(self):
        return self.floor

    # def __str__(self):
        # return str(self.id) + ' adjacent: ' + str([x.id for x in self.adjacent])
