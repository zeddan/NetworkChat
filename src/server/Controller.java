package server;

import gui.server.ServerGUI;
import message.ChatMessage;
import message.CommandMessage;
import message.Commands;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Del av gruppuppgift DA343A
 * @author Gustav Frigren
 *
 */
public class Controller {
	
	private ServerGUI gui;
	private Server server;
	private Enumeration enumeration;
	
	private Hashtable<String, User> userTable; // table med alla anslutna Users
	
	public Controller(ServerGUI gui) {
		userTable = new Hashtable<String, User>();
		this.gui = gui;
	}
	
	public User findUser(String userName) {
		User user = userTable.get(userName);
		return user;
		//TODO metoden som anropar får kolla om user är null
		//(man kan ju göra det i den här metoden också). Om den är null är
		//ju klienten inte tillgänglig och då får man spara ett eventuellt meddelande någonstans.
		//TODO för att skicka: user.send(.....);
	}
	
	public void sendToAllUsers(Object object) {
		enumeration = userTable.keys();
		while(enumeration.hasMoreElements()) {
			String userName = (String) enumeration.nextElement();
			User user = (User) userTable.get(userName);
			user.send(object);
		}
	}
	
	public String[] getClientsOnline() {
		ArrayList<String> list = new ArrayList<String>();
		enumeration = userTable.keys();
		while(enumeration.hasMoreElements()) {
			list.add((String) enumeration.nextElement());
		}
		String[] usersList = new String[list.size()];
		for(int i = 0; i < list.size(); i++) {
			usersList[i] = list.get(i);
		}
		return usersList;
	}
	
	public void processMessage(Object object) {
		ChatMessage message = (ChatMessage) object;
		
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
		userTable.remove(userName);
	}

	public void addUserToList(User newUser) {
		userTable.put(newUser.getUserName(), newUser);
	}
	
	public void createNewMessageWithPicture() {
		
	}
	
	public void createNewMessageNoPicture() {
		
	}
	
	public String decodeMessage(Object object) {
		String string = "";
		if(object instanceof CommandMessage) {
			CommandMessage cm = (CommandMessage) object;
			int type = cm.getCommand();
			if(type == Commands.GET_CLIENTS_ONLINE) {
				string = cm.getSender();
			}
		}
		return string;
	}

}