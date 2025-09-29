package exo1;

class Exclusion{};
public class Affichage extends Thread{
    String texte;
    static final Exclusion exclusionImpression = new Exclusion();

    public Affichage (String txt){texte=txt;}

    public void run(){

        // *
        synchronized (exclusionImpression) { //verrou avec exclusion, possible de verouiller System.out à la place
            for (int i=0; i<texte.length(); i++){
                System.out.print(texte.charAt(i)); //ressource critique
                try {sleep(100);} catch(InterruptedException e){};
            }
        }
        // section critique

    }
}
