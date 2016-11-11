package mt.dummy;

import mt.client.MicroTraderClient;
import mt.client.MicroTraderClientUI;

public class ClientMainDummy {

    public static void main(String[] args) {
        ClientCommDummy clientComm = new ClientCommDummy();
        MicroTraderClient client = new MicroTraderClientUI();
        client.start(clientComm);
    }
}
