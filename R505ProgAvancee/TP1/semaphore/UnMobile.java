package semaphore;

import java.awt.*;
import javax.swing.*;

class UnMobile extends JPanel implements Runnable
{
    int saLargeur, saHauteur, sonDebDessin, sonTemps;
    final int sonPas = 10, finalTemps=50, sonCote=40;
    Semaphore sem;
    
    UnMobile(int telleLargeur, int telleHauteur, Semaphore sema) {
        super();
        saLargeur = telleLargeur;
        saHauteur = telleHauteur;
        sonTemps = finalTemps;
        sem = sema;
        setSize(telleLargeur, telleHauteur);
    }

    UnMobile(int telleLargeur, int telleHauteur, int telTemps, Semaphore sema) {
        super();
        saLargeur = telleLargeur;
        saHauteur = telleHauteur;
        sonTemps = telTemps;
        setSize(telleLargeur, telleHauteur);
        sem = sema;
    }

    public void run() {
        for (sonDebDessin=0; sonDebDessin+sonCote<saLargeur/3; sonDebDessin+=sonPas) {
            repaint();
            try{Thread.sleep(sonTemps);}
            catch (InterruptedException telleExcp)
            {telleExcp.printStackTrace();}
        }
        //SEMAPHORE
        sem.syncWait();
        setForeground(Color.red);
        while(sonDebDessin+sonCote<2*(saLargeur/3)) {
            repaint();
            try{Thread.sleep(sonTemps);}
            catch (InterruptedException telleExcp)
            {telleExcp.printStackTrace();}
            sonDebDessin+=sonPas;
        }
        sem.syncSignal();
        setForeground(Color.black);
        //SEMAPHORE
        while(sonDebDessin+sonCote<saLargeur) {
            repaint();
            try{Thread.sleep(sonTemps);}
            catch (InterruptedException telleExcp)
            {telleExcp.printStackTrace();}
            sonDebDessin+=sonPas;
        }
        //RETOUR
        while(sonDebDessin>2*(saLargeur/3)) {
            repaint();
            try{Thread.sleep(sonTemps);}
            catch (InterruptedException telleExcp)
            {telleExcp.printStackTrace();}
            sonDebDessin-=sonPas;
        }
        //SEMAPHORE
        sem.syncWait();
        setForeground(Color.red);
        while(sonDebDessin>saLargeur/3) {
            repaint();
            try{Thread.sleep(sonTemps);}
            catch (InterruptedException telleExcp)
            {telleExcp.printStackTrace();}
            sonDebDessin-=sonPas;
        }
        sem.syncSignal();
        setForeground(Color.black);
        //SEMAPHORE
        while(sonDebDessin>0) {
            repaint();
            try{Thread.sleep(sonTemps);}
            catch (InterruptedException telleExcp)
            {telleExcp.printStackTrace();}
            sonDebDessin-=sonPas;
        }

        //while (sonDebDessin != 0) {
        //    sonDebDessin-= sonPas;
        //    repaint();
        //    try{Thread.sleep(sonTemps);}
        //    catch (InterruptedException telleExcp)
        //    {telleExcp.printStackTrace();}
        //}
    }

    public void paintComponent(Graphics telCG)
    {
	super.paintComponent(telCG);
	telCG.fillRect(sonDebDessin, saHauteur/2, sonCote, sonCote);
    }
}