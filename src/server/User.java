package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Del av gruppuppgift DA343A
 * @author Gustav Frigren
 *
 */
public class User implements Runnable {
	
	private String userName;
	
	private Controller controller; // User fick en egen referens till Controllern
	
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
		try { // Funkar det med input före output?
			inputStream = new ObjectInputStream(socket.getInputStream());
			outputStream = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		//TODO Check user ID here? (to set userName)........................

		while(socket != null && socket.isConnected() && active == true) {
			try {
				Object object = inputStream.readObject();
				
				//TODO decodeMessage..............................
				//TODO vet ju inte exakt vad vi har fÃ¶r protokoll.
				
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