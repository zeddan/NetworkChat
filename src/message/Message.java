package message;

import java.io.Serializable;

public class Message implements Serializable {
	private String sender;
	private String[] recipients;
	private String deliveredToServerTime;
	private String deliveredFromServerTime;
	
	/**
	 * Constructor
	 * @param sender, the name of the sending client
	 * @param recipients, the recipients. Can be one or many
	 * @param message, the message to be sent.
	 */
	public Message(String sender, String[] recipients){
		this.sender = sender;
		this.recipients = recipients;
	}
	
	/**
	 * Return an array of recievers of the message.
	 * @return
	 */
	public String[] getRecipients(){
		return recipients;
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
