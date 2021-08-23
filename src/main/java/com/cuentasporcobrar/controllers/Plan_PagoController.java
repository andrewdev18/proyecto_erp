/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuentasporcobrar.controllers;

import com.cuentasporcobrar.daos.PersonaDAO;
import com.cuentasporcobrar.daos.Plan_PagoDAO;
import com.cuentasporcobrar.models.Persona;
import com.cuentasporcobrar.models.Plan_Pago;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named(value = "plan_PagoController")
@ViewScoped
public class Plan_PagoController implements Serializable {

    //Se Declaran las clases Plan_Pago y Plan_PagoDAO
    Plan_Pago plan_Pago;
    Plan_PagoDAO plan_PagoDAO;

    //Objeto Persona y Persona DAO para buscar un cliente
    Persona persona;
    PersonaDAO personaDAO;

    //Lista donde se cargaran los cobros
    List<Plan_Pago> lista_Cobros;

    //Variable con la identificacion;
    String identificacion = "";

    public Plan_PagoController() {

    }

    public void buscarCliente() {
        try{
        personaDAO = new PersonaDAO();
        persona = new Persona();
        //Cargamos el nombre del cliente en el input
        persona = personaDAO.obtenerNombreClienteXIdentificacion(identificacion);
        //Este if nos permite verificar si existe o no un cliente.
        if (persona.getIdCliente() == 0) {
            System.out.println("El Cliente NO EXISTE O ESTA INACTIVO ");
            mostrarMensajeInformacion("El Cliente No Existe o esta Inactivo");
        } else {
            // En caso de que exista cargamos sus cobros (Claro si tiene cobros)
            lista_Cobros = new ArrayList<>();
            plan_PagoDAO = new Plan_PagoDAO();

            //Cargamos los cobros de un determinado Cliente.
            lista_Cobros = plan_PagoDAO.obtenerCobrosCliente(persona.getIdCliente());

            //Este if valida si el cliente tiene o no cobros.
            if (lista_Cobros.isEmpty()) {
                mostrarMensajeInformacion("Ese cliente no tiene cobros");
            } else {
                mostrarMensajeInformacion("Se Cargaron los Cobros de " + persona.getRazonNombre());
                
            }
        }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    //Getters y Setters de las Listas
    //Inicio
    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Plan_PagoController(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public List<Plan_Pago> getLista_Cobros() {
        return lista_Cobros;
    }
    //Fin

    //Metodos para mostrar mensajes de Información y Error
    public void mostrarMensajeInformacion(String mensaje) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Éxito: ", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void mostrarMensajeError(String mensaje) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Error: ", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}

