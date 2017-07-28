
import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
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

     public DrawTile(TiledGraph ex, JTextArea label,JLabel[][] a,JLabel[][] b, JLabel[][] c) {
//   public DrawTile(TiledGraph ex, JTextArea label) {
        this.graph = ex;
        sources_a = new JLabel[graph.getGraphSize()][graph.getGraphSize()];
        sinks_a = new JLabel[graph.getGraphSize()][graph.getGraphSize()];
        wires_a = new JLabel[graph.getGraphSize() * graph.getGraphSize()][2 * graph.getWireSize()];
        tile_a = new DrawTileLabel[graph.getGraphSize()][graph.getGraphSize()];
        switchbox = new JToggleButton[graph.getGraphSize()][graph.getGraphSize()];
        showInfo = label; 
//        sources_a = a;
//        sinks_a = b;
//        wires_a = c;
//        tile_a = d;
        transform = new AffineTransform();
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { 
//              if (e.getModifiers() == MouseEvent.BUTTON3_MASK){
                x_test = e.getX();
                y_test = e.getY();
                 System.out.println("repainttttttt" + x_test + " "+ y_test);
                repaint();
//                }
            }
        });
        this.addMouseWheelListener(new MouseAdapter() {
           @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
             if(e.getWheelRotation() == 1){
                 scale=0.5;
                 System.out.println("scale" + scale);
             }
             else{
                 scale=2;
                 System.out.println("scale" + scale);
             } }
            });
            setUp();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.transform(transform);
        transform.scale(scale, scale);   
    if(!zoom){
        removeAll();
               for (int i = 0; i < graph.getGraphSize(); i++) {
            for (int j = 0; j < graph.getGraphSize(); j++) {
                g.drawRect( i * drawBorder / graph.getGraphSize(),  j * drawBorder / graph.getGraphSize(), drawBorder / graph.getGraphSize() / 2, drawBorder / graph.getGraphSize() / 2);
                g.setColor(Color.blue);
                g.drawRect( i * drawBorder / graph.getGraphSize() + drawBorder / graph.getGraphSize() / 2,  j * drawBorder / graph.getGraphSize() + drawBorder / graph.getGraphSize() / 2, drawBorder / graph.getGraphSize() / 2, drawBorder / graph.getGraphSize() / 2);
                g.setColor(Color.black);
            }
        }
        paintRoute(g);
        showGraph();  
    }
    else{
        removeAll();
        DrawTileLabel tile_zoom = new DrawTileLabel(graph,sources_a,sinks_a,wires_a,need_zoom/graph.getGraphSize(),need_zoom%graph.getGraphSize());
        tile_zoom.changeRedraw();
        this.add(tile_zoom);
        tile_zoom.setOpaque(true);
        tile_zoom.setLocation( drawBorder / 4, drawBorder / 4);
        tile_zoom.setSize(drawBorder / 4, drawBorder / 4);
        zoom = false;
     }
        setUp();
    }

    //draw the route between the nodes
    public void paintRoute(Graphics g) {
         //update cost in the tooltip for each wire
        for (int i = 0; i < graph.getGraphSize() * graph.getGraphSize(); i++) {
            for (int j = 0; j < 2 * graph.getWireSize(); j++) {
                DecimalFormat numformat = new DecimalFormat("#.00");
                wires_a[i][j].setToolTipText("cost" + numformat.format(graph.getWireList().get(i * 2 * graph.getWireSize() + j).getCost()));
            }
        }
        for (int i = 0; i < graph.getGraphSize(); i++) {
            for (int j = 0; j < graph.getGraphSize(); j++) {
                Tile<Integer> temp = graph.getGraph()[i][j];
                drawRoute(g, temp, i, j);
            }
        }
    }

    public void setUp() {
        for (int i = 0; i < graph.getGraphSize(); i++) {
            for (int j = 0; j < graph.getGraphSize(); j++) {
                Tile<Integer> tile = graph.getGraph()[i][j];
                int x_pos = 0 + i * drawBorder / graph.getGraphSize();
                int y_pos = 0 + j * drawBorder / graph.getGraphSize();
                sources_a[i][j] = new JLabel("", CENTER);
                sources_a[i][j].setText(Integer.toString(tile.getSources().get(0).getKey()));
                sources_a[i][j].setBackground(Color.green);
                sources_a[i][j].setOpaque(true);
                sources_a[i][j].setLocation( drawBorder / graph.getGraphSize() / 2 - drawBorder / graph.getGraphSize()/8,  1);
                tile.getSources().get(0).pos_x_e = x_pos + drawBorder / graph.getGraphSize() / 2;
                tile.getSources().get(0).pos_y_e = y_pos + 1;
                sources_a[i][j].setSize(drawBorder / graph.getGraphSize() /8, drawBorder / graph.getGraphSize()/8);
                sources_a[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        JLabel l = (JLabel) e.getSource();
                        showInfo.setText("");
                        String state = "";
                        int num = Integer.parseInt(l.getText());
                        state = "source";
                        String path = "path from";
                        String cost = "cost ";
                        int key = 0;
                        for (int i = 0; i < graph.getSourceList().size(); i++) {
                            if (graph.getSourceList().get(i).getKey() == num) {
                                key = i;
                            }
                        }
                        
                        for (int i = 0; i < graph.getSourceList().get(key).paths.size(); i++) {
                            path = "path from";
                            cost = "cost";
                            for (int m = 0; m < graph.getSourceList().get(key).paths.get(i).size(); m++) {
                                path = path + " " + graph.getSourceList().get(key).paths.get(i).get(m).getKey();
                            }
                            DecimalFormat numformat = new DecimalFormat("#.00");
                            cost = cost + numformat.format(graph.getSourceList().get(key).distance.get(i));
                            showInfo.append(state + "\n" + path + "\n" + cost + "!" + "\n");
                        }

                        for (int i = 0; i < graph.getGraphSize(); i++) {
                            for (int j = 0; j < graph.getGraphSize(); j++) {
                                for (int k = 0; k < graph.getGraph()[i][j].getSources().size(); k++) {
                                    Tile<Integer> temp = graph.getGraph()[i][j];
                                    if (temp.getSources().get(k).getKey() == num) {
                                        System.out.println("hello drawing");

                                    }
                                }
                            }
                        }

                    }
                });
                sinks_a[i][j] = new JLabel("", CENTER);
                sinks_a[i][j].setText(Integer.toString(tile.getSinks().get(0).getKey()));
                sinks_a[i][j].setBackground(Color.green);
                sinks_a[i][j].setOpaque(true);
                sinks_a[i][j].setLocation(1,  drawBorder / graph.getGraphSize() / 2 - drawBorder / graph.getGraphSize() /8);
                tile.getSinks().get(0).pos_x_e = x_pos;
                tile.getSinks().get(0).pos_y_e = y_pos + drawBorder / graph.getGraphSize() / 2;
                sinks_a[i][j].setSize(drawBorder / graph.getGraphSize() /8, drawBorder / graph.getGraphSize() /8);
                int gap_x = 0;
                int gap_y = 0;
                for (int m = 0; m < tile.getWires().size(); m++) {
                    wires_a[i * graph.getGraphSize() + j][m] = new JLabel("", CENTER);
                    wires_a[i * graph.getGraphSize() + j][m].setText(Integer.toString(tile.getWires().get(m).getKey()));
                    int rc = (int)tile.getWires().get(m).getCost();
                    int gc = (int)tile.getWires().get(m).getCost()*10;
                    int bc = 255-(int)tile.getWires().get(m).getCost();
                    if (rc > 255) rc = 255;
                    if (gc > 255) gc = 255;
                    if (bc < 0)     bc = 0;
                    wires_a[i * graph.getGraphSize() + j][m].setBackground(new Color(rc,gc,bc));
                    wires_a[i * graph.getGraphSize() + j][m].setOpaque(true);
                    wires_a[i * graph.getGraphSize() + j][m].addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            JLabel l = (JLabel) e.getSource();
                            String state = "";
                            state = "wire";
                            int num = Integer.parseInt(l.getText());
                            int key = 0;
                            for (int i = 0; i < graph.getWireList().size(); i++) {
                                if (graph.getWireList().get(i).getKey() == num) {
                                    key = i;
                                }
                            }
                            double history_get = graph.getWireList().get(key).getHistory();
                            int history = 0;
                            if (history_get != 0) {
                                history = (int) (Math.log(graph.getWireList().get(key).getHistory()) / Math.log(1.1));
                            }
                            showInfo.setText(state + " " + num + "\n" + "congestion history" + history + "\n" + "congestion" + graph.getWireList().get(key).getOther() + "\n" + "cost" + graph.getWireList().get(key).getCost() + "");
                        }
                    });
                    if (tile.getWires().get(m).dir == 0) {
                        wires_a[i * graph.getGraphSize() + j][m].setLocation(gap_x + drawBorder / graph.getGraphSize() / 2 + drawBorder / graph.getGraphSize() / 16, 0);
                        wires_a[i * graph.getGraphSize() + j][m].setSize(drawBorder / graph.getGraphSize() / 8/(graph.getWireSize()-1), drawBorder / graph.getGraphSize() / 2);
                        tile.getWires().get(m).pos_x_s = x_pos + gap_x + drawBorder / graph.getGraphSize() / 2 + drawBorder / graph.getGraphSize() / 16;
                        tile.getWires().get(m).pos_y_s = y_pos;
                        tile.getWires().get(m).pos_x_e = x_pos + gap_x + drawBorder / graph.getGraphSize() / 2 + drawBorder / graph.getGraphSize() / 16;
                        tile.getWires().get(m).pos_y_e = y_pos + drawBorder / graph.getGraphSize() / 2;
                        gap_x = gap_x + drawBorder / graph.getGraphSize() / 4/(graph.getWireSize()-1);
                    } else {
                        wires_a[i * graph.getGraphSize() + j][m].setLocation(0,  gap_y + drawBorder / graph.getGraphSize() / 2 + drawBorder / graph.getGraphSize() / 16);
                        wires_a[i * graph.getGraphSize() + j][m].setSize(drawBorder / graph.getGraphSize() / 2,drawBorder / graph.getGraphSize() / 8/(graph.getWireSize()-1));
                        tile.getWires().get(m).pos_x_s = x_pos;
                        tile.getWires().get(m).pos_y_s = y_pos + gap_y + drawBorder / graph.getGraphSize() / 2 + drawBorder / graph.getGraphSize() / 16;
                        tile.getWires().get(m).pos_x_e = x_pos + drawBorder / graph.getGraphSize() / 2;
                        tile.getWires().get(m).pos_y_e = y_pos + gap_y + drawBorder / graph.getGraphSize() / 2 + drawBorder / graph.getGraphSize() / 16;
                        gap_y = gap_y + drawBorder / graph.getGraphSize() / 4/(graph.getWireSize()-1);
                    }
                }
            }
        }
    }

    public void showGraph() {
        for (int i = 0; i < graph.getGraphSize(); i++) {
            for (int j = 0; j < graph.getGraphSize(); j++) {
                Tile<Integer> tile = graph.getGraph()[i][j];
                tile_a[i][j] = new DrawTileLabel(graph,sources_a,sinks_a,wires_a,i,j);
                tile_a[i][j].setName(Integer.toString(i*graph.getGraphSize()+j));
                tile_a[i][j].setOpaque(true);
                tile_a[i][j].setLocation(i * drawBorder / graph.getGraphSize(),j * drawBorder / graph.getGraphSize());
                tile_a[i][j].setSize(drawBorder / graph.getGraphSize(), drawBorder / graph.getGraphSize());
                tile_a[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        JLabel l = (JLabel) e.getSource();
                        if(e.getClickCount() == 2 && e.getModifiers() == MouseEvent.BUTTON3_MASK){
                            need_zoom = Integer.parseInt(l.getName());
                            System.out.println("textttttttttttttttttttttttttttttttttttttttttttttttttt");
                            zoom = true;
                            repaint();
                        }
                        else {
                            x_test = e.getX();
                            y_test = e.getY();
                            System.out.println("repainttttttt" + x_test + " " + y_test);
                            repaint();
                        }
                    }
                });
                add(tile_a[i][j]);
                //add(sources_a[i][j]);
                //add(sinks_a[i][j]);               
//                for (int m = 0; m < tile.getWires().size(); m++) {
//                    int rc = (int)tile.getWires().get(m).getCost();
//                    int gc = (int)tile.getWires().get(m).getCost()*10;
//                    int bc = 255-(int)tile.getWires().get(m).getCost();
//                    if (rc > 255) rc = 255;
//                    if (gc > 255) gc = 255;
//                    if (bc < 0)     bc = 0;
//                    wires_a[i * graph.getGraphSize() + j][m].setBackground(new Color(rc,gc,bc));
//                    //add(wires_a[i * graph.getGraphSize() + j][m]);
//                }
                                
            }
        }
        showInfo.setFont(new Font("Courier", Font.PLAIN, 13));
    }

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

    public void drawRoute(Graphics g, Tile<Integer> temp, int i, int j) {
        for (int k = 0; k < temp.getSources().get(0).paths.size(); k++) {
            int prev_x = 0;
            int prev_y = 0;
            int prev_key = 0;
            for (int m = 0; m < temp.getSources().get(0).paths.get(k).size(); m++) {
                if (m == 0) {
                    prev_x = temp.getSources().get(0).paths.get(k).get(m).pos_x_e;
                    prev_y = temp.getSources().get(0).paths.get(k).get(m).pos_y_e;
                } else if (m == 1) {
                    g.drawLine(prev_x, prev_y, temp.getSources().get(0).paths.get(k).get(m).pos_x_e, prev_y);
                    prev_key = temp.getSources().get(0).paths.get(k).get(m).getKey();
                    prev_x = temp.getSources().get(0).paths.get(k).get(m).pos_x_e;
                    prev_y = temp.getSources().get(0).paths.get(k).get(m).pos_y_e;
                } else if (m != 0 && m == temp.getSources().get(0).paths.get(k).size() - 1) {
                    g.drawLine(temp.getSources().get(0).paths.get(k).get(m).pos_x_e, graph.getGraphList().get(prev_key).pos_y_s, temp.getSources().get(0).paths.get(k).get(m).pos_x_e, temp.getSources().get(0).paths.get(k).get(m).pos_y_e);
                } else {
                    if (temp.getSources().get(0).paths.get(k).get(m).dir == graph.getGraphList().get(prev_key).dir) {
                        if (prev_key > temp.getSources().get(0).paths.get(k).get(m).getKey()) {
                            g.drawLine(graph.getGraphList().get(prev_key).pos_x_s, graph.getGraphList().get(prev_key).pos_y_s, temp.getSources().get(0).paths.get(k).get(m).pos_x_e, temp.getSources().get(0).paths.get(k).get(m).pos_y_e);
                        } else {
                            g.drawLine(graph.getGraphList().get(prev_key).pos_x_e, graph.getGraphList().get(prev_key).pos_y_e, temp.getSources().get(0).paths.get(k).get(m).pos_x_s, temp.getSources().get(0).paths.get(k).get(m).pos_y_s);
                        }
                    } else {
                        if (graph.getGraphList().get(prev_key).getTile().equals(temp.getSources().get(0).paths.get(k).get(m).getTile())) {
                            g.drawLine(graph.getGraphList().get(prev_key).pos_x_e, graph.getGraphList().get(prev_key).pos_y_e, temp.getSources().get(0).paths.get(k).get(m).pos_x_e, temp.getSources().get(0).paths.get(k).get(m).pos_y_e);
                        } else {
                            if (temp.getSources().get(0).paths.get(k).get(m).dir == 0) {
                                if (prev_key < temp.getSources().get(0).paths.get(k).get(m).getKey()) {
                                    g.drawLine(graph.getGraphList().get(prev_key).pos_x_e, graph.getGraphList().get(prev_key).pos_y_e, temp.getSources().get(0).paths.get(k).get(m).pos_x_s, temp.getSources().get(0).paths.get(k).get(m).pos_y_s);
                                } else if (testDir(i, j, prev_key, 0)) {
                                    g.drawLine(graph.getGraphList().get(prev_key).pos_x_s, graph.getGraphList().get(prev_key).pos_y_s, temp.getSources().get(0).paths.get(k).get(m).pos_x_s, temp.getSources().get(0).paths.get(k).get(m).pos_y_s);
                                } else {
                                    g.drawLine(graph.getGraphList().get(prev_key).pos_x_s, graph.getGraphList().get(prev_key).pos_y_s, temp.getSources().get(0).paths.get(k).get(m).pos_x_e, temp.getSources().get(0).paths.get(k).get(m).pos_y_e);
                                }
                            } else {
                                if (prev_key > temp.getSources().get(0).paths.get(k).get(m).getKey()) {
                                    g.drawLine(graph.getGraphList().get(prev_key).pos_x_s, graph.getGraphList().get(prev_key).pos_y_s, temp.getSources().get(0).paths.get(k).get(m).pos_x_e, temp.getSources().get(0).paths.get(k).get(m).pos_y_e);
                                } else if (testDir(i, j, prev_key, 1)) {
                                    g.drawLine(graph.getGraphList().get(prev_key).pos_x_s, graph.getGraphList().get(prev_key).pos_y_s, temp.getSources().get(0).paths.get(k).get(m).pos_x_s, temp.getSources().get(0).paths.get(k).get(m).pos_y_s);
                                } else {
                                    g.drawLine(graph.getGraphList().get(prev_key).pos_x_e, graph.getGraphList().get(prev_key).pos_y_e, temp.getSources().get(0).paths.get(k).get(m).pos_x_s, temp.getSources().get(0).paths.get(k).get(m).pos_y_s);
                                }
                            }
                        }
                    }
                    prev_key = temp.getSources().get(0).paths.get(k).get(m).getKey();
                }
            }
        }
    }
    



    private TiledGraph graph;
    private JLabel[][] sources_a;
    private JLabel[][] sinks_a;
    private JLabel[][] wires_a;
    private DrawTileLabel[][] tile_a;
    private JTextArea showInfo;
    private JToggleButton[][] switchbox;
    private AffineTransform transform;
    private double scale = 1;
    private final int drawBorder = 640;
    private boolean zoom = false;
    private int need_zoom;
    private int x_test;
    private int y_test;
}
