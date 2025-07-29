/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package productos;

import conexion.Libreria;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import loginform.Pantallaprin;

/**
 *
 * @author Ana
 */
public class Bebidas extends javax.swing.JFrame {
private Map<String, ProductoSeleccionado> carrito = new HashMap<>();
private JTextField txtBuscar;
private DefaultTableModel modeloOriginal;
private JButton btnNext;

    public Bebidas() {
        this(new HashMap<>());
    }

    public Bebidas(Map<String, ProductoSeleccionado> carrito) {
        this.carrito = carrito;
        initComponents();
    setTitle("");
    setSize(950, 700);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    // Fondo degradado
    PanelConDegradado fondo = new PanelConDegradado();
    fondo.setLayout(null);
    setContentPane(fondo);

    // Panel principal translúcido
    JPanel panel = new JPanel();
    panel.setOpaque(false);
    panel.setLayout(new BorderLayout());
    panel.setBounds(20, 60, 900, 500); // espacio arriba para botones
    fondo.add(panel);
    
    

    // Panel de botones arriba
    JPanel panelBotones = new JPanel();
    panelBotones.setOpaque(false);
    panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
    panelBotones.setBounds(20, 10, 900, 50); // arriba del panel principal
    fondo.add(panelBotones);
//panelBotones.add(btn1);


    btn1 = crearBoton("HAMBURGUESAS");
    
    btn1.addActionListener(e -> {
            this.dispose();
            new hamburguesas(carrito).setVisible(true); 
        });
    btn2 = crearBoton("BEBIDAS");
     btn2.setBackground(new Color(139, 69, 19));
    btn2.addActionListener(e -> {
            this.dispose();
            new Bebidas(carrito).setVisible(true); 
        });
    btn3 = crearBoton("ACOMPAÑAMIENTOS");
    btn3.addActionListener(e -> {
            this.dispose();
            new Acompanamientos(carrito).setVisible(true); 
        });
    btn4 = crearBoton("POSTRES");
    btn4.addActionListener(e -> {
            this.dispose();
            new Postres(carrito).setVisible(true); 
        });
    btn5 = crearBoton("COMBOS");
    btn5.addActionListener(e -> {
            this.dispose();
            new Combos(carrito).setVisible(true); 
        });

    panelBotones.add(btn1);
    panelBotones.add(btn2);
    panelBotones.add(btn3);
    panelBotones.add(btn4);
    panelBotones.add(btn5);
tablaPersonas = new JTable();
    tablaPersonas.setFillsViewportHeight(true);
    tablaPersonas.setRowHeight(28);
    tablaPersonas.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    tablaPersonas.setForeground(Color.BLACK);
    //tablaPersonas.setBackground(new Color(255, 255, 255, 230));

    JTableHeader header = tablaPersonas.getTableHeader();
header.setFont(new Font("Segoe UI", Font.BOLD, 15));
header.setBackground(new Color(240, 240, 240));
header.setForeground(new Color(50, 50, 50));
    
   tablaPersonas.setSelectionBackground(new Color(214, 40, 40));
    tablaPersonas.setSelectionForeground(Color.WHITE);

    JScrollPane scrollPane = new JScrollPane(tablaPersonas);
    scrollPane.setOpaque(false);
    scrollPane.getViewport().setOpaque(false);
    panel.add(scrollPane, BorderLayout.CENTER);

    // Botones inferiores (si quieres agregar más abajo)
    JPanel panelInferior = new JPanel(new BorderLayout()); 
panelInferior.setOpaque(false);



// BOTÓN DERECHO (NEXT)

// === Botón "PEDIDO" === 
btnPedido = new JButton("PEDIDO");
btnPedido.setFont(new Font("Segoe UI", Font.BOLD, 16));
btnPedido.setBackground(new Color(214, 40, 40));
btnPedido.setForeground(Color.WHITE);
btnPedido.setFocusPainted(false);
btnPedido.setCursor(new Cursor(Cursor.HAND_CURSOR));
btnPedido.setPreferredSize(new Dimension(140, 40));

btnPedido.addActionListener(e -> {
            new Pedido(carrito, null).setVisible(true);
        });
// Panel centrado para el botón
JPanel centro = new JPanel(new FlowLayout(FlowLayout.CENTER));
centro.setOpaque(false);
centro.add(btnPedido);

// Agrega los paneles (izquierda, centro, derecha)
panelInferior.add(centro, BorderLayout.CENTER);

JButton btnNext = new JButton(new ImageIcon("C:\\Users\\Ana\\Downloads\\f1.png"));
btnNext.setContentAreaFilled(false);
btnNext.setBorderPainted(false);
btnNext.setFocusPainted(false);

// Etiqueta debajo del botón derecho
JLabel lblIma = new JLabel("Next");
lblIma.setFont(new Font("Segoe UI", Font.BOLD, 14));
lblIma.setForeground(Color.WHITE);
lblIma.setHorizontalAlignment(SwingConstants.CENTER);
btnNext.addActionListener(e -> {
           // this.dispose();
            new Acompanamientos(carrito).setVisible(true); 
        });
// Panel vertical derecho
JPanel panelVerticalDerecha = new JPanel();
panelVerticalDerecha.setOpaque(false);
panelVerticalDerecha.setLayout(new BoxLayout(panelVerticalDerecha, BoxLayout.Y_AXIS));
panelVerticalDerecha.add(btnNext);
panelVerticalDerecha.add(Box.createVerticalStrut(4));
panelVerticalDerecha.add(lblIma);

// Alinear a la derecha
JPanel derecha = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
derecha.setOpaque(false);
derecha.add(panelVerticalDerecha);
panelInferior.add(derecha, BorderLayout.EAST);


// BOTÓN IZQUIERDO (PREV o BACK)
JButton btnBack = new JButton(new ImageIcon("C:\\Users\\Ana\\Downloads\\f2.png")); // Usa una flecha hacia la izquierda
btnBack.setContentAreaFilled(false);
btnBack.setBorderPainted(false);
btnBack.setFocusPainted(false);

// Etiqueta debajo del botón izquierdo
JLabel lblBack = new JLabel("Back");
lblBack.setFont(new Font("Segoe UI", Font.BOLD, 14));
lblBack.setForeground(Color.WHITE);
lblBack.setHorizontalAlignment(SwingConstants.CENTER);
 btnBack.addActionListener(e -> {
           // this.dispose();
            new hamburguesas(carrito).setVisible(true); 
        });
// Panel vertical izquierdo
JPanel panelVerticalIzquierda = new JPanel();
panelVerticalIzquierda.setOpaque(false);
panelVerticalIzquierda.setLayout(new BoxLayout(panelVerticalIzquierda, BoxLayout.Y_AXIS));
panelVerticalIzquierda.add(btnBack);
panelVerticalIzquierda.add(Box.createVerticalStrut(4));
panelVerticalIzquierda.add(lblBack);

// Alinear a la izquierda
JPanel izquierda = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
izquierda.setOpaque(false);
izquierda.add(panelVerticalIzquierda);
panelInferior.add(izquierda, BorderLayout.WEST);

// AÑADIR PANEL INFERIOR AL PANEL PRINCIPAL
panel.add(panelInferior, BorderLayout.SOUTH);

modeloOriginal = new DefaultTableModel(new Object[]{"Nombre", "Descripción", "Precio", ""}, 0) {
    @Override
    public boolean isCellEditable(int row, int column) {
        return column == 3;
    }
};
tablaPersonas.setModel(modeloOriginal);


    cargarTabla();
    
    tablaPersonas.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        int fila = tablaPersonas.getSelectedRow();
        int columna = tablaPersonas.columnAtPoint(e.getPoint());

        if (fila >= 0 && columna == 3) {
            String nombre = tablaPersonas.getValueAt(fila, 0).toString();
            double precio = Double.parseDouble(tablaPersonas.getValueAt(fila, 2).toString());

            
                if (carrito.containsKey(nombre)) {
                    carrito.get(nombre).aumentarCantidad();
                } else {
                    carrito.put(nombre, new ProductoSeleccionado(nombre, 1, precio));
                }
                mostrarNotificacion(nombre + " agregado. Cantidad: " + carrito.get(nombre).getCantidad(), new Color(76, 175, 80));
            
            
        }
    }
});
// Panel para caja de búsqueda (debajo de la tabla)
JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
panelBusqueda.setOpaque(false);

// Etiqueta "BUSCAR:" con letra blanca y fuente moderna
JLabel lblBuscar = new JLabel("BUSCAR:");
lblBuscar.setFont(new Font("Segoe UI", Font.BOLD, 16));
lblBuscar.setForeground(Color.WHITE); // Letra blanca

// Campo de texto estilizado
txtBuscar = new JTextField(25);
txtBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
txtBuscar.setToolTipText("Buscar producto...");
txtBuscar.setBackground(new Color(255, 255, 255));
txtBuscar.setForeground(Color.BLACK);
txtBuscar.setBorder(BorderFactory.createLineBorder(new Color(214, 40, 40), 2));
txtBuscar.setCaretColor(Color.BLACK);

// Agregar componentes al panel
panelBusqueda.add(lblBuscar);
panelBusqueda.add(txtBuscar);


// Añadir búsqueda al final del panel
panel.add(panelBusqueda, BorderLayout.NORTH);

// Listener para filtrar productos al escribir
txtBuscar.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
    public void insertUpdate(javax.swing.event.DocumentEvent e) {
        filtrarTabla(txtBuscar.getText());
    }

    public void removeUpdate(javax.swing.event.DocumentEvent e) {
        filtrarTabla(txtBuscar.getText());
    }

    public void changedUpdate(javax.swing.event.DocumentEvent e) {
        filtrarTabla(txtBuscar.getText());
    }
});


    }
private void mostrarNotificacion(String mensaje, Color color) {
    JLabel etiqueta = new JLabel(mensaje, SwingConstants.CENTER);
    etiqueta.setOpaque(true);
    etiqueta.setBackground(new Color(0, 0, 0, 0)); // completamente transparente
    etiqueta.setOpaque(true);
    etiqueta.setForeground(Color.WHITE);
    etiqueta.setFont(new Font("Segoe UI", Font.BOLD, 14));
    etiqueta.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    etiqueta.setBounds(getWidth() / 2 - 150, getHeight() - 120, 300, 40);

    getLayeredPane().add(etiqueta, JLayeredPane.POPUP_LAYER);

    // Temporizador para desaparecer
    Timer timer = new Timer(2000, e -> {
        getLayeredPane().remove(etiqueta);
        getLayeredPane().repaint();
    });
    timer.setRepeats(false);
    timer.start();
}


    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(new Color(214, 40, 40));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        return btn;
    }
//rivate DefaultTableModel model;
    
private void cargarTabla() { 
    Libreria.EntidadDAO dao = new Libreria.EntidadDAO();
    dao.setTabla("bebidas");
    dao.setColumnas(Arrays.asList("nombre", "descripcion", "precio"));
    List<Libreria.EntidadGenerica> lista = dao.obtenerEntidades();

    // Crear modelo con 4 columnas
    DefaultTableModel model = new DefaultTableModel(new Object[]{"Nombre", "Descripción", "Precio",""}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 3; // solo se puede editar la columna del botón
        }
    };

    // Asignar el modelo antes de cualquier acceso a columnas
    tablaPersonas.setModel(model);
    tablaPersonas.getColumnModel().getColumn(0).setPreferredWidth(200); // ❌ MOVER ESTO
tablaPersonas.getColumnModel().getColumn(1).setPreferredWidth(400); 
tablaPersonas.getColumnModel().getColumn(2).setPreferredWidth(40);  
tablaPersonas.getColumnModel().getColumn(3).setPreferredWidth(5);


    // Cargar ícono
    URL location = getClass().getResource("/productos/imas.png");
    if (location == null) {
        System.err.println("❌ Imagen no encontrada en /productos/imas.png");
        return;
    }

    ImageIcon icono = new ImageIcon(location);
    Image img = icono.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
    ImageIcon iconoEscalado = new ImageIcon(img);

    // Agregar filas con botón
    for (Libreria.EntidadGenerica entidad : lista) {
        JButton boton = new JButton(iconoEscalado);
        boton.setToolTipText("");
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setFocusPainted(false);

        model.addRow(new Object[]{
            entidad.getCampo("nombre"),
            entidad.getCampo("descripcion"),
            entidad.getCampo("precio"),
            boton
        });
    }

    // Verificar que la columna del botón existe antes de usarla
    if (tablaPersonas.getColumnModel().getColumnCount() > 3) {
        tablaPersonas.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
    }
    
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

        tablaPersonas.setModel(filtrado);
    }
    



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaPersonas = new javax.swing.JTable();
        btn2 = new javax.swing.JButton();
        btn3 = new javax.swing.JButton();
        btn4 = new javax.swing.JButton();
        btn5 = new javax.swing.JButton();
        btn1 = new javax.swing.JButton();
        btnPedido = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tablaPersonas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tablaPersonas);

        btn2.setText("jButton2");

        btn3.setText("jButton3");

        btn4.setText("jButton4");

        btn5.setText("jButton5");

        btn1.setText("jButton1");

        btnPedido.setText("jButton1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(106, 106, 106)
                        .addComponent(btn1)
                        .addGap(18, 18, 18)
                        .addComponent(btn2)
                        .addGap(18, 18, 18)
                        .addComponent(btn3)
                        .addGap(574, 574, 574)
                        .addComponent(btn4)
                        .addGap(18, 18, 18)
                        .addComponent(btn5))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(315, 315, 315)
                        .addComponent(btnPedido))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 562, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(87, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn1)
                    .addComponent(btn2)
                    .addComponent(btn3)
                    .addComponent(btn4)
                    .addComponent(btn5))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(btnPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Bebidas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Bebidas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Bebidas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Bebidas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Bebidas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn1;
    private javax.swing.JButton btn2;
    private javax.swing.JButton btn3;
    private javax.swing.JButton btn4;
    private javax.swing.JButton btn5;
    private javax.swing.JButton btnPedido;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaPersonas;
    // End of variables declaration//GEN-END:variables
}
