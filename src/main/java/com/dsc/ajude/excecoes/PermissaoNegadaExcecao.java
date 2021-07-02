package com.dsc.ajude.excecoes;

public class PermissaoNegadaExcecao extends Exception {

    public PermissaoNegadaExcecao() {
        super("Permissão negada!");
    }

    public PermissaoNegadaExcecao(String message){
        super(message);
    }
}
