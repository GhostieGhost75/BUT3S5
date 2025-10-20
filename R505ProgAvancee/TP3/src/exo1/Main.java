package exo1;

public class Main {
    public static void main(String[] args) {
        Mailbox mb = new Mailbox();
        Producer prod1 = new Producer("matriarche", mb);
        Producer prod2 = new Producer("patriarche", mb);
        Producer prod3 = new Producer("patrick sébastien", mb);
        Consumer cons1 = new Consumer(mb);
        Consumer cons2 = new Consumer(mb);
        Consumer cons3 = new Consumer(mb);
        prod3.start();
        cons1.start();
        prod1.start();
        cons2.start();
        cons3.start();
        prod2.start();

    }
}