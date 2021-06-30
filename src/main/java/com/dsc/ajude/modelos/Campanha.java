package com.dsc.ajude.modelos;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Campanha {
    @Id
    @GeneratedValue
    private Long id;
    private String nome;
    private String descricao;
    private LocalDate deadline;
    private double meta;


    @Enumerated(EnumType.ORDINAL)
    private Status status;
    
    @ManyToOne
    private Usuario dono;
   
    @OneToMany
    private List<Doacao> doacoes;
    
    @OneToMany
    private List<Comentario> comentarios;

    @OneToMany()
    private List<Like> likes = new ArrayList<Like>();

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }
}
