/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package productos;

/**
 *
 * @author Ana
 */

import conexion.Libreria;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class AgregarProducto extends JFrame {
    private Libreria.EntidadDAO productoDAO;

    public AgregarProducto() {
        configurarDAO();

        setTitle("Agregar Producto");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout()) {
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
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 5, 0);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("Agregar Producto");
        titulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);

        JLabel lblNombre = crearLabel("Nombre:");
        JTextField txtNombre = crearTextField();

        JLabel lblDescripcion = crearLabel("Descripción:");
        JTextArea txtDescripcion = new JTextArea(3, 20);
        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
        scrollDescripcion.setMinimumSize(new Dimension(200, 60));
        scrollDescripcion.setPreferredSize(new Dimension(400, 60));
        scrollDescripcion.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 15));
        scrollDescripcion.setBackground(new Color(255, 255, 255, 180));
        scrollDescripcion.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        JLabel lblPrecio = crearLabel("Precio:");
        JTextField txtPrecio = crearTextField();

        JLabel lblCategoria = crearLabel("Categoría:");
        JComboBox<String> cmbCategoria = new JComboBox<>(new String[]{
            "hamburguesas", "bebidas", "acompanamientos", "postres", "combos"
        });
        cmbCategoria.setMinimumSize(new Dimension(200, 35));
        cmbCategoria.setPreferredSize(new Dimension(400, 35));
        cmbCategoria.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 15));
        cmbCategoria.setBackground(new Color(255, 255, 255, 180));
        cmbCategoria.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        JButton btnAgregar = new JButton("Agregar");
        estiloBoton(btnAgregar);

        JButton btnCancelar = new JButton("Cancelar");
        estiloBoton(btnCancelar);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        panel.add(lblNombre, gbc);

        gbc.gridy = 2;
        panel.add(txtNombre, gbc);

        gbc.gridy = 3;
        panel.add(lblDescripcion, gbc);

        gbc.gridy = 4;
        panel.add(scrollDescripcion, gbc);

        gbc.gridy = 5;
        panel.add(lblPrecio, gbc);

        gbc.gridy = 6;
        panel.add(txtPrecio, gbc);

        gbc.gridy = 7;
        panel.add(lblCategoria, gbc);

        gbc.gridy = 8;
        panel.add(cmbCategoria, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setOpaque(false);
        panelBotones.add(btnCancelar);
        panelBotones.add(btnAgregar);

        gbc.gridy = 9;
        gbc.gridwidth = 2;
        panel.add(panelBotones, gbc);

       btnAgregar.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String precioStr = txtPrecio.getText().trim();
        String categoria = (String) cmbCategoria.getSelectedItem();

        if (nombre.isEmpty() || descripcion.isEmpty() || precioStr.isEmpty()) {
            mostrarError("Todos los campos son obligatorios");
            return;
        }

        try {
            // Convertir el precio a double primero
            double precio = Double.parseDouble(precioStr);
            if (precio <= 0) {
                mostrarError("El precio debe ser mayor que cero");
                return;
            }

            // Configurar la tabla según la categoría seleccionada
            String nombreTabla = categoria.toLowerCase();
            productoDAO.setTabla(nombreTabla);
            
            // Configurar columnas (asumiendo que todas las tablas tienen la misma estructura)
            List<String> columnas = Arrays.asList("nombre", "descripcion", "precio");
            productoDAO.setColumnas(columnas);
            productoDAO.setColumnaId("id");

            // Verificar si el producto ya existe en la tabla específica
            List<Libreria.EntidadGenerica> existentes = productoDAO.buscarPorCampo("nombre", nombre);
            
            if (existentes != null && !existentes.isEmpty()) {
                mostrarError("Ya existe un producto con ese nombre en la categoría " + categoria);
                return;
            }

            // Crear entidad y guardar
            Libreria.EntidadGenerica producto = new Libreria.EntidadGenerica();
            producto.setCampo("nombre", nombre);
            producto.setCampo("descripcion", descripcion);
            
            // Enviar el precio como número, no como String
            producto.setCampo("precio", precio);  // <-- Cambio clave aquí
            
            boolean resultado = productoDAO.insertarEntidad(producto);

            if (resultado) {
                mostrarExito("Producto agregado exitosamente a la categoría " + categoria);
                limpiarCampos(txtNombre, txtDescripcion, txtPrecio);
            } else {
                mostrarError("Error al agregar el producto");
            }

        } catch (NumberFormatException ex) {
            mostrarError("El precio debe ser un número válido (ej: 5.99 o 7,50)");
        } catch (Exception ex) {
            mostrarError("Error inesperado: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
});

        btnCancelar.addActionListener(e -> dispose());

        add(panel);
    }

    private void configurarDAO() {
        // 1. Configurar conexión
        Libreria.Conexion.getInstancia().setParametros(
            "postgres",    // usuario
            "AnaSanty16",  // contraseña
            "Datos",      // nombre de la BD
            "localhost",   // host
            "5432"         // puerto
        );
        
        // 2. Configurar DAO (la tabla específica se configurará al agregar)
        productoDAO = new Libreria.EntidadDAO();
    }
    
    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        label.setForeground(Color.WHITE);
        return label;
    }
    
    private JTextField crearTextField() {
        JTextField textField = new JTextField(20);
        textField.setMinimumSize(new Dimension(200, 35));
        textField.setPreferredSize(new Dimension(400, 35));
        textField.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 15));
        textField.setBackground(new Color(255, 255, 255, 180));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return textField;
    }
    
    private void estiloBoton(JButton boton) {
        boton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        boton.setBackground(new Color(255, 255, 255, 150));
        boton.setForeground(Color.BLACK);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
    }
    
    private void limpiarCampos(JComponent... componentes) {
        for (JComponent componente : componentes) {
            if (componente instanceof JTextField) {
                ((JTextField) componente).setText("");
            } else if (componente instanceof JTextArea) {
                ((JTextArea) componente).setText("");
            }
        }
    }
    
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    private void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AgregarProducto agregarProd = new AgregarProducto();
            agregarProd.setVisible(true);
        });
    }
}