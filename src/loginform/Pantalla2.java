/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loginform;


import Empleado.Personas;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import productos.Productos;

public class Pantalla2 extends JFrame {

    public Pantalla2() {
        setTitle("Panel Principal");
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel principal con fondo degradado
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(0xD62828), 0, getHeight(), new Color(0x6A040F));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Panel superior con botones
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        topPanel.setOpaque(false);

        // Botones
        //JButton btnRegistrarCliente = new JButton("Registrar Cliente");
        JButton btnInicio = new JButton("INICIO");
       // JButton btnListaEmpleados = new JButton("Empleados");
        JButton btnListaClientes = new JButton("EMPLEADOS Y CLIENTES");
        JButton btnProductos = new JButton("PRODUCTOS");

        JButton[] botones = {
           btnInicio,
             btnListaClientes, btnProductos
        };

        for (JButton btn : botones) {
            btn.setPreferredSize(new Dimension(170, 40));
            btn.setBackground(new Color(255, 243, 224, 220));
            btn.setForeground(new Color(0x6A040F));
            btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btn.setFocusPainted(false);
            topPanel.add(btn);
        }

        // Acciones de los botones
       

       
        btnInicio.addActionListener(e -> {
            //this.dispose();
            new Pantallaprin().setVisible(true);
        });

        btnListaClientes.addActionListener(e -> {
           // this.dispose();
            new Personas().setVisible(true);
        });

        btnProductos.addActionListener(e -> {
           // this.dispose();
            new Productos().setVisible(true);
        });

        panel.add(topPanel, BorderLayout.NORTH);

        // Panel central vacío
        panel.add(Box.createVerticalGlue(), BorderLayout.CENTER);

        // Panel inferior con imagen
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setOpaque(false);

        try {
            BufferedImage originalImage = ImageIO.read(getClass().getResource("imag1.png"));
            Image scaledImage = originalImage.getScaledInstance(700, 380, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(scaledImage);

            JLabel imageLabel = new JLabel(imageIcon);
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imagePanel.add(imageLabel, BorderLayout.CENTER);
            imagePanel.add(Box.createVerticalStrut(0), BorderLayout.SOUTH);
        } catch (IOException e) {
            JLabel errorLabel = new JLabel("Imagen no encontrada");
            errorLabel.setForeground(Color.WHITE);
            errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imagePanel.add(errorLabel, BorderLayout.CENTER);
        }

        panel.add(imagePanel, BorderLayout.SOUTH);
        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Pantalla2().setVisible(true);
        });
    }
}
