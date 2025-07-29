/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Empleado;


import captcha.CaptchaPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import loginform.Pantalla2;
import productos.PantallaPrinU;

public class loginEmpleado extends javax.swing.JFrame {
    public static String correoEmpleado;
    private JTextField txtUsuario;
    private JPasswordField txtPass;
    private JLabel er;
    private CaptchaPanel captchaPanel;
    private JTextField txtCaptcha;

    public loginEmpleado() {
        setTitle("Login Empleado");
        setSize(400, 650); // Aumentamos el tamaño para el CAPTCHA
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Fondo degradado
        PanelConDegradado fondo = new PanelConDegradado();
        fondo.setLayout(null);
        add(fondo);

        // Panel translúcido
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBounds(50, 60, 300, 500); // Aumentamos la altura
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

        // Contraseña
        JLabel lblPass = new JLabel("Contraseña");
        lblPass.setBounds(30, 120, 240, 20);
        lblPass.setForeground(Color.WHITE);
        lblPass.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        loginPanel.add(lblPass);

        txtPass = new JPasswordField();
        txtPass.setBounds(30, 140, 240, 30);
        txtPass.setBackground(new Color(0xF77F00));
        txtPass.setForeground(Color.WHITE);
        txtPass.setCaretColor(Color.WHITE);
        txtPass.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        loginPanel.add(txtPass);

        // Mensaje de error
        er = new JLabel("");
        er.setBounds(30, 180, 240, 20);
        er.setForeground(Color.RED);
        er.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        loginPanel.add(er);

        // Panel CAPTCHA
        JLabel lblCaptcha = new JLabel("Ingrese el código CAPTCHA:");
        lblCaptcha.setBounds(30, 210, 240, 20);
        lblCaptcha.setForeground(Color.WHITE);
        lblCaptcha.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        loginPanel.add(lblCaptcha);

        // Panel de imagen CAPTCHA
        captchaPanel = new CaptchaPanel();
        captchaPanel.setBounds(30, 230, 240, 60);
        captchaPanel.setBackground(new Color(0xF77F00)); // Fondo mostaza
        loginPanel.add(captchaPanel);

        // Botón para regenerar CAPTCHA
        JButton btnRefreshCaptcha = new JButton("↻");
        btnRefreshCaptcha.setBounds(275, 230, 30, 30);
        btnRefreshCaptcha.setBackground(new Color(0xD62828));
        btnRefreshCaptcha.setForeground(Color.WHITE);
        btnRefreshCaptcha.setFocusPainted(false);
        btnRefreshCaptcha.addActionListener(e -> captchaPanel.generarCaptcha());
        loginPanel.add(btnRefreshCaptcha);

        // Campo para ingresar CAPTCHA
        txtCaptcha = new JTextField();
        txtCaptcha.setBounds(30, 300, 240, 30);
        txtCaptcha.setBackground(new Color(0xF77F00)); // mostaza
        txtCaptcha.setForeground(Color.WHITE);
        txtCaptcha.setCaretColor(Color.WHITE);
        txtCaptcha.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        loginPanel.add(txtCaptcha);

        // Botón login
        JButton btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setBounds(90, 350, 120, 35);
        btnLogin.setBackground(new Color(0xD62828));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnLogin.addActionListener(e -> validarYLogin());
        loginPanel.add(btnLogin);

        // Olvido contraseña
        JLabel olvido = new JLabel("¿Olvidaste tu contraseña?");
        olvido.setBounds(80, 400, 180, 20);
        olvido.setForeground(new Color(0xFFF3E0));
        olvido.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        loginPanel.add(olvido);
    }

    private void validarYLogin() {
        String email = txtUsuario.getText().trim();
        String password = new String(txtPass.getPassword()).trim();
        String captchaIngresado = txtCaptcha.getText().trim();
        String captchaCorrecto = captchaPanel.getCodigo();

        // Validaciones básicas
        if (email.isEmpty() || password.isEmpty() || captchaIngresado.isEmpty()) {
            er.setText("Por favor, completa todos los campos.");
            return;
        }

        // Validar CAPTCHA primero
        if (!captchaIngresado.equalsIgnoreCase(captchaCorrecto)) {
            er.setText("Código CAPTCHA incorrecto. Intenta nuevamente.");
            captchaPanel.generarCaptcha(); // Generar nuevo CAPTCHA
            txtCaptcha.setText("");
            txtCaptcha.requestFocus();
            return;
        }

        if (!validarEmail(email)) {
            er.setText("Correo no válido.");
            txtUsuario.requestFocus();
            return;
        }

        if (password.length() < 6) {
            er.setText("La contraseña debe tener al menos 6 caracteres.");
            txtPass.requestFocus();
            return;
        }

        // Validación especial para admin (bypass CAPTCHA para admin)
        if (email.equals("santiagobelen169@gmail.com") && password.equals("santy123")) {
            this.dispose();
            new Pantalla2().setVisible(true);
            return;
        }

        // Validación desde base de datos
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/Datos", "postgres", "AnaSanty16");
             PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM empleado WHERE correo = ? AND contrasena = ?")) {

            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                correoEmpleado = email;
                this.dispose();

                if (email.equalsIgnoreCase("santy18belen@gmail.com")) {
                    new PantallaPrinU().setVisible(true);
                } else {
                    new PantallaPrinU().setVisible(true);
                }

            } else {
                er.setText("Credenciales incorrectas.");
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new loginEmpleado().setVisible(true);
        });
    }

    // Clase interna para el fondo degradado
    


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
