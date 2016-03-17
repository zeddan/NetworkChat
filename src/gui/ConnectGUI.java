package gui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author zeddan
 *
 */
public class ConnectGUI extends JPanel {

    public interface ConnectGUIListener {
        void onConnect(String address, int port, String username);
    }

    private static final Integer WIN_WIDTH = 600;
    private static final Integer WIN_HEIGHT = 150;

    private JPanel pnlOptions = new JPanel(new GridLayout(3, 2));
    private JLabel lblUsername = new JLabel("Username");
    private JLabel lblAddress = new JLabel("Address");
    private JLabel lblPort = new JLabel("Port");
    private JTextField tfUsername = new JTextField();
    private JTextField tfAddress = new JTextField("localhost");
    private JTextField tfPort = new JTextField("40023");
    private JButton btnConnect = new JButton("Connect");
    private Dimension componentSize = new Dimension(200, 25);

    public ConnectGUI(ConnectGUIListener listener) {
        setPreferredSize(new Dimension(WIN_WIDTH, WIN_HEIGHT));
        setupConnectButtonListener(listener);
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

    private void setupConnectButtonListener(final ConnectGUIListener listener) {
        btnConnect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == btnConnect) {
                    String address = tfAddress.getText();
                    String portString = tfPort.getText();
                    String username = tfUsername.getText();
                    Integer port = null;
                    boolean error = false;

                    if (username.isEmpty()) {
                        showMessage("Enter a username");
                        error = true;
                        tfUsername.requestFocus();
                    } else if (portString.isEmpty()) {
                        showMessage("Port field must not be empty");
                        error = true;
                        tfPort.requestFocus();
                    } else if (address.isEmpty()) {
                        showMessage("Address field must not be empty");
                        error = true;
                        tfAddress.requestFocus();
                    } else {
                        try {
                            port = Integer.valueOf(portString);
                        } catch (NumberFormatException exception) {
                            showMessage("Invalid port number");
                            error = true;
                            tfPort.requestFocus();
                        }
                    }

                    if (!error)
                        listener.onConnect(address, port, username);
                }
            }
        });
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(getParent(), message);
    }

    //public static void main(String[] args) {
    //    SwingUtilities.invokeLater(new Runnable() {
    //        public void run() {
    //            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    //            JFrame frame = new JFrame("NetworkChat");
    //            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //            frame.add(new ConnectGUI(new ClientController()));
    //            frame.pack();
    //            frame.setLocation(
    //                    dim.width/2-frame.getSize().width/2,
    //                    dim.height/2-frame.getSize().height/2);
    //            frame.setVisible(true);
    //        }
    //    });
    //}
}
