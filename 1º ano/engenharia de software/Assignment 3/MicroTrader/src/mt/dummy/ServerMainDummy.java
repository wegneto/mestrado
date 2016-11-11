package mt.dummy;

import mt.server.MicroTraderServer;

public class ServerMainDummy {	
	public static void main(String[] args) {
		ServerCommDummy serverComm = new ServerCommDummy();
		MicroTraderServer  server     = new MicroTraderServerDummy();
		server.start(serverComm);
	}
}
