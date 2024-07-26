package UISwing.recursos;

import javax.swing.JTextField;
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.Insets;
import java.awt.Color;

public class RoundedTextField extends JTextField {
    private Shape shape;
    private int arcWidth; // Ancho del arco para las esquinas redondeadas
    private int arcHeight; // Alto del arco para las esquinas redondeadas

    public RoundedTextField(int columns, int arcWidth, int arcHeight) {
        super(columns);
        this.arcWidth = arcWidth; // Define el ancho de las esquinas redondeadas
        this.arcHeight = arcHeight; // Define el alto de las esquinas redondeadas
        setOpaque(false); // Asigna el fondo como transparente
        setMargin(new Insets(5, 10, 5, 10)); // Podría ser necesario para evitar que el texto se muestre muy cerca del borde
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Pinta el fondo transparente del campo de texto
        g2.setComposite(AlphaComposite.SrcOver.derive(0.0f)); // Aquí asegura la transparencia
        super.paintComponent(g2);

        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dibuja el borde redondeado blanco
        g2.setColor(getForeground());
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);

        g2.dispose();
    }

    @Override
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);
        }
        return shape.contains(x, y);
    }
}
