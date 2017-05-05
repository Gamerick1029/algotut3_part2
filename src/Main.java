import java.awt.*;
import java.util.ArrayList;

/**
 * Created by jacob on 05/05/2017.
 */
public class Main {

    public static void main(String[] args){
        Graph rndg = Graph.randomGraph(20, 0.5);
        Prims pr = new Prims(rndg);
        System.out.println("randomised");
        pr.graphIt();

        System.out.println("Graphed");

        ArrayList<Graph.Edge> edges = pr.getEdges();
        ArrayList<Graph.Position> vertices = pr.getVertices();

        GraphDisplay gd = new GraphDisplay();

        for (int i = 1, verticesSize = vertices.size(); i < verticesSize; i++) {
            Graph.Position position = vertices.get(i);
            gd.addNode(position, position.x, position.y);
        }

        for (Graph.Edge edge: edges){
            gd.addEdge(edge.end1, edge.end2, Color.BLACK);
        }

        GraphDisplay gd2 = new GraphDisplay();

        for (Graph.Position position: rndg.getVertices()){
            gd2.addNode(position, position.x, position.y);
        }

        for (Graph.Edge edge : rndg.edges()){
            gd2.addEdge(edge.end1, edge.end2, Color.BLACK);
        }


        gd.showInWindow(500, 500, "prims");
        gd2.showInWindow(500, 500, "original");
    }

}
