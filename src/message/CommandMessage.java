package message;

import client.Group;

/**
 * Class representing a command, e.g for sending a command from client to server.
 * @author KEJ
 *
 */
public class CommandMessage extends Message {

	private int command;
	
	/**
	 * Constructor
	 * @param sender who sent the commando
	 * @param command the command to be done.
	 */
	public CommandMessage(String sender, int command){
        super(sender, null);
		this.command = command;
	}
	
	public int getCommand(){
		return command;
	}

}