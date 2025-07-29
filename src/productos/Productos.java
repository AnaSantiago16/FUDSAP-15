/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package productos;
import conexion.Libreria;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;

public class Productos extends JFrame {

    private JTable tablaProductos;
    private JComboBox<String> comboCategorias;
    private JTextField txtBuscar;
    private DefaultTableModel modeloOriginal;

    public Productos() {
        setTitle("Lista de Productos");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Fondo degradado
        PanelConDegradado fondo = new PanelConDegradado();
        fondo.setLayout(null);
        setContentPane(fondo);

        // Panel principal translúcido
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());
       panel.setBounds(20, 20, 900, 500);
        fondo.add(panel);

        // Panel de búsqueda + combo
        JPanel panelBusqueda = new JPanel(new BorderLayout());
        panelBusqueda.setOpaque(false);

        // Campo de búsqueda
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
        buscarPanel.add(new JLabel("BUSCAR : "));
        buscarPanel.add(txtBuscar);

        // ComboBox de categorías
        comboCategorias = new JComboBox<>(new String[]{
                "Todos", "Hamburguesas", "acompanamientos", "Bebidas", "Postres", "Combos"
        });
        comboCategorias.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboCategorias.addActionListener(e -> {
            String seleccion = (String) comboCategorias.getSelectedItem();
            if (seleccion.equalsIgnoreCase("Todos")) {
                cargarTodasLasTablas();
            } else {
                cargarTabla(seleccion.toLowerCase());
            }
        });

        JPanel comboPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        comboPanel.setOpaque(false);
        comboPanel.add(new JLabel("CATEGORÍA : "));
        comboPanel.add(comboCategorias);

        panelBusqueda.add(buscarPanel, BorderLayout.NORTH);
        panelBusqueda.add(comboPanel, BorderLayout.SOUTH);

        panel.add(panelBusqueda, BorderLayout.NORTH);

        // Tabla
        tablaProductos = new JTable();
        tablaProductos.setFillsViewportHeight(true);
        tablaProductos.setRowHeight(28);
        tablaProductos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablaProductos.setForeground(Color.BLACK);
        //tablaProductos.setBackground(new Color(255, 255, 255, 230));
        // Ajustar ancho de columnas
 // Categoría

        JTableHeader header = tablaProductos.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setBackground(new Color(214, 40, 40));
        header.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        panel.add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel botones = new JPanel();
        botones.setOpaque(false);
        botones.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 15));

        JButton agregar = crearBoton("AGREGAR");
        agregar.addActionListener(e -> {
            new AgregarProducto().setVisible(true);
        });

       // Dentro del constructor de la clase Productos, donde se crea el botón eliminar:
JButton eliminar = crearBoton("ELIMINAR");
eliminar.addActionListener(e -> {
    int filaSeleccionada = tablaProductos.getSelectedRow();
    
    if (filaSeleccionada < 0) {
        JOptionPane.showMessageDialog(this, "Seleccione un producto para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // Obtener datos del producto seleccionado
    String nombre = tablaProductos.getValueAt(filaSeleccionada, 0).toString();
    String categoria = tablaProductos.getValueAt(filaSeleccionada, 3).toString().toLowerCase();
    
    int confirmacion = JOptionPane.showConfirmDialog(
        this, 
        "¿Está seguro que desea eliminar el producto: " + nombre + "?", 
        "Confirmar eliminación", 
        JOptionPane.YES_NO_OPTION
    );
    
    if (confirmacion == JOptionPane.YES_OPTION) {
        eliminarProducto(nombre, categoria);
    }
});

// Agregar el botón al panel
botones.add(eliminar);
            
         
            

        JButton refresh = crearBoton("REFRESCAR");
        refresh.addActionListener(e -> {
            String seleccion = (String) comboCategorias.getSelectedItem();
            if (seleccion.equalsIgnoreCase("Todos")) {
                cargarTodasLasTablas();
            } else {
                cargarTabla(seleccion.toLowerCase());
            }
        });

        botones.add(agregar);
        botones.add(eliminar);
        botones.add(refresh);

        panel.add(botones, BorderLayout.SOUTH);

        // Cargar tabla por defecto
        comboCategorias.setSelectedItem("Todos");
    cargarTodasLasTablas();
    

      
      tablaProductos.getColumnModel().getColumn(0).setPreferredWidth(120);  // 
tablaProductos.getColumnModel().getColumn(1).setPreferredWidth(500);  // 
tablaProductos.getColumnModel().getColumn(2).setPreferredWidth(80);   // 
tablaProductos.getColumnModel().getColumn(3).setPreferredWidth(90); 

  setVisible(true);
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

    private void cargarTabla(String categoria) {
        Libreria.EntidadDAO dao = new Libreria.EntidadDAO();
        dao.setTabla(categoria);

        DefaultTableModel model = new DefaultTableModel(new Object[]{"Nombre", "Descripción", "Precio", "Categoría"}, 0);

        dao.setColumnas(Arrays.asList("nombre", "descripcion", "precio"));

        List<Libreria.EntidadGenerica> lista = dao.obtenerEntidades();

        for (Libreria.EntidadGenerica entidad : lista) {
            model.addRow(new Object[]{
                    entidad.getCampo("nombre"),
                    entidad.getCampo("descripcion"),
                    entidad.getCampo("precio"),
                    categoria
            });
        }

        tablaProductos.setModel(model);
        modeloOriginal = model;
    }

    private void cargarTodasLasTablas() {
        String[] categorias = {"hamburguesas", "acompanamientos", "bebidas", "postres", "combos"};
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Nombre", "Descripción", "Precio", "Categoría"}, 0);

        for (String categoria : categorias) {
            Libreria.EntidadDAO dao = new Libreria.EntidadDAO();
            dao.setTabla(categoria);
            dao.setColumnas(Arrays.asList("nombre", "descripcion", "precio"));

            List<Libreria.EntidadGenerica> lista = dao.obtenerEntidades();

            for (Libreria.EntidadGenerica entidad : lista) {
                model.addRow(new Object[]{
                        entidad.getCampo("nombre"),
                        entidad.getCampo("descripcion"),
                        entidad.getCampo("precio"),
                        categoria
                });
            }
        }

        tablaProductos.setModel(model);
        modeloOriginal = model;
    }

    private void filtrarTabla(String texto) {
        if (modeloOriginal == null) return;

        DefaultTableModel filtrado = new DefaultTableModel(new Object[]{"Nombre", "Descripción", "Precio", "Categoría"}, 0);

        for (int i = 0; i < modeloOriginal.getRowCount(); i++) {
            String nombre = modeloOriginal.getValueAt(i, 0).toString().toLowerCase();
            if (nombre.contains(texto.toLowerCase())) {
                filtrado.addRow(new Object[]{
                        modeloOriginal.getValueAt(i, 0),
                        modeloOriginal.getValueAt(i, 1),
                        modeloOriginal.getValueAt(i, 2),
                        modeloOriginal.getValueAt(i, 3)
                });
            }
        }

        tablaProductos.setModel(filtrado);
    }
    private void eliminarProducto(String nombre, String categoria) {
    Libreria.EntidadDAO dao = new Libreria.EntidadDAO();
    dao.setTabla(categoria);
    dao.setColumnas(Arrays.asList("nombre", "descripcion", "precio"));
    dao.setColumnaId("nombre"); // Asumiendo que 'nombre' es único por categoría
    
    try {
        if (dao.eliminarEntidad(nombre)) {
            JOptionPane.showMessageDialog(this, "Producto eliminado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            // Refrescar la tabla
            String seleccion = (String) comboCategorias.getSelectedItem();
            if (seleccion.equalsIgnoreCase("Todos")) {
                cargarTodasLasTablas();
            } else {
                cargarTabla(seleccion.toLowerCase());
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo eliminar el producto", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al eliminar el producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Productos());
    }
}

