package mt.main;

import java.util.logging.Level;
import java.util.logging.Logger;

import mt.client.MicroTraderClient;
import mt.client.impl.MicroTraderClientImpl;
import mt.comm.ClientComm;
import mt.comm.impl.ClientCommImpl;

public class StartClient {

    public static void main(String args[]) {
        ClientComm clientComm = new ClientCommImpl();
        MicroTraderClient client = new MicroTraderClientImpl();
        client.start(clientComm);
        Logger.getLogger(MicroTraderClientImpl.class.getName()).log(Level.INFO, "This is the end...");
    }

}
