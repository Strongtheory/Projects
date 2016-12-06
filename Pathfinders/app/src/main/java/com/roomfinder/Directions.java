package com.roomfinder;

/**
 * Class which represents a single set of Directions to any given room
 * Connor Reeder
 */

public class Directions {
    private Entrance buildingEntrance;
    private Building destBuilding;
    private Room destRoom;
    private double estTravelTime;
    private double totalDistance;
    private Step[] steps;



    public Directions(Entrance buildingEntrance, Building destBuilding, Room destRoom, double estTravelTime, double totalDistance, Step[] steps) {
        this.buildingEntrance = buildingEntrance;
        this.destBuilding = destBuilding;
        this.destRoom = destRoom;
        this.estTravelTime = estTravelTime;
        this.totalDistance = totalDistance;
        this.steps = steps;
    }

    public Entrance getBuildingEntrance() {
        return buildingEntrance;
    }

    public void setBuildingEntrance(Entrance buildingEntrance) {
        this.buildingEntrance = buildingEntrance;
    }

    public Building getDestBuilding() {
        return destBuilding;
    }

    public void setDestBuilding(Building destBuilding) {
        this.destBuilding = destBuilding;
    }

    public Room getDestRoom() {
        return destRoom;
    }

    public void setDestRoom(Room destRoom) {
        this.destRoom = destRoom;
    }

    public double getEstTravelTime() {
        return estTravelTime;
    }

    public void setEstTravelTime(double estTravelTime) {
        this.estTravelTime = estTravelTime;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public void setSteps(Step[] steps) {
        this.steps = steps;
    }

    public String[] getInstructions() {
        String[] instructions = new String[steps.length];
        for (int i = 0; i < steps.length; i++) {
            String command = "";
            int dist = (int) (steps[i].getDistance() / 3.28084);
            if (steps[i].getAction() == NavigationInstruction.Arrived) {
                instructions[i] = "You have arrived!";
            }else {
                if (steps[i].getAction() == NavigationInstruction.TurnLeft)
                    command = "Turn Left in ";
                else if (steps[i].getAction() == NavigationInstruction.TurnRight)
                    command = "Turn Right in";
                else if (steps[i].getAction() == NavigationInstruction.ContinueStraight)
                    command = "Continue Straight for";
                else if (steps[i].getAction()== NavigationInstruction.Upstairs)
                    command = "Go upstairs in";
                else if (steps[i].getAction() == NavigationInstruction.Downstairs)
                    command = "Go Downstairs in";
                instructions[i] = command + " " + dist + " feet";
            }
        }
        return instructions;
    }
}
