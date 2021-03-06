package com.dsc.ajude.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LikeDTO {

    @NotNull(message = "Email do Usuário é obrigatório.")
    private String emailUsuario;

    @NotNull(message = "Necessário informar qual a campanha.")
    private Long idCampanha;

}
