/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loginform;
import captcha.CaptchaPanel;
import java.awt.*;
import java.util.regex.Pattern;
import javax.swing.*;

public class LoginModern extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtPass;
    private JLabel er;
    private CaptchaPanel captchaPanel;
    private JTextField txtCaptcha;

    public LoginModern() {
        setTitle("Login");
        setSize(400, 650); // Aumentamos el tamaño para el CAPTCHA
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Fondo degradado con rojo cálido a marrón pan
        PanelConDegradado fondo = new PanelConDegradado();
        fondo.setLayout(null);
        add(fondo);

        // Panel translúcido con borde rojo cálido
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBounds(50, 60, 300, 500); // Aumentamos la altura
        loginPanel.setBackground(new Color(255, 243, 224, 25)); // color crema translúcido
        loginPanel.setBorder(BorderFactory.createLineBorder(new Color(0xD62828), 1));
        fondo.add(loginPanel);

        // Icono de usuario
        JLabel icono = new JLabel("\uD83D\uDCF7", SwingConstants.CENTER);
        icono.setFont(new Font("Segoe UI", Font.PLAIN, 40));
        icono.setBounds(115, 10, 70, 50);
        icono.setForeground(Color.WHITE);
        loginPanel.add(icono);

        // Etiqueta Correo
        JLabel lblCorreo = new JLabel("Correo Electrónico");
        lblCorreo.setBounds(30, 60, 240, 20);
        lblCorreo.setForeground(Color.WHITE);
        lblCorreo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        loginPanel.add(lblCorreo);

        // Campo correo
        txtUsuario = new JTextField();
        txtUsuario.setBounds(30, 80, 240, 30);
        txtUsuario.setBackground(new Color(0xF77F00)); // mostaza
        txtUsuario.setForeground(Color.WHITE);
        txtUsuario.setCaretColor(Color.WHITE);
        txtUsuario.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        loginPanel.add(txtUsuario);

        // Etiqueta Contraseña
        JLabel lblPass = new JLabel("Contraseña");
        lblPass.setBounds(30, 120, 240, 20);
        lblPass.setForeground(Color.WHITE);
        lblPass.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        loginPanel.add(lblPass);

        // Campo contraseña
        txtPass = new JPasswordField();
        txtPass.setBounds(30, 140, 240, 30);
        txtPass.setBackground(new Color(0xF77F00)); // mostaza
        txtPass.setForeground(Color.WHITE);
        txtPass.setCaretColor(Color.WHITE);
        txtPass.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        loginPanel.add(txtPass);

        // Etiqueta error
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

        // Botón iniciar sesión
        JButton btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setBounds(90, 350, 120, 35);
        btnLogin.setBackground(new Color(0xD62828)); // rojo cálido
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnLogin.addActionListener(e -> validarYLogin());
        loginPanel.add(btnLogin);

        // Enlace olvidaste contraseña
        JLabel olvido = new JLabel("¿Olvidaste tu contraseña?");
        olvido.setBounds(80, 400, 180, 20);
        olvido.setForeground(new Color(0xFFF3E0)); // crema
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

        if (!email.equals("fudsap.25@gmail.com") || !password.equals("12345678")) {
            er.setText("Credenciales incorrectas.");
            return;
        }

        this.dispose();
        new Pantalla2().setVisible(true);
    }

    private boolean validarEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginModern().setVisible(true);
        });
    }
}

class PanelConDegradado extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Degradado de rojo cálido (#D62828) a marrón pan (#F77F00)
        Color colorInicio = new Color(0xD62828);
        Color colorFin = new Color(0xF77F00);
        GradientPaint gp = new GradientPaint(0, 0, colorInicio, getWidth(), getHeight(), colorFin);
        
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}