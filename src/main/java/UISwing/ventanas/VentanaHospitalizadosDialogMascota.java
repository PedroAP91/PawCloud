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
import model.Veterinario;
import UISwing.recursos.RoundedPanel;
import java.util.List;
import java.sql.Timestamp;
import javax.swing.JOptionPane;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.LocalDateTime;

public class VentanaHospitalizadosDialogMascota extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private RoundedPanel roundedPanel;
	private ClienteDAO clienteDAO;
    private MascotaDAO mascotaDAO;
    private JSpinner timeSpinner;
    private JSpinner dateSpinner;
    private JButton calendarButton;
    private JDateChooser dateChooserIngreso;
    private JDateChooser dateChooserSalida; // Opcional, puede ser null al inicio
    private JTextArea textAreaMotivo;
    private JTextArea textAreaTratamiento;
    private JComboBox<String> comboBoxEstado;
    private JTextArea textAreaNotas;
    private JButton saveButton;
    private JButton cancelButton;
	private HospitalizacionDAO hospitalizacionDAO;
	private JButton btnAddFarmaco;
	private FarmacoDAO farmacoDAO;
	private JComboBox<Veterinario> comboBoxVeterinarios;
	private String nombreMascota;
	private int idMascota;
	private HospitalizacionDAO hospitalizacionDao;
	private int idHospitalizacion = -1;

    
	public VentanaHospitalizadosDialogMascota(Frame owner, boolean modal, int idMascota, String nombreMascota) {
	    super(owner, modal);
	    this.idMascota = idMascota; // Almacena el ID de la mascota
	    this.nombreMascota = nombreMascota;
	    
	    
	    setTitle("Hospitalizaciones - " + nombreMascota);
	        setTitle("Hospitalizaciones");
	        farmacoDAO = new FarmacoDAO();
	        setUndecorated(true); // Esto elimina la decoración de la ventana
	        setSize(new Dimension(888,455));
	        
	        setLocationRelativeTo(null);
	        
	        
	        
	        roundedPanel = new RoundedPanel(30, Color.decode("#5694F9"));
	        roundedPanel.setLayout(null);
	        roundedPanel.setBounds(0, 0, 888, 399); // Ajustar al tamaño del JDialog
	        roundedPanel.setOpaque(false); // Hacer el fondo transparente
	        setBackground(new java.awt.Color(0, 0, 0, 0));
	        clienteDAO = new ClienteDAO();
	        mascotaDAO = new MascotaDAO();
	        hospitalizacionDAO = new HospitalizacionDAO();
	        
	        getContentPane().add(roundedPanel);
	        initDialogComponents();
	       
	    }
	       
	
	 private void initDialogComponents() {
	     	
			 // Configura el JTextField utilizado para la entrada de búsqueda
		  JLabel labelNombreMascota = new JLabel("Mascota: " + nombreMascota);
		    labelNombreMascota.setBounds(40, 10, 300, 25);
		    roundedPanel.add(labelNombreMascota);


	        // Selector de fecha de ingreso
			dateChooserIngreso = new JDateChooser();
			dateChooserIngreso.setDate(new Date()); // Establecer a la fecha actual
			dateChooserIngreso.setBounds(40, 127, 244, 30);
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
	        saveButton.addActionListener(e -> {
	            guardarHospitalizacion();
	        });

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
	        cancelButton.addActionListener(e -> {
	            dispose(); // Cerrar el diálogo
	        });

	       
	        
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
	        
	        comboBoxVeterinarios = new JComboBox<>();
	        comboBoxVeterinarios.setBounds(569, 127, 279, 30); // Ajusta según tus necesidades
	        roundedPanel.add(comboBoxVeterinarios);
	        llenarComboBoxVeterinarios();
	        
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
	 public VentanaHospitalizadosDialogMascota(Frame owner, boolean modal, int idMascota, String nombreMascota, int idHospitalizacion) {
		    super(owner, modal);
		    this.idMascota = idMascota; // Almacena el ID de la mascota
		    this.nombreMascota = nombreMascota;
		    this.idHospitalizacion = idHospitalizacion; // Almacena el ID de la hospitalización
		    this.hospitalizacionDao = new HospitalizacionDAO();

		    setTitle("Hospitalizaciones - " + nombreMascota);
		    farmacoDAO = new FarmacoDAO();
		    setUndecorated(true); // Esto elimina la decoración de la ventana
		    setSize(new Dimension(888,455));
		    
		    setLocationRelativeTo(null);
		    
		    roundedPanel = new RoundedPanel(30, Color.decode("#5694F9"));
		    roundedPanel.setLayout(null);
		    roundedPanel.setBounds(0, 0, 888, 399); // Ajustar al tamaño del JDialog
		    roundedPanel.setOpaque(false); // Hacer el fondo transparente
		    setBackground(new java.awt.Color(0, 0, 0, 0));
		    clienteDAO = new ClienteDAO();
		    mascotaDAO = new MascotaDAO();
		    hospitalizacionDAO = new HospitalizacionDAO();
		    
		    getContentPane().add(roundedPanel);
		    initDialogComponents();

		    // Ahora, esta lógica se puede aplicar correctamente ya que idHospitalizacion está definido
		    if (idHospitalizacion > 0) {
		        // Modo edición
		        cargarDatosHospitalizacion(idHospitalizacion);
		        saveButton.setText("Modificar");
		        // Asegurarse de eliminar action listeners previos para evitar acciones duplicadas
		        for (ActionListener al : saveButton.getActionListeners()) {
		            saveButton.removeActionListener(al);
		        }
		        saveButton.addActionListener(e -> actualizarHospitalizacion());
		    } else {
		        // Modo creación
		        saveButton.setText("Guardar");
		        // Asegurarse de eliminar action listeners previos para evitar acciones duplicadas
		        for (ActionListener al : saveButton.getActionListeners()) {
		            saveButton.removeActionListener(al);
		        }
		        saveButton.addActionListener(e -> guardarHospitalizacion());
		    }
		}
	 private void cargarDatosHospitalizacion(int idHospitalizacion) {
		    Hospitalizacion hospitalizacion = hospitalizacionDao.obtenerHospitalizacionPorId(idHospitalizacion);
		    if (hospitalizacion != null) {
		        // Ahora, rellena los campos del diálogo con los datos de la hospitalización
		        dateChooserIngreso.setDate(Date.from(hospitalizacion.getFechaIngreso().atZone(ZoneId.systemDefault()).toInstant()));
		        if (hospitalizacion.getFechaSalida() != null) {
		            dateChooserSalida.setDate(Date.from(hospitalizacion.getFechaSalida().atZone(ZoneId.systemDefault()).toInstant()));
		        }
		        textAreaMotivo.setText(hospitalizacion.getMotivo());
		        textAreaTratamiento.setText(hospitalizacion.getTratamiento());
		        comboBoxEstado.setSelectedItem(hospitalizacion.getEstado()); // Asegúrate de que los valores coincidan con los de tu ComboBox
		        textAreaNotas.setText(hospitalizacion.getNotas());
		        // Asegúrate de manejar adecuadamente el comboBox de veterinarios si necesitas mostrar el veterinario que atendió la hospitalización.
		    } else {
		        JOptionPane.showMessageDialog(this, "No se pudo cargar la información de la hospitalización.", "Error", JOptionPane.ERROR_MESSAGE);
		    }
		}
	
	 private void actualizarHospitalizacion() {
		    // Crear una nueva instancia de Hospitalizacion.
		    Hospitalizacion hospitalizacion = new Hospitalizacion();
		    
		    // Establecer el ID de la hospitalización a actualizar.
		    hospitalizacion.setId(this.idHospitalizacion);
		    
		    // Convertir la fecha de ingreso de Date a LocalDateTime directamente.
		    LocalDateTime fechaIngresoLocalDateTime = dateChooserIngreso.getDate().toInstant()
		        .atZone(ZoneId.systemDefault()).toLocalDateTime();
		    hospitalizacion.setFechaIngreso(fechaIngresoLocalDateTime);
		    
		    // Si la fecha de salida no es null, convertirla a LocalDateTime directamente.
		    if (dateChooserSalida.getDate() != null) {
		        LocalDateTime fechaSalidaLocalDateTime = dateChooserSalida.getDate().toInstant()
		            .atZone(ZoneId.systemDefault()).toLocalDateTime();
		        hospitalizacion.setFechaSalida(fechaSalidaLocalDateTime);
		    } else {
		        hospitalizacion.setFechaSalida(null); // Manejar correctamente el valor null.
		    }
		    
		    // Establecer el resto de los datos recogidos del formulario.
		    hospitalizacion.setMotivo(textAreaMotivo.getText());
		    hospitalizacion.setTratamiento(textAreaTratamiento.getText());
		    hospitalizacion.setEstado(comboBoxEstado.getSelectedItem().toString());
		    hospitalizacion.setNotas(textAreaNotas.getText());
		    
		    // Llamar al método de actualización en el DAO y manejar la respuesta.
		    boolean exito = hospitalizacionDao.actualizarHospitalizacion(hospitalizacion);
		    if (exito) {
		        JOptionPane.showMessageDialog(this, "Hospitalización actualizada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
		        this.dispose(); // Cierra el diálogo/modal.
		    } else {
		        JOptionPane.showMessageDialog(this, "No se pudo actualizar la hospitalización.", "Error", JOptionPane.ERROR_MESSAGE);
		    }
		}
	 private void abrirDialogoSeleccionFarmaco() {
		    try {
		        DialogoSeleccionFarmaco dialogo = new DialogoSeleccionFarmaco((Frame) this.getOwner(), true, farmacoDAO);
		        dialogo.setVisible(true);
		        if (dialogo.isSeleccionado()) {
		            Farmaco farmacoSeleccionado = dialogo.getFarmacoSeleccionado();
		            String dosis = dialogo.getDosis();
		            String frecuencia = dialogo.getFrecuencia();
		            
		            textAreaTratamiento.append(farmacoSeleccionado.getNombre() + " - Dosis: " + dosis + ", Frecuencia: " + frecuencia + "\n");
		            
		            boolean stockActualizado = farmacoDAO.actualizarStockFarmaco(farmacoSeleccionado.getId(), 1); // Asumiendo que se usa una unidad
		            
		            /*/ Aquí necesitas obtener el idHospitalizacion y el idVeterinario. Estos son solo ejemplos.
		            int idHospitalizacion = obtenerIdHospitalizacionActual();
		            int idVeterinario = obtenerIdVeterinario();
		            LocalDateTime fechaHoraUso = LocalDateTime.now(); // O la fecha y hora que sea relevante
		            
		            boolean usoRegistrado = farmacoDAO.registrarUsoFarmaco(farmacoSeleccionado.getId(), idHospitalizacion, idVeterinario, 1, fechaHoraUso);
		            
		            if (!stockActualizado || !usoRegistrado) {
		                JOptionPane.showMessageDialog(this, "Error al actualizar el stock del fármaco o registrar su uso.", "Error", JOptionPane.ERROR_MESSAGE);
		            }*/
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		        JOptionPane.showMessageDialog(this, "Ocurrió un error al abrir el diálogo de selección de fármaco: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		    }
		}
	 
	 
	 private void llenarComboBoxVeterinarios() {
		    VeterinarioDAO veterinarioDAO = new VeterinarioDAO();
		    List<Veterinario> veterinarios = veterinarioDAO.obtenerTodosLosVeterinarios();
		    comboBoxVeterinarios.removeAllItems(); // Limpia el comboBox antes de llenarlo
		    for (Veterinario veterinario : veterinarios) {
		        comboBoxVeterinarios.addItem(veterinario);
		    }
		}



		    

	 
	 
	 private void guardarHospitalizacion() {
		    try {
		        LocalDateTime fechaIngreso = LocalDateTime.now(); // Asumiendo uso de LocalDateTime
		        LocalDateTime fechaSalida = (dateChooserSalida.getDate() != null) ? dateChooserSalida.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : null;
		        String motivo = textAreaMotivo.getText();
		        String tratamiento = textAreaTratamiento.getText();
		        String estado = (String) comboBoxEstado.getSelectedItem(); // Asegúrate de que comboBoxEstado se está utilizando correctamente
		        String notas = textAreaNotas.getText();

		        Veterinario veterinarioSeleccionado = (Veterinario) comboBoxVeterinarios.getSelectedItem();
		        int idVeterinarioSeleccionado = veterinarioSeleccionado != null ? veterinarioSeleccionado.getId() : -1; // Asegúrate de que esto se maneja correctamente

		        Hospitalizacion hospitalizacion = new Hospitalizacion(
		            0, // Suponiendo que este es un campo de 'id' autoincrementable o no necesario en el constructor
		            idMascota, // Usando el idMascota pasado al diálogo
		            fechaIngreso,
		            fechaSalida,
		            motivo,
		            tratamiento,
		            estado,
		            notas
		        );

		        boolean resultadoInsertar = hospitalizacionDAO.insertarHospitalizacion(hospitalizacion, idVeterinarioSeleccionado);
		        if (resultadoInsertar) {
		            JOptionPane.showMessageDialog(this, "Hospitalización guardada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
		            dispose();
		        } else {
		            JOptionPane.showMessageDialog(this, "No se pudo guardar la hospitalización.", "Error", JOptionPane.ERROR_MESSAGE);
		        }
		    } catch (Exception e) {
		        JOptionPane.showMessageDialog(this, "Error al guardar la hospitalización: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		        e.printStackTrace();
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




	 
	 


}