/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.io.Serializable;
import utils.Blowfish;

/**
 *
 * @author valdemar
 */
public class Bloco implements Serializable {

    private static final long serialVersionUID = 1L;

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

        try {
            Blowfish blowfishHash = new Blowfish();

            return blowfishHash.gerarHashBlowfish( input );
        }
        catch ( Exception ex ) {
            System.out.println( "Erro ao Criptografar o bloco" );
        }

        return "";
    }

    private boolean hashDoBlocoEhValida ( String hash ) {
        return hash.contains( "202" ) || hash.contains( "026" ) || hash.contains( "261" );
    }

    public void minerarBloco () {

        while ( !hashDoBlocoEhValida( hash ) ) {
            nonce++;
            hash = calcularHashDoBloco();
        }

        System.out.println( "Bloco verificado: " + hash );
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

    public long getTimestamp () {
        return timestamp;
    }

}
