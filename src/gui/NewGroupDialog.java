package gui;

import gui.button.CustomButton;
import server.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        UIManager.put("OptionPane.background", Color.white);
        UIManager.put("Panel.background", Color.white);
        selectedUsers = new ArrayList<>();
        String title = "Select users";
        int optionType = JOptionPane.YES_NO_OPTION;
        int messageType = JOptionPane.PLAIN_MESSAGE;
        final CustomButton[] options = {
                new CustomButton("Cancel"),
                new CustomButton("Create group")};
        addActionListener(options[0]);
        addActionListener(options[1]);
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

    private static void addActionListener(final CustomButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComponent jc = (JComponent) e.getSource();
                JOptionPane pane = button.getOptionPane(jc);
                pane.setValue(button);
            }
        });
    }

    public NewGroupDialog(Map<String, User> users) {
        this.users = users;
        usersPanel = new UsersPanel();
        scrollPane = new JScrollPane(usersPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null);
        scrollPane.setBackground(Color.WHITE);
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
                    users.size() / cols : users.size() / cols + 1;
            setLayout(new GridLayout(rows, cols));
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
            addMouseListener(new MouseListener() {
                private boolean pressed = false;
                private final Color grey = new Color(145, 145, 145);
                private final Color black = Color.BLACK;

                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    User user = (User) users.get(name);
                    if (selectedUsers.contains(user)) {
                        selectedUsers.remove(user);
                        pressed = false;
                        setTextColor(black);
                    } else {
                        selectedUsers.add(user);
                        pressed = true;
                        setTextColor(grey);
                    }
                }

                @Override
                public void mousePressed(MouseEvent mouseEvent) {
                }

                @Override
                public void mouseReleased(MouseEvent mouseEvent) {
                    if (pressed)
                        setTextColor(grey);
                    else
                        setTextColor(black);
                }

                @Override
                public void mouseEntered(MouseEvent mouseEvent) {
                    setTextColor(grey);
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                    if (!pressed)
                        setTextColor(black);
                }

                private void setTextColor(Color color) {
                    UserLabel.this.setForeground(color);
                }
            });
        }
    }
}
