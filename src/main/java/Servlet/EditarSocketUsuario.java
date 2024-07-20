/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import DAO.SocketUsuarioDAO;
import Modelo.SocketUsuario;
import Sockets.BlockchainPeer;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utils.DadosUsuarioSessao;

/**
 *
 * @author valdemar
 */
@WebServlet(name = "EditarSocketUsuario", urlPatterns = { "/EditarSocketUsuario" })
public class EditarSocketUsuario extends HttpServlet {

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

        if ( request.getServletPath().equals( "/EditarSocketUsuario" ) ) {

            String endereco = request.getParameter( "endereco" );
            String porta = request.getParameter( "porta" );

            HttpSession sessao = request.getSession();

            if ( validarEndereco( endereco ) && !porta.isEmpty() ) {
                SocketUsuarioDAO socketUsuarioDao = new SocketUsuarioDAO();
                SocketUsuario socketUsuario = ( SocketUsuario ) sessao.getAttribute( "socketUsuario" );

                socketUsuario.setEndereco( endereco );
                socketUsuario.setPorta( porta );

                try {
                    socketUsuarioDao.actualizar( socketUsuario.getPk_socket(), socketUsuario );
                    logout( sessao );

                    response.sendRedirect( "index.jsp" );
                }
                catch ( SQLException ex ) {
                    System.out.println( "Erro ao atualizar os dados do SocketUsuario!" );
                }
            }else{
                response.sendRedirect( "blockchain_usuario.jsp" );
            }

        }

    }

    private boolean validarEndereco ( String endereco ) {

        //192.168.8.1
        int pontosTotal = 3;
        int pontos = 0;

        for ( int i = 0; i < endereco.length() - 1; i++ ) {
            if ( endereco.charAt( i ) == '.' ) {
                pontos += 1;
            }
        }

        return ( pontos == pontosTotal );

    }

    private void logout ( HttpSession sessao ) {

        if ( sessao != null ) {
            BlockchainPeer peer = ( BlockchainPeer ) sessao.getAttribute( "blockchainPeer" );

            if ( peer != null ) {
                //peer.fecharSocket();
            }

            sessao.invalidate();
            DadosUsuarioSessao.email = null;
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
