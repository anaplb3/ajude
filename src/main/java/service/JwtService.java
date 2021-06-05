package service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dto.LoginDTO;
import dto.RespostaLoginDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

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
}
