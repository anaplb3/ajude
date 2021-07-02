package com.dsc.ajude.modelos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Entity
@Data
@Table(name = "doacoes")
public class Doacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_doacao")
    @NonNull
    private Long doacaoId;

    @Column(name = "quantia_doada")
    @NonNull
    private double quantiaDoada;

    @Column(name = "data_da_doacao")
    @NonNull
    private Date dataDaDoacao;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "email_dono")
    private Usuario usuario;

}
