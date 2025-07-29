/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package productos;

/**
 *
 * @author Ana
 */


public class ProductoSeleccionado {
    private String nombre;
    private int cantidad;
    private double precioUnitario;

    public ProductoSeleccionado(String nombre, int cantidad, double precioUnitario) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    // Setters
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    // Métodos adicionales
    public void aumentarCantidad() {
        this.cantidad++;
    }

    public double getTotal() {
        return cantidad * precioUnitario;
    }
}