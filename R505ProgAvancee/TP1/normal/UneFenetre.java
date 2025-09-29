package normal;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

class UneFenetre extends JFrame
{
    int qtyMobile = 50;
    private final int LARG=400, HAUT=750;

    public UneFenetre() {

        setSize(LARG, HAUT);
        Container leConteneur = getContentPane();
        leConteneur.setLayout(new GridLayout(qtyMobile, 1));
        UnMobile[] mobiles = new UnMobile[qtyMobile];
        Thread[] threads = new Thread[qtyMobile];
        Random temps = new Random();
        for (int i = 0; i < qtyMobile; i++) {
            Random rand = new Random();
            UnMobile mobile = new UnMobile(LARG, HAUT/qtyMobile, temps.nextInt(500)); //enfant de la fenêtre
            mobiles[i] = mobile;
            leConteneur.add(mobile);
            threads[i] = new Thread(mobile); //runnable dans thread
        }
        setVisible(true);
        for (int i = 0; i < qtyMobile; i++) {
            threads[i].start();
        }
    }
}
