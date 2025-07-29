/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package productos;

/**
 *
 * @author Ana
 */
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import loginform.loginUsuario;

public class Pago extends JFrame {
   private final double subtotal;
private final double iva;
private final double totalConIVA;
private final Map<String, ProductoSeleccionado> carrito;

public Pago(double subtotal, double iva, double totalConIVA, Map<String, ProductoSeleccionado> carrito) {
    this.subtotal = subtotal;
    this.iva = iva;
    this.totalConIVA = totalConIVA;
    this.carrito = carrito;
    
        setTitle("MÉTODO DE PAGO");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        PanelConDegradado fondo = new PanelConDegradado();
        fondo.setLayout(new BorderLayout());
        setContentPane(fondo);

        JLabel titulo = new JLabel("MÉTODO DE PAGO", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(new EmptyBorder(0, 0, 15, 0));
        fondo.add(titulo, BorderLayout.NORTH);

        JPanel panelOpciones = new JPanel(new GridLayout(3, 1, 10, 10));
        panelOpciones.setBorder(new EmptyBorder(10, 30, 20, 30));
        panelOpciones.setOpaque(false);

        Color cremaTranslucido = new Color(255, 243, 224, 25);
        Color cremaHover = new Color(255, 243, 224, 80);

        JButton btnEfectivo = new JButton("Efectivo");
        estilizarBoton(btnEfectivo, cremaTranslucido, cremaHover, 14);
        btnEfectivo.addActionListener(e -> {
            Efectivo.procesarPagoEfectivo(subtotal, iva, totalConIVA, carrito);
            dispose();
        });

        JButton btnTransferencia = new JButton("Transferencia");
        estilizarBoton(btnTransferencia, cremaTranslucido, cremaHover, 14);
        btnTransferencia.addActionListener(e -> {
            Transferencia.procesarPagoTransferencia(subtotal, iva, totalConIVA, carrito);
            dispose();
        });

        JButton btnCancelar = new JButton("Cancelar");
        estilizarBoton(btnCancelar, cremaTranslucido, cremaHover, 14);
        btnCancelar.addActionListener(e -> dispose());

        panelOpciones.add(btnEfectivo);
        panelOpciones.add(btnTransferencia);
        panelOpciones.add(btnCancelar);
        fondo.add(panelOpciones, BorderLayout.CENTER);

        JPanel panelTotal = new JPanel();
        panelTotal.setOpaque(false);
        panelTotal.setBorder(new EmptyBorder(10, 0, 0, 0));
        JLabel lblTotal = new JLabel("Total: $" + String.format("%.2f", totalConIVA));
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTotal.setForeground(Color.WHITE);
        panelTotal.add(lblTotal);
        fondo.add(panelTotal, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void estilizarBoton(JButton boton, Color colorFondo, Color colorHover, int tamañoFuente) {
        boton.setFont(new Font("Segoe UI", Font.BOLD, tamañoFuente));
        boton.setForeground(Color.WHITE);
        boton.setBackground(colorFondo);
        boton.setFocusPainted(false);
        boton.setOpaque(false);
        boton.setContentAreaFilled(true);
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 0, 0, 40)),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorHover);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorFondo);
            }
        });
    }
}