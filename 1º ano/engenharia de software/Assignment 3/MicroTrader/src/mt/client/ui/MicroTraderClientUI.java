package mt.client.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import mt.client.MicroTraderClient;
import mt.client.controller.Controller;
import mt.comm.ClientComm;

/**
 *
 * @author wegneto
 */
public class MicroTraderClientUI extends javax.swing.JFrame implements MicroTraderClient {

    private Timer timer;

    private final String screenTitle = "Micro Trader";
    
    /**
     * Creates new form MicroTraderClientUI
     */
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(screenTitle + " | (Disconnected)");
        setResizable(false);

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
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(placeOrderBtn)
                .addGap(6, 6, 6))
        );

        setBounds(0, 0, 487, 383);
    }// </editor-fold>//GEN-END:initComponents

    private void connectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectActionPerformed
        if (!Session.clientComm.isConnected()) {
            ConnectForm form = new ConnectForm(this, true);
            form.setVisible(true);
            if (Session.clientComm.isConnected()) {
                setTitle(screenTitle + " | Connected user: " + Session.loggedUser);
                browseOrders();
            }
        } else {
            JOptionPane.showMessageDialog(this, "You are already connected to a server. \nNavigate to File > Disconnect before connecting with new nickname.", "Warning", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_connectActionPerformed

    private void disconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disconnectActionPerformed
        if (Session.clientComm.isConnected()) {
            Session.clientComm.disconnect();
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
        if (Session.clientComm.isConnected()) {
            Session.clientComm.disconnect();
        }
        System.exit(0);
    }//GEN-LAST:event_exitActionPerformed

    private void placeOrderBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_placeOrderBtnActionPerformed
        if (Session.clientComm.isConnected()) {
            PlaceOrderForm form = new PlaceOrderForm(this, true);
            form.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "You must be connected to a server to place orders. \nNavigate to File > Connect.", "Warning", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_placeOrderBtnActionPerformed

    @Override
    public void start(ClientComm clientComm) {
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
            java.util.logging.Logger.getLogger(MicroTraderClientUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MicroTraderClientUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MicroTraderClientUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MicroTraderClientUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MicroTraderClientUI client = new MicroTraderClientUI();
                Session.clientComm = clientComm;
                client.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem connect;
    private javax.swing.JMenuItem disconnect;
    private javax.swing.JMenuItem exit;
    private javax.swing.JMenu fileMenu;
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
        System.out.println("ClientUI >> Browse orders... ");
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
    
}
