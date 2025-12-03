package pi;

import java.io.FileWriter;
import java.io.IOException;

public class PiErrors {
    public static void main(String[] args) throws Exception
    {
        int maxproc = 8;
        int nbval = 1000;
        int maxval = 100000000;
        boolean weak = true;
        FileWriter results = new FileWriter("pi_"+nbval+"+"+maxproc+"_errors.txt", false);
        for (int vals=nbval; vals<=maxval; vals+=1000) {
            for (int i=0; i<10; i++) {
                double pi_esti = new MasterError().doRun(vals, maxproc);
                results.write(vals*maxproc + " " + maxproc + " " + pi_esti + "\n");
                results.flush();
            }
        }
    }
}

