/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuentasporcobrar.controllers;

import com.cuentasporcobrar.daos.Estado_De_CuentaDAO;
import com.cuentasporcobrar.models.Estado_De_Cuenta;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.component.export.ExcelOptions;
import org.primefaces.component.export.PDFOptions;

@Named(value = "estado_De_CuentaController")
@ViewScoped
public class Estado_De_CuentaController implements Serializable {

    //Componentes para tener estilos en las exportaciones
    ExcelOptions excelOpt;
    PDFOptions pdfOpt;
    
    //Se Declaran las clases Estado_De_Cuenta y Estado_De_CuentaDAO
    Estado_De_Cuenta estado_De_Cuenta;
    Estado_De_CuentaDAO estado_De_CuentaDAO;

    //Declaro mi lista con el estado de cuenta general 
    //que van hacer cargadas en el datatable
    List<Estado_De_Cuenta> lista_Estado_De_Cuenta;

    //Declaro mi lista con el estado de cuenta de UN CLIENTE
    //que van hacer cargadas en el datatable
    List<Estado_De_Cuenta> lista_Estado_De_Cuenta_Cliente;//POSIBLEMENTE SE ELIMINE
    //POR QUE NECESITA QUE SE CARGUE EN LA MISMA TABLA DEL GENERAL.

    //Procedimiento principal(Se ejecuta una vez se llame al controlador)
    public Estado_De_CuentaController() {
        //Para que se carguen en el data table el estado de cuenta general 
        estado_De_CuentaDAO =new Estado_De_CuentaDAO();
        lista_Estado_De_Cuenta=new ArrayList<>();
        lista_Estado_De_Cuenta=estado_De_CuentaDAO.obtenerTodosLosEstadosCuenta();
        Design();
    }
    
    public void Design() {
        excelOpt = new ExcelOptions();
        excelOpt.setFacetBgColor("#2E8BE4");
        excelOpt.setFacetFontSize("12");
        excelOpt.setFacetFontColor("#FFFFFF");
        excelOpt.setFacetFontStyle("BOLD");
        excelOpt.setCellFontSize("11");
        excelOpt.setAutoSizeColumn(true);
        excelOpt.setFontName("Roboto");

        pdfOpt = new PDFOptions();
        pdfOpt.setFacetBgColor("#2E8BE4");
        pdfOpt.setFacetFontSize("14");
        pdfOpt.setFacetFontColor("#FFFFFF");
        pdfOpt.setFacetFontStyle("BOLD");
        pdfOpt.setCellFontSize("12");
        pdfOpt.setFontName("Roboto");
    }

    //Getters y Setters de las Listas
    //Inicio
    public List<Estado_De_Cuenta> getLista_Estado_De_Cuenta() {
        return lista_Estado_De_Cuenta;
    }

    public void setLista_Estado_De_Cuenta(List<Estado_De_Cuenta> lista_Estado_De_Cuenta) {
        this.lista_Estado_De_Cuenta = lista_Estado_De_Cuenta;
    }

    public List<Estado_De_Cuenta> getLista_Estado_De_Cuenta_Cliente() {
        return lista_Estado_De_Cuenta_Cliente;
    }

    public void setLista_Estado_De_Cuenta_Cliente(List<Estado_De_Cuenta> lista_Estado_De_Cuenta_Cliente) {
        this.lista_Estado_De_Cuenta_Cliente = lista_Estado_De_Cuenta_Cliente;
    }

    public ExcelOptions getExcelOpt() {
        return excelOpt;
    }

    public void setExcelOpt(ExcelOptions excelOpt) {
        this.excelOpt = excelOpt;
    }

    public PDFOptions getPdfOpt() {
        return pdfOpt;
    }

    public void setPdfOpt(PDFOptions pdfOpt) {
        this.pdfOpt = pdfOpt;
    }
    
    //Fin
}
