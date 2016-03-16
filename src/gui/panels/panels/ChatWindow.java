package gui.panels;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * 
 * @author simonmansson
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
		JScrollPane scroll = new JScrollPane(chatWindow, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setViewportView(chatWindow);
		this.add(scroll, BorderLayout.CENTER);
	}
	
	
	/**
	 * 
	 * @param message the chat message to append
	 * @param image message image. doesn't matter if its null.
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

