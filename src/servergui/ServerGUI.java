package serverGUI;

import javax.swing.*;
import javax.swing.border.LineBorder;

import server.Controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerGUI extends JPanel implements ActionListener{
	private Controller controller = new Controller(this);
    private static final Integer WIN_WIDTH = 1024;
    private static final Integer WIN_HEIGHT = 600;
    private JPanel pnlLeft = new JPanel();
    private JPanel pnlMain = new JPanel();
    private JLabel lblPort = new JLabel("Port");
    private JTextField tfPort = new JTextField("40023");
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
        
        btnStartStop.addActionListener(this);

        pnlMain.setPreferredSize(new Dimension(WIN_WIDTH-WIN_WIDTH/4, WIN_HEIGHT));

        add(pnlLeft, BorderLayout.WEST);
        add(pnlMain, BorderLayout.CENTER);
    }
    
	/**
	 * Listener. Listen for button click. Starts the server. 
	 */
	public void actionPerformed(ActionEvent ae) {
		if(btnStartStop.getText().equals("Start")){
			btnStartStop.setText("Stop");
			controller.startServer(Integer.parseInt(tfPort.getText()));
			btnStartStop.setText("Stop");
		} else {
			controller.stopServer();
			btnStartStop.setText("Start");
		}
	} 

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Server");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new ServerGUI());
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

}
