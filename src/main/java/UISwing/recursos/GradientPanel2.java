package UISwing.recursos;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class GradientPanel2 extends JPanel {
    private int x, y;

    public GradientPanel2() {
        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                x = me.getX();
                y = me.getY();
            }
        };

        MouseMotionAdapter mma = new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
                // Comprueba si el componente padre (o ancestro) es una instancia de Window
                if (GradientPanel2.this.getTopLevelAncestor() instanceof java.awt.Window) {
                    java.awt.Window window = (java.awt.Window) GradientPanel2.this.getTopLevelAncestor();
                    window.setLocation(window.getX() + me.getX() - x, window.getY() + me.getY() - y);
                }
            }
        };

        addMouseListener(ma);
        addMouseMotionListener(mma);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gp = new GradientPaint(0, 0, Color.decode("#0081FF"), 0, getHeight(), Color.decode("#94A1FF"));
        g2.setPaint(gp);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        g2.fillRect(getWidth() - 20, 0, getWidth(), getHeight());
    }
}
