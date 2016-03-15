package message;

/**
 * Class representing a command, e.g for sending a command from client to server.
 * @author KEJ
 *
 */
public class CommandMessage {

    private String sender;
	private int command;
	
	/**
	 * Constructor
	 * @param sender who sent the commando
	 * @param command the command to be done.
	 */
	public CommandMessage(String sender, int command){
        this.sender = sender;
		this.command = command;
	}
	
	public int getCommand(){
		return command;
	}

    public String getSender() {
        return sender;
    }
}