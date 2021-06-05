package dto;

import lombok.Builder;

@Builder(builderClassName = "Builder")
public class UsuarioDTO {
    private String email;
    private String primeiroNome;
    private String ultimoNome;
    
    public UsuarioDTO(String email, String primeiroNome, String ultimoNome) {
    	this.email = email;
    	this.primeiroNome = primeiroNome;
    	this.ultimoNome = ultimoNome;	
    }
    
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPrimeiroNome() {
		return primeiroNome;
	}
	public void setPrimeiroNome(String primeiroNome) {
		this.primeiroNome = primeiroNome;
	}
	public String getUltimoNome() {
		return ultimoNome;
	}
	public void setUltimoNome(String ultimoNome) {
		this.ultimoNome = ultimoNome;
	}
}
