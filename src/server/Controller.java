package server;

import gui.server.ServerGUI;
import message.ChatMessage;
import message.DataMessage;
import server.log.SystemEntry;
import server.log.SystemEntryType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedList;

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

	private OnlineUsers onlineUserTable;
	private Messages messageQueue;

	public Controller(ServerGUI gui) {
		onlineUserTable = new OnlineUsers();
		messageQueue = new Messages();
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

	public void logToGUI(String message){
		gui.writeLogToGUI(message);
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
		LinkedList<ChatMessage> list = messageQueue.get(user.getUserName());
		if(list != null) {
			while(!list.isEmpty()){
				ChatMessage message = list.remove();
				message.setDeliveredToServerTime(getTime());
				user.send(message);
			}
			if(list.isEmpty()) {
				messageQueue.remove(user.getUserName());
			}
		}
	}

	private void addToMessageQueue(String userName, ChatMessage message) {
		LinkedList<ChatMessage> list = messageQueue.get(userName);
		if(list == null) {
			LinkedList<ChatMessage> newList = new LinkedList<ChatMessage>();
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
		server.stopServer();
		String[] allOnlineClients = getClientsOnline();
		for(int i = 0; i < allOnlineClients.length; i++){
			findOnlineUser(allOnlineClients[i]).cancel();
		}
	}

	public void removeUserFromList(String userName) {
		onlineUserTable.remove(userName);
		logToGUI(new SystemEntry(userName + " disconnected", SystemEntryType.INFO).toString() + "\n");
		sendUserListToAllClients();
	}

	public void addUserToList(User newUser) {
		onlineUserTable.put(newUser);
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