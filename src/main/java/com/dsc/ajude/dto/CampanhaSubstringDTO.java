package com.dsc.ajude.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CampanhaSubstringDTO {

    @NotNull(message = "Substring é obrigatório.")
    private String substring;

    @NotNull(message = "Necessário informar se são todos os resultados.")
    private Boolean todosOsResultados;

    public CampanhaSubstringDTO(String substring, boolean todosOsResultados) {
        this.substring = substring;
        this.todosOsResultados = todosOsResultados;
    }

}
