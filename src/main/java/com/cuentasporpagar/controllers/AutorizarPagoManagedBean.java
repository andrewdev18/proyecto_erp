/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuentasporpagar.controllers;

import com.cuentasporpagar.daos.AutorizarPagoDAO;
import com.cuentasporpagar.daos.FacturaDAO;
import com.cuentasporpagar.models.Factura;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 * /**
 *
 * @author PAOLA
 */
//@Named(value="uuu")
@ManagedBean(name = "autorizarPagorMB")
@ViewScoped
public class AutorizarPagoManagedBean implements Serializable {

    private FacturaDAO facturaDAO;
    private Factura factura;
    private List<Factura> listaDatos;
    private AutorizarPagoDAO AutorizarDao;
    private boolean check;

    /**
     * Creates a new instance of AutorizarPago
     */
    public AutorizarPagoManagedBean() {
        facturaDAO = new FacturaDAO();
        factura = new Factura();
        listaDatos = new ArrayList<>();
        listaDatos = facturaDAO.llenar();
        
    }

    public void mostrar() {
        listaDatos.clear();
        listaDatos = facturaDAO.llenar();
        System.out.println(listaDatos.size() + "holis");
    }

    public void cargarDatos(Factura factura) {
        System.out.println("CANTIDAD DETALLE visualizar: " + listaDatos.size());
        System.out.println(factura.getNfactura());
        String dato = factura.getNfactura();
        this.listaDatos.clear();
        this.listaDatos=AutorizarDao.llenarDatos(factura.getNfactura());
//
//        
//        this.listaDatos = this.AutorizarDao.llenarDetalle(dato);
//        System.out.println("CANTIDAD DETALLE VIUALIZAR2: " + listaDatos.size());
    }

    public void insertarfactura() {
        try {
        } catch (Exception e) {
            System.out.println(e + "ERROR DAO");
        }
    }

    public AutorizarPagoDAO getAutorizarDao() {
        return AutorizarDao;
    }

    public void setAutorizarDao(AutorizarPagoDAO AutorizarDao) {
        this.AutorizarDao = AutorizarDao;
    }

    public FacturaDAO getFacturaDAO() {
        return facturaDAO;
    }

    public void setFacturaDAO(FacturaDAO facturaDAO) {
        this.facturaDAO = facturaDAO;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public List<Factura> getListaDatos() {
        return listaDatos;
    }

    public void setListaDatos(List<Factura> listaDatos) {
        this.listaDatos = listaDatos;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean cheack) {
        this.check = cheack;
    }

    public void Registro(String estado) {
        String detail = check ? "Pago Autorizado" : "Pago no Autorizado";
        if (detail == "Pago Autorizado") {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(detail));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(detail));
        }
    }
}
