package com.dsc.ajude.modelos;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Data
@Table(name = "usuarios")
public class Usuario {

    @Id
	@Column(name = "email")
	@NotNull
    private String email;

	@NotNull
    @Column(name = "primeiro_nome")
    private String primeiroNome;

	@NotNull
    @Column(name = "ultimo_nome")
    private String ultimoNome;

	@NotNull
    @Column(name = "numero_do_cartao")
    private String numeroDoCartao;

	@NotNull
    @Column(name = "senha_do_usuario")
    private String senhaUsuario;
    
	public Usuario(String email, String primeiroNome, String ultimoNome, String numeroDoCartao, String senhaUsuario) {
		super();
		this.email = email;
		this.primeiroNome = primeiroNome;
		this.ultimoNome = ultimoNome;
		this.numeroDoCartao = numeroDoCartao;
		this.senhaUsuario = senhaUsuario;
	}

}
