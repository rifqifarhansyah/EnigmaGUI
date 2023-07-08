package enigma;

public class RotorEncrypt {
    private char output;
    private String process;

    public RotorEncrypt(char output, String process) {
        this.output = output;
        this.process = process;
    }

    public char getOutput() {
        return output;
    }

    public String getProcess() {
        return process;
    }
}