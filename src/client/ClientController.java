package client;
import gui.ClientGUI;
import gui.ConnectGUI;
import message.*;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.*;
import java.util.LinkedList;

/**
 * 
 * @author simonmansson
 *
 */

public class ClientController {
	private ClientGUI clientGui;
	private ConnectGUI connectGui;
	private String userName;
	private JFrame connectFrame;
	private JFrame clientFrame;
	private ClientConnection clientConnection;
    private String[] onlineClients;


	public ClientController () {
		connectGui = new ConnectGUI(this);
		clientGui = new ClientGUI(new ClientGUIListener());
		//createConnectFrame();
		createClientFrame();
	}

	public void connect(String address, int port, String userName) {
		this.userName = userName;
		clientConnection = new ClientConnection(address, port, new ClientConnectionListener());
		clientConnection.startListener();
        onlineClients = clientConnection.getOnlineClients();
        clientGui.updateOnlineClients(onlineClients);
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

    private class ClientGUIListener implements MessageListener {
        @Override
        public void update(Message message) {
            clientConnection.sendMessage(message);
        }
    }

	private class ClientConnectionListener implements MessageListener {
        @Override
		public void update(Message message) {
            if (message instanceof DataMessage)
                clientGui.newDataMessage((DataMessage) message);
            else if (message instanceof ChatMessage)
                clientGui.newChatMessage((ChatMessage) message);
		}
	}

    public static void main(String[] args) {
        new ClientController();
    }
}
