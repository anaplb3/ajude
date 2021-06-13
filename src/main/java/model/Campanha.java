package model;

import lombok.Data;

import javax.persistence.*;
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
    private Date deadline;
    private double meta;


    @Enumerated(EnumType.ORDINAL)
    private Status status;
    @ManyToOne
    private Usuario dono;
    @OneToMany
    private List<Doacao> doacoes;

}
