package UISwing.ventanas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import model.Cita;
import DB.CitaDAO;
import UISwing.ventanas.DialogoListaHospitalizados.PaddingTableCellRenderer;

public class DialogoListaCitas extends JDialog implements CitaActualizadaListener{
    private JTable table;
    private DefaultTableModel model;
    private CitaDAO citaDAO;
    private CitaActualizadaListener citaActualizadaListener;

    public DialogoListaCitas(Frame owner) {
        super(owner, "Listado de Citas", true);
        setSize(1024, 600);
        setLocationRelativeTo(owner);

        citaDAO = new CitaDAO();
        initUI();
        cargarCitas();
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
        String[] columnNames = {"Id","Titulo", "Fecha", "Hora", "Cliente", "Mascota", "Notas"};
        model = new DefaultTableModel(null, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Hace que ninguna celda sea editable directamente en la tabla
                return false;
            }
        };
        table = new JTable(model) {
            // Sobrescribe el método para mostrar tooltips personalizados
            public String getToolTipText(MouseEvent e) {
                String tip = null;
                java.awt.Point p = e.getPoint();
                int rowIndex = rowAtPoint(p);
                int colIndex = columnAtPoint(p);

                try {
                    // Verifica si el índice de columna es el de las notas
                    if (colIndex == this.getColumn("Notas").getModelIndex()) {
                        tip = getValueAt(rowIndex, colIndex).toString();
                    } else {
                        // Usa el tooltip predeterminado para otras columnas si es necesario
                        tip = super.getToolTipText(e);
                    }
                } catch (RuntimeException ex) {
                    // En caso de una excepción, no establece un tooltip
                }

                return tip;
            }
        };
      
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.rowAtPoint(e.getPoint());
                    if (row != -1) {
                        // Obtiene el ID de la cita desde la columna oculta (índice 0)
                        int idCita = (Integer) model.getValueAt(row, 0); // Ahora es seguro asumir que se obtendrá un Integer
                        abrirVentanaModificarCita(idCita);
                    }
                }
            }
        });
        table.setFillsViewportHeight(true);
        table.setIntercellSpacing(new Dimension(10, 4));
        // Aplicar color de fondo al panel y color de texto a las celdas de la tabla
        table.setBackground(Color.decode("#5694F9")); // Color de fondo de las filas
        table.setForeground(Color.WHITE); // Color del texto de las filas
        table.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Ajuste del tamaño de fuente para las filas
        table.setRowHeight(table.getRowHeight() + 10); // Aumenta la altura de las filas para más espacio
        table.getTableHeader().setBackground(Color.decode("#0483FF")); // Color de fondo del encabezado de la tabla
        table.getTableHeader().setForeground(Color.WHITE); // Color del texto del encabezado de la tabla
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16)); // Ajuste del tamaño de fuente para el encabezado

        // Implementación y aplicación del PaddingTableCellRenderer para el padding en las celdas
        PaddingTableCellRenderer cellRenderer = new PaddingTableCellRenderer();
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }
    
    
    private void abrirVentanaModificarCita(int idCita) {
        CitaDAO citaDAO = new CitaDAO();
        Cita cita = citaDAO.obtenerCitaPorId(idCita);
        if (cita != null) {
            VentanaModificarCitaDialog ventanaModificar = new VentanaModificarCitaDialog(null, true, cita);
            // Registra este DialogoListaCitas como listener
            ventanaModificar.addCitaActualizadaListener(citaActualizadaListener); // Pasar el listener de PanelHome
            ventanaModificar.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos de la cita.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    

    private void cargarCitas() {
        List<Cita> citas = citaDAO.recuperarCitas();
        model.setRowCount(0); // Limpia la tabla antes de cargar datos nuevos
        for (Cita cita : citas) {
            // Añade el ID de la cita como el primer elemento de cada fila
            model.addRow(new Object[]{
                cita.getId(), // ID de la cita (se ocultará más adelante)
                cita.getTitulo(), cita.getFecha(), cita.getHora(), cita.getNombreCliente(), cita.getNombreMascota(), cita.getNotas()
            });
        }
        // Opción para ocultar la columna del ID si no lo has hecho aún
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);
    }
    public void setCitaActualizadaListener(CitaActualizadaListener listener) {
        this.citaActualizadaListener = listener;
    }
    @Override
    public void onCitaActualizada() {
        cargarCitas(); // Recargar la lista de citas
    }
    
    @Override
    public void dispose() {
        // Notificar a PanelHome que debe actualizar su UI porque podría haber cambios
        if (citaActualizadaListener != null) {
            citaActualizadaListener.onCitaActualizada();
        }
        super.dispose();
    }


}
