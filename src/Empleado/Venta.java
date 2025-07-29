/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Empleado;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Venta {
    
    public static void guardarPDF(String nombreCliente) {
        File origen = new File("pdfs/" + nombreCliente + "_venta.pdf");
        File destino = new File("ventas_guardadas/" + nombreCliente + "_venta.pdf");

        try {
            Files.createDirectories(destino.getParentFile().toPath());
            Files.copy(origen.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("PDF guardado correctamente en carpeta de Ventas.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setVisible(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}