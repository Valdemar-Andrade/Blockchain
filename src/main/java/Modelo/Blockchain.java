/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Blowfish;

/**
 *
 * @author valdemar
 */
public class Blockchain implements Serializable {

    private static final long serialVersionUID = 1L;

    public ArrayList<Bloco> chain;

    public Blockchain () {

        chain = new ArrayList<>();

        try {
            Blowfish blowfish = new Blowfish();
            String conteudo = "Bloco Genesis";
            conteudo = blowfish.criptografar( conteudo );
            criarBlocoGenesis( conteudo );

            System.out.println( "Bloco Genesis criado" );
        }
        catch ( Exception ex ) {
            System.out.println( "Erro ao criar bloco Genesis" );
        }

    }

    public void criarBlocoGenesis ( String conteudo ) throws Exception {

        Blowfish blowfish = new Blowfish();

        Bloco blocoGenesis = new Bloco( 0, new Date().getTime(), conteudo, "0" );

        blocoGenesis.minerarBloco();
        chain.add( blocoGenesis );
    }

    public Bloco getUltimoBloco () {
        return chain.get( chain.size() - 1 );
    }

    public void adicionarBlocoNaBlockchain ( Bloco novoBloco ) {

        novoBloco.setHashDoBlocoAnterior( getUltimoBloco().getHash() );
        novoBloco.minerarBloco();
        chain.add( novoBloco );
    }

    public boolean blockchainEhValida () {

        for ( int i = 1; i < chain.size(); i++ ) {

            Bloco blocoAtual = chain.get( i );
            Bloco blocoAnterior = chain.get( i - 1 );

            if ( !blocoAtual.getHash().equals( blocoAtual.calcularHashDoBloco() ) ) {
                return false;
            }

            if ( !blocoAtual.getHashDoBlocoAnterior().equals( blocoAnterior.getHash() ) ) {
                return false;
            }
        }

        return true;
    }

    public boolean validarBloco ( Bloco bloco ) {

        if ( !bloco.getHashDoBlocoAnterior().equals( getUltimoBloco().getHash() ) ) {
            return false;
        }

        if ( !bloco.getHash().equals( bloco.calcularHashDoBloco() ) ) {
            return false;
        }

        return true;
    }

    public List<Bloco> pesquisarBlocos ( String pesquisa ) {

        List<Bloco> blocosEncontrados = new ArrayList<>();

        Blowfish blow;
        try {
            blow = new Blowfish();
            for ( Bloco bloco : chain ) {
                if ( blow.desencriptar( bloco.getConteudoDoBloco() ).contains( pesquisa ) ) {
                    blocosEncontrados.add( bloco );
                }
            }
        }
        catch ( Exception ex ) {
            Logger.getLogger( Blockchain.class.getName() ).log( Level.SEVERE, null, ex );
        }
        
        return blocosEncontrados;
    }

}
