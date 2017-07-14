
import java.awt.BasicStroke;
import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
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
public class DrawTile extends JPanel {

//     public DrawTile(TiledGraph ex, JLabel label,JLabel[][] a,JLabel[][] b, JLabel[][] c) {
    public DrawTile(TiledGraph ex, JTextArea label) {
        this.graph = ex;
        sources_a = new JLabel[graph.getGraphSize()][graph.getGraphSize()];
        sinks_a = new JLabel[graph.getGraphSize()][graph.getGraphSize()];
        wires_a = new JLabel[graph.getGraphSize() * graph.getGraphSize()][2 * graph.getWireSize()];
        switchbox = new JToggleButton[graph.getGraphSize()][graph.getGraphSize()];
//        sources_a = a;
//        sinks_a = b;
//        wires_a = c;
        showInfo = label;
        paintS = false;
//        switches = square_switch;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setUp(g);
        showGraph();
        int Xpos = 2;
        int Ypos = 2;
        paintRoute(g);
        for (int i = 0; i < graph.getGraphSize(); i++) {
            for (int j = 0; j < graph.getGraphSize(); j++) {
                g.drawRect(Xpos + i * 640 / graph.getGraphSize(), Ypos + j * 640 / graph.getGraphSize(), 640 / graph.getGraphSize() / 2, 640 / graph.getGraphSize() / 2);
                switchbox[i][j] = new JToggleButton("");
                switchbox[i][j].setOpaque(true);
                switchbox[i][j].setLocation(Xpos + i * 640 / graph.getGraphSize() + 640 / graph.getGraphSize() / 2, Ypos + j * 640 / graph.getGraphSize() + 640 / graph.getGraphSize() / 2);
                switchbox[i][j].setSize(640 / graph.getGraphSize() / 2, 640 / graph.getGraphSize() / 2);
                g.setColor(Color.blue);
                g.drawRect(Xpos + i * 640 / graph.getGraphSize() + 640 / graph.getGraphSize() / 2, Ypos + j * 640 / graph.getGraphSize() + 640 / graph.getGraphSize() / 2, 640 / graph.getGraphSize() / 2, 640 / graph.getGraphSize() / 2);
                g.setColor(Color.black);
            }
        }

    }

    //draw the route between the nodes
    public void paintRoute(Graphics g) {
//         //update cost in the tooltip for each wire
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

    public void setUp(Graphics g) {
        for (int i = 0; i < graph.getGraphSize(); i++) {
            for (int j = 0; j < graph.getGraphSize(); j++) {
                Tile<Integer> tile = graph.getGraph()[i][j];
                int x_pos = 2 + i * 640 / graph.getGraphSize();
                int y_pos = 2 + j * 640 / graph.getGraphSize();
                sources_a[i][j] = new JLabel("", CENTER);
                sources_a[i][j].setText(Integer.toString(tile.getSources().get(0).getKey()));
                sources_a[i][j].setBackground(Color.green);
                sources_a[i][j].setOpaque(true);
                sources_a[i][j].setLocation(x_pos + 640 / graph.getGraphSize() / 2 - 25, y_pos + 1);
                tile.getSources().get(0).pos_x_e = x_pos + 640 / graph.getGraphSize() / 2;
                tile.getSources().get(0).pos_y_e = y_pos + 1;
                sources_a[i][j].setSize(25, 25);
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
                                        paintS = true;
                                        g.setColor(Color.blue);
                                        System.out.println("hello drawing");

                                    }
                                }
                            }
                        }

                    }
                });
                sinks_a[i][j] = new JLabel("", CENTER);
                sinks_a[i][j].setText(Integer.toString(tile.getSinks().get(0).getKey()));
                sinks_a[i][j].setBackground(Color.yellow);
                sinks_a[i][j].setOpaque(true);
                sinks_a[i][j].setLocation(x_pos + 1, y_pos + 640 / graph.getGraphSize() / 2 - 25);
                tile.getSinks().get(0).pos_x_e = x_pos;
                tile.getSinks().get(0).pos_y_e = y_pos + 640 / graph.getGraphSize() / 2;
                sinks_a[i][j].setSize(25, 25);
                int gap_x = 0;
                int gap_y = 0;
                for (int m = 0; m < tile.getWires().size(); m++) {
                    wires_a[i * graph.getGraphSize() + j][m] = new JLabel("", CENTER);
                    wires_a[i * graph.getGraphSize() + j][m].setText(Integer.toString(tile.getWires().get(m).getKey()));
                    wires_a[i * graph.getGraphSize() + j][m].setBackground(Color.red);
                    wires_a[i * graph.getGraphSize() + j][m].setOpaque(true);
                    wires_a[i * graph.getGraphSize() + j][m].addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            JLabel l = (JLabel) e.getSource();
                            String state = "";
                            state = "wire";
                            int num = Integer.parseInt(l.getText());
                            int key = 0;
                            for (int i = 0; i < graph.getSourceList().size(); i++) {
                                if (graph.getWireList().get(i).getKey() == num) {
                                    key = i;
                                }
                            }
                            double history_get = graph.getWireList().get(key).getHistory();
                            int history = 0;
                            if (history_get != 0) {
                                history = (int) (Math.log(graph.getWireList().get(key).getHistory()) / Math.log(1.1));
                            }
                            showInfo.setText("<html>" + state + " " + num + "<br>" + "congestion history" + history + "<br>" + "congestion" + graph.getWireList().get(key).getOther() + "<br>" + "cost" + graph.getWireList().get(key).getCost() + "</html>");
                        }
                    });
                    if (tile.getWires().get(m).dir == 0) {
                        wires_a[i * graph.getGraphSize() + j][m].setLocation(x_pos + gap_x + 640 / graph.getGraphSize() / 2 + 640 / graph.getGraphSize() / 16, y_pos);
                        wires_a[i * graph.getGraphSize() + j][m].setSize(15, 640 / graph.getGraphSize() / 2);
                        tile.getWires().get(m).pos_x_s = x_pos + gap_x + 640 / graph.getGraphSize() / 2 + 640 / graph.getGraphSize() / 16;
                        tile.getWires().get(m).pos_y_s = y_pos;
                        tile.getWires().get(m).pos_x_e = x_pos + gap_x + 640 / graph.getGraphSize() / 2 + 640 / graph.getGraphSize() / 16;
                        tile.getWires().get(m).pos_y_e = y_pos + 640 / graph.getGraphSize() / 2;
                        gap_x = gap_x + 640 / graph.getGraphSize() / 4;
                    } else {
                        wires_a[i * graph.getGraphSize() + j][m].setLocation(x_pos, y_pos + gap_y + 640 / graph.getGraphSize() / 2 + 640 / graph.getGraphSize() / 16);
                        wires_a[i * graph.getGraphSize() + j][m].setSize(640 / graph.getGraphSize() / 2, 15);
                        tile.getWires().get(m).pos_x_s = x_pos;
                        tile.getWires().get(m).pos_y_s = y_pos + gap_y + 640 / graph.getGraphSize() / 2 + 640 / graph.getGraphSize() / 16;
                        tile.getWires().get(m).pos_x_e = x_pos + 640 / graph.getGraphSize() / 2;
                        tile.getWires().get(m).pos_y_e = y_pos + gap_y + 640 / graph.getGraphSize() / 2 + 640 / graph.getGraphSize() / 16;
                        gap_y = gap_y + 640 / graph.getGraphSize() / 4;
                    }
                }
            }
        }
    }

    public void showGraph() {
        for (int i = 0; i < graph.getGraphSize(); i++) {
            for (int j = 0; j < graph.getGraphSize(); j++) {
                Tile<Integer> tile = graph.getGraph()[i][j];
                add(sources_a[i][j]);
                add(sinks_a[i][j]);
                for (int m = 0; m < tile.getWires().size(); m++) {
                    add(wires_a[i * graph.getGraphSize() + j][m]);
                }
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
        paintS = false;
    }

    private TiledGraph graph;
    private JLabel[][] sources_a;
    private JLabel[][] sinks_a;
    private JLabel[][] wires_a;
    private JTextArea showInfo;
    private JToggleButton[][] switchbox;
    private JToggleButton[][] switches;
    private boolean paintS;
}
