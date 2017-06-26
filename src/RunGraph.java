
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


public class RunGraph {
     public static void main(String[] args) {
       RunGraph ex = new RunGraph();
       ex.run();
         
    }


     public void initialize(){
        graph = new RoutingGraph<Integer>();
        for (int j = 0; j < 16; j++) {
            if (j < 8) {
                graph.AddNode(j);
                graph.getGraph().get(j).setState(1);
            } else if (j >= 8 && j < 12) {
                graph.AddNode(j);               
            } else {
                graph.AddNode(j);
                graph.getGraph().get(j).setState(2);
            }
        }
        add_edge();
        for(int i = 0; i < graph.getGraph().size(); i++){
            System.out.println("cost " + graph.getGraph().get(i).getCost());
        }
        find_shortest_path();
     }
    /**
     * increase the size of graph from 5 to 1000
     * add node to the graph,add edge, and find shortest path 
     */

    //method to run the whole program
    public void run() {
        graph = new RoutingGraph<Integer>();
        for (int j = 0; j < 16; j++) {
            if (j < 8) {
                graph.AddNode(j);   
                graph.getGraph().get(j).setState(1);
            } else if (j >= 8 && j < 12) {
                graph.AddNode(j);
            } else {
                graph.AddNode(j);
                graph.getGraph().get(j).setState(2);
            }
        }
        add_edge();
        for(int i = 0; i < graph.getGraph().size(); i++){
            System.out.println("cost " + graph.getGraph().get(i).getCost());
        }
        find_shortest_path();
        while(!testCong(graph)){
            System.out.println("===============================");
            runIteration();
        }
    }

    /**
     * the method to add edge between two random city, the weight is randomized between 1 and 10
     * 
     */
    //the method to add edge (the node and the weight) between two random city in the graph
    public void add_edge(){
        //generate edges and add them into the graph
        graph.AddEdge(0, 2, 0.1);
        graph.AddEdge(0, 4, 0.5);
        graph.AddEdge(4, 3, 0.3);
        graph.AddEdge(3, 4, 2.0);
        graph.AddEdge(5, 7, 0.5);
        graph.AddEdge(1, 7, 0.5);
        graph.AddEdge(5, 4, 0.5);
        graph.AddEdge(2, 6, 0.1);
        graph.AddEdge(6, 2, 0.1);
        graph.AddEdge(3, 0, 0.2);
        graph.AddEdge(3, 7, 0.2);
        graph.AddEdge(7, 0,0.5);
       graph.AddEdge(8,0,graph.getGraph().get(0).getCost());  
       graph.AddEdge(8,1,graph.getGraph().get(1).getCost());  
       graph.AddEdge(9,2,graph.getGraph().get(2).getCost());  
       graph.AddEdge(9,3,graph.getGraph().get(3).getCost());  
       graph.AddEdge(10,4,graph.getGraph().get(4).getCost());  
       graph.AddEdge(10,5,graph.getGraph().get(5).getCost());  
       graph.AddEdge(11,6,graph.getGraph().get(6).getCost());  
       graph.AddEdge(11,7,graph.getGraph().get(7).getCost());   
       graph.AddEdge(0,12,graph.getGraph().get(0).getCost());   
       graph.AddEdge(1,12,graph.getGraph().get(1).getCost());   
       graph.AddEdge(2,13,graph.getGraph().get(2).getCost());
       graph.AddEdge(3,13,graph.getGraph().get(3).getCost());  
       graph.AddEdge(4,14,graph.getGraph().get(4).getCost());  
       graph.AddEdge(5,14,graph.getGraph().get(5).getCost());  
       graph.AddEdge(6,15,graph.getGraph().get(6).getCost());  
       graph.AddEdge(7,15,graph.getGraph().get(7).getCost()); 
       graph.AddEdge(0,13,graph.getGraph().get(0).getCost());   
       graph.AddEdge(1,14,graph.getGraph().get(1).getCost());   
       graph.AddEdge(2,15,graph.getGraph().get(2).getCost());
       graph.AddEdge(3,12,graph.getGraph().get(3).getCost());  
       graph.AddEdge(7,13,graph.getGraph().get(7).getCost());
       graph.AddEdge(0,15,graph.getGraph().get(0).getCost());    
       graph.AddEdge(3,15,graph.getGraph().get(3).getCost());  
       graph.AddEdge(7,12,graph.getGraph().get(7).getCost());  
    }
    /**
     * the method to decide whether discard the graph
     * if not all nodes can be reached, the method return false
     * 
     * @return     true or false
     */
    //the method to decide whether discard the graph
    public boolean discard_graph(){
        for(int i = 0; i < graph.getGraph().size(); i++){
            if(graph.getGraph().get(i).getEdge().isEmpty()){
                return false;
            }
        }
        return true;
    }

    /**
     * if all nodes can be reached, find the shortest path between two random city 
     * print the path out and the execution time out
     * 
     */
    //find the shortest path between two random city print the path out and the execution time out
    public void find_shortest_path() {
        ArrayList<RoutingGraph<Integer>.Node<Integer>> sources = new ArrayList<RoutingGraph<Integer>.Node<Integer>>();
        for(int i = 0; i < graph.getGraph().size(); i++){
            if(graph.getGraph().get(i).getState() == 0){
                sources.add(graph.getGraph().get(i));
            }
        }
         ArrayList<RoutingGraph<Integer>.Node<Integer>> sinks = new ArrayList<RoutingGraph<Integer>.Node<Integer>>();
        for(int i = 0; i < graph.getGraph().size(); i++){
            if(graph.getGraph().get(i).getState() == 2){
                sinks.add(graph.getGraph().get(i));
            }
        }
         ArrayList<RoutingGraph<Integer>.Node<Integer>> wires = graph.getWire();
            for(int k = 0; k < wires.size(); k++){
                wires.get(k).resetOther();
            }
        for (int i = 0; i < sources.size(); i++) {
                    System.out.println("path from" +sources.get(i).getKey() + " to" + sinks.get(i).getKey() );
                    ArrayList<RoutingGraph<Integer>.Node<Integer>> path_find = graph.findShortestPath(sources.get(i).getKey(),sinks.get(i).getKey());
                    for (int m = 0; m < path_find.size(); m++) {
                        System.out.println("path" + path_find.get(m).getKey());
                        if(path_find.get(m).getState()==1){
                            path_find.get(m).changeOther();
                        }
                    }
                    sources.get(i).paths.add(path_find);
        }
    }
            
    public void runIteration(){
        for(int i = 0; i < graph.getGraph().size(); i++){
            if(graph.getGraph().get(i).getState() == 1 && graph.getGraph().get(i).getOther() > 2){
                graph.getGraph().get(i).changeHistory();
                graph.getGraph().get(i).changeCost();
                System.out.println("show other: " + graph.getGraph().get(i).getOther() + "show cost: " + graph.getGraph().get(i).getCost() + "node" + graph.getGraph().get(i).getKey());
            }
        }
        graph.changeEdges();
        find_shortest_path();
    }
    
    public boolean testCong(RoutingGraph<Integer> graph) {
        ArrayList<RoutingGraph<Integer>.Node<Integer>> wires = graph.getWire();
        for (int i = 0; i < wires.size(); i++) {
            if (wires.get(i).getOther() > 2) return false;
        }
        return true;
    }
    
    public ArrayList<RoutingGraph<Integer>.Node<Integer>> drawGraph(){
        ArrayList<RoutingGraph<Integer>.Node<Integer>> list = graph.getGraph();
        return list;
    }
    
    public RoutingGraph<Integer> getGraph(){
        return graph;
    }
    
    private RoutingGraph<Integer> graph;
}

