package com.dsc.ajude.modelos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    @Column(name = "meta", columnDefinition = "NUMERIC(11,2)")
    @NonNull
    private BigDecimal meta;

    @Column(name = "status")
    @NonNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "email_dono")
    private Usuario dono;

    @OneToMany(mappedBy = "campanha")
    private List<Comentario> comentariosDaCampanha = new ArrayList<>();

    @OneToMany(mappedBy = "campanha")
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "campanha")
    private List<Doacao> doacoes = new ArrayList<>();

    @Column(name = "valor_restante_meta", columnDefinition = "NUMERIC(11,2)")
    private BigDecimal valorRestanteMeta;

    @Column(name = "quantidade_likes", columnDefinition = "integer default 0")
    private Integer quantidadeLikes;
}
