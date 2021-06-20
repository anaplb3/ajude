package com.dsc.ajude.excecoes;

public class DataInvalidaExcecao extends Exception {

    public DataInvalidaExcecao() {
        super("Data do deadline deve ser no futuro!");
    }
}
