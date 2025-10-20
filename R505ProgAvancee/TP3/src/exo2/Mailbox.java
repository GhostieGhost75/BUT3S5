package exo2;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Mailbox { //abstraction boîte aux lettre ==> moniteur
    private BlockingQueue<String> queue; //abstraction liste de lettres

    public Mailbox(int nbletters) {
        queue =  new ArrayBlockingQueue<>(nbletters); //initialise la queue
    }

    public boolean deposer(String letter) throws InterruptedException {
        return queue.offer(letter, 200, TimeUnit.MILLISECONDS); //propose d'ajouter un élément à la queue. s'il y arrive pas en 200ms il renvoie false
    }

    public String retirer() throws InterruptedException{
        return queue.poll(); //permet de récupérer un élément s'il y en a de disponible
    }

    public int getStorage() {
        return queue.size();
    }
}
