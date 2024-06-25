/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import DAO.UsuarioDAO;
import Modelo.Blockchain;
import Modelo.Bloco;
import Modelo.Usuario;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import utils.BlockchainUtil;

/**
 *
 * @author valdemar
 */
@MultipartConfig
@WebServlet(name = "CarregarFicheiro", urlPatterns = { "/CarregarFicheiro" })
public class CarregarFicheiro extends HttpServlet {

    private HashMap<String, String> usuarios = new HashMap<>();

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

        HttpSession sessao = request.getSession();
        String email = ( String ) sessao.getAttribute( "email" );

        if ( email == null ) {
            response.sendRedirect( "index.jsp" );
            return;
        }

        Part filePart = request.getPart( "file" );
        String nomeArquivo = Paths.get( filePart.getSubmittedFileName() ).getFileName().toString();

        File uploads = new File( "uploads" );
        if ( !uploads.exists() ) {
            uploads.mkdirs();
        }

        File file = new File( uploads, nomeArquivo );
        filePart.write( file.getAbsolutePath() );

        // Converter o arquivo para um formato que seja possivel armazenar na blockchain
        String conteudoDoArquivo = new String( Files.readAllBytes( file.toPath() ) );

        Blockchain blockchain = BlockchainUtil.carregarBlockchain( email + "_blockchain.ucan" );

        Bloco novoBloco = new Bloco( blockchain.chain.size(), new Date().getTime(), conteudoDoArquivo, blockchain.getUltimoBloco().getHash() );
        blockchain.adicionarBlocoNaBlockchain( novoBloco );

        BlockchainUtil.salvarBlockchain( blockchain, email + "_blockchain.ucan" );

        // Propagar atualização para todos os usuários
        for ( String user : usuarios.keySet() ) {
            BlockchainUtil.salvarBlockchain( blockchain, user + "_blockchain.ucan" );
        }

        try {
            new UsuarioDAO().listar().stream().forEach( ( Usuario usuario ) -> {
                BlockchainUtil.salvarBlockchain( blockchain, usuario.getEmail() + "_blockchain.ucan" );
            } );
        }
        catch ( SQLException ex ) {
            System.out.println( "Erro ao salvar blockchain para o usuário: " );
            ex.printStackTrace();
        }

        /*/ Adicionar o conteudo do ficheiro na blockchain
        Blockchain blockchain = ( Blockchain ) getServletContext().getAttribute( "blockchain" );

        if ( blockchain == null ) {
            blockchain = new Blockchain();
            getServletContext().setAttribute( "blockchain", blockchain );
        }

        Blowfish blowfish;

        try {
            blowfish = new Blowfish();
            String encryptedData = blowfish.encriptar( conteudoDoArquivo );
            Bloco newBlock = new Bloco( blockchain.chain.size(), new Date().getTime(), encryptedData, blockchain.getUltimoBloco().getHash() );
            blockchain.adicionarBlocoNaBlockchain( newBlock );
        }
        catch ( Exception ex ) {
            Logger.getLogger( CarregarFicheiro.class.getName() ).log( Level.SEVERE, null, ex );
        }*/
        response.getWriter().println( "Ficheiro carregado e adicionado na blockchain com Suceso!" );

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
