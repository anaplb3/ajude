package com.dsc.ajude.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class DoacaoDTO {

    @NotNull(message = "Email do Usuário é obrigatório.")
    private String emailUsuario;

    @NotNull(message = "Necessário informar qual a campanha.")
    private Long idCampanha;

    @NotNull(message = "Necessário informar o valor doado.")
    private BigDecimal valorDoado;
}
