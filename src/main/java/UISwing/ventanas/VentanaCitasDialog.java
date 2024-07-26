package UISwing.ventanas;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import com.toedter.calendar.JCalendar;
import DB.CitaDAO;
import DB.ClienteDAO;
import DB.MascotaDAO;
import DB.VeterinarioDAO;
import model.Cita;
import model.Cliente;
import model.Mascota;
import model.Mascota.MascotaContenedor;
import model.Veterinario;
import UISwing.recursos.RoundedPanel;
import java.util.List;


public class VentanaCitasDialog extends JDialog {

    private ClienteDAO clienteDAO;
    private MascotaDAO mascotaDAO;
    
    private JSpinner timeSpinner;
    private JSpinner dateSpinner;
    private JButton calendarButton;
    private JTextField textFieldTituloVisita;
    private JTextPane textPaneNotas;
    private JComboBox<Veterinario> comboBoxVeterinarios;
    private JComboBox<Cliente> comboBoxClientes;
    private JComboBox<Mascota.MascotaContenedor> comboBoxMascotas;
    private JComboBox<String> comboBoxTipo;
    private boolean cargaInicialCompleta = false;
    private RoundedPanel roundedPanel;

    public VentanaCitasDialog(Frame owner, boolean modal) {
        super(owner, modal);
        setTitle("Programar Cita");
        setUndecorated(true); // Esto elimina la decoración de la ventana
        setSize(new Dimension(888, 399));
        
        setLocationRelativeTo(null);
        
        
        
        roundedPanel = new RoundedPanel(30, Color.decode("#999CE3"));
        roundedPanel.setLayout(null);
        roundedPanel.setBounds(0, 0, 888, 399); // Ajustar al tamaño del JDialog
        roundedPanel.setOpaque(false); // Hacer el fondo transparente
        setBackground(new java.awt.Color(0, 0, 0, 0));
        clienteDAO = new ClienteDAO();
        mascotaDAO = new MascotaDAO();
        
        inicializarComponentesUI();
        cargarVeterinarios();
        cargarClientesInicialmente();
        configurarSeleccionFechaHora();
        
        // Agrega el RoundedPanel al JDialog
        getContentPane().add(roundedPanel);
	}
	
	private void inicializarComponentesUI() {
    	
		JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setForeground(new Color(255, 255, 255));
        lblTipo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTipo.setBounds(56, 37, 80, 34);
        roundedPanel.add(lblTipo);

        comboBoxTipo = new JComboBox<>(new String[]{"Visita", "Consulta", "Urgencia"}); // Ya no se redeclara aquí
        comboBoxTipo.setFont(new Font("Segoe UI", Font.BOLD, 11));
        comboBoxTipo.setBounds(54, 70, 120, 25);
        roundedPanel.add(comboBoxTipo);
        
        JLabel lblVeterinario = new JLabel("Veterinario:");
        lblVeterinario.setForeground(Color.WHITE);
        lblVeterinario.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblVeterinario.setBounds(211, 42, 80, 25);
        roundedPanel.add(lblVeterinario);

        comboBoxVeterinarios = new JComboBox<>();
        comboBoxVeterinarios.setFont(new Font("Segoe UI", Font.BOLD, 11));
        comboBoxVeterinarios.setBounds(211, 70, 179, 25);
        roundedPanel.add(comboBoxVeterinarios);
        
        JLabel lblCliente = new JLabel("Cliente:");
        lblCliente.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblCliente.setForeground(new Color(255, 255, 255));
        lblCliente.setBounds(434, 37, 80, 34);
        roundedPanel.add(lblCliente);
        
     
        JLabel lblMascota = new JLabel("Mascota:");
        lblMascota.setForeground(new Color(255, 255, 255));
        lblMascota.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblMascota.setBounds(657, 37, 80, 34);
        roundedPanel.add(lblMascota);
        
     // ComboBox CLientes
        comboBoxClientes = new JComboBox<Cliente>();
        comboBoxClientes.setFont(new Font("Segoe UI", Font.BOLD, 11));
        comboBoxClientes.setEditable(true);
        comboBoxClientes.setBounds(434, 70, 179, 25);
        JTextField textEditorClientes = (JTextField) comboBoxClientes.getEditor().getEditorComponent();
        textEditorClientes.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = textEditorClientes.getText();
                if (text.trim().length() >= 4) {
                   
                    List<Cliente> filtrado = clienteDAO.buscarClientesPorApellido(text); 
                    comboBoxClientes.removeAllItems();
                    for (Cliente cliente : filtrado) {
                        comboBoxClientes.addItem(cliente);
                    }
                    if (!filtrado.isEmpty()) {
                        comboBoxClientes.showPopup();
                    }
                } else if (text.trim().isEmpty()) {
                    comboBoxClientes.hidePopup();
                    comboBoxClientes.removeAllItems();
                }
            }
        });
     // Agregar ItemListener para cargar mascotas del cliente seleccionado
        comboBoxClientes.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    Cliente clienteSeleccionado = (Cliente) event.getItem();
                    actualizarMascotasPorCliente(clienteSeleccionado.getId());
                }
            }
        });

        comboBoxClientes.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Cliente) {
                    Cliente cliente = (Cliente) value;
                    setText(cliente.getApellidos() + ", " + cliente.getNombre()); // Formato apellidos, nombre
                }
                return this;
            }
        });
        roundedPanel.add(comboBoxClientes);
   
        // Combobox de Mascotas
        comboBoxMascotas = new JComboBox<>();
        comboBoxMascotas.setFont(new Font("Segoe UI", Font.BOLD, 11));
        comboBoxMascotas.setEditable(true);
        comboBoxMascotas.setBounds(654, 71, 179, 25);
        comboBoxMascotas.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof MascotaContenedor) {
                    MascotaContenedor contenedor = (MascotaContenedor) value;
                    setText(contenedor.getMascota().getNombre()); // Ahora correctamente obtiene el nombre de la mascota a través del contenedor
                }
                return this;
            }
        });


        roundedPanel.add(comboBoxMascotas);
        

        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setForeground(new Color(255, 255, 255));
        lblFecha.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblFecha.setBounds(434, 106, 44, 34);
        roundedPanel.add(lblFecha);

       

        JLabel lblHora = new JLabel("Hora:");
        lblHora.setForeground(new Color(255, 255, 255));
        lblHora.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblHora.setBounds(657, 104, 80, 34);
        roundedPanel.add(lblHora);
        
        JLabel lblTituloVisita = new JLabel("Título de la visita:");
        lblTituloVisita.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTituloVisita.setForeground(new Color(255, 255, 255));
        lblTituloVisita.setBounds(56, 106, 150, 30);
        roundedPanel.add(lblTituloVisita);

        textFieldTituloVisita = new JTextField();
        textFieldTituloVisita.setFont(new Font("Segoe UI", Font.BOLD, 11));
        textFieldTituloVisita.setBounds(54, 137, 336, 25);
        roundedPanel.add(textFieldTituloVisita);
        
        JLabel lblNotas = new JLabel("Nota");
        lblNotas.setForeground(new Color(255, 255, 255));
        lblNotas.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblNotas.setBounds(56, 181, 46, 34);
        roundedPanel.add(lblNotas);
        
        textPaneNotas = new JTextPane();
        textPaneNotas.setFont(new Font("Segoe UI", Font.BOLD, 11));
        textPaneNotas.setBounds(54, 218, 779, 73);
        roundedPanel.add(textPaneNotas);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnCancelar.setBounds(532, 328, 120, 30);
        btnCancelar.setBackground(Color.WHITE);
        btnCancelar.setForeground(Color.decode("#0057FF")); // Letras en color azul
        btnCancelar.setFocusPainted(false); // Evita que se pinte el foco alrededor del botón
        btnCancelar.setBorderPainted(false); // Evita que se pinte el borde predeterminado
        btnCancelar.setContentAreaFilled(false); // Evita que se pinte el área de contenido
        btnCancelar.setOpaque(true);
        btnCancelar.setRolloverEnabled(true);
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCancelar.setBackground(Color.decode("#003366")); // Color azul oscuro para rollover
                btnCancelar.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCancelar.setBackground(Color.WHITE); // Color blanco cuando el ratón sale
                btnCancelar.setForeground(Color.decode("#0057FF"));
            }
        });
        btnCancelar.addActionListener(e -> {
        	dispose();
        });
        
        roundedPanel.add(btnCancelar);
        
        JButton btnGuardarCita = new JButton("Guardar Cita");
        btnGuardarCita.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnGuardarCita.setBounds(713, 328, 120, 30);
        btnGuardarCita.setBackground(Color.WHITE);
        btnGuardarCita.setForeground(Color.decode("#0057FF")); // Letras en color azul
        btnGuardarCita.setFocusPainted(false); // Evita que se pinte el foco alrededor del botón
        btnGuardarCita.setBorderPainted(false); // Evita que se pinte el borde predeterminado
        btnGuardarCita.setContentAreaFilled(false); // Evita que se pinte el área de contenido
        btnGuardarCita.setOpaque(true);
        btnGuardarCita.setRolloverEnabled(true);
        btnGuardarCita.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarCita.setBackground(Color.decode("#003366")); // Color azul oscuro para rollover
                btnGuardarCita.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarCita.setBackground(Color.WHITE); // Color blanco cuando el ratón sale
                btnGuardarCita.setForeground(Color.decode("#0057FF"));
            }
        });
        btnGuardarCita.addActionListener(e -> guardarCita());
        roundedPanel.add(btnGuardarCita);
        
        
 
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
        centerPanel.setBackground(new Color(255, 255, 255, 70)); // Color de fondo con opacidad
        centerPanel.setOpaque(false); // Hace que el panel no pinte todos sus píxeles, lo que permite que se vea el fondo.
        centerPanel.setBounds(24, 24, 841, 352);
        roundedPanel.add(centerPanel);
 
    }
	
	private void cargarVeterinarios() {
	    VeterinarioDAO veterinarioDAO = new VeterinarioDAO();
	    List<Veterinario> veterinarios = veterinarioDAO.obtenerTodosLosVeterinarios();
	    veterinarios.forEach(comboBoxVeterinarios::addItem);

	    comboBoxVeterinarios.setRenderer(new DefaultListCellRenderer() {
	        @Override
	        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	            if (value instanceof Veterinario) {
	                Veterinario veterinario = (Veterinario) value;
	                setText(veterinario.getNombre() + " " + veterinario.getApellidos());
	            }
	            return this;
	        }
	    });
	}
	
	
	private void configurarSeleccionFechaHora() {
	    dateSpinner = new JSpinner(new SpinnerDateModel());
	    JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
	    dateSpinner.setEditor(dateEditor);
	    dateSpinner.setBounds(434, 137, 155, 25);
	    roundedPanel.add(dateSpinner);

	    calendarButton = new JButton(new ImageIcon(getClass().getResource("/imagenes/logoBotonCalendario.png")));
	    calendarButton.setBounds(588, 137, 25, 25);
	    calendarButton.addActionListener(e -> mostrarCalendario());
	    roundedPanel.add(calendarButton);

	    timeSpinner = new JSpinner(new SpinnerDateModel());
	    JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
	    timeSpinner.setEditor(timeEditor);
	    timeSpinner.setBounds(657, 138, 179, 25);
	    roundedPanel.add(timeSpinner);
	}

	private void mostrarCalendario() {
	    JDialog dialog = new JDialog();
	    dialog.setModal(true); // Hacer el diálogo modal para mantener el foco
	    dialog.setTitle("Seleccionar Fecha");
	    dialog.setSize(300, 300);
	    dialog.setLocationRelativeTo(null); // Centrar respecto a la pantalla o usar dialog.setLocationRelativeTo(this) para centrar respecto a la ventana principal
	    JCalendar calendar = new JCalendar();
	    dialog.getContentPane().add(calendar, BorderLayout.CENTER);
	    
	    JButton okButton = new JButton("OK");
	    okButton.addActionListener(e -> {
	        dateSpinner.setValue(calendar.getDate());
	        dialog.dispose(); // Cierra el diálogo una vez seleccionada la fecha
	    });
	    dialog.getContentPane().add(okButton, BorderLayout.SOUTH);

	    dialog.setVisible(true);
	}
	


    
   
  
	private void actualizarMascotasPorCliente(int clienteId) {
	    new SwingWorker<List<Mascota>, Void>() {
	        @Override
	        protected List<Mascota> doInBackground() throws Exception {
	            return mascotaDAO.obtenerMascotasPorClienteId(clienteId);
	        }

	        @Override
	        protected void done() {
	            try {
	                List<Mascota> mascotas = get();
	                comboBoxMascotas.removeAllItems();
	                for (Mascota mascota : mascotas) {
	                    comboBoxMascotas.addItem(new Mascota.MascotaContenedor(mascota)); // Uso correcto con la clase estática
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    }.execute();
	}




	private void cargarClientesInicialmente() {
	    new SwingWorker<List<Cliente>, Void>() {
	        @Override
	        protected List<Cliente> doInBackground() throws Exception {
	            return clienteDAO.obtenerTodosLosClientes();
	        }

	        @Override
	        protected void done() {
	            try {
	                List<Cliente> clientes = get();
	                comboBoxClientes.removeAllItems(); // Asegúrate de limpiar antes de añadir
	                for (Cliente cliente : clientes) {
	                	comboBoxClientes.setSelectedIndex(-1);
	                    comboBoxClientes.addItem(cliente);
	                }
	                cargaInicialCompleta = true; // Indica que la carga inicial ha terminado
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    }.execute();
	}

    
    

	private void actualizarListaClientes(String texto) {
	    // Verifica si la carga inicial aún no se ha completado para evitar ejecución innecesaria
	    if (!cargaInicialCompleta) return;

	    new SwingWorker<List<Cliente>, Void>() {
	        @Override
	        protected List<Cliente> doInBackground() throws Exception {
	            // Simula una búsqueda en base al texto ingresado por el usuario
	            return clienteDAO.buscarClientesPorNombre(texto);
	        }

	        @Override
	        protected void done() {
	            try {
	                // Obtiene el resultado de la búsqueda
	                List<Cliente> clientes = get();
	                // Limpia el JComboBox antes de añadir nuevos elementos
	                comboBoxClientes.removeAllItems();
	                for (Cliente cliente : clientes) {
	                    // Añade cada cliente encontrado al JComboBox
	                    comboBoxClientes.addItem(cliente);
	                }
	                // Si hay clientes encontrados, muestra el popup del JComboBox
	                if (!clientes.isEmpty()) {
	                    comboBoxClientes.showPopup();
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    }.execute();
	}

	private void guardarCita() {
	    try {
	        String titulo = textFieldTituloVisita.getText();
	        java.util.Date fechaUtil = (java.util.Date) dateSpinner.getValue();
	        java.sql.Date fechaSql = new java.sql.Date(fechaUtil.getTime());
	        Calendar cal = Calendar.getInstance();
	        cal.setTime((java.util.Date) timeSpinner.getValue());
	        LocalTime hora = LocalTime.of(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
	        String notas = textPaneNotas.getText();

	        Cliente clienteSeleccionado = (Cliente) comboBoxClientes.getSelectedItem();
	        Mascota.MascotaContenedor contenedorMascota = (Mascota.MascotaContenedor) comboBoxMascotas.getSelectedItem();
	        Mascota mascotaSeleccionada = contenedorMascota.getMascota();
	        
			// Aquí obtenemos el tipo de la cita desde el comboBoxTipo
	        String tipoCita = comboBoxTipo.getSelectedItem() == null ? "" : comboBoxTipo.getSelectedItem().toString();

	        if (clienteSeleccionado == null || mascotaSeleccionada == null) {
	            JOptionPane.showMessageDialog(this, "Seleccione un cliente y una mascota.", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

	        // Asegúrate de añadir un constructor en tu clase Cita que incluya el tipo, o usa el setter para tipo
	        Cita cita = new Cita(0, titulo, fechaSql.toLocalDate(), hora, notas, clienteSeleccionado.getId(), mascotaSeleccionada.getId());
	        cita.setTipo(tipoCita); // Asegúrate de que este setter exista y esté implementado correctamente

	        new CitaDAO().insertarCita(cita);

	        JOptionPane.showMessageDialog(this, "Cita guardada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
	        dispose();
	    } catch (Exception ex) {
	        JOptionPane.showMessageDialog(this, "Error al guardar la cita: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	public void setFecha(java.util.Date fecha) {
        // Establece el valor del spinner de fecha. Asegúrate de que dateSpinner es accesible aquí.
        SpinnerDateModel model = (SpinnerDateModel) dateSpinner.getModel();
        model.setValue(fecha);
    }
	
	public void setHora(java.util.Date hora) {
        // Establece el valor del spinner de hora. Asegúrate de que timeSpinner es accesible aquí.
        SpinnerDateModel model = (SpinnerDateModel) timeSpinner.getModel();
        model.setValue(hora);
    }


    
   
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaCitasDialog dialog = new VentanaCitasDialog(null, true);
            dialog.setVisible(true);
        });
    }

}