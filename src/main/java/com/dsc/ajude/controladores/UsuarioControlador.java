package com.dsc.ajude.controladores;

import com.dsc.ajude.dto.UsuarioDTO;
import com.dsc.ajude.modelos.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.HttpClientErrorException;
import com.dsc.ajude.servico.JwtServico;
import com.dsc.ajude.servico.UsuarioServico;

@RestController
@RequestMapping("/v1/api/usuarios")
public class UsuarioControlador {

	@Autowired
	private UsuarioServico usuarioServico;

	@Autowired
	private JwtServico jwtServico;

	public UsuarioControlador(UsuarioServico usuarioServico, JwtServico jwtServico){
		super();
		this.usuarioServico = usuarioServico;
		this.jwtServico = jwtServico;

	}
	
/*	@GetMapping("/")
	private ResponseEntity<String> verificarSaude() {
		return new ResponseEntity<>("Server is running!", HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<RespostaLoginDTO> realizarLogin(@RequestBody LoginDTO login) throws ServletException {
		return new ResponseEntity<>(jwtService.autenticacao(login), HttpStatus.OK);
	}*/

	@PostMapping("")
	public ResponseEntity<UsuarioDTO> criarUsuario(@RequestBody Usuario usuario){
		try {
			UsuarioDTO userCriado = this.usuarioServico.criarUsuario(usuario);
			return new ResponseEntity<>(userCriado, HttpStatus.OK);

		} catch (HttpClientErrorException errorUsuario) {
			return new ResponseEntity<>(errorUsuario.getStatusCode());
		}
	}
	
	
}
