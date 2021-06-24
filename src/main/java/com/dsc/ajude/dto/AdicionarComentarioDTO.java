package com.dsc.ajude.dto;


public class AdicionarComentarioDTO {
	
	private long idCampanha;
	
	private long idComentario;
	
	private String corpoDaMensagem;
	
	private String emailDoUsuario;

	public long getIdCampanha() {
		return idCampanha;
	}

	public void setIdCampanha(long idCampanha) {
		this.idCampanha = idCampanha;
	}

	public String getCorpoDaMensagem() {
		return corpoDaMensagem;
	}

	public void setCorpoDaMensagem(String corpoDaMensagem) {
		this.corpoDaMensagem = corpoDaMensagem;
	}

	public String getEmailDoUsuario() {
		return emailDoUsuario;
	}

	public long getIdComentario() {
		return idComentario;
	}

	public void setIdComentario(long idComentario) {
		this.idComentario = idComentario;
	}


}
