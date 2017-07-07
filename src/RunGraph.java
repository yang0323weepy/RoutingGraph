
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
    public RunGraph(int graph, int wire){
        size_g = graph;
        size_w = wire;
    }
     public void initialize(){
        graph = new RoutingGraph<Integer>();
                System.out.println(graph);
        for (int j = 0; j < size_g*size_g*2+2*(size_g*size_g*size_w); j++) {
            if (j < size_g*size_g) {
                graph.AddNode(j);                
            } else if (j >= size_g*size_g && j < size_g*size_g+2*(size_g*size_g*size_w)) {
                graph.AddNode(j);
                graph.getGraph().get(j).setState(1);
            } else {
                graph.AddNode(j);
                graph.getGraph().get(j).setState(2);
            }
        }
        add_edge();
        find_shortest_path();
     }
  
    /**
     * the method to add edge between two random city, the weight is randomized between 1 and 10
     * 
     */
    //the method to add edge (the node and the weight) between two random city in the graph
    public void add_edge(){
        ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>> ();
        int init = size_g*size_g;
        for(int i = 0; i <size_g*size_g;i++){
            ArrayList<Integer> num_list = new ArrayList<Integer> ();
            for(int j = 0; j < size_w; j++){
            num_list.add(init+j);
            num_list.add(init+size_w+j);
            num_list.add(init+size_g*size_g*size_w+j);
            num_list.add(init+size_g*size_g*size_w+size_g*size_w+j);
        }
            init = init+size_w;
            list.add(num_list);
        }
        for(int i = 0; i < list.size();i++){
            for(int j = 0; j < list.get(i).size();j++){
                System.out.print("list"+ list.get(i).get(j)+" ");
            }
            System.out.println();
        }
        //generate edges and add them into the graph
        for(int i = 0; i < size_g*size_g; i++){
          for(int k = 0; k < size_w*size_w*size_g; k++){
            Random random = new Random();
            int city1 = random.nextInt(list.get(i).size()) + 0;
            int city2 = random.nextInt(list.get(i).size()) + 0;
            double weight = random.nextDouble()+2;
            if(city1 != city2 && list.get(i).get(city1) < size_g*size_g+2*(size_g*size_g*size_w) && list.get(i).get(city2) < size_g*size_g+2*(size_g*size_g*size_w)){
                graph.AddEdge(list.get(i).get(city1),list.get(i).get(city2),weight);
            } 
              System.out.println("edge test1 " + list.get(i).get(city1)+" "+list.get(i).get(city2)+" " + weight);             
        }
    }
    int num = size_g*size_g;
     for(int i = 0; i < size_g*size_g; i++){
       graph.AddEdge(i,num,graph.getGraph().get(num).getCost()); 
       num++;
       graph.AddEdge(i,num,graph.getGraph().get(num).getCost()); 
       num++;
        }
     
     int num_s = size_g*size_g+size_g*size_g*size_w;
      for(int i = size_g*size_g+2*(size_g*size_g*size_w); i < (size_g*size_g*2+2*size_g*size_g*size_w); i++){       
       graph.AddEdge(num_s,i,graph.getGraph().get(num_s).getCost());  
       System.out.println("edge test2 " + num_s + " " + i);
       num_s++;
       graph.AddEdge(num_s,i,graph.getGraph().get(num_s).getCost()); 
              System.out.println("edge test2 " + num_s + " " + i);
       num_s++;
        }
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
    public int getGraphSize(){
        return size_g;
    }
    
    public int getWireSize(){
        return size_w;
    }
    
    private RoutingGraph<Integer> graph;
    private int size_g;
    private int size_w;
}

