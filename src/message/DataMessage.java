package message;

/**
 * Class representing data to be transfered, e.g. data from server to client. 
 * @author KEJ
 *
 */
public class DataMessage extends Message{
	private String[] data;
	
	public DataMessage(String sender, String[] recipients, String[] data){
		super(sender, recipients);
		this.data = data;
	}
	
	public String[] getData(){
		return data;
	}
}
