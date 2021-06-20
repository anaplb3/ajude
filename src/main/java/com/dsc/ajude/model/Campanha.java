package com.dsc.ajude.model;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Campanha {
    @Id
    @GeneratedValue
    private Long id;
    private String nome;
    private String descricao;
    private Date deadline;
    private double meta;


    @Enumerated(EnumType.ORDINAL)
    private Status status;

}
