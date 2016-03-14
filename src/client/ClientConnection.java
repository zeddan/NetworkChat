package client;

import message.CommandMessage;
import message.DataMessage;
import message.Message;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Christoffer Strandberg
 */

// ta emot lista med klienter som är online
//cmd-meddelande hej vi är här ge lista med klienter
//Se till att klienten läggs till vid anslutning. Via commandMessage
//Och sedan uppdatera klienter
//Skapa grupp med de klienter som finns i meddelandet (recipients)
public class ClientConnection {

    private static final String CMD_GET_CLIENTS = "Get clients";
    private static final String[] RCP_SERVER = new String[] {"Server"};
    private static final String SNDR_CLIENT = "Client";
	
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private ServerConnection serverConnection;

	public ClientConnection(String address,
                            int port,
                            MessageListener messageListener){
		try {
            socket = new Socket(address,port);
			oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			serverConnection = new ServerConnection(ois, messageListener);
		} catch (IOException e) {
			e.printStackTrace();
		}
		getOnlineClients();
	}

	public void close(){
		try {
			socket.close();
			ois.close();
			oos.close();
		} catch (IOException e) {
			System.err.println("Socket not closed!");
		}
	}
	
    public void startListener(){
        serverConnection.start();
    }
	
	public void sendMessage(Message message){
		try {
			oos.writeObject(message);
			oos.flush();
		} catch (IOException e) {
			System.out.println("Message could not be sent!");
		}
	}
	
	public String[] getOnlineClients(){
        String[] onlineClients = {};
        CommandMessage getClientsCommand = new CommandMessage(SNDR_CLIENT, RCP_SERVER, CMD_GET_CLIENTS);
		try {
			oos.writeObject(getClientsCommand);
			oos.flush();
			DataMessage tempMes = (DataMessage) ois.readObject();
			onlineClients = tempMes.getData();
		} catch (IOException e) {
			System.out.println("The request not sent to server");
		} catch (ClassNotFoundException e) {
			System.out.println("Could not typecast");
		}
        return onlineClients;
	}
	
	private class ServerConnection extends Thread{
		private ObjectInputStream ois;
		private MessageListener listener;
		
		public ServerConnection(ObjectInputStream ois,
                                MessageListener listener){
			this.ois = ois;
			this.listener = listener;
		}
		
		public void run(){
			while(true){
				try {
                    Message message = (Message) ois.readObject();
					listener.update(message);
					System.out.println("Callback made");
				} catch (ClassNotFoundException | IOException e) {
					System.out.println("Could not cast to Message");
				}
			}
		}
	}
}