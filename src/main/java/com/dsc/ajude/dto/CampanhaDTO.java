package com.dsc.ajude.dto;

import com.dsc.ajude.modelos.Campanha;
import com.dsc.ajude.modelos.Status;
import com.dsc.ajude.modelos.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class CampanhaDTO {
    private String nome;
    private String descricao;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
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
        campanha.setNome(this.nome);
        campanha.setDescricao(this.descricao);
        campanha.setDeadline(this.deadline);
        campanha.setMeta(this.meta);
        campanha.setStatus(status);
        campanha.setDono(donoDaCampanha);
        campanha.setDoacoes(new ArrayList<>());

        return campanha;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public LocalDate getDeadline() {
        return deadline;
    }
}
