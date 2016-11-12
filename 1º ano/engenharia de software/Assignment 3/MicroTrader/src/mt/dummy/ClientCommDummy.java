package mt.dummy;

import java.util.ArrayList;
import java.util.List;
import mt.Order;
import mt.comm.ClientComm;
import mt.comm.ClientSideMessage;

public class ClientCommDummy implements ClientComm {
    
    private static boolean isConnected = false;
    
    private static List<Order> orders = new ArrayList<>();

    @Override
    public void connect(String serverAddress, String nickname) {
        System.out.println("connecting " + nickname + " to server " + serverAddress);
        isConnected = true;
    }

    @Override
    public boolean isConnected() {
        return isConnected;
    }

    @Override
    public void disconnect() {
        System.out.println("disconnecting...");
        isConnected = false;
    }

    @Override
    public boolean hasNextMessage() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public ClientSideMessage getNextMessage() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void sendOrder(Order order) {
        orders.add(order);
    }

}
