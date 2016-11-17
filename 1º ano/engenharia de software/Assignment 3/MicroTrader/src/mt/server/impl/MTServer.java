package mt.server.impl;

import java.util.ArrayDeque;
import java.util.HashMap;
import mt.Order;
import mt.comm.ServerComm;
import mt.comm.ServerSideMessage;
import mt.comm.impl.ServerCommImpl;
import mt.server.MicroTraderServer;

public class MTServer implements MicroTraderServer {

	private ServerComm serverComm;

	private ArrayDeque<String> connectedClients;

	private HashMap<Integer, Order> buyOrders;

	private HashMap<Integer, Order> sellOrders;

	private int orderId;

	public MTServer(ServerComm serverComm) {

		this.setServerComm(serverComm);
	}

	@Override
	public void start(ServerComm serverComm) {
		
		connectedClients = new ArrayDeque<>();

		buyOrders = new HashMap<Integer, Order>();

		sellOrders = new HashMap<Integer, Order>();

		serverComm.start();

		MTServerConsole serverConsole = new MTServerConsole(this);

		// Server console to allow interaction with the server
		serverConsole.start();

		// Waits server console to start
		try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (true) {

			ServerSideMessage message = (ServerSideMessage) serverComm.getNextMessage();
			/*
			 * A blocking queue should be implemented in the serverComm side.
			 * This method should block because it's trying to take a message
			 * from a blocking queue.
			 */

			if (searchIfClientConnected(message.getSenderNickname()) || message.getType().equals(ServerSideMessage.Type.CONNECTED)) {

				interpretMessage(message);

			} else {

				serverComm.sendError(message.getSenderNickname(),
						"You are not conneted. Your order will not be processed.");
				
				System.out.println("The client is not connected. The order will not be processed.");
			}
		}

	}

	public void interpretMessage(ServerSideMessage message) {

		String client = message.getSenderNickname();

		if (message.getType().equals(ServerSideMessage.Type.CONNECTED)) {

			addConnectedClient(client);
		}

		else if (message.getType().equals(ServerSideMessage.Type.DISCONNECTED)) {

			removeConnectedClient(client);
		}

		else if (message.getType().equals(ServerSideMessage.Type.NEW_ORDER)) {

			Order o = message.getOrder();

			readOrder(o);
		}
	}
	
	/**
	 * Searches if a client is connected at the moment.
	 */
	
	public boolean searchIfClientConnected(String clientChecked) {
		
		for (String client : connectedClients) {
			
			if (client.equals(clientChecked)) {
				
				return true;
			}
		}
		
		return false;
	}

	/**
	 * 
	 * @param client
	 */
	public void addConnectedClient(String client) {

		if (!connectedClients.contains(client)) {
			connectedClients.add(client);
			System.out.println("Client " + client + " connected.");

			updateOrdersToClient(client);
		}

	}

	/**
	 * Remove connected client and respective orders.
	 * 
	 * @param client
	 */
	public void removeConnectedClient(String client) {

		if (connectedClients.contains(client)) {
			connectedClients.remove(client);
			System.out.println("Client " + client + " disconnected.");

			removeOrdersFromClient(client);

		}

	}

	/**
	 * Remove all orders from certain client.
	 * 
	 * @param client
	 */
	public void removeOrdersFromClient(String client) {

		for (Order order : buyOrders.values()) {

			if (order.getNickname().equals(client)) {

				buyOrders.remove(order);
			}
		}

		for (Order order : sellOrders.values()) {

			if (order.getNickname().equals(client)) {

				sellOrders.remove(order);
			}
		}
	}

	/**
	 * Update all orders to a certain client.
	 * 
	 * @param order
	 */
	public void updateOrdersToClient(String client) {

		for (Order order : buyOrders.values()) {

			serverComm.sendOrder(client, order);
		}

		for (Order order : sellOrders.values()) {

			serverComm.sendOrder(client, order);
		}

	}

	/**
	 * Interprets orders from clients.
	 * 
	 * @param order
	 */
	public void readOrder(Order order) {

		if (order.isSellOrder()) {

			sellOrders.put(orderId++, order);

		}

		else if (order.isBuyOrder()) {

			buyOrders.put(orderId++, order);

		}

		checkForPairOrders(order);

	}

	/**
	 * This method contains the algorithm necessary to check all the orders that
	 * exist in the server and update them accordingly with the order received.
	 * 
	 * @param sellOrder
	 */
	public void checkForPairOrders(Order order) {

		if (order.isSellOrder()) {
			Order sellOrder = order;

			for (Order buyOrder : buyOrders.values()) {

				if (buyOrder.getStock().equals(sellOrder.getStock())
						&& buyOrder.getPricePerUnit() >= sellOrder.getPricePerUnit()) {

					// Sell order fulfilled scenario - More buy units than sell
					// units.
					if (sellOrder.getNumberOfUnits() - buyOrder.getNumberOfUnits() <= 0) {

						// Update buy order in the server.
						buyOrder.setNumberOfUnits(buyOrder.getNumberOfUnits() - sellOrder.getNumberOfUnits());

						// Update sell order's number of units to zero.
						sellOrder.setNumberOfUnits(0);
						sellOrder.toString();
						System.out.println("Sell order has been fulfilled.");

						// The fact that the order has 0 number of units tells
						// that she's been fulfilled.
						// Inform connected clients that the sell order has been
						// fulfilled.
						informClientsOfOrder(sellOrder);

						// Remove the order from the server.
						sellOrders.remove(sellOrder);

						// Buy order is fulfilled too.
						if (buyOrder.getNumberOfUnits() == 0) {
							informClientsOfOrder(buyOrder);
							buyOrder.toString();
							System.out.println("Buy order has been fulfilled");
							buyOrders.remove(buyOrder);

						}
						return;

					}

					// Buy order fulfilled scenario - more units to sell than
					// buy units.
					// We still need to check if more buy orders exist for this
					// stock in the server.
					else if (sellOrder.getNumberOfUnits() - buyOrder.getNumberOfUnits() > 0) {

						sellOrder.setNumberOfUnits(buyOrder.getNumberOfUnits() - sellOrder.getNumberOfUnits());

						buyOrder.setNumberOfUnits(0);
						buyOrder.toString();
						System.out.println("Buy order fulfilled");

						// Inform that the buyOrder is fulfilled
						informClientsOfOrder(buyOrder);

						// Remove buy order from server
						buyOrders.remove(buyOrder);

					}
				}
			}
		}

		else if (order.isBuyOrder()) {

			Order buyOrder = order;

			for (Order sellOrder : sellOrders.values()) {

				if (sellOrder.getStock().equals(buyOrder.getStock())
						&& sellOrder.getPricePerUnit() >= buyOrder.getPricePerUnit()) {

					// Buy order fulfilled scenario - More sell units than buy
					// units.
					if (buyOrder.getNumberOfUnits() - sellOrder.getNumberOfUnits() <= 0) {

						// Update sell order in the server.
						sellOrder.setNumberOfUnits(sellOrder.getNumberOfUnits() - buyOrder.getNumberOfUnits());

						// Update sell order's number of units to zero.
						buyOrder.setNumberOfUnits(0);
						buyOrder.toString();
						System.out.println("Buy order has been fulfilled.");

						// The fact that the order has 0 number of units tells
						// that she's been fulfilled.
						// Inform connected clients that the buy order has been
						// fulfilled.
						informClientsOfOrder(buyOrder);

						// Remove the order from the server.
						buyOrders.remove(buyOrder);

						// Buy order is fulfilled too.
						if (sellOrder.getNumberOfUnits() == 0) {
							informClientsOfOrder(sellOrder);
							sellOrders.remove(sellOrder);
							sellOrder.toString();
							System.out.println("Sel order has been fulfilled");

						}
						return;

					}

					// Buy order fulfilled scenario - more units to sell than
					// buy units.
					else if (buyOrder.getNumberOfUnits() - sellOrder.getNumberOfUnits() > 0) {

						buyOrder.setNumberOfUnits(buyOrder.getNumberOfUnits() - sellOrder.getNumberOfUnits());

						sellOrder.setNumberOfUnits(0);
						sellOrder.toString();
						System.out.println("Sell order fulfilled");

						// Inform that the buyOrder is fulfilled
						informClientsOfOrder(sellOrder);

						// Remove buy order from server
						sellOrders.remove(sellOrder);

					}
				}
			}
		}
	}

	/**
	 * Informs clients of an order that was just received or fulfilled in the
	 * server.
	 * 
	 * @param order
	 */
	public void informClientsOfOrder(Order order) {

		for (String s : connectedClients) {
			serverComm.sendOrder(order.getNickname(), order);
		}
	}

	/**
	 * Allows to shutdown the server.
	 */
	public void shutdown() {

		System.out.println("The server is closing...");
		System.exit(0);
		System.out.println("The server is down.");
	}

	public ServerComm getServerComm() {
		return serverComm;
	}

	public void setServerComm(ServerComm serverComm) {
		this.serverComm = serverComm;
	}

	public ArrayDeque<String> getConnectedClients() {
		return connectedClients;
	}

	public void setConnectedClients(ArrayDeque<String> connectedClients) {
		this.connectedClients = connectedClients;
	}

	public HashMap<Integer, Order> getBuyOrders() {
		return buyOrders;
	}

	public void setBuyOrders(HashMap<Integer, Order> buyOrders) {
		this.buyOrders = buyOrders;
	}

	public HashMap<Integer, Order> getSellOrders() {
		return sellOrders;
	}

	public void setSellOrders(HashMap<Integer, Order> sellOrders) {
		this.sellOrders = sellOrders;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public static void main(String[] args) {

		ServerCommImpl serverCommCreated = new ServerCommImpl();

		MTServer mtServer = new MTServer(serverCommCreated);
		mtServer.start(serverCommCreated);

	}

}
