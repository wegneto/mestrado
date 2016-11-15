/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mt.dummy;

import mt.Order;
import mt.comm.ClientSideMessage;

/**
 *
 * @author wegneto
 */
public class ClientSideMessageDummy implements ClientSideMessage {
    
    private Type type;
    
    private Order order;
    
    public ClientSideMessageDummy(Order order) {
        this.type = Type.ORDER;
        this.order = order;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public Order getOrder() {
        return order;
    }

    @Override
    public String getError() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
