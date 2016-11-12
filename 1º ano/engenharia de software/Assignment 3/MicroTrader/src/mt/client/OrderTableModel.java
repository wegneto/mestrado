/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mt.client;

import java.util.LinkedList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import mt.Order;

/**
 *
 * @author wegneto
 */
public class OrderTableModel extends DefaultTableModel {
    
    public OrderTableModel(List<Order> orders) {
        addColumn("Nickname");
        addColumn("Stock");
        addColumn("Units");
        addColumn("Price");
        addColumn("Type");
        
        for (Order order : orders) {
            List<Object> row = new LinkedList<>();

            row.add(order.getNickname());
            row.add(order.getStock());
            row.add(order.getNumberOfUnits());
            row.add(order.getPricePerUnit());
            row.add(order.isBuyOrder() ? "Buy" : "Sell");
            
            addRow(row.toArray());
        }
        
    }

}
