package UISwing.ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DB.CitaDAO;
import model.Cita;

public class PanelCalendario extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private LocalDate fechaInicioSemana;
    private JLabel lblCurrentDate;
    private CitaDAO citaDAO = new CitaDAO();
 // Agregar esto como un campo de la clase PanelCalendario
    private Map<Point, Integer> posicionACitaId = new HashMap<>();
    private List<CitaActualizadaListener> listeners = new ArrayList<>();

    public PanelCalendario() {
        fechaInicioSemana = LocalDate.now().with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        setLayout(null);
        inicializarModeloYTabla();
        inicializarComponentes();
        cargarCitas();
    }
    private void inicializarModeloYTabla() {
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Las celdas no deben ser editables
            }
        };

        table = new JTable(model);
        table.setRowHeight(50);
        table.setDefaultRenderer(Object.class, new CitaTableCellRenderer());

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = table.rowAtPoint(e.getPoint());
                int columna = table.columnAtPoint(e.getPoint());

                LocalDate fechaSeleccionada = fechaInicioSemana.plusDays(columna - 1);
                LocalTime horaSeleccionada = LocalTime.of(8 + (fila / 4), (fila % 4) * 15);

                if (columna > 0) { // Asegúrate de que se haga clic en una columna de cita, no en la de "Hora"
                    Point clave = new Point(fila, columna);
                    if (posicionACitaId.containsKey(clave)) {
                        int idCita = posicionACitaId.get(clave);
                        Cita cita = citaDAO.obtenerCitaPorId(idCita);
                        if (cita != null) {
                            VentanaModificarCitaDialog ventanaModificar = new VentanaModificarCitaDialog(null, true, cita);
                            ventanaModificar.addCitaActualizadaListener(() -> {
                                cargarCitas(); // Recarga las citas en PanelCalendario
                                notificarCitaActualizada(); // Notifica a todos los listeners de PanelCalendario
                            });
                            ventanaModificar.setVisible(true);
                        }
                    } else {
                        // Aquí manejas el caso para una nueva cita
                        java.util.Date fecha = java.util.Date.from(fechaSeleccionada.atStartOfDay(ZoneId.systemDefault()).toInstant());
                        java.util.Date hora = java.util.Date.from(LocalDateTime.of(fechaSeleccionada, horaSeleccionada).atZone(ZoneId.systemDefault()).toInstant());
                        
                        VentanaCitasDialog nuevaCitaDialog = new VentanaCitasDialog(null, true);
                        nuevaCitaDialog.setFecha(fecha); // Ajusta la fecha
                        nuevaCitaDialog.setHora(hora); // Ajusta la hora
                        nuevaCitaDialog.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                cargarCitas(); // Recargar citas después de potencialmente añadir una nueva
                                notificarCitaActualizada(); // Notificar a los listeners
                            }
                        });
                        nuevaCitaDialog.setVisible(true);
                    }
                }
            }
        });


    }



    private void inicializarComponentes() {
        JPanel panelHeader = crearPanelHeader();
        panelHeader.setBounds(0, 0, 1092, 50); // Ajustar a tu layout
        add(panelHeader);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 50, 1092, 590); // Ajustar a tu layout
        add(scrollPane);
        actualizarModeloTabla();
    }

    private JPanel crearPanelHeader() {
        JPanel panelHeader = new JPanel(new FlowLayout());
        JButton prevWeekButton = new JButton("<");
        prevWeekButton.addActionListener(e -> navegar(-1));
        panelHeader.add(prevWeekButton);

        lblCurrentDate = new JLabel();
        panelHeader.add(lblCurrentDate);
        actualizarFechaActual();

        JButton nextWeekButton = new JButton(">");
        nextWeekButton.addActionListener(e -> navegar(1));
        panelHeader.add(nextWeekButton);

        return panelHeader;
    }

    private void navegar(int semanas) {
        fechaInicioSemana = fechaInicioSemana.plusWeeks(semanas);
        actualizarModeloTabla();
        cargarCitas();
    }

    private void actualizarFechaActual() {
        // Esto actualiza la fecha actual en el lblCurrentDate.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
        lblCurrentDate.setText(String.format("Semana del %s al %s",
                fechaInicioSemana.format(formatter),
                fechaInicioSemana.plusDays(6).format(formatter)));
    }

    private void cargarCitas() {
        List<Cita> citas = citaDAO.recuperarCitasPorSemana(fechaInicioSemana, fechaInicioSemana.plusDays(6));
        actualizarTablaConCitas(citas);
    }

    private void actualizarModeloTabla() {
        model.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E dd/MM");
        LocalDate diaDeLaSemana = fechaInicioSemana;
        String[] columnNames = new String[8];
        columnNames[0] = "Hora";
        for (int i = 1; i <= 7; i++) {
            columnNames[i] = diaDeLaSemana.format(formatter);
            diaDeLaSemana = diaDeLaSemana.plusDays(1);
        }
        model.setColumnIdentifiers(columnNames);
        actualizarTablaConHoras();
        actualizarFechaActual(); // Asegúrate de que la fecha entre las flechas se actualiza correctamente
    }

    private void actualizarTablaConHoras() {
        model.setRowCount(0); // Limpia las filas antes de añadir las horas nuevamente
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        for (int i = 8; i <= 22; i++) {
            model.addRow(new Object[]{formatter.format(LocalTime.of(i, 0)), "", "", "", "", "", "", ""});
            model.addRow(new Object[]{formatter.format(LocalTime.of(i, 15)), "", "", "", "", "", "", ""});
            model.addRow(new Object[]{formatter.format(LocalTime.of(i, 30)), "", "", "", "", "", "", ""});
            model.addRow(new Object[]{formatter.format(LocalTime.of(i, 45)), "", "", "", "", "", "", ""});
        }
    }




    private void actualizarTablaConCitas(List<Cita> citas) {
        posicionACitaId.clear(); // Limpiar el mapa antes de llenarlo nuevamente
        for (int i = 1; i < model.getColumnCount(); i++) {
            for (int j = 0; j < model.getRowCount(); j++) {
                model.setValueAt("", j, i);
            }
        }
        // El resto del proceso de carga de citas...
        citas.forEach(cita -> {
            LocalDate fechaCita = cita.getFecha();
            LocalTime horaCita = cita.getHora();
            int columna = fechaCita.getDayOfWeek().getValue();
            int fila = ((horaCita.getHour() - 8) * 4) + (horaCita.getMinute() / 15); // Ajustar según cómo estás llenando las filas
            
            String infoCita = cita.getTitulo() + " - " + cita.getTipo(); // O cualquier otro dato que estés mostrando
            model.setValueAt(infoCita, fila, columna);
            
            posicionACitaId.put(new Point(fila, columna), cita.getId()); // Asociar la posición de la celda con el ID de la cita
        });
    }
    
    public void addCitaActualizadaListener(CitaActualizadaListener listener) {
        listeners.add(listener);
    }

    private void notificarCitaActualizada() {
        for (CitaActualizadaListener listener : listeners) {
            listener.onCitaActualizada();
        }
    }



    class CitaTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (value != null) {
                String[] parts = value.toString().split(" - ");
                if (parts.length > 1) {
                    String tipo = parts[1];
                    switch (tipo) {
                        case "Visita":
                            // Utiliza un verde más oscuro que el Color.GREEN predeterminado
                            setBackground(new Color(0, 153, 0)); // Un ejemplo de verde oscuro
                            setForeground(Color.WHITE); // Texto en blanco para mejor contraste
                            setFont(new Font("Segoe UI", Font.BOLD, 12)); // Cambiar el font si deseas
                            break;
                        case "Consulta":
                            setBackground(Color.BLUE);
                            setForeground(Color.WHITE); // Asegúrate de que el texto sea visible
                            setFont(new Font("Segoe UI", Font.BOLD, 12));
                            break;
                        case "Urgencia":
                            setBackground(Color.RED);
                            setForeground(Color.WHITE); // Mejora el contraste
                            setFont(new Font("Segoe UI", Font.BOLD, 12));
                            break;
                        default:
                            setBackground(Color.LIGHT_GRAY);
                            setForeground(Color.BLACK); // Color del texto por defecto
                            setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Font por defecto
                            break;
                    }
                } else {
                    // Configuración por defecto para celdas sin tipo especificado
                    setBackground(Color.WHITE);
                    setForeground(Color.BLACK);
                    setFont(new Font("Segoe UI", Font.PLAIN, 12));
                }
            } else {
                // Configuración por defecto si el valor es nulo
                setBackground(Color.WHITE);
                setForeground(Color.BLACK);
                setFont(new Font("Segoe UI", Font.PLAIN, 12));
            }
            return this;
        }
    }


}
