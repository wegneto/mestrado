package mt.comm.impl;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import mt.Order;
import mt.comm.ClientComm;
import mt.comm.ClientSideMessage;
import mt.comm.common.CommUtils;
import mt.comm.socket.ClientCommSocket;

public class ClientCommImpl implements ClientComm {
	/**
	 * Class responsible for handling sockets and streams.
	 */
	private ClientCommSocket clientSocket;

	/**
	 * Stores the messages received from server.
	 */
	private BlockingQueue<ClientSideMessage> clientMessages;

	/**
	 * Default constructor.
	 */
	public ClientCommImpl() {
		this.clientMessages = new ArrayBlockingQueue<>(CommUtils.BLOCKING_QUEUE_CAPACITY);
	}

	@Override
	public void connect(String host, String nickname) throws UnknownHostException, IOException {
		clientSocket = new ClientCommSocket(host, nickname, clientMessages);
		clientSocket.connect();
	}

	@Override
	public boolean isConnected() {
		return clientSocket.isConnected();
	}

	@Override
	public void disconnect() {
		clientSocket.disconnect();
	}

	@Override
	public ClientSideMessage getNextMessage() {
		ClientSideMessage nextMessage = null;
		if(!isConnected()){
			System.out.println("ClientComm >> The client is no longer connected, so a null client message is sent to inform GUI.");
			return null;
		}
		
		try {
			nextMessage = clientMessages.take();
		} catch (InterruptedException e) {
			System.out.println("ClientComm >> An error has thrown while taking a client message due to: " + CommUtils.getCause(e));
		}
		return nextMessage;
	}

	@Override
	public boolean hasNextMessage() {
		return clientMessages.peek() != null;
	}

	@Override
	public void sendOrder(Order order) {
		clientSocket.sendOrder(order);
	}
}
