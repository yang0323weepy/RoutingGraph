
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import javax.swing.JTextArea;
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
public class DrawTileLabel extends JLabel {
    
/*
  constructor of DrawTileLabel this class tries to organize each tile and present it as a jlanel to draw on the jpanel  
    */
    public DrawTileLabel(TiledGraph ex, int x, int y, JTextArea label) {
        this.graph = ex;
        sources_a = new JLabel[graph.getSourceSize()];
        sinks_a = new JLabel[graph.getSinkSize()];
        wires_a = new JLabel[2 * graph.getWireSize()];
        showInfo = label;
        //specify the position of tile in tiled graph    
        i = x;
        j = y;
    }

    public void paintComponent(Graphics g) {
       setUp();
       System.out.println("hh" + redraw);
        Tile<Integer> tile = graph.getGraph()[i][j];
        if (redraw == false) {   
        g.drawRect(0, 0, drawBorder / graph.getGraphSize() / 2, drawBorder / graph.getGraphSize() / 2);
        g.setColor(Color.blue);
        g.drawRect(drawBorder / graph.getGraphSize() / 2, drawBorder / graph.getGraphSize() / 2, drawBorder / graph.getGraphSize() / 2 - 1, drawBorder / graph.getGraphSize() / 2 - 1);
        g.setColor(Color.black);
            for (int m = 0; m < graph.getSourceSize(); m++) {
                add(sources_a[m]);
            }
            for (int m = 0; m < graph.getSinkSize(); m++) {
                add(sinks_a[m]);
            }
            for (int m = 0; m < tile.getWires().size(); m++) {
                int rc = (int) tile.getWires().get(m).getCost();
                int gc = (int) tile.getWires().get(m).getCost() * 10;
                int bc = 255 - (int) tile.getWires().get(m).getCost();
                if (rc > 255) {
                    rc = 255;
                }
                if (gc > 255) {
                    gc = 255;
                }
                if (bc < 0) {
                    bc = 0;
                }
                wires_a[m].setBackground(new Color(rc, gc, bc));
                add(wires_a[m]);
            }
        } else {
            removeAll();
            System.out.println("test repaint on label");
            int Xpos = 0;
            int Ypos = 0;
            g.drawRect(Xpos, Ypos, drawBorder / 4, drawBorder / 4);
            g.setColor(Color.blue);
            g.drawRect(drawBorder / 4, Ypos + drawBorder / 4, drawBorder / 4 - 1, drawBorder / 4 - 1);
            g.setColor(Color.black);
            for (int m = 0; m < graph.getSourceSize(); m++) {
                sources_a[m].setLocation(Xpos + drawBorder / 4 - drawBorder / 16, Ypos + 1 + m * drawBorder / 12);
                sources_a[m].setSize(drawBorder / 16, drawBorder / 16);
                add(sources_a[m]);
            }
            for (int m = 0; m < graph.getSourceSize(); m++) {
                sinks_a[m].setLocation(Xpos + 1, Ypos + drawBorder / 4 - drawBorder / 16 - m * drawBorder / 12);
                sinks_a[m].setSize(drawBorder / 16, drawBorder / 16);
                add(sinks_a[m]);
            }
            int gap_x = 0;
            int gap_y = 0;
            for (int m = 0; m < tile.getWires().size(); m++) {
                int rc = (int) tile.getWires().get(m).getCost();
                int gc = (int) tile.getWires().get(m).getCost() * 10;
                int bc = 255 - (int) tile.getWires().get(m).getCost();
                if (rc > 255) {
                    rc = 255;
                }
                if (gc > 255) {
                    gc = 255;
                }
                if (bc < 0) {
                    bc = 0;
                }
                wires_a[m].setBackground(new Color(rc, gc, bc));
                if (tile.getWires().get(m).dir == 0) {
                    wires_a[m].setLocation(gap_x + drawBorder / 4 + 5, 0);
                    wires_a[m].setSize(drawBorder / 12 / (graph.getWireSize() - 1), drawBorder / 4);
                    gap_x = gap_x + drawBorder / 8 / (graph.getWireSize() - 1);
                } else {
                    wires_a[m].setLocation(0, gap_y + drawBorder / 4 + 5);
                    wires_a[m].setSize(drawBorder / 4, drawBorder / 12 / (graph.getWireSize() - 1));
                    gap_y = gap_y + drawBorder / 8 / (graph.getWireSize() - 1);
                }
                add(wires_a[m]);
            }
        }
            for (int j = 0; j < 2 * graph.getWireSize(); j++) {
                DecimalFormat numformat = new DecimalFormat("#.00");
                wires_a[j].setToolTipText("cost" + numformat.format(tile.getWires().get(j).getCost()));
            }
    }

    public boolean changeRedraw() {
        redraw = !redraw;
        return redraw;
    }
    /*
      initialize the labels 
    */
    public void setUp() {
                Tile<Integer> tile = graph.getGraph()[i][j];
                int x_pos = 0 + i * drawBorder / graph.getGraphSize();
                int y_pos = 0 + j * drawBorder / graph.getGraphSize();
                for (int m = 0; m < tile.getSources().size(); m++) {
                    sources_a[m] = new JLabel("", CENTER);
                    sources_a[m].setText(Integer.toString(tile.getSources().get(m).getKey()));
                    sources_a[m].setBackground(Color.green);
                    sources_a[m].setOpaque(true);
                    sources_a[m].setLocation(drawBorder / graph.getGraphSize() / 2 - drawBorder / graph.getGraphSize() / 8, 1 + m * drawBorder / graph.getGraphSize() / 8);
                    tile.getSources().get(m).pos_x_e = x_pos + drawBorder / graph.getGraphSize() / 2;
                    tile.getSources().get(m).pos_y_e = y_pos + 1 + drawBorder / graph.getGraphSize() / 8 + m * drawBorder / graph.getGraphSize() / 8;
                    sources_a[m].setSize(drawBorder / graph.getGraphSize() / 8, drawBorder / graph.getGraphSize() / 8);
                    sources_a[m].addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            JLabel l = (JLabel) e.getSource();
                            showInfo.setText("");
                            String state = "source";
                            int num = Integer.parseInt(l.getText());
                            String path = "path from";;
                            String cost= "cost";
                            int key = 0;
                            for (int i = 0; i < graph.getSourceList().size(); i++) {
                                if (graph.getSourceList().get(i).getKey() == num) {
                                    key = i;
                                }
                            }
                            for (int i = 0; i < graph.getSourceList().get(key).paths.size(); i++) {
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
                }
                for (int m = 0; m < tile.getSinks().size(); m++) {
                    sinks_a[m] = new JLabel("", CENTER);
                    sinks_a[m].setText(Integer.toString(tile.getSinks().get(m).getKey()));
                    sinks_a[m].setBackground(Color.green);
                    sinks_a[m].setOpaque(true);
                    sinks_a[m].setLocation(1, drawBorder / graph.getGraphSize() / 2 - drawBorder / graph.getGraphSize() / 8 - m * drawBorder / graph.getGraphSize() / 8);
                    tile.getSinks().get(m).pos_x_e = x_pos + m * drawBorder / graph.getGraphSize() / 8;
                    tile.getSinks().get(m).pos_y_e = y_pos + drawBorder / graph.getGraphSize() / 2;
                    sinks_a[m].setSize(drawBorder / graph.getGraphSize() / 8, drawBorder / graph.getGraphSize() / 8);
                }
                int gap_x = 0;
                int gap_y = 0;
                for (int m = 0; m < tile.getWires().size(); m++) {
                    wires_a[m] = new JLabel("", CENTER);
                    wires_a[m].setText(Integer.toString(tile.getWires().get(m).getKey()));
                    int rc = (int) tile.getWires().get(m).getCost();
                    int gc = (int) tile.getWires().get(m).getCost() * 10;
                    int bc = 255 - (int) tile.getWires().get(m).getCost();
                    if (rc > 255) {
                        rc = 255;
                    }
                    if (gc > 255) {
                        gc = 255;
                    }
                    if (bc < 0) {
                        bc = 0;
                    }
                    wires_a[m].setBackground(new Color(rc, gc, bc));
                    wires_a[m].setOpaque(true);
                    wires_a[m].addMouseListener(new MouseAdapter() {
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
                        wires_a[m].setLocation(gap_x + drawBorder / graph.getGraphSize() / 2 + drawBorder / graph.getGraphSize() / 16, 0);
                        wires_a[m].setSize(drawBorder / graph.getGraphSize() / 8 / (graph.getWireSize() - 1), drawBorder / graph.getGraphSize() / 2);
                        //give positions to wire nodes for drawing route in the future
                        tile.getWires().get(m).pos_x_s = x_pos + gap_x + drawBorder / graph.getGraphSize() / 2 + drawBorder / graph.getGraphSize() / 16;
                        tile.getWires().get(m).pos_y_s = y_pos;
                        tile.getWires().get(m).pos_x_e = x_pos + gap_x + drawBorder / graph.getGraphSize() / 2 + drawBorder / graph.getGraphSize() / 16;
                        tile.getWires().get(m).pos_y_e = y_pos + drawBorder / graph.getGraphSize() / 2;
                        gap_x = gap_x + drawBorder / graph.getGraphSize() / 4 / (graph.getWireSize() - 1);
                    } else {
                        wires_a[m].setLocation(0, gap_y + drawBorder / graph.getGraphSize() / 2 + drawBorder / graph.getGraphSize() / 16);
                        wires_a[m].setSize(drawBorder / graph.getGraphSize() / 2, drawBorder / graph.getGraphSize() / 8 / (graph.getWireSize() - 1));
                        //give positions to wire nodes for drawing route in the future
                        tile.getWires().get(m).pos_x_s = x_pos;
                        tile.getWires().get(m).pos_y_s = y_pos + gap_y + drawBorder / graph.getGraphSize() / 2 + drawBorder / graph.getGraphSize() / 16;
                        tile.getWires().get(m).pos_x_e = x_pos + drawBorder / graph.getGraphSize() / 2;
                        tile.getWires().get(m).pos_y_e = y_pos + gap_y + drawBorder / graph.getGraphSize() / 2 + drawBorder / graph.getGraphSize() / 16;
                        gap_y = gap_y + drawBorder / graph.getGraphSize() / 4 / (graph.getWireSize() - 1);
                    }
                }
    }


    private TiledGraph graph;
    private final int drawBorder = 640;
    private JLabel[] sources_a;
    private JLabel[] sinks_a;
    private JLabel[] wires_a;
    private int i;
    private int j;
    private boolean redraw = false;
    private JTextArea showInfo;
}
