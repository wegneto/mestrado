package mt.dummy;

import java.util.ArrayList;
import java.util.List;
import mt.Order;
import mt.comm.ClientComm;
import mt.comm.ClientSideMessage;

public class ClientCommDummy implements ClientComm {
    
    private static boolean isConnected = false;
       
    private static List<Order> buffer = new ArrayList<>();
    
    private static int serverOrderID = 1;

    @Override
    public void connect(String serverAddress, String nickname) {
        System.out.println("connecting " + nickname + " to server " + serverAddress);
        isConnected = true;
        createDummyData();
    }
    
    private void createDummyData() {
        for (int i = 1; i <= 5; i++) {
            if (i % 2 == 0) {
                this.sendOrder(Order.createBuyOrder("user " + i, "stock " + i, i, i));
            } else {
                this.sendOrder(Order.createSellOrder("user " + i, "stock " + i, i, i));
            }
        }
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
        return (buffer.size()) > 0;
    }

    @Override
    public ClientSideMessage getNextMessage() {
        if (hasNextMessage()) {
            ClientSideMessageDummy cms = new ClientSideMessageDummy(buffer.remove(0));
            return cms;
        } else { 
            return null;
        }
    }

    @Override
    public void sendOrder(Order order) {
        if (order.getServerOrderID() == 0) {
            order.setServerOrderID(serverOrderID);
            serverOrderID++;
        }
        
        buffer.add(order);
    }

}
