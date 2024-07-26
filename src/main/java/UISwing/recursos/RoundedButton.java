package UISwing.recursos;

import java.awt.Graphics;

import javax.swing.JButton;

public class RoundedButton extends JButton {
    public RoundedButton(String label) {
        super(label);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        super.paintComponent(g);
    }
    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());
        g.drawRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
    }
}
