package UISwing.ventanas;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import com.toedter.calendar.JDateChooser;
import DB.CitaDAO;
import DB.ClienteDAO;
import DB.FarmacoDAO;
import DB.HospitalizacionDAO;
import DB.MascotaDAO;
import DB.VeterinarioDAO;
import model.Cita;
import model.Cliente;
import model.Farmaco;
import model.Hospitalizacion;
import model.Mascota;
import model.Mascota.MascotaContenedor;
import model.UsoFarmaco;
import model.Veterinario;
import UISwing.recursos.RoundedPanel;
import java.util.List;

public class DialogoDetalleHospitalizados extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private RoundedPanel roundedPanel;
	private JTextField txtMascotaNombre;
	private JTextField txtVeterinarioNombre;
    private JDateChooser dateChooserIngreso;
    private JDateChooser dateChooserSalida;
    private JTextArea textAreaMotivo;
    private JTextArea textAreaTratamiento;
    private JComboBox<String> comboBoxEstado;
    private JTextArea textAreaNotas;
    private JButton saveButton;
    private JButton cancelButton;
	private HospitalizacionDAO hospitalizacionDAO;
	private JButton btnAddFarmaco;
	private FarmacoDAO farmacoDAO;
	private Hospitalizacion hospitalizacion;
	private static ActualizacionListener actualizacionListener;
	private List<UsoFarmaco> usosFarmacos = new ArrayList<>();
	
	
	public interface ActualizacionListener {
	    void onActualizacion();
	}


    
	 public DialogoDetalleHospitalizados(Frame owner, boolean modal, ActualizacionListener actualizacionListener) {
	        super(owner, modal);
	        this.actualizacionListener = actualizacionListener;
	    setTitle("Hospitalizaciones");
	    farmacoDAO = new FarmacoDAO();
	    hospitalizacionDAO = new HospitalizacionDAO(); // Asegúrate de que esta línea esté agregada
	    setUndecorated(true);
	    setSize(new Dimension(888,455));    
	    setLocationRelativeTo(null);
 
	        initDialogComponents();
	       
		}
	 
	 

	private void initDialogComponents() {
		 	roundedPanel = new RoundedPanel(30, Color.decode("#0483FF"));
	        roundedPanel.setLayout(null);
	        roundedPanel.setBounds(0, 0, 888, 399);
	        roundedPanel.setOpaque(false);
	        setBackground(new Color(0, 0, 0, 0));

	        getContentPane().add(roundedPanel);

	        txtMascotaNombre = new JTextField();
	        txtMascotaNombre.setBounds(40, 55, 244, 30);
	        txtMascotaNombre.setEditable(false); // Para que no se pueda editar
	        txtMascotaNombre.setFont(new Font("Segoe UI", Font.PLAIN, 12));
	       // txtMascotaNombre.setForeground(Color.WHITE); // Texto en blanco
	        txtMascotaNombre.setOpaque(true); // Fondo transparente
	        txtMascotaNombre.setBorder(null); // Sin borde
	        roundedPanel.add(txtMascotaNombre);



	        // Selector de fecha de ingreso
	        dateChooserIngreso = new JDateChooser();
	        dateChooserIngreso.setBounds(40, 127, 244, 30);
	        roundedPanel.add(dateChooserIngreso);
	        // Selector de fecha de salida (opcional al inicio)
	        dateChooserSalida = new JDateChooser();
	        dateChooserSalida.setBounds(319, 127, 200, 30);

	     // Reemplazo de JTextField por JTextArea para el motivo de la hospitalización
	        textAreaMotivo = new JTextArea();
	        textAreaMotivo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
	        textAreaMotivo.setLineWrap(true);
	        textAreaMotivo.setWrapStyleWord(true);
	        JScrollPane scrollPaneMotivo = new JScrollPane(textAreaMotivo);
	        scrollPaneMotivo.setBounds(568, 55, 280, 30); // Mismo tamaño y ubicación que textFieldMotivo
	        

	     
	        // ComboBox para el estado inicial de la mascota
	        comboBoxEstado = new JComboBox<>(new String[]{"Estable", "Crítico", "En observación"});
	        comboBoxEstado.setFont(new Font("Segoe UI", Font.PLAIN, 12));
	        comboBoxEstado.setBounds(319, 55, 200, 30);
	        

	        // Botones
	        saveButton = new JButton("Guardar");
	        saveButton.setFont(new Font("Tahoma", Font.BOLD, 12));
	        saveButton.setBounds(748, 392, 100, 30);
	        saveButton.setBackground(Color.WHITE);
	        saveButton.setForeground(Color.decode("#0057FF")); // Letras en color azul
	        saveButton.setFocusPainted(false); // Evita que se pinte el foco alrededor del botón
	        saveButton.setBorderPainted(false); // Evita que se pinte el borde predeterminado
	        saveButton.setContentAreaFilled(false); // Evita que se pinte el área de contenido
	        saveButton.setOpaque(true);
	        saveButton.setRolloverEnabled(true);
	        saveButton.addMouseListener(new java.awt.event.MouseAdapter() {
	            @Override
	            public void mouseEntered(java.awt.event.MouseEvent evt) {
	                saveButton.setBackground(Color.decode("#003366")); // Color azul oscuro para rollover
	                saveButton.setForeground(Color.WHITE);
	            }

	            @Override
	            public void mouseExited(java.awt.event.MouseEvent evt) {
	                saveButton.setBackground(Color.WHITE); // Color blanco cuando el ratón sale
	                saveButton.setForeground(Color.decode("#0057FF"));
	            }
	        });
	        saveButton.addActionListener(e -> guardarCambios());
	       

	        cancelButton = new JButton("Cancelar");
	        cancelButton.setFont(new Font("Tahoma", Font.BOLD, 12));
	        cancelButton.setBounds(590, 392, 100, 30);
	        cancelButton.setBackground(Color.WHITE);
	        cancelButton.setForeground(Color.decode("#0057FF")); // Letras en color azul
	        cancelButton.setFocusPainted(false); // Evita que se pinte el foco alrededor del botón
	        cancelButton.setBorderPainted(false); // Evita que se pinte el borde predeterminado
	        cancelButton.setContentAreaFilled(false); // Evita que se pinte el área de contenido
	        cancelButton.setOpaque(true);
	        cancelButton.setRolloverEnabled(true);
	        cancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
	            @Override
	            public void mouseEntered(java.awt.event.MouseEvent evt) {
	                cancelButton.setBackground(Color.decode("#003366")); // Color azul oscuro para rollover
	                cancelButton.setForeground(Color.WHITE);
	            }

	            @Override
	            public void mouseExited(java.awt.event.MouseEvent evt) {
	                cancelButton.setBackground(Color.WHITE); // Color blanco cuando el ratón sale
	                cancelButton.setForeground(Color.decode("#0057FF"));
	            }
	        });
	        cancelButton.addActionListener(e -> dispose());

	       
	        
	        // Área de texto para el tratamiento
	        textAreaTratamiento = new JTextArea();
	        textAreaTratamiento.setFont(new Font("Segoe UI", Font.PLAIN, 12));
	        textAreaTratamiento.setLineWrap(true);
	        textAreaTratamiento.setWrapStyleWord(true);
	        JScrollPane scrollPaneTratamiento = new JScrollPane(textAreaTratamiento); // Asocias el JTextArea al JScrollPane
	        scrollPaneTratamiento.setBounds(40, 200, 808, 87);
	        roundedPanel.add(scrollPaneTratamiento);
	        	        
	        // Área de texto para notas adicionales
	        textAreaNotas = new JTextArea();
	        textAreaNotas.setFont(new Font("Segoe UI", Font.PLAIN, 12));
	        textAreaNotas.setLineWrap(true);
	        textAreaNotas.setWrapStyleWord(true);
	        JScrollPane scrollPaneNotas = new JScrollPane(textAreaNotas); // Asocias el JTextArea al JScrollPane
	        scrollPaneNotas.setBounds(40, 320, 808, 50);
	        roundedPanel.add(scrollPaneNotas);
	        
	        txtVeterinarioNombre = new JTextField();
	        txtVeterinarioNombre.setBounds(569, 127, 279, 30);
	        txtVeterinarioNombre.setFont(new Font("Segoe UI", Font.PLAIN, 12));
	        txtVeterinarioNombre.setEditable(false); // Para que no se pueda editar
	        //txtVeterinarioNombre.setForeground(Color.WHITE); // Texto en blanco
	        txtVeterinarioNombre.setOpaque(true); // Fondo transparente
	        txtVeterinarioNombre.setBorder(null); // Sin borde
	        roundedPanel.add(txtVeterinarioNombre);
	        
	       /* JPanel centerPanel = new JPanel() {
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
	        centerPanel.setBounds(21, 21, 846, 414);*/
	        
	        // Agregar los componentes al panel
	        
	          
	        roundedPanel.add(dateChooserIngreso);
	        roundedPanel.add(dateChooserSalida);
	        roundedPanel.add(scrollPaneMotivo);
	        roundedPanel.add(scrollPaneTratamiento);
	        roundedPanel.add(comboBoxEstado);
	        roundedPanel.add(scrollPaneNotas);
	        roundedPanel.add(saveButton);
	        roundedPanel.add(cancelButton);
	        //roundedPanel.add(centerPanel);
	        
	        JLabel lblMascota = new JLabel("Mascota:");
	        lblMascota.setForeground(new Color(255, 255, 255));
	        lblMascota.setFont(new Font("Segoe UI", Font.BOLD, 13));
	        lblMascota.setBounds(40, 26, 79, 30);
	        roundedPanel.add(lblMascota);
	        
	        JLabel lblEstado = new JLabel("Estado:");
	        lblEstado.setForeground(new Color(255, 255, 255));
	        lblEstado.setFont(new Font("Segoe UI", Font.BOLD, 13));
	        lblEstado.setBounds(319, 26, 79, 30);
	        roundedPanel.add(lblEstado);
	        
	        JLabel lblMotivo = new JLabel("Motivo:");
	        lblMotivo.setForeground(new Color(255, 255, 255));
	        lblMotivo.setFont(new Font("Segoe UI", Font.BOLD, 13));
	        lblMotivo.setBounds(568, 26, 79, 30);
	        roundedPanel.add(lblMotivo);
	        
	        JLabel lblFechaIngreso = new JLabel("Fecha ingreso:");
	        lblFechaIngreso.setForeground(new Color(255, 255, 255));
	        lblFechaIngreso.setFont(new Font("Segoe UI", Font.BOLD, 13));
	        lblFechaIngreso.setBounds(40, 96, 111, 30);
	        roundedPanel.add(lblFechaIngreso);
	        
	        JLabel lblFechaSalida = new JLabel("Fecha salida:");
	        lblFechaSalida.setForeground(new Color(255, 255, 255));
	        lblFechaSalida.setFont(new Font("Segoe UI", Font.BOLD, 13));
	        lblFechaSalida.setBounds(319, 94, 111, 30);
	        roundedPanel.add(lblFechaSalida);
	        
	        JLabel lblTratamiento = new JLabel("Tratamiento:");
	        lblTratamiento.setForeground(new Color(255, 255, 255));
	        lblTratamiento.setFont(new Font("Segoe UI", Font.BOLD, 13));
	        lblTratamiento.setBounds(40, 168, 111, 30);
	        roundedPanel.add(lblTratamiento);
	        
	        JLabel lblNotas = new JLabel("Notas:");
	        lblNotas.setForeground(new Color(255, 255, 255));
	        lblNotas.setFont(new Font("Segoe UI", Font.BOLD, 13));
	        lblNotas.setBounds(40, 286, 79, 30);
	        roundedPanel.add(lblNotas);
	        
	        JLabel lblVeterinario = new JLabel("Veterinario:");
	        lblVeterinario.setForeground(new Color(255, 255, 255));
	        lblVeterinario.setFont(new Font("Segoe UI", Font.BOLD, 13));
	        lblVeterinario.setBounds(569, 96, 79, 30);
	        roundedPanel.add(lblVeterinario);
	        
	        btnAddFarmaco = new JButton("Añadir Fármaco");
	        btnAddFarmaco.setBounds(369, 396, 150, 25); // Ajusta la posición como necesites
	        btnAddFarmaco.addActionListener(e -> abrirDialogoSeleccionFarmaco());
	        roundedPanel.add(btnAddFarmaco);
	      
	    }
	 
	private void abrirDialogoSeleccionFarmaco() {
	    try {
	        DialogoSeleccionFarmaco dialogo = new DialogoSeleccionFarmaco((Frame) this.getOwner(), true, farmacoDAO);
	        dialogo.setVisible(true);
	        if (dialogo.isSeleccionado()) {
	            Farmaco farmacoSeleccionado = dialogo.getFarmacoSeleccionado();
	            String dosis = dialogo.getDosis().replaceAll("\\D+","");  // Limpieza para asegurar que es numérico
	            String frecuencia = dialogo.getFrecuencia();
	            
	            // Compilar la información del tratamiento actualizado
	            String tratamientoActualizado = textAreaTratamiento.getText() + farmacoSeleccionado.getNombre() + 
	                                            " - Dosis: " + dosis + "mg, Frecuencia: " + frecuencia + "\n";
	            textAreaTratamiento.setText(tratamientoActualizado);
	            
	            // Actualizar el stock y registrar el uso
	            if (farmacoDAO.actualizarStockFarmaco(farmacoSeleccionado.getId(), -1)) {
	                UsoFarmaco uso = new UsoFarmaco();
	                uso.setIdFarmaco(farmacoSeleccionado.getId());
	                uso.setIdHospitalizacion(hospitalizacion.getId());
	                uso.setCantidadUsada(Integer.parseInt(dosis));  // Convertir dosis a entero si es necesario
	                uso.setFrecuencia(frecuencia);
	                uso.setFechaHoraUso(LocalDateTime.now());

	                if (farmacoDAO.registrarUsoFarmaco(uso)) {
	                    hospitalizacion.setTratamiento(tratamientoActualizado);
	                    if (!hospitalizacionDAO.actualizarHospitalizacion(hospitalizacion)) {
	                        JOptionPane.showMessageDialog(this, "Error al actualizar el tratamiento.", "Error", JOptionPane.ERROR_MESSAGE);
	                    }
	                } else {
	                    JOptionPane.showMessageDialog(this, "Error al registrar el uso del fármaco.", "Error", JOptionPane.ERROR_MESSAGE);
	                }
	            } else {
	                JOptionPane.showMessageDialog(this, "Error al actualizar el stock de fármacos.", "Error", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(this, "Error al abrir el diálogo de selección de fármaco: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

	 
	 public void cargarDatosHospitalizacion(Hospitalizacion hospitalizacion) {
		    this.hospitalizacion = hospitalizacion; // Asegurarse de inicializar la variable de clase con el objeto hospitalizacion pasado como parámetro.

		    txtMascotaNombre.setText(hospitalizacion.getNombreMascota());
		    txtVeterinarioNombre.setText(hospitalizacion.getNombreVeterinario());
		    dateChooserIngreso.setDate(Date.from(hospitalizacion.getFechaIngreso().atZone(ZoneId.systemDefault()).toInstant()));
		    if (hospitalizacion.getFechaSalida() != null) {
		        dateChooserSalida.setDate(Date.from(hospitalizacion.getFechaSalida().atZone(ZoneId.systemDefault()).toInstant()));
		    }
		    textAreaMotivo.setText(hospitalizacion.getMotivo());
		    textAreaTratamiento.setText(hospitalizacion.getTratamiento());
		    comboBoxEstado.setSelectedItem(hospitalizacion.getEstado());
		    textAreaNotas.setText(hospitalizacion.getNotas());
		}



	 private void guardarCambios() {
		    try {
		        // Recolectar datos de la UI
		        LocalDateTime fechaSalida = null;
		        if (dateChooserSalida.getDate() != null) {
		            fechaSalida = LocalDateTime.ofInstant(dateChooserSalida.getDate().toInstant(), ZoneId.systemDefault());
		        }
		        String tratamiento = textAreaTratamiento.getText();
		        String notas = textAreaNotas.getText();
		        String estado = comboBoxEstado.getSelectedItem().toString();
		        
		        // Suponiendo que tienes una instancia de Hospitalizacion que estás editando
		        hospitalizacion.setFechaSalida(fechaSalida);
		        hospitalizacion.setTratamiento(tratamiento);
		        hospitalizacion.setNotas(notas);
		        hospitalizacion.setEstado(estado);
		        
		        // Llama al DAO para guardar los cambios
		        boolean resultado = hospitalizacionDAO.actualizarHospitalizacion(hospitalizacion);
		        
		        if (resultado) {
		            JOptionPane.showMessageDialog(this, "Cambios guardados correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
		            if (actualizacionListener != null) {
		                actualizacionListener.onActualizacion(); // Notifica al listener para que actualice la lista
		            }
		            dispose(); // Cierra este diálogo después de guardar los cambios
		        } else {
		            JOptionPane.showMessageDialog(this, "No se pudieron guardar los cambios.", "Error", JOptionPane.ERROR_MESSAGE);
		        }

		    } catch (Exception ex) {
		        JOptionPane.showMessageDialog(this, "Error al guardar los cambios: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		    }
		}




	 
	 




		private void actualizarFechaSalida(int idMascota, Date fechaSalida) {
		    // Aquí necesitarías un método en tu HospitalizacionDAO que te permita actualizar la fecha de salida
		    boolean resultado = hospitalizacionDAO.actualizarFechaSalidaHospitalizacion(idMascota, new java.sql.Date(fechaSalida.getTime()));
		    if (resultado) {
		        JOptionPane.showMessageDialog(this, "Fecha de salida actualizada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
		    } else {
		        JOptionPane.showMessageDialog(this, "No se pudo actualizar la fecha de salida.", "Error", JOptionPane.ERROR_MESSAGE);
		    }
		}
		
		

	



	public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        	DialogoDetalleHospitalizados dialog = new DialogoDetalleHospitalizados(null, true, actualizacionListener);
            dialog.setVisible(true);
        });
    }
}
