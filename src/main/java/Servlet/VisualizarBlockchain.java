/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import Modelo.Blockchain;
import Modelo.Bloco;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.Blowfish;

/**
 *
 * @author valdemar
 */
@WebServlet(name = "VisualizarBlockchain", urlPatterns = { "/VisualizarBlockchain" })
public class VisualizarBlockchain extends HttpServlet {

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

        Blockchain blockchain = ( Blockchain ) getServletContext().getAttribute( "blockchain" );

        StringBuilder sb = new StringBuilder();

        for ( Bloco block : blockchain.chain ) {
            String decryptedData;

            try {
                Blowfish blowfish = new Blowfish();
                decryptedData = blowfish.desencriptar( block.getConteudoDoBloco() );
                
                sb.append( "Bloco #" ).append( block.getIndex().append( ": " ).append( decryptedData ).append( "<br>" ) );
            }
            catch ( Exception ex ) {
                Logger.getLogger( VisualizarBlockchain.class.getName() ).log( Level.SEVERE, null, ex );
            }

        }

        request.setAttribute( "blockchainData", sb.toString() );
        request.getRequestDispatcher( "/visualizarBlockchain.jsp" ).forward( request, response );

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
