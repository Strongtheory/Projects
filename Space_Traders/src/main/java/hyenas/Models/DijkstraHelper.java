package hyenas.Models;

import hyenas.HyenasLoader;
import hyenas.database.PlanetTable;
import hyenas.database.SolarSystemTable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Helper methods for Dijkstra's algorithm.
 * @author Alex
 */
public class DijkstraHelper {
    /**
     * The tint color for the lines.
     */
    private static final String LINE_TINT_COLOR = "cyan";
    
    /**
     * Get the linear distance between two systems.
     * @param system1 starting system
     * @param system2 ending system
     * @return distance between the two systems
     */
    public static double getDistance(SolarSystem system1, SolarSystem system2) {
        int x1 = system1.getX();
        int y1 = system1.getY();
        int x2 = system2.getX();
        int y2 = system2.getY();
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }
    
    /**
     * Get the distance between two systems according to Dijkstra's algorithm.
     * @param start starting system
     * @param goal ending system
     * @return distance between the two systems on the graph
     */
    public static double getDijkstraDistance(SolarSystem start, SolarSystem goal) {
        Queue<ABPair<SolarSystem, Double>> distances = new PriorityQueue<>(
                (ABPair<SolarSystem, Double> o1, ABPair<SolarSystem, Double> o2)
                        -> (int) (o1.getB() - o2.getB()));
        Map<SolarSystem, List<ABPair<SolarSystem, Double>>> adjList = Galaxy
                .getInstance().getDistances();

        if (!adjList.containsKey(start)) {
            return -1;
        }
        distances.add(new ABPair<>(start, 0.0));

        Set<SolarSystem> visited = new HashSet<>();

        while (!distances.isEmpty()) {
            ABPair<SolarSystem, Double> node = distances.remove();
            if (node.getA().equals(goal)) {
                return node.getB();
            }

            if (!visited.contains(node.getA())) {
                visited.add(node.getA());
                List<ABPair<SolarSystem, Double>> neighbors = adjList.get(node.getA());
                if (neighbors != null) {
                    adjList.get(node.getA()).parallelStream().forEach((neighbor) -> {
                            double alternateDistance = node.getB() + neighbor.getB();
                            distances.add(new ABPair<>(neighbor.getA(), alternateDistance));
                        });
                }
            }
        }

        return -1;
    }
    
    /**
     * Calculates the Dijkstra distances.
     * @param solarSystemValues the solar system values
     * @param distances the solar system distances
     */
    public static void calculateDijkstraDistances(List<SolarSystem> solarSystemValues,
            Map<SolarSystem, List<ABPair<SolarSystem, Double>>> distances) {
        Player player = Player.getInstance();
        Random random = new Random();
        for (int i = 0; i < solarSystemValues.size(); i++) {
            SolarSystem solarSystem1 = solarSystemValues.get(i);
            for (int j = i; j < solarSystemValues.size(); j++) {
                SolarSystem solarSystem2 = solarSystemValues.get(j);

                double distance = DijkstraHelper.getDistance(solarSystem1, solarSystem2);
                if (solarSystem1 != player.getCurrentSystem()
                        && solarSystem2 != player.getCurrentSystem()) {
                    if (distance >= 400) {
                        continue;
                    }

                    if (random.nextDouble() >= 0.35) {
                        continue;
                    }
                } else {
                    if (distance >= 250) {
                        continue;
                    }
                }

                double weight = distance + (solarSystem1.getPlanets().size()
                        - solarSystem2.getPlanets().size()) * 10;
                ABPair<SolarSystem, Double> destination = new ABPair<>(solarSystem2,
                        weight);
                if (!distances.containsKey(solarSystem1)) {
                    distances.put(solarSystem1, new LinkedList<>());
                }
                distances.get(solarSystem1).add(destination);

                weight = distance + (solarSystem2.getPlanets().size()
                        - solarSystem1.getPlanets().size()) * 10;
                destination = new ABPair<>(solarSystem1, weight);
                if (!distances.containsKey(solarSystem2)) {
                    distances.put(solarSystem2, new LinkedList<>());
                }
                distances.get(solarSystem2).add(destination);
            }
        }
    }
    
    /**
     * Saves the solar systems to the database.
     * @param solarSystemValues the solar system values
     */
    public static void saveSolarSystems(List<SolarSystem> solarSystemValues) {
        SolarSystemTable ssTable = HyenasLoader.getInstance()
                .getConnectionManager().getSolarSystemTable();
        Player player = Player.getInstance();
        if (!Galaxy.getInstance().isLocationsSet()) {
            HyenasLoader.getInstance().getConnectionManager()
                    .beginTransaction();

            PlanetTable planetTable = HyenasLoader.getInstance()
                    .getConnectionManager().getPlanetTable();

            for (SolarSystem ss : solarSystemValues) {
                ssTable.addRow(ss, null);
                ss.getPlanets().stream().forEach((planet) -> {
                        planetTable.addRow(planet, ss);
                    });
            }

            HyenasLoader.getInstance().getConnectionManager().getPlayerTable()
                    .update(player, null);

            HyenasLoader.getInstance().getConnectionManager()
                    .commitTransaction();
            Galaxy.getInstance().setLocationsSet(true);
        }
    }
    
    /**
     * Adds the Dijsktra lines between solar systems.
     * @param solarSystemValues the solar system values.
     * @param distances the solar system distances
     * @param pane the pane to add the lines to
     */
    public static void addDijkstraLines(List<SolarSystem> solarSystemValues,
            Map<SolarSystem, List<ABPair<SolarSystem, Double>>> distances, Pane pane) {
        for (SolarSystem solarSystem1 : solarSystemValues) {
            List<ABPair<SolarSystem, Double>> connections = distances.get(solarSystem1);

            if (connections == null) {
                continue;
            }

            for (int j = 0; j < connections.size(); j++) {
                SolarSystem solarSystem2 = distances.get(solarSystem1).get(j).getA();
                createLine(solarSystem1, solarSystem2, pane);
            }
        }
    }
    
    /**
     * Creates a line between two solar systems.
     * @param solarSystem1 the first solar system
     * @param solarSystem2 the second solar system
     * @param pane the pane to add the lines to
     */
    private static void createLine(SolarSystem solarSystem1,
            SolarSystem solarSystem2, Pane pane) {
        double system1Size = solarSystem1.getSize();
        double system2Size = solarSystem2.getSize();
        Line connection = new Line(solarSystem1.getX() + system1Size,
                solarSystem1.getY() + system1Size, solarSystem2.getX() + system2Size,
                solarSystem2.getY() + system2Size);
        connection.setStroke(Color.web(LINE_TINT_COLOR, 1));
        pane.getChildren().add(0, connection);
    }
}
