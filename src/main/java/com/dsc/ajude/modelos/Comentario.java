package com.dsc.ajude.modelos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class Comentario {
    @Id
    @GeneratedValue
    private Long id;
}
