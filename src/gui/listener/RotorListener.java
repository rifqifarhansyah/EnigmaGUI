package gui.listener;

public interface RotorListener {
	public void configure(
			String[] leftRotor, String[] centerRotor, String[] rightRotor, 
			char leftStart, char centerStart, char rightStart, 
			char leftRing, char centerRing, char rightRing, 
			String reflector);
}
