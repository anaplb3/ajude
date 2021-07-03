package com.dsc.ajude.modelos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Data
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_like")
    @NonNull
    private Long id;

    @ManyToOne
    @JoinColumn(name = "email_dono")
    @NonNull
    private Usuario usuario;

}
