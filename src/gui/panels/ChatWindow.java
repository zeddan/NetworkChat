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
	
	public void append(String text, Icon image) {
	
		try {
			doc.insertString(doc.getLength(), text + "\n", null);
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

