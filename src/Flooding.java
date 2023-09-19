package src;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Flooding {
    private Map<String, Boolean> visited;
    private List<List<String>> topology;

    public Flooding(List<List<String>> topology) {
        this.visited = new HashMap<>();
        this.topology = topology;
    }

    public void floodMessage(String startNode, String message) {
        algorithmFlooding(startNode, message);
    }

    private void algorithmFlooding(String node, String message) {
        if (visited.containsKey(node) && visited.get(node)) {
            return;
        }

        System.out.println("Node " + node + " received message: " + message);
        visited.put(node, true);

        for (List<String> relation : topology) {
            if (relation.get(0).equals(node)) {
                algorithmFlooding(relation.get(2), message);
            } else if (relation.get(2).equals(node)) {
                algorithmFlooding(relation.get(0), message);
            }
        }
    }

    public void showVisitedNodes() {
        System.out.println("\n---------------------------------");
        for (Map.Entry<String, Boolean> visNode : visited.entrySet()) {
            System.out.println("Node: " + visNode.getKey());
            System.out.println("Visited: " + visNode.getValue());
        }
        System.out.println("\n---------------------------------");
    }
}
