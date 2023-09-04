package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DistanceVector {
    public DistanceVector() {

    }

    public List<List<String>> initialStep(String node_name, List<List<String>> topology) {
        List<List<String>> result = new ArrayList<>();
        result.add(Arrays.asList(node_name, "0", node_name));
        // DESTINATION NODE, DISTANCE, HOP (NODE)
        for(List<String> relation : topology) {
            String l_node = relation.get(0);
            String r_node = relation.get(2);

        }
        return result;
    }

    public List<String> getListElement(int index, List<List<String>> source) {
        // OBTAIN ALL (index) ELEMENTS IN (source) LIST
        List<String> result = new ArrayList<>();
        for(List<String> temp : source) {
            result.add(temp.get(index));
        }
        return result;
    }
}
