/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package productos;



import Empleado.Venta;
import Empleado.loginEmpleado;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.TableModelEvent;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import calculadora.CalculadoraImpuestos;
import loginform.loginUsuario;
import static loginform.loginUsuario.correoUsuario;

public class Pedido extends JFrame {

    public Pedido(Map<String, ProductoSeleccionado> carrito, Object par1) {
        setTitle("Pedido");
        setSize(700, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        PanelConDegradado fondo = new PanelConDegradado();
        fondo.setLayout(new BorderLayout());
        setContentPane(fondo);
      
   
    
        JPanel panelCentral = new JPanel(new BorderLayout(10, 10));
        panelCentral.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xD62828), 2),
                new EmptyBorder(10, 10, 10, 10)
        ));
        fondo.add(panelCentral, BorderLayout.CENTER);

        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 4;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return (columnIndex == 4) ? JButton.class : Object.class;
            }
        };

        model.setColumnIdentifiers(new Object[]{"Producto", "Cantidad", "Precio Unitario", "Total", ""});

        final JLabel lblSubtotal = new JLabel();
        final JLabel lblIVA = new JLabel();
        final JLabel lblTotalConIVA = new JLabel();
        final JTable tabla = new JTable(model);

        for (ProductoSeleccionado p : carrito.values()) {
            double totalProducto = p.getCantidad() * p.getPrecioUnitario();
            model.addRow(new Object[]{
                    p.getNombre(),
                    p.getCantidad(),
                    p.getPrecioUnitario(),
                    totalProducto,
                    "Eliminar"
            });
        }

        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabla.setRowHeight(28);
        tabla.setShowGrid(false);
        tabla.setIntercellSpacing(new Dimension(0, 0));
        tabla.setSelectionBackground(new Color(0xFFB4A2));
        tabla.setSelectionForeground(Color.BLACK);
         
    tabla.setFillsViewportHeight(true);
    tabla.setRowHeight(28);
    tabla.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    tabla.setForeground(Color.BLACK);
    tabla.setBackground(new Color(255, 255, 255, 230));

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panelCentral.add(scrollPane, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setOpaque(false);

        JPanel panelTotales = new JPanel();
        panelTotales.setLayout(new BoxLayout(panelTotales, BoxLayout.Y_AXIS));
        panelTotales.setOpaque(false);

        lblSubtotal.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblIVA.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblTotalConIVA.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTotalConIVA.setForeground(new Color(0xD62828));

        JLabel lblNota = new JLabel("Nota: los precios incluyen IVA del 16%");
        lblNota.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblNota.setForeground(Color.DARK_GRAY);

        panelTotales.add(lblSubtotal);
        panelTotales.add(lblIVA);
        panelTotales.add(lblTotalConIVA);
        panelTotales.add(lblNota);

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBoton.setOpaque(false);
        JButton btnPagar = new JButton("Pagar ahora");
        btnPagar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnPagar.setBackground(new Color(0xD62828));
        btnPagar.setForeground(Color.WHITE);
        btnPagar.setFocusPainted(false);
        btnPagar.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        btnPagar.addActionListener(e -> {
    double subtotal = carrito.values().stream()
            .mapToDouble(p -> p.getCantidad() * p.getPrecioUnitario())
            .sum();

    double iva = CalculadoraImpuestos.calcularIVA(subtotal);
    double totalConIVA = subtotal + iva;

    if ("santy18belen@gmail.com".equalsIgnoreCase(loginUsuario.correoUsuario)) {
        new Venta().setVisible(true);
    } else {
        new Pago(subtotal, iva, totalConIVA, carrito).setVisible(true);
    }

    dispose();
});



        panelBoton.add(btnPagar);
        panelInferior.add(panelTotales, BorderLayout.CENTER);
        panelInferior.add(panelBoton, BorderLayout.SOUTH);
        panelCentral.add(panelInferior, BorderLayout.SOUTH);

        model.addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 1) {
                int row = e.getFirstRow();
                try {
                    int nuevaCantidad = Integer.parseInt(model.getValueAt(row, 1).toString());
                    if (nuevaCantidad < 1) nuevaCantidad = 1;

                    double precio = Double.parseDouble(model.getValueAt(row, 2).toString());
                    double nuevoTotal = nuevaCantidad * precio;
                    model.setValueAt(nuevoTotal, row, 3);

                    String producto = model.getValueAt(row, 0).toString();
                    carrito.get(producto).setCantidad(nuevaCantidad);

                    calcularTotales(model, lblSubtotal, lblIVA, lblTotalConIVA);
                } catch (NumberFormatException ex) {
                    model.setValueAt(1, row, 1);
                }
            }
        });

        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tabla.rowAtPoint(e.getPoint());
                int col = tabla.columnAtPoint(e.getPoint());

                if (col == 4) {
                    String nombre = model.getValueAt(row, 0).toString();
                    carrito.remove(nombre);
                    model.removeRow(row);
                    calcularTotales(model, lblSubtotal, lblIVA, lblTotalConIVA);
                }
            }
        });

        calcularTotales(model, lblSubtotal, lblIVA, lblTotalConIVA);

        setVisible(true);
    }

    private double calcularTotales(DefaultTableModel model, JLabel lblSubtotal, JLabel lblIVA, JLabel lblTotalIVA) {
        double subtotal = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            Object val = model.getValueAt(i, 3);
            if (val != null) {
                subtotal += Double.parseDouble(val.toString());
            }
        }

        double iva = CalculadoraImpuestos.calcularIVA(subtotal);
        double totalConIVA = subtotal + iva;

        lblSubtotal.setText("Subtotal: $" + String.format("%.2f", subtotal));
        lblIVA.setText("IVA (16%): $" + String.format("%.2f", iva));
        lblTotalIVA.setText("Total con IVA: $" + String.format("%.2f", totalConIVA));

        return subtotal;
    }


 

    // Opción 2: Constructor vacío solo para pruebas o evitar error en el main
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
