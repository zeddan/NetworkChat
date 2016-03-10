package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClientGUI extends JPanel {

    private JPanel pnlLeft = new JPanel(new FlowLayout());
    private JPanel pnlRight = new JPanel(new FlowLayout());
    private JPanel pnlLeftGroups = new JPanel(new FlowLayout());
    private JPanel pnlLeftPrivate = new JPanel(new FlowLayout());
    private JPanel pnlMain = new JPanel(new BorderLayout());
    private JTextField tfChatWrite = new JTextField();
    private JTextField tfChatWindow = new JTextField();
    private JLabel lblGroupList = new JLabel("Groups", SwingConstants.CENTER);
    private JLabel lblGroupCreate = new JLabel("+ Create group", SwingConstants.CENTER);
    private JLabel lblPrivateList = new JLabel("Private messages", SwingConstants.CENTER);
    private JLabel lblPrivateCreate = new JLabel("+ New message", SwingConstants.CENTER);

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
        this.setPreferredSize(new Dimension(WIN_WIDTH, WIN_HEIGHT));
        this.setLayout(new BorderLayout());
        setupListeners();

        // set sizes
        tfChatWrite.setPreferredSize(new Dimension(WIN_WIDTH/3, WIN_HEIGHT/20));
        lblGroupList.setPreferredSize(new Dimension(PNL_LEFT_WIDTH, 20));
        lblPrivateList.setPreferredSize(new Dimension(PNL_LEFT_WIDTH, 20));
        pnlLeft.setPreferredSize(pnlLeftSize);
        pnlLeftGroups.setPreferredSize(pnlLeftGroupsSize);
        pnlLeftPrivate.setPreferredSize(pnlLeftPrivateSize);
        pnlRight.setPreferredSize(pnlRightSize);

        // add components to left panel
        pnlLeftGroups.add(lblGroupList);
        pnlLeftGroups.add(lblGroupCreate);
        pnlLeftPrivate.add(lblPrivateList);
        pnlLeftPrivate.add(lblPrivateCreate);
        pnlLeft.add(pnlLeftGroups);
        pnlLeft.add(pnlLeftPrivate);

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
                JOptionPane.showMessageDialog(null, "hey");
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });

        /* * * * * * * * * * *
            CREATE NEW MESSAGE
        * * * * * * * * * * */
        lblPrivateCreate.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                JOptionPane.showMessageDialog(null, "hey");
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

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
