package p3;

import java.io.Serializable;

import javax.swing.Icon;

// Skall den kompletteras med hasPicture()?
/**
 * Class for messages to be sent between client-server-client.
 * @author KEJ
 *
 */
public class Message implements Serializable{
	private String from;
	private String[] to;
	private String message;
	private String deliveredToServerTime;
	private String deliveredFromServerTime;
	private Icon picture;
	
	/**
	 * Constructor to be used by sending client.
	 * @param from. The user at the sending client
	 * @param to. An array of recieving users
	 * @param message. The text message to send
	 * @param picture. The picture (Icon) to send.
	 */
	public Message(String from, String[] to, String message, Icon picture){
		this.from = from;
		this.to = to;
		this.message = message;
		this.picture = picture;
	}
	
	/**
	 * Constructor to be used by server.
	 * @param from. The user at the sending client
	 * @param to. The receiving client
	 * @param message. The text message to send.
	 * @param picture. The picture (Icon) to send.
	 * @param deliveredToServerTime. The time when the message was delivered to/recieved by the server.
	 * @param deliveredFromServerTime. The time when the message was delivered from the server.
	 */
	public Message(String from, String to, String message, Icon picture, String deliveredToServerTime,
			String deliveredFromServerTime){
		this.from = from;
		this.to[0] = to;
		this.message = message;
		this.picture = picture;
		this.deliveredToServerTime = deliveredToServerTime;
		this.deliveredFromServerTime = deliveredFromServerTime;
	}
	
	/**
	 * Returns the sending client.
	 * @return
	 */
	public String getFrom(){
		return from;
	}
	
	/**
	 * Return an array of recievers of the message.
	 * @return
	 */
	public String[] getTo(){
		return to;
	}
	
	/**
	 * @return the time when the message was delivered to the server, from the client
	 */
	public String getDeliveredToServerTime(){
		return deliveredToServerTime;
	}
	
	/**
	 * @return the time when the message was delivered from the server to the recieving client.
	 */
	public String getDeliveredFromServerTime(){
		return deliveredFromServerTime;
	}
	
	/**
	 * @return the picture (Icon)
	 */
	public Icon getPicture(){
		return picture;
	}
	
	/**
	 * Sets the time when the message was delivered to server.
	 * @param deliveredToServerTime
	 */
	public void setDeliveredToServerTime(String deliveredToServerTime){
		this.deliveredToServerTime = deliveredToServerTime;
	}
	
	public void setSentFromServer(String deliveredFromServerTime){
		this.deliveredFromServerTime = deliveredFromServerTime;
	}
}
