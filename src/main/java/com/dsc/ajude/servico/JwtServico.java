package com.dsc.ajude.servico;

import java.util.Date;

import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsc.ajude.dto.LoginDTO;
import com.dsc.ajude.dto.RespostaLoginDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import com.dsc.ajude.filtro.TokenFiltro;

import javax.servlet.ServletException;

@Service
public class JwtServico {

	@Autowired
	private UsuarioServico usuarioServico;
	public static final String TOKEN_KEY = "TOKEN_VAIDACERTO";
	
	public RespostaLoginDTO autenticacao(LoginDTO login) {
		
		if(!usuarioServico.validarSenhaDoUsuario(login)) {
			return new RespostaLoginDTO("Usuário ou senha incorreto! Não foi possível realizar o login");
		}
		
		String token = gerarToken(login.getEmail());
		return new RespostaLoginDTO(token);
		
	}
	
	private String gerarToken(String email) {
		return Jwts.builder().setSubject(email).signWith(SignatureAlgorithm.HS512, TOKEN_KEY)
				.setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000)).compact();
	}

	public String getSujeitoDoToken(String authorizationHeader) throws ServletException {
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			throw new ServletException("Token inexistente ou mal formatado!");
		}

		// Extraindo apenas o token do cabecalho.
		String token = authorizationHeader.substring(TokenFiltro.TOKEN_INDEX);

		String subject = null;
		try {
			subject = Jwts.parser().setSigningKey(TOKEN_KEY).parseClaimsJws(token).getBody().getSubject();
		} catch (SignatureException e) {
			throw new ServletException("Token invalido ou expirado!");
		}
		return subject;
	}

}
