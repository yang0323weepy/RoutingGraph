/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Enumeration;
import java.awt.geom.Line2D;

import javax.swing.*;

/**
 *
 * @author yangy
 */
public class DrawGraph extends JPanel {

    public DrawGraph(RunGraph ex, JLabel square[][], JLabel square_sink[][], JLabel square_wire[][], JToggleButton square_switch[][]) {
        this.run = ex;
        graph = ex.getGraph();
        sources_a = square;
        sinks_a = square_sink; 
        wires_a = square_wire; 
        switches = square_switch;
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int Xpos = 2;
        int Ypos = 2;
        for (int i = 0; i < run.getGraphSize(); i++) {
            for (int j = 0; j < run.getGraphSize(); j++) {               
               g.drawRect(Xpos+i*640/run.getGraphSize(), Ypos+j*640/run.getGraphSize(), 640/run.getGraphSize() / 2, 640/run.getGraphSize()/2);
               switches[i][j] = new JToggleButton();
               switches[i][j].setLocation(Xpos+(640*i+320)/run.getGraphSize(),Ypos+(640*j+320)/run.getGraphSize());
               switches[i][j].setSize( 640/run.getGraphSize()/2, 640/run.getGraphSize()/2);
               switches[i][j].setOpaque(true);
            }
        }
        paintRoute(g);
    }

    //draw the route between the nodes
    public void paintRoute(Graphics g) { 
        graph = run.getGraph();
       ArrayList<RoutingGraph<Integer>.Node<Integer>> wires = run.getGraph().getWire();
       ArrayList<RoutingGraph<Integer>.Node<Integer>> sources = new ArrayList<RoutingGraph<Integer>.Node<Integer>>();
        for (int i = 0; i < graph.getGraph().size(); i++) {
            if (graph.getGraph().get(i).getState() == 0) {
                sources.add(graph.getGraph().get(i));
            }
        }
         for (int i = 0; i < sources.size(); i++) {
                for (int j = 0; j < sources.get(i).paths.size(); j++) {
                    int m = sources.get(i).getKey() % run.getGraphSize();
                    int n = (sources.get(i).getKey() - m) / run.getGraphSize();
                    int prev_x =  0;
                    int prev_y =  0;
                    for (int k = 1; k < sources.get(i).paths.get(j).size()-1; k++) {
                       m = (sources.get(i).paths.get(j).get(k).getKey() -sources.size())% run.getWireSize();
                       n = (sources.get(i).paths.get(j).get(k).getKey() - sources.size()) / run.getWireSize();
                        if (sources.get(i).paths.get(j).get(k).dir == 0) {
                            if(prev_x != 0 && prev_y!=0)    g.drawLine(prev_x, prev_y, wires_a[n][m].getX(),wires_a[n][m].getY()+ 640/run.getGraphSize()/2);
                            prev_x = wires_a[n][m].getX();
                            prev_y = wires_a[n][m].getY() + 640/run.getGraphSize()/2;
                            
                        } else {
                            if(prev_x != 0 && prev_y!=0)   g.drawLine(prev_x, prev_y, wires_a[n][m].getX()+ 640/run.getGraphSize()/2,wires_a[n][m].getY());
                            prev_x = wires_a[n][m].getX() + 640/run.getGraphSize()/2;
                            prev_y = wires_a[n][m].getY();
                        }
                    }
                    int sink_num = sources.get(i).paths.get(j).get(sources.get(i).paths.get(j).size()-1).getKey();
                    m = (sink_num -sources.size()-wires.size())% run.getGraphSize();
                    n = (sink_num - m - sources.size()-wires.size()) / run.getGraphSize();
//                    g.drawLine(prev_x, prev_y, sinks_a[n][m].getX(), sinks_a[n][m].getY());
                    
                }
            }

         //update cost in the tooltip for each wire
         for (int i = 0; i < 2*(run.getGraphSize()-1)*run.getGraphSize(); i++) {
            for (int j = 0; j < run.getWireSize(); j++) {
                 wires_a[i][j].setToolTipText("cost"+graph.getWire().get(run.getWireSize() * i + j).getCost());
            }
        }
    }

    private RunGraph run;
    private RoutingGraph<Integer> graph;
    private JLabel sources_a[][];
    private JLabel sinks_a[][];
    private JLabel wires_a[][];
    private JToggleButton switches[][];
}
