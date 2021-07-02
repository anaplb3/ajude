package com.dsc.ajude.dto;

import com.dsc.ajude.modelos.Campanha;
import com.dsc.ajude.modelos.Status;
import com.dsc.ajude.modelos.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CampanhaDTO {
    private String nome;
    private String descricao;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate deadline;
    private double meta;
    private String emailUsuario;

    public CampanhaDTO(String nome, String descricao, LocalDate deadline, double meta, String emailUsuario) {
        this.nome = nome;
        this.descricao = descricao;
        this.deadline = deadline;
        this.meta = meta;
        this.emailUsuario = emailUsuario;
    }

    public Campanha campanhaDTOParaCampanha(Usuario donoDaCampanha, Status status) {
        Campanha campanha = new Campanha();
        campanha.setNome(this.nome.toLowerCase());
        campanha.setDescricao(this.descricao);
        campanha.setDeadline(this.deadline);
        campanha.setMeta(this.meta);
        campanha.setStatus(status);
        campanha.setDono(donoDaCampanha);

        return campanha;
    }
}
