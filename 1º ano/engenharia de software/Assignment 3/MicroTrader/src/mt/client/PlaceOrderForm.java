/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mt.client;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.text.InternationalFormatter;
import javax.swing.text.NumberFormatter;
import mt.Order;
import mt.comm.ClientComm;

/**
 *
 * @author wegneto
 */
public class PlaceOrderForm extends javax.swing.JDialog {

    private final ClientComm clientComm;

    /**
     * Creates new form PlaceOrderForm
     */
    public PlaceOrderForm(java.awt.Frame parent, boolean modal, ClientComm clientComm) {
        super(parent, modal);
        initComponents();
        this.clientComm = clientComm;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        nicknameLabel = new javax.swing.JLabel();
        stockLabel = new javax.swing.JLabel();
        numberLabel = new javax.swing.JLabel();
        priceLabel = new javax.swing.JLabel();
        operationLabel = new javax.swing.JLabel();
        nicknameTxt = new javax.swing.JTextField();
        stockTxt = new javax.swing.JTextField();
        buyRdBtn = new javax.swing.JRadioButton();
        sellRdBtn = new javax.swing.JRadioButton();
        numberOfUnitsTxt = new javax.swing.JTextField();
        pricePerUnitTxt = new javax.swing.JTextField();
        cancelBtn = new javax.swing.JButton();
        okBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Place Order");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        nicknameLabel.setText("Nickname:");

        stockLabel.setText("Stock:");

        numberLabel.setText("Number of units:");

        priceLabel.setText("Price per unit:");

        operationLabel.setText("Operation:");

        nicknameTxt.setSize(new java.awt.Dimension(60, 26));

        buttonGroup2.add(buyRdBtn);
        buyRdBtn.setText("Buy");

        buttonGroup2.add(sellRdBtn);
        sellRdBtn.setText("Sell");

        numberOfUnitsTxt.setColumns(5);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(nicknameLabel)
                            .addComponent(numberLabel)
                            .addComponent(stockLabel)
                            .addComponent(priceLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(nicknameTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                                .addComponent(stockTxt))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(pricePerUnitTxt, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(numberOfUnitsTxt, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(operationLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buyRdBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sellRdBtn)))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nicknameLabel)
                    .addComponent(nicknameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stockLabel)
                    .addComponent(stockTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numberLabel)
                    .addComponent(numberOfUnitsTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(priceLabel)
                    .addComponent(pricePerUnitTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(operationLabel)
                    .addComponent(buyRdBtn)
                    .addComponent(sellRdBtn)))
        );

        cancelBtn.setText("Cancel");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        okBtn.setText("Ok");
        okBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(okBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelBtn, okBtn});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelBtn)
                    .addComponent(okBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void okBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okBtnActionPerformed
        if (clientComm.isConnected()) {
            String message = "";

            if (nicknameTxt.getText().isEmpty()) {
                message = "Nickname must be provided.";
            }

            if (stockTxt.getText().isEmpty()) {
                message = (message.isEmpty() ? "" : message + "\n") + "Stock must be provided.";
            }

            if (numberOfUnitsTxt.getText().isEmpty()) {
                message = (message.isEmpty() ? "" : message + "\n") + "Number of units must be provided.";
            } else {
                try {
                    long numberOfUnits = Long.valueOf(numberOfUnitsTxt.getText());
                    if (numberOfUnits <= 0) {
                        message = (message.isEmpty() ? "" : message + "\n") + "Number of units must be greater than 0.";
                    } else if (numberOfUnits > Integer.MAX_VALUE) {
                        message = (message.isEmpty() ? "" : message + "\n") + "Number of units must be less than " + Integer.MAX_VALUE + ".";
                    }
                } catch (NumberFormatException e) {
                    message = (message.isEmpty() ? "" : message + "\n") + "Number of units must be a number";
                }
            }

            if (pricePerUnitTxt.getText().isEmpty()) {
                message = (message.isEmpty() ? "" : message + "\n") + "Price per unit must be provided.";
            } else {
                try {
                    double pricePerUnit = Double.valueOf(pricePerUnitTxt.getText());
                    if (pricePerUnit <= 0) {
                        message = (message.isEmpty() ? "" : message + "\n") + "Price per unit must be greater than 0.";
                    } else if (pricePerUnit > Double.MAX_VALUE) {
                        message = (message.isEmpty() ? "" : message + "\n") + "Price per unit must be less than " + Integer.MAX_VALUE + ".";
                    }
                } catch (NumberFormatException e) {
                    message = (message.isEmpty() ? "" : message + "\n") + "Price per unit must be a number";
                }
            }

            if ((!buyRdBtn.isSelected() && (!sellRdBtn.isSelected()))) {
                message = (message.isEmpty() ? "" : message + "\n") + "Operation must be provided.";
            }

            if (!message.isEmpty()) {
                JOptionPane.showMessageDialog(this, message, "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Order order = null;
            String nickname = nicknameTxt.getText();
            String stock = stockTxt.getText();
            int numberOfUnits = Integer.valueOf(numberOfUnitsTxt.getText());
            double pricePerUnit = Double.valueOf(pricePerUnitTxt.getText());

            if (buyRdBtn.isSelected()) {
                order = Order.createBuyOrder(nickname, stock, numberOfUnits, pricePerUnit);
            } else if (sellRdBtn.isSelected()) {
                order = Order.createSellOrder(nickname, stock, numberOfUnits, pricePerUnit);
            }

            System.out.println("->" + order);

            clientComm.sendOrder(order);
            this.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this, "You're not connected to any server.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_okBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JRadioButton buyRdBtn;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel nicknameLabel;
    private javax.swing.JTextField nicknameTxt;
    private javax.swing.JLabel numberLabel;
    private javax.swing.JTextField numberOfUnitsTxt;
    private javax.swing.JButton okBtn;
    private javax.swing.JLabel operationLabel;
    private javax.swing.JLabel priceLabel;
    private javax.swing.JTextField pricePerUnitTxt;
    private javax.swing.JRadioButton sellRdBtn;
    private javax.swing.JLabel stockLabel;
    private javax.swing.JTextField stockTxt;
    // End of variables declaration//GEN-END:variables

}
