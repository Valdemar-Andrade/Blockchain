/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package extra;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author valdemar
 */
public class Blockchain {

    private List<Bloco> chain;
    private List<Transacao> transacoesPendentes;

    public Blockchain () {

        this.chain = new ArrayList<>();
        this.transacoesPendentes = new ArrayList<>();
        this.chain.add( criarBlocoGeneses() );

    }

    private Bloco criarBlocoGeneses () {
        return new Bloco( 0, "0", null );
    }

    private Bloco obterUltimoBloco () {
        return this.chain.get( chain.size() - 1 );
    }

    public void adicionarBloco ( Bloco novoBloco ) {

        novoBloco.setHashBlocoAnterior( this.obterUltimoBloco().getHash() );
        novoBloco.minerarBloco( 4 );
        this.chain.add( novoBloco );

    }

    public void adicionarTransacao ( Transacao transacao ) {

        if ( transacao == null || !transacao.verificarAssinatura() ) {
            System.out.println( "Transação inválida." );
        }
        else {
            transacoesPendentes.add( transacao );
        }
    }
    
    public void minerarTransacoesPendentes() {
        
        Bloco novoBloco = new Bloco(chain.size(), chain.get(chain.size() - 1).getHash(), transacoesPendentes);
        novoBloco.minerarBloco(4 );
        chain.add(novoBloco);
        
        transacoesPendentes.clear();
    }
    
    public double getBalanco(PublicKey chavePublica) {
        
        double balanco = 0;
        
        for (Bloco bloco : chain) {
            for (Transacao transacao : bloco.getTransacoes()) {
                if (transacao.getChaveDoRemetente().equals(chavePublica)) {
                    balanco -= transacao.getQuantia();
                }
                if (transacao.getChaveDoDestinatario().equals(chavePublica)) {
                    balanco += transacao.getQuantia();
                }
            }
        }
        
        return balanco;
    }

    public boolean isChainValida () {

        for ( int i = 1; i < this.chain.size(); i++ ) {

            Bloco blocoAtual = this.chain.get( i );
            Bloco blocoAnterior = this.chain.get( i - 1 );

            if ( !blocoAtual.getHash().equals( blocoAtual.calcularHashDoBloco() ) ) {

                System.out.println( "Hash do bloco atual nao e igual" );

                return false;
            }

            if ( !blocoAtual.getHashBlocoAnterior().equals( blocoAnterior.getHash() ) ) {

                System.out.println( "Hash do bloco anterior nao e igual" );

                return false;

            }

        }

        return true;
    }

}
