/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import Modelo.Blockchain;
import java.io.File;
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

        File file = new File(nomeArquivo);
        
        if (!file.exists()) {
            //System.out.println("Arquivo não encontrado ao carregar: " + file.getAbsolutePath());
            return new Blockchain();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Blockchain) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return new Blockchain();
        }
    }

    public static void salvarBlockchain ( Blockchain blockchain, String nomeArquivo ) {

        File file = new File(nomeArquivo);
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(blockchain);
            //System.out.println("Blockchain salva em: " + file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean apagarBlockchain ( String nomeArquivo ) {
        File file = new File(nomeArquivo);
        
        if (!file.exists()) {
            System.out.println("Arquivo não encontrado ao apagar: " + file.getAbsolutePath());
            return false;
        }
        
        if (!file.canWrite()) {
            System.out.println("Sem permissão para excluir o arquivo: " + file.getAbsolutePath());
            return false;
        }
        
        boolean deleted = file.delete();
        if (!deleted) {
            System.out.println("Falha ao excluir o arquivo: " + file.getAbsolutePath());
        } else {
            System.out.println("Arquivo excluído com sucesso: " + file.getAbsolutePath());
        }
        
        return deleted;
    }
    
    public static void listarArquivosDiretorio(String diretorio) {
        File dir = new File(diretorio);
        if (dir.isDirectory()) {
            String[] files = dir.list();
            if (files != null) {
                for (String file : files) {
                    System.out.println(file);
                }
            } else {
                System.out.println("O diretório está vazio ou não é possível listar os arquivos.");
            }
        } else {
            System.out.println(diretorio + " não é um diretório.");
        }
    }

}
