package com.dsc.ajude.dto;


public class RespostaLoginDTO {

	private String token;
	
	public RespostaLoginDTO() {
		
	}

	public RespostaLoginDTO(String token){
		this.setToken(token);
    }

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
