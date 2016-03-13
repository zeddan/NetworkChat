package gui;

import server.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Map;

public class NewGroupDialog extends JPanel {

    private Map users;
    private JScrollPane scrollPane;
    private UsersPanel usersPanel;
    private static ArrayList<User> selectedUsers;

    public static ArrayList<User> display(Map<String, User> users) {
        selectedUsers = new ArrayList<>();
        String title = "Select users";
        int optionType = JOptionPane.YES_NO_OPTION;
        int messageType = JOptionPane.PLAIN_MESSAGE;
        String[] options = {"Cancel", "Create group"};
        int i = JOptionPane.showOptionDialog(
                null,
                new NewGroupDialog(users),
                title,
                optionType,
                messageType,
                null,
                options,
                null);
        return i == 1 ? selectedUsers : null;
    }

    public NewGroupDialog(Map<String, User> users) {
        this.users = users;
        usersPanel = new UsersPanel();
        scrollPane = new JScrollPane(usersPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null);
        setCustomSize();
        add(scrollPane);
    }

    private void setCustomSize() {
        Dimension d = usersPanel.getDimension();
        int margin = 25;
        int x = (int) d.getWidth() + margin;
        int y = (int) d.getHeight() + margin;
        scrollPane.setPreferredSize(d);
        setPreferredSize(new Dimension(x, y));
    }

    private class UsersPanel extends JPanel {

        private int cols, rows;

        public UsersPanel() {
            cols = 5;
            rows = users.size() % cols == 0 ?
                   users.size()/cols : users.size()/cols + 1;
            setLayout(new GridLayout(rows, cols));
            setPreferredSize(getSize());
            for (Object object : users.values()) {
                User user = (User) object;
                add(new UserLabel(user.getUserName()));
            }
        }

        public Dimension getDimension() {
            int x = cols * UserLabel.WIDTH;
            int y = rows * UserLabel.HEIGHT;
            return new Dimension(x, y);
        }
    }

    private class UserLabel extends JLabel {

        public static final int WIDTH = 100;
        public static final int HEIGHT = 20;

        public UserLabel(final String name) {
            super(name);
            setPreferredSize(new Dimension(WIDTH, HEIGHT));
            setOpaque(true);
            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    User user = (User) users.get(name);
                    selectedUsers.add(user);
                    // also make some kind of indication visually
                }

                @Override
                public void mousePressed(MouseEvent mouseEvent) {

                }

                @Override
                public void mouseReleased(MouseEvent mouseEvent) {

                }

                @Override
                public void mouseEntered(MouseEvent mouseEvent) {
                    // change text color
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                    // change text color
                }
            });
        }

        public int getHeight() {
            return HEIGHT;
        }

        public int getWidth() {
            return WIDTH;
        }
    }
}
