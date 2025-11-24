// Estimate the value of Pi using Monte-Carlo Method, using parallel program
package assignments;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
class PiMonteCarlo {
	AtomicInteger nAtomSuccess; //int avec conditions de MaJ, en gros int accessible que par un thread à la fois. c'est ncible
    //AtomicInteger est probablement un moniteur sur ncible
	int nThrows; // = ntot taille de la boucle
	double value;
	class MonteCarlo implements Runnable { //association, plus précisément composition. c'est une itération de monte carlo T1i
		@Override
		public void run() {
			double x = Math.random();
			double y = Math.random();
			if (x * x + y * y <= 1)
				nAtomSuccess.incrementAndGet(); //ncible ++ et récupère la valeur
		}
	}
	public PiMonteCarlo(int i) {
		this.nAtomSuccess = new AtomicInteger(0);
		this.nThrows = i;
		this.value = 0;
	}
	public double getPi() {
		int nProcessors = Runtime.getRuntime().availableProcessors(); //nb processeurs, récupère nombre de processeurs disponibles
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
public class Assignment102 {
	public static void main(String[] args) {
		PiMonteCarlo PiVal = new PiMonteCarlo(10000000); //instancie objet qui va faire monte carlo
		long startTime = System.currentTimeMillis(); //pour mesurer le temps d'exécution du code en ms
		double value = PiVal.getPi(); //lance le calcul
		long stopTime = System.currentTimeMillis();
		System.out.println("Approx value:" + value);
		System.out.println("Difference to exact value of pi: " + (value - Math.PI));
		System.out.println("Error: " + (value - Math.PI) / Math.PI * 100 + " %");
		System.out.println("Available processors: " + Runtime.getRuntime().availableProcessors());
		System.out.println("Time Duration: " + (stopTime - startTime) + "ms");
	}
}