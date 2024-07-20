/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import utils.Blowfish;

/**
 *
 * @author valdemar
 */
public class Main {

    public static void main ( String[] args ) throws Exception {

        try {

            Blowfish blowfish = new Blowfish();

            String textoOriginal = "Texto para criptografar";
            String textoCriptografado = blowfish.criptografar( textoOriginal );
            String textoDesencriptado = blowfish.desencriptar( textoCriptografado );
            String hashBlowfish = blowfish.gerarHashBlowfish( textoOriginal );

            System.out.println( "Texto Original: " + textoOriginal );
            System.out.println( "Texto Criptografado: " + textoCriptografado );
            System.out.println( "Texto Desencriptado: " + textoDesencriptado );
            System.out.println( "Hash Blowfish: " + hashBlowfish );

        }
        catch ( Exception e ) {
            e.printStackTrace();
        }

    }

}
