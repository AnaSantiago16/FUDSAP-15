/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loginform;

import conexion.Libreria.EntidadGenerica;
import conexion.Libreria.EntidadDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.List;
import java.util.Arrays;
import javax.activation.*;
import java.io.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import conexion.Libreria;
//import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;


public class RegistroPersona extends JFrame {
    private EntidadDAO personaDAO;

    public RegistroPersona() {
        configurarDAO();

        setTitle("Registro de Persona");
        setSize(500, 500);
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

        JLabel lblEmail = crearLabel("Correo Electrónico:");
        JTextField txtEmail = crearTextField();

        JLabel lblTelefono = crearLabel("Teléfono:");
        JTextField txtTelefono = crearTextField();
        

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
        panel.add(lblEmail, gbc);

        gbc.gridy = 4;
        panel.add(txtEmail, gbc);

        gbc.gridy = 5;
        panel.add(lblTelefono, gbc);

        gbc.gridy = 6;
        panel.add(txtTelefono, gbc);

       

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setOpaque(false);
        panelBotones.add(btnCancelar);
        panelBotones.add(btnRegistrar);

        gbc.gridy = 11;
        gbc.gridwidth = 2;
        panel.add(panelBotones, gbc);

        btnRegistrar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String email = txtEmail.getText().trim();
            String telefono = txtTelefono.getText().trim();
            

            if (nombre.isEmpty() || email.isEmpty() || telefono.isEmpty() ) {
                mostrarError("Todos los campos son obligatorios");
                return;
            }

            if (!validarEmail(email)) {
                mostrarError("Formato de correo electrónico inválido");
                return;
            }

            
            new Thread(() -> {
                try {
                    boolean exitoDB = guardarEnBaseDeDatos(nombre, email, telefono);

                    if (!exitoDB) {
                        SwingUtilities.invokeLater(() -> mostrarError("Error al guardar en la base de datos"));
                        return;
                    }

                    File pdfFile = generarPDF(nombre, email);
                    enviarCorreoConPDF(email, nombre, pdfFile);
                    pdfFile.delete();

                    SwingUtilities.invokeLater(() -> {
                        mostrarExito("Registro completado y PDF enviado a " + email);
                        limpiarCampos(txtNombre, txtEmail, txtTelefono);
                        
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
    personaDAO = new EntidadDAO();
    personaDAO.setTabla("registro"); // Nombre exacto de la tabla
    
    // 3. Configurar columnas (DEBEN coincidir exactamente con la tabla)
    List<String> columnas = Arrays.asList(
        "nombre",    // minúsculas
        "correo",    // minúsculas
        "telefono"  // minúsculas
       
        );
    personaDAO.setColumnas(columnas);
    personaDAO.setColumnaId("id"); // Nombre de la columna ID
    
    // 4. Verificar conexión
    
}
    
    private boolean guardarEnBaseDeDatos(String nombre, String email, String telefono) {
    try {
        // 1. Validar datos esenciales
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo no puede estar vacío");
        }
        
        
        List<Libreria.EntidadGenerica> existentes = personaDAO.buscarPorCampo("correo", email.trim());
        if (existentes != null && !existentes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El correo electrónico ya está registrado. Por favor, usa otro.");
            return false;
        }
        // 2. Crear entidad con valores
        EntidadGenerica persona = new EntidadGenerica();
        persona.setCampo("nombre", nombre.trim());
        persona.setCampo("correo", email.trim());
        persona.setCampo("telefono", telefono != null ? telefono.trim() : null);
        
        

        // 4. Insertar en la base de datos
        boolean resultado = personaDAO.insertarEntidad(persona);
        
        if (!resultado) {
            JOptionPane.showMessageDialog(null, "El correo electrónico ya está registrado. Por favor, usa otro.");
        }

   
        
        return resultado;
    } catch (Exception e) {
        System.err.println("Error en guardarEnBaseDeDatos: " + e.getMessage());
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
    
    private File generarPDF(String nombre, String email) throws Exception {
        File pdfFile = File.createTempFile("registro_", ".pdf");
        
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
        document.open();

        
        // Fuentes personalizadas
// Configuración de fuentes (asegúrate de que estas líneas estén ANTES de usarlas)
Font tituloFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.RED);
Font subtituloFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.DARK_GRAY);
Font textoFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);
Font destacadoFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.RED);
Font footerFont = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC, BaseColor.GRAY);

try {
    // Logo (asegúrate de tener la ruta correcta o manejar la excepción si no existe)
    Image logo = Image.getInstance("C:\\Users\\Ana\\Documents\\NetBeansProjects\\libreria\\LoginForm\\src\\loginform.png");
    logo.scaleToFit(150, 150);
    logo.setAlignment(Element.ALIGN_CENTER);
    document.add(logo);
} catch (Exception e) {
    // Si no hay logo, continuar sin él
    System.out.println("No se pudo cargar el logo: " + e.getMessage());
}

// Título
Paragraph titulo = new Paragraph("FUDSAP - Comida Rápida", tituloFont);
titulo.setAlignment(Element.ALIGN_CENTER);
titulo.setSpacingAfter(20f);
document.add(titulo);

// Subtítulo
Paragraph subtitulo = new Paragraph("Confirmación de Registro", subtituloFont);
subtitulo.setAlignment(Element.ALIGN_CENTER);
subtitulo.setSpacingAfter(15f);
document.add(subtitulo);

// Fecha
Paragraph fecha = new Paragraph("Fecha: " + new java.util.Date().toString(), textoFont);
fecha.setSpacingAfter(10f);
document.add(fecha);

// Saludo personalizado
Paragraph saludo = new Paragraph("Estimad@ " +" "+ nombre + "", textoFont);
saludo.setSpacingAfter(15f);
document.add(saludo);

// Información del usuario
Paragraph infoUsuario = new Paragraph("Su registro ha sido completado con los siguientes datos:", textoFont);
infoUsuario.setSpacingAfter(10f);
document.add(infoUsuario);

// Lista de datos
// Lista de datos del usuario (completa y corregida)

com.itextpdf.text.List datosUsuario = new com.itextpdf.text.List();
datosUsuario.add(new ListItem("Correo electrónico: " + email, textoFont));
//datosUsuario.add(new ListItem("Fecha de registro: " + new java.util.Date().toString(), textoFont));
datosUsuario.add(new ListItem("Tipo de cuenta: Usuario ", textoFont));

// Para añadir espacio después de la lista, usa un Paragraph vacío
document.add(datosUsuario);
Paragraph espacio = new Paragraph(" "); // Espacio en blanco
espacio.setSpacingAfter(15f); // 15 puntos de espacio después
document.add(espacio);

// Beneficios para nuevos usuarios
Paragraph beneficiosTitulo = new Paragraph("¡Bienvenid@ a la familia FUDSAP!", subtituloFont);
beneficiosTitulo.setSpacingAfter(10f);
document.add(beneficiosTitulo);

com.itextpdf.text.List beneficios = new com.itextpdf.text.List();
beneficios.add(new ListItem("Descuento del 10% en su primera compra", textoFont));
beneficios.add(new ListItem("Acumulación de puntos por cada compra", textoFont));
beneficios.add(new ListItem("Ofertas exclusivas para miembros", textoFont));
beneficios.add(new ListItem("Notificación anticipada de promociones", textoFont));
document.add(beneficios);

// Otra alternativa: configurar el espaciado mediante la lista misma
datosUsuario.setListSymbol(new Chunk("")); // Elimina viñetas si es necesario
datosUsuario.setIndentationLeft(20f); 

// Instrucciones (lista ordenada)
com.itextpdf.text.List pasos = new com.itextpdf.text.List(com.itextpdf.text.List.ORDERED);
pasos.add(new ListItem("Inicie sesión con su Correo Electrónico", textoFont));
pasos.add(new ListItem("Explore nuestro menú y realice su primer pedido", textoFont));
pasos.add(new ListItem("Presente este documento para reclamar su descuento de bienvenida", textoFont));
document.add(pasos);

// Mensaje final
Paragraph agradecimiento = new Paragraph(
    "Gracias por unirse a FUDSAP. ¡Esperamos que disfrute de nuestra deliciosa comida rápida y excelente servicio!", 
    textoFont);
agradecimiento.setSpacingBefore(20f);
agradecimiento.setAlignment(Element.ALIGN_CENTER);
document.add(agradecimiento);

// Pie de página
Paragraph footer = new Paragraph(
        """
        FUDSAP - Calle Principal 123, Ciudad | Tel: 555-1234 | www.fudsap.com
        Si tiene alguna pregunta, contacte a: soporte@fudsap.com""", 
    footerFont);
footer.setAlignment(Element.ALIGN_CENTER);
footer.setSpacingBefore(30f);
document.add(footer);
        
        document.close();
        return pdfFile;
    }
    
    private void enviarCorreoConPDF(String emailDestino, String nombre, File pdfFile) throws Exception {
        final String username = "fudsap.25@gmail.com"; // Cambiar por tu correo
        final String password = "cgvh dirx jirv rbzm"; // Cambiar por tu contraseña
        
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
        message.setSubject("Confirmación de Registro - " + nombre);
        
        // Cuerpo del mensaje
        MimeBodyPart texto = new MimeBodyPart();
        String contenido = "Estimad@ " + nombre + " "  +",\n\n" +
            "Adjunto encontrará el comprobante de su registro.\n\n" +
            "Gracias por preferirnos.\n\n" ;
        texto.setText(contenido);
        
        // Adjuntar PDF
        MimeBodyPart adjunto = new MimeBodyPart();
        adjunto.setDataHandler(new DataHandler(new FileDataSource(pdfFile)));
        adjunto.setFileName("ComprobanteRegistro.pdf");
        
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
            RegistroPersona registro = new RegistroPersona();
            registro.setVisible(true);
        });
    }

    
}