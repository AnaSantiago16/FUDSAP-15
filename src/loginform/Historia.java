/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loginform;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Historia extends JFrame {

    private JPanel cardPanel;
    private CardLayout cardLayout;

    public Historia() {
        setTitle("Historia de Fudsap");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal con degradado mejorado
        PanelConDegradado fondo = new PanelConDegradado();
        fondo.setLayout(new BorderLayout());
        add(fondo);

        // Panel de navegación superior simplificado
        JPanel navPanel = new JPanel();
        navPanel.setOpaque(false);
        navPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));
        
        String[] navItems = {"Historia", "Datos Curiosos"};
        for (String item : navItems) {
            JButton navButton = new JButton(item);
            navButton.setFont(new Font("Arial", Font.BOLD, 16));
            navButton.setForeground(new Color(0x2B2D42));
            navButton.setBackground(new Color(0xF8F7FF));
            navButton.setFocusPainted(false);
            
            Border lineBorder = BorderFactory.createLineBorder(new Color(0x6A040F));
            Border emptyBorder = BorderFactory.createEmptyBorder(8, 20, 8, 20);
            navButton.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));
            
            navButton.addActionListener(new NavButtonListener(item));
            navPanel.add(navButton);
        }
        fondo.add(navPanel, BorderLayout.NORTH);

        // Panel de contenido con CardLayout
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setOpaque(false);

        cardPanel.add(createHistoriaPanel(), "Historia");
        cardPanel.add(createDatosCuriososPanel(), "Datos Curiosos");

        fondo.add(cardPanel, BorderLayout.CENTER);

        // Footer mejorado
        JLabel footer = new JLabel("© 2023 Fudsap Corporation - ¡Sabores que te sorprenden!", SwingConstants.CENTER);
        footer.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 12));
        footer.setForeground(new Color(0xF8F7FF));
        footer.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        fondo.add(footer, BorderLayout.SOUTH);
    }

    private JPanel createHistoriaPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);  // Aquí estaba el problema potencial - debe ser boolean
        panel.setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        
        Border lineBorder = BorderFactory.createLineBorder(new Color(0x6A040F));
        Border emptyBorder = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));
        
        // Corrección importante: usar constructor correcto para Color con transparencia
        contentPanel.setBackground(new Color(248, 247, 255, 200)); // Equivalente a 0xF8F7FF con alpha 200

        JTextArea historiaText = new JTextArea();
        historiaText.setEditable(false);
        historiaText.setLineWrap(true);
        historiaText.setWrapStyleWord(true);
        historiaText.setFont(new Font("Arial", Font.PLAIN, 14));
        historiaText.setForeground(new Color(0x2B2D42));
        historiaText.setOpaque(false);  // Otro lugar donde debe ser boolean
        historiaText.setText("En el año 1998, Richard McAllister, un ex ingeniero de software, " +
                "decidió cambiar radicalmente su vida y entrar en el mundo de la gastronomía. " +
                "Con una idea innovadora: combinar tecnología y comida rápida, nació Fudsap.\n\n" +
                "Lo que comenzó como un pequeño local en Silicon Valley, pronto se expandió " +
                "gracias a su sistema de preparación 'algoritmica' que garantiza " +
                "la misma calidad y sabor en todas sus sucursales alrededor del mundo.\n\n" +
                "Hoy, Fudsap opera en 35 países con más de 2,500 establecimientos, " +
                "siendo reconocida por su enfoque innovador y su compromiso con " +
                "la satisfacción del cliente y la sostenibilidad ambiental.");

        JScrollPane scrollPane = new JScrollPane(historiaText);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Logo mejorado
        JLabel logoLabel = new JLabel();
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
        
        BufferedImage logoImage = new BufferedImage(300, 120, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = logoImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        GradientPaint gp = new GradientPaint(0, 0, new Color(0xD62828), 300, 120, new Color(0x6A040F));
        g2d.setPaint(gp);
        g2d.fillRoundRect(0, 0, 300, 120, 30, 30);
        
        g2d.setColor(new Color(0xF8F7FF));
        g2d.setFont(new Font("Arial", Font.BOLD, 28));
        FontMetrics fm = g2d.getFontMetrics();
        String text = "FUDSAP";
        int x = (logoImage.getWidth() - fm.stringWidth(text)) / 2;
        int y = ((logoImage.getHeight() - fm.getHeight()) / 2) + fm.getAscent();
        g2d.drawString(text, x, y);
        g2d.dispose();
        
        logoLabel.setIcon(new ImageIcon(logoImage));

        panel.add(logoLabel, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createDatosCuriososPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        
        Border lineBorder = BorderFactory.createLineBorder(new Color(0x6A040F));
        Border emptyBorder = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));
        
        contentPanel.setBackground(new Color(248, 247, 255, 200));

        JList<String> datosList = new JList<>(new String[]{
                "• El nombre Fudsap viene de 'Food' + 'SAP' (sistema de planificación de recursos)",
                "• Fue la primera cadena en implementar kioskos de auto-pedido con reconocimiento facial",
                "• Todos los ingredientes son trazados mediante blockchain para garantizar calidad",
                "• La salsa 'Binary Sauce' tiene exactamente 256 ingredientes (como los valores de un byte)",
                "• Cada nuevo producto es probado por un panel de catadores y un algoritmo de IA"
        });
        datosList.setFont(new Font("Arial", Font.PLAIN, 14));
        datosList.setForeground(new Color(0x2B2D42));
        datosList.setBackground(new Color(0, 0, 0, 0));
        datosList.setSelectionBackground(new Color(0xD62828));
        datosList.setSelectionForeground(Color.WHITE);
        datosList.setCellRenderer(new TransparentListCellRenderer());

        JScrollPane scrollPane = new JScrollPane(datosList);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        contentPanel.add(scrollPane, BorderLayout.CENTER);
        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    class NavButtonListener implements ActionListener {
        private String panelName;

        public NavButtonListener(String panelName) {
            this.panelName = panelName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(cardPanel, panelName);
        }
    }

    static class TransparentListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, 
                                                   int index, boolean isSelected, 
                                                   boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            setOpaque(isSelected);
            setBackground(isSelected ? new Color(0xD62828) : new Color(0, 0, 0, 0));
            setForeground(isSelected ? Color.WHITE : new Color(0x2B2D42));
            return this;
        }
    }

    static class PanelConDegradado extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            GradientPaint gp = new GradientPaint(
                0, 0, new Color(0x2B2D42), 
                getWidth(), getHeight(), new Color(0x6A040F)
            );
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            Historia historia = new Historia();
            historia.setVisible(true);
        });
    }
}