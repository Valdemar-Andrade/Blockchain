/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import Modelo.PeerInformacao;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author valdemar
 */
public class PeersOnline {
    
    private static List<PeerInformacao> peersOnline = new ArrayList();
    
    public static void adicionarPeerOnline(PeerInformacao peer){
        peersOnline.add( peer );
    }
    
    public static List<PeerInformacao> getPeersOnline(){
        return peersOnline;
    }
}
