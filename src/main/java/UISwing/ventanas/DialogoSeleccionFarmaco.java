package UISwing.ventanas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import model.Farmaco;
import DB.FarmacoDAO;

public class DialogoSeleccionFarmaco extends JDialog {
    private JComboBox<Farmaco> comboBoxFarmacos;
    private JTextField textFieldDosis;
    private JTextField textFieldFrecuencia;
    private JButton btnAceptar;
    private JButton btnCancelar;
    private boolean seleccionado = false;
    private FarmacoDAO farmacoDAO;

    public DialogoSeleccionFarmaco(Frame owner, boolean modal, FarmacoDAO farmacoDAO) {
        super(owner, modal);
        this.farmacoDAO = farmacoDAO;
        setTitle("Seleccionar Fármaco");
        setUndecorated(true);
        setSize(new Dimension(300, 250));
        setLocationRelativeTo(null);

        comboBoxFarmacos = new JComboBox<>();
        comboBoxFarmacos.setEditable(true);
        comboBoxFarmacos.setBounds(26, 41, 248, 24);
        comboBoxFarmacos.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Farmaco) {
                    setText(((Farmaco) value).getNombre());
                }
                return this;
            }
        });

        JTextField textfield = (JTextField) comboBoxFarmacos.getEditor().getEditorComponent();
        textfield.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String search = textfield.getText();
                actualizarListaFarmacos(search);
            }
        });

        getContentPane().setLayout(null);
        getContentPane().add(comboBoxFarmacos);

        textFieldDosis = new JTextField(10);
        textFieldDosis.setBounds(26, 96, 116, 22);
        getContentPane().add(textFieldDosis);

        textFieldFrecuencia = new JTextField(10);
        textFieldFrecuencia.setBounds(26, 154, 248, 22);
        getContentPane().add(textFieldFrecuencia);

        btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(182, 200, 92, 23);
        btnAceptar.addActionListener(e -> {
            seleccionado = true;
            setVisible(false);
        });
        getContentPane().add(btnAceptar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(26, 200, 95, 23);
        btnCancelar.addActionListener(e -> {
            seleccionado = false;
            setVisible(false);
        });
        getContentPane().add(btnCancelar);
        
        JLabel lblFarmaco = new JLabel("Farmaco:");
        lblFarmaco.setForeground(new Color(255, 255, 255));
        lblFarmaco.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblFarmaco.setBounds(26, 16, 69, 14);
        getContentPane().add(lblFarmaco);
        
        JLabel lblDosis = new JLabel("Dosis:");
        lblDosis.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblDosis.setForeground(new Color(255, 255, 255));
        lblDosis.setBounds(26, 76, 46, 14);
        getContentPane().add(lblDosis);
        
        JLabel lblFrecuencia = new JLabel("Frecuencia:");
        lblFrecuencia.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblFrecuencia.setForeground(new Color(255, 255, 255));
        lblFrecuencia.setBounds(26, 129, 95, 14);
        getContentPane().add(lblFrecuencia);

        applyStyles();
    }
    
    private void actualizarListaFarmacos(String texto) {
        List<Farmaco> farmacos = farmacoDAO.buscarFarmacos(texto);
        comboBoxFarmacos.removeAllItems();
        farmacos.forEach(comboBoxFarmacos::addItem);
        comboBoxFarmacos.setPopupVisible(true);
    }

    private void applyStyles() {
        getContentPane().setBackground(Color.decode("#0483FF"));
        comboBoxFarmacos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        textFieldDosis.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        textFieldFrecuencia.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnAceptar.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnAceptar.setBackground(Color.WHITE);
        btnCancelar.setBackground(Color.WHITE);
        btnAceptar.setForeground(Color.decode("#0057FF"));
        btnCancelar.setForeground(Color.decode("#0057FF"));
    }
    
    

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public Farmaco getFarmacoSeleccionado() {
        return (Farmaco) comboBoxFarmacos.getSelectedItem();
    }

    public String getDosis() {
        return textFieldDosis.getText();
    }

    public String getFrecuencia() {
        return textFieldFrecuencia.getText();
    }
    
    public static void main(String[] args) {
        // Crear instancia de FarmacoDAO (supongamos que ya está inicializado correctamente)
        FarmacoDAO farmacoDAO = new FarmacoDAO();

        // Crear un nuevo diálogo de selección de fármaco y mostrarlo
        DialogoSeleccionFarmaco dialogo = new DialogoSeleccionFarmaco(null, true, farmacoDAO);
        dialogo.setVisible(true);
    }
}
