/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.*;
/**
 *
 * @author yangy
 */
public class DrawGraph extends JPanel {

    public DrawGraph(RoutingGraph<Integer> graph_draw, JLabel square[][] ) {
        graph = graph_draw;
        squares = square;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        ArrayList<RoutingGraph<Integer>.Node<Integer>> sources = new ArrayList<RoutingGraph<Integer>.Node<Integer>>();
        for (int i = 0; i < graph.getGraph().size(); i++) {
            if (graph.getGraph().get(i).getState() == 0) {
                sources.add(graph.getGraph().get(i));;
            }
        }
        for(int i = 0; i < sources.size();i++){
            for(int j = 0; j < sources.get(i).paths.size();j++){
                for(int k = 0; k < sources.get(i).paths.get(j).size();k++){
                     if (sources.get(i).paths.get(j).get(k).getState() == 1) {
                         int m = sources.get(i).paths.get(j).get(k).getKey()%4;
                         int n = (sources.get(i).paths.get(j).get(k).getKey()-m)/4;
                         squares[n][m].setBackground(Color.green);
                         squares[n][m].setOpaque(true);
                     }
            }
        }
    }
    }
    private RoutingGraph<Integer> graph;
    private JLabel squares[][];
}
