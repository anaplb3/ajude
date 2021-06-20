package com.dsc.ajude.controller;

import com.dsc.ajude.dto.LoginDTO;
import com.dsc.ajude.dto.RespostaLoginDTO;
import com.dsc.ajude.dto.UsuarioDTO;
import com.dsc.ajude.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.HttpClientErrorException;
import com.dsc.ajude.service.JwtService;
import com.dsc.ajude.service.UsuarioService;

import javax.servlet.ServletException;

@RestController
@RequestMapping("/v1/api/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService uruarioService;

	@Autowired
	private JwtService jwtService;

	public UsuarioController(UsuarioService usuarioService, JwtService jwtService){
		super();
		this.uruarioService = usuarioService;
		this.jwtService = jwtService;

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
			UsuarioDTO userCriado = this.uruarioService.criarUsuario(usuario);
			return new ResponseEntity<>(userCriado, HttpStatus.OK);

		} catch (HttpClientErrorException errorUsuario) {
			return new ResponseEntity<>(errorUsuario.getStatusCode());
		}
	}
	
	
}