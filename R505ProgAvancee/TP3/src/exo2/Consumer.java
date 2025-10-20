package exo2;

import java.util.Objects;
import java.util.Random;

public class Consumer extends Thread { //abstraction lecteur
    private String letter; //le string qu'il récupère
    private Mailbox mb; //la boîte aux lettres pour pouvoir utiliser les méthodes

    public Consumer(Mailbox mb) { //constructeur
        super();
        this.letter = null;
        this.mb = mb;
    }

    public void run() {
        Random rand = new Random(); //pour faire de l'aléatoire
        try {
            while (true) { //récupère une lettre tant qu'il n'est pas interrompu
                Thread.sleep(rand.nextInt(1000)); //suspend le thread pendant une durée aléatoire entre 0ms et 1 sec
                letter = mb.retirer(); //récupère une lettre
                if (letter == null) { //s'il n'y a pas de lettre
                    System.out.println("laisse moi lire");
                } else { //s'il y a une lettre, l'affiche
                    System.out.println(letter);
                }
                if (Objects.equals(letter, "*")) //condition d'arrêt
                    Thread.currentThread().interrupt(); //interromp le thread
            }
        } catch (InterruptedException e) { //capture l'interruption pour pas renvoyer une erreur
            System.out.println("stop fini de lire c'est ciao");
        }
    }
}
