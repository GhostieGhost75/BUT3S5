package assignments;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

class PiMonteCarloTest {
    AtomicInteger nAtomSuccess; //int avec conditions de MaJ, en gros int accessible que par un thread à la fois. c'est ncible
    //AtomicInteger est probablement un moniteur sur ncible
    int nThrows; // = ntot taille de la boucle
    double value;
    int nProcessors;
    class MonteCarlo implements Runnable { //association, plus précisément composition. c'est une itération de monte carlo T1i
        @Override
        public void run() {
            double x = Math.random();
            double y = Math.random();
            if (x * x + y * y <= 1)
                nAtomSuccess.incrementAndGet(); //ncible ++ et récupère la valeur
        }
    }
    public PiMonteCarloTest(int i) {
        this.nAtomSuccess = new AtomicInteger(0);
        this.nThrows = i;
        this.value = 0;
        nProcessors = Runtime.getRuntime().availableProcessors(); //nb processeurs, récupère nombre de processeurs disponibles
    }
    public PiMonteCarloTest(int i, int proc) {
        this.nAtomSuccess = new AtomicInteger(0);
        this.nThrows = i;
        this.value = 0;
        nProcessors = proc;
    }
    public double getPi() {
        ExecutorService executor = Executors.newWorkStealingPool(nProcessors); //"ensemble du vol de travail", paradigme vol de tâches
        for (int i = 1; i <= nThrows; i++) { //parallel for, itération parallèle
            Runnable worker = new MonteCarlo(); //crée un worker MonteCarlo, c'est un runnable qui fait une itération de Monte Carlo
            executor.execute(worker); //exécuter la tâche
        }
        executor.shutdown(); //bute toutes les itérations
        while (!executor.isTerminated()) {
        }
        value = 4.0 * nAtomSuccess.get() / nThrows; //4*ncible/ntot ==> approximation de pi
        return value;
    }
}