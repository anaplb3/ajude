package com.dsc.ajude.dto;

import com.dsc.ajude.modelos.Usuario;
import lombok.Builder;
import lombok.Data;

@Builder(builderClassName = "Builder")
@Data
public class UsuarioDTO {

    private String email;
    private String primeiroNome;
    private String ultimoNome;
    
    public UsuarioDTO(String email, String primeiroNome, String ultimoNome) {
    	this.email = email;
    	this.primeiroNome = primeiroNome;
    	this.ultimoNome = ultimoNome;	
    }

    public UsuarioDTO(Usuario usuario) {
        this.email = usuario.getEmail();
        this.primeiroNome = usuario.getPrimeiroNome();
        this.ultimoNome = usuario.getUltimoNome();
    }

}
