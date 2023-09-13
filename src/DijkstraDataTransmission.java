package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DijkstraDataTransmission {
    List<List<String>> table_info;

    public DijkstraDataTransmission() {
        this.table_info = new ArrayList<>();
    }

    public void initialSetup(String nodeName, int[][] topology) {
        int V = topology.length;

        // Inicializar la tabla con la distancia máxima y sin salto (hop) para cada nodo
        for (int i = 0; i < V; i++) {
            if (i == Integer.parseInt(nodeName)) {
                this.table_info.add(Arrays.asList(String.valueOf(i), "0", nodeName));
            } else {
                this.table_info.add(Arrays.asList(String.valueOf(i), String.valueOf(Integer.MAX_VALUE), "-"));
            }
        }

        for (int i = 0; i < V; i++) {
            if (topology[Integer.parseInt(nodeName)][i] != 0) {
                this.table_info.set(i, Arrays.asList(String.valueOf(i), String.valueOf(topology[Integer.parseInt(nodeName)][i]), String.valueOf(i)));
            }
        }
    }

    public void updateTable(Message msg) {
        for (List<String> temp : msg.getTable_info()) {
            int newDist = Integer.parseInt(temp.get(1));
            List<String> currentEntry = getElement(temp.get(0));

            if (newDist < Integer.parseInt(currentEntry.get(1))) {
                this.table_info.set(Integer.parseInt(temp.get(0)), Arrays.asList(temp.get(0), String.valueOf(newDist), msg.getFrom()));
            }
        }
    }

    public List<String> getElement(String name) {
        return this.table_info.get(Integer.parseInt(name));
    }

    public void showCurrentTable() {
        System.out.println("======= DIJKSTRA DATA TRANSMISSION =======");
        for (List<String> temp : this.table_info) {
            System.out.println("Node: " + temp.get(0) + ", Distance: " + temp.get(1) + ", Hop: " + temp.get(2));
        }
        System.out.println("");
    }
}
