package UISwing.ventanas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import model.Hospitalizacion;
import DB.HospitalizacionDAO;
import UISwing.ventanas.DialogoDetalleHospitalizados.ActualizacionListener;

public class DialogoListaHospitalizados extends JDialog implements ActualizacionListener {
    private JTable table;
    private DefaultTableModel model;
    private HospitalizacionDAO hospitalizacionDAO;
    private final Frame ownerFrame;
    
    public DialogoListaHospitalizados(Frame owner) {
        super(owner, "Listado de Hospitalizados", true);
        this.ownerFrame = owner; // Asignar el propietario a la variable
        setSize(1024, 600); // Aumentado el tamaño para una mejor visualización
        setLocationRelativeTo(owner);

        hospitalizacionDAO = new HospitalizacionDAO();
        initUI();
        cargarHospitalizados();
    }
    
    class PaddingTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (component instanceof JLabel) {
                ((JLabel) component).setBorder(new EmptyBorder(0, 15, 0, 15)); // Añade padding izquierdo y derecho
            }
            return component;
        }
    }

    private void initUI() {
        // Añadir columna "Veterinario"
        String[] columnNames = {"ID Hospitalización", "Mascota", "Veterinario", "Fecha ingreso", "Fecha salida", "Motivo", "Tratamiento", "Estado", "Notas"};
        model = new DefaultTableModel(null, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hace que ninguna celda sea editable directamente en la tabla
            }
        };

        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setIntercellSpacing(new Dimension(10, 4)); // Aumenta la separación entre las celdas

        // Ajustes visuales
        table.setBackground(Color.decode("#5694F9"));
        table.setForeground(Color.WHITE);
        table.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Tamaño de fuente aumentado
        table.setRowHeight(table.getRowHeight() + 10); // Aumentar el espaciamiento entre filas
        table.getTableHeader().setBackground(Color.decode("#0483FF"));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16)); // Tamaño de fuente del encabezado aumentado
        PaddingTableCellRenderer cellRenderer = new PaddingTableCellRenderer();
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Ocultar la columna del ID de hospitalización
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setMinWidth(0);
        columnModel.getColumn(0).setMaxWidth(0);
        columnModel.getColumn(0).setWidth(0);
        ActualizacionListener listenerActual = this;
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && row != -1) {
                    int idHospitalizacion = (Integer) model.getValueAt(row, 0);

                    Hospitalizacion hospitalizacion = hospitalizacionDAO.obtenerHospitalizacionPorId(idHospitalizacion);
                    if (hospitalizacion != null) {
                        DialogoDetalleHospitalizados dialogoDetalle = new DialogoDetalleHospitalizados(ownerFrame, true, listenerActual);
                        dialogoDetalle.cargarDatosHospitalizacion(hospitalizacion);
                        dialogoDetalle.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo cargar los detalles de la hospitalización.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }
    

    private void cargarHospitalizados() {
        // Primero, limpia todos los datos existentes en la tabla
        model.setRowCount(0);

        // Luego, carga los datos actualizados
        List<Hospitalizacion> hospitalizaciones = hospitalizacionDAO.recuperarTodasLasHospitalizaciones();
        for (Hospitalizacion hospitalizacion : hospitalizaciones) {
            String nombreVeterinario = hospitalizacion.getNombreVeterinario() != null ? hospitalizacion.getNombreVeterinario() : "No asignado";
            model.addRow(new Object[]{
                hospitalizacion.getId(),
                hospitalizacion.getNombreMascota(),
                nombreVeterinario,
                hospitalizacion.getFechaIngreso(),
                hospitalizacion.getFechaSalida(),
                hospitalizacion.getMotivo(),
                hospitalizacion.getTratamiento(),
                hospitalizacion.getEstado(),
                hospitalizacion.getNotas()
            });
        }
    }

    private void mostrarDialogoDetalleHospitalizacion(Hospitalizacion hospitalizacion) {
        // Asegúrate de que este método esté siendo llamado correctamente cuando sea necesario.
        DialogoDetalleHospitalizados dialogoDetalle = new DialogoDetalleHospitalizados(ownerFrame, true, this); // `this` pasa la instancia de ActualizacionListener
        dialogoDetalle.cargarDatosHospitalizacion(hospitalizacion);
        dialogoDetalle.setVisible(true);
    }

    
    @Override
    public void onActualizacion() {
        cargarHospitalizados(); // Recarga la tabla
    }
    
    
    
    
    

}
