/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.security.MessageDigest;

/**
 *
 * @author valdemar
 */
public class Bloco {

    private int index;
    private long timestamp;
    private String conteudoDoBloco;
    private String hashDoBlocoAnterior;
    private String hash;
    private int nonce;

    public Bloco ( int index, long timestamp, String conteudoDoBloco, String hashDoBlocoAnterior ) {
        this.index = index;
        this.timestamp = timestamp;
        this.conteudoDoBloco = conteudoDoBloco;
        this.hashDoBlocoAnterior = hashDoBlocoAnterior;
        this.hash = calcularHashDoBloco();
        this.nonce = 0;
    }

    public String calcularHashDoBloco () {
        String input = index + Long.toString( timestamp ) + conteudoDoBloco + hashDoBlocoAnterior + nonce;
        return gerarHashDoBloco( input );
    }

    private boolean hashDoBlocoEhValida ( String hash ) {
        return hash.contains( "202" ) || hash.contains( "026" ) || hash.contains( "261" );
    }

    public void minerarBloco () {

        while ( !hashDoBlocoEhValida( hash ) ) {
            nonce++;
            hash = calcularHashDoBloco();
        }

        System.out.println( "Bloco minerado: " + hash );
    }

    public static String gerarHashDoBloco ( String input ) {

        try {

            MessageDigest digest = MessageDigest.getInstance( "SHA-256" );
            byte[] hash = digest.digest( input.getBytes( "UTF-8" ) );
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

    public String getHash () {
        return hash;
    }

    public void setHashDoBlocoAnterior ( String hashDoBlocoAnterior ) {
        this.hashDoBlocoAnterior = hashDoBlocoAnterior;
    }

    public String getHashDoBlocoAnterior () {
        return hashDoBlocoAnterior;
    }

    public String getConteudoDoBloco () {
        return conteudoDoBloco;
    }

    public int getIndex () {
        return index;
    }

}
