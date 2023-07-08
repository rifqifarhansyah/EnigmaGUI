package enigma;

public class Rotor {
    private int rotorOut[] = new int[26];
    private int rotorIn[] = new int[26];
    private int rotorHead;
    private int ringHead;
    private char notch;
    private int rotate;

    protected Rotor(String rotor, char notch) {
        setRotor(new String[]{rotor, notch + ""});
    }

    protected int getOutputOf(int pos) {
        int rotorRingDiff = rotorHead >= ringHead ? rotorHead - ringHead : 26 - ringHead + rotorHead;
        return (pos + rotorOut[(pos + rotate + rotorRingDiff) % 26]) % 26;
    }

    protected int getInputOf(int pos) {
        int rotorRingDiff = rotorHead >= ringHead ? rotorHead - ringHead : 26 - ringHead + rotorHead;
        int posJump = pos - rotorIn[(pos + rotate + rotorRingDiff) % 26];
        return posJump > 0 ? (posJump % 26) : (26 + posJump) % 26;
    }

    protected int getOutputOfDecrypt(int pos) {
        int rotorRingDiff = rotorHead >= ringHead ? rotorHead - ringHead : 26 - ringHead + rotorHead;
        int posJump = pos - rotorOut[(pos + rotate + rotorRingDiff) % 26];
        return posJump >= 0 ? (posJump % 26) : (26 + posJump) % 26;
    }

    protected int getInputOfDecrypt(int pos) {
        int rotorRingDiff = rotorHead >= ringHead ? rotorHead - ringHead : 26 - ringHead + rotorHead;
        int posJump = pos - rotorIn[(pos + rotate + rotorRingDiff) % 26];
        return posJump >= 0 ? (posJump % 26) : (26 + posJump) % 26;
    }

    public char getNotch() {
        return notch;
    }

    public char getRotorHead() {
        return (char) ('A' + (rotorHead + rotate) % 26);
    }

    public char getRingHead() {
        return (char) ('A' + (ringHead + rotate) % 26);
    }

    protected void rotate() {
        rotate = (rotate + 1) % 26;
    }

    protected void rotateBackward() {
        rotate = (rotate - 1 + 26) % 26;
    }

    public void setRotorHead(char c) {
        if (c < 'A' || c > 'Z')
            throw new RuntimeException("Only upper case letters allowed!");
        rotorHead = c - 'A';
        rotate = 0;
    }

    public void setRingHead(char c) {
        if (c < 'A' || c > 'Z')
            throw new RuntimeException("Only upper case letters allowed!");
        ringHead = c - 'A';
    }

    public void setRotor(String[] rotor) {
        this.notch = rotor[1].charAt(0);
        for (int i = 0; i < 26; i++) {
            int from = (char) ('A' + i);
            int to = rotor[0].charAt(i);
            rotorOut[i] = from < to ? to - from : (26 - (from - to)) % 26;
            rotorIn[(i + rotorOut[i]) % 26] = rotorOut[i];
        }
    }

    public void reset() {
        rotate = 0;
    }
}
