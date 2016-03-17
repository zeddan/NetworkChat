package server;

import gui.server.ServerGUI;
import message.ChatMessage;
import message.CommandMessage;
import message.Commands;
import message.DataMessage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import client.Group;

/**
 * Del av gruppuppgift DA343A
 * @author Gustav Frigren
 *
 */
public class Controller {
	
	private ServerGUI gui;
	private Server server;
	private Enumeration enumeration;
	
	private Hashtable<String, User> onlineUserTable; // table med alla anslutna Users
	private Hashtable<String, ArrayList<ChatMessage>> messageQueue;
	
	public Controller(ServerGUI gui) {
		onlineUserTable = new Hashtable<String, User>();
		messageQueue = new Hashtable<String, ArrayList<ChatMessage>>();
		this.gui = gui;
	}

	public User findOnlineUser(String userName) {
		User user = onlineUserTable.get(userName);
		return user;
	}

	public void sendToAllUsers(DataMessage dataMessage) {
		enumeration = onlineUserTable.keys();
		while(enumeration.hasMoreElements()) {
			String userName = (String) enumeration.nextElement();
			User user = (User) onlineUserTable.get(userName);
			user.send(dataMessage);
		}
	}
	
	public String[] getClientsOnline() {
		ArrayList<String> list = new ArrayList<String>();
		enumeration = onlineUserTable.keys();
		while(enumeration.hasMoreElements()) {
			list.add((String) enumeration.nextElement());
		}
		String[] usersList = new String[list.size()];
		for(int i = 0; i < list.size(); i++) {
			usersList[i] = list.get(i);
		}
		return usersList;
	}
	
	public void processChatMessage(Object object) {
		ChatMessage message = (ChatMessage) object;
		message.setDeliveredToServerTime(getTime());
		Group group = message.getRecipients();
		String[] list = group.getRecipients();
		for(int i = 0; i < list.length; i++) {
			User user = findOnlineUser(list[i]);
			if(user != null) {
				user.send(message);
			} else {
				addToMessageQueue(list[i], message);
			}
		}
	}

	public void checkMessageQueue(User user) {
		ArrayList<ChatMessage> list = messageQueue.get(user.getUserName());
		System.out.println("Checking message queue");
		if(list != null) {
			for(int i = 0; i < list.size(); i++) {
				ChatMessage message = list.get(i);
				System.out.println("Found queued message");
				message.setDeliveredToServerTime(getTime());
				user.send(message);
				list.remove(i);
			}
			if(list.isEmpty()) {
				System.out.println("Removing Arraylist " + user.getUserName());
				messageQueue.remove(user);
			}
		}
	}

	private void addToMessageQueue(String userName, ChatMessage message) {
		ArrayList<ChatMessage> list = messageQueue.get(userName);
		System.out.println("Adding to messagequeue");
		if(list == null) {
			System.out.println("Adding new Arraylist");
			ArrayList<ChatMessage> newList = new ArrayList<ChatMessage>();
			newList.add(message);
			messageQueue.put(userName, newList);
		} else {
			list.add(message);
		}
		
	}
	
	public void startServer(int port) {
		server = new Server(this, port);
		server.start();
	}
	
	public void stopServer(){
		if(server!=null){
			server.interrupt();
		}
	}

	public void removeUserFromList(String userName) {
		onlineUserTable.remove(userName);
		System.out.println("Removed from userlist: " + userName);
		sendUserListToAllClients();
	}

	public void addUserToList(User newUser) {
		onlineUserTable.put(newUser.getUserName(), newUser);
		System.out.println("Added new User: " + newUser.getUserName());
		sendUserListToAllClients();
	}
	
	public void sendUserListToAllClients() {
		String[] list = getClientsOnline();
		DataMessage message = new DataMessage(null, null, list);
		sendToAllUsers(message);
	}
	
	public String getTime() {
		DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		Date date = new Date();
		String dateTime = df.format(date);
		return dateTime;
	}
}