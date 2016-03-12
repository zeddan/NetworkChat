package gui;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnection {
	
	private Socket socket;
	private ObjectOutputStream oos;
	private MessageListener messageListener;
	
	public ClientConnection(String address, int port,
		MessageCallback messageCallback){
		connect(address,port);		
		try {
			oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			messageListener = new MessageListener(new ObjectInputStream
					(new BufferedInputStream(socket.getInputStream())),messageCallback);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void connect(String address, int port){
		try {
			socket = new Socket(address,port);
		} catch (IOException e) {
			System.err.println("Could not connect!");
		}
	}
	
	public void close(){
		try {
			socket.close();
		} catch (IOException e) {
			System.err.println("Socket not closed!");
		}
	}
	
	public void startListener(){
		messageListener.start();
	}
	
	public void sendMessage(Message message){
		try {
			oos.writeObject(message);
			oos.flush();
		} catch (IOException e) {
			System.out.println("Message could not be sent!");
		}
	}
	
	public void updateClients(){
		
	}
	
	private class MessageListener extends Thread{
		
		private ObjectInputStream ois;
		private MessageCallback mc;
		
		public MessageListener(ObjectInputStream ois,
				MessageCallback mc){
			this.ois = ois;
			this.mc = mc;
		}
		
		public void run(){
			while(true){
				try {
					mc.add((Message)ois.readObject());
					System.out.println("Callback made");
				} catch (ClassNotFoundException | IOException e) {
					System.out.println("Could not cast to Message");
				}
			}
		}
	}

}
