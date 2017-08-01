
import java.awt.Color;
import java.awt.Graphics;
import java.text.DecimalFormat;
import javax.swing.JLabel;

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

    public DrawTileLabel(TiledGraph ex, JLabel[][] a, JLabel[][] b, JLabel[][] c, int x, int y) {
        this.graph = ex;
        sources_a = a;
        sinks_a = b;
        wires_a = c;
        i = x;
        j = y;

    }

    public void paintComponent(Graphics g) {
//        super.paintComponent(g);
        if (redraw == false) {
            Tile<Integer> tile = graph.getGraph()[i][j];
            for(int m = 0; m < graph.getSourceSize(); m++){
            add(sources_a[i*graph.getGraphSize()+j][m]);
            }
            for(int m = 0; m < graph.getSinkSize(); m++){
            add(sinks_a[i*graph.getGraphSize()+j][m]);
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
                wires_a[i * graph.getGraphSize() + j][m].setBackground(new Color(rc, gc, bc));
                add(wires_a[i * graph.getGraphSize() + j][m]);
            }
        } else {
            int Xpos = 0;
            int Ypos = 0;
            g.drawRect(Xpos, Ypos, drawBorder / 8, drawBorder / 8);
            g.setColor(Color.blue);
            g.drawRect(drawBorder / 8, Ypos + drawBorder / 8, drawBorder / 8 - 1, drawBorder / 8 - 1);
            g.setColor(Color.black);
            Tile<Integer> tile = graph.getGraph()[i][j];
            for(int m = 0; m < graph.getSourceSize(); m++){
            sources_a[i*graph.getGraphSize()+j][m].setLocation(Xpos + drawBorder / 8 - drawBorder / 25, Ypos + 1 + m *drawBorder/25);
            sources_a[i*graph.getGraphSize()+j][m].setSize(drawBorder / 25, drawBorder / 25);
            add(sources_a[i*graph.getGraphSize()+j][m]);
              }
            for(int m = 0; m < graph.getSourceSize(); m++){
            sinks_a[i*graph.getGraphSize()+j][m].setLocation(Xpos + 1 , Ypos + drawBorder / 8 - drawBorder / 20 - m *drawBorder/25);
            sinks_a[i*graph.getGraphSize()+j][m].setSize(drawBorder / 25, drawBorder / 25);
            add(sinks_a[i*graph.getGraphSize()+j][m]);
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
                wires_a[i * graph.getGraphSize() + j][m].setBackground(new Color(rc, gc, bc));
                if (tile.getWires().get(m).dir == 0) {
                    wires_a[i * graph.getGraphSize() + j][m].setLocation(gap_x + drawBorder / 8 + 5, 0);
                    wires_a[i * graph.getGraphSize() + j][m].setSize(drawBorder / 24 / (graph.getWireSize() - 1), drawBorder / 8);
//                        tile.getWires().get(m).pos_x_s =  gap_x + drawBorder / graph.getGraphSize() / 2 + drawBorder / graph.getGraphSize() / 16;
//                        tile.getWires().get(m).pos_y_s = 0;
//                        tile.getWires().get(m).pos_x_e =  gap_x + drawBorder / graph.getGraphSize() / 2 + drawBorder / graph.getGraphSize() / 16;
//                        tile.getWires().get(m).pos_y_e =  drawBorder / graph.getGraphSize() / 2;
                    gap_x = gap_x + drawBorder / 16 / (graph.getWireSize() - 1);
                } else {
                    wires_a[i * graph.getGraphSize() + j][m].setLocation(0, gap_y + drawBorder / 8 + 5);
                    wires_a[i * graph.getGraphSize() + j][m].setSize(drawBorder / 8, drawBorder / 24 / (graph.getWireSize() - 1));
//                        tile.getWires().get(m).pos_x_s = x_pos;
//                        tile.getWires().get(m).pos_y_s = y_pos + gap_y + drawBorder / graph.getGraphSize() / 2 + drawBorder / graph.getGraphSize() / 16;
//                        tile.getWires().get(m).pos_x_e = x_pos + drawBorder / graph.getGraphSize() / 2;
//                        tile.getWires().get(m).pos_y_e = y_pos + gap_y + drawBorder / graph.getGraphSize() / 2 + drawBorder / graph.getGraphSize() / 16;
                    gap_y = gap_y + drawBorder / 16 / (graph.getWireSize() - 1);
                }
                add(wires_a[i * graph.getGraphSize() + j][m]);
            }
            changeRedraw();
        }
//       showInfo.setFont(new Font("Courier", Font.PLAIN, 13));
//        paintRoute(g);
    }

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

    public boolean changeRedraw() {
        redraw = !redraw;
        return redraw;
    }

    private TiledGraph graph;
    private final int drawBorder = 640;
    private JLabel[][] sources_a;
    private JLabel[][] sinks_a;
    private JLabel[][] wires_a;
    private int i;
    private int j;
    private boolean redraw = false;
}
