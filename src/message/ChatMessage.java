package message;

import java.io.Serializable;

import javax.swing.Icon;

// Skall den kompletteras med hasPicture()?
/**
 * Class for messages to be sent between client-server-client.
 * @author KEJ
 *
 */
public class ChatMessage extends Message {
	private String chatMessage;
	private Icon picture;
	
	/**
	 * Constructor to be used by sending client.
	 * @param sender. The user at the sending client
	 * @param recipients. An array of recieving users
	 * @param message. The text message to send
	 * @param picture. The picture (Icon) to send.
	 */
	public ChatMessage(String from, String[] recipients, String chatMessage, Icon picture){
		super(from, recipients);
		this.chatMessage = chatMessage;
		this.picture = picture;
	}
		
	/**
	 * @return the picture (Icon)
	 */
	public Icon getPicture(){
		return picture;
	}
	
	public boolean hasPicture(){
		boolean has = false;
		if(picture != null){
			has = true;
		}
		return has;
	}
	
}
