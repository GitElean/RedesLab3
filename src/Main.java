package src;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // GET TOPOLOGY
        ArrayList<ArrayList<String>> topology = getTopology();
        String user_name = "";

    }

    public static void displayMenu() {
        System.out.println("=== CLIENT MENU ===\n" +
                "1. Enter \n");
    }

    public static ArrayList<ArrayList<String>> getTopology() {
        // DEFINE NETWORK TOPOLOGY (NON DIRECTIONAL)
        ArrayList<ArrayList<String>> topology = new ArrayList<>();
        // (list[0] -> list[1] -> list[2]) | (list[2] -> list[1] -> list[0])
        topology.add(new ArrayList<>(Arrays.asList("A", "1", "B")));
        topology.add(new ArrayList<>(Arrays.asList("A", "3", "C")));
        topology.add(new ArrayList<>(Arrays.asList("B", "2", "C")));
        return topology;
    }
}
