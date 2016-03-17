package gui;

import client.Group;
import client.interfaces.ClientGUIListener;
import gui.button.CustomButton;
import gui.panels.ChatWindow;
import gui.panels.UserPanel;
import message.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
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
	private JLabel lblReciever;

	private ClientGUIListener listener;
	private String username;
	private Group currentGroup;
	private Icon selectedImage;
	private String selectedImageName;
	private JTextField tfChatWrite;

	private ArrayList<Group> groups;
	private ChatWindow chatWindow;
	private UserPanel userPanel;

    private String[] onlineClients;

	public ClientGUI(ClientGUIListener listener) {
		this.listener = listener;

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
		lblReciever = lblReciever();
		pnlChatWrite.add(lblReciever, BorderLayout.NORTH);
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
		pnlLeftGroups.add(lblGroupList);
		pnlLeftGroups.add(lblGroupCreate);
		pnlLeftPrivate.add(lblPrivateList);
		pnlLeft.add(pnlLeftGroups);
		pnlLeft.add(pnlLeftPrivate);

		// RIGHT PANEL
		userPanel = new UserPanel(pnlRightSize, listener);

		// ASSEMBLE
		add(pnlLeft, BorderLayout.WEST);
		add(pnlMain, BorderLayout.CENTER);
		add(userPanel, BorderLayout.EAST);
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

	private JLabel lblReciever() {
		JLabel reciever = new JLabel();
        reciever.setOpaque(false);
		reciever.setPreferredSize(new Dimension(70, WIN_HEIGHT/20));
		reciever.setText("To: ");
		return reciever;
	}

	private JLabel lblNewGroup(String name){
		final JLabel lbl = new JLabel(name);
		lbl.setForeground(new Color(145,145,145));
		lbl.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				setCurrentGroup(lbl.getText());
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
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
                ArrayList<String> selectedUsers = NewGroupDialog.getRecipients(onlineClients);
                String groupName = NewGroupDialog.getGroupName();
                listener.newGroup(selectedUsers, groupName);
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
	}

	public void updateOnlineClients(String[] clientList) {
        if (groups == null) {
            groups = new ArrayList<>();
            groups.add(new Group(clientList, "All"));
            addGroup(groups.get(0));
            setCurrentGroup(groups.get(0).getGroupName());
        }
        onlineClients = clientList;
		userPanel.clearUserPanel();
        userPanel.updateOnlineClients(clientList, username);
	}

	public void setUsername(String username) {
		this.username = username;
        userPanel.setUsername(username);
	}

	public void setCurrentGroup(String groupName) {
		for(Group group : groups) {
			if(group.getGroupName().equalsIgnoreCase(groupName)) {
                currentGroup = group;
                lblReciever.setText("To: " + currentGroup.getGroupName());
                lblReciever.updateUI();
			}
		}
	}

    public synchronized void addPrivateMessage(Group group) {
        String groupName = group.getGroupName();
        JLabel label = lblNewGroup(groupName);
        groups.add(group);
        pnlLeftPrivate.add(label);
        pnlLeftPrivate.updateUI();
        setCurrentGroup(groupName);
        tfChatWrite.requestFocus();
    }

	public synchronized void addGroup(Group group) {
        String groupName = group.getGroupName();
        JLabel label = lblNewGroup(groupName);
        groups.add(group);
        pnlLeftGroups.add(label);
        pnlLeftGroups.updateUI();
        setCurrentGroup(groupName);
        tfChatWrite.requestFocus();
    }

	private void sendChatMessage() {
		ChatMessage message = new ChatMessage(username, currentGroup, tfChatWrite.getText(), selectedImage, selectedImageName);
		listener.onMessage(message);
		selectedImage = null;
		selectedImageName = null;
		tfChatWrite.setText("");
	}

}
