/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author valdemar
 */
public class StringUtil {

    public static String aplicarSHA256 ( String informacao ) {

        try {

            MessageDigest digest = MessageDigest.getInstance( "SHA-256" );
            byte[] hash = digest.digest( informacao.getBytes( "UTF-8" ) );
            StringBuffer hexString = new StringBuffer();

            for ( int i = 0; i < hash.length; i++ ) {

                String hex = Integer.toHexString( 0xff & hash[i] );

                if ( hex.length() == 1 ) {
                    hexString.append( '0' );
                }

                hexString.append( hex );
            }

            return hexString.toString();
        }
        catch ( NoSuchAlgorithmException e ) {
            throw new RuntimeException( e );
        }
        catch ( UnsupportedEncodingException ex ) {
            Logger.getLogger( StringUtil.class.getName() ).log( Level.SEVERE, null, ex );

            return "";
        }

    }

    // Retorna a chave pública como uma string
    public static String getStringDeChave ( Key key ) {
        return Base64.getEncoder().encodeToString( key.getEncoded() );
    }

    // Aplica ECDSA Signature e retorna o resultado (como bytes)
    public static byte[] aplicarAssinaturaECDSA ( PrivateKey privateKey, String input ) {

        try {

            Signature signature = Signature.getInstance( "SHA256withECDSA" );
            signature.initSign( privateKey );
            signature.update( input.getBytes() );

            return signature.sign();

        }
        catch ( Exception e ) {
            throw new RuntimeException( e );
        }
    }

    // Verifica uma String assinada com a chave pública
    public static boolean verificarAssinaturaECDSA ( PublicKey publicKey, String data, byte[] assinatura ) {

        try {

            Signature ecdsaVerify = Signature.getInstance( "ECDSA", "BC" );

            ecdsaVerify.initVerify( publicKey );
            ecdsaVerify.update( data.getBytes() );

            return ecdsaVerify.verify( assinatura );

        }
        catch ( InvalidKeyException | NoSuchAlgorithmException | NoSuchProviderException | SignatureException e ) {
            throw new RuntimeException( e );
        }

    }

}
