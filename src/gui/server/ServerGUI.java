package gui.server;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import gui.button.CustomButton;
import server.Controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author zeddan
 *
 */
public class ServerGUI extends JPanel implements ActionListener{

    private static final Integer WIN_WIDTH = 1024;
    private static final Integer WIN_HEIGHT = 600;
    private static final Integer PNL_LEFT_WIDTH = WIN_WIDTH/4;
    private static final Integer PNL_LEFT_HEIGHT = WIN_HEIGHT;

    private JPanel pnlLeft;
    private JPanel pnlMain;
    private JLabel lblPort;
    private JTextField tfPort;
    private JTextArea taLog;
    private CustomButton btnStartStop;
    private Controller controller = new Controller(this);
    private boolean started = false;

    public ServerGUI() {
        setPreferredSize(new Dimension(WIN_WIDTH, WIN_HEIGHT));
        setLayout(new BorderLayout());
        setBorder(null);
        pnlLeft = pnlLeft();
        pnlMain = pnlMain();
        add(pnlLeft, BorderLayout.WEST);
        add(pnlMain, BorderLayout.CENTER);
        readLog();
    }

    private void readLog() {
        LogReader logReader = new LogReader();
        taLog.setText(logReader.read());
    }
    
    public void writeLogToGUI(final String text){
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
		    	taLog.append(text);
			}
		});

    }

    private JTextArea taLog() {
        JTextArea taLog = new JTextArea();
        Dimension size = new Dimension(WIN_WIDTH-PNL_LEFT_WIDTH, WIN_HEIGHT);
        taLog.setPreferredSize(size);
        taLog.setBorder(new EmptyBorder(10, 10, 10, 10));
        return taLog;
    }

    private JPanel pnlMain() {
        JPanel pnlMain = new JPanel();
        pnlMain.setLayout(new BorderLayout());
        pnlMain.setPreferredSize(new Dimension(WIN_WIDTH-WIN_WIDTH/4, WIN_HEIGHT));
        taLog = taLog();
        pnlMain.add(taLog);
        return pnlMain;
    }

    private CustomButton btnStartStop() {
        CustomButton btnStartStop = new CustomButton("Start");
        btnStartStop.addActionListener(this);
        return btnStartStop;
    }

    private JLabel lblPort() {
        JLabel lblPort = new JLabel("Port");
        lblPort.setPreferredSize(new Dimension(200, 20));
        return lblPort;
    }

    private JTextField tfPort() {
        JTextField tfPort = new JTextField("40023");
        tfPort.setPreferredSize(new Dimension(200, 20));
        return tfPort;
    }

    private JPanel pnlLeft() {
        JPanel pnlLeft = new JPanel();
        pnlLeft.setPreferredSize(new Dimension(PNL_LEFT_WIDTH, PNL_LEFT_HEIGHT));
        lblPort = lblPort();
        tfPort = tfPort();
        btnStartStop = btnStartStop();
        pnlLeft.add(lblPort);
        pnlLeft.add(tfPort);
        pnlLeft.add(btnStartStop);
        return pnlLeft;
    }

	/**
	 * Listener. Listen for button click. Starts the server. 
	 */
    
	public void actionPerformed(ActionEvent ae) {
		if(!started){
			controller.startServer(Integer.parseInt(tfPort.getText()));
			btnStartStop.setText("Stop");
            started = true;
		} else {
			controller.stopServer();
			btnStartStop.setText("Start");
            started = false;
		}
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
