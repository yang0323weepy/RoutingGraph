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

    public DrawGraph(RunGraph ex, JLabel square[][]) {
        this.run = ex;
        graph = ex.getGraph();
        squares = square;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        ArrayList<RoutingGraph<Integer>.Node<Integer>> wires = graph.getWire();
        for (int i = 0; i < wires.size(); i++) {
            int m = wires.get(i).getKey() % 4;
            int n = (wires.get(i).getKey() - m) / 4;
            squares[n][m].setBackground(Color.yellow);
            squares[n][m].setOpaque(true);
        }
            paintRoute(g);
    }

    public void paintRoute(Graphics g) {  
        graph = run.getGraph();
       ArrayList<RoutingGraph<Integer>.Node<Integer>> sources = new ArrayList<RoutingGraph<Integer>.Node<Integer>>();
        for (int i = 0; i < graph.getGraph().size(); i++) {
            if (graph.getGraph().get(i).getState() == 0) {
                sources.add(graph.getGraph().get(i));;
            }
        }
         for (int i = 0; i < sources.size(); i++) {
                for (int j = 0; j < sources.get(i).paths.size(); j++) {
                    int prev_x = 0;
                    int prev_y = 0;
                    for (int k = 0; k < sources.get(i).paths.get(j).size(); k++) {
                        int m = sources.get(i).paths.get(j).get(k).getKey() % 4;
                        int n = (sources.get(i).paths.get(j).get(k).getKey() - m) / 4;
                        if (k != 0) {
                            g.setColor(Color.black);
                            g.drawLine(prev_x, prev_y, squares[n][m].getX() + 20, squares[n][m].getY() + 20);
                        }
                        if (sources.get(i).paths.get(j).get(k).getState() == 1) {
                            squares[n][m].setBackground(Color.green);
                            squares[n][m].setOpaque(true);
                        }
                        prev_x = squares[n][m].getX() + 20;
                        prev_y = squares[n][m].getY() + 20;
                    }
                }
            }
    }
    private RunGraph run;
    private RoutingGraph<Integer> graph;
    private JLabel squares[][];
}
