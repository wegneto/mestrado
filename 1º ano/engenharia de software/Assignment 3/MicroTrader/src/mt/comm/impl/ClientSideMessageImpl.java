package mt.comm.impl;

import java.io.Serializable;

import mt.Order;
import mt.comm.ClientSideMessage;
import mt.comm.common.CommUtils;

public class ClientSideMessageImpl implements ClientSideMessage, Serializable {
	/**
	 * Default serial version id.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Message type.
	 */
	private Type type;

	/**
	 * Order (sell/buy)
	 */
	private Order order;

	/**
	 * Eroor message.
	 */
	private String error;

	/**
	 * Constructor.
	 * 
	 * @param type
	 *            Message type
	 * @param order
	 *            Order to be sent.
	 */
	public ClientSideMessageImpl(Type type, Order order) {
		this.type = type;
		this.order = order;
	}

	/**
	 * Constructor.
	 * 
	 * @param type
	 *            Message Type.
	 * @param error
	 *            Error message.
	 */
	public ClientSideMessageImpl(Type type, String error) {
		this.type = type;
		this.error = error;
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public Order getOrder() {
		return order;
	}

	@Override
	public String getError() {
		return error;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ClientSideMessage: [Type: ").append(type.toString()).append("] ");
		if (!CommUtils.isEmpty(error)) {
			sb.append("[Message: ").append(error).append("] ");
		}
		if (order != null) {
			sb.append("[Order: ").append(order).append("] ");
		}
		return sb.toString();
	}
}
