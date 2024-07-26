package UISwing.ventanas;

import javax.swing.*;
import java.awt.*;

public class RegistroMascota extends JPanel {

    private static final long serialVersionUID = 1L;

    public RegistroMascota() {
        setLayout(new BorderLayout(10, 10)); // Añade espacio entre componentes
        initializeUI();
    }

    private void initializeUI() {
        JPanel panelBotones = crearPanelBotones();
        add(panelBotones, BorderLayout.SOUTH);

        JPanel panelPrincipal = crearPanelRegistroMascota();
        add(panelPrincipal, BorderLayout.CENTER);
        
        JPanel panel = new JPanel();
        add(panel, BorderLayout.NORTH);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        
        JLabel lblNewLabel = new JLabel("Registro Mascota");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        panel.add(lblNewLabel);
    }

    private JPanel crearPanelBotones() {
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCerrar = new JButton("Cerrar");

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.add(btnEliminar);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCerrar);

        return panelBotones;
    }

    private JPanel crearPanelRegistroMascota() {
        JPanel PMascotas = new JPanel();
        PMascotas.setBackground(Color.WHITE);

        // Nombre
        JLabel lbNombre = new JLabel("Nombre");
        lbNombre.setBounds(51, 33, 44, 33);
        JTextField tfNombre = new JTextField(10);
        tfNombre.setBounds(51, 62, 186, 33);
        PMascotas.setLayout(null);
        PMascotas.add(lbNombre);
        PMascotas.add(tfNombre);

        // Especie
        JLabel lbEspecie = new JLabel("Especie");
        lbEspecie.setBounds(331, 33, 44, 33);
        JTextField tfEspecie = new JTextField(10);
        tfEspecie.setBounds(331, 62, 186, 33);
        PMascotas.add(lbEspecie);
        PMascotas.add(tfEspecie);

        // Microchip
        JLabel lbMicrochip = new JLabel("Microchip");
        lbMicrochip.setBounds(607, 33, 44, 33);
        JTextField tfMicrochip = new JTextField(10);
        tfMicrochip.setBounds(607, 62, 186, 33);
        PMascotas.add(lbMicrochip);
        PMascotas.add(tfMicrochip);

        // Raza
        JLabel lbRaza = new JLabel("Raza");
        lbRaza.setBounds(332, 132, 31, 33);
        JTextField tfRaza = new JTextField(10);
        tfRaza.setBounds(331, 159, 186, 33);
        PMascotas.add(lbRaza);
        PMascotas.add(tfRaza);

        // Fecha de Nacimiento
        JLabel lbFechaNacimiento = new JLabel("Fecha de Nacimiento");
        lbFechaNacimiento.setBounds(51, 132, 99, 33);
        JTextField tfFechaNacimiento = new JTextField(10);
        tfFechaNacimiento.setBounds(51, 159, 186, 33);
        PMascotas.add(lbFechaNacimiento);
        PMascotas.add(tfFechaNacimiento);

        // Caracter
        JLabel lbCaracter = new JLabel("Caracter");
        lbCaracter.setBounds(607, 132, 42, 33);
        JTextField tfCaracter = new JTextField(10);
        tfCaracter.setBounds(607, 159, 186, 33);
        PMascotas.add(lbCaracter);
        PMascotas.add(tfCaracter);

        // Color
        JLabel lbColor = new JLabel("Color");
        lbColor.setBounds(51, 226, 31, 33);
        JTextField tfColor = new JTextField(10);
        tfColor.setBounds(51, 260, 186, 33);
        PMascotas.add(lbColor);
        PMascotas.add(tfColor);

        // Tipo de pelo
        JLabel lbTipoPelo = new JLabel("Tipo de pelo");
        lbTipoPelo.setBounds(337, 226, 62, 33);
        JTextField tfTipoPelo = new JTextField(10);
        tfTipoPelo.setBounds(331, 260, 186, 33);
        PMascotas.add(lbTipoPelo);
        PMascotas.add(tfTipoPelo);

        // Sexo
        JLabel lbSexo = new JLabel("Sexo");
        lbSexo.setBounds(51, 312, 31, 33);
        JComboBox<String> cbSexo = new JComboBox<>(new String[]{"Masculino", "Femenino"});
        cbSexo.setBounds(51, 340, 186, 33);
        PMascotas.add(lbSexo);
        PMascotas.add(cbSexo);

        // Esterilizado
        JLabel lbEsterilizado = new JLabel("Esterilizado");
        lbEsterilizado.setBounds(331, 304, 62, 33);
        JComboBox<String> cbEsterilizado = new JComboBox<>(new String[]{"Sí", "No"});
        cbEsterilizado.setBounds(331, 340, 186, 33);
        PMascotas.add(lbEsterilizado);
        PMascotas.add(cbEsterilizado);

        // Ajusta el tamaño del panel automáticamente
        PMascotas.setPreferredSize(PMascotas.getPreferredSize());

        return PMascotas;
    }


    // Método main para probar la ventana
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new RegistroMascota());
        frame.pack(); // Ajusta el tamaño de la ventana a los componentes
        frame.setLocationRelativeTo(null); // Centra la ventana
        frame.setVisible(true);
    }
}
