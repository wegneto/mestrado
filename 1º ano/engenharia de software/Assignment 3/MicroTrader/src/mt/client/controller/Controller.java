package mt.client.controller;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import mt.Order;
import mt.client.Session;
import mt.comm.ClientSideMessage;

public class Controller {

	public void connect(String host, String nickname) throws UnknownHostException, IOException {
		try {
			Session.clientComm.connect(host, nickname);
			Session.loggedUser = nickname;
		} catch (UnknownHostException uhe) {
			throw new IOException(String.format("Host '%s' not found", host));
		} catch (IOException ex) {
			throw new IOException(String.format("Could not connect to the host %s: %s", host, ex.getMessage()));
		}
	}

	public boolean isConnected() {
		return !Session.loggedUser.isEmpty();
	}

	public void disconnect() {
		Session.loggedUser = "";
		Session.clientComm.disconnect();
	}

	public String getLoggedUser() {
		return Session.loggedUser;
	}

	public void sendOrder(Order order) throws Exception {
		if (Session.clientComm.isConnected()) {
			Session.clientComm.sendOrder(order);
		} else {
			throw new Exception("You're not connected to any server.");
		}
	}

	public void browseOrders() throws Exception {
		while (Session.clientComm.hasNextMessage()) {
			Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Processing new messages");
			ClientSideMessage message = Session.clientComm.getNextMessage();
			Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Message received " + message);
			if (message == null) {
				throw new Exception("You have been disconnected from the server.");
			} else if (message.getType() == ClientSideMessage.Type.ORDER) {
				int index = -1;

				for (Order order : Session.orders) {
					if (order.getServerOrderID() == message.getOrder().getServerOrderID()) {
						Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Order found:" + order);
						index = Session.orders.indexOf(order);
					}
				}

				if (index != -1) {
					if (message.getOrder().getNumberOfUnits() == 0) {
						Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Order fullfiled:" + message.getOrder());
						Session.orders.remove(index);
					} else {
						Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Order updated:" + message.getOrder());
						Session.orders.get(index).setNumberOfUnits(message.getOrder().getNumberOfUnits());
					}
				} else if (message.getOrder().getNumberOfUnits() != 0) {
					Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Order added:" + message.getOrder());
					Session.orders.add(message.getOrder());
					if (message.getOrder().getNickname().equalsIgnoreCase(Session.loggedUser)) {
						Session.history.add(message.getOrder());
					}
				}
			}
		}
	}

}