package exo2;

public class Main {
    public static void main(String[] args) {
        Mailbox mb = new Mailbox(10);
        Consumer c = new Consumer(mb);
        Producer p = new Producer(mb);
        c.start();
        p.start();
    }
}