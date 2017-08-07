 
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author yangy
 */
public class TiledGraph {

//constructor of tiles graph
    public TiledGraph(int num1, int num2,int num3,int num4){
        size_g = num1;
        size_w = num2;
        source_num = num3;
        sink_num = num4;
        graph = new Tile[size_g][size_g];
        graphList = new ArrayList<Tile<Integer>.Node<Integer>>();
        wireList = new ArrayList<Tile<Integer>.Node<Integer>>();
        sinkList = new ArrayList<Tile<Integer>.Node<Integer>>();
        destList = new ArrayList<Tile<Integer>.Node<Integer>>();
        sourceList = new ArrayList<Tile<Integer>.Node<Integer>>();
        sinkinList = new ArrayList<Tile<Integer>.Node<Integer>>();
        sourceinList = new ArrayList<Tile<Integer>.Node<Integer>>();
    }
//initialize the tiled graph by generateing tiles and its nodes, and connect wires nodes randomly within each     
    public void initialize(){
        int index = 0;
        for(int i = 0; i < size_g; i++){
            for(int j = 0; j < size_g; j++){
                graph[i][j] = new Tile<Integer>(source_num,sink_num,size_w,index);
                graph[i][j].generate();
                index = graph[i][j].getStartNum();  
            }
        }
        connectTile();
        makeGraph();
    }
//add potential connection between wire nodes from different tiles in each switch box 
    public void connectTile() {
        for (int i = 0; i < size_g; i++) {
            for (int j = 0; j < size_g; j++) {
                if (i + 1 < size_g) {
                    for (int k = 0; k < size_w * size_w * size_w *size_w; k++) {
                        Random random = new Random();
                        int city1 = random.nextInt(graph[i][j].getWires().size()) + 0;
                        int city2 = random.nextInt(graph[i+1][j].getWires().size()) + 0;
                        int key1 = graph[i][j].getWires().get(city1).getKey();
                        int key2 = graph[i+1][j].getWires().get(city2).getKey();
                        if(graph[i+1][j].findNode(key2).dir == 1)
                        graph[i][j].add_edge(graph[i][j].findNode(key1),graph[i+1][j].findNode(key2),graph[i+1][j].findNode(key2).getCost());
                    }
                    for (int k = 0; k < size_w * size_w* size_w*size_w; k++) {
                        Random random = new Random();
                        int city1 = random.nextInt(graph[i+1][j].getWires().size()) + 0;
                        int city2 = random.nextInt(graph[i][j].getWires().size()) + 0;
                        int key1 = graph[i+1][j].getWires().get(city1).getKey();
                        int key2 = graph[i][j].getWires().get(city2).getKey();
                        if(graph[i+1][j].findNode(key1).dir == 1)
                        graph[i+1][j].add_edge(graph[i+1][j].findNode(key1),graph[i][j].findNode(key2),graph[i][j].findNode(key2).getCost());
                    }
                }
                if (j + 1 < size_g) {
                    for (int k = 0; k < size_w * size_w * size_w*size_w; k++) {
                        Random random = new Random();
                        int city1 = random.nextInt(graph[i][j].getWires().size()) + 0;
                        int city2 = random.nextInt(graph[i][j+1].getWires().size()) + 0;
                        int key1 = graph[i][j].getWires().get(city1).getKey();
                        int key2 = graph[i][j+1].getWires().get(city2).getKey();
                        if(graph[i][j+1].findNode(key2).dir == 0)
                        graph[i][j].add_edge(graph[i][j].findNode(key1),graph[i][j+1].findNode(key2),graph[i][j+1].findNode(key2).getCost());
                    }
                    for (int k = 0; k < size_w * size_w * size_w*size_w; k++) {
                        Random random = new Random();
                        int city1 = random.nextInt(graph[i][j+1].getWires().size()) + 0;
                        int city2 = random.nextInt(graph[i][j].getWires().size()) + 0;
                        int key1 = graph[i][j+1].getWires().get(city1).getKey();
                        int key2 = graph[i][j].getWires().get(city2).getKey();
                        if(graph[i][j+1].findNode(key1).dir == 0)
                        graph[i][j+1].add_edge(graph[i][j+1].findNode(key1),graph[i][j].findNode(key2),graph[i][j].findNode(key2).getCost());
                    }
                }
            }
        }
    }
//clear all the lists and refill the different lists with the different kinds of nodes from all tiles    
    public void makeGraph(){
        graphList.clear();
        wireList.clear();
        sinkList.clear();
        sourceList.clear();
        sourceinList.clear();
        sinkinList.clear();
        destList.clear();
         for(int i = 0; i < size_g; i++){
            for(int j = 0; j < size_g; j++){
                for(int k = 0; k < graph[i][j].getSources().size(); k++){
                    graphList.add(graph[i][j].getSources().get(k));
                    sourceList.add(graph[i][j].getSources().get(k));
                }
                for(int k = 0; k < graph[i][j].getSinks().size(); k++){
                    graphList.add(graph[i][j].getSinks().get(k));
                    sinkList.add(graph[i][j].getSinks().get(k));
                    destList.add(graph[i][j].getSinks().get(k));
                }
                for(int k = 0; k < graph[i][j].getWires().size(); k++){
                    graphList.add(graph[i][j].getWires().get(k));
                    wireList.add(graph[i][j].getWires().get(k));
                }
            }
        }
    }
 
// the method is to add a random destination rather than input a source-destination list to source nodes 
    public void addDest(){
        Random random = new Random();
        for(int i = 0; i < size_g; i++){
            for(int j = 0; j < size_g; j++){
                for(int k = 0; k < graph[i][j].getSources().size();k++){
                    int num =  random.nextInt(destList.size()) + 0;
                    graph[i][j].getSources().get(k).dest.add(destList.get(num));
                    destList.remove(num);
                }  
            }
        }
    }
   
// the method to find shortest path for all source nodes to their destinations    
    public void find_shortest_path() {
        for (int i = 0; i < wireList.size(); i++) {
            wireList.get(i).resetOther();
        }
        for (int i = 0; i < size_g; i++) {
            for (int j = 0; j < size_g; j++) {
                for (int k = 0; k < graph[i][j].getSources().size(); k++) {                    
                    graph[i][j].getSources().get(k).paths.clear();
                    graph[i][j].getSources().get(k).distance.clear();
                    System.out.println("path from" +graph[i][j].getSources().get(k).getKey() + " to" + graph[i][j].getSources().get(k).dest.get(0).getKey());
                    ArrayList<Tile<Integer>.Node<Integer>> path_find = graph[i][j].findShortestPath(graph[i][j].getSources().get(k).getKey(),graph[i][j].getSources().get(k).dest.get(0).getKey(),graphList);
                    for (int m = 0; m < path_find.size(); m++) {
                        System.out.println("path" + path_find.get(m).getKey());
                        if(path_find.get(m).getState()==1){
                            path_find.get(m).changeOther();
                        }
                    }
                    graph[i][j].getSources().get(k).paths.add(path_find);
                    graph[i][j].getSources().get(k).distance.add(graph[i][j].getSources().get(k).dest.get(0).min_distance);
                }
            }
        }
    }
    //find shortest path for the source nodes we input in the list
    public void find_shortest_path_list() {
        for (int i = 0; i < sourceinList.size(); i++) {
            for (int m = 0; m < sourceinList.get(i).paths.size(); m++) {
                for (int n = 0; n < sourceinList.get(i).paths.get(m).size(); n++) {
                    sourceinList.get(i).paths.get(m).get(n).relaxOther();
                    sourceinList.get(i).paths.get(m).get(n).changeCost();
                    System.out.println("test" + sourceinList.get(i).paths.get(m).get(n).getKey() + " " + sourceinList.get(i).paths.get(m).get(n).getCost());
                    for (int a = 0; a < size_g; a++) {
                        for (int b = 0; b < size_g; b++) {
                            graph[a][b].changeEdges();
                        }
                    }
                }
                sourceinList.get(i).paths.get(m).clear();
            }
            sourceinList.get(i).paths.clear();
            sourceinList.get(i).distance.clear();
            for (int j = 0; j < sourceinList.get(i).dest.size(); j++) {
                System.out.println("path from" + sourceinList.get(i).getKey() + " to" + sourceinList.get(i).dest.get(j).getKey());
                ArrayList<Tile<Integer>.Node<Integer>> path_find = graph[0][0].findShortestPath(sourceinList.get(i).getKey(), sourceinList.get(i).dest.get(j).getKey(), graphList);
                for (int m = 0; m < path_find.size(); m++) {
                    DecimalFormat numformat = new DecimalFormat("#.00");
                    System.out.println("path" + path_find.get(m).getKey() + " " + numformat.format(path_find.get(m).getCost()));
                }
                sourceinList.get(i).paths.add(path_find);
                sourceinList.get(i).distance.add(sourceinList.get(i).dest.get(j).min_distance);
            }
        }
    }
    
//method to add destiantion to source nodes in the list according to the configuration file we input
    public void addNet(int index1, int index2) {
        for (int i = 0; i < sourceList.size(); i++) {
            if (sourceList.get(i).getKey() == index1) {
                sourceinList.add(sourceList.get(i));
                for (int j = 0; j < sinkList.size(); j++) {
                    if (sinkList.get(j).getKey() == index2) {
                        sourceList.get(i).dest.add(sinkList.get(j));
                    }
                }
            }
        }
    }
//method to test whether there still a congestion in the graph from the present usage of wire nodes    
    public boolean testCong() {
        for (int i = 0; i < wireList.size(); i++) {
            if (wireList.get(i).getOther() > 2) return false;
        }
        return true;
    }
//if there is still a congestion, update the costs of nodes and run algorithm again    
    public void runIteration(){
        for(int i = 0; i < size_g; i++){
            for(int j = 0; j < size_g; j++){
                for(int k = 0; k < graph[i][j].getWires().size();k++){
                    if(graph[i][j].getWires().get(k).getOther() > 2){
                        graph[i][j].getWires().get(k).changeHistory();
                        graph[i][j].getWires().get(k).changeCost();
                        System.out.println("show other: " + graph[i][j].getWires().get(k).getOther() + "show cost: " + graph[i][j].getWires().get(k).getCost() + "node" + graph[i][j].getWires().get(k).getKey());
                    }
                }
            }
        }
        for (int i = 0; i < size_g; i++) {
            for (int j = 0; j < size_g; j++) {
                graph[i][j].changeEdges();
            }
        }
        find_shortest_path_list();
    }
   
// return the size of graph
    public int getGraphSize(){
        return size_g;
    }
    
// return the size of wire 
    public int getWireSize(){
        return size_w;
    }
//return tiled graph    
    public Tile[][] getGraph(){
        return graph;
    }
//return the total number of source nodes    
    public int getSourceSize(){
        return source_num;
    }
//return the total number of sink nodes    
    public int getSinkSize(){
        return sink_num;
    }
//return list of all nodes in the graph
    public ArrayList<Tile<Integer>.Node<Integer>> getGraphList(){
        return graphList;
    }
//return list of all wires in the graph
    public ArrayList<Tile<Integer>.Node<Integer>> getWireList() {
        return wireList;
    }
//return list of all sources in the graph
    public ArrayList<Tile<Integer>.Node<Integer>> getSourceList() {
        return sourceList;
    }
    private int size_g;
    private int size_w;
    private int source_num;
    private int sink_num;
    private Tile<Integer>[][] graph;
    ArrayList<Tile<Integer>.Node<Integer>> graphList;
    ArrayList<Tile<Integer>.Node<Integer>> sourceList;
    ArrayList<Tile<Integer>.Node<Integer>> wireList;
    ArrayList<Tile<Integer>.Node<Integer>> sinkList;
    ArrayList<Tile<Integer>.Node<Integer>> destList;
    ArrayList<Tile<Integer>.Node<Integer>> sourceinList;
    ArrayList<Tile<Integer>.Node<Integer>> sinkinList;
}
