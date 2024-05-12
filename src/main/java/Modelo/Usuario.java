/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author valdemar
 */
public class Usuario {
    
    private String nome, email;
    private Carteira carteira;

    public Usuario ( String nome, String email ) {
        this.nome = nome;
        this.email = email;
        this.carteira = new Carteira();
    }

    public String getNome () {
        return nome;
    }

    public void setNome ( String nome ) {
        this.nome = nome;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail ( String email ) {
        this.email = email;
    }

    public Carteira getCarteira () {
        return carteira;
    }
    
}
