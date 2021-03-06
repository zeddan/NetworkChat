package gui.panels;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.*;

/**
 * 
 * @author simonmansson 
 * Main chatWindow for presenting text and Images
 * 
 */
public class ChatWindow extends JPanel {
	private StyledDocument doc;
	private JTextPane chatWindow;
	private Style imgStyle;
	
	public ChatWindow() {
		this.setLayout(new BorderLayout());
		doc = new DefaultStyledDocument();
		imgStyle = doc.addStyle("imgStyle", null);
		chatWindow = new JTextPane(doc);
		chatWindow.setAutoscrolls(true);
		chatWindow.setEditable(false);
		chatWindow.setFont(new Font("Monospaced", Font.PLAIN, 12));
        DefaultCaret caret = (DefaultCaret) chatWindow.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		JScrollPane scroll = new JScrollPane(chatWindow, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setViewportView(chatWindow);
		this.add(scroll, BorderLayout.CENTER);
	}
	
	
	/**
	 * 
	 * @param message The chat message to append.
	 * @param image Message image to getRecipients. doesn't matter if its null.
	 */
	public void append(String message, Icon image) {
	
		try {
			doc.insertString(doc.getLength(), message + "\n", null);
			if (image != null) {
				StyleConstants.setIcon(imgStyle, image);
				doc.insertString(doc.getLength(), "ignored", imgStyle);
				doc.insertString(doc.getLength(), "\n", null);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}	
	}
}

