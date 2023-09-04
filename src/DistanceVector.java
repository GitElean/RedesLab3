package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DistanceVector {
    List<List<String>> table_info;
    public DistanceVector() {
        this.table_info = new ArrayList<>();
    }

    public void initialStep(String node_name, List<List<String>> topology) {
        this.table_info.add(Arrays.asList(node_name, "0", node_name));
        // DESTINATION NODE, DISTANCE, HOP (NODE)
        for(List<String> relation : topology) {
            String l_node = relation.get(0);
            String r_node = relation.get(2);
            List<String> stored_nodes = getTableIndex(0);
            // CHECK IF RELATION IS VALID)
            if(l_node.equals(r_node)) continue;
            // ADD ELEMENT
            if(l_node.equals(node_name) || r_node.equals(node_name)) {
                // RELATION HAS (node_name)
                String node = l_node.equals(node_name) ? r_node : l_node;
                if(stored_nodes.contains(node)) {
                    // REMOVE OLD ENTRY
                    List<String> old_entry = getElement(node);
                    this.table_info.remove(old_entry);
                }
                // ADD NEW ENTRY
                this.table_info.add(Arrays.asList(node, relation.get(1), node));
            } else {
                // NODES ARE NOT (node_name)
                // 1ST NODE
                if(!stored_nodes.contains(l_node)) this.table_info.add(Arrays.asList(l_node, "-1", "-"));
                // 2ND NODE
                if(!stored_nodes.contains(r_node)) this.table_info.add(Arrays.asList(r_node, "-1", "-"));
            }
        }
    }

    public void udpateTable(Message msg) {
        // CHECK IF NODE NAME IS VALID
        List<String> entry = getElement(msg.from);
        if(entry == null) {
            System.out.println(msg.from + " is not recognized");
            return;
        }
    }

    public List<String> getElement(String name) {
        // OBTAIN ENTRY IN RESULT TABLE (DESTINATION, DISTANCE, HOP)
        for(List<String> temp : this.table_info)
            if(temp.get(0).equals(name)) return temp;
        return null;
    }

    public List<String> getTableIndex(int index) {
        // OBTAIN ALL (index) ELEMENTS IN (source) LIST
        List<String> result = new ArrayList<>();
        for(List<String> temp : this.table_info)
            result.add(temp.get(index));
        return result;
    }

    public void showCurrentTable() {
        // DISPLAY CURRENT TABLE INFORMATION
        System.out.println("======= DISTANCE VECTOR =======");
        for(List<String> temp : this.table_info) {
            System.out.println("Node: "+temp.get(0)+", Distance: "+temp.get(1)+", Hop: "+temp.get(2));
        }
        System.out.println("");
    }
}
