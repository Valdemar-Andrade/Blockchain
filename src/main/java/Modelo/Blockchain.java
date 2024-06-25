/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author valdemar
 */
public class Blockchain {

    public ArrayList<Bloco> chain;

    public Blockchain () {

        chain = new ArrayList<>();

        criarBlocoGenesis();
    }

    public void criarBlocoGenesis () {

        Bloco blocoGenesis = new Bloco( 0, new Date().getTime(), "Bloco Genesis", "0" );

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

}
