/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import Modelo.Blockchain;
import Modelo.Bloco;
import Sockets.BlockchainPeer;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import utils.BlockchainUtil;
import utils.Blowfish;

/**
 *
 * @author valdemar
 */
@MultipartConfig
@WebServlet(name = "CarregarFicheiro", urlPatterns = { "/CarregarFicheiro" })
public class CarregarFicheiro extends HttpServlet {

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

        if ( request.getServletPath().equals( "/CarregarFicheiro" ) ) {

            request.setCharacterEncoding( "UTF-8" );
            response.setCharacterEncoding( "UTF-8" );
            String diretorio = "/Users/valdemar/apache-tomcat-9.0.71/bin/";

            HttpSession sessao = request.getSession();
            String email = ( String ) sessao.getAttribute( "email" );

            if ( email == null ) {
                response.sendRedirect( "index.jsp" );
            }

            String ehTexto = request.getParameter( "texto" );

            if ( ehTexto.equals( "true" ) ) {

                String conteudo = request.getParameter( "texto-simples" );

                String nomeArquivo = email + "_blockchain.ucan";

                Blockchain blockchain = BlockchainUtil.carregarBlockchain( diretorio + nomeArquivo );

                //Para criptografar os dados
                String encriptado = encriptarConteudoDoBloco( conteudo );

                Bloco novoBloco = new Bloco( blockchain.chain.size(), new Date().getTime(), encriptado, blockchain.getUltimoBloco().getHash() );
                blockchain.adicionarBlocoNaBlockchain( novoBloco );

                BlockchainUtil.salvarBlockchain( blockchain, diretorio + nomeArquivo );

                // Propagar atualização para todos os usuários
                BlockchainPeer blockchainPeer = ( BlockchainPeer ) request.getSession().getAttribute( "blockchainPeer" );
                blockchainPeer.adicionarBloco( novoBloco );

            }

            if ( ehTexto.equals( "false" ) ) {

                Part filePart = request.getPart( "arquivo" );
                String nomeArquivo = Paths.get( filePart.getSubmittedFileName() ).getFileName().toString();

                File uploads = new File( "uploads" );
                if ( !uploads.exists() ) {
                    uploads.mkdirs();
                }

                File file = new File( uploads, nomeArquivo );
                filePart.write( file.getAbsolutePath() );

                String filePath = file.getAbsolutePath();

                Blockchain blockchain = BlockchainUtil.carregarBlockchain( email + "_blockchain.ucan" );

                String encriptar = "FILE:" + filePath;
                String encriptado = encriptarConteudoDoBloco( encriptar );

                Bloco novoBloco = new Bloco( blockchain.chain.size(), new Date().getTime(), encriptado, blockchain.getUltimoBloco().getHash() );
                blockchain.adicionarBlocoNaBlockchain( novoBloco );

                BlockchainUtil.salvarBlockchain( blockchain, email + "_blockchain.ucan" );

                // Propagar atualização para todos os usuários
                BlockchainPeer blockchainPeer = ( BlockchainPeer ) request.getSession().getAttribute( "blockchainPeer" );
                blockchainPeer.adicionarBloco( novoBloco );

            }

            response.sendRedirect( "blockchain_usuario.jsp" );
        }

    }

    private String encriptarConteudoDoBloco ( String conteudo ) {

        //Para criptografar os dados
        Blowfish blowfish;

        try {
            blowfish = new Blowfish();
            conteudo = blowfish.criptografar(conteudo );
        }
        catch ( Exception ex ) {
            Logger.getLogger( CarregarFicheiro.class.getName() ).log( Level.SEVERE, null, ex );
        }

        return conteudo;

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
