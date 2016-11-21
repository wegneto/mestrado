/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mt.client.controller;

import java.io.IOException;
import java.net.UnknownHostException;
import mt.Order;
import mt.client.Session;
import mt.comm.ClientSideMessage;

/**
 *
 * @author wegneto
 */
public class Controller {

    public void connect(String host, String nickname) throws UnknownHostException, IOException {
        try {
            Session.clientComm.connect(host, nickname);
            Session.loggedUser = nickname;
        } catch (UnknownHostException uhe) {
            throw new IOException(String.format("Host '%s' not found", host));
        } catch (IOException ex) {
            throw new IOException(String.format("Could not connect to the host %s: %s", host, ex.getMessage()));
        }
    }

    public boolean isConnected() {
        return !Session.loggedUser.isEmpty();
    }

    public void disconnect() {
        Session.loggedUser = "";
        Session.clientComm.disconnect();
    }
    
    public String getLoggedUser() {
        return Session.loggedUser;
    }

    public void sendOrder(Order order) throws Exception {
        if (Session.clientComm.isConnected()) {
            Session.clientComm.sendOrder(order);
        } else {
            throw new Exception("You're not connected to any server.");
        }
    }

    public void browseOrders() throws Exception {
        while (Session.clientComm.hasNextMessage()) {
            System.out.println("Controller >> Processing new messages... ");
            ClientSideMessage message = Session.clientComm.getNextMessage();
            System.out.println("Controller >> Message received " + message);
            if (message == null) {
                throw new Exception("You have been disconnected from the server.");
            } else if (message.getType() == ClientSideMessage.Type.ORDER) {
                int index = -1;

                for (Order order : Session.orders) {
                    if (order.getServerOrderID() == message.getOrder().getServerOrderID()) {
                        System.out.println("Controller >> Order found:" + order);
                        index = Session.orders.indexOf(order);
                    }
                }

                if (index != -1) {
                    if (message.getOrder().getNumberOfUnits() == 0) {
                        System.out.println("Controller >> Order fullfiled:" + message.getOrder());
                        Session.orders.remove(index);
                    } else {
                        System.out.println("Controller >> Order updated:" + message.getOrder());
                        Session.orders.get(index).setNumberOfUnits(message.getOrder().getNumberOfUnits());
                    }
                } else if (message.getOrder().getNumberOfUnits() != 0) {
                    System.out.println("Controller >> Order added:" + message.getOrder());
                    Session.orders.add(message.getOrder());
                    if (message.getOrder().getNickname().equalsIgnoreCase(Session.loggedUser)) {
                        Session.history.add(message.getOrder());
                    }
                }
            }
        }
    }

}
