package gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ConnectGUI extends JPanel {

    private static final Integer WIN_WIDTH = 600;
    private static final Integer WIN_HEIGHT = 150;

    private JPanel pnlOptions = new JPanel(new GridLayout(3, 2));
    private JLabel lblUsername = new JLabel("Username");
    private JLabel lblAddress = new JLabel("Address");
    private JLabel lblPort = new JLabel("Port");
    private JTextField tfUsername = new JTextField();
    private JTextField tfAddress = new JTextField();
    private JTextField tfPort = new JTextField("80023");
    private JButton btnConnect = new JButton("Connect");
    private Dimension componentSize = new Dimension(200, 25);

    public ConnectGUI() {
        setPreferredSize(new Dimension(WIN_WIDTH, WIN_HEIGHT));
        lblUsername.setPreferredSize(componentSize);
        lblAddress.setPreferredSize(componentSize);
        lblPort.setPreferredSize(componentSize);
        tfUsername.setPreferredSize(componentSize);
        tfAddress.setPreferredSize(componentSize);
        tfPort.setPreferredSize(componentSize);
        btnConnect.setPreferredSize(componentSize);
        pnlOptions.add(lblUsername);
        pnlOptions.add(tfUsername);
        pnlOptions.add(lblAddress);
        pnlOptions.add(tfAddress);
        pnlOptions.add(lblPort);
        pnlOptions.add(tfPort);
        add(pnlOptions);
        add(btnConnect);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("NetworkChat");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new ConnectGUI());
                frame.pack();
                frame.setLocation(400, 300);
                frame.setVisible(true);
            }
        });
    }
}
