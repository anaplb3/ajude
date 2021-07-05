package com.dsc.ajude.modelos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
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

    @Column(name = "quantia_doada", columnDefinition = "NUMERIC(11,2)")
    @NonNull
    private BigDecimal quantiaDoada;

    @Column(name = "data_da_doacao")
    @NonNull
    private LocalDate dataDaDoacao;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "email_dono")
    private Usuario usuario;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_campanha")
    private Campanha campanha;

    @JsonIgnore
    public Campanha getCampanha() {
        return campanha;
    }

}
