package UISwing.ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import DB.ClienteDAO;
import model.Cliente;
import DB.MascotaDAO;
import model.Mascota;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelClienteMascota extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField txtBuscarClientemascota;
    private JTextField txtBuscarCliente;
    private JTable tablaClientes;  // JTable para clientes
    private JTable tablaMascotas;  // JTable para mascotas
    private DefaultTableModel modeloTablaClientes;
    private ClienteDAO clienteDao;
    private DefaultTableModel modeloTablaMascotas; // Nuevo modelo para mascotas
    private MascotaDAO mascotaDAO;
    private JScrollPane scrollPaneClientes; // Declaración de la variable scrollPaneClientes como una variable de instancia
    /**
     * Create the panel.
     */
    public PanelClienteMascota() {
        setLayout(null);

        clienteDao = new ClienteDAO(); // clase para la gestión de la base de datos
        mascotaDAO = new MascotaDAO(); 
        
        JPanel panel = new JPanel();
        panel.setBounds(0, -11, 1112, 664);
        add(panel);
        panel.setLayout(null);

        JButton btnExportar = new JButton("Exportar");
        btnExportar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnExportar.setBounds(875, 42, 89, 23);
        panel.add(btnExportar);

        JButton btnImportar = new JButton("Importar");
        btnImportar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnImportar.setBounds(764, 42, 89, 23);
        panel.add(btnImportar);

        JButton btnAnadir = new JButton("Añadir");
        btnAnadir.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnAnadir.setBounds(186, 42, 89, 23);
        panel.add(btnAnadir);

        // Añadir ActionListener al botón Añadir
        btnAnadir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame newFrame = new JFrame("Registro de Cliente"); // Crea un nuevo JFrame
                newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Configura la acción de cierre
                newFrame.setSize(800, 600); // Establece el tamaño del JFrame
                PanelRegistroCliente panelRegistro = new PanelRegistroCliente(); // Crea el panel de registro
                newFrame.getContentPane().add(panelRegistro); // Añade el panel al JFrame
                newFrame.setLocationRelativeTo(null); // Centra el JFrame en la pantalla
                newFrame.setVisible(true); // Hace visible el JFrame
            }
        });

        JButton btnMostrarPor = new JButton("Mostrar por");
        btnMostrarPor.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnMostrarPor.setBounds(302, 42, 89, 23);
        panel.add(btnMostrarPor);

        txtBuscarClientemascota = new JTextField("Buscar mascota"); // Crea un JTextField con texto predeterminado
        txtBuscarClientemascota.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Establece la fuente del texto
        txtBuscarClientemascota.setBounds(569, 43, 161, 20); // Establece la posición y el tamaño del campo de texto

        // Agrega un MouseListener para detectar clics en el campo de texto
        txtBuscarClientemascota.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                txtBuscarClientemascota.setText(""); // Borra el texto cuando se hace clic en el campo de texto
            }
        });

        // Agrega un FocusListener para detectar cuando el campo de texto pierde el foco
        txtBuscarClientemascota.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (txtBuscarClientemascota.getText().isEmpty()) { // Comprueba si el campo de texto está vacío
                    txtBuscarClientemascota.setText("Buscar mascota"); // Restablece el texto predeterminado si está vacío
                }
            }
        });
        txtBuscarClientemascota.addActionListener(e -> {
            String searchText = txtBuscarClientemascota.getText().trim().toLowerCase(); // Obtenemos el texto y lo convertimos a minúsculas para una búsqueda insensible a mayúsculas

            // Recorremos las filas de la tabla para buscar coincidencias
            boolean found = false;
            for (int row = 0; row < tablaMascotas.getRowCount(); row++) {
                String nombreMascota = tablaMascotas.getValueAt(row, 0).toString().toLowerCase();
                if (nombreMascota.equals(searchText)) {
                    // Si encuentra una coincidencia, seleccionar la fila
                    tablaMascotas.setRowSelectionInterval(row, row);
                    
                    // Hacemos que la fila seleccionada sea visible
                    Rectangle rect = tablaMascotas.getCellRect(row, 0, true);
                    tablaMascotas.scrollRectToVisible(rect);
                    found = true;
                    break;
                }
            }
            
            if (!found) {
                JOptionPane.showMessageDialog(this, "Mascota no encontrada", "Búsqueda", JOptionPane.INFORMATION_MESSAGE);
                tablaMascotas.clearSelection();
            }
        });
        panel.add(txtBuscarClientemascota); // Agrega el campo de texto al panel

        txtBuscarCliente = new JTextField("Buscar cliente"); 			// Creamos un JTextField con texto predeterminado
        txtBuscarCliente.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Establecemos la fuente del texto
        txtBuscarCliente.setBounds(10, 43, 166, 20);					// Establecemos la posición y el tamaño del campo de texto
     // Agregamos un MouseListener para detectar clics en el campo de texto
        txtBuscarCliente.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                txtBuscarCliente.setText(""); // Borramos el texto cuando se hace clic en el campo de texto
            }
        });
     // Agregamos un FocusListener para detectar cuando el campo de texto pierde el foco
        txtBuscarCliente.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (txtBuscarCliente.getText().isEmpty()) { 	 // Comprueba si el campo de texto está vacío
                    txtBuscarCliente.setText("Buscar cliente");	 // Restablece el texto predeterminado si está vacío
                }
            }
        });
        txtBuscarCliente.addActionListener(e -> {
            String searchText = txtBuscarCliente.getText().trim().toLowerCase(); // Obtiene el texto ingresado y lo convierte a minúsculas
            boolean found = false;

            // Realiza la búsqueda en la tabla de clientes por nombre, apellido o DNI
            for (int row = 0; row < tablaClientes.getRowCount(); row++) {
                String nombre = tablaClientes.getValueAt(row, 0).toString().toLowerCase(); // Nombre del cliente
                String apellidos = tablaClientes.getValueAt(row, 1).toString().toLowerCase(); // Apellidos del cliente
                String dni = tablaClientes.getValueAt(row, 2).toString().toLowerCase(); // DNI del cliente

                // Concatena nombre y apellido para la búsqueda
                String fullName = nombre + " " + apellidos;

                if (fullName.contains(searchText) || dni.equals(searchText)) {
                    // Selecciona la fila correspondiente si se encuentra una coincidencia
                    tablaClientes.setRowSelectionInterval(row, row);
                    // Hace que la fila seleccionada sea visible en la tabla
                    Rectangle rect = tablaClientes.getCellRect(row, 0, true);
                    tablaClientes.scrollRectToVisible(rect);
                    found = true;
                    break; // Sale del bucle después de encontrar la primera coincidencia
                }
            }
            if (!found) {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado", "Búsqueda", JOptionPane.INFORMATION_MESSAGE);
                tablaClientes.clearSelection();
            }
        });

        panel.add(txtBuscarCliente);  // Agrega el campo de texto al pane

        inicializarComponentesClientes(panel); // Agregar inicialización de componentes de mascotas
        inicializarComponentesMascotas(panel); 
        /* Asi tambien se puede
        JScrollPane scrollPaneClientes = new JScrollPane();
        scrollPaneClientes.setBounds(10, 105, 549, 537);
        panel.add(scrollPaneClientes); */
        scrollPaneClientes = new JScrollPane();
        scrollPaneClientes.setBounds(10, 105, 549, 537); // Ajusta las dimensiones según necesites
        panel.add(scrollPaneClientes); // Asegúrate de agregarlo al panel adecuado

        JScrollPane scrollPaneMascotas = new JScrollPane(); // Nuevo JScrollPane para mascotas
        scrollPaneMascotas.setBounds(569, 105, 518, 537); // Ajusta las dimensiones según necesites
        panel.add(scrollPaneMascotas);
    }

    private void inicializarComponentesClientes(JPanel panel) {
    	 // Configuramos el modelo de la tabla sin mostrar el ID
        modeloTablaClientes = new DefaultTableModel(new Object[]{"Nombre", "Apellidos", "DNI"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Hacer que la tabla no sea editable
            }
        };

        tablaClientes = new JTable(modeloTablaClientes);
        tablaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {  // Doble clic
                    int filaSeleccionada = tablaClientes.getSelectedRow();
                    if (filaSeleccionada != -1) {
                        String dni = (String) modeloTablaClientes.getValueAt(filaSeleccionada, 2); 
                        abrirPanelDetalleClientePorDni(dni);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tablaClientes);
        scrollPane.setBounds(10, 105, 549, 537); // Ajustamos las dimensiones según necesitamos
        panel.add(scrollPane);

        cargarDatosClientes(); // Cargamos los datos de los clientes después de inicializar la tabla
    }
    
    private void inicializarComponentesMascotas(JPanel panel) {
    	 // Configuramos el modelo de la tabla para mascotas
        modeloTablaMascotas = new DefaultTableModel(new Object[]{"Nombre", "Especie", "Microchip"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Hacer que la tabla no sea editable
            }
        };

        tablaMascotas = new JTable(modeloTablaMascotas);
        tablaMascotas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaMascotas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {  // Doble clic
                    int filaSeleccionada = tablaMascotas.getSelectedRow();
                    if (filaSeleccionada != -1) {
                        String microchip = (String) modeloTablaMascotas.getValueAt(filaSeleccionada, 2);  
                        abrirPanelDetalleMascotaPorMicrochip(microchip);
                    }
                }
            }
        });

        JScrollPane scrollPaneMascotas = new JScrollPane(tablaMascotas);
        scrollPaneMascotas.setBounds(569, 105, 518, 537); // Ajustamos las dimensiones según necesitamos
        panel.add(scrollPaneMascotas);
        panel.add(scrollPaneMascotas);

        cargarDatosMascotas(); // Cargamos los datos de las mascotas en la tabla
    }

    private void cargarDatosClientes() {
        List<Cliente> listaClientes = clienteDao.obtenerTodosLosClientes(); // Obtiene los datos desde la base de datos a través del DAO
        DefaultTableModel modelo = (DefaultTableModel) tablaClientes.getModel();
        modelo.setRowCount(0); // Limpiamos la tabla antes de agregar nuevos datos

        for (Cliente cliente : listaClientes) {
            modelo.addRow(new Object[]{
                cliente.getNombre(),
                cliente.getApellidos(),
                cliente.getDni()
            });
        }
    }
    
    private void cargarDatosMascotas() {
        try {
            List<Mascota> listaMascotas = mascotaDAO.obtenerMascotasOrdenadasPorNombreEspecieMicrochip();

            DefaultTableModel modelo = (DefaultTableModel) tablaMascotas.getModel();
            modelo.setRowCount(0); // Limpiar la tabla antes de agregar nuevos datos

            for (Mascota mascota : listaMascotas) {
                modelo.addRow(new Object[]{
                        mascota.getNombre(),
                        mascota.getEspecie(),
                        mascota.getMicrochip()
                });
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }// Maneja la excepción de SQL
        // Aquí podrías mostrar un mensaje de error al usuario si la carga de datos falla.
    }

    private void abrirPanelDetalleClientePorDni(String dni) {
        PanelCliente panelCliente = new PanelCliente(dni);
        JFrame frame = new JFrame("Detalles del Cliente");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(panelCliente);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    // Método para abrir el panel de detalles de la mascota por microchip
    private void abrirPanelDetalleMascotaPorMicrochip(String microchip) {
        Mascota mascota = mascotaDAO.obtenerMascotaPorMicrochip(microchip); // Agregamos este método al DAO
        if (mascota != null) {
            JFrame frame = new JFrame("Detalles de la Mascota");
            PanelInfoMascota panelInfoMascota = new PanelInfoMascota(mascota.getId());
            frame.getContentPane().add(panelInfoMascota);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } 
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Panel Cliente Mascota");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            PanelClienteMascota panel = new PanelClienteMascota();
            frame.getContentPane().add(panel);
            frame.setSize(800, 600); // Ajustamos el tamaño según necesitamos
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}