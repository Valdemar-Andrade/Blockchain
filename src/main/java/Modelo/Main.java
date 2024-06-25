/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.Date;
import utils.Blowfish;

/**
 *
 * @author valdemar
 */
public class Main {
    
    public static void main(String[] args) throws Exception {
        Blockchain blockchain = new Blockchain();
        
        Blowfish blowfish = new Blowfish();
        String encryptedData = blowfish.encriptar("Some confidential data");
        
        blockchain.adicionarBlocoNaBlockchain(new Bloco(1, new Date().getTime(), encryptedData, blockchain.getUltimoBloco().getHash()));
        blockchain.adicionarBlocoNaBlockchain(new Bloco(2, new Date().getTime(), encryptedData, blockchain.getUltimoBloco().getHash()));
        
        System.out.println("Blockchain is valid: " + blockchain.blockchainEhValida());
        
        for (Bloco block : blockchain.chain) {
            System.out.println("Block Data: " + blowfish.desencriptar(block.getConteudoDoBloco()));
        }
    }
    
}
