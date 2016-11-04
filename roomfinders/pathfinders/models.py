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
    imageURL = db.Column(db.Text)

    def __init__(self, name, address):
        self.name = name
        self.address = address

    def __repr__(self):
        return '<Building: %s, %s>' % (self.name, self.address)

    def to_dict(self):
        return {
            'id': self.id,
            'name': self.name,
            'latitude': self.latitude,
            'longitude': self.longitude,
            'address': self.address,
            'imageURL': self.imageURL
        }
    
    @staticmethod
    def dict_to_building(building):
        db_building = Building(building['name'], building['address'])
        db_building.latitude = building['latitude']
        db_building.longitude = building['longitude']
        db_building.imageURL = building['imageURL']
        
        rooms = building.get('rooms')
        for room in rooms or []:
            db_room = Room(room)
            db_building.rooms.append(db_room)
        
        return db_building

class Room(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    room_number = db.Column(db.String(20))
    building_id = db.Column(db.Integer, db.ForeignKey('building.id'))

    def __init__(self, room_number):
        self.room_number = room_number

    def __repr__(self):
        return '<Room Number: %s>' % (self.room_number)

    def to_dict(self):
        return {
            'id': self.id,
            'roomNumber': self.room_number,
            'buildingId': self.building_id
        }
