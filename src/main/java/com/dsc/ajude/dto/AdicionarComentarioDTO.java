package com.dsc.ajude.dto;

import lombok.Data;

@Data
public class AdicionarComentarioDTO {
	
	private Long idCampanha;
	
	private Long idComentario;
	
	private String corpoDaMensagem;
	
	private String emailDoUsuario;


}
