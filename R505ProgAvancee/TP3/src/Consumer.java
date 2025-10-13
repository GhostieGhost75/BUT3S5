public class Consumer extends Thread {
    private String letter;
    private Mailbox mb;

    public Consumer(Mailbox mb) {
        super();
        this.letter = null;
        this.mb = mb;
    }

    public void run() {
        try {
            letter = mb.retirer();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(letter);
    }
}
