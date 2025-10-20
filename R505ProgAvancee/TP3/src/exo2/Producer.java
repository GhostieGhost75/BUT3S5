package exo2;

import java.util.Random;

public class Producer extends Thread { //abstraction auteur
    private Mailbox mb; //la boîte aux lettres pour pouvoir utiliser les méthodes

    public Producer(Mailbox mb) { //constructeur
        super();
        this.mb = mb;
    }

    public void run() {
        try {
            Random rand = new Random(); //permet l'aléatoire
            String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ*"; //"liste" des lettres à envoyer à la mailbox
            for (int i=0; i<letters.length(); i++) { //boucle pour envoyer chaque lettre
                boolean depose = false; //force l'essai d'envoi d'une lettre tant qu'elle est pas déposée
                while (!depose) {
                    Thread.sleep(rand.nextInt(1000)); //temps de suspension aléatoire
                    depose = mb.deposer(Character.toString(letters.charAt(i))); //deposer renvoie true si c'est réussi
                    if (!depose) { //message si la lettre n'a pas pu être déposée
                        System.out.println("producer attend de pouvoir écrire");
                    }
                }

            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e); //erreur en cas d'interruption, pas prévu donc pas empêché cette fois
        }
    }
}
