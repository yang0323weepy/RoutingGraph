/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author yangy
 */

import java.util.*;
import java.text.DecimalFormat;

public class RoutingGraph<K> {
    ArrayList<Node<K>> nodes;
    HashMap<Node<K>,ArrayList<Node<K>>> map;
    
    public RoutingGraph(){
        nodes = new ArrayList<Node<K>>(); 
        map = new HashMap<Node<K>,ArrayList<Node<K>>>(); 
    }
    
    public boolean AddNode(K k){
        nodes.add(new Node<K>(k));
        return true;
    }
    
    public boolean AddEdge(K k1, K k2, Double value) {
        for (int i = 0; i < nodes.size(); i++) {
            if (k1.equals(nodes.get(i).getKey())) {
                for (int j = 0; j < nodes.get(i).getEdge().size(); j++) {
                    if (k2.equals(nodes.get(i).getEdge().get(j).getNode().getKey())) {
                        return false;
                    }
                }
                for (int j = 0; j < nodes.size(); j++) {
                    if (k2.equals(nodes.get(j).getKey())) {
                        Edge<K> edge1 = new Edge<K>(nodes.get(j), value);
                        nodes.get(i).addEdge(edge1);
                        return true;
                    }
                }
            }
        }
        return false;
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
    public ArrayList<Node<K>> findShortestPath(K k1, K k2) {
        ArrayList<Node<K>> path = new ArrayList<Node<K>>();
        Node<K> start;
        Node<K> end;
        for (int i = 0; i < nodes.size(); i++) {
            if (k1.equals(nodes.get(i).getKey())) {
                start = nodes.get(i);
                for (int k = 0; k < nodes.size(); k++) {
                    nodes.get(k).prev = null;
                    nodes.get(k).min_distance = Integer.MAX_VALUE;
                }
                computePath(start);
                for (int j = 0; j < nodes.size(); j++) {
                    if (k2.equals(nodes.get(j).getKey())) {
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

    public ArrayList<Node<K>> getGraph(){
        return nodes;
    }
    
    public void changeEdges(){
        for(int i = 0; i < nodes.size(); i++){
            for(int j = 0; j < nodes.get(i).getEdge().size(); j++){
                nodes.get(i).changeWeight(j);
            }
    }    
    }
    
    public ArrayList<Node<K>> getWire(){
        ArrayList<Node<K>> wires = new ArrayList<Node<K>>();
        for(int i = 0; i < nodes.size(); i++){
        if(nodes.get(i).getState()==1){
            wires.add(nodes.get(i));
        }
        }
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
    K key;
    LinkedList<Edge<K>> edge;
    LinkedList<ArrayList<Node<Integer>>> paths;
    public Node(K k){
        edge = new LinkedList<Edge<K>> ();
        this.key = k;
    }
    public K getKey(){
        return key;
    }
    public int getState(){
        return state;
    }
    public int getOther(){
        return other;
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
            paths = new LinkedList<ArrayList<Node<Integer>>>();
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
                if (edge.get(num).getNode().getState()==2) {
                    edge.get(num).setWeight(cost);
                }
            }
    }
    public LinkedList<Edge<K>> getEdge(){
        return edge;
    }
    public void addEdge(Edge e){
        edge.add(e);
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
