import java.awt.*;
import javax.swing.*;

class UneFenetre extends JFrame
{
    UnMobile sonMobile1;
    UnMobile sonMobile2;
    private final int LARG=400, HAUT=250;

    public UneFenetre() {
        setSize(LARG, HAUT);
        Container leConteneur = getContentPane();
        leConteneur.setLayout(new GridLayout(2, 1));
        sonMobile1 = new UnMobile(LARG, HAUT/2); //enfant de la fenêtre
        leConteneur.add(sonMobile1);
        sonMobile2 = new UnMobile(LARG, HAUT/2); //enfant de la fenêtre
        leConteneur.add(sonMobile2);
        Thread laThread1 = new Thread(sonMobile1); //runnable dans thread
        Thread laThread2 = new Thread(sonMobile2); //runnable dans thread
        setVisible(true);
        laThread1.start();
        laThread2.start();
    }
}
