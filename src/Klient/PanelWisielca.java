
package Klient;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class PanelWisielca extends JPanel {

    private int proby;

    public PanelWisielca(int proby) {
        this.proby = proby;
    }

    public void setProby(int proby) {
        this.proby = proby;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));

        g2.setColor(Color.BLACK);
        g2.drawLine(50, 250, 150, 250);
        g2.drawLine(100, 250, 100, 50);
        g2.drawLine(100, 50, 200, 50);
        g2.drawLine(200, 50, 200, 80);

    
        if (proby < 6) { // Głowa
            g2.setColor(Color.RED);
            g2.drawOval(180, 80, 40, 40);
        }
        if (proby < 5) { // Tułów
            g2.setColor(Color.ORANGE);
            g2.drawLine(200, 120, 200, 180);
        }
        if (proby < 4) { // Lewa ręka
            g2.setColor(Color.YELLOW);
            g2.drawLine(200, 140, 170, 160);
        }
        if (proby < 3) { // Prawa ręka
            g2.setColor(Color.GREEN);
            g2.drawLine(200, 140, 230, 160);
        }
        if (proby < 2) { // Lewa noga
            g2.setColor(Color.BLUE);
            g2.drawLine(200, 180, 180, 220);
        }
        if (proby < 1) { // Prawa noga
            g2.setColor(Color.MAGENTA);
            g2.drawLine(200, 180, 220, 220);
        }
    }
}


