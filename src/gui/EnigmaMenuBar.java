package gui;

import gui.listener.EnigmaMenuListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;

public class EnigmaMenuBar {

	private JMenuBar menuBar;
	private File lastFile;
	private Scanner fileInputReader;
	private PrintWriter fileOutputWriter;
	private JFileChooser fileChooser;
	private String FILE_EXTENSION = "txt";
	private EnigmaMenuListener emlistener;
	
	public EnigmaMenuBar(final JFrame frame) {
		menuBar = new JMenuBar();

		fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new FileFilter() {

			public String getDescription() {
				return FILE_EXTENSION;
			}

			public boolean accept(File file) {
				String[] extentions = {FILE_EXTENSION};
				if (file.isDirectory()) {
					return true;
			    } else {
			      String path = file.getAbsolutePath().toLowerCase();
			      for (int i = 0, n = extentions.length; i < n; i++) {
			        String extension = extentions[i];
			        if ((path.endsWith(extension) && (path.charAt(path.length() - extension.length() - 1)) == '.')) 
			          return true;
			      }
			    }
			    return false;
			}
		});
		
		JMenu file = new JMenu("File");
		JMenuItem importTxt = new JMenuItem("Import text");
		JMenuItem exportTxt = new JMenuItem("Export text");
		JMenuItem restart = new JMenuItem("Restart");
		JMenuItem exit = new JMenuItem("Exit");
		file.add(importTxt);
		file.add(exportTxt);
		file.add(restart);
		file.add(exit);
		JRadioButtonMenuItem textBox = new JRadioButtonMenuItem("Text box");
		textBox.setSelected(true);

		JMenu display = new JMenu("Display");
		ButtonGroup displayGroup = new ButtonGroup();
		JRadioButtonMenuItem encrypt = new JRadioButtonMenuItem("Encrypt");
		JRadioButtonMenuItem decrypt = new JRadioButtonMenuItem("Decrypt");
		textBox.setSelected(true);
		displayGroup.add(encrypt);
		displayGroup.add(decrypt);
		display.add(encrypt);
		display.add(decrypt);

		menuBar.add(file);
		menuBar.add(display);
		
		encrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0){
				emlistener.encryptDisplay();
			}
		});

		decrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0){
				emlistener.decryptDisplay();
			}
		});

		restart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emlistener.restart();
			}
		});
		
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});

		importTxt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,ActionEvent.CTRL_MASK));
		importTxt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(lastFile != null)
					fileChooser.setCurrentDirectory(lastFile);
				
				if(fileChooser.showOpenDialog(frame) == JFileChooser.CANCEL_OPTION)
					return;
				
				lastFile = fileChooser.getSelectedFile();
				
				if(lastFile != null){
					if(getExtension(lastFile).equalsIgnoreCase(FILE_EXTENSION)){
						try {
							fileInputReader = new Scanner(lastFile);
							
							String text = "";
							while(fileInputReader.hasNextLine())
								text += fileInputReader.nextLine()+"\n";
							emlistener.importFile(text);
							
						} catch (FileNotFoundException e) {
							JOptionPane.showMessageDialog(frame, "An error occurred while loading the file.");
						} finally{
							if(fileInputReader != null)
								fileInputReader.close();
						}
						
					}else{
						JOptionPane.showMessageDialog(frame, "You selected an unsupported file input.");
					}
				}
			}
		});

		exportTxt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,ActionEvent.CTRL_MASK));
		exportTxt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String text = emlistener.exportFile();
				if(text.isEmpty()){
					JOptionPane.showMessageDialog(frame, "You can't export an empty file");
					return;

				}

				if(lastFile != null)
					fileChooser.setCurrentDirectory(lastFile);

				if(fileChooser.showSaveDialog(frame) == JFileChooser.CANCEL_OPTION)
					return;

				lastFile = fileChooser.getSelectedFile();

				if(lastFile != null){

					if(lastFile.exists() && JOptionPane.showConfirmDialog(frame, "Would you like to overwrite the existing file ?", "Overwite existing file", JOptionPane.YES_NO_OPTION) == JOptionPane.CANCEL_OPTION)
						return;
					
					try {
						if(getExtension(lastFile).equalsIgnoreCase(FILE_EXTENSION))
							fileOutputWriter = new PrintWriter(lastFile.getAbsolutePath());
						else
							fileOutputWriter = new PrintWriter(lastFile.getAbsolutePath()+ "." + FILE_EXTENSION);
						fileOutputWriter.print(emlistener.exportFile());
						JOptionPane.showMessageDialog(frame, "File output exported successfully");
					} catch (FileNotFoundException e) {
						JOptionPane.showMessageDialog(frame, "An error occured while exporting the file");
					} finally{
						if(fileOutputWriter != null)
							fileOutputWriter.close();
					}
				}
			}
		});
		
	}

	public JMenuBar getMenuBar(){
		return menuBar;
	}
	
	private String getExtension(File f) {
        String ext = "";
        String s = f.getName();
        int i = s.lastIndexOf('.');
        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
	
	public void setEmlistener(EnigmaMenuListener emlistener) {
		this.emlistener = emlistener;
	}
}
