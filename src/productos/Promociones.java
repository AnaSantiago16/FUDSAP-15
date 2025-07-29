/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package productos;

import conexion.Libreria;
import conexion.Libreria.EntidadDAO;
import conexion.Libreria.EntidadGenerica;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Promociones {
    private EntidadDAO entidadDAO;  // Campo para almacenar la instancia

    public Promociones() {
        // Inicializa entidadDAO en el constructor
        this.entidadDAO = new Libreria.EntidadDAO();
    }
    
    public List<Promocion> obtenerLanzamientos() {
        List<Promocion> lista = new ArrayList<>();
        
        // Verificación de nulidad (aunque ya está inicializado en el constructor)
        if (entidadDAO == null) {
            throw new IllegalStateException("EntidadDAO no ha sido inicializado");
        }
        
        // Configuración de la tabla
        entidadDAO.setTabla("lanzamientos");
        entidadDAO.setColumnas(Arrays.asList("id", "nombre", "descripcion", "precio", 
                                          "fecha_lanzamiento", "imagen", "activo"));
        entidadDAO.setColumnaId("id");

        List<EntidadGenerica> entidades = entidadDAO.obtenerEntidades();
        
        for (EntidadGenerica entidad : entidades) {
            if (Boolean.TRUE.equals(entidad.getCampo("activo"))) {
                Promocion promo = new Promocion();
                promo.setId(entidad.getId("id"));
                promo.setNombre((String) entidad.getCampo("nombre"));
                promo.setDescripcion((String) entidad.getCampo("descripcion"));
                
                Object precioObj = entidad.getCampo("precio");
                if (precioObj != null) {
                    try {
                        promo.setPrecio(Double.parseDouble(precioObj.toString()));
                    } catch (NumberFormatException e) {
                        promo.setPrecio(0.0);
                    }
                }
                
                promo.setFechaInicio((Date) entidad.getCampo("fecha_lanzamiento"));
                promo.setImagen((String) entidad.getCampo("imagen"));
                promo.setTipo("lanzamiento");
                lista.add(promo);
            }
        }
        return lista;
    }


    // Obtener todas las promociones
    public List<Promocion> obtenerPromociones() {
        List<Promocion> lista = new ArrayList<>();
        entidadDAO.setTabla("promociones");
        entidadDAO.setColumnas(Arrays.asList("id", "nombre", "descripcion", "precio_original", 
                                           "precio_promocion", "fecha_inicio", "fecha_fin", "imagen"));
        entidadDAO.setColumnaId("id");

        List<EntidadGenerica> entidades = entidadDAO.obtenerEntidades();
        
        for (EntidadGenerica entidad : entidades) {
            Promocion promo = new Promocion();
            promo.setId(entidad.getId("id"));
            promo.setNombre((String) entidad.getCampo("nombre"));
            promo.setDescripcion((String) entidad.getCampo("descripcion"));
            promo.setPrecio(Double.parseDouble(entidad.getCampo("precio_original").toString()));
            promo.setPrecioPromocion(Double.parseDouble(entidad.getCampo("precio_promocion").toString()));
            promo.setFechaInicio((Date) entidad.getCampo("fecha_inicio"));
            promo.setFechaFin((Date) entidad.getCampo("fecha_fin"));
            promo.setImagen((String) entidad.getCampo("imagen"));
            promo.setTipo("promocion");
            lista.add(promo);
        }
        return lista;
    }

    // Obtener todos los descuentos
    public List<Promocion> obtenerDescuentos() {
        List<Promocion> lista = new ArrayList<>();
        entidadDAO.setTabla("descuentos");
        entidadDAO.setColumnas(Arrays.asList("id", "codigo", "porcentaje", "descripcion", 
                                           "fecha_inicio", "fecha_fin", "aplica_a"));
        entidadDAO.setColumnaId("id");

        List<EntidadGenerica> entidades = entidadDAO.obtenerEntidades();
        
        for (EntidadGenerica entidad : entidades) {
            Promocion promo = new Promocion();
            promo.setId(entidad.getId("id"));
            promo.setCodigoDescuento((String) entidad.getCampo("codigo"));
            promo.setPorcentajeDescuento(Integer.parseInt(entidad.getCampo("porcentaje").toString()));
            promo.setDescripcion((String) entidad.getCampo("descripcion"));
            promo.setFechaInicio((Date) entidad.getCampo("fecha_inicio"));
            promo.setFechaFin((Date) entidad.getCampo("fecha_fin"));
            promo.setTipo("descuento");
            lista.add(promo);
        }
        return lista;
    }

    // Método para insertar una nueva promoción
    public boolean insertarPromocion(Promocion promocion) {
        entidadDAO.setTabla("promociones");
        entidadDAO.setColumnas(Arrays.asList("nombre", "descripcion", "precio_original", 
                                           "precio_promocion", "fecha_inicio", "fecha_fin", "imagen"));
        
        EntidadGenerica entidad = new EntidadGenerica();
        entidad.setCampo("nombre", promocion.getNombre());
        entidad.setCampo("descripcion", promocion.getDescripcion());
        entidad.setCampo("precio_original", promocion.getPrecio());
        entidad.setCampo("precio_promocion", promocion.getPrecioPromocion());
        entidad.setCampo("fecha_inicio", promocion.getFechaInicio());
        entidad.setCampo("fecha_fin", promocion.getFechaFin());
        entidad.setCampo("imagen", promocion.getImagen());
        
        return entidadDAO.insertarEntidad(entidad);
    }

    // Método para eliminar una promoción
    public boolean eliminarPromocion(int id) {
        entidadDAO.setTabla("promociones");
        entidadDAO.setColumnaId("id");
        return entidadDAO.eliminarEntidad(id);
    }
}