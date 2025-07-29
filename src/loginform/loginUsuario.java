/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package loginform;


import conexion.Libreria.Conexion;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.regex.Pattern;
import productos.PantallaPrinU;

public class loginUsuario extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtPass;
    JLabel er;
    public static String correoUsuario; 
    
    public loginUsuario() {
        setTitle("Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Fondo degradado
        PanelConDegradado fondo = new PanelConDegradado();
        fondo.setLayout(null);
        add(fondo);

        // Panel translúcido
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBounds(50, 60, 300, 150);
        loginPanel.setBackground(new Color(255, 243, 224, 25)); // translúcido
        loginPanel.setBorder(BorderFactory.createLineBorder(new Color(0xD62828), 1));
        fondo.add(loginPanel);

        // Icono
        JLabel icono = new JLabel("\uD83D\uDCF7", SwingConstants.CENTER);
        icono.setFont(new Font("Segoe UI", Font.PLAIN, 40));
        icono.setBounds(115, 10, 70, 50);
        icono.setForeground(Color.WHITE);
        loginPanel.add(icono);

        // Correo
        JLabel lblCorreo = new JLabel("Correo Electrónico");
        lblCorreo.setBounds(30, 60, 240, 20);
        lblCorreo.setForeground(Color.WHITE);
        lblCorreo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        loginPanel.add(lblCorreo);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(30, 80, 240, 30);
        txtUsuario.setBackground(new Color(0xF77F00));
        txtUsuario.setForeground(Color.WHITE);
        txtUsuario.setCaretColor(Color.WHITE);
        txtUsuario.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        loginPanel.add(txtUsuario);

        // ENTER para login
        txtUsuario.addActionListener(e -> validarYLogin());

        // Mensaje de error
        er = new JLabel("");
        er.setBounds(30, 120, 240, 20);
        er.setForeground(Color.RED);
        er.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        loginPanel.add(er);
    }

    private void validarYLogin() {
        String email = txtUsuario.getText().trim();

        if (email.isEmpty()) {
            er.setText("Por favor, ingresa tu correo.");
            return;
        }

        if (!validarEmail(email)) {
            er.setText("Correo no válido.");
            txtUsuario.requestFocus();
            return;
        }

        // Validación en base de datos
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/Datos", "postgres", "AnaSanty16");
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM registro WHERE correo = ?")) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                // GUARDAR el correo en variable global
                iniciarSesion(email);

                new PantallaPrinU().setVisible(true);  // Reemplaza con tu clase real
                this.dispose();
            } else {
                er.setText("Correo no registrado.");
                new RegistroPersona().setVisible(true);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al conectar con la base de datos:\n" + ex.getMessage(),
                    "Error de conexión", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validarEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    // ✅ Método que guarda el correo ingresado globalmente
    public void iniciarSesion(String correo) {
        loginUsuario.correoUsuario = correo;
        System.out.println("Correo guardado: " + correo);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new loginUsuario().setVisible(true);
        });
    }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 855, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 520, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panel;
    // End of variables declaration//GEN-END:variables
}
