/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Modelo.Login;
import config.GerenciadorConexaoDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author valdemar
 */
public class LoginDAO extends DAOImplementacao<Login, Integer> {

    private GerenciadorConexaoDB conexaoBD;
    private final Connection conexao;

    public LoginDAO () {
        super( new Login() );

        conexaoBD = GerenciadorConexaoDB.getInstance();
        conexaoBD.abrirConexao();
        conexao = conexaoBD.obterConexao();
    }

    public List<Login> listarLogin () throws SQLException {
        List<Login> logins = new ArrayList<>();
        String sql = "SELECT pk_login, email, senha, fk_usuario FROM login";

        try ( Connection conn = conexao; PreparedStatement stmt = conn.prepareStatement( sql ); ResultSet rs = stmt.executeQuery() ) {
            while ( rs.next() ) {

                int pk_login = rs.getInt( "pk_login" );
                String email = rs.getString( "email" );
                String senha = rs.getString( "senha" );
                int fk_usuario = rs.getInt( "fk_usuario" );

                logins.add( new Login( pk_login, email, senha, fk_usuario ) );
            }
        }

        return logins;
    }

    public boolean autenticarLogin ( String email, String senha ) {

        LoginDAO loginDao = new LoginDAO();

        try {
            /*/List<Login> logins = loginDao.listar();
            return loginDao.listar().stream().anyMatch( login -> login.getSenha().equals( senha ) && login.getEmail().equals( email ) );
            
            for ( Login login : logins ) {
                if ( login.getEmail().equals( email ) && login.getSenha().equals( senha ) ) {
                    return true;
                }
            }
             */
            //return false;

            System.out.println( "Iniciando autenticação para o usuário: " + email );
            List<Login> logins = loginDao.listarLogin();
            System.out.println( "Lista de logins obtida: " + logins.size() );

            boolean autenticado = logins.stream().anyMatch( login -> login.getSenha().equals( senha ) && login.getEmail().equals( email ) );
            System.out.println( "Autenticação resultado: " + autenticado );

            return autenticado;
        }
        catch ( SQLException ex ) {
            System.err.println( "Erro ao listar logins: " + ex.getMessage() );
            ex.printStackTrace();
        }

        return false;

    }

}
