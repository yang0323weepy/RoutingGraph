
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
    }

    public void connectTile() {
        for (int i = 0; i < size_g; i++) {
            for (int j = 0; j < size_g; j++) {
                if (i + 1 < size_g) {
                    for (int k = 0; k < size_w * size_w; k++) {
                        Random random = new Random();
                        int city1 = random.nextInt(graph[i][j].getWires().size()) + 0;
                        int city2 = random.nextInt(graph[i+1][j].getWires().size()) + 0;
                        double weight = random.nextDouble() + 2;
                        graph[i][j].add_edge(graph[i][j].findNode(city1),graph[i+1][j].findNode(city2),weight);
                    }
                    for (int k = 0; k < size_w * size_w; k++) {
                        Random random = new Random();
                        int city1 = random.nextInt(graph[i+1][j].getWires().size()) + 0;
                        int city2 = random.nextInt(graph[i][j].getWires().size()) + 0;
                        double weight = random.nextDouble() + 2;
                        graph[i+1][j].add_edge(graph[i+1][j].findNode(city1),graph[i][j].findNode(city2),weight);
                    }
                }
                if (j + 1 < size_g) {
                    for (int k = 0; k < size_w * size_w; k++) {
                        Random random = new Random();
                        int city1 = random.nextInt(graph[i][j].getWires().size()) + 0;
                        int city2 = random.nextInt(graph[i][j+1].getWires().size()) + 0;
                        double weight = random.nextDouble() + 2;
                        graph[i][j].add_edge(graph[i][j].findNode(city1),graph[i][j+1].findNode(city2),weight);
                    }
                    for (int k = 0; k < size_w * size_w; k++) {
                        Random random = new Random();
                        int city1 = random.nextInt(graph[i][j+1].getWires().size()) + 0;
                        int city2 = random.nextInt(graph[i][j].getWires().size()) + 0;
                        double weight = random.nextDouble() + 2;
                        graph[i][j+1].add_edge(graph[i][j+1].findNode(city1),graph[i][j].findNode(city2),weight);
                    }
                }
            }
        }
    }
    
    public void makeGraph(){
        graphList = new ArrayList<Tile<Integer>.Node<Integer>>();
         for(int i = 0; i < size_g; i++){
            for(int j = 0; j < size_g; j++){
                for(int k = 0; k < graph[i][j].getSources().size(); k++){
                    graphList.add(graph[i][j].getSources().get(k));
                }
                for(int k = 0; k < graph[i][j].getSinks().size(); k++){
                    graphList.add(graph[i][j].getSinks().get(k));
                }
                for(int k = 0; k < graph[i][j].getWires().size(); k++){
                    graphList.add(graph[i][j].getWires().get(k));
                }
            }
        }
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
    private int size_g;
    private int size_w;
    private Tile<Integer>[][] graph;
    ArrayList<Tile<Integer>.Node<Integer>> graphList;
}
