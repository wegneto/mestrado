package mt.server.impl;

import mt.Order;
import mt.comm.ServerSideMessage;

public class ToServerMessage implements ServerSideMessage {
	
	private Type type;
	private String nickname;
	private Order order;
	
	public ToServerMessage(Type type, String nickname, Order order) {
		
		this.type = type;
		this.nickname = nickname;
		this.order = order;
		
	}

	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return type;
	}

	@Override
	public String getSenderNickname() {
		// TODO Auto-generated method stub
		return nickname;
	}

	@Override
	public Order getOrder() {
		// TODO Auto-generated method stub
		return order;
	}

}
