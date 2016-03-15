package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import message.ChatMessage;
import message.CommandMessage;
import message.Commands;
import message.DataMessage;

/**
 * Del av gruppuppgift DA343A
 * @author Gustav Frigren
 *
 */
public class User implements Runnable {
	
	private String userName;
	
	private Controller controller;
	
	private boolean active = true;
	
	private Socket socket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	
	public User(Controller controller, Socket socket) {
		this.controller = controller;
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStream = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		while(socket != null && socket.isConnected() && active == true) {
			try {
				Object object = inputStream.readObject();
				if(object instanceof CommandMessage) {
					CommandMessage cm = (CommandMessage) object;
					int type = cm.getCommand();
					if(type == Commands.CONNECT) {
						userName = cm.getSender();
						controller.addUserToList(this);
						DataMessage dataMessage = new DataMessage(null, null, controller.getClientsOnline());
						controller.checkMessageQueue(this);
					}
				} else if(object instanceof ChatMessage) {
					
					controller.processChatMessage(object);
				}
				//String string = controller.decodeMessage(object);
				//controller.addUserToList(newUser);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				cancel();
				e.printStackTrace();
			}
		}
		
	}

	public void send(Object object) {
		try {
			outputStream.writeObject(object);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void cancel() {
		active = false;
		if(userName != null) {
			controller.removeUserFromList(userName);
		}
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getUserName() {
		return userName;
	}
}