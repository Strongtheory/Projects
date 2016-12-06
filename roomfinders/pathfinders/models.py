import os
import json
from flask_sqlalchemy import SQLAlchemy
from pathfinders import app
from flask_sqlalchemy import SQLAlchemy

app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
app.config['SQLALCHEMY_DATABASE_URI'] = os.environ.get('DATABASE_URL')
db = SQLAlchemy(app)

class Building(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(200), unique=True)
    latitude = db.Column(db.Float)
    longitude = db.Column(db.Float)
    address = db.Column(db.String(250))
    rooms = db.relationship('Room', backref='building')
    entrances = db.relationship('Entrance', backref='building')
    imageURL = db.Column(db.Text)

    def __init__(self, name, address):
        self.name = name
        self.address = address

    def __repr__(self):
        return '<Building: %s, %s>' % (self.name, self.address)

    def to_dict(self):
        building = {
            'id': self.id,
            'name': self.name,
            'latitude': self.latitude,
            'longitude': self.longitude,
            'address': self.address,
            'imageURL': self.imageURL,
        }
        
        if self.entrances:
            entrances = []
            for entrance in self.entrances:
                entrances.append(entrance.to_dict())
            building['entrances'] = entrances
        
        return building
    
    @staticmethod
    def dict_to_building(building):
        db_building = Building(building['name'], building['address'])
        db_building.latitude = building['latitude']
        db_building.longitude = building['longitude']
        db_building.imageURL = building['imageURL']
        
        entrances = building.get('entrances')
        for entrance in entrances or []:
            db_entrance = Entrance(entrance['name'], entrance['node'])
            db_building.entrances.append(db_entrance)
        
        nodes = building.get('nodes', {})
        for node, node_dict in nodes.iteritems():
            # node_id = node
            # latitude = node_dict['latitude']
            # longitude = node_dict['longitude']
            # floor = node_dict['floor']
            rooms = node_dict.get('rooms')
            for room in rooms or []:
                db_room = Room(room, node)
                db_building.rooms.append(db_room)
        
        return db_building

class Room(db.Model):
    room_number = db.Column(db.String(20), primary_key=True)
    node = db.Column(db.String(70))
    building_id = db.Column(db.Integer, db.ForeignKey('building.id'))

    def __init__(self, room_number, node):
        self.room_number = room_number
        self.node = node
    
    def __repr__(self):
        return '<Room Number: %s (%s), building_id: %s>' % (self.room_number, self.node, self.building_id)
    
    def to_dict(self):
        return {
            'roomNumber': self.room_number,
            'node': self.node
        }

class Entrance(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(70))
    node = db.Column(db.String(70))
    building_id = db.Column(db.Integer, db.ForeignKey('building.id'))

    def __init__(self, name, node):
        self.name = name
        self.node = node

    def __repr__(self):
        return '<Entrance: %s>' % (self.name)

    def to_dict(self):
        return {
            'name': self.name,
            'node': self.node
        }
