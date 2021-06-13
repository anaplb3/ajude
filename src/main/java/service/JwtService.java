package service;

import java.util.Date;

import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dto.LoginDTO;
import dto.RespostaLoginDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import token.TokenFilter;

import javax.servlet.ServletException;

@Service
public class JwtService {

	@Autowired
	private UsuarioService usuarioService;
	private final String TOKEN_KEY = "TOKEN_VAIDACERTO";
	
	public RespostaLoginDTO autenticacao(LoginDTO login) {
		
		if(usuarioService.validarSenhaDoUsuario(login)) {
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
		String token = authorizationHeader.substring(TokenFilter.TOKEN_INDEX);

		String subject = null;
		try {
			subject = Jwts.parser().setSigningKey(TOKEN_KEY).parseClaimsJws(token).getBody().getSubject();
		} catch (SignatureException e) {
			throw new ServletException("Token invalido ou expirado!");
		}
		return subject;
	}

}
