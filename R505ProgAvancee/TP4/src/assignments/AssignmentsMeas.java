package assignments;

import java.io.FileWriter;
import java.io.IOException;

public class AssignmentsMeas {
    public static void main(String[] args) throws IOException {
        int maxproc = 8;
        int nbval = 1000000;
        boolean weak = true;
        FileWriter results;

        if  (weak) {
            results = new FileWriter("assign_"+nbval+"+"+maxproc+"_weak.txt", false);
        } else {
            results = new FileWriter("assign_"+nbval+"+"+maxproc+"_strong.txt", false);
        }
        for (int proc=1; proc<=maxproc; proc++) {
            int val = nbval;
            if (weak)
                val = nbval*proc;
            PiMonteCarloTest PiVal = new PiMonteCarloTest(val, proc); //instancie objet qui va faire monte carlo
            for (int i=0; i<15; i++) {
                long startTime = System.currentTimeMillis(); //pour mesurer le temps d'exécution du code en ms
                double value = PiVal.getPi(); //lance le calcul
                long stopTime = System.currentTimeMillis();
                results.write( val + " " + proc + " " + (stopTime-startTime)+"\n");
                results.flush();
            }
        }
    }
}

