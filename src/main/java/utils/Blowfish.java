/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 *
 * @author valdemar
 */
public class Blowfish {

    private static final String BLOWFISH = "Blowfish";
    private SecretKey chaveSecreta;

    public Blowfish () throws Exception {
        
        KeyGenerator keyGenerator = KeyGenerator.getInstance( BLOWFISH );
        keyGenerator.init( 448 );
        
        this.chaveSecreta = keyGenerator.generateKey();
    }

    public String encriptar ( String conteudo ) throws Exception {
        
        Cipher cipher = Cipher.getInstance( BLOWFISH );
        cipher.init(Cipher.ENCRYPT_MODE, chaveSecreta );
        
        byte[] encriptado = cipher.doFinal( conteudo.getBytes() );
        
        return Base64.getEncoder().encodeToString( encriptado );
    }

    public String desencriptar ( String conteudoEncriptado ) throws Exception {
        
        Cipher cipher = Cipher.getInstance( BLOWFISH );
        cipher.init(Cipher.DECRYPT_MODE, chaveSecreta );
        
        byte[] decoded = Base64.getDecoder().decode( conteudoEncriptado );
        byte[] desencriptado = cipher.doFinal( decoded );
        
        return new String( desencriptado );
    }

    public SecretKey getChaveSecreta () {
        return chaveSecreta;
    }

}
