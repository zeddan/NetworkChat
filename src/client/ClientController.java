package client;
import client.interfaces.MessageListener;
import client.interfaces.ClientGUIListener;
import gui.ClientGUI;
import gui.ConnectGUI;
import message.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.awt.*;
import java.util.ArrayList;

/**
 * 
 * @author simonmansson
 * Handels the creation of connection and gui frames and passing of messages 
 * between the server and gui.
 */

public class ClientController {
	private ClientGUI clientGui;
	private ConnectGUI connectGui;
	private JFrame connectFrame;
	private JFrame clientFrame;
	private ClientConnection clientConnection;
    private String[] onlineClients;
    private ArrayList<Group> groups;

	public ClientController () {
		connectGui = new ConnectGUI(new ConnectGUIListener());
		clientGui = new ClientGUI(new ClientGUIListener());
		createConnectFrame();
	}
	
	/**
	 * 
	 * @param address Server address : String
	 * @param port Server port : int
	 * @param username Client username: String
	 */
	public void connect(String address, int port, String username) {
		clientConnection = new ClientConnection(address, port);
		if (clientConnection.hasConnected()) {
			clientConnection.setListener(new ClientConnectionListener());
			clientConnection.setUsername(username);
			clientConnection.start();
			createClientFrame(username);
			connectFrame.dispose();
		} else {
			JOptionPane.showMessageDialog(connectFrame, "Could not Connect");
		}

	}

	public void createClientFrame(final String username) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				clientFrame = new JFrame("Client:" + username);
				clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				clientFrame.add(clientGui);
				clientGui.setUsername(username);
				clientFrame.pack();
				clientFrame.setLocation(
						dim.width/2-clientFrame.getSize().width/2,
						dim.height/2-clientFrame.getSize().height/2);
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

	private class ConnectGUIListener implements ConnectGUI.ConnectGUIListener {
		@Override
		public void onConnect(String address, int port, String username) {
			connect(address, port, username);
		}
	}

	private class ClientGUIListener implements client.interfaces.ClientGUIListener {

        @Override
        public void newPrivateMessage(String sender, String recipient) {
            Group group = new Group(new String[]{sender, recipient}, recipient);
            groups.add(group);
            clientGui.addPrivateMessage(group);
        }

        @Override
        public void newGroup(ArrayList<String> users, String groupName) {
            String[] array = users.toArray(new String[users.size()]);
            Group group = new Group(array, groupName);
            groups.add(group);
            clientGui.addGroup(group);
        }

        @Override
        public void onMessage(Message message) {
            clientConnection.sendMessage(message);
        }
    }

	private class ClientConnectionListener implements MessageListener {
		@Override
		public void update(Message message) {
			if (message instanceof DataMessage) {
                onlineClients = ((DataMessage) message).getData();
                groups = new ArrayList<>();
                groups.add(new Group(onlineClients, "All"));
				clientGui.updateOnlineClients(onlineClients);
			} else if (message instanceof ChatMessage) {
                clientGui.newChatMessage((ChatMessage) message);
            }
		}
	}

	public static void main(String[] args) {
		new ClientController();
	}
}
