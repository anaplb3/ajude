package com.dsc.ajude.excecoes;

public class RecursoNaoEncontradoExcecao extends Exception {

    public RecursoNaoEncontradoExcecao() {
        super("Id não pode ser encontrado.");
    }
}
