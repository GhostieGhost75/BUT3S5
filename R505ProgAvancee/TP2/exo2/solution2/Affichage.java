package exo2.solution2; /**
 * 
 */

public class Affichage extends Thread{
	String texte;
    UseAffichage ua;

	public Affichage (UseAffichage c, String txt){
        texte = txt;
        ua = c;
    }

    public void run() {
        ua.ecrire(texte);
    }
}
