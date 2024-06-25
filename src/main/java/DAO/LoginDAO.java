/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Modelo.Login;
import java.sql.SQLException;

/**
 *
 * @author valdemar
 */
public class LoginDAO extends DAOImplementacao<Login, Integer> {

    public LoginDAO () {
        super( new Login() );
    }
    
    public boolean autenticarLogin ( String email, String senha ) {

        LoginDAO loginDao = new LoginDAO();

        try {
            return loginDao.listar().stream().anyMatch( login -> login.getSenha().equals( senha ) && login.getEmail().equals( email ) );
        }
        catch ( SQLException ex ) {
            ex.getMessage();
        }

        return false;

    }

}
