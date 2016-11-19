package mt.dummy;

import mt.client.MicroTraderClient;
import mt.client.ui.MicroTraderClientUI;
import mt.comm.ClientComm;
import mt.comm.impl.ClientCommImpl;

public class ClientMainDummy {

    public static void main(String[] args) {
        ClientComm clientComm = new ClientCommImpl();
        //ClientComm clientComm = new ClientCommDummy();
        MicroTraderClient client = new MicroTraderClientUI();
        client.start(clientComm);
    }
}
