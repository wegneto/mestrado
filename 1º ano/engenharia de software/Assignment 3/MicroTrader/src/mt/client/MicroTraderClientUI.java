package mt.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import mt.Order;
import mt.comm.ClientComm;
import mt.comm.ClientSideMessage;

/**
 *
 * @author wegneto
 */
public class MicroTraderClientUI extends javax.swing.JFrame implements MicroTraderClient {

    private static ClientComm clientComm;

    private static List<Order> orders = new ArrayList<>();

    private static String nickname;

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

        jScrollPane1 = new javax.swing.JScrollPane();
        ordersTable = new javax.swing.JTable();
        placeOrderBtn = new javax.swing.JButton();
        placeOrderBtn1 = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        connect = new javax.swing.JMenuItem();
        disconnect = new javax.swing.JMenuItem();
        jSeparator = new javax.swing.JPopupMenu.Separator();
        exit = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Micro Trader");

        ordersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(ordersTable);

        placeOrderBtn.setText("Place Order");
        placeOrderBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                placeOrderBtnActionPerformed(evt);
            }
        });

        placeOrderBtn1.setText("Test");
        placeOrderBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                placeOrderBtn1ActionPerformed(evt);
            }
        });

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
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(placeOrderBtn1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(placeOrderBtn))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(placeOrderBtn)
                    .addComponent(placeOrderBtn1))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        setBounds(0, 0, 506, 322);
    }// </editor-fold>//GEN-END:initComponents

    private void connectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectActionPerformed
        ConnectForm form = new ConnectForm(this, true, clientComm);
        form.setVisible(true);
        if (clientComm.isConnected()) {
            nickname = form.getNickname();
            browseOrders();
        }
    }//GEN-LAST:event_connectActionPerformed

    private void disconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disconnectActionPerformed
        clientComm.disconnect();
        ordersTable.setModel(new DefaultTableModel());
    }//GEN-LAST:event_disconnectActionPerformed

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitActionPerformed

    private void placeOrderBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_placeOrderBtnActionPerformed
        if (clientComm.isConnected()) {
            PlaceOrderForm form = new PlaceOrderForm(this, true, clientComm, nickname);
            form.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "You must be connected to a server to place orders. \nNavigate to File > Connect.", "Warning", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_placeOrderBtnActionPerformed

    private void placeOrderBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_placeOrderBtn1ActionPerformed
        if (clientComm.isConnected()) {
            TestPlaceOrderForm form = new TestPlaceOrderForm(this, true, clientComm);
            form.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "You must be connected to a server to place orders. \nNavigate to File > Connect.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_placeOrderBtn1ActionPerformed

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

        MicroTraderClientUI.clientComm = clientComm;

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MicroTraderClientUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem connect;
    private javax.swing.JMenuItem disconnect;
    private javax.swing.JMenuItem exit;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JTable ordersTable;
    private javax.swing.JButton placeOrderBtn;
    private javax.swing.JButton placeOrderBtn1;
    // End of variables declaration//GEN-END:variables

    private void browseOrders() {
        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                while (clientComm.hasNextMessage()) {
                    ClientSideMessage message = clientComm.getNextMessage();

                    if (message == null) {
                        JOptionPane.showMessageDialog(null, "You have been disconnected from the server.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else if (message.getType() == ClientSideMessage.Type.ORDER) {
                        int index = -1;

                        for (Order order : orders) {
                            if (order.getServerOrderID() == message.getOrder().getServerOrderID()) {
                                index = orders.indexOf(order);
                            }
                        }

                        if (index != -1) {
                            if (message.getOrder().getNumberOfUnits() == 0) {
                                orders.remove(index);
                            } else {
                                orders.get(index).setNumberOfUnits(message.getOrder().getNumberOfUnits());
                            }
                        } else {
                            orders.add(message.getOrder());
                        }
                    }
                }

                ordersTable.setModel(new OrderTableModel(orders));
            }
        });
        timer.start();
    }
}
