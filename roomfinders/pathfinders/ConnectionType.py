import Node
from enum import Enum, unique

@unique
class ConnectionType(Enum):
    Hallway = 'Hallway'
    Stairs = 'Stairs'
    Elevator = 'Elevator'
