package pi;

import java.io.FileWriter;
import java.io.IOException;

public class PiMeas {
    public static void main(String[] args) throws Exception
    {
        long time;
        int maxproc = 16;
        int nbval = 4032000; //8! *100
        boolean weak = true;
        FileWriter results;
        // 10 workers, 50000 iterations each
        time =  new MasterTest().doRun(50000, 10);
        if  (weak) {
            results = new FileWriter("pi_"+nbval+"+"+maxproc+"_weak.txt", false);
        } else {
            results = new FileWriter("pi_"+nbval+"+"+maxproc+"_strong.txt", false);
        }
        for (int proc=1; proc<=maxproc; proc++) {
            int val = nbval;
            if (!weak)
                val = nbval/proc; //pour strong scaling, on garde mm taille et on divise par coeur
            //PiMonteCarloTest PiVal = new PiMonteCarloTest(val, 1); //instancie objet qui va faire monte carlo
            for (int i=0; i<15; i++) {
                time =  new MasterTest().doRun(val, proc);
                results.write( val*proc + " " + proc + " " + time+"\n");
                results.flush();
            }
        }
    }
}

