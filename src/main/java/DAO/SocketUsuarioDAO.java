/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Modelo.SocketUsuario;
import java.sql.SQLException;

/**
 *
 * @author valdemar
 */
public class SocketUsuarioDAO extends DAOImplementacao<SocketUsuario, Integer> {

    public SocketUsuarioDAO () {
        super( new SocketUsuario() );
    }

    public SocketUsuario getSocketUsuarioByPk ( Integer pk_usuario ) {

        SocketUsuario su;
        SocketUsuarioDAO socketUsuario = new SocketUsuarioDAO();
        
        try {
            su = socketUsuario.listar().stream().filter( u -> u.getFk_usuario().equals( pk_usuario ) ).findFirst().get();
            return su;
        }
        catch ( SQLException ex ) {
        }

        return new SocketUsuario();

    }

}
