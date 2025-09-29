package exo2.solution2;

public class UseAffichage {
    Semaphore semaBin = new SemaphoreBinaire(1);

    public void ecrire(String texte) {
        semaBin.syncWait();
        for (int j=0; j<texte.length(); j++){
            System.out.print(texte.charAt(j));
            try{Thread.sleep(20);}catch(InterruptedException _){}
        }
        semaBin.syncSignal();
    }
}
