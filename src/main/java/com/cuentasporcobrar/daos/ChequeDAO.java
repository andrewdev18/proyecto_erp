/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuentasporcobrar.daos;

import com.cuentasporcobrar.models.Cheque;
import com.global.config.Conexion;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ChequeDAO implements Serializable{
    List<Cheque> lista_cheques;
    Conexion conex;
    Cheque cheque;
    ResultSet result;
    
    //Constructor sin parámetros, para iniciar una conexion.

    public ChequeDAO() {
        conex = new Conexion();
    }
   
    //Constructor que recibe el objeto Cheque e inicia una nueva conexion.
    public ChequeDAO(Cheque cheque) {
        conex = new Conexion();
        this.cheque = cheque;
    }

    public ChequeDAO(List<Cheque> lista_cheques, Conexion conex, Cheque cheque, ResultSet result) {
        this.lista_cheques = lista_cheques;
        this.conex = conex;
        this.cheque = cheque;
        this.result = result;
    }
   
    /*Procedimiento para insertar un nuevo cheque. Caso falle retornará -1*/
    public int insertarNuevoCheque(int idVenta) {
        /*Se ubica en el siguiente orden: 
        (codigo del banco, id de la factura/venta, la fecha de cobro, 
            numero de cuenta, y el estado del cheque (Cobrado, Pendiente,
            protestado, Rechazado y revocado))*/
        String sentenciaSQL = "Select Ingresar_Cheque("+cheque.getCodBanco()+","
                +idVenta+","+cheque.getFecha()+","+cheque.getNumeroDeCuenta()+","+cheque.getEstadoCheque()+")";

        //Verificamos la conexion
        if (conex.isEstado()) {

            /*Una vez se asegura que la conexion este correcta.
            Se ejecuta la sentencia ingresada.*/
            return conex.ejecutarProcedimiento(sentenciaSQL);

        }

        return -1;
    }
    
    //Modificar/Actualizar un Cheque, retorn 1 o -1 dependiendo si la función 
    //ejecuta correctamente
    public int actualizarCheque(int idCheque){
        String sentenciaSQL="select actualizar_cheque("+idCheque+","
                +cheque.getCodBanco()+","+cheque.getIdVenta()+","
                +cheque.getFecha()+","+cheque.getNumeroDeCuenta()+","
                +cheque.getEstadoCheque()+")";
        
        //Verificamos la conexion
        if (conex.isEstado()) {
            
            //Una vez se asegura que la conexion este correcta.
            //Se ejecuta la sentencia ingresada.
            return conex.ejecutarProcedimiento(sentenciaSQL);
            
        }
        //Caso contrario: Se retorna -1 indicando que la conexión está
        //en estado Falso
        return -1;
        
    }   
    
    //Funcion para enlistar todos los cheques un cliente.
    public List<Cheque> obtenerChequesDeCliente(int idCliente){
        lista_cheques=new ArrayList<>();
        
        //verificamos la conexion
         if(conex.isEstado()){
             try{
                 /* Se obtiene una TABLA con todos los cheques que se receptaron
                 de un cliente a una determinada factura.*/
               String sentencia="Select*from obtener_cheques_de_clientes("+idCliente+")";
               result = conex.ejecutarConsulta(sentencia);
               
                //Recorremos la TABLA retornada y la almacenamos en la lista.
               while(result.next()){
                   
                   lista_cheques.add(new Cheque(result.getInt("idcheque_r"),
                           result.getInt("idventa_r"),
                           result.getString("nombrecliente_r"),
                           result.getString("codbanco_r"),
                           result.getString("nombrebanco_r"),
                           result.getString("numerodecuenta_r"),
                           result.getString("estadocheque_r"),
                           result.getObject("estadocheque_r", LocalDate.class )));
                   
               }
               
             } catch (SQLException ex) {
                /*Enviamos su respectivo mensaje de error a su ves una lista 
                    con valores incorrectos.*/
                System.out.println(ex.getMessage());
                lista_cheques.add(new Cheque(-1,
                           -1,
                           "",
                           "",
                           "",
                           "",
                           "",
                           null));

                
            } finally {
                
                conex.cerrarConexion();

            }
         }
        
        return lista_cheques;
    }
}

