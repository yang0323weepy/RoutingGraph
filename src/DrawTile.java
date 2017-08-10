
import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.text.DecimalFormat;
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
public class DrawTile extends JPanel {

    public DrawTile(TiledGraph ex, JTextArea label) {
        this.graph = ex;
        this.setPreferredSize(new Dimension(640, 640));
        tile_a = new DrawTileLabel[graph.getGraphSize()][graph.getGraphSize()];
        switchbox = new JToggleButton[graph.getGraphSize()][graph.getGraphSize()];
        showInfo = label;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                repaint();
                zoom = false;
            }
        });
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    // to zoom the whole panel, use transfor to realize it
        if (zoom_view) {
            Graphics2D g2 = (Graphics2D) g;
            transform = g2.getTransform();
            transform.scale(scale, scale);
            g2.setTransform(transform);
        }
        //if no zoom request, just show the tile on the jpanel                                                 
        if (!zoom) {
            removeAll();
            showGraph();
            paintRoute(g);
            
        } else {
            // to zoom specific tile and its details 
            removeAll();
            DrawTileLabel tile_zoom = new DrawTileLabel(graph, need_zoom / graph.getGraphSize(), need_zoom % graph.getGraphSize(),showInfo);
            tile_zoom.changeRedraw();
            tile_zoom.setOpaque(true);
            tile_zoom.setLocation(drawBorder / 4, drawBorder / 4);
            tile_zoom.setSize(drawBorder / 2, drawBorder / 2);
            this.add(tile_zoom);
            zoom = false;
        }
         
//        setUp();
    }

    //draw the route between the nodes
    public void paintRoute(Graphics g) {
        //update cost in the tooltip for each wire
        for (int i = 0; i < graph.getGraphSize(); i++) {
            for (int j = 0; j < graph.getGraphSize(); j++) {
                Tile<Integer> temp = graph.getGraph()[i][j];
                drawRoute(g, temp, i, j);
            }
        }
    }

//add tile labels to show them in the panel 
    public void showGraph() {
        for (int i = 0; i < graph.getGraphSize(); i++) {
            for (int j = 0; j < graph.getGraphSize(); j++) {
                tile_a[i][j] = new DrawTileLabel(graph, i, j,showInfo);
                tile_a[i][j].setName(Integer.toString(i * graph.getGraphSize() + j));
                tile_a[i][j].setOpaque(true);
                tile_a[i][j].setLocation(i * drawBorder / graph.getGraphSize(), j * drawBorder / graph.getGraphSize());
                tile_a[i][j].setSize(drawBorder / graph.getGraphSize(), drawBorder / graph.getGraphSize());
                tile_a[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        JLabel l = (JLabel) e.getSource();
                        if (e.getClickCount() == 2 && e.getModifiers() == MouseEvent.BUTTON3_MASK) {
                            need_zoom = Integer.parseInt(l.getName());
                            zoom = true;
                            repaint();
                        }
                    }
                });
                add(tile_a[i][j]);
            }
        }
        showInfo.setFont(new Font("Courier", Font.PLAIN, 13));
    }
    
//test the direction of current wire node and destination wire node, to 
    public boolean testDir(int num1, int num2, int num3, int dir) {
        if (dir == 0 && num1 + 1 < graph.getGraphSize() && num2 > 0) {
            Tile<Integer> temp2 = graph.getGraph()[num1 + 1][num2 - 1];
            for (int i = 0; i < temp2.getWires().size(); i++) {
                if (temp2.getWires().get(i).getKey() == num3 && temp2.getWires().get(i).dir == 1) {
                    return true;
                }
            }
        } else if (dir == 1 && num2 + 1 < graph.getGraphSize() && num1 > 0) {
            Tile<Integer> temp2 = graph.getGraph()[num1 - 1][num2 + 1];
            for (int i = 0; i < temp2.getWires().size(); i++) {
                if (temp2.getWires().get(i).getKey() == num3 && temp2.getWires().get(i).dir == 0) {
                    return true;
                }
            }
        }
        return false;
    }
//draw routes according to the results of pathFinder algorithm 
    public void drawRoute(Graphics g, Tile<Integer> temp, int i, int j) {
        for (int a = 0; a < temp.getSources().size(); a++) {
            for (int k = 0; k < temp.getSources().get(a).paths.size(); k++) {
                int prev_x = 0;
                int prev_y = 0;
                int prev_key = 0;
                for (int m = 0; m < temp.getSources().get(a).paths.get(k).size(); m++) {
                    if (m == 0) {
                        prev_x = temp.getSources().get(a).paths.get(k).get(m).pos_x_e;
                        prev_y = temp.getSources().get(a).paths.get(k).get(m).pos_y_e;
                    } else if (m == 1) {
                        g.drawLine(prev_x, prev_y, temp.getSources().get(a).paths.get(k).get(m).pos_x_e, prev_y);
                        prev_key = temp.getSources().get(a).paths.get(k).get(m).getKey();
                        prev_x = temp.getSources().get(a).paths.get(k).get(m).pos_x_e;
                        prev_y = temp.getSources().get(a).paths.get(k).get(m).pos_y_e;
                    } else if (m != 0 && m == temp.getSources().get(a).paths.get(k).size() - 1) {
                        g.drawLine(temp.getSources().get(a).paths.get(k).get(m).pos_x_e, graph.getGraphList().get(prev_key).pos_y_s, temp.getSources().get(a).paths.get(k).get(m).pos_x_e, temp.getSources().get(a).paths.get(k).get(m).pos_y_e);
                    } else {
                        if (temp.getSources().get(a).paths.get(k).get(m).dir == graph.getGraphList().get(prev_key).dir) {
                            if (prev_key > temp.getSources().get(a).paths.get(k).get(m).getKey()) {
                                g.drawLine(graph.getGraphList().get(prev_key).pos_x_s, graph.getGraphList().get(prev_key).pos_y_s, temp.getSources().get(a).paths.get(k).get(m).pos_x_e, temp.getSources().get(a).paths.get(k).get(m).pos_y_e);
                            } else {
                                g.drawLine(graph.getGraphList().get(prev_key).pos_x_e, graph.getGraphList().get(prev_key).pos_y_e, temp.getSources().get(a).paths.get(k).get(m).pos_x_s, temp.getSources().get(a).paths.get(k).get(m).pos_y_s);
                            }
                        } else {
                            if (graph.getGraphList().get(prev_key).getTile().equals(temp.getSources().get(a).paths.get(k).get(m).getTile())) {
                                g.drawLine(graph.getGraphList().get(prev_key).pos_x_e, graph.getGraphList().get(prev_key).pos_y_e, temp.getSources().get(a).paths.get(k).get(m).pos_x_e, temp.getSources().get(a).paths.get(k).get(m).pos_y_e);
                            } else {
                                if (temp.getSources().get(a).paths.get(k).get(m).dir == 0) {
                                    if (prev_key < temp.getSources().get(a).paths.get(k).get(m).getKey()) {
                                        g.drawLine(graph.getGraphList().get(prev_key).pos_x_e, graph.getGraphList().get(prev_key).pos_y_e, temp.getSources().get(a).paths.get(k).get(m).pos_x_s, temp.getSources().get(a).paths.get(k).get(m).pos_y_s);
                                    } else if (testDir(i, j, prev_key, 0)) {
                                        g.drawLine(graph.getGraphList().get(prev_key).pos_x_s, graph.getGraphList().get(prev_key).pos_y_s, temp.getSources().get(a).paths.get(k).get(m).pos_x_s, temp.getSources().get(a).paths.get(k).get(m).pos_y_s);
                                    } else {
                                        g.drawLine(graph.getGraphList().get(prev_key).pos_x_s, graph.getGraphList().get(prev_key).pos_y_s, temp.getSources().get(a).paths.get(k).get(m).pos_x_e, temp.getSources().get(a).paths.get(k).get(m).pos_y_e);
                                    }
                                } else {
                                    if (prev_key > temp.getSources().get(a).paths.get(k).get(m).getKey()) {
                                        g.drawLine(graph.getGraphList().get(prev_key).pos_x_s, graph.getGraphList().get(prev_key).pos_y_s, temp.getSources().get(a).paths.get(k).get(m).pos_x_e, temp.getSources().get(a).paths.get(k).get(m).pos_y_e);
                                    } else if (testDir(i, j, prev_key, 1)) {
                                        g.drawLine(graph.getGraphList().get(prev_key).pos_x_s, graph.getGraphList().get(prev_key).pos_y_s, temp.getSources().get(a).paths.get(k).get(m).pos_x_s, temp.getSources().get(a).paths.get(k).get(m).pos_y_s);
                                    } else {
                                        g.drawLine(graph.getGraphList().get(prev_key).pos_x_e, graph.getGraphList().get(prev_key).pos_y_e, temp.getSources().get(a).paths.get(k).get(m).pos_x_s, temp.getSources().get(a).paths.get(k).get(m).pos_y_s);
                                    }
                                }
                            }
                        }
                        prev_key = temp.getSources().get(a).paths.get(k).get(m).getKey();
                    }
                }
            }
        }
    }
//set zoom scale
    public void setScale(double num) {
        scale = num;
        int width = (int) (getWidth() * num);
        int height = (int) (getHeight() * num);
        setPreferredSize(new Dimension(width, height));
        revalidate();
        repaint();
        System.out.println("transform1");

    }

    public void setZoomView() {
        zoom_view = !zoom_view;
    }

    private TiledGraph graph;
    private DrawTileLabel[][] tile_a;
    private JTextArea showInfo;
    private JToggleButton[][] switchbox;
    private double scale = 1;
    private final int drawBorder = 640;
    private boolean zoom = false;
    private boolean zoom_view = false;
    private int need_zoom;
    private AffineTransform transform;
}
