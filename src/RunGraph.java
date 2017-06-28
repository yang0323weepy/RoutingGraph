
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
         
    }
     public void initialize(){
        graph = new RoutingGraph<Integer>();
                System.out.println(graph);
        for (int j = 0; j < 16; j++) {
            if (j < 4) {
                graph.AddNode(j);
                
            } else if (j >= 4 && j < 12) {
                graph.AddNode(j);
                graph.getGraph().get(j).setState(1);
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
     * the method to add edge between two random city, the weight is randomized between 1 and 10
     * 
     */
    //the method to add edge (the node and the weight) between two random city in the graph
    public void add_edge(){
        //generate edges and add them into the graph
        graph.AddEdge(4, 6, 0.1);
        graph.AddEdge(4, 8, 0.5);
        graph.AddEdge(8, 7, 0.3);
        graph.AddEdge(7, 8, 2.0);
        graph.AddEdge(9, 11, 0.5);
        graph.AddEdge(5, 11, 0.5);
        graph.AddEdge(9, 8, 0.5);
        graph.AddEdge(6, 10, 0.1);
        graph.AddEdge(10, 6, 0.1);
        graph.AddEdge(7, 4, 0.2);
        graph.AddEdge(7, 11, 0.2);
        graph.AddEdge(11, 4,0.5);
       graph.AddEdge(0,4,graph.getGraph().get(4).getCost());  
       graph.AddEdge(0,5,graph.getGraph().get(5).getCost());  
       graph.AddEdge(1,6,graph.getGraph().get(6).getCost());  
       graph.AddEdge(1,7,graph.getGraph().get(7).getCost());  
       graph.AddEdge(2,8,graph.getGraph().get(8).getCost());  
       graph.AddEdge(2,9,graph.getGraph().get(9).getCost());  
       graph.AddEdge(3,10,graph.getGraph().get(10).getCost());  
       graph.AddEdge(3,11,graph.getGraph().get(11).getCost());   
       graph.AddEdge(4,12,graph.getGraph().get(4).getCost());   
       graph.AddEdge(5,12,graph.getGraph().get(5).getCost());   
       graph.AddEdge(6,13,graph.getGraph().get(6).getCost());
       graph.AddEdge(7,13,graph.getGraph().get(7).getCost());  
       graph.AddEdge(8,14,graph.getGraph().get(8).getCost());  
       graph.AddEdge(9,14,graph.getGraph().get(9).getCost());  
       graph.AddEdge(10,15,graph.getGraph().get(10).getCost());  
       graph.AddEdge(11,15,graph.getGraph().get(11).getCost()); 
       graph.AddEdge(4,13,graph.getGraph().get(4).getCost());   
       graph.AddEdge(5,14,graph.getGraph().get(5).getCost());   
       graph.AddEdge(6,15,graph.getGraph().get(6).getCost());
       graph.AddEdge(7,12,graph.getGraph().get(7).getCost());  
       graph.AddEdge(11,13,graph.getGraph().get(11).getCost());
       graph.AddEdge(4,15,graph.getGraph().get(4).getCost());    
       graph.AddEdge(7,15,graph.getGraph().get(7).getCost());  
       graph.AddEdge(11,12,graph.getGraph().get(11).getCost());  
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
                    sources.get(i).paths.clear();
                    sources.get(i).distance.clear();
                    System.out.println("path from" +sources.get(i).getKey() + " to" + sinks.get(i).getKey() );
                    ArrayList<RoutingGraph<Integer>.Node<Integer>> path_find = graph.findShortestPath(sources.get(i).getKey(),sinks.get(i).getKey());
                    for (int m = 0; m < path_find.size(); m++) {
                        System.out.println("path" + path_find.get(m).getKey());
                        if(path_find.get(m).getState()==1){
                            path_find.get(m).changeOther();
                        }
                    }
                    sources.get(i).paths.add(path_find);
                    sources.get(i).distance.add(sinks.get(i).min_distance);
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

