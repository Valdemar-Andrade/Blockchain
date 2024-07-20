/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.Objects;

/**
 *
 * @author valdemar
 */
public class Usuario {
    
    private Integer pkUsuario;
    private String nome;
    private String email;

    public Usuario () {
    }

    public Usuario ( Integer pkUsuario, String nome, String email ) {
        this.pkUsuario = pkUsuario;
        this.nome = nome;
        this.email = email;
    }

    public Integer getPkUsuario () {
        return pkUsuario;
    }

    public void setPkUsuario ( Integer pkUsuario ) {
        this.pkUsuario = pkUsuario;
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

    @Override
    public int hashCode () {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.pkUsuario );
        hash = 59 * hash + Objects.hashCode( this.nome );
        hash = 59 * hash + Objects.hashCode( this.email );
        return hash;
    }

    @Override
    public boolean equals ( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final Usuario other = ( Usuario ) obj;
        if ( !Objects.equals( this.nome, other.nome ) ) {
            return false;
        }
        if ( !Objects.equals( this.email, other.email ) ) {
            return false;
        }
        return Objects.equals(this.pkUsuario, other.pkUsuario );
    }
    
}
