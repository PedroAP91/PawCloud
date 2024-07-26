package UISwing.ventanas;

import javax.swing.*;

import com.toedter.calendar.JDateChooser;

import DB.VeterinarioDAO;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;

import UISwing.recursos.RoundedPanel;
import model.Veterinario;

public class VentanaRegistroVeterinarioDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private RoundedPanel roundedPanel;
	private JTextField txtNombre, txtApellidos, txtLicencia, txtTelefono, txtEmail;
	private JButton saveButton, cancelButton;
	private JDateChooser dateChooserContratacion;

	public VentanaRegistroVeterinarioDialog(Frame owner, boolean modal) {
		super(owner, modal);
		setTitle("Registro de Veterinario");
		setUndecorated(true);
		setSize(new Dimension(500, 400));
		setLocationRelativeTo(null);

		roundedPanel = new RoundedPanel(30, Color.decode("#0483FF"));
		roundedPanel.setLayout(null);
		roundedPanel.setBounds(0, 0, 500, 400);
		roundedPanel.setOpaque(false);
		setBackground(new Color(0, 0, 0, 0));

		getContentPane().add(roundedPanel);
		initComponents();
	}

	private void initComponents() {
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setForeground(Color.WHITE);
		lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNombre.setBounds(45, 30, 100, 25);
		roundedPanel.add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(45, 63, 110, 25);
		roundedPanel.add(txtNombre);

		JLabel lblApellidos = new JLabel("Apellidos:");
		lblApellidos.setForeground(Color.WHITE);
		lblApellidos.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblApellidos.setBounds(196, 30, 100, 25);
		roundedPanel.add(lblApellidos);

		txtApellidos = new JTextField();
		txtApellidos.setBounds(196, 63, 110, 25);
		roundedPanel.add(txtApellidos);

		JLabel lblLicencia = new JLabel("Licencia:");
		lblLicencia.setForeground(Color.WHITE);
		lblLicencia.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblLicencia.setBounds(343, 30, 100, 25);
		roundedPanel.add(lblLicencia);

		txtLicencia = new JTextField();
		txtLicencia.setBounds(343, 63, 110, 25);
		roundedPanel.add(txtLicencia);

		JLabel lblTelefono = new JLabel("Teléfono:");
		lblTelefono.setForeground(Color.WHITE);
		lblTelefono.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblTelefono.setBounds(45, 112, 100, 25);
		roundedPanel.add(lblTelefono);

		txtTelefono = new JTextField();
		txtTelefono.setBounds(45, 146, 110, 25);
		roundedPanel.add(txtTelefono);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblEmail.setBounds(196, 112, 100, 25);
		roundedPanel.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setBounds(196, 146, 110, 25);
		roundedPanel.add(txtEmail);

		saveButton = new JButton("Guardar");
		saveButton.setBounds(353, 338, 100, 30);
		initButton(saveButton, "#0057FF", "#003366");
		saveButton.addActionListener(e -> {
			// Aquí va la lógica para guardar el veterinario
		});
		roundedPanel.add(saveButton);

		cancelButton = new JButton("Cancelar");
		cancelButton.setBounds(216, 339, 100, 30);
		initButton(cancelButton, "#0057FF", "#003366");
		cancelButton.addActionListener(e -> dispose());
		roundedPanel.add(cancelButton);

		JLabel lblEspecialidades = new JLabel("Especialidades:");
		lblEspecialidades.setForeground(Color.WHITE);
		lblEspecialidades.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblEspecialidades.setBounds(343, 112, 120, 25);
		roundedPanel.add(lblEspecialidades);

		JTextField txtEspecialidades = new JTextField();
		txtEspecialidades.setBounds(343, 146, 110, 25);
		roundedPanel.add(txtEspecialidades);

		JLabel lblHorarioTrabajo = new JLabel("Horario de trabajo:");
		lblHorarioTrabajo.setForeground(Color.WHITE);
		lblHorarioTrabajo.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblHorarioTrabajo.setBounds(45, 249, 140, 25);
		roundedPanel.add(lblHorarioTrabajo);

		JTextField txtHorarioTrabajo = new JTextField();
		txtHorarioTrabajo.setBounds(45, 285, 408, 25);
		roundedPanel.add(txtHorarioTrabajo);

		JLabel lblFechaContratacion = new JLabel("Fecha de contratación:");
		lblFechaContratacion.setForeground(Color.WHITE);
		lblFechaContratacion.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblFechaContratacion.setBounds(45, 204, 160, 25);
		
		roundedPanel.add(lblFechaContratacion);

		dateChooserContratacion = new JDateChooser();
		dateChooserContratacion.setBounds(223, 204, 230, 25);
		if (dateChooserContratacion.getDate() != null) {
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		    String fechaContratacion = sdf.format(dateChooserContratacion.getDate());
		    System.out.println(fechaContratacion);
		    // Aquí puedes usar fechaContratacion para lo que necesites, como guardarla en la base de datos
		}

		roundedPanel.add(dateChooserContratacion);

		JPanel centerPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				// Personaliza aquí tu componente
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setComposite(AlphaComposite.SrcOver.derive(0.5f)); // Ajusta la opacidad aquí
				g2.setColor(getBackground());
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Puedes ajustar el radio de las esquinas si
																			// es necesario
				g2.dispose();
				super.paintComponent(g);
			}
		};
		centerPanel.setBackground(new Color(255, 255, 255, 80)); // Color de fondo con opacidad
		centerPanel.setOpaque(false); // Hace que el panel no pinte todos sus píxeles, lo que permite que se vea el
										// fondo.
		centerPanel.setBounds(21, 21, 457, 360);
		roundedPanel.add(centerPanel);
		
		saveButton.addActionListener(e -> {
		    Veterinario veterinario = new Veterinario();
		    veterinario.setNombre(txtNombre.getText());
		    veterinario.setApellidos(txtApellidos.getText());
		    veterinario.setLicencia(txtLicencia.getText());
		    veterinario.setTelefono(txtTelefono.getText());
		    veterinario.setEmail(txtEmail.getText());
		    veterinario.setEspecialidades(txtEspecialidades.getText());
		    veterinario.setHorarioTrabajo(txtHorarioTrabajo.getText());

		    // Captura de la fecha de contratación desde el JDateChooser
		    if (dateChooserContratacion.getDate() != null) {
		        veterinario.setFechaContratacion(new java.sql.Date(dateChooserContratacion.getDate().getTime()));
		    } else {
		        // Manejar el caso en que no se haya seleccionado una fecha
		        JOptionPane.showMessageDialog(this, "Debe seleccionar una fecha de contratación.", "Error", JOptionPane.ERROR_MESSAGE);
		        return; // Para no proceder con el registro si la fecha no está establecida
		    }

		    try {
		        VeterinarioDAO veterinarioDAO = new VeterinarioDAO();
		        boolean success = veterinarioDAO.insertarVeterinario(veterinario);

		        if (success) {
		            JOptionPane.showMessageDialog(this, "Veterinario registrado con éxito.");
		            dispose();
		        } else {
		            JOptionPane.showMessageDialog(this, "Error al registrar el veterinario.");
		        }
		    } catch (Exception ex) {
		        ex.printStackTrace();
		        JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos: " + ex.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
		    }
		});




	}

	private void initButton(JButton button, String colorHex, String rolloverColorHex) {
		button.setFont(new Font("Tahoma", Font.BOLD, 12));
		button.setBackground(Color.WHITE);
		button.setForeground(Color.decode(colorHex));
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setOpaque(true);
		button.setRolloverEnabled(true);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent evt) {
				button.setBackground(Color.decode(rolloverColorHex));
				button.setForeground(Color.WHITE);
			}

			@Override
			public void mouseExited(MouseEvent evt) {
				button.setBackground(Color.WHITE);
				button.setForeground(Color.decode(colorHex));
			}
		});
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			VentanaRegistroVeterinarioDialog dialog = new VentanaRegistroVeterinarioDialog(null, true);
			dialog.setVisible(true);
		});
	}
}
