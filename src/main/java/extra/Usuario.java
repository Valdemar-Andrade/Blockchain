/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package extra;

import java.security.PublicKey;

/**
 *
 * @author valdemar
 */
public class Usuario {
    
    private String nome, email;
    private Carteira carteira;

    public Usuario ( String nome, String email ) {
        this.nome = nome;
        this.email = email;
        this.carteira = new Carteira();
    }

    public String getNome () {
        return nome;
    }

    public void setNome ( String nome ) {
        this.nome = nome;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail ( String email ) {
        this.email = email;
    }

    public Carteira getCarteira () {
        return carteira;
    }
    
    public double consultarSaldo(Blockchain blockchain) {
        return blockchain.getBalanco(carteira.getChavePublica());
    }

    public boolean realizarTransacao(PublicKey receptor, double quantia, Blockchain blockchain) {
        
        Transacao transacao = carteira.criarTransacao(receptor, quantia, blockchain);
        
        if (transacao != null) {
            blockchain.adicionarTransacao(transacao);
            return true;
        }
        return false;
    }
    
}
