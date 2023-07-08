package enigma;
public class Enigma {
	private Rotor rightRotor;
	private Rotor centerRotor;
	private Rotor leftRotor;
	private Reflector reflector;
	private int[] plugboard;
	
	// data rotor
	public static final String[] I = {"EKMFLGDQVZNTOWYHXUSPAIBRCJ", "Q"};
	public static final String[] II = {"AJDKSIRUXBLHWTMCQGZNPYFVOE", "E"};
	public static final String[] III = {"BDFHJLCPRTXVZNYEIWGAKMUSQO", "V"};
	public static final String[] IV = {"ESOVPZJAYQUIRHXLNFTGKDCMWB", "J"};
	public static final String[] V = {"VZBRGITYUPSDNHLXAWMJQOFECK", "Z"};
	
	// data reflektor
	public static final String A = "EJMZALYXVBWFCRQUONTSPIKHGD";
	public static final String B = "YRUHQSLDPXNGOKMIEBFZCWVJAT";
	public static final String C = "FVPJIAOYEDRZXWGCTKUQSBNMHL";
	
	public Enigma(String[] left,String[] center,String[] right,String ref){
		if(!correctRotor(left) || !correctRotor(center) || !correctRotor(right))
			throw new RuntimeException("Please choose a correct Rotor");
		
		if(!correctReflector(ref))
			throw new RuntimeException("Please choose a correct Reflector");
		
		// assign rotor dan reflektor
		this.leftRotor = new Rotor(left[0], left[1].charAt(0));
		this.centerRotor = new Rotor(center[0], center[1].charAt(0));
		this.rightRotor = new Rotor(right[0], right[1].charAt(0));
		this.reflector = new Reflector(ref);
		
		// inisialisasi plugboard
		plugboard = new int[26];
		
		// reset plugboard
		resetPlugboard();
	}
	
	private boolean correctRotor(String[] rotor){
		return rotor == I || rotor == II || rotor == III || rotor == IV || rotor == V;
	}
	
	private boolean correctReflector(String reflector){
		return reflector == A || reflector == B || reflector == C;
	}
	
	// Encode
	public EnigmaOutput encrypt(String text){
		String output = "";
		String process = "";
		RotorEncrypt result;
		for (int i=0; i<text.length(); i++){
			
			// Karakter harus uppercase
			if(text.charAt(i) >= 'A' && text.charAt(i) <= 'Z'){
				result = rotorsEncryption(text.charAt(i));
				output += result.getOutput();
				process += result.getProcess();
			}
			// Karakter Spasi dan Newline tidak di encode
			else if(text.charAt(i) == ' ' || text.charAt(i) == '\n')
				output += text.charAt(i);
			
			// Karakter lainnya
			else
				throw new RuntimeException("Only upper case letters allowed!");
		}
		return new EnigmaOutput(output, process);
	}

	public EnigmaOutput decrypt(String text){
		String output = "";
		String process = "";
		RotorEncrypt result;
		for (int i = 0; i <text.length(); i++) {
			// Karakter harus uppercase
			if (text.charAt(i) >= 'A' && text.charAt(i) <= 'Z') {
				result = rotorsDecryption(text.charAt(i));
				output += result.getOutput();
				process += result.getProcess();
			}
			// Karakter Spasi dan Newline tidak di encode
			else if (text.charAt(i) == ' ' || text.charAt(i) == '\n') {
				output += text.charAt(i);
			}
			// Karakter lainnya
			else {
				throw new RuntimeException("Only upper case letters allowed!");
			}
		}
		
		return new EnigmaOutput(output, process);
	}

	private RotorEncrypt rotorsEncryption(char inputC){
		String process = "";
		process += "Keyboard Input: " + inputC + "\n";
		process += "Rotors Position: " + leftRotor.getRotorHead() + centerRotor.getRotorHead() + rightRotor.getRotorHead() + "\n";
		if (centerRotor.getNotch() == centerRotor.getRotorHead()){
			leftRotor.rotate();
			centerRotor.rotate();
		}

		if (rightRotor.getNotch() == rightRotor.getRotorHead())
			centerRotor.rotate();

		rightRotor.rotate();

		int input = inputC - 'A';

		if(plugboard[input] != -1)
			input = plugboard[input];

		char plugboardEncryption = (char) (input + 'A');
		process += "Plugboard Encryption: " + plugboardEncryption + "\n";

		int outOfrightRotor = rightRotor.getOutputOf(input);
		char outOfRightRotorChar = (char) (outOfrightRotor + 'A');
		process += "Wheel 3 Encryption: " + outOfRightRotorChar + "\n";

		int outOfcenterRotor = centerRotor.getOutputOf(outOfrightRotor);
		char outOfCenterRotorChar = (char) (outOfcenterRotor + 'A');
		process += "Wheel 2 Encryption: " + outOfCenterRotorChar + "\n";

		int outOfleftRotor = leftRotor.getOutputOf(outOfcenterRotor);
		char outOfLeftRotorChar = (char) (outOfleftRotor + 'A');
		process += "Wheel 1 Encryption: " + outOfLeftRotorChar + "\n";

		int outOfReflector = reflector.getOutputOf(outOfleftRotor);
		char outOfRefelctorChar = (char) (outOfReflector + 'A');
		process += "Reflector Encryption: " + outOfRefelctorChar + "\n";

		int inOfleftRotor = leftRotor.getInputOf(outOfReflector);
		char inOfLeftRotorChar = (char) (inOfleftRotor + 'A');
		process += "Wheel 1 Encryption: " + inOfLeftRotorChar + "\n";

		int inOfcenterRotor = centerRotor.getInputOf(inOfleftRotor);
		char inOfCenterRotorChar = (char) (inOfcenterRotor + 'A');
		process += "Wheel 2 Encryption: " + inOfCenterRotorChar + "\n";

		int inOfrightRotor = rightRotor.getInputOf(inOfcenterRotor);
		char inOfRightRotorChar = (char) (inOfrightRotor + 'A');
		process += "Wheel 3 Encryption: " + inOfRightRotorChar + "\n";

		if(plugboard[inOfrightRotor] != -1)
			inOfrightRotor = plugboard[inOfrightRotor];
		char reversedPlugboardEncryption = (char)(inOfrightRotor+'A');

		process += "Plugboard Encryption: " + reversedPlugboardEncryption + "\n";
		process += "Output (Lampboard): " + reversedPlugboardEncryption + "\n";
		process += "---------------------------------" + "\n";
		return new RotorEncrypt(reversedPlugboardEncryption, process);
	}

	private RotorEncrypt rotorsDecryption(char inputC) {
		String process = "";
		process += "Keyboard Input: " + inputC + "\n";
		process += "Rotors Position: " + leftRotor.getRotorHead() + centerRotor.getRotorHead() + rightRotor.getRotorHead() + "\n";

		if (centerRotor.getNotch() == centerRotor.getRotorHead()){
			leftRotor.rotate();
			centerRotor.rotate();
		}

		if (rightRotor.getNotch() == rightRotor.getRotorHead())
			centerRotor.rotate();

		rightRotor.rotate();

		int input = inputC - 'A';

		if(plugboard[input] != -1)
			input = plugboard[input];

		char plugboardEncryption = (char) (input + 'A');
		process += "Plugboard Decryption: " + plugboardEncryption + "\n";

		int outOfrightRotor = rightRotor.getOutputOf(input);
		char outOfRightRotorChar = (char) (outOfrightRotor + 'A');
		process += "Wheel 3 Decryption: " + outOfRightRotorChar + "\n";

		int outOfcenterRotor = centerRotor.getOutputOf(outOfrightRotor);
		char outOfCenterRotorChar = (char) (outOfcenterRotor + 'A');
		process += "Wheel 2 Decryption: " + outOfCenterRotorChar + "\n";

		int outOfleftRotor = leftRotor.getOutputOf(outOfcenterRotor);
		char outOfLeftRotorChar = (char) (outOfleftRotor + 'A');
		process += "Wheel 1 Decryption: " + outOfLeftRotorChar + "\n";

		int outOfReflector = reflector.getOutputOf(outOfleftRotor);
		char outOfRefelctorChar = (char) (outOfReflector + 'A');
		process += "Reflector Decryption: " + outOfRefelctorChar + "\n";

		int inOfleftRotor = leftRotor.getInputOf(outOfReflector);
		char inOfLeftRotorChar = (char) (inOfleftRotor + 'A');
		process += "Wheel 1 Decryption: " + inOfLeftRotorChar + "\n";

		int inOfcenterRotor = centerRotor.getInputOf(inOfleftRotor);
		char inOfCenterRotorChar = (char) (inOfcenterRotor + 'A');
		process += "Wheel 2 Decryption: " + inOfCenterRotorChar + "\n";

		int inOfrightRotor = rightRotor.getInputOf(inOfcenterRotor);
		char inOfRightRotorChar = (char) (inOfrightRotor + 'A');
		process += "Wheel 3 Decryption: " + inOfRightRotorChar + "\n";

		if(plugboard[inOfrightRotor] != -1)
			inOfrightRotor = plugboard[inOfrightRotor];
		char reversedPlugboardEncryption = (char)(inOfrightRotor+'A');

		process += "Plugboard Decryption: " + reversedPlugboardEncryption + "\n";
		process += "Output (Lampboard): " + reversedPlugboardEncryption + "\n";
		process += "---------------------------------" + "\n";
		return new RotorEncrypt(reversedPlugboardEncryption, process);
	}
	
	

	public Rotor getLeftRotor(){
		return leftRotor;
	}

	public Rotor getCenterRotor(){
		return centerRotor;
	}

	public Rotor getRightRotor(){
		return rightRotor;
	}

	public Reflector getReflector(){
		return reflector;
	}

	public int getPlugboardOf(int a){
		return this.plugboard[a];
	}

	public void insertPlugboardWire(char a, char b){
		this.plugboard[ a - 'A' ] = b - 'A';
		this.plugboard[ b - 'A' ] = a - 'A';
	}

	public void removePlugboardWire(char a){
		this.plugboard[ this.plugboard[ a - 'A' ] ] = -1;
		this.plugboard[ a - 'A' ] = -1;
	}

	public void resetPlugboard(){
		for (int wire = 0; wire < 26; wire++)
			this.plugboard[ wire ] = -1;
	}

	public boolean isPlugged(char c){
		return plugboard[c - 'A'] != -1;
	}

	public void resetRotation(){
		leftRotor.reset();
		centerRotor.reset();
		rightRotor.reset();
	}
}