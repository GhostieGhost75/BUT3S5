import java.awt.*;
import javax.swing.*;

class UneFenetre extends JFrame 
{
    UnMobile sonMobile;
    private final int LARG=400, HAUT=250;
    
    public UneFenetre() {
        setSize(LARG, HAUT);
        Container leConteneur = getContentPane();
        sonMobile = new UnMobile(LARG, HAUT); //enfant de la fenêtre
        leConteneur.add(sonMobile);
        Thread laThread = new Thread(sonMobile); //runnable dans thread
        setVisible(true);
        laThread.start();
    }
}
