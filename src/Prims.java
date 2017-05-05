import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.TreeSet;

/**
 * Created by jacob on 04/05/2017.
 */
public class Prims {

    private Graph graph;
    private TreeSet<Integer> addedNodes = new TreeSet<>();
    private TreeSet<Graph.Edge> addedEdges = new TreeSet<>();



    public Prims(Graph graph){
        this.graph = graph;
    }

    public ArrayList<Graph.Edge> getEdges(){
        ArrayList<Graph.Edge> edges = new ArrayList<>();

        for(Graph.Edge edge: addedEdges){
            edges.add(edge);
        }

        return edges;
    }

    public ArrayList<Graph.Position> getVertices(){
        ArrayList<Graph.Position> vertices = new ArrayList<>();

        for(int i = 0; i <= graph.size(); i++){
            vertices.add(graph.intToPosition.get(i));
        }

        return vertices;
    }

    public Boolean graphIt(){
        PriorityQueue<Graph.Edge> edgesToCheck = new PriorityQueue<>();
        if (graph.size() == 0) return false;

        for (int j = 1; j <= graph.size(); j++) {

            addedNodes.add(j);
            System.out.println(graph.neighbours(j));
            for (int k:graph.neighbours(j)) {
                if ((!addedNodes.contains(k)) && (!edgesToCheck.contains(graph.getEdge(j,k)))) edgesToCheck.add(graph.getEdge(j, k));
            }
            Graph.Edge newEdge = edgesToCheck.poll();
            if (newEdge != null) {
                addedEdges.add(newEdge);
            }
        }
        return true;
    }

}
