
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
    public TiledGraph(int num1, int num2){
        size_g = num1;
        size_w = num2;
        graph = new Tile[size_g][size_g];
        graphList = new ArrayList<Tile<Integer>.Node<Integer>>();
        wireList = new ArrayList<Tile<Integer>.Node<Integer>>();
        sinkList = new ArrayList<Tile<Integer>.Node<Integer>>();
        destList = new ArrayList<Tile<Integer>.Node<Integer>>();
        sourceList = new ArrayList<Tile<Integer>.Node<Integer>>();
        sinkinList = new ArrayList<Tile<Integer>.Node<Integer>>();
        sourceinList = new ArrayList<Tile<Integer>.Node<Integer>>();
    }
    
    public void initialize(){
        int index = 0;
        for(int i = 0; i < size_g; i++){
            for(int j = 0; j < size_g; j++){
                graph[i][j] = new Tile<Integer>(1,1,size_w,index);
                graph[i][j].generate();
                index = graph[i][j].getStartNum();  
            }
        }
        connectTile();
        makeGraph();
        //addDest();
    }

    public void connectTile() {
        for (int i = 0; i < size_g; i++) {
            for (int j = 0; j < size_g; j++) {
                if (i + 1 < size_g) {
                    for (int k = 0; k < size_w * size_w * size_w*size_w; k++) {
                        Random random = new Random();
                        int city1 = random.nextInt(graph[i][j].getWires().size()) + 0;
                        int city2 = random.nextInt(graph[i+1][j].getWires().size()) + 0;
                        int key1 = graph[i][j].getWires().get(city1).getKey();
                        int key2 = graph[i+1][j].getWires().get(city2).getKey();
                        double weight = random.nextDouble()+random.nextInt(5);
                        if(graph[i+1][j].findNode(key2).dir == 1)
                        graph[i][j].add_edge(graph[i][j].findNode(key1),graph[i+1][j].findNode(key2),weight);
                    }
                    for (int k = 0; k < size_w * size_w* size_w*size_w; k++) {
                        Random random = new Random();
                        int city1 = random.nextInt(graph[i+1][j].getWires().size()) + 0;
                        int city2 = random.nextInt(graph[i][j].getWires().size()) + 0;
                        int key1 = graph[i+1][j].getWires().get(city1).getKey();
                        int key2 = graph[i][j].getWires().get(city2).getKey();
                        double weight = random.nextDouble()+random.nextInt(5);
                        if(graph[i+1][j].findNode(key1).dir == 1)
                        graph[i+1][j].add_edge(graph[i+1][j].findNode(key1),graph[i][j].findNode(key2),weight);
                    }
                }
                if (j + 1 < size_g) {
                    for (int k = 0; k < size_w * size_w * size_w*size_w; k++) {
                        Random random = new Random();
                        int city1 = random.nextInt(graph[i][j].getWires().size()) + 0;
                        int city2 = random.nextInt(graph[i][j+1].getWires().size()) + 0;
                        double weight = random.nextDouble()+random.nextInt(5);
                        int key1 = graph[i][j].getWires().get(city1).getKey();
                        int key2 = graph[i][j+1].getWires().get(city2).getKey();
                        if(graph[i][j+1].findNode(key2).dir == 0)
                        graph[i][j].add_edge(graph[i][j].findNode(key1),graph[i][j+1].findNode(key2),weight);
                    }
                    for (int k = 0; k < size_w * size_w * size_w*size_w; k++) {
                        Random random = new Random();
                        int city1 = random.nextInt(graph[i][j+1].getWires().size()) + 0;
                        int city2 = random.nextInt(graph[i][j].getWires().size()) + 0;
                        int key1 = graph[i][j+1].getWires().get(city1).getKey();
                        int key2 = graph[i][j].getWires().get(city2).getKey();
                        double weight = random.nextDouble()+random.nextInt(5);
                        if(graph[i][j+1].findNode(key1).dir == 0)
                        graph[i][j+1].add_edge(graph[i][j+1].findNode(key1),graph[i][j].findNode(key2),weight);
                    }
                }
            }
        }
    }
    
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
    
    public void addDest(){
        Random random = new Random();
        ArrayList<Integer> num_list = new ArrayList<Integer>();
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
    
    public void find_shortest_path_list() {
        for (int i = 0; i < wireList.size(); i++) {
            wireList.get(i).resetOther();
        }
        for(int i = 0; i < sourceList.size();i++){
            sourceList.get(i).paths.clear();
            sourceList.get(i).distance.clear();
        }
        for (int i = 0; i < sourceinList.size(); i++) {                  
                    sourceinList.get(i).paths.clear();
                    sourceinList.get(i).distance.clear();
                    for(int j = 0; j < sourceinList.get(i).dest.size(); j++){
                    System.out.println("path from" +sourceinList.get(i).getKey() + " to" +sourceinList.get(i).dest.get(j).getKey());
                    ArrayList<Tile<Integer>.Node<Integer>> path_find = graph[0][0].findShortestPath(sourceinList.get(i).getKey(),sourceinList.get(i).dest.get(j).getKey(),graphList);
                    for (int m = 0; m < path_find.size(); m++) {
                        System.out.println("path" + path_find.get(m).getKey());
                        if(path_find.get(m).getState()==1){
                            path_find.get(m).changeOther();
                        }
                    }
                    sourceinList.get(i).paths.add(path_find);
                    sourceinList.get(i).distance.add(sourceinList.get(i).dest.get(j).min_distance);
                    }
                }
    }
        
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
    
    public boolean testCong() {
        for (int i = 0; i < wireList.size(); i++) {
            if (wireList.get(i).getOther() > 2) return false;
        }
        return true;
    }
    
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
   

    public int getGraphSize(){
        return size_g;
    }
    public int getWireSize(){
        return size_w;
    }
    
    public Tile[][] getGraph(){
        return graph;
    }
    
    public ArrayList<Tile<Integer>.Node<Integer>> getWireList(){
        return wireList;
    }
    
    public ArrayList<Tile<Integer>.Node<Integer>> getGraphList(){
        return graphList;
    }
    
    public ArrayList<Tile<Integer>.Node<Integer>> getSourceList(){
        return sourceList;
    }
    private int size_g;
    private int size_w;
    private Tile<Integer>[][] graph;
    ArrayList<Tile<Integer>.Node<Integer>> graphList;
    ArrayList<Tile<Integer>.Node<Integer>> sourceList;
    ArrayList<Tile<Integer>.Node<Integer>> wireList;
    ArrayList<Tile<Integer>.Node<Integer>> sinkList;
    ArrayList<Tile<Integer>.Node<Integer>> destList;
    ArrayList<Tile<Integer>.Node<Integer>> sourceinList;
    ArrayList<Tile<Integer>.Node<Integer>> sinkinList;
}
