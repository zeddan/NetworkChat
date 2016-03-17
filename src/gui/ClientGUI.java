package gui;

import client.Group;
import client.interfaces.MessageListener;
import gui.button.CustomButton;
import gui.panels.ChatWindow;
import gui.panels.UserPanel;
import message.*;
import server.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import javax.swing.JFileChooser;

/**
 * @author zeddan
 *
 */
public class ClientGUI extends JPanel {

	private static final Integer WIN_WIDTH = 1024;
	private static final Integer WIN_HEIGHT = 600;
	private static final Integer PNL_LEFT_WIDTH = WIN_WIDTH/5;
	private static final Integer PNL_LEFT_HEIGHT = WIN_HEIGHT;
	private static final Integer PNL_RIGHT_WIDTH = WIN_WIDTH/5;
	private static final Integer PNL_RIGHT_HEIGHT = WIN_HEIGHT;

	private Dimension pnlLeftSize = new Dimension(PNL_LEFT_WIDTH, PNL_LEFT_HEIGHT);
	private Dimension pnlLeftGroupsSize = new Dimension(PNL_LEFT_WIDTH, PNL_LEFT_HEIGHT/2);
	private Dimension pnlLeftPrivateSize = new Dimension(PNL_LEFT_WIDTH, PNL_LEFT_HEIGHT/2);
	private Dimension pnlRightSize = new Dimension(PNL_RIGHT_WIDTH, PNL_RIGHT_HEIGHT);

	private JFileChooser chooser = new JFileChooser();
	private FileNameExtensionFilter filter = new FileNameExtensionFilter(
			"JPG & PNG Images", "jpg", "png");

	private JPanel pnlLeftGroups;
	private JPanel pnlLeftPrivate;
	private JLabel lblReciver;

	private MessageListener listener;
	private String username;
	private Group selectedGroup;
	private Icon selectedImage;
	private String selectedImageName;
	private JTextField tfChatWrite;

	private ArrayList<Group> groupList;
	private ArrayList<JLabel> groupLabels;
	private ArrayList<String> onlineClients;
	private ArrayList<Group> privateMessageList;
	private Group all = new Group(null, "All");
	private ChatWindow chatWindow; 
	private UserPanel userPanel;
	
	
	public ClientGUI(MessageListener listener) {
		this.listener = listener;
		groupList = new ArrayList<Group>();
		groupLabels = new ArrayList<JLabel>();
		onlineClients = new ArrayList<String>();

		selectedGroup = all;

		setPreferredSize(new Dimension(WIN_WIDTH, WIN_HEIGHT));
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);

		// MAIN PANEL
		chatWindow = new ChatWindow();
		JPanel pnlMain = pnlMain();
		CustomButton btnImageChooser = btnImageChooser();
		tfChatWrite = tfChatWrite();
		pnlMain.add(chatWindow, BorderLayout.CENTER);
		JPanel pnlChatWrite = pnlChatWrite();
		lblReciver = lblReciver();
		pnlChatWrite.add(lblReciver, BorderLayout.NORTH);
		pnlChatWrite.add(tfChatWrite, BorderLayout.CENTER);
		pnlChatWrite.add(btnImageChooser, BorderLayout.EAST);
		pnlMain.add(pnlChatWrite, BorderLayout.SOUTH);

		// LEFT PANEL
		JPanel pnlLeft = pnlLeft();
		JLabel lblGroupList = lblGroupList();
		JLabel lblPrivateList = lblPrivateList();
		pnlLeftGroups = pnlLeftGroups();
		pnlLeftPrivate = pnlLeftPrivate();
		JLabel lblGroupCreate = lblGroupCreate();
		JLabel lblPrivateCreate = lblPrivateCreate();

		pnlLeftGroups.add(lblGroupList);
		pnlLeftGroups.add(lblGroupCreate);

		pnlLeftPrivate.add(lblPrivateList);
		pnlLeftPrivate.add(lblPrivateCreate);
		pnlLeft.add(pnlLeftGroups);
		pnlLeft.add(pnlLeftPrivate);

		// RIGHT PANEL
		userPanel = new UserPanel(pnlRightSize);

		//ASSEMBLE
		add(pnlLeft, BorderLayout.WEST);
		add(pnlMain, BorderLayout.CENTER);
		add(userPanel, BorderLayout.EAST);

		allGroupInit();
		setlblRecivers();
	}

	private void allGroupInit() {
		groupList.add(all);
		JLabel label = lblNewGroup(all.getGroupName());
		groupLabels.add(label);
		pnlLeftGroups.add(label);
		pnlLeftGroups.updateUI();
	}

	private JPanel pnlChatWrite() {
		JPanel pnlChatWrite = new JPanel(new BorderLayout());
		return pnlChatWrite;
	}

	private JPanel pnlMain() {
		JPanel pnlMain = new JPanel(new BorderLayout());
		pnlMain.setBackground(Color.WHITE);
		pnlMain.setBorder(new EmptyBorder(10, 10, 10, 10));
		return pnlMain;
	}

	private JLabel lblReciver() {
		JLabel reciver = new JLabel();
		reciver.setPreferredSize(new Dimension(70, WIN_HEIGHT/20));
		reciver.setText("To: ");
		return reciver;
	}

	private JLabel lblPrivateCreate() {
		final JLabel lblPrivateCreate = new JLabel("+ New message", SwingConstants.CENTER);
		lblPrivateCreate.setForeground(new Color(145,145,145));
		lblPrivateCreate.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				//addPrivate();
			}

			@Override
			public void mousePressed(MouseEvent mouseEvent) {}

			@Override
			public void mouseReleased(MouseEvent mouseEvent) {}

			@Override
			public void mouseEntered(MouseEvent mouseEvent) {
				lblPrivateCreate.setForeground(new Color(250,250,250));
			}

			@Override
			public void mouseExited(MouseEvent mouseEvent) {
				lblPrivateCreate.setForeground(new Color(145,145,145));
			}

		});
		return lblPrivateCreate;
	}
	private JLabel lblNewGroup(String name){
		final JLabel lbl = new JLabel(name);
		lbl.setForeground(new Color(145,145,145));
		lbl.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				setSelectedGroup(lbl.getText());
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				lbl.setForeground(new Color(250,250,250));

			}

			@Override
			public void mouseExited(MouseEvent e) {
				lbl.setForeground(new Color(145,145,145));
			}

		});
		return lbl;
	}



	private JLabel lblGroupCreate() {
		final JLabel lblGroupCreate = new JLabel("+ Create group", SwingConstants.CENTER);
		lblGroupCreate.setForeground(new Color(145,145,145));
		lblGroupCreate.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				addGroup(createGroup());                
			}

			@Override
			public void mousePressed(MouseEvent mouseEvent) {}

			@Override
			public void mouseReleased(MouseEvent mouseEvent) {}

			@Override
			public void mouseEntered(MouseEvent mouseEvent) {
				lblGroupCreate.setForeground(new Color(250,250,250));
			}

			@Override
			public void mouseExited(MouseEvent mouseEvent) {
				lblGroupCreate.setForeground(new Color(145,145,145));
			}
		});
		return lblGroupCreate;
	}

	private JPanel pnlLeftPrivate() {
		JPanel pnlLeftPrivate = new JPanel();
		pnlLeftPrivate.setLayout(new BoxLayout(pnlLeftPrivate, BoxLayout.Y_AXIS));
		pnlLeftPrivate.setPreferredSize(pnlLeftPrivateSize);
		pnlLeftPrivate.setOpaque(false);
		return pnlLeftPrivate;
	}

	private JPanel pnlLeftGroups() {
		JPanel pnlLeftGroups = new JPanel();
		pnlLeftGroups.setLayout(new BoxLayout(pnlLeftGroups, BoxLayout.Y_AXIS));
		pnlLeftGroups.setPreferredSize(pnlLeftGroupsSize);
		pnlLeftGroups.setOpaque(false);
		return pnlLeftGroups;
	}

	private JPanel pnlLeft() {
		JPanel pnlLeft = new JPanel(new FlowLayout());
		pnlLeft.setPreferredSize(pnlLeftSize);
		pnlLeft.setBorder(new EmptyBorder(10,30,10,0));
		pnlLeft.setBackground(new Color(56, 56, 56));
		return pnlLeft;
	}

	private JLabel lblPrivateList() {
		JLabel lblPrivateList = new JLabel("Private messages", SwingConstants.CENTER);
		lblPrivateList.setPreferredSize(new Dimension(PNL_LEFT_WIDTH, 20));
		lblPrivateList.setForeground(Color.WHITE);
		return lblPrivateList;
	}

	private JLabel lblGroupList() {
		JLabel lblGroupList = new JLabel("Groups", SwingConstants.CENTER);
		lblGroupList.setPreferredSize(new Dimension(PNL_LEFT_WIDTH, 20));
		lblGroupList.setForeground(Color.WHITE);
		return lblGroupList;
	}

	private JTextField tfChatWrite() {
		final JTextField tfChatWrite = new JTextField();
		tfChatWrite.setPreferredSize(new Dimension(WIN_WIDTH/3-70, WIN_HEIGHT/20));
		tfChatWrite.setBorder(BorderFactory.createCompoundBorder(
				new LineBorder(new Color(145, 145, 145), 3),
				new EmptyBorder(5, 5, 5, 5)));
		tfChatWrite.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					sendChatMessage();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {}
		});
		return tfChatWrite;
	}

	private CustomButton btnImageChooser() {
		CustomButton btnImageChooser = new CustomButton("img");
		btnImageChooser.setPreferredSize(new Dimension(70, WIN_HEIGHT/20));
		btnImageChooser.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					chooser.setFileFilter(filter);
					int returnVal = chooser.showOpenDialog(null);
					if(returnVal == JFileChooser.APPROVE_OPTION) {
						selectedImage = new ImageIcon(chooser.getSelectedFile().getAbsolutePath());
						selectedImageName = chooser.getSelectedFile().getName();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				System.out.println(chooser.getSelectedFile().getAbsolutePath());
			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}
		});
		return btnImageChooser;
	}

	public void newChatMessage(ChatMessage message) {
		chatWindow.append(message.toString(), message.getPicture());
		addGroup(message.getGroup());
	}

	public void newDataMessage(DataMessage message) {
		updateOnlineClients(message.getData());
	}


	public void updateOnlineClients(String[] clientList) {
		addToGroupAll(clientList);
		userPanel.clearUserPanel();

		for(String client : clientList) {
			if(!client.equals(username)) {
				onlineClients.add(client);
				userPanel.appendUserPanel(client);
			}
		}

	}

	public void setUsername(String username) {
		this.username = username;
	}


	public void setSelectedGroup(String groupName) {
		for(Group group : groupList) {
			if(group.getGroupName().equals(groupName)) {
				selectedGroup = group;
				setlblRecivers();
			}
		}
		
	}

	public void addToGroupAll(String[] clientList) {
		Group tempGroup = new Group(clientList, "All");

		if(selectedGroup.getGroupName().equals("All"))
			selectedGroup = tempGroup;
			setlblRecivers();
		groupList.set(0, tempGroup);
	}

	public synchronized void addGroup(Group inGroup) {	
		boolean foundGroupName = false;
		for(Group group : groupList) {
			if(group.getGroupName().equals(inGroup.getGroupName()))
				foundGroupName = true;
		}
		if(!foundGroupName){
			JLabel label = lblNewGroup(inGroup.getGroupName());
			groupList.add(inGroup);
			groupLabels.add(label);
			pnlLeftGroups.add(label);
			pnlLeftGroups.updateUI();
		}
	}

//	private void addPrivate() {
//		boolean foundPMName = false;
//		String name;
//		for(Group group : privateMessageList) {
//			if(group.getGroupName().equals(name))
//				foundPMName = true;
//		}
//		if(!foundPMName){
//			JLabel label = lblNewGroup(name);
//			groupLabels.add(label);
//			pnlLeftPrivate.add(label);
//			pnlLeftPrivate.updateUI();
//		}
//	}

	private Group createGroup() {
		Group group;
		boolean groupNameUsed = false;
		String groupName;
		String[] recipients = presentRecipients();
		do{
			groupName = JOptionPane.showInputDialog("Enter group name");
			for(int i = 0; i < groupList.size(); i++){
				groupNameUsed = groupList.get(i).isEqualTo(groupName);
			}
		}while(groupNameUsed);
		group = new Group(recipients, groupName);
		return group;
	}

	private String[] presentRecipients() {
		Map<String, User> users = new Hashtable<>();
		for (int i=0; i < onlineClients.size(); i++) {
			users.put(onlineClients.get(i), new UserMock(onlineClients.get(i)));
		}
		ArrayList<User> selectedUsers = NewGroupDialog.display(users);
		String[] recipients = new String[selectedUsers.size() + 1];

		for(int i = 0; i < selectedUsers.size(); i++){
			recipients[i] = selectedUsers.get(i).getUserName();
		}
		recipients[selectedUsers.size()] = username;

		return recipients;
	}
	
	private void setlblRecivers() {
		lblReciver.setText("To: " + selectedGroup.getGroupName());
	}

	private void sendChatMessage() {
		if (selectedGroup == null) {
			selectedGroup = groupList.get(0);
		}
		ChatMessage message = new ChatMessage(username, selectedGroup, tfChatWrite.getText(), selectedImage, selectedImageName);
		listener.update(message);
		selectedImage = null;
		selectedImageName = null;
		tfChatWrite.setText("");

	}


	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				JFrame frame = new JFrame("Client");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.add(new ClientGUI(null));
				frame.pack();
				frame.setLocation(
						dim.width/2-frame.getSize().width/2,
						dim.height/2-frame.getSize().height/2);
				frame.setVisible(true);
			}
		});
	}

}
