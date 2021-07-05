package com.dsc.ajude.dto;

import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.NotNull;

@Builder(builderClassName = "Builder")
@Data
public class LoginDTO {

	@NotNull(message = "Necessário informar o email.")
	private String email;

	@NotNull(message = "Necessário informar a senha.")
	private String senha;

}
