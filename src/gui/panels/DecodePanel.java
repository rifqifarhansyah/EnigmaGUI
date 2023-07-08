package gui.panels;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import enigma.Enigma;
import enigma.EnigmaOutput;
import gui.listener.TypeListener;

public class DecodePanel extends JPanel{
	
	private JTextArea inputText;
	private JTextArea outputText;
	private JTextArea processText;
	private JScrollPane spInput;
	private JScrollPane spOutput;
	private JScrollPane spProcess;
	private Enigma enigma;
	
	private TypeListener typeListener;
	
	public DecodePanel() {
		setLayout(new GridLayout());

		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(outerBorder);

		inputText = new JTextArea();
		outputText = new JTextArea();
		processText = new JTextArea();
		spInput = new JScrollPane(inputText);
		spOutput = new JScrollPane(outputText);
		spProcess = new JScrollPane(processText);

		spInput.setBorder(BorderFactory.createCompoundBorder(outerBorder, BorderFactory.createTitledBorder("Input")));
		spInput.setBackground(Color.white);
		spOutput.setBorder(BorderFactory.createCompoundBorder(outerBorder, BorderFactory.createTitledBorder("Output")));
		spOutput.setBackground(Color.white);
		spProcess.setBorder(BorderFactory.createCompoundBorder(outerBorder, BorderFactory.createTitledBorder("Process")));
		spProcess.setBackground(Color.white);

		outputText.setEditable(false);
		processText.setEditable(false);

		JSplitPane topSplitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, spOutput, spProcess);
		JSplitPane splitter = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topSplitter, spInput);
		splitter.setResizeWeight(0.5);
		splitter.setDividerSize(10);
		splitter.setContinuousLayout(true);

		inputText.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		outputText.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		processText.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		final UndoManager undoManager = new UndoManager();
		Document doc = inputText.getDocument();
		doc.addUndoableEditListener(new UndoableEditListener() {
		    public void undoableEditHappened(UndoableEditEvent e) {
		        undoManager.addEdit(e.getEdit());
		    }
		});

		InputMap im = inputText.getInputMap(JComponent.WHEN_FOCUSED);
		ActionMap am = inputText.getActionMap();
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Undo");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Redo");
		am.put("Undo", new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		        try {
		            if (undoManager.canUndo())
		                undoManager.undo();
		        } catch (CannotUndoException exp) {}
		    }
		});
		
		am.put("Redo", new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		        try {
		            if (undoManager.canRedo()) 
		                undoManager.redo();
		        } catch (CannotUndoException exp) {}
		    }
		});
		add(splitter);
		( (AbstractDocument) inputText.getDocument() ).setDocumentFilter(new DocumentFilter(){
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
				for (int i = 0; i < text.length(); i++) 
					if( (text.charAt(i) < 'a' || text.charAt(i) > 'z') && (text.charAt(i) < 'A' || text.charAt(i) > 'Z') && text.charAt(i) != ' ' && text.charAt(i) != '\n')
						return;
				text = text.toUpperCase();
				super.replace(fb, offset, length, text, attrs);
			}
		});
		
		// Add listener on document change
		inputText.getDocument().addDocumentListener(new DocumentListener() {
			public void removeUpdate(DocumentEvent e) {
				enigma.resetRotation();
				EnigmaOutput result = enigma.decrypt(inputText.getText());
				String output = result.getOutput();
				String process = result.getProcess();
				outputText.setText(output);
				processText.setText(process);
				typeListener.typeAction();
			}
			
			public void insertUpdate(DocumentEvent e) {
				enigma.resetRotation();
				EnigmaOutput result = enigma.decrypt(inputText.getText());
				String output = result.getOutput();
				String process = result.getProcess();
				outputText.setText(output);
				processText.setText(process);
				typeListener.typeAction();
			}
			public void changedUpdate(DocumentEvent e) {}
		});
	}

	public void setEnigma(Enigma enigma){
		this.enigma = enigma;
	}

	public void refresh(){
		inputText.setText(inputText.getText());
	}

	public void setTypeListener(TypeListener typeListener){
		this.typeListener = typeListener;
	}

	public void setDefaultText(String text){
		inputText.setText(text);
	}

	public String getOutput(){
		return outputText.getText();
	}

	public String getProcess(){
		return processText.getText();
	}
}

