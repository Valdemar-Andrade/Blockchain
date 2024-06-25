/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package extra;

import java.security.PublicKey;
import java.util.Arrays;

/**
 *
 * @author valdemar
 */
public class Criptomoeda {
    
    private String nome;
    private double totalSuprimento;
    private Blockchain blockchain;
    private Carteira carteiraGenesis;  // Carteira que receberá o suprimento inicial

    public Criptomoeda(String nome, double suprimentoInicial) {
        this.nome = nome;
        this.totalSuprimento = suprimentoInicial;
        this.blockchain = new Blockchain();
        this.carteiraGenesis = new Carteira();
        emitirMoedaInicial();
    }

    private void emitirMoedaInicial() {
        
        // Transação inicial para a carteira gênese
        Transacao transacaoGenesis = new Transacao(null, carteiraGenesis.getChavePublica(), totalSuprimento);
        transacaoGenesis.gerarAssinatura(carteiraGenesis.getChavePrivada());
        
        Bloco blocoGenesis = new Bloco(0, "0", Arrays.asList(transacaoGenesis));
        blocoGenesis.minerarBloco(4);
        
        blockchain.adicionarBloco(blocoGenesis);
        System.out.println("Moeda inicial emitida para a carteira gênese com saldo de: " + totalSuprimento);
    }

    public Blockchain getBlockchain() {
        return blockchain;
    }

    public Carteira getCarteiraGenesis() {
        return carteiraGenesis;
    }

//    private void emitirMoedaInicial() {
//        // Aqui, a criptomoeda é inicialmente emitida para a carteira central
//        Transaction genesisTransaction = new Transaction(null, walletCentral.getPublicKey(), totalSupply);
//        genesisTransaction.generateSignature(walletCentral.getPrivateKey());
//        Block genesisBlock = new Block(0, System.currentTimeMillis(), "0", Arrays.asList(genesisTransaction));
//        genesisBlock.mineBlock(4);
//        blockchain.addBlock(genesisBlock);
//    }
//
//    public boolean realizarTransacao(PublicKey from, PublicKey to, double amount, PrivateKey privateKey) {
//        if (verificarSaldo(from, amount)) {
//            Transaction newTransaction = new Transaction(from, to, amount);
//            newTransaction.generateSignature(privateKey);
//            if (newTransaction.verifySignature()) {
//                // Supondo que haja um método para processar transações no Blockchain
//                blockchain.addTransaction(newTransaction);
//                return true;
//            }
//        }
//        return false;
//    }

    private boolean verificarSaldo(PublicKey publicKey, double amount) {
        // Este método deve verificar se o saldo da carteira é suficiente para a transação
        return blockchain.getBalanco(publicKey) >= amount;
    }

    public double consultarSaldo(PublicKey publicKey) {
        return blockchain.getBalanco(publicKey);
    }
    
}
