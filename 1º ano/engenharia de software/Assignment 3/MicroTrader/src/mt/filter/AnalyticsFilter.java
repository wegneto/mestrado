package mt.filter;

import mt.Order;
import mt.comm.ServerComm;
import mt.comm.ServerSideMessage;

public class AnalyticsFilter implements ServerComm {

	private ServerComm decoratedServerComm;
	
	public AnalyticsFilter(ServerComm serverCommToDecorate) {
		decoratedServerComm = serverCommToDecorate;
	}
	
	@Override
	public void start() {
		decoratedServerComm.start();	
	}

	@Override
	public ServerSideMessage getNextMessage() {
		ServerSideMessage message = decoratedServerComm.getNextMessage();
		 
		//TODO: add filter code here
	
		return message;	
	}

	@Override
	public boolean hasNextMessage() {
		return decoratedServerComm.hasNextMessage();
	}

	@Override
	public void sendOrder(String receiversNickname, Order order) {
		decoratedServerComm.sendOrder(receiversNickname, order);
	}

	@Override
	public void sendError(String toNickname, String error) {
		decoratedServerComm.sendError(toNickname, error);
	}

	@Override
	public boolean clientIsConnected(String nickname) {
		return decoratedServerComm.clientIsConnected(nickname);
	}

	@Override
	public void disconnectClient(String nickname) {
		decoratedServerComm.disconnectClient(nickname);
	}
}
