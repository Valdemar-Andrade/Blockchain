/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package extra;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author valdemar
 */
public class Bloco {

    private int nonce;
    private String hash, hashBlocoAnterior;
    private List<Transacao> transacoes;

    public Bloco () {
    }

    public Bloco ( int nonce, String hashBlocoAnterior, List<Transacao> transacoes ) {
        this.nonce = nonce;
        this.hash = calcularHashDoBloco();
        this.hashBlocoAnterior = hashBlocoAnterior;
        this.transacoes = transacoes;
    }

    public int getId () {
        return nonce;
    }

    public void setId ( int id ) {
        this.nonce = id;
    }

    public String getHash () {
        return hash;
    }

    public void setHash ( String hash ) {
        this.hash = hash;
    }

    public String getHashBlocoAnterior () {
        return hashBlocoAnterior;
    }

    public void setHashBlocoAnterior ( String hashBloboAnterior ) {
        this.hashBlocoAnterior = hashBloboAnterior;
    }

    public List<Transacao> getTransacoes () {
        return transacoes;
    }

    public void setTransacoes ( List<Transacao> transacoes ) {
        this.transacoes = transacoes;
    }

    public String calcularHashDoBloco () {

        String dataToHash = hashBlocoAnterior + Arrays.asList( transacoes ).toString();
        MessageDigest digest;
        byte[] bytes = null;

        try {
            digest = MessageDigest.getInstance( "SHA-256" );
            bytes = digest.digest( dataToHash.getBytes( "UTF-8" ) );
        }
        catch ( UnsupportedEncodingException | NoSuchAlgorithmException ex ) {
            ex.getStackTrace();
        }

        StringBuilder buffer = new StringBuilder();

        for ( byte b : bytes ) {
            buffer.append( String.format( "%02x", b ) );
        }

        return buffer.toString();
    }

    public void minerarBloco ( int grau ) {

        String target = new String( new char[grau] ).replace( '\0', '0' );

        while ( !this.hash.substring( 0, grau ).equals( target ) ) {
            nonce++;
            this.hash = calcularHashDoBloco();
        }
        System.out.println( "Bloco Minerado: " + hash );
    }

}
