package UISwing.ventanas;

import javax.swing.*;

import DB.CitaDAO;
import DB.ClienteDAO;
import DB.FarmacoDAO;
import DB.HospitalizacionDAO;
import DB.MascotaDAO;
import UISwing.recursos.CustomPanelOpaco;
import UISwing.recursos.RoundedPanel;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import model.Cita;
import model.Cliente;
import model.Hospitalizacion;
import model.Mascota;
import model.UsoFarmaco;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PanelHome extends JPanel implements CitaActualizadaListener {
	
	private JPanel panelDatos;
	private JLabel lblHoraCita;
	private JLabel lblDiaCita;
	private JLabel lblMascotaCita;
	private JLabel lblClienteCita;
	private JLabel lblHoraCita_2;
	private JLabel lblDiaCita_2;
	private JLabel lblMascotaCita_2;
	private JLabel lblNombreCliente2;
	private CustomPanelOpaco panelOpacoCitas;
	private JLabel lblDiaHospitalizacion;
    private JLabel lblMascotaHospitalizacion;
    private JLabel lblClienteHospitalizacion;	
    private JLabel lblHoraHospita_1;
    private JLabel lblHoraHospita_2;
    private JLabel lblDiaHospitalizacion_2;
    private JLabel lblMascotaHospitalizacion_2;
    private JLabel lblClienteHospitalizacion_2;
    private FarmacoDAO farmacoDAO;	

    public PanelHome() {
        setLayout(null); // Usando layout nulo para control total sobre la posición de los componentes
        setOpaque(false);
        this.farmacoDAO = new FarmacoDAO(); 

        inicializarPanelCitas();
        mostrarCitasProximas(); // Luego puedes llamar a mostrarCitasProximas()
        inicializarPanelHospitalizados();
        mostrarHospitalizacionesRecientes();
        inicializarPanelVentas(); // Lo mismo que farmacos pero mas corto
        inicializarPanelFarmacos(); // Panel fármacos sin bordes ni scrollbar visible
        

    }

    private void inicializarPanelCitas() {
        RoundedPanel panelCitas = new RoundedPanel(20);
        panelCitas.setBackground(Color.decode("#7E88E2"));
        panelCitas.setBounds(0, 0, 322, 402);
        add(panelCitas);
        panelCitas.setLayout(null);
        
        panelOpacoCitas = new CustomPanelOpaco();
        panelOpacoCitas.setBounds(24, 77, 274, 192);
        panelOpacoCitas.setBackground(new Color(255, 255, 255, 70));
        panelCitas.add(panelOpacoCitas);
        panelOpacoCitas.setLayout(null);
        
        JLabel lblProximasCitas = new JLabel("Próximas Citas");
        lblProximasCitas.setForeground(new Color(255, 255, 255));
        lblProximasCitas.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblProximasCitas.setBounds(37, 13, 113, 27);
        panelOpacoCitas.add(lblProximasCitas);
        
        lblHoraCita = new JLabel("");
        lblHoraCita.setForeground(new Color(255, 255, 255));
        lblHoraCita.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblHoraCita.setBounds(66, 51, 46, 26);
        panelOpacoCitas.add(lblHoraCita);
        
        lblDiaCita = new JLabel("");
        lblDiaCita.setForeground(new Color(255, 255, 255));
        lblDiaCita.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDiaCita.setBounds(66, 76, 71, 26);
        panelOpacoCitas.add(lblDiaCita);
        
        lblMascotaCita = new JLabel("");
        lblMascotaCita.setForeground(new Color(255, 255, 255));
        lblMascotaCita.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblMascotaCita.setBounds(180, 51, 58, 26);
        panelOpacoCitas.add(lblMascotaCita);
        
        lblClienteCita = new JLabel("");
        lblClienteCita.setForeground(new Color(255, 255, 255));
        lblClienteCita.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblClienteCita.setBounds(180, 76, 84, 26);
        panelOpacoCitas.add(lblClienteCita);
        
        JLabel lblLogoCitaMascota = new JLabel("");
        lblLogoCitaMascota.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoCitasMascota.png")));
        lblLogoCitaMascota.setBounds(155, 51, 20, 26);
        panelOpacoCitas.add(lblLogoCitaMascota);
        
        JLabel lblLogoCitaCliente = new JLabel("");
        lblLogoCitaCliente.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoCitasCliente.png")));
        lblLogoCitaCliente.setBounds(155, 76, 20, 26);
        panelOpacoCitas.add(lblLogoCitaCliente);
        
        lblHoraCita_2 = new JLabel("");
        lblHoraCita_2.setForeground(new Color(255, 255, 255));
        lblHoraCita_2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblHoraCita_2.setBounds(66, 113, 46, 26);
        panelOpacoCitas.add(lblHoraCita_2);	
        
        JLabel lblLogoCitaMascota2 = new JLabel("");
        lblLogoCitaMascota2.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoCitasMascota.png")));
        lblLogoCitaMascota2.setBounds(155, 113, 20, 26);
        panelOpacoCitas.add(lblLogoCitaMascota2);
        
        lblMascotaCita_2 = new JLabel("");
        lblMascotaCita_2.setForeground(new Color(255, 255, 255));
        lblMascotaCita_2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblMascotaCita_2.setBounds(180, 109, 58, 26);
        panelOpacoCitas.add(lblMascotaCita_2);
        
        lblDiaCita_2 = new JLabel("");
        lblDiaCita_2.setForeground(new Color(255, 255, 255));
        lblDiaCita_2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDiaCita_2.setBounds(66, 139, 71, 26);
        panelOpacoCitas.add(lblDiaCita_2);
        
        lblNombreCliente2 = new JLabel("");
        lblNombreCliente2.setForeground(new Color(255, 255, 255));
        lblNombreCliente2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblNombreCliente2.setBounds(180, 139, 84, 26);
        panelOpacoCitas.add(lblNombreCliente2);
        
        JLabel lblLogoCitaCliente2 = new JLabel("");
        lblLogoCitaCliente2.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoCitasCliente.png")));
        lblLogoCitaCliente2.setBounds(155, 139, 20, 26);
        panelOpacoCitas.add(lblLogoCitaCliente2);
        
        JLabel lbllogoCitaReloj = new JLabel("");
        lbllogoCitaReloj.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoReloj.png")));
        lbllogoCitaReloj.setBounds(37, 51, 19, 26);
        panelOpacoCitas.add(lbllogoCitaReloj);
        
        JLabel lbllogoCitaReloj2 = new JLabel("");
        lbllogoCitaReloj2.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoReloj.png")));
        lbllogoCitaReloj2.setBounds(37, 113, 19, 26);
        panelOpacoCitas.add(lbllogoCitaReloj2);
        
        JLabel lbllogoCitaFecha2 = new JLabel("");
        lbllogoCitaFecha2.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoFechaPaneles.png")));
        lbllogoCitaFecha2.setBounds(39, 139, 18, 26);
        panelOpacoCitas.add(lbllogoCitaFecha2);
        
        JLabel lbllogoCitaFecha = new JLabel("");
        lbllogoCitaFecha.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoFechaPaneles.png")));
        lbllogoCitaFecha.setBounds(39, 76, 18, 26);
        panelOpacoCitas.add(lbllogoCitaFecha);
        
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(255, 255, 255));
        separator.setBounds(37, 106, 206, 1);
        panelOpacoCitas.add(separator);
        
        JLabel lblListadoCitas = new JLabel("Ver Listado");
        lblListadoCitas.setForeground(new Color(255, 255, 255));
        lblListadoCitas.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblListadoCitas.setBounds(129, 359, 71, 14);
        // Hacer que el cursor cambie a la forma de mano al pasar sobre el JLabel
        lblListadoCitas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        // Agregar MouseListener al JLabel
        lblListadoCitas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DialogoListaCitas dialogoListaCitas = new DialogoListaCitas(JFrame.getFrames()[0]); // Asume que este es el frame principal
                dialogoListaCitas.setCitaActualizadaListener(PanelHome.this); // Registra PanelHome como el listener
                dialogoListaCitas.setVisible(true);
            }
        });


        panelCitas.add(lblListadoCitas);
       
        
        JButton btnAñadirCita = new JButton("Añadir Cita");
        btnAñadirCita.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btnAñadirCita.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAñadirCita.setBounds(24, 297, 274, 31);
        btnAñadirCita.setBackground(Color.WHITE);
        btnAñadirCita.setForeground(Color.decode("#0057FF")); // Letras en color azul
        btnAñadirCita.setFocusPainted(false); // Evita que se pinte el foco alrededor del botón
        btnAñadirCita.setBorderPainted(false); // Evita que se pinte el borde predeterminado
        btnAñadirCita.setContentAreaFilled(false); // Evita que se pinte el área de contenido
        btnAñadirCita.setOpaque(true); // El botón debe pintar cada pixel dentro de sus límites. Esto es necesario para ver el color de fondo.

        // Personalización del efecto rollover
        btnAñadirCita.setRolloverEnabled(true);
        btnAñadirCita.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAñadirCita.setBackground(Color.decode("#003366")); // Color azul oscuro para rollover
                btnAñadirCita.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAñadirCita.setBackground(Color.WHITE); // Color blanco cuando el ratón sale
                btnAñadirCita.setForeground(Color.decode("#0057FF"));
            }
        });
        
        btnAñadirCita.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VentanaCitasDialog dialog = new VentanaCitasDialog(null, true);
                dialog.setTitle("Añadir Cita");
                dialog.setLocationRelativeTo(null);
                dialog.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        // Llama aquí al método para actualizar las citas pendientes en PanelHome
                        actualizarCitasPendientes();
                    }
                });
                dialog.setVisible(true);
            }
        });



        panelCitas.add(btnAñadirCita);
        
        JLabel lblLogoCitas = new JLabel("");
        lblLogoCitas.setBounds(24, 21, 19, 37);
        lblLogoCitas.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoPanelCitas.png")));
        panelCitas.add(lblLogoCitas);
        
        JLabel lbltextoCitaspendientes = new JLabel("Citas Pendientes");
        lbltextoCitaspendientes.setForeground(new Color(255, 255, 255));
        lbltextoCitaspendientes.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbltextoCitaspendientes.setBounds(44, 21, 183, 37);
        panelCitas.add(lbltextoCitaspendientes);
        
      

    }

    private void inicializarPanelHospitalizados() {
        RoundedPanel panelHospitalizados = new RoundedPanel(20);
        panelHospitalizados.setBackground(Color.decode("#0483FF"));
        panelHospitalizados.setBounds(347, 0, 321, 402);
        panelHospitalizados.setLayout(null);
        add(panelHospitalizados);
        
        CustomPanelOpaco panelOpacoHospita = new CustomPanelOpaco();
        panelOpacoHospita.setLayout(null);
        panelOpacoHospita.setBounds(24, 77, 274, 192);
        panelOpacoHospita.setBackground(new Color(255, 255, 255, 70));
        panelHospitalizados.add(panelOpacoHospita);
        
        JLabel lbltextoHospitalizados = new JLabel("Últimas hospitalizaciones");
        lbltextoHospitalizados.setForeground(Color.WHITE);
        lbltextoHospitalizados.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lbltextoHospitalizados.setBounds(37, 11, 216, 27);
        panelOpacoHospita.add(lbltextoHospitalizados);
        
        lblDiaHospitalizacion = new JLabel("");
        lblDiaHospitalizacion.setForeground(new Color(255, 255, 255));
        lblDiaHospitalizacion.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDiaHospitalizacion.setBounds(63, 74, 71, 26);
        panelOpacoHospita.add(lblDiaHospitalizacion);
        
        lblMascotaHospitalizacion = new JLabel("");
        lblMascotaHospitalizacion.setForeground(new Color(255, 255, 255));
        lblMascotaHospitalizacion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblMascotaHospitalizacion.setBounds(169, 49, 58, 26);
        panelOpacoHospita.add(lblMascotaHospitalizacion);
        
        lblClienteHospitalizacion = new JLabel("");
        lblClienteHospitalizacion.setForeground(new Color(255, 255, 255));
        lblClienteHospitalizacion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblClienteHospitalizacion.setBounds(169, 74, 84, 26);
        panelOpacoHospita.add(lblClienteHospitalizacion);
        
        JLabel lblLogoHospitalizacionMascota = new JLabel("");
        lblLogoHospitalizacionMascota.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoCitasMascota.png")));
        lblLogoHospitalizacionMascota.setBounds(144, 49, 20, 26);
        panelOpacoHospita.add(lblLogoHospitalizacionMascota);
        
        JLabel lblLogoHospitalizacionCliente = new JLabel("");
        lblLogoHospitalizacionCliente.setBounds(144, 74, 20, 26);
        lblLogoHospitalizacionCliente.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoCitasCliente.png")));
        panelOpacoHospita.add(lblLogoHospitalizacionCliente);
        
        JLabel lblLogolblDiaHospitalizacionMascota_2 = new JLabel("");
        lblLogolblDiaHospitalizacionMascota_2.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoCitasMascota.png")));
        lblLogolblDiaHospitalizacionMascota_2.setBounds(144, 111, 20, 26);
        panelOpacoHospita.add(lblLogolblDiaHospitalizacionMascota_2);
        
        lblMascotaHospitalizacion_2 = new JLabel("");
        lblMascotaHospitalizacion_2.setForeground(new Color(255, 255, 255));
        lblMascotaHospitalizacion_2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblMascotaHospitalizacion_2.setBounds(169, 111, 58, 26);
        panelOpacoHospita.add(lblMascotaHospitalizacion_2);
        
        lblDiaHospitalizacion_2 = new JLabel("");
        lblDiaHospitalizacion_2.setForeground(new Color(255, 255, 255));
        lblDiaHospitalizacion_2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDiaHospitalizacion_2.setBounds(66, 137, 68, 26);
        panelOpacoHospita.add(lblDiaHospitalizacion_2);
        
        lblClienteHospitalizacion_2 = new JLabel("");
        lblClienteHospitalizacion_2.setForeground(new Color(255, 255, 255));
        lblClienteHospitalizacion_2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblClienteHospitalizacion_2.setBounds(169, 137, 84, 26);
        panelOpacoHospita.add(lblClienteHospitalizacion_2);
        
        JLabel lblLogoHospitalizacionCliente_2 = new JLabel("");
        lblLogoHospitalizacionCliente_2.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoCitasCliente.png")));
        lblLogoHospitalizacionCliente_2.setBounds(144, 137, 20, 26);
        panelOpacoHospita.add(lblLogoHospitalizacionCliente_2);
        
        lblHoraHospita_1 = new JLabel("");
        lblHoraHospita_1.setForeground(Color.WHITE);
        lblHoraHospita_1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblHoraHospita_1.setBounds(66, 51, 68, 26);
        panelOpacoHospita.add(lblHoraHospita_1);
        
        lblHoraHospita_2 = new JLabel("");
        lblHoraHospita_2.setForeground(Color.WHITE);
        lblHoraHospita_2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblHoraHospita_2.setBounds(66, 113, 68, 26);
        panelOpacoHospita.add(lblHoraHospita_2);
        
        JLabel lbllogoHospitaReloj = new JLabel("");
        lbllogoHospitaReloj.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoReloj.png")));
        lbllogoHospitaReloj.setBounds(37, 51, 19, 26);
        panelOpacoHospita.add(lbllogoHospitaReloj);
        
        JLabel lbllogoHospitaFecha_2 = new JLabel("");
        lbllogoHospitaFecha_2.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoFechaPaneles.png")));
        lbllogoHospitaFecha_2.setBounds(39, 139, 18, 26);
        panelOpacoHospita.add(lbllogoHospitaFecha_2);
        
        JLabel lbllogoHospitaFecha = new JLabel("");
        lbllogoHospitaFecha.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoFechaPaneles.png")));
        lbllogoHospitaFecha.setBounds(39, 76, 19, 26);
        panelOpacoHospita.add(lbllogoHospitaFecha);
        
        JLabel lbllogoHospitaReloj2 = new JLabel("");
        lbllogoHospitaReloj2.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoReloj.png")));
        lbllogoHospitaReloj2.setBounds(37, 113, 19, 26);
        panelOpacoHospita.add(lbllogoHospitaReloj2);
        
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(255, 255, 255));
        separator.setBounds(37, 106, 206, 1);
        panelOpacoHospita.add(separator);
        
        JButton btnAñadirHospita = new JButton("Añadir Mascota");
        btnAñadirHospita.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btnAñadirHospita.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAñadirHospita.setBounds(24, 297, 274, 31);
        btnAñadirHospita.setBackground(Color.WHITE);
        btnAñadirHospita.setForeground(Color.decode("#0057FF")); // Letras en color azul
        btnAñadirHospita.setFocusPainted(false); // Evita que se pinte el foco alrededor del botón
        btnAñadirHospita.setBorderPainted(false); // Evita que se pinte el borde predeterminado
        btnAñadirHospita.setContentAreaFilled(false); // Evita que se pinte el área de contenido
        btnAñadirHospita.setOpaque(true); // El botón debe pintar cada pixel dentro de sus límites. Esto es necesario para ver el color de fondo.

        // Personalización del efecto rollover
        btnAñadirHospita.setRolloverEnabled(true);
        btnAñadirHospita.addMouseListener(new java.awt.event.MouseAdapter() {
        	@Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAñadirHospita.setBackground(Color.decode("#003366")); // Color azul oscuro para rollover
                btnAñadirHospita.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAñadirHospita.setBackground(Color.WHITE); // Color blanco cuando el ratón sale
                btnAñadirHospita.setForeground(Color.decode("#0057FF"));
            }
        });
        btnAñadirHospita.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VentanaHospitalizadosDialog dialog = new VentanaHospitalizadosDialog(null, true);
                dialog.setTitle("Añadir Cita");
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });

        panelHospitalizados.add(btnAñadirHospita);
        
        JLabel lblListadoHospitalizaciones = new JLabel("Ver Listado");
        lblListadoHospitalizaciones.setForeground(Color.WHITE);
        lblListadoHospitalizaciones.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblListadoHospitalizaciones.setBounds(129, 359, 71, 14);
        // Hacer que el cursor cambie a la forma de mano al pasar sobre el JLabel
        lblListadoHospitalizaciones.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        // Agregar MouseListener al JLabel
        lblListadoHospitalizaciones.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Crea y muestra el diálogo de listado de citas al hacer clic en el JLabel
                DialogoListaHospitalizados dialogoListaHospitalizados = new DialogoListaHospitalizados(JFrame.getFrames()[0]); // Asume que este es el frame principal
                dialogoListaHospitalizados.setVisible(true);
            }
        });

        panelHospitalizados.add(lblListadoHospitalizaciones);
        
        JLabel lblLogoHospita = new JLabel("");
        lblLogoHospita.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoHospita.png")));
        lblLogoHospita.setBounds(24, 21, 24, 37);
        panelHospitalizados.add(lblLogoHospita);
        
        JLabel lbltextoHospitapendientes = new JLabel("Hospitalizados");
        lbltextoHospitapendientes.setForeground(Color.WHITE);
        lbltextoHospitapendientes.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbltextoHospitapendientes.setBounds(47, 21, 165, 37);
        panelHospitalizados.add(lbltextoHospitapendientes);

    }

    private void inicializarPanelVentas() {
        RoundedPanel panelVentas = new RoundedPanel(20);
        panelVentas.setBackground(Color.decode("#577BD1"));
        panelVentas.setBounds(692, 0, 420, 402);
        panelVentas.setLayout(null);
        add(panelVentas);
        
        JLabel lblListadoVentas = new JLabel("Ver Listado");
        lblListadoVentas.setForeground(Color.WHITE);
        lblListadoVentas.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblListadoVentas.setBounds(183, 359, 74, 14);
        panelVentas.add(lblListadoVentas);
        
        JButton btnAñadirVentas = new JButton("Añadir Venta");
        btnAñadirVentas.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btnAñadirVentas.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAñadirVentas.setBounds(24, 297, 374, 31);
        btnAñadirVentas.setBackground(Color.WHITE);
        btnAñadirVentas.setForeground(Color.decode("#0057FF")); // Letras en color azul
        btnAñadirVentas.setFocusPainted(false); // Evita que se pinte el foco alrededor del botón
        btnAñadirVentas.setBorderPainted(false); // Evita que se pinte el borde predeterminado
        btnAñadirVentas.setContentAreaFilled(false); // Evita que se pinte el área de contenido
        btnAñadirVentas.setOpaque(true); // El botón debe pintar cada pixel dentro de sus límites. Esto es necesario para ver el color de fondo.

        // Personalización del efecto rollover
        btnAñadirVentas.setRolloverEnabled(true);
        btnAñadirVentas.addMouseListener(new java.awt.event.MouseAdapter() {
        	@Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAñadirVentas.setBackground(Color.decode("#003366")); // Color azul oscuro para rollover
                btnAñadirVentas.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAñadirVentas.setBackground(Color.WHITE); // Color blanco cuando el ratón sale
                btnAñadirVentas.setForeground(Color.decode("#0057FF"));
            }
        });
        panelVentas.add(btnAñadirVentas);
        
        CustomPanelOpaco panelOpacoVentas = new CustomPanelOpaco();
        panelOpacoVentas.setLayout(null);
        panelOpacoVentas.setBounds(24, 77, 374, 191);
        panelOpacoVentas.setBackground(new Color(255, 255, 255, 70));
        panelVentas.add(panelOpacoVentas);
          
        JLabel lbltextoVentas = new JLabel("Últimas ventas");
        lbltextoVentas.setForeground(Color.WHITE);
        lbltextoVentas.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbltextoVentas.setBounds(48, 21, 171, 31);
        panelVentas.add(lbltextoVentas);
        
        JLabel lblLogoPanelVentas = new JLabel("");
        lblLogoPanelVentas.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoPanelVentas.png")));
        lblLogoPanelVentas.setBounds(24, 21, 22, 31);
        panelVentas.add(lblLogoPanelVentas);
        
        JPanel panelEncabezadosVentas = crearPanelEncabezadosVentas();
        panelEncabezadosVentas.setOpaque(false);
        panelEncabezadosVentas.setBounds(0, 10, 376, 30); // Establece la posición y el tamaño del panel de encabezados de ventas
        panelOpacoVentas.add(panelEncabezadosVentas);

        JPanel panelDatosVentas = new JPanel();
        panelDatosVentas.setLayout(new BoxLayout(panelDatosVentas, BoxLayout.Y_AXIS));
        panelDatosVentas.setBounds(0, 40, 376, 152); // Establece la posición y el tamaño del panel de datos de ventas
        panelDatosVentas.setOpaque(false);
        panelOpacoVentas.add(panelDatosVentas);

        // Agrega algunos datos de ventas de ejemplo
        agregarFilaDatosVentas(panelDatosVentas, new String[]{"10:00", "Alimento para perros", "2", "$20.00"});
        agregarFilaDatosVentas(panelDatosVentas, new String[]{"10:30", "Collar antipulgas", "1", "$15.00"});
        agregarFilaDatosVentas(panelDatosVentas, new String[]{"10:30", "Collar antipulgas", "1", "$15.00"});
        agregarFilaDatosVentas(panelDatosVentas, new String[]{"10:30", "Collar antipulgas", "1", "$150.00"});
       
    }

    private void inicializarPanelFarmacos() {
        // Panel principal de fármacos que contendrá todo
    	RoundedPanel panelFarmacos = new RoundedPanel(20); // Radio Borde
        panelFarmacos.setLayout(null);
        panelFarmacos.setBackground(Color.decode("#5C8CCD"));
        panelFarmacos.setBounds(0, 413, 1112, 240); 
        add(panelFarmacos);

        // Panel opaco que contendrá tanto el panel de encabezados como el panel de datos
        CustomPanelOpaco panelOpaco = new CustomPanelOpaco();
        panelOpaco.setLayout(null); 
        panelOpaco.setBounds(24, 57, 1065, 160);
        panelOpaco.setBackground(new Color(255, 255, 255, 70)); // El color se establece aquí pero la opacidad en paintComponent
        panelFarmacos.add(panelOpaco);

        // Panel de encabezados
        JPanel panelEncabezados = crearPanelEncabezadosFarmacos();
        panelEncabezados.setBounds(10, 10, 1034, 30); // Asegura que los encabezados se colocan correctamente
        panelOpaco.add(panelEncabezados);

        panelDatos = new JPanel();
        panelDatos.setLayout(new BoxLayout(panelDatos, BoxLayout.Y_AXIS));
        panelDatos.setBounds(10, 40, 1034, 110);
        panelDatos.setOpaque(false);
        panelOpaco.add(panelDatos);

        	

        
        
        JLabel lbltextoUltimosFarmacos = new JLabel("Ultimos fármacos utilizados");
        lbltextoUltimosFarmacos.setForeground(new Color(255, 255, 255));
        lbltextoUltimosFarmacos.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbltextoUltimosFarmacos.setBounds(54, 11, 259, 28);
        panelFarmacos.add(lbltextoUltimosFarmacos);
        cargarDatosFarmacos();
        JLabel lblLogoUltimosFarmacos = new JLabel("");
        lblLogoUltimosFarmacos.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoFarmacos.png")));
        lblLogoUltimosFarmacos.setBounds(24, 11, 26, 28);
        panelFarmacos.add(lblLogoUltimosFarmacos);

  
        
    }
    
    private void mostrarCitasProximas() {
        CitaDAO citaDAO = new CitaDAO();
        List<Cita> citasProximas = citaDAO.recuperarCitasFuturas();

        if (!citasProximas.isEmpty()) {
            Cita primeraCita = citasProximas.get(0);
            lblHoraCita.setText(primeraCita.getHora() != null ? primeraCita.getHora().toString() : "Hora no disponible");
            lblDiaCita.setText(primeraCita.getFecha() != null ? primeraCita.getFecha().toString() : "Fecha no disponible");
            lblMascotaCita.setText(primeraCita.getNombreMascota());
            lblClienteCita.setText(primeraCita.getNombreCliente());

            if (citasProximas.size() > 1) {
                Cita segundaCita = citasProximas.get(1);
                lblHoraCita_2.setText(segundaCita.getHora() != null ? segundaCita.getHora().toString() : "Hora no disponible");
                lblDiaCita_2.setText(segundaCita.getFecha() != null ? segundaCita.getFecha().toString() : "Fecha no disponible");
                lblMascotaCita_2.setText(segundaCita.getNombreMascota());
                lblNombreCliente2.setText(segundaCita.getNombreCliente());
            }
        }

        panelOpacoCitas.revalidate();
        panelOpacoCitas.repaint();
    }


  


    private JPanel crearPanelEncabezadosFarmacos() {
        JPanel panelEncabezados = new JPanel();
        panelEncabezados.setLayout(new GridLayout(1, 6));  // Asegúrate de que el número de columnas aquí y en las filas de datos sea el mismo
        panelEncabezados.setPreferredSize(new Dimension(1000, 30)); // Ajusta el tamaño según tus necesidades
        panelEncabezados.setOpaque(false);

        String[] encabezados = {"Hora", "Fecha", "Nombre Farmaco", "Paciente", "Dosis", "Frecuencia"};
        for (String encabezado : encabezados) {
            JLabel label = new JLabel(encabezado, SwingConstants.CENTER);
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Segoe UI", Font.BOLD, 16));
            panelEncabezados.add(label);
        }
        return panelEncabezados;
    }

    private void agregarFilaDatos(JPanel panelDatos, String[] datos) {
        JPanel panelFila = new JPanel();
        panelFila.setLayout(new GridLayout(1, 6)); // Asegúrate de que el número de columnas sea el mismo que en los encabezados
        panelFila.setPreferredSize(new Dimension(1000, 30)); // Ajusta el tamaño como sea necesario
        panelFila.setOpaque(false);

        for (String dato : datos) {
            JLabel labelDato = new JLabel(dato, SwingConstants.CENTER);
            labelDato.setForeground(Color.WHITE);
            labelDato.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            panelFila.add(labelDato);
        }

        panelDatos.add(panelFila);
    }


    private void agregarFilaDatos(String[] datos) {
        JPanel panelFila = new JPanel();
        panelFila.setLayout(new GridLayout(1, 7, 0, 0)); // Ajustado a 7 columnas
        panelFila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panelFila.setOpaque(false);

        for (String dato : datos) {
            JLabel labelDato = new JLabel(dato, SwingConstants.CENTER);
            labelDato.setForeground(Color.WHITE);
            panelFila.add(labelDato);
        }

        panelDatos.add(panelFila);
    }
    
    private void cargarDatosFarmacos() {
        List<UsoFarmaco> usosFarmacos = farmacoDAO.obtenerUltimosUsosFarmacos();
        panelDatos.removeAll(); // Limpia los datos existentes antes de cargar nuevos

        for (UsoFarmaco uso : usosFarmacos) {
            String[] datosFila = new String[]{
                uso.getFechaHoraUso().format(DateTimeFormatter.ofPattern("HH:mm")), // Hora de uso
                uso.getFechaHoraUso().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), // Fecha de uso
                uso.getNombreFarmaco(), // Nombre del fármaco
                uso.getNombreMascota(), // Nombre de la mascota tratada
                String.valueOf(uso.getCantidadUsada()), // Cantidad usada (dosis administrada)
                uso.getFrecuencia() // Frecuencia de administración del fármaco
            };
            agregarFilaDatos(datosFila);
        }

        panelDatos.revalidate();
        panelDatos.repaint();
    }




    private JPanel crearPanelEncabezadosVentas() {
        JPanel panelEncabezadosVentas = new JPanel();
        panelEncabezadosVentas.setLayout(new GridLayout(1, 4, 0, 0)); // 4 columnas para las ventas
        panelEncabezadosVentas.setBackground(new Color(75, 75, 153)); // Color de fondo azul oscuro

        // Lista de encabezados para ventas
        String[] encabezadosVentas = {"Hora", "Producto", "Cantidad", "Precio"};
        for (String encabezado : encabezadosVentas) {
            JLabel labelEncabezado = new JLabel(encabezado, SwingConstants.CENTER);
            labelEncabezado.setForeground(Color.WHITE);
            labelEncabezado.setFont(new Font("Segoe UI", Font.BOLD, 16));
            panelEncabezadosVentas.add(labelEncabezado);
        }
        
        return panelEncabezadosVentas;
    }
    private void agregarFilaDatosVentas(JPanel panelDatosVentas, String[] datosVentas) {
        JPanel panelFilaVentas = new JPanel();
        panelFilaVentas.setLayout(new GridLayout(1, 4, 0, 0)); // 4 columnas para las ventas
        panelFilaVentas.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); // Ajusta la altura de la fila
        panelFilaVentas.setOpaque(false);

        for (int i = 0; i < datosVentas.length; i++) {
            JLabel labelDato = new JLabel(datosVentas[i], SwingConstants.CENTER);
            labelDato.setForeground(Color.WHITE); // Color de texto
            // Configurar el tooltip para la columna del producto
            if (i == 1) { // Asumiendo que el producto está en la segunda posición del array
                labelDato.setToolTipText(datosVentas[i]);
            }
            panelFilaVentas.add(labelDato);
        }

        panelDatosVentas.add(panelFilaVentas);
        panelDatosVentas.revalidate();
        panelDatosVentas.repaint();
    }
    private void mostrarHospitalizacionesRecientes() {
        HospitalizacionDAO hospitalizacionDAO = new HospitalizacionDAO();
        MascotaDAO mascotaDAO = new MascotaDAO();
        ClienteDAO clienteDAO = new ClienteDAO();
        
        List<Hospitalizacion> hospitalizacionesRecientes = hospitalizacionDAO.recuperarHospitalizacionesSinFechaSalida();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        if (!hospitalizacionesRecientes.isEmpty()) {
            Hospitalizacion primeraHospitalizacion = hospitalizacionesRecientes.get(0);
            Mascota mascotaPrimera = mascotaDAO.obtenerMascotaPorId(primeraHospitalizacion.getIdMascota());
            Cliente clientePrimera = clienteDAO.obtenerClientePorId(mascotaPrimera.getIdCliente());

            lblDiaHospitalizacion.setText(primeraHospitalizacion.getFechaIngreso().format(dateFormatter));
			lblHoraHospita_1.setText(primeraHospitalizacion.getFechaIngreso().format(timeFormatter));
            lblMascotaHospitalizacion.setText(mascotaPrimera.getNombre());
            lblClienteHospitalizacion.setText(clientePrimera.getNombre() + " " + clientePrimera.getApellidos());

            if (hospitalizacionesRecientes.size() > 1) {
                Hospitalizacion segundaHospitalizacion = hospitalizacionesRecientes.get(1);
                Mascota mascotaSegunda = mascotaDAO.obtenerMascotaPorId(segundaHospitalizacion.getIdMascota());
                Cliente clienteSegunda = clienteDAO.obtenerClientePorId(mascotaSegunda.getIdCliente());

                lblDiaHospitalizacion_2.setText(segundaHospitalizacion.getFechaIngreso().format(dateFormatter));
                lblHoraHospita_2.setText(segundaHospitalizacion.getFechaIngreso().format(timeFormatter));
                lblMascotaHospitalizacion_2.setText(mascotaSegunda.getNombre());
                lblClienteHospitalizacion_2.setText(clienteSegunda.getNombre() + " " + clienteSegunda.getApellidos());
            }
        }
    }
    public void actualizarCitasPendientes() {
        mostrarCitasProximas();
    }
    @Override
    public void onCitaActualizada() {
        actualizarCitasPendientes(); // Suponiendo que este método actualiza la lista de citas pendientes
        mostrarCitasProximas();
        // Cualquier otra actualización de UI necesaria
    }
  

}
