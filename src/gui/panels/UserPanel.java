package gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class UserPanel extends JPanel{
	private JScrollPane scrollableUserList;
	private JTextArea taUserList;
	private JLabel lblChatUsers;

	public UserPanel (Dimension userPanelSize ) {
		setPreferredSize(userPanelSize);
		setBorder(new EmptyBorder(15,15,10,0));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(new Color(56, 56, 56));
		scrollableUserList = scrollableUserList();
		lblChatUsers = new JLabel("Users", SwingConstants.CENTER);
		lblChatUsers.setForeground(Color.WHITE);
		this.add(lblChatUsers);
		this.add(scrollableUserList);
	}


	public void clearUserPanel() {
		taUserList.setText("");
	}
	/**
	 * 
	 * @param client for adding a single client
	 */
	public void appendUserPanel(String client) {
		taUserList.append(client + "\n");
	}

	/**
	 * 
	 * @param clients for adding several clients
	 */
	public void appendUserPanel(String[] clients) {
		for(String client: clients) {
			taUserList.append(client + "\n");
		}
	}

	private JScrollPane scrollableUserList() {
		taUserList = new JTextArea();
		taUserList.setBackground(new Color(56, 56, 56));
		taUserList.setForeground(new Color(145,145,145));
		taUserList.setEditable(false);
		JScrollPane scrollableUserList = new JScrollPane(taUserList);
		scrollableUserList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollableUserList.setBorder(null);
		return scrollableUserList;
	}



}
