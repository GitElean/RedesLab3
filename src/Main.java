package src;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // GET TOPOLOGY
        List<List<String>> topology = getTopology();
        // GET NETWORK NODE NAMES
        List<String> node_names = getNodeNames(topology);
        // STORE NODE NAME
        String node_name;
        System.out.print("Enter node name: ");
        node_name = scanner.nextLine();
        // VERIFY THAT NODE IS DEFINED IN TOPOLOGY
        if(!node_names.contains(node_name)){
            System.out.println("Node name is invalid");
            System.exit(1);
        }
        // === NODE VALUES ===
        DistanceVector dv = new DistanceVector();
        dv.initialStep(node_name, topology);
        // === MAIN LOOP ===
        int user_option;
        while(true) {
            displayMenu();
            // GET USER OPTION
            user_option = getIntInput(1, 3, 0);
            if(user_option == 0) {
                // INVALID ANSWER
                System.out.println("Enter a valid option");
                continue;
            } else if(user_option == 1) {
                // OBTAIN JSON INFORMATION
                Message json_msg = parseJSON();
                if(json_msg != null) {
                    if(json_msg.getType().equals("info")) {
                        // UPDATE DISTANCE VECTOR TABLE
                        dv.udpateTable(json_msg);
                    }
                }
            } else if(user_option == 2) {
                // DISTANCE VECTOR INFORMATION
                dv.showCurrentTable();
            } else {
                // EXIT PROGRAM
                System.out.println("Exiting program ...");
                break;
            }
        }
        System.exit(0);
    }

    public static void displayMenu() {
        System.out.print("""
                ======= CLIENT MENU =======
                1. Parse JSON file (Message.json)
                2. Show node information
                3. Exit program
                """);
    }

    public static List<List<String>> getTopology() {
        // DEFINE NETWORK TOPOLOGY (NON DIRECTIONAL)
        List<List<String>> topology = new ArrayList<>();
        // (list[0] -> list[1] -> list[2]) | (list[2] -> list[1] -> list[0])
        topology.add(new ArrayList<>(Arrays.asList("A", "1", "B")));
        topology.add(new ArrayList<>(Arrays.asList("A", "3", "C")));
        topology.add(new ArrayList<>(Arrays.asList("B", "2", "C")));
        return topology;
    }

    public static List<String> getNodeNames(List<List<String>> topology) {
        // GET each list[0] & list[2] in topology and return set result
        Set<String> hash_set = new HashSet<>();
        for(List<String> temp : topology) {
            hash_set.add(temp.get(0));
            hash_set.add(temp.get(2));
        }
        return new ArrayList<>(hash_set);
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

    public static Message parseJSON() {
        // READ .JSON FILE AND
        String json_file = "src/Message.json";
        StringBuilder json = new StringBuilder();
        json.append("[");
        try {
            BufferedReader br = new BufferedReader(new FileReader(json_file));
            String line;
            while((line = br.readLine()) != null) {
                // APPEND INFORMATION
                json.append(line);
            }
            json.append("]");
            // CREATE NEW JSON ARRAY OBJECT
            JSONArray jsonArray = new JSONArray(json.toString());
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            // STORE INFORMATION (MESSAGE CLASS)
            Message result = new Message();
            result.setType(jsonObject.getString("type"));
            result.setFrom(jsonObject.getJSONObject("headers").getString("from"));
            result.setTo(jsonObject.getJSONObject("headers").getString("to"));
            result.setHop_count(jsonObject.getJSONObject("headers").getInt("hop_count"));
            if(jsonObject.getString("type").equalsIgnoreCase("message")) {
                result.setMessage(jsonObject.getString("payload"));
            } else {
                // OBTAIN TABLE NEW INFORMATION
                List<Object> temp = jsonObject.getJSONObject("payload").getJSONArray("distance_vector").toList();
                List<List<String>> table_info = new ArrayList<>();
                for(Object obj : temp) {
                    // OBTAIN VALUES
                    String vvv = obj.toString().substring(1, obj.toString().length()-1);
                    List<String> entries = new ArrayList<>(Arrays.stream(vvv.split(",")).toList());
                    entries.replaceAll(String::strip);
                    table_info.add(entries);
                }
                result.setTable_info(table_info);
            }
            // RETURN INFORMATION
            return result;
        } catch (IOException e) {
            // ERROR OCURRED DURING .JSON FILE READING
            System.out.println("An error ocurred while reading .json file");
            return null;
        }
    }
}
