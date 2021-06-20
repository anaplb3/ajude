package com.dsc.ajude.modelos;

import lombok.Data;

import javax.persistence.*;
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
