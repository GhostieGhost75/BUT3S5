package distributedMC;

import java.io.*;
import java.net.*;

/** Master is a client. It makes requests to numWorkers.
 *   
 */
public class MasterSocket {
    static int maxServer = 16;
    static final int[] tab_port = {25545,25546,25547,25548,25549,25550,25551,25552, 25553, 25554, 25555, 25556, 25557, 25558, 25559, 25560}; //liste ports libres
    static String[] tab_total_workers = new String[maxServer];
    static String ip = "192.168.24.220"; //localhost
    static BufferedReader[] reader = new BufferedReader[maxServer];
    static PrintWriter[] writer = new PrintWriter[maxServer];
    static Socket[] sockets = new Socket[maxServer];

    public static void main(String[] args) throws Exception {
        //DEBUG
        int debug = -1;
        boolean multipleIPs = false;
        boolean weak = false;
        int totalCount = 16000000; // total number of throws on a Worker
        FileWriter results = null;
        if (args.length > 0) {
            try {
                if (args[0].equals("debug")) {
                    debug = 10;
                    maxServer = Math.min(Integer.parseInt(args[1]),  maxServer);
                    try{if (args[2].equals("weak")) weak = true;} catch(Exception e){}
                    results = new FileWriter("socket_"+totalCount+"x"+maxServer+(weak ? "_weak" : "_strong")+".txt",false);
                } else if (args[0].equals("multipleIPs")) multipleIPs = true;
            } catch (Exception e) {}
        }

        System.out.println(debug);
        // MC parameters
        int total; // total number of throws inside quarter of disk
        double pi;

        int numWorkers = maxServer;
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        //tout ça est automatisé si en mode debug
        if (debug == -1) {
            String s; // for bufferRead
            System.out.println("#########################################");
            System.out.println("# Computation of PI by MC method        #");
            System.out.println("#########################################");

            System.out.println("\n How many workers for computing PI (< maxServer): ");
            try{
                s = bufferRead.readLine(); //saisie clavier
                numWorkers = Integer.parseInt(s);
            }
            catch(IOException ioE){
                ioE.printStackTrace();
            }
            for (int i=0; i<numWorkers; i++){
                System.out.println("Enter worker"+ i +" port : ");
                try{
                    s = bufferRead.readLine(); //saisie clavier du port
                    System.out.println("You select " + s);
                    tab_port[i]=Integer.parseInt(s);
                }
                catch(IOException ioE){
                    ioE.printStackTrace();
                }
            }
        }
        System.out.println(numWorkers);
        //LinkedList<Pair<String, Integer>>

       //create worker's socket
       for(int i = 0 ; i < numWorkers ; i++) {
           sockets[i] = new Socket(ip, tab_port[i]); //on donne adresse ip et port au socket pour savoir où il va
           System.out.println("SOCKET = " + sockets[i]);

           reader[i] = new BufferedReader( new InputStreamReader(sockets[i].getInputStream()));
           writer[i] = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sockets[i].getOutputStream())),true);
           //pour pouvoir lire msg venant du worker transitant par le socket
           //en gros il crée en se basant sur socket.getInputStream qui renvoie
           //socket.getOutputStream() renvoie
       }

       String message_to_send;
       message_to_send = String.valueOf(totalCount);

       String message_repeat = "y";

       long stopTime, startTime;

       while (message_repeat.equals("y")){
           total = 0;

           startTime = System.currentTimeMillis();
           // initialize workers
           for(int i = 0 ; i < numWorkers ; i++) {
               writer[i].println(message_to_send);          // send a message to each worker
               //ecrit dans un objet PrintWriter càd dans un OutputStream ==> envoie le msg getOutputStream
           }

           //listen to workers's message
           for(int i = 0 ; i < numWorkers ; i++) {
               tab_total_workers[i] = reader[i].readLine();      // read message from server
               //attend qu'on écrive trucs. readline fait du getInputStream
               //là il veut récup les messages qui sont ncible de chaque worker
               System.out.println("Client sent: " + tab_total_workers[i]);
           }

           // compute PI with the result of each workers
           for(int i = 0 ; i < numWorkers ; i++) {
               total += Integer.parseInt(tab_total_workers[i]);
           }
           pi = 4.0 * total / totalCount / numWorkers;

           stopTime = System.currentTimeMillis();

           if (debug == -1) {
               System.out.println("\nPi : " + pi );
               System.out.println("Error: " + (Math.abs((pi - Math.PI)) / Math.PI) +"\n");

               System.out.println("Ntot: " + totalCount*numWorkers);
               System.out.println("Available processors: " + numWorkers);
               System.out.println("Time Duration (ms): " + (stopTime - startTime) + "\n");

               System.out.println("\n Repeat computation (y/N): ");
               try{
                   message_repeat = bufferRead.readLine();
                   System.out.println(message_repeat);
               }
               catch(IOException ioE){
                   ioE.printStackTrace();
               }
           } else {
               results.write(numWorkers+" "+ totalCount*numWorkers+" "+(stopTime-startTime)+" "+pi+"\n");
               results.flush();
               System.out.println( (Math.abs((pi - Math.PI)) / Math.PI) +" "+ totalCount*numWorkers +" "+ numWorkers +" "+ (stopTime - startTime));
               if (debug == 0) message_repeat = "n";
               else  {
                   message_repeat = "y";
                   debug--;
               }
           }
       }
       for(int i = 0 ; i < numWorkers ; i++) {
           System.out.println("END");     // Send ending message
           writer[i].println("END") ;
           reader[i].close();
           writer[i].close();
           sockets[i].close();
       }
   }
}