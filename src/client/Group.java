package client;

import java.io.Serializable;

public class Group implements Serializable{
	private String[] recipients;
	private String groupName;
	
	public Group(String[] recipients, String groupName){
		this.recipients = recipients;
		this.groupName = groupName;
	}
	
	public String[] getRecipients(){
		return recipients;
	}
	
	public String getGroupName(){
		return groupName;
	}
	
	public boolean isEqualTo(String otherGroupName){
		return groupName.equals(otherGroupName);
	}


}
