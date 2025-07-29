/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loginform;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import conexion.Libreria;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.*;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import conexion.Libreria;
//import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;


/**
 *
 * @author Ana
 */
public class RegistrarEmpleado extends JFrame{
 private Libreria.EntidadDAO personaDAO;
 // En tu clase loginUsuario
public static String correoUsuario;

 //String ocupacion;

    public RegistrarEmpleado() {
        configurarDAO();

        setTitle("Registro de Persona");
        setSize(700, 700);
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

        JLabel titulo = new JLabel("Registro");
        titulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);

        JLabel lblNombre = crearLabel("Nombre:");
        JTextField txtNombre = crearTextField();

        JLabel lblEdad= crearLabel("Edad:");
        JTextField txtEdad = crearTextField();

        JLabel lblHorario = crearLabel("Horario:");
        JTextField txtHorario = crearTextField();

        JLabel lblEmail = crearLabel("Correo:");
        JTextField txtEmail = crearTextField();

        JLabel lblContrasena = crearLabel("Contraseña:");
        JPasswordField txtContrasena = new JPasswordField(20);
        txtContrasena.setMinimumSize(new Dimension(200, 35));
        txtContrasena.setPreferredSize(new Dimension(400, 35));
        txtContrasena.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 15));
        txtContrasena.setBackground(new Color(255, 255, 255, 180));
        txtContrasena.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        JButton btnRegistrar = new JButton("Registrar");
        estiloBoton(btnRegistrar);

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
        panel.add(lblEdad, gbc);

        gbc.gridy = 4;
        panel.add(txtEdad, gbc);

        gbc.gridy = 5;
        panel.add(lblHorario, gbc);

        gbc.gridy = 6;
        panel.add(txtHorario, gbc);

        gbc.gridy = 7;
        panel.add(lblEmail, gbc);

        gbc.gridy = 8;
        panel.add(txtEmail, gbc);

        gbc.gridy = 9;
        panel.add(lblContrasena, gbc);

        gbc.gridy = 10;
        panel.add(txtContrasena, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setOpaque(false);
        panelBotones.add(btnCancelar);
        panelBotones.add(btnRegistrar);

        gbc.gridy = 11;
        gbc.gridwidth = 2;
        panel.add(panelBotones, gbc);

        btnRegistrar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String edad = txtEdad.getText().trim();
            String horario = txtHorario.getText().trim();
            String email = txtEmail.getText().trim();
            String contrasena = new String(txtContrasena.getPassword());

            if (nombre.isEmpty() || email.isEmpty() || edad.isEmpty() || horario.isEmpty() || contrasena.isEmpty()) {
                mostrarError("Todos los campos son obligatorios");
                return;
            }

            if (!validarEmail(email)) {
                mostrarError("Formato de correo electrónico inválido");
                return;
            }

            
            new Thread(() -> {
                try {
                    boolean exitoDB = guardarEnBaseDeDatos(nombre, edad, horario, email,contrasena);

                    if (!exitoDB) {
                        //SwingUtilities.invokeLater(() -> mostrarError("Error al guardar en la base de datos"));
                        return;
                    }
String ocupacion = "CAJERO"; // o "GERENTE" - debe venir de tu interfaz
//String horario = "Lunes a Viernes 9:00-18:00"; // debe venir de tu interfaz

File pdfFile = generarPDF(nombre, email, ocupacion, horario);
enviarCorreoConPDF(email, nombre, ocupacion, pdfFile);
pdfFile.delete();

                    SwingUtilities.invokeLater(() -> {
                        mostrarExito("Registro completado y PDF enviado a " + email);
                        limpiarCampos(txtNombre, txtEdad, txtHorario, txtEmail,txtContrasena);
                        txtContrasena.setText("");
                    });

                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() -> mostrarError("Error durante el registro: " + ex.getMessage()));
                    ex.printStackTrace();
                }
            }).start();
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
    
    // 2. Configurar DAO
    personaDAO = new Libreria.EntidadDAO();
    personaDAO.setTabla("empleado"); // Nombre exacto de la tabla
    
    // 3. Configurar columnas (DEBEN coincidir exactamente con la tabla)
    List<String> columnas = Arrays.asList(
        "nombre",    // minúsculas
        "edad",    // minúsculas
        "horario",   // minúsculas
        "correo",
        "contrasena" // minúsculas
    );
    personaDAO.setColumnas(columnas);
    personaDAO.setColumnaId("id"); // Nombre de la columna ID
    
    // 4. Verificar conexión
    
}
    
    private boolean guardarEnBaseDeDatos(String nombre, String edad, String horario, String email, String contrasena) {
    try {
        // Validar campos obligatorios
        if (nombre == null || nombre.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío.");
            return false;
        }

        if (email == null || email.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El correo no puede estar vacío.");
            return false;
        }

        // Configurar tabla y columnas
        personaDAO.setTabla("registro"); // Asegúrate de que sea el nombre correcto
        personaDAO.setColumnas(Arrays.asList("nombre", "edad", "horario", "correo", "contrasena"));

        // Validar si el correo ya existe
        List<Libreria.EntidadGenerica> existentes = personaDAO.buscarPorCampo("correo", email.trim());
        if (existentes != null && !existentes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El correo electrónico ya está registrado. Por favor, usa otro.");
            return false;
        }

        // Crear entidad
        Libreria.EntidadGenerica persona = new Libreria.EntidadGenerica();
        persona.setCampo("nombre", nombre.trim());
        persona.setCampo("edad", edad != null ? edad.trim() : null);
        persona.setCampo("horario", horario != null ? horario.trim() : null);
        persona.setCampo("correo", email.trim());
        persona.setCampo("contrasena", contrasena != null ? contrasena.trim() : null);

        // Insertar en la base de datos
        boolean resultado = personaDAO.insertarEntidad(persona);

        if (!resultado) {
            JOptionPane.showMessageDialog(null, "El correo electrónico ya está registrado. Por favor, usa otro.");
        }

        return resultado;

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error inesperado: " + e.getMessage());
        e.printStackTrace();
        return false;
    }
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
    
    private File generarPDF(String nombre, String email, String ocupacion, String horario) throws Exception {
    File pdfFile = File.createTempFile("notificacion_ingreso_", ".pdf");
    
    Document document = new Document();
    PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
    document.open();

    // Fuentes personalizadas
    Font tituloFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLUE);
    Font subtituloFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.DARK_GRAY);
    Font textoFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);
    Font destacadoFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, new BaseColor(0, 102, 0));
    Font footerFont = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC, BaseColor.GRAY);

    // Validar ocupación
    if(ocupacion == null || ocupacion.isEmpty()) {
        ocupacion = "EMPLEADO"; // Valor por defecto
    }

    try {
        // Cargar logo desde recursos (forma más confiable)
        Image logo = Image.getInstance(getClass().getResource("/loginform.png"));
        logo.scaleToFit(150, 150);
        logo.setAlignment(Element.ALIGN_CENTER);
        document.add(logo);
    } catch (Exception e) {
        System.out.println("No se pudo cargar el logo, continuando sin él: " + e.getMessage());
        // Puedes añadir un texto alternativo si lo deseas
        Paragraph logoTxt = new Paragraph("FUDSAP", tituloFont);
        logoTxt.setAlignment(Element.ALIGN_CENTER);
        document.add(logoTxt);
    }

    // Título
    Paragraph titulo = new Paragraph("FUDSAP - Notificación de Ingreso", tituloFont);
    titulo.setAlignment(Element.ALIGN_CENTER);
    titulo.setSpacingAfter(20f);
    document.add(titulo);

    // Subtítulo según ocupación
    String subtituloTexto = "Bienvenid@ " + nombre + " - " + ocupacion;
    Paragraph subtitulo = new Paragraph(subtituloTexto, subtituloFont);
    subtitulo.setAlignment(Element.ALIGN_CENTER);
    subtitulo.setSpacingAfter(15f);
    document.add(subtitulo);

    // Fecha y horario
    Paragraph fecha = new Paragraph("Fecha de ingreso: " + new java.util.Date().toString(), textoFont);
    fecha.setSpacingAfter(5f);
    document.add(fecha);
    
    if(horario != null && !horario.isEmpty()) {
        Paragraph horarioP = new Paragraph("Horario asignado: " + horario, textoFont);
        horarioP.setSpacingAfter(10f);
        document.add(horarioP);
    }


    // Saludo personalizado
    Paragraph saludo = new Paragraph("Estimad@ " + nombre + ",", textoFont);
    saludo.setSpacingAfter(15f);
    document.add(saludo);

    // Información del empleado
    Paragraph infoEmpleado = new Paragraph("Sus datos de ingreso son los siguientes:", textoFont);
    infoEmpleado.setSpacingAfter(10f);
    document.add(infoEmpleado);

    // Lista de datos del empleado
    com.itextpdf.text.List datosEmpleado = new com.itextpdf.text.List();
    datosEmpleado.add(new ListItem("Correo electrónico: " + email, textoFont));
    datosEmpleado.add(new ListItem("Ocupación: " + ocupacion, textoFont));
    datosEmpleado.add(new ListItem("Fecha de ingreso: " + new java.util.Date().toString(), textoFont));
    document.add(datosEmpleado);

    // Espacio
    document.add(new Paragraph(" "));

    // Beneficios y responsabilidades según ocupación
    Paragraph beneficiosTitulo = new Paragraph(ocupacion.equals("GERENTE") ? 
        "Sus responsabilidades y beneficios como Gerente:" : 
        "Sus responsabilidades y beneficios como Cajer@:", subtituloFont);
    beneficiosTitulo.setSpacingAfter(10f);
    document.add(beneficiosTitulo);

    com.itextpdf.text.List beneficios = new com.itextpdf.text.List();
    
    if(ocupacion.equals("GERENTE")) {
        beneficios.add(new ListItem("Responsable de la gestión general del restaurante", textoFont));
        beneficios.add(new ListItem("Acceso completo al sistema de administración", textoFont));
        beneficios.add(new ListItem("Bonificación por cumplimiento de metas", textoFont));
        beneficios.add(new ListItem("Horario flexible según necesidades operativas", textoFont));
    } else {
        beneficios.add(new ListItem("Atención al cliente en caja", textoFont));
        beneficios.add(new ListItem("Manejo del sistema de punto de venta", textoFont));
        beneficios.add(new ListItem("Descuento del 20% en consumo personal", textoFont));
        beneficios.add(new ListItem("Horario rotativo según programación", textoFont));
    }
    document.add(beneficios);

    // Instrucciones para el primer día
    Paragraph instruccionesTitulo = new Paragraph("Instrucciones para su primer día:", subtituloFont);
    instruccionesTitulo.setSpacingAfter(10f);
    document.add(instruccionesTitulo);

    com.itextpdf.text.List instrucciones = new com.itextpdf.text.List(com.itextpdf.text.List.ORDERED);
    instrucciones.add(new ListItem("Presentarse en recursos humanos a las 8:00 AM", textoFont));
    instrucciones.add(new ListItem("Traer documentación requerida (original y copia)", textoFont));
    instrucciones.add(new ListItem("Asistir a la inducción inicial", textoFont));
    instrucciones.add(new ListItem("Recibir uniforme y credencial de acceso", textoFont));
    document.add(instrucciones);

    // Mensaje final
    Paragraph agradecimiento = new Paragraph(
        "¡El equipo de FUDSAP le da la bienvenida y esperamos que tenga una excelente experiencia con nosotros!", 
        textoFont);
    agradecimiento.setSpacingBefore(20f);
    agradecimiento.setAlignment(Element.ALIGN_CENTER);
    document.add(agradecimiento);

    // Pie de página
    Paragraph footer = new Paragraph(
        """
        FUDSAP - Departamento de Recursos Humanos
        Tel: 555-1234 | Email: rrhh@fudsap.com | www.fudsap.com""", 
        footerFont);
    footer.setAlignment(Element.ALIGN_CENTER);
    footer.setSpacingBefore(30f);
    document.add(footer);
    
    document.close();
    return pdfFile;
}

private void enviarCorreoConPDF(String emailDestino, String nombre, String ocupacion, File pdfFile) throws Exception {
    final String username = "fudsap.25@gmail.com"; // Cambiar por tu correo
        final String password = "cgvh dirx jirv rbzm";
    
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");
    
    Session session = Session.getInstance(props,
        new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    
    Message message = new MimeMessage(session);
    message.setFrom(new InternetAddress(username));
    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDestino));
    message.setSubject("Notificación de Ingreso - " + nombre);
    
    // Cuerpo del mensaje personalizado según ocupación
    MimeBodyPart texto = new MimeBodyPart();
    String contenido = "Estimad@ " + nombre + ",\n\n" +
        "Adjunto encontrará la notificación oficial de su ingreso a FUDSAP como " + 
        (ocupacion.equals("GERENTE") ? "Gerente" : "Cajer@") + ".\n\n" +
        "Por favor revise los detalles de su incorporación y las instrucciones para su primer día.\n\n" +
        "Bienvenid@ a nuestro equipo!\n\n" +
        "Atentamente,\n" +
        "Departamento de Recursos Humanos\n" +
        "FUDSAP";
    texto.setText(contenido);
    
    // Adjuntar PDF
    MimeBodyPart adjunto = new MimeBodyPart();
    adjunto.setDataHandler(new DataHandler(new FileDataSource(pdfFile)));
    adjunto.setFileName("Notificacion_Ingreso_" + nombre.replace(" ", "_") + ".pdf");
    
    // Ensamblar mensaje
    Multipart multipart = new MimeMultipart();
    multipart.addBodyPart(texto);
    multipart.addBodyPart(adjunto);
    message.setContent(multipart);
    
    // Enviar correo
    Transport.send(message);
}
    
    
    
    
    private boolean validarEmail(String email) {
        return email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }
    
    private void limpiarCampos(JTextField... campos) {
        for (JTextField campo : campos) {
            campo.setText("");
        }
        campos[0].requestFocus();
    }
    
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    private void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegistrarEmpleado registr = new RegistrarEmpleado();
            registr.setVisible(true);
        });
    }

    
}