/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package productos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import loginform.Historia;

/**
 *
 * @author Ana
 */
public class PantallaPrinU extends javax.swing.JFrame {

private Map<String, ProductoSeleccionado> carrito = new HashMap<>();
    public PantallaPrinU() {
        initComponents();

        // Configurar el frame
        setTitle("Pantalla principal");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Fondo degradado
        PanelConDegradado fondo = new PanelConDegradado();
        fondo.setLayout(null);
        setContentPane(fondo);

        // Crear panel principal translúcido sobre el fondo
        panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());
        panel.setBounds(0, 0, 800, 600); // Tamaño del panel principal
        fondo.add(panel);

        // Barra de menú real
        jMenuBar1 = new JMenuBar();
        jMenuBar1.setBackground(new Color(214, 40, 40)); // Color personalizado
        jMenu5 = new JMenu();
        jMenu5.setIcon(new ImageIcon("C:\\Users\\Ana\\Documents\\NetBeansProjects\\libreria\\LoginForm\\src\\loginform\\im.png"));
        jMenuBar1.add(jMenu5);
        setJMenuBar(jMenuBar1);

        // Personalización visual
        customizeUI();

        // Si deseas agregar imagen central, descomenta esto:
        addImageToPanel();
    }

    private void customizeUI() {
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Crear botones
        JButton btnProd = new JButton("PRODUCTOS");
        JButton btnConfiguracion = new JButton("PROMOCIONES");
        JButton btnCerrarSesion = new JButton("HISTORIA ");

        btnProd.setFocusable(false);
        btnConfiguracion.setFocusable(false);
        btnCerrarSesion.setFocusable(false);
      //  btnCerrarSesion.addActionListener(e -> System.exit(0));

        // Panel para los botones
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        botonesPanel.setOpaque(false);
        botonesPanel.add(btnProd);
        botonesPanel.add(btnConfiguracion);
        botonesPanel.add(btnCerrarSesion);

        // Panel contenedor para bajar visualmente los botones
        JPanel contenedorSuperior = new JPanel();
        contenedorSuperior.setLayout(new BoxLayout(contenedorSuperior, BoxLayout.Y_AXIS));
        contenedorSuperior.setOpaque(false);
        contenedorSuperior.add(Box.createVerticalStrut(50)); // Espacio arriba
        contenedorSuperior.add(botonesPanel);

        panel.add(contenedorSuperior, BorderLayout.NORTH);

        SwingUtilities.updateComponentTreeUI(this);
        
        btnProd.addActionListener(e -> {
           // this.dispose();
            new hamburguesas().setVisible(true); 
        });
        btnConfiguracion.addActionListener(e -> {
         
            new PromocionesGUI().setVisible(true);
        });
        btnCerrarSesion.addActionListener(e -> {
           // this.dispose();
            new Historia().setVisible(true); 
        });
    }

    private void addImageToPanel() {
        try {
            URL imageUrl = getClass().getResource("image.png");
            if (imageUrl != null) {
                ImageIcon originalIcon = new ImageIcon(imageUrl);
                Image originalImage = originalIcon.getImage();
                Image scaledImage = originalImage.getScaledInstance(750, 400, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);

                JLabel imageLabel = new JLabel(scaledIcon);
                imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

                JPanel imagePanel = new JPanel(new GridBagLayout());
                imagePanel.setOpaque(false);

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.insets = new Insets(20, 20, 20, 20);

                imagePanel.add(imageLabel, gbc);
                panel.add(imagePanel, BorderLayout.CENTER);
            } else {
                System.err.println("No se encontró la imagen en la ruta especificada");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar la imagen", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    


   

    // Clase interna para el panel con degradado
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PantallaPrinU frame = new PantallaPrinU();
            frame.setVisible(true);
        });
    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu5 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 788, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 404, Short.MAX_VALUE)
        );

        jMenuBar1.setBackground(new java.awt.Color(214, 40, 40));
        jMenuBar1.setBorder(null);

        jMenu5.setIcon(new javax.swing.ImageIcon("C:\\Users\\Ana\\Documents\\NetBeansProjects\\libreria\\LoginForm\\src\\loginform\\im.png")); // NOI18N
        jMenuBar1.add(jMenu5);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel panel;
    // End of variables declaration//GEN-END:variables
}
