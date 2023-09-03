package src;

import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // GET TOPOLOGY
        ArrayList<ArrayList<String>> topology = getTopology();
        // GET NETWORK NODE NAMES
        ArrayList<String> node_names = getNodeNames(topology);
        // STORE NODE NAME
        String user_name = "";
        System.out.print("Enter node name: ");
        user_name = scanner.nextLine();
        // VERIFY THAT NODE IS DEFINED IN TOPOLOGY
        if(!node_names.contains(user_name)){
            System.out.println("Node name is invalid");
            System.exit(1);
        }
        // MAIN LOOP
        int user_option;
        while(true) {
            displayMenu();
            user_option = getIntInput(1, 4, 0);
            if(user_option == 0) {
                System.out.println("Enter a valid option");
                continue;
            }
            if(user_option == 4) {
                System.out.println("Exiting program ...");
                break;
            }
        }
        System.exit(0);
    }

    public static void displayMenu() {
        System.out.print("=== CLIENT MENU ===\n" +
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

    public static ArrayList<String> getNodeNames(ArrayList<ArrayList<String>> topology) {
        // GET each list[0] & list[2] in topology and return set result
        Set<String> hash_set = new HashSet<String>();
        for(ArrayList<String> temp : topology) {
            hash_set.add(temp.get(0));
            hash_set.add(temp.get(2));
        }
        return new ArrayList<String>(hash_set);
    }

    public static int getIntInput(int min, int max, int def) {
        try {
            System.out.print("> Enter option: ");
            int result = Integer.parseInt(scanner.nextLine());
            if(result < min || result > max) return def;
            return result;
        } catch(NumberFormatException e) {
            return def;
        }
    }
}
