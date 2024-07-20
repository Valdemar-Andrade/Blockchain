/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author valdemar
 */
public class SocketUsuario {
    
    private Integer pk_socket;
    private String endereco;
    private String porta;
    private Integer fk_usuario;

    public SocketUsuario () {
    }

    public SocketUsuario ( Integer pk_socket, String endereco, String porta, Integer fk_usuario ) {
        this.pk_socket = pk_socket;
        this.endereco = endereco;
        this.porta = porta;
        this.fk_usuario = fk_usuario;
    }

    public Integer getPk_socket () {
        return pk_socket;
    }

    public void setPk_socket ( Integer pk_socket ) {
        this.pk_socket = pk_socket;
    }

    public String getEndereco () {
        return endereco;
    }

    public void setEndereco ( String endereco ) {
        this.endereco = endereco;
    }

    public String getPorta () {
        return porta;
    }

    public void setPorta ( String porta ) {
        this.porta = porta;
    }

    public Integer getFk_usuario () {
        return fk_usuario;
    }

    public void setFk_usuario ( Integer fk_usuario ) {
        this.fk_usuario = fk_usuario;
    }
    
    
    
}
