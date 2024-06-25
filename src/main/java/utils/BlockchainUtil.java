/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import Modelo.Blockchain;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author valdemar
 */
public class BlockchainUtil {

    public static Blockchain carregarBlockchain ( String nomeArquivo ) {

        try ( ObjectInputStream ois = new ObjectInputStream( new FileInputStream( nomeArquivo ) ) ) {
            return ( Blockchain ) ois.readObject();
        }
        catch ( Exception e ) {
            return new Blockchain(); // Se não conseguir carregar, retorna uma nova blockchain
        }
    }

    public static void salvarBlockchain ( Blockchain blockchain, String nomeArquivo ) {
        
        try ( ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream( nomeArquivo ) ) ) {
            oos.writeObject( blockchain );
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }

}
