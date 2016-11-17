package mt.server.impl;

import java.util.Scanner;


public class MTServerConsole extends Thread {
	
	MTServer server;
	Long born;
	
	
	public MTServerConsole(MTServer server) {
		
		born = System.currentTimeMillis();
		
		this.server = server;
		
	}
	
	
	public void run() {
		
		System.out.println("Hello! ");
		
		
		while (!isInterrupted()) {
			
			System.out.println("I'm the server console! \n \n My available commands are: \n" + showCommands());
			System.out.println("\n\nChoose a command!\n" );
		
		Scanner s = new Scanner(System.in);
				
			int j = s.nextInt();
			
			if (j == 1) {
				
				System.out.println("\nYou chose to list Online Users! Here they are: ");
				
				/*for (String onlineUsers : server.getOnlineUsers()) {
					
					System.out.println(onlineUsers);
				}*/
				
				System.out.println("\n\n");
			}
			
			if (j == 2) {
				
				System.out.println("\nYou chose to see the messages waiting to be sent! Here they are: ");
				
				/*LinkedList<PendentMessage> pendentMessages = server.getPendentMessagesQueue();
				
				for (PendentMessage m : pendentMessages) {
					
					System.out.println("Message from: " + m.getMsg().getOrigin() + " to " + m.getMsg().getDestiny() + " with the text (" + m.getMsg().getText() +")");
							
				} */
			} 
			
			if (j == 3) {
				
				System.out.println("The server is online for " + (System.currentTimeMillis() - born));
				
			}
			
			if (j == 4) {
				
				server.shutdown();
			}
			
			
		}
	
		
	}
		
public String showCommands() {
			
			String s = "1 - List online users \n2 - List message queue \n3 - Show info about the server \n4 - Shut down the server";
			
			return s;
			
			
			
			
			
		}


}
