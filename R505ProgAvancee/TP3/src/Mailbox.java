public class Mailbox { //abstraction boîte aux lettre ==> moniteur
    private String buffer; //abstraction lettre
    public boolean available = true; //0 pas accessible ou 1 accessible

    public synchronized String retirer() throws InterruptedException {
        if (available) {
            wait();
        }
        String tmp = buffer;
        available = true;
        notify();
        return tmp;
    }

    public synchronized void deposer(String letter) throws InterruptedException {
        if (!available) {
            wait();
        }
        buffer = letter;
        available = false;
        notify();
    }
}
