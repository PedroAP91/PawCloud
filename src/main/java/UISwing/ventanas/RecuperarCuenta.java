package UISwing.ventanas;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.UUID;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import DB.BaseDatosServicio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import UISwing.recursos.GradientPanel;
import application.LoginFrame;
import model.ServicioRecuperacion;

import javax.swing.JTextPane;

public class RecuperarCuenta extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RecuperarCuenta frame = new RecuperarCuenta();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RecuperarCuenta() {
		setBounds(100, 100, 497, 524);
        setUndecorated(true);
        GradientPanel gradientPanel = new GradientPanel();
        setContentPane(gradientPanel);
        gradientPanel.setLayout(null);
        setLocationRelativeTo(null);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));

        JLabel lblLogoVertical = new JLabel("");
        lblLogoVertical.setIcon(new ImageIcon(getClass().getResource("/imagenes/logo_vertical.png")));
        lblLogoVertical.setBounds(171, 93, 165, 111);
        gradientPanel.add(lblLogoVertical);
        
        Border roundedBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1, true), // Borde blanco
                BorderFactory.createEmptyBorder(5, 10, 5, 10) // Espacio interno
        );
        
        JTextField textcorreo = new JTextField();
        textcorreo.setOpaque(false);
        textcorreo.setColumns(10);
        textcorreo.setBounds(147, 285, 204, 30);
        gradientPanel.add(textcorreo);
        textcorreo.setBorder(roundedBorder);
        textcorreo.setOpaque(false);
        textcorreo.setForeground(Color.WHITE);
        
        JLabel lblemail = new JLabel("Email");
        lblemail.setForeground(Color.WHITE);
        lblemail.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblemail.setBounds(148, 265, 46, 14);
        gradientPanel.add(lblemail);
        
        JButton btnEnviar = new JButton("Enviar");
        btnEnviar.setForeground(Color.WHITE);
        btnEnviar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEnviar.setBorder(new LineBorder(new Color(30, 144, 255), 2, true));
        btnEnviar.setBackground(new Color(0, 87, 255));
        btnEnviar.setBounds(147, 342, 204, 34);
        gradientPanel.add(btnEnviar);
        btnEnviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String correo = textcorreo.getText().trim();
                if (!correo.isEmpty()) {
                    // Genera un token
                    String token = UUID.randomUUID().toString();
                    // Intenta guardar el token en la base de datos
                    boolean tokenGuardado = BaseDatosServicio.guardarToken(correo, token);
                    if (tokenGuardado) {
                        // Envía el token por correo electrónico
                        ServicioRecuperacion servicio = new ServicioRecuperacion();
                        servicio.enviarTokenRecuperacion(correo, token);
                        JOptionPane.showMessageDialog(null, "Se ha enviado un token a tu correo electrónico. Por favor, revisa tu bandeja de entrada y Correo no deseado.");
                        
                        // Cambiar a la interfaz de RestablecimientoContraseñaUI
                        RestablecimientoContraseñaUI restablecimientoUI = new RestablecimientoContraseñaUI();
                        restablecimientoUI.setVisible(true);
                        RecuperarCuenta.this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al guardar el token. Por favor, intenta de nuevo.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, introduce un correo electrónico válido.");
                }
            }
        });

        
        
        JLabel lblVolver = new JLabel("Volver");
        lblVolver.setForeground(new Color(255, 255, 255));
        lblVolver.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblVolver.setBounds(230, 399, 48, 14);
        gradientPanel.add(lblVolver);
        lblVolver.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LoginFrame login = new LoginFrame(); // Crear una nueva instancia de LoginFrame
                login.setVisible(true); // Hacer visible LoginFrame
                RecuperarCuenta.this.dispose(); // Cerrar y liberar los recursos de RegistrosLogin
            }
        });

      
        JLabel lbllogocerrar = new JLabel("");
        lbllogocerrar.setIcon(new ImageIcon(getClass().getResource("/imagenes/cerrar.png")));
        lbllogocerrar.setBounds(445, 11, 26, 30);
        gradientPanel.add(lbllogocerrar);
        lbllogocerrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Cierra la ventana y termina la aplicación
                System.exit(0); // O puedes usar LoginFrame.this.dispose(); si prefieres solo cerrar la ventana
            }
        });
        
        JLabel lblNewLabel = new JLabel("Introduce tu email a continuación y te");
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setBackground(new Color(255, 255, 255));
        lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblNewLabel.setBounds(145, 215, 195, 14);
        gradientPanel.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("enviaremos un token para restablecer tu");
        lblNewLabel_1.setForeground(new Color(255, 255, 255));
        lblNewLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblNewLabel_1.setBounds(140, 227, 228, 14);
        gradientPanel.add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel("contraseña");
        lblNewLabel_2.setForeground(new Color(255, 255, 255));
        lblNewLabel_2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblNewLabel_2.setBounds(211, 240, 105, 14);
        gradientPanel.add(lblNewLabel_2);
        // Añade un MouseListener a lbllogocerrar para cerrar la aplicación
        
        
        JPanel centerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                // Personaliza aquí tu componente
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setComposite(AlphaComposite.SrcOver.derive(0.5f)); // Ajusta la opacidad aquí
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Puedes ajustar el radio de las esquinas si es necesario
                g2.dispose();
                super.paintComponent(g);
            }
        };
        centerPanel.setBackground(new Color(255, 255, 255, 80)); // Color de fondo con opacidad
        centerPanel.setOpaque(false); // Hace que el panel no pinte todos sus píxeles, lo que permite que se vea el fondo.
        centerPanel.setBounds(95, 52, 308, 414);
        gradientPanel.add(centerPanel);
        
 
	}
}