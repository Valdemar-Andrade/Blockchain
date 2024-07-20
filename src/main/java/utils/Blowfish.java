/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author valdemar
 */
public class Blowfish {

    private static final String BLOWFISH = "Blowfish";
    private static final int MAX_KEY_SIZE = 56; // 448 bits = 56 bytes
    private SecretKey chaveSecreta;
    private final String chave = "whvv7DR4Mv3cFmnJbIUjLNfSWZLhx/sPhLMMJJVF55wetnc/qVg/gvyOKzHqjGUBaC9s30SDxJU=";

    public Blowfish () throws Exception {
        getChave();
    }

    private void getChave () {
        try {
            byte[] keyData = chave.getBytes( StandardCharsets.UTF_8 );

            if ( keyData.length > MAX_KEY_SIZE ) {
                byte[] truncatedKey = new byte[MAX_KEY_SIZE];
                System.arraycopy( keyData, 0, truncatedKey, 0, MAX_KEY_SIZE );
                this.chaveSecreta = new SecretKeySpec( truncatedKey, BLOWFISH );
            }
            else {
                this.chaveSecreta = new SecretKeySpec( keyData, BLOWFISH );
            }

        }
        catch ( Exception e ) {
            System.err.println( "Erro ao gerar a chave secreta: " + e.getMessage() );
            e.printStackTrace();
        }
    }

    public String criptografar ( String conteudo ) {
        try {
            Cipher cipher = Cipher.getInstance( BLOWFISH );
            cipher.init( Cipher.ENCRYPT_MODE, chaveSecreta );
            byte[] encryptedData = cipher.doFinal( conteudo.getBytes( StandardCharsets.UTF_8 ) );
            return Base64.getEncoder().encodeToString( encryptedData );
        }
        catch ( Exception e ) {
            System.err.println( "Erro ao criptografar: " + e.getMessage() );
            e.printStackTrace();
            return null;
        }
    }

    public String desencriptar ( String conteudoEncriptado ) {
        try {
            Cipher cipher = Cipher.getInstance( BLOWFISH );
            cipher.init( Cipher.DECRYPT_MODE, chaveSecreta );
            byte[] decoded = Base64.getDecoder().decode( conteudoEncriptado );
            byte[] desencriptado = cipher.doFinal( decoded );
            return new String( desencriptado, StandardCharsets.UTF_8 );
        }
        catch ( Exception e ) {
            System.err.println( "Erro ao desencriptar: " + e.getMessage() );
            e.printStackTrace();
            return null;
        }
    }

    public String gerarHashBlowfish ( String conteudo ) {

        try {
            Cipher cipher = Cipher.getInstance( BLOWFISH );
            cipher.init( Cipher.ENCRYPT_MODE, chaveSecreta );
            byte[] encryptedData = cipher.doFinal( conteudo.getBytes( StandardCharsets.UTF_8 ) );

            return Base64.getEncoder().encodeToString( encryptedData );
        }
        catch ( Exception ex ) {
            System.out.println( "Erro ao gerar Hash com o Blowfish: " + ex.getMessage() );
        }

        return null;

    }

    public String getSecretKeyBase64 () {
        return Base64.getEncoder().encodeToString( chaveSecreta.getEncoded() );
    }

    public SecretKey getChaveSecreta () {
        return chaveSecreta;
    }

}


/*
public String gerarHash ( String conteudo ) {

        try {

            MessageDigest digest = MessageDigest.getInstance( "SHA-256" );
            byte[] hash = digest.digest( conteudo.getBytes( "UTF-8" ) );
            StringBuilder hexString = new StringBuilder();

            for ( byte b : hash ) {

                String hex = Integer.toHexString( 0xff & b );

                if ( hex.length() == 1 ) {
                    hexString.append( '0' );
                }

                hexString.append( hex );
            }

            return hexString.toString();
        }
        catch ( Exception e ) {
            throw new RuntimeException( e );
        }

    }


 */


/*
    try {
            Cipher cipher = Cipher.getInstance( BLOWFISH );
            cipher.init( Cipher.ENCRYPT_MODE, chaveSecreta );
            byte[] hashBytes = cipher.doFinal( valor.getBytes( StandardCharsets.UTF_8 ) );
            return Base64.getEncoder().encodeToString( hashBytes );
        }
        catch ( Exception e ) {
            System.err.println( "Erro ao gerar hash Blowfish: " + e.getMessage() );
            e.printStackTrace();
            return null;
        }
     */
