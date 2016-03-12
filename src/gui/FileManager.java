package gui;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 * Del av gruppuppgift DA343A
 * @author Gustav Frigren
 *
 */
public class FileManager {
	
	private JFrame frame;
	
	public String getImageURL() {
		JFileChooser imageChooser = new JFileChooser();
		//imageChooser.setFileFilter(filter); //TODO beroende på vilka filtyper som ska visas...
		imageChooser.setDialogTitle("Choose imagefile:");
		imageChooser.showOpenDialog(frame);
		
		//TODO kan behövas en formatering av imageURL beroende på operativsystem?
		return (imageChooser.getCurrentDirectory() + "/" + imageChooser.getName(imageChooser.getSelectedFile()));
	}

}
