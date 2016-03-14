package server;

import java.util.Hashtable;

import serverGUI.ServerGUI;

/**
 * Del av gruppuppgift DA343A
 * @author Gustav Frigren
 *
 */
public class Controller {
	private ServerGUI gui;
	private Server server;
	
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

}