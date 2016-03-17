package gui.panels;

import client.interfaces.ClientGUIListener;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class UserPanel extends JPanel{
	private JPanel pnlUserList;
    private ClientGUIListener listener;
    private Color bgGrey = new Color(56, 56, 56);
    private Color txtGrey = new Color(145, 145, 145);
    private Color txtWhite = Color.WHITE;
    private String username;

	public UserPanel(Dimension userPanelSize, ClientGUIListener listener) {
        this.listener = listener;
        setLayout(new FlowLayout());
		setPreferredSize(userPanelSize);
		setBorder(new EmptyBorder(10, 15, 0, 10));
		setBackground(bgGrey);
		add(lblChatUsers());
        add(pnlUserList());
	}


	public void clearUserPanel() {
        remove(pnlUserList);
        pnlUserList = null;
        add(pnlUserList());
	}

    public void updateOnlineClients(String[] clientList, String username) {
        for(String client : clientList) {
            if(!client.equals(username)) {
                appendUserPanel(client);
            }
        }
    }

	/**
	 * 
	 * @param username for adding a single client
	 */
	public synchronized void appendUserPanel(String username) {
        JLabel lbl = new JLabel(username);
        lbl.setForeground(txtGrey);
        lbl.setOpaque(false);
        lbl.setPreferredSize(new Dimension(100, 10));
        lbl.addMouseListener(new LabelListener(lbl));
        pnlUserList.add(lbl);
        updateUI();
	}

    private JLabel lblChatUsers() {
        JLabel lblChatUsers = new JLabel("All users");
        lblChatUsers.setBorder(new EmptyBorder(0,12,0,0));
        lblChatUsers.setPreferredSize(new Dimension(getPreferredSize().width, 10));
        lblChatUsers.setForeground(Color.WHITE);
        return lblChatUsers;
    }

    private JPanel pnlUserList() {
        pnlUserList = new JPanel();
        pnlUserList.setLayout(new BoxLayout(pnlUserList, BoxLayout.Y_AXIS));
        pnlUserList.setOpaque(false);
        //pnlUserList.setBackground(bgGrey);
        //pnlUserList.setBackground(Color.RED);
        int w = (int) getPreferredSize().getWidth();
        int h = (int) getPreferredSize().getHeight();
        Dimension d = new Dimension(w-25, h-50);
        pnlUserList.setPreferredSize(d);
        return pnlUserList;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private class LabelListener implements MouseListener {
        private JLabel label;

        public LabelListener(JLabel label) {
            this.label = label;
        }

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            if (mouseEvent.getClickCount() == 2) {
                String recipient = label.getText();
                listener.newPrivateMessage(username, recipient);
            }
        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {
            label.setForeground(txtWhite);
        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {
            label.setForeground(txtGrey);
        }
    }



}
