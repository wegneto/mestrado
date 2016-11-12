package mt.dummy;

import mt.Order;
import mt.comm.ClientComm;
import mt.comm.ClientSideMessage;

public class ClientCommDummy implements ClientComm {

    @Override
    public void connect(String serverAddress, String nickname) {
        System.out.println("connecting " + nickname + " to server " + serverAddress);
    }

    @Override
    public boolean isConnected() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void disconnect() {
        System.out.println("disconnecting...");
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
        // TODO Auto-generated method stub

    }

}
