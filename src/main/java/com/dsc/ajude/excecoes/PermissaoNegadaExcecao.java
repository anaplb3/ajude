package com.dsc.ajude.excecoes;

import org.springframework.http.HttpStatus;

public class PermissaoNegadaExcecao extends Exception {

    public PermissaoNegadaExcecao() {
        super("Permissão negada!");
    }

    public PermissaoNegadaExcecao(String message){
        super(message);
    }

    public PermissaoNegadaExcecao(HttpStatus statuscode){
        super();
    }

}
