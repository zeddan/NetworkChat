package message;
import client.Group;

/**
 * Class representing data to be transfered, e.g. data from server to client. 
 * @author KEJ
 *
 */
public class DataMessage extends Message{
	
	private String[] data;
	
	public DataMessage(String sender, Group group, String[] data){
		super(sender, group);
		this.data = data;
	}
	
	public String[] getData(){
		return data;
	}
}