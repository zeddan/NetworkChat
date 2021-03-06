package message;

import java.io.Serializable;
import client.Group;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import client.Group;

// Skall den kompletteras med hasPicture()?
/**
 * Class for messages to be sent between client-server-client.
 * @author KEJ
 *
 */
public class ChatMessage extends Message {
	private String chatMessage;
	private Icon picture;
	private String fileName;
	
	/**
	 * Constructor to be used by sending client.
	 * @param sender. The user at the sending client
	 * @param recipients. An array of recieving users
	 * @param message. The text message to send
	 * @param picture. The picture (Icon) to send.
	 */
	public ChatMessage(String from, Group group, String chatMessage, Icon picture, String fileName){
		super(from, group);
		this.chatMessage = chatMessage;
		this.picture = picture;
		this.fileName = fileName;
	}
		
	/**
	 * @return the picture (Icon)
	 */
	public Icon getPicture(){
		return picture;
	}
	
	/**
	 * 
	 * @return the chatMessage (String)
	 */
	public String getChatMessage(){
		return chatMessage;
	}
	
	public boolean hasPicture(){
		return picture != null;
	}
	
	public String getFileName(){
		return fileName;
	}
	
	public String toString(){
		return "[" + super.getSender() + "]" + "(" + super.getGroup().getGroupName()+ ")" + "<" + super.getDeliveredToServerTime() + "> " + chatMessage; 
	}
	
}
