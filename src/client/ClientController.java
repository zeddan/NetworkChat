package client;
import client.interfaces.MessageListener;
import gui.ClientGUI;
import gui.ConnectGUI;
import message.*;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.*;

/**
 * 
 * @author simonmansson
 *
 */

public class ClientController {
	private ClientGUI clientGui;
	private ConnectGUI connectGui;
	private JFrame connectFrame;
	private JFrame clientFrame;
	private ClientConnection clientConnection;

	public ClientController () {
		connectGui = new ConnectGUI(new ConnectGUIListener());
		clientGui = new ClientGUI(new ClientGUIListener());
		createConnectFrame();
	}

	public void connect(String address, int port, String username) {
		clientConnection = new ClientConnection(address, port);
        clientConnection.setListener(new ClientConnectionListener());
        clientConnection.setUsername(username);
        clientConnection.start();
        createClientFrame(username);
	}

	public void createClientFrame(String username) {
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

    private class ClientGUIListener implements MessageListener {
        @Override
        public void update(Message message) {
            clientConnection.sendMessage(message);
        }
    }

	private class ClientConnectionListener implements MessageListener {
        @Override
		public void update(Message message) {
            if (message instanceof DataMessage) {
                String[] onlineClients = ((DataMessage) message).getData();
                clientGui.updateOnlineClients(onlineClients);
            } else if (message instanceof ChatMessage)
                clientGui.newChatMessage((ChatMessage) message);
		}
	}

    public static void main(String[] args) {
        new ClientController();
    }
}
