package mt.client.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import mt.client.controller.Controller;
import mt.client.Session;

public class MicroTraderClientUI extends javax.swing.JFrame {

    private Timer timer;

    private final String screenTitle = "Micro Trader";
    
    private final Controller controller = new Controller();
    
    public boolean teste = false;
    
    public MicroTraderClientUI() {
        initComponents();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        placeOrderBtn = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        unfulfilledOrdersScrollPane = new javax.swing.JScrollPane();
        unfulfilledOrdersTable = new javax.swing.JTable();
        myOrdersScrollPane = new javax.swing.JScrollPane();
        myOrdersTable = new javax.swing.JTable();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        connect = new javax.swing.JMenuItem();
        disconnect = new javax.swing.JMenuItem();
        jSeparator = new javax.swing.JPopupMenu.Separator();
        exit = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(screenTitle + " | (Disconnected)");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                MicroTraderClientUI.this.windowClosing(evt);
            }
        });

        placeOrderBtn.setText("Place Order");
        placeOrderBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                placeOrderBtnActionPerformed(evt);
            }
        });

        unfulfilledOrdersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        unfulfilledOrdersScrollPane.setViewportView(unfulfilledOrdersTable);

        jTabbedPane1.addTab("Unfulfilled Orders", unfulfilledOrdersScrollPane);

        myOrdersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        myOrdersScrollPane.setViewportView(myOrdersTable);

        jTabbedPane1.addTab("My Orders", myOrdersScrollPane);

        fileMenu.setText("File");

        connect.setText("Connect");
        connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectActionPerformed(evt);
            }
        });
        fileMenu.add(connect);

        disconnect.setText("Disconnect");
        disconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnectActionPerformed(evt);
            }
        });
        fileMenu.add(disconnect);
        fileMenu.add(jSeparator);

        exit.setText("Exit");
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });
        fileMenu.add(exit);

        menuBar.add(fileMenu);

        jMenu2.setText("Orders");

        jMenuItem2.setText("Generate");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        menuBar.add(jMenu2);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(placeOrderBtn))
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(placeOrderBtn)
                .addGap(6, 6, 6))
        );

        setBounds(0, 0, 640, 502);
    }// </editor-fold>//GEN-END:initComponents

    private void connectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectActionPerformed
        if (!controller.isConnected()) {
            ConnectForm form = new ConnectForm(this, true);
            form.setLocationRelativeTo(this);
            form.setVisible(true);
            if (controller.isConnected()) {
                setTitle(screenTitle + " | Connected user: " + controller.getLoggedUser());
                browseOrders();
            }
        } else {
            JOptionPane.showMessageDialog(this, "You are already connected to a server. \nNavigate to File > Disconnect before connecting with new nickname.", "Warning", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_connectActionPerformed

    private void disconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disconnectActionPerformed
        if (controller.isConnected()) {
            controller.disconnect();
            timer.stop();
            unfulfilledOrdersTable.setModel(new DefaultTableModel());
            myOrdersTable.setModel(new DefaultTableModel());
            setTitle(screenTitle + " | (Disconnected)");
        } else {
            JOptionPane.showMessageDialog(this, "You are already disconnected from the server.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_disconnectActionPerformed

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
        if (controller.isConnected()) {
            controller.disconnect();
        }
        
        this.dispose();
        
        try {
            notifyObject(this);
        } catch (InterruptedException ex) {
            Logger.getLogger(MicroTraderClientUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_exitActionPerformed

    private void placeOrderBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_placeOrderBtnActionPerformed
        if (controller.isConnected()) {
            PlaceOrderForm form = new PlaceOrderForm(this, true);
            form.setLocationRelativeTo(this);
            form.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "You must be connected to a server to place orders. \nNavigate to File > Connect.", "Warning", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_placeOrderBtnActionPerformed

    private void windowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_windowClosing
        if (controller.isConnected()) {
            controller.disconnect();
        }

        try {
            notifyObject(this);
        } catch (InterruptedException ex) {
            Logger.getLogger(MicroTraderClientUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_windowClosing

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem connect;
    private javax.swing.JMenuItem disconnect;
    private javax.swing.JMenuItem exit;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPopupMenu.Separator jSeparator;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JScrollPane myOrdersScrollPane;
    private javax.swing.JTable myOrdersTable;
    private javax.swing.JButton placeOrderBtn;
    private javax.swing.JScrollPane unfulfilledOrdersScrollPane;
    private javax.swing.JTable unfulfilledOrdersTable;
    // End of variables declaration//GEN-END:variables

    private void browseOrders() {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Browse orders.");
        timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new Controller().browseOrders();
                    unfulfilledOrdersTable.setModel(new OrderTableModel(Session.orders));
                    myOrdersTable.setModel(new OrderTableModel(Session.history));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        timer.start();
    }
    
    public void notifyObject(MicroTraderClientUI object) throws InterruptedException {
        synchronized(object) {
            object.notify();
        }
    }
    
}
