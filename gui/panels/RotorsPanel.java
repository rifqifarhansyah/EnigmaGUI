package gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import enigma.Enigma;
import gui.listener.RotorListener;

public class RotorsPanel extends JPanel{
	
	private GridBagConstraints gc;
	private JComboBox reflectorBox;
	private JComboBox leftRotorBox;
	private JComboBox centerRotorBox;
	private JComboBox rightRotorBox;
	
	private JComboBox leftRotorStartBox;
	private JComboBox centerRotorStartBox;
	private JComboBox rightRotorStartBox;
	
	private JComboBox leftRotorRingBox;
	private JComboBox centerRotorRingBox;
	private JComboBox rightRotorRingBox;
	
	private JButton applyBtn;
	
	private JTextField leftState;
	private JTextField centerState;
	private JTextField rightState;
	
	private JPanel reflectorPanel;
	private JPanel leftRotorPanel;
	private JPanel centerRotorPanel;
	private JPanel rightRotorPanel;
	private JPanel stateRotorPanel;
	
	private String[][] ENIGMA_ROTORS = {Enigma.I, Enigma.II, Enigma.III, Enigma.IV, Enigma.V}; 
	private String[] ENIGMA_REFLECTORS = {Enigma.A, Enigma.B, Enigma.C}; 
	
	private RotorListener rotorListener;
	private Enigma enigma;
	
	private JLabel rLabels[] = {
			new JLabel("Type: "),
			new JLabel("Type: "),
			new JLabel("Initial: "),
			new JLabel("Ring: "),
			new JLabel("Type: "),
			new JLabel("Initial: "),
			new JLabel("Ring: "),
			new JLabel("Type: "),
			new JLabel("Initial: "),
			new JLabel("Ring: "),
			new JLabel("L"),
			new JLabel("C"),
			new JLabel("R")
	};
	
	private TitledBorder titleBorders[] = {
			BorderFactory.createTitledBorder("Reflector"),
			BorderFactory.createTitledBorder("Left Rotor"),
			BorderFactory.createTitledBorder("Center Rotor"),
			BorderFactory.createTitledBorder("Right Rotor"),
			BorderFactory.createTitledBorder("State")
	};
	
	public RotorsPanel() {
		
		// Layout grid
		setLayout(new GridBagLayout());
		
		// Panel
		reflectorPanel = new JPanel();
		reflectorPanel.setLayout(new GridBagLayout());
		reflectorPanel.setPreferredSize(new Dimension(100,100));
		
		leftRotorPanel = new JPanel();
		leftRotorPanel.setLayout(new GridBagLayout());
		leftRotorPanel.setPreferredSize(new Dimension(220,100));
		
		centerRotorPanel = new JPanel();
		centerRotorPanel.setLayout(new GridBagLayout());
		centerRotorPanel.setPreferredSize(new Dimension(220,100));
		
		rightRotorPanel = new JPanel();
		rightRotorPanel.setLayout(new GridBagLayout());
		rightRotorPanel.setPreferredSize(new Dimension(220,100));
		
		stateRotorPanel = new JPanel();
		stateRotorPanel.setLayout(new GridBagLayout());
		stateRotorPanel.setPreferredSize(new Dimension(130,100));
		
		// Komponen
		String[] rotorBoxOption = {"I" ,"II", "III", "IV", "V"};
		String[] rotorBoxLetterOption = new String[26];
		for(int i=0; i < 26; i++) rotorBoxLetterOption[i] = (char) ('A' + i)+"";
		
		// Label
		leftState = new JTextField("A", 1);
		leftState.setEditable(false);
		centerState = new JTextField("A", 1);
		centerState.setEditable(false);
		rightState = new JTextField("A", 1);
		rightState.setEditable(false);
		
		// Reflektor
		String[] reflectorBoxOption = {"A" ,"B", "C"};
		reflectorBox = new JComboBox(reflectorBoxOption);
		reflectorBox.setSelectedIndex(0);
		
		// Left Rotor
		leftRotorBox = new JComboBox(rotorBoxOption);
		leftRotorBox.setSelectedIndex(0);
		
		// Center Rotor
		centerRotorBox = new JComboBox(rotorBoxOption);
		centerRotorBox.setSelectedIndex(1);
		
		// Right Rotor
		rightRotorBox = new JComboBox(rotorBoxOption);
		rightRotorBox.setSelectedIndex(2);
		
		// Left Init
		leftRotorStartBox = new JComboBox(rotorBoxLetterOption);
		leftRotorStartBox.setSelectedIndex(0);
	
		// Center Init
		centerRotorStartBox = new JComboBox(rotorBoxLetterOption);
		centerRotorStartBox.setSelectedIndex(0);
		
		// Right Init
		rightRotorStartBox = new JComboBox(rotorBoxLetterOption);
		rightRotorStartBox.setSelectedIndex(0);

		// Left Ring
		leftRotorRingBox = new JComboBox(rotorBoxLetterOption);
		leftRotorRingBox.setSelectedIndex(0);
	
		// Center Ring
		centerRotorRingBox = new JComboBox(rotorBoxLetterOption);
		centerRotorRingBox.setSelectedIndex(0);
		
		// Right Ring
		rightRotorRingBox = new JComboBox(rotorBoxLetterOption);
		rightRotorRingBox.setSelectedIndex(0);
		
		// Tombol Set
		applyBtn = new JButton("SET");
		applyBtn.setBorderPainted(false);
		applyBtn.setFocusPainted(false);
		applyBtn.setForeground(Color.black);
		applyBtn.setPreferredSize(new Dimension(86, 85));
		applyBtn.setFont(new Font("Arial", Font.PLAIN, 14));
		applyBtn.setBackground(new Color(219, 235, 245)); // Mengatur warna latar belakang tombol

		// Membuat efek hover saat di klik
		applyBtn.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				applyBtn.setBackground(new Color(209, 225, 235)); // Mengubah warna latar belakang saat dihover
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				applyBtn.setBackground(new Color(219, 235, 245)); // Mengubah warna latar belakang saat tidak dihover
			}
		});

		// Membuat efek hover saat di klik
		applyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				applyBtn.setBackground(new Color(199, 215, 225)); // Mengubah warna latar belakang saat diklik
			}
		});
		
		// Tambah border pada panel
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		reflectorPanel.setBorder(BorderFactory.createCompoundBorder(outerBorder, titleBorders[0]));
		leftRotorPanel.setBorder(BorderFactory.createCompoundBorder(outerBorder, titleBorders[1]));
		centerRotorPanel.setBorder(BorderFactory.createCompoundBorder(outerBorder, titleBorders[2]));
		rightRotorPanel.setBorder(BorderFactory.createCompoundBorder(outerBorder, titleBorders[3]));
		stateRotorPanel.setBorder(BorderFactory.createCompoundBorder(outerBorder, titleBorders[4]));
		
		// Grid Default
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.NONE;
		gc.insets = new Insets(0, 10, 0, 10);
		gc.anchor = GridBagConstraints.NORTHWEST;
		int y = 0;
		gc.weightx = 1;
		gc.weighty = 0.15;
		
		// Kotak Reflector
		gridPosition(0, y);
		reflectorPanel.add(rLabels[0],gc);
		gridPosition(0, y+1);
		reflectorPanel.add(reflectorBox, gc);
		
		// Kotak LeftRotor
		gridPosition(0, y);
		leftRotorPanel.add(rLabels[1],gc);
		gridPosition(0, y+1);
		leftRotorPanel.add(leftRotorBox, gc);
		gridPosition(1, y);
		leftRotorPanel.add(rLabels[2],gc);
		gridPosition(1, y+1);
		leftRotorPanel.add(leftRotorStartBox, gc);
		gridPosition(2, y);
		leftRotorPanel.add(rLabels[3],gc);
		gridPosition(2, y+1);
		leftRotorPanel.add(leftRotorRingBox, gc);
		
		// Kotak CenterRotor
		gridPosition(0, y);
		centerRotorPanel.add(rLabels[4],gc);
		gridPosition(0, y+1);
		centerRotorPanel.add(centerRotorBox, gc);
		gridPosition(1, y);
		centerRotorPanel.add(rLabels[5],gc);
		gridPosition(1, y+1);
		centerRotorPanel.add(centerRotorStartBox, gc);
		gridPosition(2, y);
		centerRotorPanel.add(rLabels[6],gc);
		gridPosition(2, y+1);
		centerRotorPanel.add(centerRotorRingBox, gc);
		
		// Kotak RightRotor
		gridPosition(0, y);
		rightRotorPanel.add(rLabels[7],gc);
		gridPosition(0, y+1);
		rightRotorPanel.add(rightRotorBox, gc);
		gridPosition(1, y);
		rightRotorPanel.add(rLabels[8],gc);
		gridPosition(1, y+1);
		rightRotorPanel.add(rightRotorStartBox, gc);
		gridPosition(2, y);
		rightRotorPanel.add(rLabels[9],gc);
		gridPosition(2, y+1);
		rightRotorPanel.add(rightRotorRingBox, gc);
		
		// Kotak StateRotor
		gridPosition(0, y);
		stateRotorPanel.add(rLabels[10],gc);
		gridPosition(0, y+1);
		stateRotorPanel.add(leftState, gc);
		gridPosition(1, y);
		stateRotorPanel.add(rLabels[11],gc);
		gridPosition(1, y+1);
		stateRotorPanel.add(centerState, gc);
		gridPosition(2, y);
		stateRotorPanel.add(rLabels[12],gc);
		gridPosition(2, y+1);
		stateRotorPanel.add(rightState, gc);
		
		// Panel
		add(reflectorPanel);
		add(leftRotorPanel);
		add(centerRotorPanel);
		add(rightRotorPanel);
		add(stateRotorPanel);
		add(applyBtn);
		
		// Button Listener
		applyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rotorListener.configure(
						ENIGMA_ROTORS[leftRotorBox.getSelectedIndex()], ENIGMA_ROTORS[centerRotorBox.getSelectedIndex()], ENIGMA_ROTORS[rightRotorBox.getSelectedIndex()],
						(char) ('A' + leftRotorStartBox.getSelectedIndex()), (char) ('A' + centerRotorStartBox.getSelectedIndex()), (char) ('A' + rightRotorStartBox.getSelectedIndex()), 
						(char) ('A' + leftRotorRingBox.getSelectedIndex()), (char) ('A' + centerRotorRingBox.getSelectedIndex()), (char) ('A' + rightRotorRingBox.getSelectedIndex()), 
						ENIGMA_REFLECTORS[reflectorBox.getSelectedIndex()]);
				
				// Update State
				leftState.setText( enigma.getLeftRotor().getRotorHead() + "");
				centerState.setText( enigma.getCenterRotor().getRotorHead() + "");
				rightState.setText( enigma.getRightRotor().getRotorHead() + "");
			}
		});
		
		// Hapus backgrpund panel
		reflectorPanel.setOpaque(false);
		leftRotorPanel.setOpaque(false);
		centerRotorPanel.setOpaque(false);
		rightRotorPanel.setOpaque(false);
		stateRotorPanel.setOpaque(false);
		
		// Atur Ukuran
		setPreferredSize(new Dimension(100,100));
	}

	public void setRotorListener(RotorListener rotorListener){
		this.rotorListener = rotorListener; 
	}

	public void setStates(char l, char c, char r){
		leftState.setText(l+"");
		centerState.setText(c+"");
		rightState.setText(r+"");
	}

	public void setEnigma(Enigma enigma){
		this.enigma = enigma;
	}

	private void gridPosition(int x, int y) {
		gc.gridx = x;
		gc.gridy = y;
	}
}