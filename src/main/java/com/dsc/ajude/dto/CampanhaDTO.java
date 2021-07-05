package com.dsc.ajude.dto;

import com.dsc.ajude.modelos.Campanha;
import com.dsc.ajude.modelos.Status;
import com.dsc.ajude.modelos.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CampanhaDTO {

    @NotNull(message = "Nome da campanha é obrigatório.")
    private String nome;

    @NotNull(message = "Descriição da campanha é obrigatório.")
    private String descricao;

    @NotNull(message = "Deadline da campanha é obrigatório.")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate deadline;

    @NotNull(message = "Meta da campanha é obrigatório.")
    private BigDecimal meta;

    @NotNull(message = "Email do Usuário é obrigatório.")
    private String emailUsuario;

    public CampanhaDTO(String nome, String descricao, LocalDate deadline, BigDecimal meta, String emailUsuario) {
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
        campanha.setValorRestanteMeta(this.meta);
        campanha.setQuantidadeLikes(0);

        return campanha;
    }

    public CampanhaDTO(){

    }
}
