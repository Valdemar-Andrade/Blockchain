/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author valdemar
 */
public class PeerInformacao {
    
    private String host;
    private int porta;

    public PeerInformacao ( String host, int porta ) {
        this.host = host;
        this.porta = porta;
    }

    public String getHost () {
        return host;
    }

    public int getPorta () {
        return porta;
    }
    
    
    
}
