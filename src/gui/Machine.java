package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.border.Border;

import enigma.Enigma;
import gui.listener.EnigmaMenuListener;
import gui.listener.RotorListener;
import gui.listener.TypeListener;
import gui.panels.EncodePanel;
import gui.panels.DecodePanel;
import gui.panels.PlugboardPanel;
import gui.panels.RotorsPanel;

public class Machine extends JFrame{
	
	private EncodePanel epanel;
	private DecodePanel dpanel;
	private RotorsPanel rpanel;
	private PlugboardPanel pbpanel;
	private Enigma enigma;
	private EnigmaMenuBar menuBar;
	
	private final int TEXT_MODE = 1;
	private final int DE_MODE = 2;
	private int mode = TEXT_MODE;
	
	public Machine() {
		super("Enigma GUI");

		ImageIcon icon = new ImageIcon(getClass().getResource("/gui/images/icon.png")); // Ubah path sesuai dengan lokasi file gambar PNG Anda
		setIconImage(icon.getImage());

		enigma = new Enigma(Enigma.I, Enigma.II, Enigma.III, Enigma.A);

		setLayout(new BorderLayout());

		menuBar = new EnigmaMenuBar(this);

		setJMenuBar(menuBar.getMenuBar());

		epanel = new EncodePanel();
		rpanel = new RotorsPanel();
		pbpanel = new PlugboardPanel();
		dpanel = new DecodePanel();

		epanel.setEnigma(enigma);
		rpanel.setEnigma(enigma);
		dpanel.setEnigma(enigma);

		add(epanel,BorderLayout.CENTER);
		add(rpanel,BorderLayout.NORTH);
		add(pbpanel,BorderLayout.SOUTH);

		rpanel.setRotorListener(new RotorListener() {
			public void configure(String[] leftRotor, String[] centerRotor,
					String[] rightRotor, char leftStart, char centerStart,
					char rightStart, char leftRing, char centerRing, char rightRing,
					String reflector) {

				enigma.getLeftRotor().setRotor(leftRotor);
				enigma.getCenterRotor().setRotor(centerRotor);
				enigma.getRightRotor().setRotor(rightRotor);

				enigma.getLeftRotor().setRotorHead(leftStart);
				enigma.getCenterRotor().setRotorHead(centerStart);
				enigma.getRightRotor().setRotorHead(rightStart);

				enigma.getLeftRotor().setRingHead(leftRing);
				enigma.getCenterRotor().setRingHead(centerRing);
				enigma.getRightRotor().setRingHead(rightRing);

				enigma.getReflector().setReflector(reflector);

				enigma.resetPlugboard();
				Scanner scan = new Scanner(pbpanel.getPlugboard());
				while(scan.hasNext()){
					String wire = scan.next();
					if(wire.length() == 2){
						char from = wire.charAt(0);
						char to = wire.charAt(1);
						if(!enigma.isPlugged(from) && !enigma.isPlugged(to) && from != to)
							enigma.insertPlugboardWire(from, to);
					}
				}
				scan.close();

				if(mode == TEXT_MODE)
					epanel.refresh();

				if(mode == DE_MODE)
					dpanel.refresh();
			}
		});

		epanel.setTypeListener(new TypeListener() {
			public void typeAction() {
				rpanel.setStates(enigma.getLeftRotor().getRotorHead(), enigma.getCenterRotor().getRotorHead(), enigma.getRightRotor().getRotorHead());
			}
		});

		dpanel.setTypeListener(new TypeListener() {
			public void typeAction() {
				rpanel.setStates(enigma.getLeftRotor().getRotorHead(), enigma.getCenterRotor().getRotorHead(), enigma.getRightRotor().getRotorHead());
			}
		});

		epanel.setDefaultText("AKU MAU MASUK IRK");

		menuBar.setEmlistener(new EnigmaMenuListener() {
			public void encryptDisplay(){
				mode = TEXT_MODE;
				epanel.setVisible(true);
				dpanel.setVisible(false);
				add(epanel, BorderLayout.CENTER);
				revalidate();

				epanel.setDefaultText("A");
				epanel.setDefaultText("");
			}

			public void decryptDisplay(){
				mode = DE_MODE;
				epanel.setVisible(false);
				dpanel.setVisible(true);
				add(dpanel, BorderLayout.CENTER);
				revalidate();

				dpanel.setDefaultText("A");
				dpanel.setDefaultText("");
			}

			public void importFile(String text) {
				if(mode != TEXT_MODE){
					JOptionPane.showMessageDialog(Machine.this, "You cannot import a file in this display");
					return;
				}
				epanel.setDefaultText(text);
			}
			
			public String exportFile() {
				if(mode == TEXT_MODE)
					return epanel.getOutput();
				return "-1";
			}

			public void restart() {
				Machine.this.dispose();
				epanel = null;
				dpanel = null;
				rpanel = null;
				pbpanel = null;
				enigma = null;
				menuBar = null;
				System.gc();
				new Machine();
			}
		});

		setVisible(true);
		Dimension dim = new Dimension(1150,700);
		setPreferredSize(dim);
		setMinimumSize(dim);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		pack();
	}
}
