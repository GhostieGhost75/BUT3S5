import java.awt.*;
import javax.swing.*;

class UnMobile extends JPanel implements Runnable
{
    int saLargeur, saHauteur, sonDebDessin, sonTemps;
    final int sonPas = 10, finalTemps=50, sonCote=40;
    
    UnMobile(int telleLargeur, int telleHauteur) {
        super();
        saLargeur = telleLargeur;
        saHauteur = telleHauteur;
        sonTemps = finalTemps;
        setSize(telleLargeur, telleHauteur);
    }

    UnMobile(int telleLargeur, int telleHauteur, int telTemps) {
        super();
        saLargeur = telleLargeur;
        saHauteur = telleHauteur;
        sonTemps = telTemps;
        setSize(telleLargeur, telleHauteur);
    }

    public void run() {
        for (sonDebDessin=0; sonDebDessin < saLargeur - sonPas; sonDebDessin+= sonPas) {
            repaint();
            try{Thread.sleep(sonTemps);}
            catch (InterruptedException telleExcp)
            {telleExcp.printStackTrace();}
        }
        while (sonDebDessin != 0) {
            sonDebDessin-= sonPas;
            repaint();
            try{Thread.sleep(sonTemps);}
            catch (InterruptedException telleExcp)
            {telleExcp.printStackTrace();}
        }
    }

    public void paintComponent(Graphics telCG)
    {
	super.paintComponent(telCG);
	telCG.fillRect(sonDebDessin, saHauteur/2, sonCote, sonCote);
    }
}