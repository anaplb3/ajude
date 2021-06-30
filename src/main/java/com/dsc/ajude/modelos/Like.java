package com.dsc.ajude.modelos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Like {

    @Id
    @GeneratedValue
    private long id;

    private String usuario;

    private int contador;

    public Like(String usuario){
        super();
        this.usuario = usuario;
    }

    public Like(){
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

}
