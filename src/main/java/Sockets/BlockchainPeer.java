/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sockets;

import Modelo.Blockchain;
import Modelo.Bloco;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import utils.BlockchainUtil;
import utils.DadosUsuarioSessao;

/**
 *
 * @author valdemar
 */
public class BlockchainPeer {

    private Blockchain blockchain;
    private List<PeerConnection> peers = new ArrayList<>();
    private int porta;
    private ReentrantLock lock = new ReentrantLock();

    public BlockchainPeer ( int porta ) {
        this.blockchain = BlockchainUtil.carregarBlockchain( "/Users/valdemar/apache-tomcat-9.0.71/bin/" + DadosUsuarioSessao.email + "_blockchain.ucan" );
        this.porta = porta;
    }

    public void start () {
        new Thread( () -> {
            try ( ServerSocket serverSocket = new ServerSocket( porta ) ) {
                System.out.println( "BlockchainPeer iniciado na porta: " + porta );
                while ( true ) {
                    Socket peerSocket = serverSocket.accept();
                    PeerConnection peerConnection = new PeerConnection( peerSocket );
                    peers.add( peerConnection );
                    new Thread( new PeerHandler( peerConnection ) ).start();
                }
            }
            catch ( IOException e ) {
                e.printStackTrace();
            }
        } ).start();
    }

    public void conectarAoPeer ( String host, int port ) throws IOException {
        Socket socket = new Socket( host, port );
        
        PeerConnection peerConnection = new PeerConnection( socket );
        peers.add( peerConnection );
        
        new Thread( new PeerHandler( peerConnection ) ).start();
        enviarBlockchainInteira( peerConnection.getOutputStream() );
    }

    private void enviarBlockchainInteira ( ObjectOutputStream oos ) throws IOException {
        oos.writeObject( blockchain );
        oos.flush();
        oos.reset();
    }

    public void adicionarBloco ( Bloco bloco ) {
        lock.lock();
        try {
            blockchain.adicionarBlocoNaBlockchain( bloco );
            BlockchainUtil.salvarBlockchain( blockchain, "/Users/valdemar/apache-tomcat-9.0.71/bin/" + DadosUsuarioSessao.email + "_blockchain.ucan" );
            propagarNovoBloco( bloco );
        }
        finally {
            lock.unlock();
        }
    }

    private void propagarNovoBloco ( Bloco bloco ) {
        peers.forEach( peer -> {
            try {
                ObjectOutputStream oos = peer.getOutputStream();
                oos.writeObject( bloco );
                oos.flush();
                oos.reset();
            }
            catch ( IOException e ) {
                e.printStackTrace();
            }
        } );
    }

    public boolean validarBlocoComPeers ( Bloco bloco ) {
        List<Boolean> validacoes = new ArrayList<>();
        peers.forEach( peer -> {
            try {
                ObjectOutputStream oos = peer.getOutputStream();
                oos.writeObject( bloco );
                oos.flush();
                oos.reset();
                ObjectInputStream ois = peer.getInputStream();
                boolean validacao = ois.readBoolean();
                validacoes.add( validacao );
            }
            catch ( IOException e ) {
                e.printStackTrace();
            }
        } );

        // lógica de consenso
        long countAprovado = validacoes.stream().filter( v -> v ).count();
        return countAprovado > ( validacoes.size() / 2 );
    }

    private class PeerHandler implements Runnable {

        private PeerConnection peerConnection;

        public PeerHandler ( PeerConnection peerConnection ) {
            this.peerConnection = peerConnection;
        }

        @Override
        public void run () {
            try {
                ObjectInputStream ois = peerConnection.getInputStream();
                while ( true ) {
                    try {
                        Object objetoRecebido = ois.readObject();
                        System.out.println( "Objeto recebido: " + objetoRecebido.getClass().getName() );
                        
                        if ( objetoRecebido instanceof Blockchain ) {
                            Blockchain blockchainRecebida = ( Blockchain ) objetoRecebido;
                            
                            if ( blockchainRecebida.blockchainEhValida() ) {
                                blockchain = blockchainRecebida;
                                BlockchainUtil.salvarBlockchain( blockchain, "/Users/valdemar/apache-tomcat-9.0.71/bin/" + DadosUsuarioSessao.email + "_blockchain.ucan" );
                            }
                        }
                        else if ( objetoRecebido instanceof Bloco ) {
                            Bloco blocoRecebido = ( Bloco ) objetoRecebido;
                            boolean validacao = blockchain.validarBloco( blocoRecebido );
                            
                            ObjectOutputStream oos = peerConnection.getOutputStream();
                            oos.writeBoolean( validacao );
                            oos.flush();
                            
                            if ( validacao ) {
                                lock.lock();
                                try {
                                    blockchain.adicionarBlocoNaBlockchain( blocoRecebido );
                                    BlockchainUtil.salvarBlockchain( blockchain, "/Users/valdemar/apache-tomcat-9.0.71/bin/" + DadosUsuarioSessao.email + "_blockchain.ucan" );
                                }
                                finally {
                                    lock.unlock();
                                }
                            }
                        }
                    }
                    catch ( OptionalDataException e ) {
                        ois.skipBytes( e.length );
                    }
                    catch ( EOFException e ) {
                        break;
                    }
                }
            }
            catch ( IOException | ClassNotFoundException e ) {
                e.printStackTrace();
            }
        }
    }

    private static class PeerConnection {

        private final Socket socket;
        private final ObjectOutputStream oos;
        private final ObjectInputStream ois;

        public PeerConnection ( Socket socket ) throws IOException {
            this.socket = socket;
            this.oos = new ObjectOutputStream( socket.getOutputStream() );
            this.ois = new ObjectInputStream( socket.getInputStream() );
        }

        public ObjectOutputStream getOutputStream () {
            return oos;
        }

        public ObjectInputStream getInputStream () {
            return ois;
        }
    }

    public Blockchain getBlockchain () {
        return blockchain;
    }
}
