package message;

/**
 * Class representing a command, e.g for sending a command from client to server.
 * @author KEJ
 *
 */
public class CommandMessage extends Message {
	private String command;
	
	/**
	 * Constructor
	 * @param sender
	 * @param recipients
	 * @param command, the command to be done.
	 */
	public CommandMessage(String sender, String[] recipients, String command){
		super(sender, recipients);
		this.command = command;
	}
	
	public String getCommand(){
		return command;
	}
}
