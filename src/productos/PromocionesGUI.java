/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package productos;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.List;

public class PromocionesGUI extends JFrame {
    private Promociones promocionesDAO;

    public PromocionesGUI() {
        promocionesDAO = new Promociones();
        
        setTitle("Promociones de Comida Rápida");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Panel principal con degradado
        PanelConDegradado fondo = new PanelConDegradado();
        fondo.setLayout(new BorderLayout());
        add(fondo);
        
        // Panel principal con pestañas
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setOpaque(false);
        
        // Pestaña de Lanzamientos
        JPanel lanzamientosPanel = crearPanelPromociones("Nuevos Lanzamientos", promocionesDAO.obtenerLanzamientos());
        tabbedPane.addTab("Lanzamientos", lanzamientosPanel);
        
        // Pestaña de Promociones
        JPanel promocionesPanel = crearPanelPromociones("Promociones Especiales", promocionesDAO.obtenerPromociones());
        tabbedPane.addTab("Promociones", promocionesPanel);
        
        // Pestaña de Descuentos
        JPanel descuentosPanel = crearPanelDescuentos(promocionesDAO.obtenerDescuentos());
        tabbedPane.addTab("Descuentos", descuentosPanel);
        
        fondo.add(tabbedPane, BorderLayout.CENTER);
    }

    static class PanelConDegradado extends JPanel {
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

    private JPanel crearPanelPromociones(String titulo, List<Promocion> promociones) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel tituloLabel = new JLabel(titulo);
        tituloLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        tituloLabel.setForeground(new Color(0xF8F9FA));
        tituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(tituloLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 25)));
        
        if (promociones.isEmpty()) {
            JLabel vacioLabel = new JLabel("No hay " + titulo.toLowerCase() + " disponibles en este momento.");
            vacioLabel.setForeground(new Color(0xF8F9FA));
            vacioLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(vacioLabel);
        } else {
            for (Promocion promo : promociones) {
                panel.add(crearItemPromocion(promo));
                panel.add(Box.createRigidArea(new Dimension(0, 20)));
            }
        }
        
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setOpaque(false);
        contenedor.add(scrollPane, BorderLayout.CENTER);
        
        return contenedor;
    }

    private JPanel crearItemPromocion(Promocion promo) {
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BorderLayout(15, 15));
        itemPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0xF8F9FA), 2, true),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        itemPanel.setBackground(new Color(0x343A40));
        itemPanel.setMaximumSize(new Dimension(800, 150));
        
        // Panel de información
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        
        JLabel nombreLabel = new JLabel(promo.getNombre());
        nombreLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        nombreLabel.setForeground(new Color(0xF8F9FA));
        
        JTextArea descripcionArea = new JTextArea(promo.getDescripcion());
        descripcionArea.setEditable(false);
        descripcionArea.setLineWrap(true);
        descripcionArea.setWrapStyleWord(true);
        descripcionArea.setOpaque(false);
        descripcionArea.setForeground(new Color(0xE9ECEF));
        descripcionArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JPanel precioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        precioPanel.setOpaque(false);
        
        if ("promocion".equals(promo.getTipo())) {
            JLabel precioOriginal = new JLabel(String.format("$%.2f", promo.getPrecio()));
            precioOriginal.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            precioOriginal.setForeground(new Color(0xADB5BD));
            precioOriginal.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
            
            JLabel precioPromo = new JLabel(String.format("$%.2f", promo.getPrecioPromocion()));
            precioPromo.setFont(new Font("Segoe UI", Font.BOLD, 20));
            precioPromo.setForeground(new Color(0x94D82D));
            
            precioPanel.add(new JLabel("Precio: "));
            precioPanel.add(precioOriginal);
            precioPanel.add(new JLabel("→ "));
            precioPanel.add(precioPromo);
            
            double descuento = 100 - (promo.getPrecioPromocion() * 100 / promo.getPrecio());
            JLabel descuentoLabel = new JLabel(String.format("(%.0f%% OFF)", descuento));
            descuentoLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            descuentoLabel.setForeground(new Color(0xFF6B6B));
            precioPanel.add(descuentoLabel);
        } else {
            JLabel precioLabel = new JLabel(String.format("Precio: $%.2f", promo.getPrecio()));
            precioLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
            precioLabel.setForeground(new Color(0xF8F9FA));
            precioPanel.add(precioLabel);
        }
        
        infoPanel.add(nombreLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        infoPanel.add(descripcionArea);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        infoPanel.add(precioPanel);
        
        itemPanel.add(infoPanel, BorderLayout.CENTER);
        
        return itemPanel;
    }

    private JPanel crearPanelDescuentos(List<Promocion> descuentos) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel tituloLabel = new JLabel("Cupones de Descuento");
        tituloLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        tituloLabel.setForeground(new Color(0xF8F9FA));
        tituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(tituloLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 25)));
        
        if (descuentos.isEmpty()) {
            JLabel vacioLabel = new JLabel("No hay descuentos disponibles en este momento.");
            vacioLabel.setForeground(new Color(0xF8F9FA));
            vacioLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(vacioLabel);
        } else {
            for (Promocion desc : descuentos) {
                panel.add(crearItemDescuento(desc));
                panel.add(Box.createRigidArea(new Dimension(0, 20)));
            }
        }
        
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setOpaque(false);
        contenedor.add(scrollPane, BorderLayout.CENTER);
        
        return contenedor;
    }

    private JPanel crearItemDescuento(Promocion descuento) {
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BorderLayout(15, 15));
        itemPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0xF8F9FA), 2, true),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        itemPanel.setBackground(new Color(0x343A40));
        itemPanel.setMaximumSize(new Dimension(800, 120));
        
        // Panel del código de descuento
        JPanel codigoPanel = new JPanel();
        codigoPanel.setLayout(new BoxLayout(codigoPanel, BoxLayout.Y_AXIS));
        codigoPanel.setPreferredSize(new Dimension(180, 100));
        codigoPanel.setBackground(new Color(0x495057));
        codigoPanel.setBorder(new RoundBorder(10, new Color(0xF8F9FA)));
        
        JLabel codigoLabel = new JLabel(descuento.getCodigoDescuento());
        codigoLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        codigoLabel.setForeground(new Color(0x94D82D));
        codigoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel porcentajeLabel = new JLabel(descuento.getPorcentajeDescuento() + "% OFF");
        porcentajeLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        porcentajeLabel.setForeground(new Color(0xFF6B6B));
        porcentajeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        codigoPanel.add(Box.createVerticalGlue());
        codigoPanel.add(codigoLabel);
        codigoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        codigoPanel.add(porcentajeLabel);
        codigoPanel.add(Box.createVerticalGlue());
        
        // Panel de información
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        
        JLabel aplicaLabel = new JLabel("Aplica a: " + 
            (descuento.getDescripcion() != null ? descuento.getDescripcion() : "Productos seleccionados"));
        aplicaLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        aplicaLabel.setForeground(new Color(0xE9ECEF));
        
        JLabel validezLabel = new JLabel("Válido hasta: " + descuento.getFechaFin());
        validezLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        validezLabel.setForeground(new Color(0xF8F9FA));
        
        infoPanel.add(aplicaLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        infoPanel.add(validezLabel);
        
        itemPanel.add(codigoPanel, BorderLayout.WEST);
        itemPanel.add(infoPanel, BorderLayout.CENTER);
        
        return itemPanel;
    }

    // Clase para bordes redondeados
    static class RoundBorder extends AbstractBorder {
        private int radius;
        private Color color;
        
        public RoundBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }
        
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(color);
            g2d.drawRoundRect(x, y, width-1, height-1, radius, radius);
            g2d.dispose();
        }
        
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius+1, radius+1, radius+1, radius+1);
        }
        
        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.right = radius+1;
            insets.top = insets.bottom = radius+1;
            return insets;
        }
    }

    
    
}