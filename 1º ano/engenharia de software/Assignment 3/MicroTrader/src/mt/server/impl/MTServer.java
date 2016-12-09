package mt.server.impl;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import mt.Order;
import mt.comm.ServerComm;
import mt.comm.ServerSideMessage;
import mt.comm.impl.ServerCommImpl;
import mt.server.MicroTraderServer;

public class MTServer implements MicroTraderServer {

	private ServerComm serverComm;

	private MTServerConsole serverConsole;

	private ArrayDeque<String> connectedClients;

	private ArrayList<Order> buyOrders;

	private ArrayList<Order> sellOrders;

	private int orderId;

	public MTServer(ServerComm serverComm) {

		this.setServerComm(serverComm);
	}

	@Override
	public void start(ServerComm serverComm) {

		initFields();

		serverConsole = new MTServerConsole(this);

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

			waitForMessage();

		}
	}

	public void interpretMessage(ServerSideMessage message) {

		String client = null;

		if (message.getSenderNickname() != null && !message.getSenderNickname().isEmpty()) {
			client = message.getSenderNickname();
		}

		if (message.getType() != null) {

			if (message.getType().equals(ServerSideMessage.Type.CONNECTED)) {

				addConnectedClient(client);
			}

			else if (message.getType().equals(ServerSideMessage.Type.DISCONNECTED)) {

				removeConnectedClient(client);
			}

			else if (message.getType().equals(ServerSideMessage.Type.NEW_ORDER)) {

				if (message.getOrder() != null) {
					Order o = message.getOrder();

					readOrder(o);
				}
			}
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

		Iterator<Order> itBuyOrders = buyOrders.iterator();

		Iterator<Order> itSellOrders = sellOrders.iterator();

		Order order;

		while (itBuyOrders.hasNext()) {

			order = itBuyOrders.next();

			if (order.getNickname().equals(client)) {

				itBuyOrders.remove();
			}
		}

		while (itSellOrders.hasNext()) {

			order = itSellOrders.next();

			if (order.getNickname().equals(client)) {

				itSellOrders.remove();

			}
		}
	}

	/**
	 * Update all orders to a certain client.
	 * 
	 * @param order
	 */
	public void updateOrdersToClient(String client) {

		for (Order order : buyOrders) {

			serverComm.sendOrder(client, order);
		}

		for (Order order : sellOrders) {

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

			order.setServerOrderID(++orderId);
			sellOrders.add(order);

		}

		else if (order.isBuyOrder()) {

			order.setServerOrderID(++orderId);
			buyOrders.add(order);

		}

		System.out.println("SERVER - I'm passing here at least...");
		updateAllClientsOfOrder(order);

		checkForPairOrders(order);

	}

	/**
	 * This method contains the algorithm necessary to check all the orders that
	 * exist in the server and update them accordingly with the order received.
	 * 
	 * @param sellOrder
	 */
	public void checkForPairOrders(Order order) {

		Iterator<Order> itBuyOrders = buyOrders.iterator();

		Iterator<Order> itSellOrders = sellOrders.iterator();

		Order buyOrder;

		Order sellOrder;

		if (order.isSellOrder()) {

			sellOrder = order;

			// for (Order buyOrder : buyOrders) {
			while (itBuyOrders.hasNext()) {

				buyOrder = itBuyOrders.next();

				if (buyOrder.getStock().equals(sellOrder.getStock())
						&& buyOrder.getPricePerUnit() >= sellOrder.getPricePerUnit()) {

					// Sell order fulfilled scenario - More buy units than
					// sell
					// units.
					if (sellOrder.getNumberOfUnits() - buyOrder.getNumberOfUnits() <= 0) {

						// Update buy order in the server.
						buyOrder.setNumberOfUnits(buyOrder.getNumberOfUnits() - sellOrder.getNumberOfUnits());

						// Update sell order's number of units to zero.
						sellOrder.setNumberOfUnits(0);
						sellOrder.toString();
						// System.out.println("Sell order has been
						// fulfilled.");

						// The fact that the order has 0 number of units
						// tells
						// that she's been fulfilled.
						// Inform connected clients that the sell order has
						// been
						// fulfilled.
						updateAllClientsOfOrder(sellOrder);
						System.out.println(
								"Server >> Updated all clients of the fulfilled sell order. " + sellOrder.toString());

						// Remove the order from the server.
						removeOrder(sellOrder);

						// Update all clients of buy order
						updateAllClientsOfOrder(buyOrder);

						System.out.println(
								"Server >> Updated all clients of the updated buy order. " + buyOrder.toString());

						// Buy order is fulfilled too.
						if (buyOrder.getNumberOfUnits() == 0) {
							removeOrder(buyOrder);
						}

						return;

					}

					// Buy order fulfilled scenario - more units to sell
					// than
					// buy units.
					// We still need to check if more buy orders exist for
					// this
					// stock in the server.
					else if (sellOrder.getNumberOfUnits() - buyOrder.getNumberOfUnits() > 0) {

						sellOrder.setNumberOfUnits(sellOrder.getNumberOfUnits() - buyOrder.getNumberOfUnits());

						buyOrder.setNumberOfUnits(0);
						buyOrder.toString();
						// System.out.println("Buy order fulfilled");

						// Inform that the buyOrder is fulfilled
						updateAllClientsOfOrder(buyOrder);
						System.out.println(
								"Server >> Updated all clients of the fulfilled buy order. " + buyOrder.toString());
						// Remove buy order from server
						removeOrder(buyOrder);

						// Update all clients of the sell order
						updateAllClientsOfOrder(sellOrder);
						System.out.println(
								"Server >> Updated the client of the updated sell order. " + sellOrder.toString());

					}
				}
			}
		}

		else if (order.isBuyOrder()) {

			buyOrder = order;

			while (itSellOrders.hasNext()) {
				// for (Order sellOrder : sellOrders) {
				sellOrder = itSellOrders.next();

				if (sellOrder.getStock().equals(buyOrder.getStock())
						&& sellOrder.getPricePerUnit() <= buyOrder.getPricePerUnit()) {

					// Buy order fulfilled scenario - More sell units than
					// buy
					// units.
					if (buyOrder.getNumberOfUnits() - sellOrder.getNumberOfUnits() <= 0) {

						// Update sell order in the server.
						sellOrder.setNumberOfUnits(sellOrder.getNumberOfUnits() - buyOrder.getNumberOfUnits());

						// Update sell order's number of units to zero.
						buyOrder.setNumberOfUnits(0);
						buyOrder.toString();
						System.out.println("Buy order has been fulfilled.");

						// The fact that the order has 0 number of units
						// tells
						// that she's been fulfilled.
						// Inform connected clients that the buy order has
						// been
						// fulfilled.
						updateAllClientsOfOrder(buyOrder);
						System.out.println(
								"Server >> Updated all clients of the fulfilled buy order. " + buyOrder.toString());

						// Remove the order from the server.
						removeOrder(buyOrder);

						// Update all clients of the sell order
						updateAllClientsOfOrder(sellOrder);
						System.out.println("Server >> Updated all clients of the sell order. " + sellOrder.toString());

						if (sellOrder.getNumberOfUnits() == 0) {

							removeOrder(sellOrder);
						}

						return;

					}

					// Buy order fulfilled scenario - more units to sell
					// than
					// buy units.
					else if (buyOrder.getNumberOfUnits() - sellOrder.getNumberOfUnits() > 0) {

						buyOrder.setNumberOfUnits(buyOrder.getNumberOfUnits() - sellOrder.getNumberOfUnits());

						sellOrder.setNumberOfUnits(0);
						sellOrder.toString();
						// System.out.println("Sell order fulfilled");

						// Inform that the sellOrder is fulfilled
						updateAllClientsOfOrder(sellOrder);
						System.out.println(
								"Server >> Updated all clients of the fulfilled sell order." + sellOrder.toString());

						// Remove buy order from server
						removeOrder(sellOrder);

						// Update all clients of the updated buy order
						updateAllClientsOfOrder(buyOrder);
						System.out.println(
								"Server >> Updated the client of the updated buy order. " + buyOrder.toString());

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
	public void updateAllClientsOfOrder(Order order) {

		for (String s : connectedClients) {
			serverComm.sendOrder(s, order);
		}
	}

	public void waitForMessage() {

		ServerSideMessage message = (ServerSideMessage) serverComm.getNextMessage();
		/*
		 * A blocking queue should be implemented in the serverComm side. This
		 * method should block because it's trying to take a message from a
		 * blocking queue.
		 */

		if (message != null) {

			if (searchIfClientConnected(message.getSenderNickname())
					|| message.getType().equals(ServerSideMessage.Type.CONNECTED)) {

				interpretMessage(message);

			} else {

				serverComm.sendError(message.getSenderNickname(),
						"You are not conneted. Your order will not be processed.");

				System.out.println("The client is not connected. The order will not be processed.");
			}
		}
	}

	public void initFields() {

		connectedClients = new ArrayDeque<>();

		buyOrders = new ArrayList<Order>();

		sellOrders = new ArrayList<Order>();

		serverComm.start();
	}

	private void removeOrder(Order order) {

		if (order.isBuyOrder()) {

			Iterator<Order> itBuyOrder = buyOrders.iterator();

			loop: while (itBuyOrder.hasNext()) {

				if (itBuyOrder.next().getServerOrderID() == order.getServerOrderID()) {

					itBuyOrder.remove();
					break loop;
				}
			}
		}

		if (!order.isBuyOrder()) {

			Iterator<Order> itSellOrder = sellOrders.iterator();

			loop: while (itSellOrder.hasNext()) {

				if (itSellOrder.next().getServerOrderID() == order.getServerOrderID()) {

					itSellOrder.remove();
					break loop;
				}
			}
		}
	}

	/**
	 * Allows to shutdown the server.
	 */
	public void shutdown() {

		System.out.println("The server is closing...");
		serverConsole.interrupt();
		System.exit(0);
		System.out.println("The server is down.");
	}

	// Getters and Setters

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

	public ArrayList<Order> getBuyOrders() {
		return buyOrders;
	}

	public void setBuyOrders(ArrayList<Order> buyOrders) {
		this.buyOrders = buyOrders;
	}

	public ArrayList<Order> getSellOrders() {
		return sellOrders;
	}

	public void setSellOrders(ArrayList<Order> sellOrders) {
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
