package UISwing.ventanas;

import javax.swing.*;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import DB.ClienteDAO;
import model.Cliente;

public class ListaClientesPanel extends JPanel {
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private ClienteDAO clienteDao;

    public ListaClientesPanel() {
        super(new BorderLayout());
        clienteDao = new ClienteDAO();
        inicializarComponentes();
        cargarDatosClientes();
    }

    private void inicializarComponentes() {
        // Ajustado a "id" para mantener consistencia con la base de datos
        modeloTabla = new DefaultTableModel(new Object[]{"id", "Nombre", "Apellidos", "DNI"}, 0);
        tablaClientes = new JTable(modeloTabla) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int filaSeleccionada = tablaClientes.getSelectedRow();
                    if (filaSeleccionada != -1) {
                        int idCliente = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
                        abrirPanelCliente(idCliente);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tablaClientes);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void cargarDatosClientes() {
        List<Cliente> clientes = clienteDao.obtenerTodosLosClientes();
        modeloTabla.setRowCount(0);
        for (Cliente cliente : clientes) {
            modeloTabla.addRow(new Object[]{cliente.getId(), cliente.getNombre(), cliente.getApellidos(), cliente.getDni()});
        }
    }

    private void abrirPanelCliente(int idCliente) {
        // Crear una instancia de PanelCliente con el id del cliente seleccionado
        PanelCliente panelCliente = new PanelCliente(idCliente);

        // Usar un JFrame para mostrar el panel (ventana nueva)
        JFrame frame = new JFrame("Detalles del Cliente");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(panelCliente); // Añade el panel al frame
        frame.pack(); // Ajusta el tamaño de la ventana basado en el contenido
        frame.setLocationRelativeTo(null); // Centra la ventana
        frame.setVisible(true);
    }
    
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Lista de Clientes");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            ListaClientesPanel listaClientesPanel = new ListaClientesPanel();
            frame.add(listaClientesPanel);
            
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
