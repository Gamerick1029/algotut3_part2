import java.util.*;

/**
 * Created by jacob on 04/05/2017.
 */
public class Graph {

    public HashMap<Integer, Position> intToPosition = new HashMap<>();
    public HashMap<Position, Integer> positionToInt = new HashMap<>();
    private ArrayList<Edge> edges = new ArrayList<>();

    /**
     * Simply stores 2 integer values
      */
    public class Position{
        public int x;
        public int y;

        public Position(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Stores 2 position objects and the weight between them
     */
    public class Edge implements Comparable<Edge>{
        public Position end1;
        public Position end2;
        public double weight;

        public Edge(Position position1, Position position2, double weight){
            end1 = position1;
            end2 = position2;
            this.weight = weight;
        }

        /**
         * If the supplied position exists in this edge object, returns true. Otherwise returns false.
         * Performs a shallow search. Will only compare the x and y primitives of the Position objects as opposed to
         * the specific object instances
         * @param position The position to search for
         * @return If the position exists in this edge
         */
        public Boolean positionExists(Position position){
            if ((end1.x == position.x) && (end1.y == position.y)){
                return true;
            } else if ((end2.x == position.x) && (end2.y == position.y)){
                return true;
            }
            return false;
        }

        /**
         * Returns the position object that is not equal to the supplied object iff the supplied object exists within
         * this edge object
         * @param position
         * @return
         */
        public Position neighbour(Position position){
            if (!positionExists(position)) return null;

            if ((end1.x == position.x) && (end1.y == position.y)){
                return end2;
            } else return end1;
        }

        @Override
        public int compareTo(Edge e) {
            if (this.weight - e.weight < 0){
                return -1;
            } else if (this.weight - e.weight > 0){
                return 1;
            } else return 0;
        }
    }

    public void addVertex(int x, int y) {
        int vertex = intToPosition.size() + 1;
        Position newPosition = new Position(x, y);
        intToPosition.put(vertex, newPosition);
        positionToInt.put(newPosition, vertex);
    }

    /**
     * Adds an edge to the 2 vertices, calculating weight as the absolute distance between the locations of both points.
     * @param vertex1
     * @param vertex2
     */
    public Boolean addEdge(int vertex1, int vertex2){
        if (edgeExists(vertex1, vertex2)) return false;
        Position position1 = position(vertex1);
        Position position2 = position(vertex2);
        int deltaX2, deltaY2;
        deltaX2 = (Math.abs(position1.x - position2.x))^2;
        deltaY2 = (Math.abs(position1.y - position2.y))^2;
        double distance = Math.sqrt(deltaX2 + deltaY2);
        edges.add(new Edge(position1, position2, distance));
        return true;
    }

    /**
     * Adds an edge between the 2 specified vertices with the given weight
     * @param vertex1
     * @param vertex2
     * @param weight
     */
    public boolean addEdge(int vertex1, int vertex2, double weight){
        if (edgeExists(vertex1, vertex2)) return false;
        Position position1 = position(vertex1);
        Position position2 = position(vertex2);
        edges.add(new Edge(position1, position2, weight));
        return true;
    }

    public ArrayList<Edge> edges(){
     return (ArrayList<Edge>)edges.clone();
    }

    /**
     * Returns the position object associated with the supplied integer
     * @param vertex The position objects "ID"
     * @return The position associated with said "ID"
     */
    public Position position(int vertex){
        return intToPosition.get(vertex);
    }

    public ArrayList<Position> positions(){
        ArrayList<Position> positions = new ArrayList<>();

        for (int i = 1; i <= intToPosition.size(); i++) {
            positions.add(position(i));
        }

        return positions;
    }

    public int size(){
        return intToPosition.size();
    }

    /**
     * Checks to see if there exists an edge between 2 specified vertices
     * @param vertex1 First vertex
     * @param vertex2 Second vertex
     * @return Whether or not there exists an edge
     */
    public Boolean edgeExists(int vertex1, int vertex2){

        //Check if vertices exist
        if ((position(vertex1) == null) || (position(vertex2) == null)) return false;

        for (Edge edge:edges) {

            //A single Edge object must contain both vertices for the edge to exist
            if (edge.positionExists(position(vertex1)) && edge.positionExists(position(vertex2))){
                return true;
            }
        }
        return false;
    }

    public Edge getEdge(int vertex1, int vertex2){
        //Check if vertices exist
        if ((position(vertex1) == null) || (position(vertex2) == null)) return null;

        for (Edge edge:edges) {

            //A single Edge object must contain both vertices for the edge to exist
            if (edge.positionExists(position(vertex1)) && edge.positionExists(position(vertex2))){
                return edge;
            }
        }
        return null;
    }

    public ArrayList<Position> getVertices(){
        ArrayList<Position> positions = new ArrayList<>();

        for (Position poisition: intToPosition.values())
            positions.add(poisition);
        return positions;
    }

    /**
     * Gets the weight of the edge between two vertices, as long as such an edge exists
     * @param vertex1
     * @param vertex2
     * @return
     */
    public double weight(int vertex1, int vertex2){
        if (!edgeExists(vertex1, vertex2)) return -1;
        return getEdge(vertex1, vertex2).weight;
    }

    /**
     * Returns a list of all Neighbours associated with the specified vertex
     * @param vertex The vertex to check
     * @return The list of neighbours. Can be an empty List if no neighbours are found
     */
    public List<Integer> neighbours(int vertex){
        List<Integer> neighbours = new ArrayList<>();

        Position currentNode = position(vertex);
        for (Edge edge:edges){
            if (edge.positionExists(currentNode)){
                neighbours.add(positionToInt.get(edge.neighbour(currentNode)));
            }
        }

        return neighbours;
    }

    /**
     * Creates a randomly filled graph, with the given number of vertices, and places edges between them with the
     * given probability
     * @param numOfVertices
     * @param probabilityOfEdge
     * @return
     */
    public static Graph randomGraph(int numOfVertices, double probabilityOfEdge){
        Graph graph = new Graph();
        Random rnd = new Random();
        for (int i = 1; i < numOfVertices; i++) {
            graph.addVertex(rnd.nextInt(100), rnd.nextInt(100));
        }

        HashMap<Integer, Position> orphanPositions = (HashMap<Integer, Position>) graph.intToPosition.clone();

        //Repeat loop until every vertex has at least one edge
        while(!orphanPositions.isEmpty()){
            for (int i = 1; i <= graph.intToPosition.size(); i++) {
                for (int j = i+1; j <= graph.intToPosition.size() ; j++) {
                    if (Math.random() < probabilityOfEdge) {
                        graph.addEdge(i, j);
                        orphanPositions.remove(i);
                        orphanPositions.remove(j);
                    }
                }
            }
        }

        return graph;
    }
}
