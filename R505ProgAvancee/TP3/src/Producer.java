public class Producer extends Thread {
    private String letter;
    private Mailbox mb;

    public Producer(String letter, Mailbox mb) {
        super();
        this.letter = letter;
        this.mb = mb;
    }

    public void run() {
        try {
            mb.deposer(letter);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
