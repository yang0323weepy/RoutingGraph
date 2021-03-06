
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.*;
import static javax.swing.SwingConstants.CENTER;
import java.util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author yangy
 */
public class RoutingResourceGraph extends javax.swing.JFrame {

    /**
     * Creates new form RoutingResourceGraph
     */
    public RoutingResourceGraph() {
        ex = new RunGraph(3,2);
        squares = new JLabel[ex.getGraphSize()][ex.getGraphSize()];
        squares_sinks = new JLabel[ex.getGraphSize()][ex.getGraphSize()];
        squares_wires = new JLabel[2 * ex.getGraphSize() * ex.getGraphSize()][ex.getWireSize()];
        switches = new JToggleButton[ex.getGraphSize()][ex.getGraphSize()];
        ex.initialize();
        initComponents();
        initialize();
        add_edge();
        ex.find_shortest_path();
        showCong();
        showGraph();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        showPro = new javax.swing.JDialog();
        jScrollPane1 = new javax.swing.JScrollPane();
        prosperity = new javax.swing.JTextArea();
        exitD = new javax.swing.JButton();
        graphPanel = new DrawGraph(ex,squares,squares_sinks,squares_wires,switches);
        route = new javax.swing.JButton();
        reroute = new javax.swing.JButton();
        showState = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        showInfo = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        about = new javax.swing.JMenuItem();
        exit = new javax.swing.JMenuItem();

        showPro.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        prosperity.setColumns(20);
        prosperity.setRows(5);
        jScrollPane1.setViewportView(prosperity);

        exitD.setText("OK");

        javax.swing.GroupLayout showProLayout = new javax.swing.GroupLayout(showPro.getContentPane());
        showPro.getContentPane().setLayout(showProLayout);
        showProLayout.setHorizontalGroup(
            showProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(showProLayout.createSequentialGroup()
                .addGroup(showProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(showProLayout.createSequentialGroup()
                        .addGap(190, 190, 190)
                        .addComponent(exitD, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(showProLayout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(87, Short.MAX_VALUE))
        );
        showProLayout.setVerticalGroup(
            showProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(showProLayout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(exitD)
                .addGap(35, 35, 35))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        graphPanel.setPreferredSize(new java.awt.Dimension(640, 640));

        javax.swing.GroupLayout graphPanelLayout = new javax.swing.GroupLayout(graphPanel);
        graphPanel.setLayout(graphPanelLayout);
        graphPanelLayout.setHorizontalGroup(
            graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );
        graphPanelLayout.setVerticalGroup(
            graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );

        route.setText("Start");
        route.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                routeActionPerformed(evt);
            }
        });

        reroute.setText("Restart");
        reroute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rerouteActionPerformed(evt);
            }
        });

        showState.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        showState.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showStateMouseClicked(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Yellow: wire not in use");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Red: Sink node");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Green: wire in use");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Blue: Source node");

        showInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        showInfo.setText("Node Information");
        showInfo.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jMenu1.setText("Menu");

        about.setText("About");
        jMenu1.add(about);

        exit.setText("Exit");
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });
        jMenu1.add(exit);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(showState, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(128, 128, 128)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(route, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(reroute)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                                .addComponent(showInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(graphPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(68, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(showState, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(route)
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(reroute)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(showInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(graphPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void routeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_routeActionPerformed
        // TODO add your handling code here:
        System.out.println("===============================");
        showInfo.setText("");
        ex.runIteration();
        showCong();
        graphPanel.repaint();
    }//GEN-LAST:event_routeActionPerformed

    private void rerouteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rerouteActionPerformed
        // TODO add your handling code here:
        ex.initialize();
        showInfo.setText("");
        initialize();
        add_edge();
        ex.find_shortest_path();
        showCong();
        graphPanel.repaint();
    }//GEN-LAST:event_rerouteActionPerformed

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_exitActionPerformed

    private void showStateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showStateMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_showStateMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RoutingResourceGraph.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RoutingResourceGraph.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RoutingResourceGraph.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RoutingResourceGraph.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RoutingResourceGraph().setVisible(true);
            }
        });
    }

    
    public void initialize(){
         ArrayList<RoutingGraph<Integer>.Node<Integer>> wires = ex.getGraph().getWire();
        ArrayList<RoutingGraph<Integer>.Node<Integer>> sources = new ArrayList<RoutingGraph<Integer>.Node<Integer>>();
        for (int i = 0; i < ex.getGraph().getGraph().size(); i++) {
            if (ex.getGraph().getGraph().get(i).getState() == 0) {
                sources.add(ex.getGraph().getGraph().get(i));
            }
        }
        ArrayList<RoutingGraph<Integer>.Node<Integer>> sinks = new ArrayList<RoutingGraph<Integer>.Node<Integer>>();
        for (int i = 0; i < ex.getGraph().getGraph().size(); i++) {
            if (ex.getGraph().getGraph().get(i).getState() == 2) {
                sinks.add(ex.getGraph().getGraph().get(i));
            }
        }
        for (int i = 0; i < sources.size(); i++) {
            squares[i / ex.getGraphSize()][i % ex.getGraphSize()] = new JLabel("", CENTER);
            squares[i / ex.getGraphSize()][i % ex.getGraphSize()].setText(Integer.toString(sources.get(i).getKey()));
            squares[i / ex.getGraphSize()][i % ex.getGraphSize()].setBackground(Color.green);
            squares[i / ex.getGraphSize()][i % ex.getGraphSize()].setOpaque(true);
            squares[i / ex.getGraphSize()][i % ex.getGraphSize()].setLocation(2 + (640 * (i / ex.getGraphSize()) + 320) / ex.getGraphSize() - 26, 3 + (i % ex.getGraphSize()) * 640 / ex.getGraphSize());
            squares[i / ex.getGraphSize()][i % ex.getGraphSize()].setSize(25, 25);
            squares[i / ex.getGraphSize()][i % ex.getGraphSize()].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JLabel l = (JLabel) e.getSource();
                    String state = "";
                    int num = Integer.parseInt(l.getText());
                    state = "source";
                    String path = "path from";
                    String cost = "cost ";
                    for (int i = 0; i < ex.drawGraph().get(num).paths.size(); i++) {
                        for (int m = 0; m < ex.drawGraph().get(num).paths.get(i).size(); m++) {
                            path = path + " " + ex.drawGraph().get(num).paths.get(i).get(m).getKey();
                        }
                        DecimalFormat numformat = new DecimalFormat("#.00");
                        cost = cost+numformat.format(ex.drawGraph().get(num).distance.get(i));
                    }
                   
                    showInfo.setText("<html>" + state + "<br>" + path + "<br>" + cost + "</html>");

                }
            });
        }

        for (int i = 0; i < sinks.size(); i++) {
            squares_sinks[i / ex.getGraphSize()][i % ex.getGraphSize()] = new JLabel("", CENTER);
            squares_sinks[i / ex.getGraphSize()][i % ex.getGraphSize()].setText(Integer.toString(sinks.get(i).getKey()));
            squares_sinks[i / ex.getGraphSize()][i % ex.getGraphSize()].setBackground(Color.yellow);
            squares_sinks[i / ex.getGraphSize()][i % ex.getGraphSize()].setOpaque(true);
            squares_sinks[i / ex.getGraphSize()][i % ex.getGraphSize()].setLocation(3 + (640 * (i / ex.getGraphSize())) / ex.getGraphSize(), 2 + (640 * (i % ex.getGraphSize()) + 320) / ex.getGraphSize() - 26);
            squares_sinks[i / ex.getGraphSize()][i % ex.getGraphSize()].setSize(25, 25);
            squares_sinks[i / ex.getGraphSize()][i % ex.getGraphSize()].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JLabel l = (JLabel) e.getSource();
                    String state = "";
                    state = "sink";
                    showInfo.setText("state: " + state);
                }
            });
        }
        int array_size = wires.size() / 2;
        int j = 0;
        int direction = 0;
        for (int i = 0; i < wires.size(); i++) {
            if (j == array_size) {
                j = 0;
                direction = (direction + 1) % 2;
            }
            squares_wires[i / ex.getWireSize()][i % ex.getWireSize()] = new JLabel("", CENTER);
            squares_wires[i / ex.getWireSize()][i % ex.getWireSize()].setText(Integer.toString(wires.get(i).getKey()));
            squares_wires[i / ex.getWireSize()][i % ex.getWireSize()].setBackground(Color.red);
            squares_wires[i / ex.getWireSize()][i % ex.getWireSize()].setOpaque(true);
            wires.get(i).dir = direction;
            if (direction == 0) {
                sources.get(i / ex.getWireSize()).addNeighbour(wires.get(i));
                squares_wires[i / ex.getWireSize()][i % ex.getWireSize()].setSize(15,640/ex.getGraphSize()/2);
                int x_pos = (i / (ex.getWireSize() * ex.getGraphSize())) * 640 / ex.getGraphSize() + (i % ex.getWireSize()) * 50 + 640 / ex.getGraphSize() / 2 + 640 / ex.getGraphSize() / 16;
                int y_pos = 3 + 640 / ex.getGraphSize() * (i % (ex.getWireSize() * ex.getGraphSize()) / ex.getWireSize());
                squares_wires[i / ex.getWireSize()][i % ex.getWireSize()].setLocation(x_pos, y_pos);
            } else if (direction == 1) {
                sources.get((i-array_size)/ ex.getWireSize()).addNeighbour(wires.get(i));
                int x_pos = 640 / ex.getGraphSize() * ((i - array_size) / (ex.getWireSize() * ex.getGraphSize()));
                int y_pos = 640 / ex.getGraphSize() / 2 + 640 / ex.getGraphSize() / 16 + (i % ex.getWireSize()) * 50 + (i - array_size) % (ex.getWireSize() *ex.getGraphSize()) / ex.getWireSize() * 640 / ex.getGraphSize();
                squares_wires[i / ex.getWireSize()][i % ex.getWireSize()].setSize( 640/ex.getGraphSize() / 2, 10);
                squares_wires[i / ex.getWireSize()][i % ex.getWireSize()].setLocation(x_pos, y_pos);
            }
            squares_wires[i / ex.getWireSize()][i % ex.getWireSize()].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JLabel l = (JLabel) e.getSource();
                    String state = "";
                    state = "wire";
                    int num = Integer.parseInt(l.getText());
                    double history_get = ex.drawGraph().get(num).getHistory();
                    int history = 0;
                    if (history_get != 0) {
                        history = (int) (Math.log(ex.drawGraph().get(num).getHistory()) / Math.log(1.1));
                    }
                    showInfo.setText("<html>" + state + "<br>" + "congestion history" + history + "<br>" + "congestion" + ex.drawGraph().get(num).getOther() + "</html>");
                }
            });
            j++;
        }
        showInfo.setFont(new Font("Courier", Font.PLAIN, 13));
    }
    public void showGraph() {
        ArrayList<RoutingGraph<Integer>.Node<Integer>> wires = ex.getGraph().getWire();
        ArrayList<RoutingGraph<Integer>.Node<Integer>> sources = new ArrayList<RoutingGraph<Integer>.Node<Integer>>();
        for (int i = 0; i < ex.getGraph().getGraph().size(); i++) {
            if (ex.getGraph().getGraph().get(i).getState() == 0) {
                sources.add(ex.getGraph().getGraph().get(i));
            }
        }
        ArrayList<RoutingGraph<Integer>.Node<Integer>> sinks = new ArrayList<RoutingGraph<Integer>.Node<Integer>>();
        for (int i = 0; i < ex.getGraph().getGraph().size(); i++) {
            if (ex.getGraph().getGraph().get(i).getState() == 2) {
                sinks.add(ex.getGraph().getGraph().get(i));
            }
        }
        for (int i = 0; i < sources.size(); i++) {
            graphPanel.add(squares[i / ex.getGraphSize()][i % ex.getGraphSize()]);
        }
        for (int i = 0; i < sinks.size(); i++) {
            graphPanel.add(squares_sinks[i / ex.getGraphSize()][i % ex.getGraphSize()]);
        }
        for (int i = 0; i < wires.size(); i++) {
            graphPanel.add(squares_wires[i/ex.getWireSize()][i%ex.getWireSize()]);
        }
        showInfo.setFont(new Font("Courier", Font.PLAIN, 13));
    }
    public void add_edge() {
        ArrayList<RoutingGraph<Integer>.Node<Integer>> sources = new ArrayList<RoutingGraph<Integer>.Node<Integer>>();
        for (int i = 0; i < ex.getGraph().getGraph().size(); i++) {
            if (ex.getGraph().getGraph().get(i).getState() == 0) {
                sources.add(ex.getGraph().getGraph().get(i));
            }
        }
        int size_w = ex.getWireSize();
        int size_g = ex.getGraphSize();
        ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
        
        for (int i = 0; i < size_g; i++) {
            for (int j = 0; j < size_g; j++) {
                ArrayList<Integer> num_list = new ArrayList<Integer>();
                for (int k = 0; k < sources.get(i * size_g + j).neighbour.size(); k++) {
                    num_list.add(sources.get(i * size_g + j).neighbour.get(k).getKey());
                }
                if (i + 1 < size_g) {
                    for (int k = 0; k < sources.get((i + 1) * size_g + j).neighbour.size(); k++) {
                        if (sources.get((i + 1) * size_g + j).neighbour.get(k).dir == 1) {
                            num_list.add(sources.get((i + 1) * size_g + j).neighbour.get(k).getKey());
                        }
                    }
                }
                if (j+1 < size_g) {
                    for (int k = 0; k < sources.get(i * size_g + j+1).neighbour.size(); k++) {
                        if (sources.get(i* size_g + j+1).neighbour.get(k).dir == 0) {
                            num_list.add(sources.get(i * size_g + j+1).neighbour.get(k).getKey());
                        }
                    }
                }
                list.add(num_list);
            }
        }
       
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).size(); j++) {
                System.out.print("list " + list.get(i).get(j));
            }
            System.out.println();
            }
      for(int i = 0; i < size_g*size_g; i++){
          for(int k = 0; k < size_w*size_w*size_g; k++){
            Random random = new Random();
            int city1 = random.nextInt(list.get(i).size()) + 0;
            int city2 = random.nextInt(list.get(i).size()) + 0;
            double weight = random.nextDouble()+2;
            if(city1 != city2 && list.get(i).get(city1) < size_g*size_g+2*(size_g*size_g*size_w) && list.get(i).get(city2) < size_g*size_g+2*(size_g*size_g*size_w)){
                ex.getGraph().AddEdge(list.get(i).get(city1),list.get(i).get(city2),weight);
            } 
              System.out.println("edge test1 " + list.get(i).get(city1)+" "+list.get(i).get(city2)+" " + weight);             
        }
    }
         
    int num = size_g*size_g;
     for(int i = 0; i < size_g*size_g; i++){
        ex.getGraph().AddEdge(i,num, ex.getGraph().getGraph().get(num).getCost()); 
       num++;
       ex.getGraph().AddEdge(i,num, ex.getGraph().getGraph().get(num).getCost()); 
       num++;
        }
     
     int num_s = size_g*size_g+size_g*size_g*size_w;
      for(int i = size_g*size_g+2*(size_g*size_g*size_w); i < (size_g*size_g*2+2*size_g*size_g*size_w); i++){       
       ex.getGraph().AddEdge(num_s,i,ex.getGraph().getGraph().get(num_s).getCost());  
       System.out.println("edge test2 " + num_s + " " + i);
       num_s++;
       ex.getGraph().AddEdge(num_s,i,ex.getGraph().getGraph().get(num_s).getCost()); 
              System.out.println("edge test2 " + num_s + " " + i);
       num_s++;
        }
    }


    //show whether the congestion has been resolved in the panel 
    public void showCong() {
        if (!ex.getGraph().testCong()) {
            showState.setText("STILL CONGESTED");
        } else {
            showState.setText("This circuit has no congestion");
        }
    }
    private RunGraph ex;
    private JLabel squares[][];
    private JLabel squares_sinks[][];
    private JLabel squares_wires[][];
    private JToggleButton switches[][];
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem about;
    private javax.swing.JMenuItem exit;
    private javax.swing.JButton exitD;
    private javax.swing.JPanel graphPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea prosperity;
    private javax.swing.JButton reroute;
    private javax.swing.JButton route;
    private javax.swing.JLabel showInfo;
    private javax.swing.JDialog showPro;
    private javax.swing.JLabel showState;
    // End of variables declaration//GEN-END:variables
}
