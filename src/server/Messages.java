package server;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;

import message.ChatMessage;

public class Messages {
	private Hashtable<String, LinkedList<ChatMessage>> messageQueue = new Hashtable<String, LinkedList<ChatMessage>>();
	
	public synchronized void put(String userName, LinkedList<ChatMessage> list){
		messageQueue.put(userName,list);
	}
	
	public synchronized LinkedList<ChatMessage> get(String key){
		return messageQueue.get(key);
	}
	
	public synchronized Enumeration<String> keys(){
		return messageQueue.keys();
	}
	
	public synchronized void remove(String key){
		messageQueue.remove(key);
	}
}
