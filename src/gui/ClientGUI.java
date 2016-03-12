package gui;

import gui.interfaces.UserSelectionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class ClientGUI extends JPanel {

    private JPanel pnlLeft = new JPanel(new FlowLayout());
    private JPanel pnlRight = new JPanel();
    private JPanel pnlLeftGroups = new JPanel();
    private JPanel pnlLeftPrivate = new JPanel();
    private JPanel pnlMain = new JPanel(new BorderLayout());
    private JTextField tfChatWrite = new JTextField();
    private JTextField tfChatWindow = new JTextField();
    private JLabel lblGroupList = new JLabel("Groups", SwingConstants.CENTER);
    private JLabel lblGroupCreate = new JLabel("+ Create group", SwingConstants.CENTER);
    private JLabel lblPrivateList = new JLabel("Private messages", SwingConstants.CENTER);
    private JLabel lblPrivateCreate = new JLabel("+ New message", SwingConstants.CENTER);
    private JLabel lblChatUsers = new JLabel("Users", SwingConstants.CENTER);

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

    public ClientGUI() {
        setPreferredSize(new Dimension(WIN_WIDTH, WIN_HEIGHT));
        setLayout(new BorderLayout());
        setupListeners();

        // set sizes
        tfChatWrite.setPreferredSize(new Dimension(WIN_WIDTH/3, WIN_HEIGHT/20));
        lblGroupList.setPreferredSize(new Dimension(PNL_LEFT_WIDTH, 20));
        lblPrivateList.setPreferredSize(new Dimension(PNL_LEFT_WIDTH, 20));
        pnlLeft.setPreferredSize(pnlLeftSize);
        pnlLeftGroups.setPreferredSize(pnlLeftGroupsSize);
        pnlLeftPrivate.setPreferredSize(pnlLeftPrivateSize);
        pnlRight.setPreferredSize(pnlRightSize);

        // styling and behavior
        pnlLeft.setBorder(new EmptyBorder(10,30,10,0));
        pnlRight.setBorder(new EmptyBorder(15,15,10,0));
        pnlLeftGroups.setLayout(new BoxLayout(pnlLeftGroups, BoxLayout.Y_AXIS));
        pnlLeftPrivate.setLayout(new BoxLayout(pnlLeftPrivate, BoxLayout.Y_AXIS));
        pnlRight.setLayout(new BoxLayout(pnlRight, BoxLayout.Y_AXIS));

        // add components to left panel
        pnlLeftGroups.add(lblGroupList);
        pnlLeftGroups.add(lblGroupCreate);
        pnlLeftPrivate.add(lblPrivateList);
        pnlLeftPrivate.add(lblPrivateCreate);
        pnlLeft.add(pnlLeftGroups);
        pnlLeft.add(pnlLeftPrivate);

        // add components to right panel
        pnlRight.add(lblChatUsers);

        // panel bg colors
        pnlLeft.setBackground(new Color(56, 56, 56));
        pnlRight.setBackground(new Color(56, 56, 56));
        pnlLeftGroups.setOpaque(false);
        pnlLeftPrivate.setOpaque(false);

        // text colors
        lblGroupList.setForeground(Color.WHITE);
        lblGroupCreate.setForeground(new Color(145,145,145));
        lblPrivateList.setForeground(Color.WHITE);
        lblPrivateCreate.setForeground(new Color(145,145,145));
        lblChatUsers.setForeground(Color.WHITE);

        tfChatWindow.setEditable(false);

        pnlMain.add(tfChatWindow, BorderLayout.CENTER);
        pnlMain.add(tfChatWrite, BorderLayout.SOUTH);
        this.add(pnlLeft, BorderLayout.WEST);
        this.add(pnlMain, BorderLayout.CENTER);
        this.add(pnlRight, BorderLayout.EAST);
    }

    public void setupListeners() {

        /* * * * * * * * * * *
            CREATE NEW GROUP
        * * * * * * * * * * */
        lblGroupCreate.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String bigList[] = new String[30];
                for (int i = 0; i < bigList.length; i++) {
                    bigList[i] = Integer.toString(i);
                }
                JOptionPane.showInputDialog(null, "Select users", "Create group", JOptionPane.QUESTION_MESSAGE,
                        null, bigList, "Titan");
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                lblGroupCreate.setForeground(new Color(250,250,250));
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                lblGroupCreate.setForeground(new Color(145,145,145));
            }
        });

        /* * * * * * * * * * *
            CREATE NEW MESSAGE
        * * * * * * * * * * */
        lblPrivateCreate.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                JOptionPane.showMessageDialog(null, new NewMessageDialog(new UserSelectionListener() {
                    @Override
                    public void onUsers(ArrayList<String> users) {

                    }
                }), "Select users", JOptionPane.PLAIN_MESSAGE);
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                lblPrivateCreate.setForeground(new Color(250,250,250));
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                lblPrivateCreate.setForeground(new Color(145,145,145));
            }

        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Client");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new ClientGUI());
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

}
