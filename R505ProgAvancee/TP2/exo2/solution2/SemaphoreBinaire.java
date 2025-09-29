package exo2.solution2;

public final class SemaphoreBinaire extends Semaphore {
public SemaphoreBinaire(int valeurInitiale){
	super((valeurInitiale != 0) ? 1:0); //valeur initiale à 1 ou 0
	//System.out.print(valeurInitiale);
}
public final synchronized void syncSignal(){
	super.syncSignal();
	//System.out.print(valeur);
	if (valeur>1) valeur = 1; //fait en sorte de n'avoir qu'une place
}
}

