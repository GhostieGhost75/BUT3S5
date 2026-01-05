package distributedMC;

import java.io.*;
import java.net.*;
import java.util.Random;

import pi.Master;
import pi.Pi;

/**
 * Worker is a server. It computes PI by Monte Carlo method and sends 
 * the result to Master.
 */
public class WorkerSocket {
    static int port = 25545; //default port
    private static boolean isRunning = true;
    static int cores = 6;
    
    /**
     * compute PI locally by MC and sends the number of points 
     * inside the disk to Master. 
     */
    public static void main(String[] args) throws Exception {

    try {
        if (!("".equals(args[0]))) port=Integer.parseInt(args[0]);
    } catch (Exception e) {}

	System.out.println(port);
        ServerSocket s = new ServerSocket(port);
        System.out.println("Server started on port " + port + " with "+cores+" cores available");
        Socket soc = s.accept();
        System.out.println("Master connected on "+soc.getInetAddress().getHostAddress());
	
        // BufferedReader bRead for reading message from Master
        BufferedReader bRead = new BufferedReader(new InputStreamReader(soc.getInputStream()));

        // PrintWriter pWrite for writing message to Master
        PrintWriter pWrite = new PrintWriter(new BufferedWriter(new OutputStreamWriter(soc.getOutputStream())), true);
	String str;
    String[] params;
        while (isRunning) {
            str = bRead.readLine();          // read message from Master
            params =  str.split(" ");
            if (!(str.equals("END"))){
                int ntot = Integer.parseInt(params[0]);
                try {
                    cores = Math.min(cores, Integer.parseInt(params[1]));
                } catch (Exception e){}
                //System.out.println("Server receives totalCount = " +  str);

                // compute
                //System.out.println("TODO : compute Monte Carlo and send total");
                Master master = new Master();
                long ncible = master.doRun(ntot/cores, cores);
                str = String.valueOf(ncible);
                pWrite.println(str);         // send number of points in quarter of disk
            }else{
                System.out.println(str);
                isRunning=false;
            }
        }
        bRead.close();
        pWrite.close();
        soc.close();
   }
}