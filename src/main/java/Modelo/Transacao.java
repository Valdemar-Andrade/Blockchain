/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.security.PrivateKey;
import java.security.PublicKey;
import utils.StringUtil;

/**
 *
 * @author valdemar
 */
public class Transacao {

    private String transactionID;
    private byte[] assinatura;
    private PublicKey chaveDoRemetente, chaveDoDestinatario;
    private Double quantia;

    public Transacao () {
    }

    public Transacao ( String transactionID, String assinatura, PublicKey chaveDoRemetente, PublicKey chaveDoDestinatario, Double quantia, PrivateKey chavePrivada ) {
        this.transactionID = transactionID;
        this.assinatura = gerarAssinatura( chavePrivada );
        this.chaveDoRemetente = chaveDoRemetente;
        this.chaveDoDestinatario = chaveDoDestinatario;
        this.quantia = quantia;
    }

    public String getTransactionID () {
        return transactionID;
    }

    public void setTransactionID ( String transactionID ) {
        this.transactionID = transactionID;
    }

    public byte[] getAssinatura () {
        return assinatura;
    }

    public void setAssinatura ( byte[] assinatura ) {
        this.assinatura = assinatura;
    }

    public PublicKey getChaveDoRemetente () {
        return chaveDoRemetente;
    }

    public void setChaveDoRemetente ( PublicKey chaveDoRemetente ) {
        this.chaveDoRemetente = chaveDoRemetente;
    }

    public PublicKey getChaveDoDestinatario () {
        return chaveDoDestinatario;
    }

    public void setChaveDoDestinatario ( PublicKey chaveDoDestinatario ) {
        this.chaveDoDestinatario = chaveDoDestinatario;
    }

    public Double getQuantia () {
        return quantia;
    }

    public void setQuantia ( Double quantia ) {
        this.quantia = quantia;
    }
    
    

    public String calcularHash () {
        String data = StringUtil.getStringDeChave( chaveDoRemetente ) + StringUtil.getStringDeChave( chaveDoDestinatario ) + Double.toString( quantia );

        return StringUtil.aplicarSHA256( data );
    }

    private byte[] gerarAssinatura ( PrivateKey privateKey ) {
        String data = StringUtil.getStringDeChave( chaveDoRemetente ) + StringUtil.getStringDeChave( chaveDoDestinatario ) + Double.toString( quantia );

        return StringUtil.aplicarAssinaturaECDSA( privateKey, data );
    }

    public boolean verificarAssinatura () {
        String data = StringUtil.getStringDeChave( chaveDoRemetente ) + StringUtil.getStringDeChave( chaveDoDestinatario ) + Double.toString( quantia );

        return StringUtil.verificarAssinaturaECDSA( chaveDoRemetente, data, assinatura );
    }

}
