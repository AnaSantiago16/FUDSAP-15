/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Empleado;

import conexion.Libreria;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Personas extends JFrame {
    private JTable tablaPersonas;
    private JComboBox<String> comboTipo;
    private JTextField txtBuscar;
    private DefaultTableModel modeloOriginal;

    public Personas() {
        setTitle("Gestión de Personas");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        PanelConDegradado fondo = new PanelConDegradado();
        fondo.setLayout(null);
        setContentPane(fondo);

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());
        panel.setBounds(20, 20, 900, 500);
        fondo.add(panel);

        JPanel panelBusqueda = new JPanel(new BorderLayout());
        panelBusqueda.setOpaque(false);

        txtBuscar = new JTextField(20);
        txtBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtBuscar.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                filtrarTabla(txtBuscar.getText());
            }
            public void removeUpdate(DocumentEvent e) {
                filtrarTabla(txtBuscar.getText());
            }
            public void changedUpdate(DocumentEvent e) {
                filtrarTabla(txtBuscar.getText());
            }
        });

        JPanel buscarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buscarPanel.setOpaque(false);
        buscarPanel.add(new JLabel("BUSCAR: "));
        buscarPanel.add(txtBuscar);

        comboTipo = new JComboBox<>(new String[]{"Todos", "Clientes", "Empleados"});
        comboTipo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboTipo.addActionListener(e -> {
            String seleccion = (String) comboTipo.getSelectedItem();
            if (seleccion.equalsIgnoreCase("Todos")) {
                cargarTodasLasTablas();
            } else if (seleccion.equalsIgnoreCase("Clientes")) {
                cargarTabla("registro");
            } else {
                cargarTabla("empleado");
            }
        });

        JPanel comboPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        comboPanel.setOpaque(false);
        comboPanel.add(new JLabel("TIPO: "));
        comboPanel.add(comboTipo);

        panelBusqueda.add(buscarPanel, BorderLayout.NORTH);
        panelBusqueda.add(comboPanel, BorderLayout.SOUTH);
        panel.add(panelBusqueda, BorderLayout.NORTH);

        tablaPersonas = new JTable();
        tablaPersonas.setFillsViewportHeight(true);
        tablaPersonas.setRowHeight(28);
        tablaPersonas.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablaPersonas.setForeground(Color.BLACK);

        JTableHeader header = tablaPersonas.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setBackground(new Color(214, 40, 40));
        header.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(tablaPersonas);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel botones = new JPanel();
        botones.setOpaque(false);
        botones.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 15));

        JButton agregar = crearBoton("AGREGAR");
        agregar.addActionListener(e -> new AgregarPersona().setVisible(true));

        JButton eliminar = crearBoton("ELIMINAR");
        eliminar.addActionListener(e -> {
            int filaSeleccionada = tablaPersonas.getSelectedRow();
            if (filaSeleccionada < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione una persona para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String nombre = tablaPersonas.getValueAt(filaSeleccionada, 0).toString();
            String tipo = tablaPersonas.getValueAt(filaSeleccionada, tablaPersonas.getColumnCount() - 1).toString();

            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro que desea eliminar a: " + nombre + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                eliminarPersona(nombre, tipo);
            }
        });

        JButton refresh = crearBoton("REFRESCAR");
        refresh.addActionListener(e -> {
            String seleccion = (String) comboTipo.getSelectedItem();
            if (seleccion.equalsIgnoreCase("Todos")) {
                cargarTodasLasTablas();
            } else if (seleccion.equalsIgnoreCase("Clientes")) {
                cargarTabla("registro");
            } else {
                cargarTabla("empleado");
            }
        });

        botones.add(agregar);
        botones.add(eliminar);
        botones.add(refresh);
        panel.add(botones, BorderLayout.SOUTH);

        comboTipo.setSelectedItem("Todos");
        cargarTodasLasTablas();
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFocusPainted(false);
        boton.setBackground(new Color(214, 40, 40));
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setPreferredSize(new Dimension(120, 35));
        return boton;
    }

    private void cargarTabla(String tipo) {
        Libreria.EntidadDAO dao = new Libreria.EntidadDAO();
        dao.setTabla(tipo);

        DefaultTableModel model;
        List<String> columnas;

        if (tipo.equals("registro")) {
            model = new DefaultTableModel(new Object[]{"Nombre", "Correo", "Teléfono", "Tipo"}, 0);
            columnas = Arrays.asList("nombre", "correo", "telefono");
        } else {
            model = new DefaultTableModel(new Object[]{"Nombre", "Correo", "Edad", "Horario", "Tipo"}, 0);
            columnas = Arrays.asList("nombre", "correo", "edad", "horario");
        }

        dao.setColumnas(columnas);
        List<Libreria.EntidadGenerica> lista = dao.obtenerEntidades();

        for (Libreria.EntidadGenerica entidad : lista) {
            if (tipo.equals("registro")) {
                model.addRow(new Object[]{entidad.getCampo("nombre"), entidad.getCampo("correo"), entidad.getCampo("telefono"), "Cliente"});
            } else {
                model.addRow(new Object[]{entidad.getCampo("nombre"), entidad.getCampo("correo"), entidad.getCampo("edad"), entidad.getCampo("horario"), "Empleado"});
            }
        }

        tablaPersonas.setModel(model);
        ajustarAnchoColumnas();
        modeloOriginal = model;
    }

    private void cargarTodasLasTablas() {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Nombre", "Descripción", "Tipo"}, 0);

        Libreria.EntidadDAO daoRegistro = new Libreria.EntidadDAO();
        daoRegistro.setTabla("registro");
        daoRegistro.setColumnas(Arrays.asList("nombre", "correo"));
        for (Libreria.EntidadGenerica entidad : daoRegistro.obtenerEntidades()) {
            model.addRow(new Object[]{entidad.getCampo("nombre"), entidad.getCampo("correo"), "Cliente"});
        }

        Libreria.EntidadDAO daoEmpleado = new Libreria.EntidadDAO();
        daoEmpleado.setTabla("empleado");
        daoEmpleado.setColumnas(Arrays.asList("nombre", "correo"));
        for (Libreria.EntidadGenerica entidad : daoEmpleado.obtenerEntidades()) {
            model.addRow(new Object[]{entidad.getCampo("nombre"), entidad.getCampo("correo"), "Empleado"});
        }

        tablaPersonas.setModel(model);
        ajustarAnchoColumnas();
        modeloOriginal = model;
    }

    private void ajustarAnchoColumnas() {
        if (tablaPersonas.getColumnModel().getColumnCount() >= 3) {
            TableColumn colNombre = tablaPersonas.getColumnModel().getColumn(0);
            TableColumn colDescripcion = tablaPersonas.getColumnModel().getColumn(1);
            TableColumn colTipo = tablaPersonas.getColumnModel().getColumn(2);
            colNombre.setPreferredWidth(150);
            colDescripcion.setPreferredWidth(500);
            colTipo.setPreferredWidth(100);
        }
    }

    private void filtrarTabla(String texto) {
        if (modeloOriginal == null) return;

        DefaultTableModel filtrado = new DefaultTableModel();
        for (int i = 0; i < modeloOriginal.getColumnCount(); i++) {
            filtrado.addColumn(modeloOriginal.getColumnName(i));
        }

        for (int i = 0; i < modeloOriginal.getRowCount(); i++) {
            String valor = modeloOriginal.getValueAt(i, 0).toString().toLowerCase();
            if (valor.contains(texto.toLowerCase())) {
                Object[] fila = new Object[modeloOriginal.getColumnCount()];
                for (int j = 0; j < fila.length; j++) fila[j] = modeloOriginal.getValueAt(i, j);
                filtrado.addRow(fila);
            }
        }

        tablaPersonas.setModel(filtrado);
        ajustarAnchoColumnas();
    }

    private void eliminarPersona(String nombre, String tipo) {
        Libreria.EntidadDAO dao = new Libreria.EntidadDAO();
        String tabla = tipo.equalsIgnoreCase("Cliente") ? "registro" : "empleado";
        dao.setTabla(tabla);
        dao.setColumnaId("nombre");

        try {
            if (dao.eliminarEntidad(nombre)) {
                JOptionPane.showMessageDialog(this, "Persona eliminada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                if (comboTipo.getSelectedItem().toString().equalsIgnoreCase("Todos")) cargarTodasLasTablas();
                else cargarTabla(tabla);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar la persona", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar la persona: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Personas().setVisible(true));
    }
}

class PanelConDegradado extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gp = new GradientPaint(0, 0, new Color(0xD62828), 0, getHeight(), new Color(0x6A040F));
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}