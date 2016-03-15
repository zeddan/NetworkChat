package client;

import client.interfaces.MessageListener;
import message.CommandMessage;
import message.Commands;
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
public class ClientConnection implements Runnable {

	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
    private MessageListener listener;
    private String username;
    private Thread thread = new Thread(this);

	public ClientConnection(String address, int port) {
		try {
            socket = new Socket(address,port);
			oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    public void start() {
        thread.start();
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

	public void sendMessage(Message message){
		try {
			oos.writeObject(message);
			oos.flush();
		} catch (IOException e) {
			System.out.println("Message could not be sent!");
		}
	}

    public void setUsername(String username) {
        this.username = username;
    }

    public void setListener(MessageListener listener) {
        this.listener = listener;
    }

    public void run(){
        try {
            CommandMessage cm = new CommandMessage(username, Commands.GET_CLIENTS_ONLINE);
            oos.writeObject(cm);
            oos.flush();
            Message message = (Message) ois.readObject();
            listener.update(message);
        } catch (IOException e) {
            System.out.println("The request not sent to server");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not typecast");
        }

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