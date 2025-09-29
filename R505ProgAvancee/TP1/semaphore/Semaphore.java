package semaphore;

public abstract class Semaphore {

    protected int valeur=0;

    protected Semaphore (int valeurInitiale){
        valeur = valeurInitiale>0 ? valeurInitiale:0; //vérifie que val initiale est positive
    }

    public synchronized void syncWait(){
	try {
	    while(valeur<=0) //s'il n'y a plus de place, fait attendre les thread
            wait();
	    valeur--; //enlève 1 si un thread entre
	} catch(InterruptedException _){}
    }

    public synchronized void syncSignal(){
        if(++valeur > 0) //notifie les thread s'il y a de la place et
            notifyAll();
    }
}
