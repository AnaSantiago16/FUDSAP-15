/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package productos;
import Empleado.loginEmpleado;
import calculadora.CalculadoraImpuestos;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.swing.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import loginform.loginUsuario;

public class Transferencia {

    public static void procesarPagoTransferencia(double subtotal, double ivaCalculado, double totalConIVA, Map<String, ProductoSeleccionado> carrito) {
        String correoUsuario = loginUsuario.correoUsuario;
        String correoEmpleado = loginEmpleado.correoEmpleado;
        String correoFinal = null;
        String nombreCliente = null;
       
        boolean esEmpleado = false;

        // Validar correo
        if (correoUsuario != null && !correoUsuario.isEmpty()) {
            correoFinal = correoUsuario;
            String[] datos = obtenerDatosDesdeBD(correoFinal, false);
            nombreCliente = datos[0];
          
        } else if (correoEmpleado != null && !correoEmpleado.isEmpty()) {
            correoFinal = correoEmpleado;
            esEmpleado = true;
            String[] datos = obtenerDatosDesdeBD(correoFinal, true);
            nombreCliente = datos[0];
            
        }

        if (correoFinal == null || nombreCliente == null) {
            JOptionPane.showMessageDialog(null, "No se pudo recuperar el correo o nombre del usuario.");
            return;
        }

        // 🔐 Solicitar y validar número de cuenta
        String cuenta = JOptionPane.showInputDialog(null, "Ingrese el número de cuenta bancaria:");
        if (cuenta == null || cuenta.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Número de cuenta no ingresado.");
            return;
        }
        cuenta = cuenta.trim();
        if (!cuenta.matches("\\d{10,18}")) {
            JOptionPane.showMessageDialog(null, "Número de cuenta inválido. Debe contener solo números (10-18 dígitos).");
            return;
        }

        try {
            Document document = new Document();
            File pdfFile;

            if (correoFinal.equalsIgnoreCase("santy18belen@gmail.com")) {
                File carpeta = new File("Venta");
                if (!carpeta.exists()) carpeta.mkdirs();
                pdfFile = new File(carpeta, "ticket_transferencia_" + System.currentTimeMillis() + ".pdf");
            } else {
                pdfFile = new File("ticket_transferencia.pdf");
            }

            PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();

            // Tipografías
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.BLUE);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.DARK_GRAY);
            Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);

           
           
if (correoFinal.equals("santy18belen@gmail.com")) {
     Paragraph titulo = new Paragraph("🏦 PAGO POR TRANSFERENCIA 🧾", titleFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(15f);
            document.add(titulo);
           
    // Datos del EMPLEADO
    document.add(new Paragraph("👤 Nombre del Empleado: " + nombreCliente, boldFont));
    document.add(new Paragraph("📧 Correo: " + correoFinal, normalFont));
    document.add(new Paragraph(" ")); // Espaciado
} else {
    Paragraph titulo = new Paragraph("🏦 PAGO POR TRANSFERENCIA 🧾", titleFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(15f);
            document.add(titulo);
           
    document.add(new Paragraph("👤 Nombre del Cliente: " + nombreCliente, boldFont));
    document.add(new Paragraph("📧 Correo: " + correoFinal, normalFont));
    document.add(new Paragraph(" ")); // Espaciado
}

            // Tabla de productos
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{4, 2, 2});

            String[] headers = {"Producto", "Cantidad", "Subtotal"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, boldFont));
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(8);
                table.addCell(cell);
            }

            for (ProductoSeleccionado p : carrito.values()) {
                table.addCell(createCell(p.getNombre(), normalFont));
                table.addCell(createCell(String.valueOf(p.getCantidad()), normalFont));
                table.addCell(createCell(String.format("$%.2f", p.getCantidad() * p.getPrecioUnitario()), normalFont));
            }

            document.add(table);
            document.add(new Paragraph(" "));

            // Totales
            Paragraph resumen = new Paragraph();
            resumen.setFont(normalFont);
            resumen.add("💵 Subtotal: $" + String.format("%.2f", subtotal) + "\n");
            resumen.add("🧾 IVA (16%): $" + String.format("%.2f", ivaCalculado) + "\n");
            resumen.add("✅ Total Final: $" + String.format("%.2f", totalConIVA) + "\n");
            document.add(resumen);

            // Mensaje final
            Paragraph gracias = new Paragraph("¡Gracias por su compra vía transferencia bancaria! 🙌", boldFont);
            gracias.setSpacingBefore(10f);
            gracias.setAlignment(Element.ALIGN_CENTER);
            document.add(gracias);

            document.close();

            if (correoFinal.equalsIgnoreCase("santy18belen@gmail.com")) {
                JOptionPane.showMessageDialog(null, "Ticket guardado correctamente en carpeta Venta/");
            } else {
                enviarCorreoConPDF(correoFinal, "Recibo de Transferencia", "Adjunto encontrarás tu recibo por transferencia.", pdfFile);
                JOptionPane.showMessageDialog(null, "Ticket enviado al correo: " + correoFinal);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar o enviar el ticket: " + e.getMessage());
        }
    }

    private static String[] obtenerDatosDesdeBD(String correo, boolean esEmpleado) {
        String tabla = esEmpleado ? "empleado" : "registro";
        String consulta = "SELECT nombre FROM " + tabla + " WHERE correo = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Datos", "postgres", "AnaSanty16");
             PreparedStatement stmt = conn.prepareStatement(consulta)) {
            stmt.setString(1, correo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new String[]{rs.getString("nombre")};
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener datos desde BD: " + ex.getMessage());
        }
        return new String[]{null, null};
    }

    private static PdfPCell createCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(6);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return cell;
    }

    private static void enviarCorreoConPDF(String destinatario, String asunto, String mensajeTexto, File pdfAdjunto) {
        final String remitente = "fudsap.25@gmail.com";
        final String password = "cgvh dirx jirv rbzm"; // Usa una contraseña de aplicación

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session sesion = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, password);
            }
        });

        try {
            Message message = new MimeMessage(sesion);
            message.setFrom(new InternetAddress(remitente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);

            MimeBodyPart mensajeTextoPart = new MimeBodyPart();
            mensajeTextoPart.setText(mensajeTexto);

            MimeBodyPart adjuntoPart = new MimeBodyPart();
            adjuntoPart.attachFile(pdfAdjunto);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mensajeTextoPart);
            multipart.addBodyPart(adjuntoPart);

            message.setContent(multipart);
            Transport.send(message);

        } catch (MessagingException | IOException e) {
            JOptionPane.showMessageDialog(null, "Error al enviar correo: " + e.getMessage());
        }
    }
}