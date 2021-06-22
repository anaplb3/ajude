package com.dsc.ajude.dto;

public class CampanhaSubstringDTO {
    private String substring;
    private boolean todosOsResultados;

    public CampanhaSubstringDTO(String substring, boolean todosOsResultados) {
        this.substring = substring;
        this.todosOsResultados = todosOsResultados;
    }

    public String getSubstring() {
        return substring;
    }

    public boolean isTodosOsResultados() {
        return todosOsResultados;
    }
}
