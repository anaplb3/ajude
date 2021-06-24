package com.dsc.ajude.modelos;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
public class Comentario {
    @Id
    @GeneratedValue
    private Long id;
    
    private String conteudoDaMensagem;
    
    @ManyToOne
    private Usuario usuario;
    
    @OneToOne
    private Comentario respostasUsuarios; 
    
    @ManyToOne
    private Campanha campanhaReferencia;
    
    
    
    
}
