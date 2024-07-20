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
    
    private Integer pkLogin;
    private String email;
    private String senha;
    private Integer fkUsuario;

    public Login () {
    }

    public Login ( Integer pkLogin, String email, String senha, Integer fkUsuario ) {
        this.pkLogin = pkLogin;
        this.email = email;
        this.senha = senha;
        this.fkUsuario = fkUsuario;
    }

    public Integer getPkLogin () {
        return pkLogin;
    }

    public void setPkLogin ( Integer pkLogin ) {
        this.pkLogin = pkLogin;
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

    public Integer getFkUsuario () {
        return fkUsuario;
    }

    public void setFkUsuario ( Integer fkUsuario ) {
        this.fkUsuario = fkUsuario;
    }

    @Override
    public int hashCode () {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.pkLogin );
        hash = 31 * hash + Objects.hashCode( this.email );
        hash = 31 * hash + Objects.hashCode( this.senha );
        hash = 31 * hash + Objects.hashCode(this.fkUsuario );
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
        if ( !Objects.equals(this.pkLogin, other.pkLogin ) ) {
            return false;
        }
        return Objects.equals(this.fkUsuario, other.fkUsuario );
    }

}
