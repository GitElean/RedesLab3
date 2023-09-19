package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DijkstraDataTransmission {
    List<List<String>> table_info;
    String currentNode;

    public DijkstraDataTransmission(String currentNode) {
        this.table_info = new ArrayList<>();
        this.currentNode = currentNode;
    }

    public void initialSetup(List<List<String>> topology) {
        this.table_info.add(Arrays.asList(currentNode, "0", currentNode)); // El nodo base siempre tiene distancia 0 a sí mismo.

        for (List<String> relation : topology) {
            String l_node = relation.get(0);
            String r_node = relation.get(2);

            if (l_node.equals(currentNode)) {
                this.table_info.add(Arrays.asList(r_node, relation.get(1), l_node));
            } else if (r_node.equals(currentNode)) {
                this.table_info.add(Arrays.asList(l_node, relation.get(1), r_node));
            }
            // Los nodos que no son adyacentes al nodo base inicialmente tendrán distancia infinita.
            else if (!containsNode(l_node)) {
                this.table_info.add(Arrays.asList(l_node, String.valueOf(Integer.MAX_VALUE), "-"));
            } else if (!containsNode(r_node)) {
                this.table_info.add(Arrays.asList(r_node, String.valueOf(Integer.MAX_VALUE), "-"));
            }
        }
    }

    private boolean containsNode(String nodeName) {
        for (List<String> entry : table_info) {
            if (entry.get(0).equals(nodeName)) {
                return true;
            }
        }
        return false;
    }

    public void updateTable(Message msg) {
        for (List<String> temp : msg.getTable_info()) {
            int newDist = Integer.parseInt(temp.get(1));
            int index = getIndex(temp.get(0));

            if (index != -1) {
                List<String> currentEntry = table_info.get(index);
                if (newDist < Integer.parseInt(currentEntry.get(1))) {
                    this.table_info.set(index, Arrays.asList(temp.get(0), String.valueOf(newDist), msg.getFrom()));
                }
            } else {
                this.table_info.add(Arrays.asList(temp.get(0), String.valueOf(newDist), msg.getFrom()));
            }
        }
    }

    private int getIndex(String nodeName) {
        for (int i = 0; i < table_info.size(); i++) {
            if (table_info.get(i).get(0).equals(nodeName)) {
                return i;
            }
        }
        return -1; // No encontrado
    }

    public void showCurrentTable() {
        System.out.println("======= DIJKSTRA DATA TRANSMISSION =======");
        for (List<String> temp : this.table_info) {
            System.out.println("Node: " + temp.get(0) + ", Distance: " + temp.get(1) + ", Hop: " + temp.get(2));
        }
        System.out.println("");
    }
}

