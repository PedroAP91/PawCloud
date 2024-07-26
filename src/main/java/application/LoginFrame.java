package application;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import UISwing.recursos.GradientPanel;
import UISwing.ventanas.RecuperarCuenta;
import UISwing.ventanas.RegistrosLogin;
import model.Authentication;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;



public class LoginFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textUsuario;
    private JPasswordField textcontraseña;
    private JLabel lblusuario;
    private JLabel lblcontraseña;
    private JLabel lblNewLabel_2;
    private JLabel lblNewLabel_3;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginFrame frame = new LoginFrame();
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
    public LoginFrame() {
        setBounds(100, 100, 497, 524);
        setUndecorated(true);
        GradientPanel gradientPanel = new GradientPanel();
        setContentPane(gradientPanel);
        gradientPanel.setLayout(null);
        setLocationRelativeTo(null);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));

        JLabel lblLogoVertical = new JLabel("");
        lblLogoVertical.setIcon(new ImageIcon(getClass().getResource("/imagenes/logo_vertical.png")));
        lblLogoVertical.setBounds(173, 76, 165, 111);
        gradientPanel.add(lblLogoVertical);

        Border roundedBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1, true), // Borde blanco
                BorderFactory.createEmptyBorder(5, 10, 5, 10) // Espacio interno
        );

        textUsuario = new JTextField();
        textUsuario.setBounds(148, 218, 204, 30);
        gradientPanel.add(textUsuario);
        textUsuario.setColumns(10);
        textUsuario.setBorder(roundedBorder);
        textUsuario.setOpaque(false);
        textUsuario.setForeground(Color.WHITE);

        textcontraseña = new JPasswordField();
        textcontraseña.setBounds(148, 277, 204, 30);
        gradientPanel.add(textcontraseña);
        textcontraseña.setColumns(10);
        textcontraseña.setBorder(roundedBorder);
        textcontraseña.setOpaque(false);
        textcontraseña.setForeground(Color.WHITE);

        lblusuario = new JLabel("Usuario");
        lblusuario.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblusuario.setForeground(new Color(255, 255, 255));
        lblusuario.setBounds(148, 198, 46, 14);
        gradientPanel.add(lblusuario);

        lblcontraseña = new JLabel("Contraseña");
        lblcontraseña.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblcontraseña.setForeground(new Color(255, 255, 255));
        lblcontraseña.setBounds(148, 259, 84, 14);
        gradientPanel.add(lblcontraseña);

        JButton btnLogin = new JButton("Iniciar sesión");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setBounds(148, 339, 204, 34);
        btnLogin.setBackground(Color.decode("#0057FF"));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBorder(new LineBorder(new Color(30, 144, 255), 2, true));
        btnLogin.addActionListener(e -> iniciarSesion());
        gradientPanel.add(btnLogin);

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


        JLabel lblolvidaste = new JLabel("¿Olvidaste la contraseña?");
        lblolvidaste.setForeground(new Color(255, 255, 255));
        lblolvidaste.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblolvidaste.setBounds(186, 390, 166, 21);
        gradientPanel.add(lblolvidaste);
        lblolvidaste.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                RecuperarCuenta recuperarCuenta = new RecuperarCuenta(); // Crear una instancia de RegistrosLogin
                recuperarCuenta.setVisible(true); // Hacer visible RegistrosLogin
                LoginFrame.this.setVisible(false); // Ocultar LoginFrame
            }
        });


        JLabel lblnotienescuenta = new JLabel("¿No tienes cuenta?");
        lblnotienescuenta.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblnotienescuenta.setForeground(new Color(255, 255, 255));
        lblnotienescuenta.setBounds(173, 445, 121, 21);
        gradientPanel.add(lblnotienescuenta);

        JLabel lblRegistrar = new JLabel("Registrate");
        lblRegistrar.setForeground(new Color(255, 255, 255));
        lblRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblRegistrar.setBounds(279, 448, 63, 14);
        gradientPanel.add(lblRegistrar);
        lblRegistrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                RegistrosLogin registro = new RegistrosLogin(); // Crear una instancia de RegistrosLogin
                registro.setVisible(true); // Hacer visible RegistrosLogin
                LoginFrame.this.setVisible(false); // Ocultar LoginFrame
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
        centerPanel.setBounds(95, 53, 308, 392);
        gradientPanel.add(centerPanel);
    }

    private void iniciarSesion() {
        String username = textUsuario.getText().trim();
        String password = new String((textcontraseña).getPassword()).trim();

        Authentication auth = new Authentication();

        if (auth.authenticateUser(username, password)) {
            JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            // Va a la ventana MainFrame
            MainFrame mainFrame = new MainFrame();
            mainFrame.setNombreUsuario(username);
            mainFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.", "Error de Inicio de Sesión", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
