package mt.server.impl;

import mt.Order;
import mt.comm.ClientSideMessage;
import mt.comm.ServerSideMessage.Type;

public class ToClientMessage implements ClientSideMessage {
	
	private Type type;
	private String nickname;
	private Order order;
	private String error;
	
	public ToClientMessage(Type type, String nickname, Order order) {
		
		this.type = type;
		this.nickname = nickname;
		this.order = order;
		
	}
	
	public ToClientMessage(Type type, String nickname, String error) {
		
		this.type = type;
		this.nickname = nickname;
		this.error = error;	
	}

	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return this.type;
	}

	@Override
	public Order getOrder() {
		// TODO Auto-generated method stub
		return this.order;
	}

	@Override
	public String getError() {
		// TODO Auto-generated method stub
		return this.error;
	}
	
	
	
	

}
