package UISwing.ventanas;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSeparator;

public class PanelAdministracion extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public PanelAdministracion() {
		
		JPanel panelVeterinarios = new JPanel();
		panelVeterinarios.setBackground(Color.decode("#7E88E2"));
		panelVeterinarios.setBounds(38, 24, 496, 607);
		setLayout(null);
		panelVeterinarios.setLayout(null);
		add(panelVeterinarios);
		
		JButton btnAgregarVeterinario = new JButton("Agregar Veterinario");
		btnAgregarVeterinario.setBounds(41, 542, 415, 37);
		panelVeterinarios.add(btnAgregarVeterinario);
		
		JButton btnListaVeterinarios = new JButton("Lista Veterinarios");
		btnListaVeterinarios.setBounds(41, 487, 415, 37);
		panelVeterinarios.add(btnListaVeterinarios);
		
		JPanel panel = new JPanel();
		panel.setBounds(41, 72, 415, 394);
		panelVeterinarios.add(panel);
		panel.setLayout(null);
		
		JLabel lblImageVet = new JLabel("Imagen");
		lblImageVet.setBounds(23, 26, 38, 38);
		panel.add(lblImageVet);
		
		JLabel lblImageVet_1 = new JLabel("Imagen");
		lblImageVet_1.setBounds(23, 70, 38, 38);
		panel.add(lblImageVet_1);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(82, 26, 278, 38);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setBounds(82, 70, 278, 38);
		panel.add(lblNewLabel_1);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.WHITE);
		separator.setBounds(10, 118, 395, 2);
		panel.add(separator);
		
		JLabel lblImageVet_2 = new JLabel("Imagen");
		lblImageVet_2.setBounds(23, 143, 38, 38);
		panel.add(lblImageVet_2);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setBounds(82, 143, 278, 38);
		panel.add(lblNewLabel_2);
		
		JLabel lblImageVet_1_1 = new JLabel("Imagen");
		lblImageVet_1_1.setBounds(23, 187, 38, 38);
		panel.add(lblImageVet_1_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("New label");
		lblNewLabel_1_1.setBounds(82, 187, 278, 38);
		panel.add(lblNewLabel_1_1);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.WHITE);
		separator_1.setBounds(10, 235, 395, 2);
		panel.add(separator_1);
		
		JLabel lblImageVet_3 = new JLabel("Imagen");
		lblImageVet_3.setBounds(23, 261, 38, 38);
		panel.add(lblImageVet_3);
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setBounds(82, 261, 278, 38);
		panel.add(lblNewLabel_3);
		
		JLabel lblImageVet_1_2 = new JLabel("Imagen");
		lblImageVet_1_2.setBounds(23, 305, 38, 38);
		panel.add(lblImageVet_1_2);
		
		JLabel lblNewLabel_1_2 = new JLabel("New label");
		lblNewLabel_1_2.setBounds(82, 305, 278, 38);
		panel.add(lblNewLabel_1_2);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(Color.WHITE);
		separator_2.setBounds(10, 353, 395, 2);
		panel.add(separator_2);
		
		JLabel lblVetImage = new JLabel("New label");
		lblVetImage.setBounds(41, 24, 46, 37);
		panelVeterinarios.add(lblVetImage);
		
		JLabel lblHorarioVeterinario = new JLabel("Horario Veterinario");
		lblHorarioVeterinario.setForeground(new Color(255, 255, 255));
		lblHorarioVeterinario.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblHorarioVeterinario.setBounds(91, 24, 172, 37);
		panelVeterinarios.add(lblHorarioVeterinario);
		btnAgregarVeterinario.addActionListener(new ActionListener() {	    public void actionPerformed(ActionEvent e) {
		        // Crear y mostrar el diálogo de registro de veterinario
		        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(PanelAdministracion.this);
		        VentanaRegistroVeterinarioDialog dialogoRegistroVeterinario = new VentanaRegistroVeterinarioDialog(frame, true);
		        dialogoRegistroVeterinario.setVisible(true);
		    }
		});
		
		JPanel panelEmpleados = new JPanel();
		panelEmpleados.setBackground(Color.decode("#003366"));
		panelEmpleados.setLayout(null);
		panelEmpleados.setBounds(578, 24, 496, 607);
		add(panelEmpleados);
		
		JButton btnListaEmpleados = new JButton("Lista Empleados");
		btnListaEmpleados.setBounds(41, 544, 199, 37);
		panelEmpleados.add(btnListaEmpleados);
		
		JButton btnAgregarEmpleado = new JButton("Agregar Empleado");
		btnAgregarEmpleado.setBounds(262, 544, 194, 37);
		panelEmpleados.add(btnAgregarEmpleado);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(41, 72, 415, 447);
		panelEmpleados.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblHorarioEmpleados = new JLabel("Horario Empleados");
		lblHorarioEmpleados.setForeground(new Color(255, 255, 255));
		lblHorarioEmpleados.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblHorarioEmpleados.setBounds(87, 24, 207, 37);
		panelEmpleados.add(lblHorarioEmpleados);
		
		JLabel lblEmpleadoImage = new JLabel("New label");
		lblEmpleadoImage.setBounds(41, 24, 46, 37);
		panelEmpleados.add(lblEmpleadoImage);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(0, 492, 40, 40);
		add(btnNewButton);

	}
}
