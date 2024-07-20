/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import DAO.LoginDAO;
import DAO.SocketUsuarioDAO;
import DAO.UsuarioDAO;
import Modelo.Login;
import Modelo.SocketUsuario;
import Modelo.Usuario;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author valdemar
 */
@WebServlet(name = "CadastrarConta", urlPatterns = { "/CadastrarConta" })
public class CadastrarConta extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest ( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        if ( request.getServletPath().equals( "/CadastrarConta" ) ) {

            String nome = request.getParameter( "nome" );
            String email = request.getParameter( "email" );
            String senha = request.getParameter( "senha" );
            String endereco = request.getParameter( "endereco" );
            String porta = request.getParameter( "porta" );

            UsuarioDAO usuarioDao = new UsuarioDAO();
            LoginDAO loginDao = new LoginDAO();
            SocketUsuarioDAO socketUsuarioDao = new SocketUsuarioDAO();

            Usuario usuario = new Usuario();
            Login login = new Login();
            SocketUsuario socketUsuario = new SocketUsuario();

            int pkUsuario = 1, pkLogin = 1, pkSocket = 1;

            try {

                List<Usuario> usuarios = usuarioDao.listar();
                List<Login> logins = loginDao.listar();
                List<SocketUsuario> socketUsuarios = socketUsuarioDao.listar();

                if ( !usuarios.isEmpty() && !logins.isEmpty() && !socketUsuarios.isEmpty() ) {

                    pkUsuario = usuarios.get( usuarios.size() - 1 ).getPkUsuario() + 1;
                    pkLogin = logins.get( logins.size() - 1 ).getPkLogin() + 1;
                    pkSocket = socketUsuarios.get( socketUsuarios.size() - 1 ).getPk_socket() + 1;

                }

                //Usuario
                usuario.setPkUsuario( pkUsuario );
                usuario.setNome( nome );
                usuario.setEmail( email );

                usuarioDao.adicionar( usuario );

                //Login do Usuario
                login.setPkLogin( pkLogin );
                login.setEmail( email );
                login.setSenha( senha );
                login.setFkUsuario( pkUsuario );

                loginDao.adicionar( login );

                //Socket do Usuario
                socketUsuario.setPk_socket( pkSocket );
                socketUsuario.setEndereco( endereco );
                socketUsuario.setPorta( porta );
                socketUsuario.setFk_usuario( pkUsuario );

                socketUsuarioDao.adicionar( socketUsuario );

                response.sendRedirect( "index.jsp" );

            }
            catch ( SQLException ex ) {
                System.out.println( "Erro criar conta" );
                ex.printStackTrace();
                response.sendRedirect( "criar_conta.jsp" );
            }
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet ( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        processRequest( request, response );
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost ( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        processRequest( request, response );
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo () {
        return "Short description";
    }// </editor-fold>

}
