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
public class Login {
    
    private Integer pk_login;
    private String email;
    private String senha;
    private Integer fk_usuario;

    public Login () {
    }

    public Login ( Integer pk_login, String email, String senha, Integer fk_usuario ) {
        this.pk_login = pk_login;
        this.email = email;
        this.senha = senha;
        this.fk_usuario = fk_usuario;
    }

    public Integer getPk_login () {
        return pk_login;
    }

    public void setPk_login ( Integer pk_login ) {
        this.pk_login = pk_login;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail ( String email ) {
        this.email = email;
    }

    public String getSenha () {
        return senha;
    }

    public void setSenha ( String senha ) {
        this.senha = senha;
    }

    public Integer getFk_usuario () {
        return fk_usuario;
    }

    public void setFk_usuario ( Integer fk_usuario ) {
        this.fk_usuario = fk_usuario;
    }

    @Override
    public int hashCode () {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode( this.pk_login );
        hash = 31 * hash + Objects.hashCode( this.email );
        hash = 31 * hash + Objects.hashCode( this.senha );
        hash = 31 * hash + Objects.hashCode( this.fk_usuario );
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
        final Login other = ( Login ) obj;
        if ( !Objects.equals( this.email, other.email ) ) {
            return false;
        }
        if ( !Objects.equals( this.senha, other.senha ) ) {
            return false;
        }
        if ( !Objects.equals( this.pk_login, other.pk_login ) ) {
            return false;
        }
        return Objects.equals( this.fk_usuario, other.fk_usuario );
    }

}
