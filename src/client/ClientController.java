package client;
import gui.ClientGUI;
import gui.ConnectGUI;
import message.*;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import client.MessageCallback;
import java.awt.*;
import java.util.LinkedList;

/**
 * 
 * @author simonmansson
 *
 */

public class ClientController  {
	private ClientGUI clientGui;
	private ConnectGUI connectGui;
	private String userName;
	private JFrame connectFrame;
	private JFrame clientFrame;
	private ConnectionListener listener;
	private ClientConnection clientConnection;


	public ClientController () {
		connectGui = new ConnectGUI(this);
		clientGui = new ClientGUI(this);
		listener = new ConnectionListener();
		//createConnectFrame();
		createClientFrame();
		
	}


	public void connect(String address, int port, String userName) {
		this.userName = userName;
		clientConnection = new ClientConnection(address, port, listener);
		clientConnection.startListener();
		createClientFrame();

	}

	public void createClientFrame() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				clientFrame = new JFrame("Client");
				clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				clientFrame.add(clientGui);
				clientFrame.pack();
				clientFrame.setLocation(
						dim.width/2-connectFrame.getSize().width/2,
						dim.height/2-connectFrame.getSize().height/2);
				clientFrame.setVisible(true);
			}
		});
	}

	public void createConnectFrame() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				connectFrame = new JFrame("NetworkChat");
				connectFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				connectFrame.add(connectGui);
				connectFrame.pack();
				connectFrame.setLocation(
						dim.width/2-connectFrame.getSize().width/2,
						dim.height/2-connectFrame.getSize().height/2);
				connectFrame.setVisible(true);
			}
		});
	}

	public void sendMessage(String chatMessage, String[] recipients) {
		//if pic
		
		//else pic
		clientConnection.sendMessage( new ChatMessage(userName, recipients, chatMessage, null));

	}
	
	public void addPicture() {
		
	}
	
	public Message getNextMessage(){
		return listener.getNextMessage();
	}
	
	public boolean hasMessage(){
		return (listener.getMessageListSize() > 0);
	}

	public static void main(String[] args) {
		new ClientController();	

	}

	private class ConnectionListener implements MessageCallback {
		LinkedList<Message> messageList = new LinkedList<Message>(); 
		
		public ConnectionListener(){
			ChatMessage message = new ChatMessage("Kalk", new String[] {"Coffe"}, "HEJSAN ALLA GLADA" , null);
			messageList.add(message);
			messageList.add(message);
			messageList.add(message);
			messageList.add(message);
			messageList.add(message);
		
		}
		public void add(Message message) {
			messageList.add(message);
		} 
		public Message getNextMessage(){
			return messageList.removeLast();
		}
		public int getMessageListSize(){
			return messageList.size();
		}

	}
}
