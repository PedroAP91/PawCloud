package UISwing.recursos;

import javax.swing.*;
import java.awt.*;

public class CustomPanelOpaco extends JPanel {
    public CustomPanelOpaco() {
        setOpaque(false); // Importante para asegurar la transparencia
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setComposite(AlphaComposite.SrcOver.derive(0.5f)); // Ajusta la opacidad aquí
        g2.setColor(getBackground()); // Usa el color de fondo establecido
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Puedes ajustar el radio de las esquinas
        g2.dispose();
        
    }
}
