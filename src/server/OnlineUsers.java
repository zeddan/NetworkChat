package server;

import java.util.Enumeration;
import java.util.Hashtable;

public class OnlineUsers {
	private Hashtable<String, User> onlineUserTable = new Hashtable<String, User>();
	
	public synchronized void put(User user){
		onlineUserTable.put(user.getUserName(),user);
	}
	
	public synchronized User get(String key){
		return onlineUserTable.get(key);
	}
	
	public synchronized Enumeration<String> keys(){
		Enumeration<String> users = onlineUserTable.keys();
		return users;
	}
	
	public synchronized void remove(String key){
		onlineUserTable.remove(key);
	}

}
