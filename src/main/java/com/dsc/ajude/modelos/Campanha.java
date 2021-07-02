package com.dsc.ajude.modelos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@NoArgsConstructor
@Entity
@Data
@Table(name = "campanhas")
public class Campanha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_campanha")
    @NonNull
    private Long campanhaId;

    @Column(name = "nome")
    @NonNull
    private String nome;

    @Column(name = "descricao")
    @NonNull
    private String descricao;

    @Column(name = "deadline")
    @NonNull
    private LocalDate deadline;

    @Column(name = "meta")
    @NonNull
    private double meta;

    @Column(name = "status")
    @NonNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "email_dono")
    private Usuario dono;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "id_comentario")
    private List<Comentario> comentariosDaCampanha;

    /*@OneToMany
    @JoinColumn(name = "id_doacao")
    private List<Doacao> doacoesDaCampanha;*/
}
