/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author valdemar
 */
public class Blockchain {

    private List<Bloco> chain;

    public Blockchain () {

        this.chain = new ArrayList<>();
        this.chain.add( criarBlocoGeneses() );

    }

    private Bloco criarBlocoGeneses () {
        return new Bloco( 0, "0", null );
    }

    private Bloco obterUltimoBloco () {
        return this.chain.get( chain.size() - 1 );
    }

    private void adicionarBloco ( Bloco novoBloco ) {

        novoBloco.setHashBlocoAnterior( this.obterUltimoBloco().getHash() );
        novoBloco.minerarBloco( 4 );
        this.chain.add( novoBloco );

    }

    public boolean isChainValida () {

        for ( int i = 1; i < this.chain.size(); i++ ) {
            
            Bloco blocoAtual = this.chain.get( i );
            Bloco blocoAnterior = this.chain.get( i - 1);
           
            if(!blocoAtual.getHash().equals( blocoAtual.calcularHashDoBloco())){
                
                System.out.println( "Hash do bloco atual nao e igual" );
                
                return false;
            }
            
            if(!blocoAtual.getHashBlocoAnterior().equals( blocoAnterior.getHash())){
                
                System.out.println( "Hash do bloco anterior nao e igual" );
                
                return false;
                
            }
            
        }
        
        return true;
    }

}
