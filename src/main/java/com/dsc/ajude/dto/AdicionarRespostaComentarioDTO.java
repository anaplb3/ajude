package com.dsc.ajude.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AdicionarRespostaComentarioDTO {

    @NotNull(message = "Necessário informar o Comentário.")
    private Long idComentario;

    @NotNull(message = "Corpo da mensagem não pode ser vazia.")
    private String corpoDaMensagem;

    @NotNull(message = "Email do Usuário é obrigatório.")
    private String emailDoUsuario;
}
