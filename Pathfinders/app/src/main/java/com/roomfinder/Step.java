package com.roomfinder;

/**
 * Created by connor on 11/16/16.
 */

public class Step {
    private NavigationInstruction action;
    private double distance;
    private Node startingNode;
    private Node endingNode;

    public Step(NavigationInstruction action, double distance, Node startingNode, Node endingNode) {
        this.action = action;
        this.distance = distance;
        this.startingNode = startingNode;
        this.endingNode = endingNode;
    }

    public NavigationInstruction getAction() {
        return action;
    }

    public void setAction(NavigationInstruction action) {
        this.action = action;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Node getStartingNode() {
        return startingNode;
    }

    public void setStartingNode(Node startingNode) {
        this.startingNode = startingNode;
    }

    public Node getEndingNode() {
        return endingNode;
    }

    public void setEndingNode(Node endingNode) {
        this.endingNode = endingNode;
    }
}
