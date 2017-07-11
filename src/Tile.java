/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;
import java.text.DecimalFormat;
/**
 *
 * @author yangy
 */
public class Tile<K> {
    private int source_num;
    private int sink_num;
    private int wire_num;
    private int start_num;
    ArrayList<Node<K>> sources;
    ArrayList<Node<K>> sinks;
    ArrayList<Node<K>> wires;
    public Tile(int num1, int num2, int num3, int start){
        sources = new ArrayList<Node<K>>();
        sinks = new ArrayList<Node<K>>();
        wires = new ArrayList<Node<K>>();
        source_num = num1;
        sink_num = num2;
        wire_num = num3;
        start_num = start;
    }
    
    public void generate() {
        for (int i = 0; i < source_num; i++) {
            Node<K> src = new Node<K>(start_num);
            src.setState(0);
            sources.add(src);
            start_num++;
        }
        for (int i = 0; i < sink_num; i++) {
            Node<K> si = new Node<K>(start_num);
            si.setState(2);
            sinks.add(si);
            start_num++;
        }
        for (int i = 0; i < 2*wire_num; i++) {
            Node<K> w = new Node<K>(start_num);
            w.setState(1);
            if (i < wire_num ) {
                w.dir = 0;
                for (int j = 0; j < sources.size(); j++) {
                    Edge edge1 = new Edge(w, w.getCost());
                    sources.get(j).addEdge(edge1);
                }
            } else {
                w.dir = 1;
                for (int j = 0; j < sinks.size(); j++) {
                    Edge edge1 = new Edge(sinks.get(j), w.getCost());
                    w.addEdge(edge1);
                }
            }
            wires.add(w);
            start_num++;
        }
    }
    
    public boolean add_edge(Node<K> node1,Node<K> node2,double weight){
        for(int i = 0; i < node1.getEdge().size(); i++){
            if(node1.getEdge().get(i).getNode().getKey()== node2.getKey()){
                return false;
            }
        }
        node1.addEdge(new Edge(node2, weight));
        return true;
    }
    
    public Node<K> findNode(int key) {
        for (int i = 0; i < sources.size(); i++) {
            if (sources.get(i).getKey() == key) {
                return sources.get(i);
            }
        }
        for (int i = 0; i < sinks.size(); i++) {
            if (sinks.get(i).getKey() == key) {
                return sinks.get(i);
            }
        }
        for (int i = 0; i < wires.size(); i++) {
            if (wires.get(i).getKey() == key) {
                return wires.get(i);
            }
        }
        return null;
    }
    
    public void computePath(Node<K> pin){
        pin.min_distance = 0;
        PriorityQueue<Node<K>> node_queue = new PriorityQueue<Node<K>>();
        node_queue.add(pin);
        while (!node_queue.isEmpty()){
            Node<K> vertex = node_queue.poll();
            for(int i = 0; i < vertex.getEdge().size(); i++){
                Edge<K> edge = vertex.getEdge().get(i);
                Node<K> neighbor = edge.getNode();
                double total_distance = edge.getWeight() + vertex.min_distance;
                if(total_distance < neighbor.min_distance){
                    node_queue.remove(neighbor);
                    neighbor.min_distance = total_distance;
                    neighbor.prev = vertex;
                    node_queue.add(neighbor);
                }
            }
        }
    }
    public ArrayList<Node<K>> findShortestPath(int k1, int k2, ArrayList<Node<K>> nodes) {
        ArrayList<Node<K>> path = new ArrayList<Node<K>>();
        Node<K> start;
        Node<K> end;
        for (int i = 0; i < nodes.size(); i++) {
            if (k1==(nodes.get(i).getKey())) {
                start = nodes.get(i);
                for (int k = 0; k < nodes.size(); k++) {
                    nodes.get(k).prev = null;
                    nodes.get(k).min_distance = Integer.MAX_VALUE;
                }
                computePath(start);
                for (int j = 0; j < nodes.size(); j++) {
                    if (k2==(nodes.get(j).getKey())) {
                        end = nodes.get(j);
                        start.find_distance = end.min_distance;
                        if (start.find_distance != Integer.MAX_VALUE) {
                            DecimalFormat numformat = new DecimalFormat("#.00");
                            System.out.println("final distance" + numformat.format(end.min_distance) + " !");
                        } else {
                            System.out.println("no path found");
                        }
                        for (Node<K> visit = end; visit != null; visit = visit.prev) {
                            path.add(visit);
                        }
                        Collections.reverse(path);
                    }
                }
            }
        }
        return path;
    }
    
    public void changeEdges() {
        for (int i = 0; i < wires.size(); i++) {
            for (int j = 0; j < wires.get(i).getEdge().size(); j++) {
                wires.get(i).changeWeight(j);
            }
        }
                for (int i = 0; i < sources.size(); i++) {
            for (int j = 0; j < sources.get(i).getEdge().size(); j++) {
                sources.get(i).changeWeight(j);
            }
        }
    }
    
    
    public int getSourceNum(){
        return source_num;
    }
    
    public int getSinkNum(){
        return sink_num;
    }
        
    public int getWireNum(){
        return wire_num;
    }
    
    public int getStartNum(){
        return start_num;
    }
    
    public ArrayList<Node<K>> getSources(){
        return sources;
    }
    
    public ArrayList<Node<K>> getSinks(){
        return sinks;
    }
        
    public ArrayList<Node<K>> getWires(){
        return wires;
    }
    
     public class Node<K> implements Comparable<Node<K>>{
    Node<K> prev;
    int state = 0;
    double cost = 0;
    double history = 0;
    int other = 1;
    int base = 0;
    double min_distance = Integer.MAX_VALUE;
    double find_distance = 0;
    int pos_x_s = 0;
    int pos_y_s = 0;
    int pos_x_e = 0;
    int pos_y_e = 0;
    int dir = 0;
    int key;
    LinkedList<Edge<K>> edge;
    LinkedList<Node<K>> dest;
    LinkedList<Node<K>> neighbour;
    LinkedList<ArrayList<Node<Integer>>> paths;
    LinkedList<Double> distance;
    public Node(int k){
        edge = new LinkedList<Edge<K>> ();
        this.key = k;
        this.paths = new LinkedList<ArrayList<Node<Integer>>>();
        this.distance = new LinkedList<Double>();
        this.neighbour = new LinkedList<Node<K>>();
        this.dest = new LinkedList<Node<K>>();
    }
    public int getKey(){
        return key;
    }
    public int getState(){
        return state;
    }
    public int getOther(){
        return other;
    }
    public double getHistory(){
        return history;
    }
    public double getCost(){
        return cost;
    }
    public void setState(int num){
        state = num;
        if(state == 1){
            Random random = new Random();
            base = random.nextInt(5) + 1;
            changeCost();
        }
        else{
            cost = 0;
        }
    }
    public void changeCost(){
        cost = base + history + other;
    }
    public double changeHistory(){
        if (history==0) history = 1;
        history = 1.1*history;
        return history;
    }
    public int changeOther(){
        other++;
        return other;
    }
    
    public int resetOther(){
        other = 1;
        return other;
    }
         public void changeWeight(int num) {
             if (state == 0) {
                 edge.get(num).setWeight(edge.get(num).getNode().getCost());
             } else if (state == 1) {
                 if (edge.get(num).getNode().getState() == 2) {
                     edge.get(num).setWeight(cost);
                 }
             }
         }

         public LinkedList<Edge<K>> getEdge() {
             return edge;
         }
    public void addEdge(Edge e){
        edge.add(e);
    }
    public void addNeighbour(Node<K> nei){
        neighbour.add(nei);
    }
    public int compareTo(Node<K> other){
        return Double.compare(min_distance, other.min_distance);
    }
  }
    
    public class Edge<K> {
        Node<K> node;
        Double value;

        public Edge(Node<K> node, double weight) {
            this.node = node;
            this.value = weight;
        }

        public Node<K> getNode() {
            return node;
        }

        public double getWeight() {
            return value;
        }
        
        public double setWeight(double num) {
            value = num;
            return value;
        }
    }
}
