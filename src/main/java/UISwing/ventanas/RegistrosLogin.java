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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import UISwing.recursos.GradientPanel;
import application.LoginFrame;
import model.UserModel;

public class RegistrosLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistrosLogin frame = new RegistrosLogin();
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
	public RegistrosLogin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 497, 524);
        setUndecorated(true);
        GradientPanel gradientPanel = new GradientPanel();
        setContentPane(gradientPanel);
        gradientPanel.setLayout(null);
        setLocationRelativeTo(null);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));

        JLabel lblLogoVertical = new JLabel("");
        lblLogoVertical.setIcon(new ImageIcon(getClass().getResource("/imagenes/logo_vertical.png")));
        lblLogoVertical.setBounds(170, 36, 165, 111);
        gradientPanel.add(lblLogoVertical);
        
        Border roundedBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1, true), // Borde blanco
                BorderFactory.createEmptyBorder(5, 10, 5, 10) // Espacio interno
        );

        JTextField textUsuario = new JTextField();
        textUsuario.setBounds(148, 178, 204, 30);
        gradientPanel.add(textUsuario);
        textUsuario.setColumns(10);
        textUsuario.setBorder(roundedBorder);
        textUsuario.setOpaque(false);
        textUsuario.setForeground(Color.white);
        
        JTextField textcorreo = new JTextField();
        textcorreo.setOpaque(false);
        textcorreo.setColumns(10);
        textcorreo.setBounds(148, 239, 204, 30);
        gradientPanel.add(textcorreo);
        textcorreo.setBorder(roundedBorder);
        textcorreo.setOpaque(false);
        textcorreo.setForeground(Color.WHITE);
        
        JPasswordField textcontraseña = new JPasswordField();
        textcontraseña.setOpaque(false);
        textcontraseña.setColumns(10);
        textcontraseña.setBounds(148, 299, 204, 30);
        gradientPanel.add(textcontraseña);
        textcontraseña.setBorder(roundedBorder);
        textcontraseña.setForeground(Color.WHITE);

        JPasswordField textrepeatcontraseña = new JPasswordField();
        textrepeatcontraseña.setBounds(148, 359, 204, 30);
        gradientPanel.add(textrepeatcontraseña);
        textrepeatcontraseña.setColumns(10);
        textrepeatcontraseña.setBorder(roundedBorder);
        textrepeatcontraseña.setOpaque(false);
        textrepeatcontraseña.setForeground(Color.WHITE);
        
        
        
        JLabel lblusuario = new JLabel("Usuario");
        lblusuario.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblusuario.setForeground(new Color(255, 255, 255));
        lblusuario.setBounds(148, 158, 46, 14);
        gradientPanel.add(lblusuario);
        
        JLabel lblemail = new JLabel("Email");
        lblemail.setForeground(Color.WHITE);
        lblemail.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblemail.setBounds(148, 219, 46, 14);
        gradientPanel.add(lblemail);
        
        JLabel lblcontraseña_2 = new JLabel("Contraseña");
        lblcontraseña_2.setForeground(Color.WHITE);
        lblcontraseña_2.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblcontraseña_2.setBounds(148, 280, 84, 14);
        gradientPanel.add(lblcontraseña_2);

        JLabel lblcontraseña = new JLabel("Repetir contraseña");
        lblcontraseña.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblcontraseña.setForeground(new Color(255, 255, 255));
        lblcontraseña.setBounds(148, 340, 126, 14);
        gradientPanel.add(lblcontraseña);
        
        JButton btnRegistrarse = new JButton("Registrarse");
        btnRegistrarse.setForeground(Color.WHITE);
        btnRegistrarse.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegistrarse.setBorder(new LineBorder(new Color(30, 144, 255), 2, true));
        btnRegistrarse.setBackground(new Color(0, 87, 255));
        btnRegistrarse.setBounds(148, 407, 204, 34);
        gradientPanel.add(btnRegistrarse);
        btnRegistrarse.addActionListener(e -> {
            String username = textUsuario.getText().trim();
            String email = textcorreo.getText().trim();
            String password = new String(textcontraseña.getPassword()).trim();
            String repeatPassword = new String(textrepeatcontraseña.getPassword()).trim();

            // Verifica campos vacíos
            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
                JOptionPane.showMessageDialog(RegistrosLogin.this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Verifica que el email contenga "@"
            if (!email.contains("@")) {
                JOptionPane.showMessageDialog(RegistrosLogin.this, "Introduce un email válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verifica la longitud mínima de la contraseña
            if (password.length() < 8) {
                JOptionPane.showMessageDialog(RegistrosLogin.this, "La contraseña debe tener al menos 8 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!password.equals(repeatPassword)) {
                JOptionPane.showMessageDialog(RegistrosLogin.this, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            UserModel userModel = new UserModel();
            boolean success = userModel.registerUser(username, email, password);

            if (success) {
                JOptionPane.showMessageDialog(RegistrosLogin.this, "Registro exitoso. Por favor, inicie sesión.", "Registro", JOptionPane.INFORMATION_MESSAGE);
                // Opcional: limpiar campos o cerrar ventana de registro aquí
            } else {
                JOptionPane.showMessageDialog(RegistrosLogin.this, "Error al registrar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        
        JLabel lblVolver = new JLabel("Volver");
        lblVolver.setForeground(new Color(255, 255, 255));
        lblVolver.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblVolver.setBounds(235, 462, 48, 14);
        gradientPanel.add(lblVolver);
        lblVolver.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LoginFrame login = new LoginFrame(); // Crear una nueva instancia de LoginFrame
                login.setVisible(true); // Hacer visible LoginFrame
                RegistrosLogin.this.dispose(); // Cerrar y liberar los recursos de RegistrosLogin
            }
        });

      
        JLabel lbllogocerrar = new JLabel("");
        lbllogocerrar.setIcon(new ImageIcon(getClass().getResource("/imagenes/cerrar.png")));
        lbllogocerrar.setBounds(445, 11, 26, 30);
        gradientPanel.add(lbllogocerrar);
        // Añade un MouseListener a lbllogocerrar para cerrar la aplicación
        lbllogocerrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Cierra la ventana y termina la aplicación
                System.exit(0); // O puedes usar LoginFrame.this.dispose(); si prefieres solo cerrar la ventana
            }
        });
        
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
        centerPanel.setBounds(95, 21, 308, 480);
        gradientPanel.add(centerPanel);
 
	}
	
	
}
