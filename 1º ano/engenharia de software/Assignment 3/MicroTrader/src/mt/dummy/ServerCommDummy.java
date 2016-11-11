package mt.dummy;

import mt.Order;
import mt.comm.ServerComm;
import mt.comm.ServerSideMessage;

public class ServerCommDummy implements ServerComm {
	
	
	@Override
	public void start() {
		
	}

	@Override
	public boolean hasNextMessage() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ServerSideMessage getNextMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendError(String toNickname, String error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean clientIsConnected(String nickname) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void disconnectClient(String nickname) {
		// TODO Auto-generated method stub
	}

	@Override
	public void sendOrder(String receiversNickname, Order order) {
		// TODO Auto-generated method stub
		
	}

}
