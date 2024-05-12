/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author valdemar
 */
public class Carteira {
    
    private PrivateKey chavePrivada;
    private PublicKey chavePublica;

    public Carteira () {
        
        gerarParDeChaves();
        
    }
    
    private void gerarParDeChaves(){
        
        try{
            
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance( "ECDSA", "BC");
            SecureRandom secureRandom = SecureRandom.getInstance( "SHA1PRNG");
            ECGenParameterSpec ecgen = new ECGenParameterSpec("secp256r1");
            
            keyGen.initialize( ecgen, secureRandom );
            KeyPair parChaves = keyGen.generateKeyPair();
            
            chavePrivada = parChaves.getPrivate();
            chavePublica = parChaves.getPublic();
             
        }
        catch ( InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException ex ) {
            Logger.getLogger( Carteira.class.getName() ).log( Level.SEVERE, null, ex );
        }
        
    }

    public PrivateKey getChavePrivada () {
        return chavePrivada;
    }

    public void setChavePrivada ( PrivateKey chavePrivada ) {
        this.chavePrivada = chavePrivada;
    }

    public PublicKey getChavePublica () {
        return chavePublica;
    }

    public void setChavePublica ( PublicKey chavePublica ) {
        this.chavePublica = chavePublica;
    }
    
    
    
}
