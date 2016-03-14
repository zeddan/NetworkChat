package gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * @author zeddan
 *
 */
public class ServerGUI extends JPanel {

    private static final Integer WIN_WIDTH = 1024;
    private static final Integer WIN_HEIGHT = 600;

    private JPanel pnlLeft = new JPanel();
    private JPanel pnlMain = new JPanel();
    private JLabel lblPort = new JLabel("Port");
    private JTextField tfPort = new JTextField("80023");
    private JButton btnStartStop = new JButton("Start");

    public ServerGUI() {
        setPreferredSize(new Dimension(WIN_WIDTH, WIN_HEIGHT));
        setLayout(new BorderLayout());
        tfPort.setPreferredSize(new Dimension(200, 20));
        lblPort.setPreferredSize(new Dimension(200, 20));

        pnlLeft.setPreferredSize(new Dimension(WIN_WIDTH/4, WIN_HEIGHT));
        pnlLeft.setBorder(new LineBorder(Color.BLACK));
        pnlLeft.add(lblPort);
        pnlLeft.add(tfPort);
        pnlLeft.add(btnStartStop);

        pnlMain.setPreferredSize(new Dimension(WIN_WIDTH-WIN_WIDTH/4, WIN_HEIGHT));

        add(pnlLeft, BorderLayout.WEST);
        add(pnlMain, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                JFrame frame = new JFrame("Server");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new ServerGUI());
                frame.pack();
                frame.setLocation(
                        dim.width/2-frame.getSize().width/2,
                        dim.height/2-frame.getSize().height/2);
                frame.setVisible(true);
            }
        });
    }

}
