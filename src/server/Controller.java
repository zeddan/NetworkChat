package server;

import java.util.Hashtable;

/**
 * Del av gruppuppgift DA343A
 * @author Gustav Frigren
 *
 */
public class Controller {
	
	private Connection connection;
	
	private Hashtable<String, User> userTable; // table med alla anslutna Users
	
	public Controller() {
		userTable = new Hashtable<String, User>();
	}
	
	public User findUser(String userName) {
		User user = userTable.get(userName);
		return user;
		//TODO metoden som anropar får kolla om user är null
		//(man kan ju göra det i den här metoden också). Om den är null är
		//ju klienten inte tillgänglig och då får man spara ett eventuellt meddelande någonstans.
		//TODO för att skicka: user.send(.....);
	}
	
	public void startConnection(int port) {
		connection = new Connection(this, port);
		connection.run();
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