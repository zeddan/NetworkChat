package message;
import client.Group;
import java.io.Serializable;

import client.Group;

public class Message implements Serializable {
	private String sender;
	private Group group;
	private String deliveredToServerTime;
	private String deliveredFromServerTime;
	
	/**
	 * Constructor
	 * @param sender, the name of the sending client
	 * @param recipients, the recipients. Can be one or many
	 * @param message, the message to be sent.
	 */
	public Message(String sender, Group group){
		this.sender = sender;
		this.group = group;
	}
	
	/**
	 * 
	 * @return String the sender
	 */
	public String getSender() {
		return sender;
	}
	
	/**
	 * Return an array of recievers of the message.
	 * @return
	 */
	public Group getRecipients(){
		return group;
	}
	
	/**
	 * @return the time when the message was delivered to the server, from the client
	 */
	public String getDeliveredToServerTime(){
		return deliveredToServerTime;
	}
	
	/**
	 * Sets the time when the message was delivered to server.
	 * @param deliveredToServerTime
	 */
	
	public void setDeliveredToServerTime(String deliveredToServerTime){
		this.deliveredToServerTime = deliveredToServerTime;
	}
	
	/**
	 * @return the time when the message was delivered to the server, from the client
	 */
	public String getDeliveredFromServerTime(){
		return deliveredFromServerTime;
	}
	
	/**
	 * Sets the time when the message is sent from the server.
	 * @param deliveredFromServerTime
	 */
	public void setSentFromServer(String deliveredFromServerTime){
		this.deliveredFromServerTime = deliveredFromServerTime;
	}
	
	
}
