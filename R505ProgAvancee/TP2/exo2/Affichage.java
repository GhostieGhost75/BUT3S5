package exo2; /**
 * 
 */
import java.lang.String;

class Exclusion{};
public class Affichage extends Thread{
    static Semaphore semaBin = new SemaphoreBinaire(1);
	String texte;

	public Affichage (String txt){texte=txt;}
	
	public void run(){

        // *
        semaBin.syncWait(); //Semaphore fait attendre si nécessaire
        System.out.println("j'entre dans la section critique");
        for (int i=0; i<texte.length(); i++){
            System.out.print(texte.charAt(i)); //ressource critique
            try {sleep(100);} catch(InterruptedException e){};
        }
        semaBin.syncSignal(); //Semaphore notifie en cas de libération
        System.out.println("\nje sors de la section critique");
        // section critique

	}
}
