package gui.panels;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class PlugboardPanel extends JPanel{
	
	private JTextField pbField;
	private TitledBorder innerBorder;
	
	public PlugboardPanel() {
		
		// Inisialisasi Component
		pbField = new JTextField(95);
		pbField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
		// Tambah Border
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		innerBorder = BorderFactory.createTitledBorder("Plugboard (example: AB CD)");
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
		
		// Tambah Komponen
		add(pbField);
		
		// Pengubah ke uppercase
		( (AbstractDocument) pbField.getDocument() ).setDocumentFilter(new DocumentFilter(){
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
				for (int i = 0; i < text.length(); i++) 
					if( (text.charAt(i) < 'a' || text.charAt(i) > 'z') && (text.charAt(i) < 'A' || text.charAt(i) > 'Z') && text.charAt(0) != ' ')
						return;
				super.replace(fb, offset, length, text.toUpperCase(), attrs);
			}
		});
	}

	public String getPlugboard(){
		return pbField.getText();
	}
}

