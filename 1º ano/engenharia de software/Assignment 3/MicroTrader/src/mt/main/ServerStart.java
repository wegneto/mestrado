package mt.main;

import mt.comm.impl.ServerCommImpl;
import mt.server.impl.MTServer;

public class ServerStart {

	public static void main(String[] args) {
		ServerCommImpl serverCommCreated = new ServerCommImpl();
		MTServer mtServer = new MTServer(serverCommCreated);
		mtServer.start(serverCommCreated);
	}

}
