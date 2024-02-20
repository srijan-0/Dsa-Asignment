package Que5;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

class NetworkGraph {
    ArrayList<Integer> affectedDevicesList = new ArrayList<Integer>(); 
    List<List<Integer>> networkGraph;
    boolean visited[];
    int numberOfDevices;
    int targetDevice;

    NetworkGraph(int[][] connections, int target) {

        this.targetDevice = target;

        networkGraph = new ArrayList<>();

        this.numberOfDevices = connections.length;

        visited = new boolean[this.numberOfDevices];

        for (int i = 0; i < numberOfDevices; i++) {
            networkGraph.add(i, new ArrayList<>());
        }

        for (int i = 0; i < connections.length; i++) {
            int[] connection = new int[2];
            for (int j = 0; j < connections[i].length; j++) {

                connection[j] = connections[i][j];
            }

            System.out.println("connection between " + Arrays.toString(connection));
            if (connection[0] != target && connection[1] != target) {
                addEdge(connection[0], connection[1]);
            }
        }
    }

    public void addEdge(int a, int b) {
        networkGraph.get(a).add(b);
        networkGraph.get(b).add(a);
    }

    public ArrayList<Integer> findAffectedDevices() {
        int startIndex = 0;
        if (startIndex == targetDevice) {
            startIndex = 1;
        }
        depthFirstSearch(startIndex);

        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {

                if (i != targetDevice) {
                    affectedDevicesList.add(i);
                }

            }
        }

        return affectedDevicesList;
    }

    public void depthFirstSearch(int start) {
        Stack<Integer> stack = new Stack<>();

        stack.push(start);
        visited[start] = true;

        while (!stack.isEmpty()) {

            Integer node = stack.pop();

            List<Integer> neighboursList = networkGraph.get(node);

            for (Integer neighbour : neighboursList) {
                if (!visited[neighbour]) {
                    stack.push(neighbour);
                    visited[neighbour] = true;
                }
            }
        }
    }
}

public class AffectedDevicesFinder {

    public static void main(String[] args) {
        int[][] connections = { { 0, 1 }, { 0, 2 }, { 1, 3 }, { 1, 6 }, { 2, 4 }, { 4, 6 }, { 4, 5 }, { 5, 7 } };

        int target = 4;

        NetworkGraph myNetwork = new NetworkGraph(connections, target);

        System.out.println("Affected devices: " + (myNetwork.findAffectedDevices()));

    }

}
