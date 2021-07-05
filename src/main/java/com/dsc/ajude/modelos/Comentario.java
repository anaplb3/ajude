package com.dsc.ajude.modelos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Data
@Table(name = "comentarios")
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comentario")
    @NonNull
    private Long comentarioId;

    @Column(name = "conteudo_da_mensagem")
    @NonNull
    private String conteudoDaMensagem;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "email_dono")
    private Usuario usuario;

    @OneToOne
    @JoinColumn(name = "id_comentario_resposta")
    private Comentario respostasUsuarios;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_campanha")
    private Campanha campanha;

    @JsonIgnore
    public Campanha getCampanha() {
        return campanha;
    }

}
