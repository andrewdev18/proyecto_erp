/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuentasporpagar.daos;

import com.cuentasporpagar.models.Factura;
import com.global.config.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PAOLA
 */
public class AutorizarPagoDAO {

    private List<Factura> listaDetalle;
    private Factura factura;
    private Conexion conexion;
    private ResultSet result;

    public AutorizarPagoDAO() {
        listaDetalle = new ArrayList<>();
    }

    public List<Factura> llenarDatos(String nfactura) {
        listaDetalle.clear();
        if (conexion.isEstado()) {
            try {
                String sentencia = "SELECT p.nombre,f.nfactura,f.descripcion,f.importe,\n"
                        + "c.cantdiasvencidos,f.fecha,f.vencimiento\n"
                        + "FROM factura f \n"
                        + "inner join proveedor p \n"
                        + "on(f.idproveedor= p.idproveedor)\n"
                        + "inner join condiciones c\n"
                        + "on(c.idproveedor= p.idproveedor)\n"
                        + "where f.nfactura = '" + nfactura + "';";
                result = conexion.ejecutarConsulta(sentencia);
                while (result.next()) {
                   this.factura.setNombre(result.getString("nombre"));
                   this.factura.setNfactura(result.getString("nfactura"));
                   this.factura.setDescripcion(result.getString("descripcion"));
                   this.factura.setImporte(result.getFloat("importe"));
                   this.factura.setAux(result.getInt("cantdiasvencidos"));
                   this.factura.setFecha(result.getObject("fecha", LocalDate.class));
                   this.factura.setVencimiento(result.getObject("vencimiento", LocalDate.class));
                   
                   listaDetalle.add(factura);
                }
                System.out.print(sentencia);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally {
                conexion.cerrarConexion();
            }
        }
        return listaDetalle;
    }

    public List<Factura> llenarDetalle(String n) {
        listaDetalle.clear();
        if (conexion.isEstado()) {
            try {
                String sentencia = "select * from detalle_compra where nfactura = '" + n + "';";
                result = conexion.ejecutarConsulta(sentencia);
                System.out.println("Factura: " + result.toString());
                while (result.next()) {
                    listaDetalle.add(new Factura(result.getFloat("importe"),
                            result.getString("detalle"), result.getString("iddetallecompra")));
                }
                System.out.print(sentencia);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally {
                conexion.cerrarConexion();
            }
        }
        return listaDetalle;
    }

    public List<Factura> getListaDetalle() {
        return listaDetalle;
    }

    public void setListaDetalle(List<Factura> listaDetalle) {
        this.listaDetalle = listaDetalle;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

}
