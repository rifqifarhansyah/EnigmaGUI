package enigma;
public class Reflector {
	private int rotorOut[] = new int[26];

	protected Reflector(String reflector){
		setReflector(reflector);
	}

	protected int getOutputOf(int pos){
		return (pos + rotorOut[pos]) % 26;
	}
	
	public void setReflector(String reflector){
		for (int i = 0; i < 26; i++){
			int from = (char) ('A' + i);
			int to = reflector.charAt(i);
			rotorOut[i] = from < to ? to - from : (26 - (from - to)) % 26;
		}
	}
}
