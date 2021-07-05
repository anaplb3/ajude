package com.dsc.ajude.dto;

import com.dsc.ajude.modelos.Campanha;
import com.dsc.ajude.modelos.Status;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class ResponseCampanhaDTO {

    private Long campanhaId;
    private String nome;
    private String descricao;
    private LocalDate deadline;
    private BigDecimal meta;
    private Status status;
    private UsuarioDTO dono;
    private BigDecimal valorRestanteMeta;
    private Integer quantidadeLikes;
    private List<ResponseLikeDTO> likes;
    private List<ResponseDoacoesDTO> doacoes;
    private List<ResponseComentariosDTO> comentarios;

    public ResponseCampanhaDTO(Campanha campanha) {
        this.campanhaId = campanha.getCampanhaId();
        this.nome = campanha.getNome();
        this.descricao = campanha.getDescricao();
        this.deadline = campanha.getDeadline();
        this.meta = campanha.getMeta();
        this.status = campanha.getStatus();
        this.dono = new UsuarioDTO(campanha.getDono());
        this.valorRestanteMeta = campanha.getValorRestanteMeta();
        this.quantidadeLikes = campanha.getQuantidadeLikes();
        this.likes = ResponseLikeDTO.convertLikes(campanha.getLikes());
        this.doacoes = ResponseDoacoesDTO.convertDoacoes(campanha.getDoacoes());
        this.comentarios = ResponseComentariosDTO.convertComentarios(campanha.getComentariosDaCampanha());
    }
}

