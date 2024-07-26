package UISwing.ventanas;

import javax.swing.*;

import com.toedter.calendar.JCalendar;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import DB.ClienteDAO;
import DB.MascotaDAO;
import model.Cliente;
import model.Mascota;

import java.util.Calendar;
import java.util.Date;

public class VentanaCitas extends JPanel {
    private JTextField textFieldBuscarCliente;
    private JPopupMenu popupMenuSugerenciasCliente;
    private ClienteDAO clienteDAO;
    
    private JTextField textFieldBuscarMascota;
    private JPopupMenu popupMenuSugerenciasMascota;
    private MascotaDAO mascotaDAO;

    private JSpinner timeSpinner;
    private JSpinner dateSpinner;
    private JButton calendarButton;
    private JTextField textFieldTituloVisita;
    
    
    public VentanaCitas() {
        setPreferredSize(new Dimension(888, 399)); // Ajusta según necesidad
        setLayout(null);
        setBackground(Color.decode("#7E88E2"));
        
        clienteDAO = new ClienteDAO();
        mascotaDAO = new MascotaDAO();
        
        inicializarComponentesUI();
        configurarSeleccionFechaHora();
    }

    private void inicializarComponentesUI() {
        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setForeground(new Color(255, 255, 255));
        lblTipo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTipo.setBounds(62, 50, 80, 25);
        add(lblTipo);
        
        JComboBox<String> comboBoxTipo = new JComboBox<>(new String[]{"Visita", "Consulta", "Urgencia"});
        comboBoxTipo.setFont(new Font("Segoe UI", Font.BOLD, 11));
        comboBoxTipo.setBounds(60, 74, 120, 25);
        add(comboBoxTipo);
        
        JLabel lblDoctor = new JLabel("Doctor:");
        lblDoctor.setForeground(new Color(255, 255, 255));
        lblDoctor.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblDoctor.setBounds(236, 50, 80, 25);
        add(lblDoctor);
        
        JComboBox<String> comboBoxDoctor = new JComboBox<>(new String[]{"Doctor 1", "Doctor 2", "Doctor 3"});
        comboBoxDoctor.setFont(new Font("Segoe UI", Font.BOLD, 11));
        comboBoxDoctor.setBounds(234, 74, 120, 25);
        add(comboBoxDoctor);
        
        JLabel lblCliente = new JLabel("Cliente:");
        lblCliente.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblCliente.setForeground(new Color(255, 255, 255));
        lblCliente.setBounds(420, 50, 80, 25);
        add(lblCliente);
        
        textFieldBuscarCliente = new JTextField();
        textFieldBuscarCliente.setFont(new Font("Segoe UI", Font.BOLD, 11));
        textFieldBuscarCliente.setBounds(420, 74, 179, 25);
        add(textFieldBuscarCliente);
        
        JLabel lblMascota = new JLabel("Mascota:");
        lblMascota.setForeground(new Color(255, 255, 255));
        lblMascota.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblMascota.setBounds(663, 50, 80, 25);
        add(lblMascota);
        
        textFieldBuscarMascota = new JTextField();
        textFieldBuscarMascota.setFont(new Font("Segoe UI", Font.BOLD, 11));
        textFieldBuscarMascota.setBounds(660, 74, 120, 25);
        add(textFieldBuscarMascota);
        
        popupMenuSugerenciasCliente = new JPopupMenu();
        textFieldBuscarCliente.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                buscarClientes(textFieldBuscarCliente.getText());
            }
        });
        
        popupMenuSugerenciasMascota = new JPopupMenu();
        textFieldBuscarMascota.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                buscarMascotas(textFieldBuscarMascota.getText());
            }
        });
        
        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setForeground(new Color(255, 255, 255));
        lblFecha.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblFecha.setBounds(420, 132, 44, 25);
        add(lblFecha);

       

        JLabel lblHora = new JLabel("Hora:");
        lblHora.setForeground(new Color(255, 255, 255));
        lblHora.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblHora.setBounds(663, 132, 80, 25);
        add(lblHora);
        
        JLabel lblTituloVisita = new JLabel("Título de la visita:");
        lblTituloVisita.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTituloVisita.setForeground(new Color(255, 255, 255));
        lblTituloVisita.setBounds(58, 132, 150, 25);
        add(lblTituloVisita);

        textFieldTituloVisita = new JTextField();
        textFieldTituloVisita.setFont(new Font("Segoe UI", Font.BOLD, 11));
        textFieldTituloVisita.setBounds(60, 158, 294, 42);
        add(textFieldTituloVisita);

        
    }

    private void buscarClientes(String texto) {
        new SwingWorker<List<Cliente>, Void>() {
            @Override
            protected List<Cliente> doInBackground() throws Exception {
                return clienteDAO.buscarClientesPorNombre(texto);
            }

            @Override
            protected void done() {
                try {
                    List<Cliente> clientesEncontrados = get();
                    popupMenuSugerenciasCliente.removeAll();
                    for (Cliente cliente : clientesEncontrados) {
                        JMenuItem item = new JMenuItem(cliente.getNombre());
                        item.addActionListener(e -> {
                            textFieldBuscarCliente.setText(cliente.getNombre());
                            buscarMascotasPorCliente(cliente.getId());
                        });
                        popupMenuSugerenciasCliente.add(item);
                    }
                    if (!clientesEncontrados.isEmpty()) {
                        popupMenuSugerenciasCliente.show(textFieldBuscarCliente, 0, textFieldBuscarCliente.getHeight());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    private void buscarMascotas(String texto) {
        new SwingWorker<List<Mascota>, Void>() {
            @Override
            protected List<Mascota> doInBackground() throws Exception {
                return mascotaDAO.buscarMascotasPorNombre(texto);
            }

            @Override
            protected void done() {
                try {
                    List<Mascota> mascotasEncontradas = get();
                    popupMenuSugerenciasMascota.removeAll();
                    for (Mascota mascota : mascotasEncontradas) {
                        Cliente cliente = clienteDAO.buscarClientePorId(mascota.getIdCliente());
                        String displayText = String.format("%s (%s)", mascota.getNombre(), cliente.getNombre());
                        JMenuItem item = new JMenuItem(displayText);
                        item.addActionListener(e -> {
                            textFieldBuscarMascota.setText(mascota.getNombre());
                            // Opcionalmente, actualizar UI con información del cliente
                        });
                        popupMenuSugerenciasMascota.add(item);
                    }
                    if (!mascotasEncontradas.isEmpty()) {
                        popupMenuSugerenciasMascota.show(textFieldBuscarMascota, 0, textFieldBuscarMascota.getHeight());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    private void buscarMascotasPorCliente(int idCliente) {
        new SwingWorker<List<Mascota>, Void>() {
            @Override
            protected List<Mascota> doInBackground() throws Exception {
                // Llama al método de MascotaDAO que devuelve las mascotas de un cliente específico
                return mascotaDAO.buscarMascotasPorCliente(idCliente);
            }

            @Override
            protected void done() {
                try {
                    List<Mascota> mascotasEncontradas = get(); // Obtiene el resultado de doInBackground
                    popupMenuSugerenciasMascota.removeAll(); // Limpia el menú de sugerencias actual
                    
                    // Itera sobre las mascotas encontradas y añádelas al menú emergente
                    for (Mascota mascota : mascotasEncontradas) {
                        JMenuItem item = new JMenuItem(mascota.getNombre() + " - " + mascota.getEspecie());
                        item.addActionListener(e -> {
                            // Aquí puedes implementar la lógica que ocurra al seleccionar una mascota,
                            // por ejemplo, mostrar detalles de la mascota o seleccionarla para otra operación.
                            textFieldBuscarMascota.setText(mascota.getNombre());
                            popupMenuSugerenciasMascota.setVisible(false);
                        });
                        popupMenuSugerenciasMascota.add(item);
                    }
                    
                    // Muestra el menú emergente si hay mascotas encontradas
                    if (!mascotasEncontradas.isEmpty()) {
                        popupMenuSugerenciasMascota.show(textFieldBuscarMascota, 0, textFieldBuscarMascota.getHeight());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }
    private void buscarClientePorId(int idCliente) {
        new SwingWorker<Cliente, Void>() {
            @Override
            protected Cliente doInBackground() throws Exception {
                // Asume que clienteDAO es una instancia de ClienteDAO
                return clienteDAO.buscarClientePorId(idCliente);
            }

            @Override
            protected void done() {
                try {
                    Cliente cliente = get(); // Obtiene el resultado de doInBackground
                    if (cliente != null) {
                        // Aquí puedes implementar la lógica para mostrar los detalles del cliente
                        textFieldBuscarCliente.setText(cliente.getNombre());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }
    private void configurarSeleccionFechaHora() {

        dateSpinner = new JSpinner(new SpinnerDateModel());
        dateSpinner.setFont(new Font("Segoe UI", Font.BOLD, 11));
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
        dateSpinner.setEditor(dateEditor);
        dateSpinner.setBounds(420, 168, 153, 25);
        add(dateSpinner);

        
        calendarButton = new JButton();
        try {
            // Intenta cargar el ícono desde los recursos y establecerlo en el botón
            calendarButton.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoBotonCalendario.png")));
        } catch (Exception e) {
            e.printStackTrace();
            calendarButton.setText("Cal"); // Establece un texto de respaldo en caso de que el ícono no se cargue
        }
        calendarButton.setBounds(574, 168, 25, 25);
        add(calendarButton);

        calendarButton.addActionListener(e -> mostrarCalendario());

        // Configuración de JSpinner para la hora
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 12); // Valor predeterminado
        calendar.set(Calendar.MINUTE, 0);
        timeSpinner = new JSpinner(new SpinnerDateModel(calendar.getTime(), null, null, Calendar.HOUR_OF_DAY));
        timeSpinner.setFont(new Font("Segoe UI", Font.BOLD, 11));
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(timeEditor);
        timeSpinner.setBounds(660, 167, 120, 25);
        add(timeSpinner);
        
        JLabel lblNotas = new JLabel("Nota");
        lblNotas.setForeground(new Color(255, 255, 255));
        lblNotas.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblNotas.setBounds(62, 229, 46, 14);
        add(lblNotas);
        
        JTextPane textPaneNotas = new JTextPane();
        textPaneNotas.setFont(new Font("Segoe UI", Font.BOLD, 11));
        textPaneNotas.setBounds(62, 254, 537, 73);
        add(textPaneNotas);
        
        JButton btnGuardarCita = new JButton("Guardar Cita");
        btnGuardarCita.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnGuardarCita.setBounds(663, 285, 117, 42);
        add(btnGuardarCita);

       
    }
    private void mostrarCalendario() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Seleccionar Fecha");
        dialog.setSize(300, 300);
        dialog.getContentPane().setLayout(new BorderLayout());
        dialog.setLocationRelativeTo(this);

        JCalendar calendar = new JCalendar();
        dialog.getContentPane().add(calendar, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        dialog.getContentPane().add(okButton, BorderLayout.SOUTH);

        okButton.addActionListener(e -> {
            Date fecha = calendar.getDate();
            dateSpinner.setValue(fecha);
            dialog.dispose();
        });

        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Programar Cita");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new VentanaCitas());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
