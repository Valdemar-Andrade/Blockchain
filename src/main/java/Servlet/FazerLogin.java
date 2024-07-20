/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import DAO.LoginDAO;
import DAO.SocketUsuarioDAO;
import DAO.UsuarioDAO;
import Modelo.PeerInformacao;
import Modelo.SocketUsuario;
import Sockets.BlockchainPeer;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utils.DadosUsuarioSessao;
import utils.PeersOnline;

/**
 *
 * @author valdemar
 */
@WebServlet(name = "FazerLogin", urlPatterns = { "/FazerLogin" })
public class FazerLogin extends HttpServlet {

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

        if ( request.getServletPath().equals( "/FazerLogin" ) ) {

            String email = request.getParameter( "email" );
            String senha = request.getParameter( "senha" );

            try {

                if ( autenticarLogin( email, senha ) ) {

                    UsuarioDAO usuarioDao = new UsuarioDAO();
                    SocketUsuarioDAO socketUsuarioDao = new SocketUsuarioDAO();

                    String usuario = usuarioDao.listar().stream().filter( usu -> usu.getEmail().equals( email ) ).findFirst().get().getNome();
                    int idUsuario = usuarioDao.listar().stream().filter( usu -> usu.getEmail().equals( email ) ).findFirst().get().getPkUsuario();
                    SocketUsuario socketUsuario = socketUsuarioDao.listar().stream().filter( soc -> soc.getFk_usuario().equals( idUsuario ) ).findFirst().get();

                    // Inicializar o nó do blockchain para o usuário logado
                    //int porta = 5000 + idUsuario;
                    int porta = Integer.parseInt( socketUsuario.getPorta() );
                    
                    BlockchainPeer peer = new BlockchainPeer( porta );
                    peer.start();
                    System.out.println( "BlockchainPeer iniciado na porta: " + porta );

                    PeersOnline.adicionarPeerOnline( new PeerInformacao( "localhost", porta ) );
                    System.out.println( "Peer adicionado à lista de peers online: localhost:" + porta );

                    // Conectar-se a outros nós conhecidos
                    List<PeerInformacao> peersOnline = getPeersOnline();

                    for ( PeerInformacao peerInfo : peersOnline ) {
                        try {
                            System.out.println( "Conectando ao peer: " + peerInfo.getHost() + ":" + peerInfo.getPorta() );
                            peer.conectarAoPeer( peerInfo.getHost(), peerInfo.getPorta() );
                            System.out.println( "Conectado ao peer: " + peerInfo.getHost() + ":" + peerInfo.getPorta() );
                        }
                        catch ( IOException e ) {
                            System.err.println( "Erro ao conectar ao peer: " + peerInfo.getHost() + ":" + peerInfo.getPorta() );
                            e.printStackTrace();
                        }
                    }

                    HttpSession sessao = request.getSession();
                    sessao.setAttribute( "email", email );
                    sessao.setAttribute( "usuario", usuario );
                    sessao.setAttribute( "blockchainPeer", peer );
                    sessao.setAttribute( "socketUsuario", socketUsuario );

                    DadosUsuarioSessao.email = email;

                    response.sendRedirect( "blockchain_usuario.jsp" );

                }
                else {

                    String mensagemErro = "Credenciais invalidas";
                    response.sendRedirect( "index.jsp?mensagemErro=" + mensagemErro );
                }
            }
            catch ( SQLException ex ) {
            }
        }
    }

    private boolean autenticarLogin ( String email, String senha ) {

        return new LoginDAO().autenticarLogin( email, senha );

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

    private List<PeerInformacao> getPeersOnline () {

        return PeersOnline.getPeersOnline().size() > 1 ? PeersOnline.getPeersOnline() : new ArrayList<>();

    }

}
