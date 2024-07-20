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
public class Chaves {
    
    private Integer pk_chaves;
    private String chave_publica;
    private String chave_privada;
    private Integer fk_usuario;

    public Chaves () {
    }

    public Chaves ( Integer pk_chaves, String chave_publica, String chave_privada, Integer fk_usuario ) {
        this.pk_chaves = pk_chaves;
        this.chave_publica = chave_publica;
        this.chave_privada = chave_privada;
        this.fk_usuario = fk_usuario;
    }

    public Integer getPk_chaves () {
        return pk_chaves;
    }

    public void setPk_chaves ( Integer pk_chaves ) {
        this.pk_chaves = pk_chaves;
    }

    public String getChave_publica () {
        return chave_publica;
    }

    public void setChave_publica ( String chave_publica ) {
        this.chave_publica = chave_publica;
    }

    public String getChave_privada () {
        return chave_privada;
    }

    public void setChave_privada ( String chave_privada ) {
        this.chave_privada = chave_privada;
    }

    public Integer getFk_usuario () {
        return fk_usuario;
    }

    public void setFk_usuario ( Integer fk_usuario ) {
        this.fk_usuario = fk_usuario;
    }

    @Override
    public int hashCode () {
        int hash = 5;
        hash = 31 * hash + Objects.hashCode( this.pk_chaves );
        hash = 31 * hash + Objects.hashCode( this.chave_publica );
        hash = 31 * hash + Objects.hashCode( this.chave_privada );
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
        final Chaves other = ( Chaves ) obj;
        if ( !Objects.equals( this.chave_publica, other.chave_publica ) ) {
            return false;
        }
        if ( !Objects.equals( this.chave_privada, other.chave_privada ) ) {
            return false;
        }
        if ( !Objects.equals( this.pk_chaves, other.pk_chaves ) ) {
            return false;
        }
        return Objects.equals( this.fk_usuario, other.fk_usuario );
    }
    
    
    
}
