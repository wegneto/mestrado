/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mt.client.controller;

import java.io.IOException;
import java.net.UnknownHostException;
import mt.Order;
import mt.client.ui.Session;

/**
 *
 * @author wegneto
 */
public class Controller {
    
    public void connect(String host, String nickname) throws UnknownHostException, IOException{
        try {
            Session.clientComm.connect(host, nickname);
            Session.loggedUser = nickname;
        } catch (UnknownHostException uhe) {
            throw new IOException(String.format("Host '%s' not found", host));
        } catch (IOException ex) {
            throw new IOException(String.format("Could not connect to the host %s: %s", host, ex.getMessage()));
        }
    }

    public void sendOrder(Order order) throws Exception {
        if (Session.clientComm.isConnected()) {
            Session.clientComm.sendOrder(order);
        } else {
            throw new Exception("You're not connected to any server.");
        }
        
    }
    
}
