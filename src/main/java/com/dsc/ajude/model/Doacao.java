package com.dsc.ajude.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@Data
public class Doacao {
    @Id
    @GeneratedValue
    private Long id;
    private double quantiaDoada;
    private Date dataDaDoacao;

    @ManyToOne
    private Usuario usuario;
    @ManyToOne
    private Campanha campanha;

}
