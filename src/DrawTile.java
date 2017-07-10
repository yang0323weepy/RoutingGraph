
import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import static javax.swing.SwingConstants.CENTER;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author yangy
 */
public class DrawTile extends JPanel{
    
     public DrawTile(TiledGraph ex) {
        this.graph = ex;
        sources_a = new JLabel[graph.getGraphSize()][graph.getGraphSize()];
        sinks_a = new JLabel[graph.getGraphSize()][graph.getGraphSize()];
        wires_a = new JLabel[2 * graph.getGraphSize() * graph.getGraphSize()][ex.getWireSize()];
//        switches = square_switch;
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int Xpos = 2;
        int Ypos = 2;
        for (int i = 0; i < graph.getGraphSize(); i++) {
            for (int j = 0; j < graph.getGraphSize(); j++) {               
               g.drawRect(Xpos+i*640/graph.getGraphSize(), Ypos+j*640/graph.getGraphSize(), 640/graph.getGraphSize() / 2, 640/graph.getGraphSize()/2);
            }
        }
        paintRoute(g);
    }

    //draw the route between the nodes
    public void paintRoute(Graphics g) {
       ArrayList<Tile<Integer>.Node<Integer>> wires = new ArrayList<Tile<Integer>.Node<Integer>>();
       ArrayList<Tile<Integer>.Node<Integer>> sources = new ArrayList<Tile<Integer>.Node<Integer>>();
       ArrayList<Tile<Integer>.Node<Integer>> sinks = new ArrayList<Tile<Integer>.Node<Integer>>();
        for (int i = 0; i < graph.getGraphSize(); i++) {
            for (int j = 0; j < graph.getGraphSize(); j++) {
                Tile<Integer> tile = graph.getGraph()[i][j];
                sources.add(tile.getSources().get(0));
                sinks.add(tile.getSinks().get(0));
                for(int k = 0; k <tile.getWires().size();k++){
                    wires.add(tile.getWires().get(k));
                }
            }
        }
        for(int i = 0; i < sources.size(); i++){
//                sources_a[i / tile_graph.getGraphSize()][i % tile_graph.getGraphSize()] = new JLabel("", CENTER);
//                sources_a[i / tile_graph.getGraphSize()][i % tile_graph.getGraphSize()].setText(Integer.toString(sources.get(i).getKey()));
//                sources_a[i / tile_graph.getGraphSize()][i % tile_graph.getGraphSize()].setBackground(Color.green);
//                sources_a[i / tile_graph.getGraphSize()][i % tile_graph.getGraphSize()].setOpaque(true);
//                sources_a[i / tile_graph.getGraphSize()][i % tile_graph.getGraphSize()].setLocation(2 + (640 * (i/graph.getGraphSize()) + 320) / graph.getGraphSize() - 26, 3 + (i%graph.getGraphSize()) * 640 / graph.getGraphSize());
//                sources_a[i / tile_graph.getGraphSize()][i % tile_graph.getGraphSize()].setSize(25, 25);
//                add(sources_a[i / tile_graph.getGraphSize()][i % tile_graph.getGraphSize()]);
        }
//        graph = run.getGraph();
//       ArrayList<TiledGraph<Integer>.Node<Integer>> wires = run.getGraph().getWire();
//       ArrayList<TiledGraph<Integer>.Node<Integer>> sources = new ArrayList<RoutingGraph<Integer>.Node<Integer>>();
//       ArrayList<TiledGraph<Integer>.Node<Integer>> sinks = new ArrayList<RoutingGraph<Integer>.Node<Integer>>();
//        for (int i = 0; i < graph.getGraph().size(); i++) {
//            if (graph.getGraph().get(i).getState() == 0) {
//                sources.add(graph.getGraph().get(i));
//            }
//        }
//         for (int i = 0; i < sources.size(); i++) {
//                for (int j = 0; j < sources.get(i).paths.size(); j++) {
//                    int m = sources.get(i).getKey() % run.getGraphSize();
//                    int n = (sources.get(i).getKey() - m) / run.getGraphSize();
//                    int prev_x =  0;
//                    int prev_y =  0;
//                    for (int k = 1; k < sources.get(i).paths.get(j).size()-1; k++) {
//                       m = (sources.get(i).paths.get(j).get(k).getKey() -sources.size())% run.getWireSize();
//                       n = (sources.get(i).paths.get(j).get(k).getKey() - sources.size()) / run.getWireSize();
//                        if (sources.get(i).paths.get(j).get(k).dir == 0) {
//                            if(prev_x != 0 && prev_y!=0)    g.drawLine(prev_x, prev_y, wires_a[n][m].getX(),wires_a[n][m].getY()+ 640/run.getGraphSize()/2);
//                            prev_x = wires_a[n][m].getX();
//                            prev_y = wires_a[n][m].getY() + 640/run.getGraphSize()/2;
//                            
//                        } else {
//                            if(prev_x != 0 && prev_y!=0)   g.drawLine(prev_x, prev_y, wires_a[n][m].getX()+ 640/run.getGraphSize()/2,wires_a[n][m].getY());
//                            prev_x = wires_a[n][m].getX() + 640/run.getGraphSize()/2;
//                            prev_y = wires_a[n][m].getY();
//                        }
//                    }
//                    int sink_num = sources.get(i).paths.get(j).get(sources.get(i).paths.get(j).size()-1).getKey();
//                    m = (sink_num -sources.size()-wires.size())% run.getGraphSize();
//                    n = (sink_num - m - sources.size()-wires.size()) / run.getGraphSize();
////                    g.drawLine(prev_x, prev_y, sinks_a[n][m].getX(), sinks_a[n][m].getY());
//                    
//                }
//            }
//
//         //update cost in the tooltip for each wire
//         for (int i = 0; i < 2*(run.getGraphSize()-1)*run.getGraphSize(); i++) {
//            for (int j = 0; j < run.getWireSize(); j++) {
//                 wires_a[i][j].setToolTipText("cost"+graph.getWire().get(run.getWireSize() * i + j).getCost());
//            }
//        }
    }

    private TiledGraph graph;
    private JLabel[][] sources_a;
    private JLabel[][] sinks_a;
    private JLabel[][] wires_a;
    private JToggleButton[][] switches;
}
