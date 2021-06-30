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

import java.util.List;
import java.util.Optional;

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
	
	@GetMapping("/")
	private List<Usuario> retornarUsuarios() {
		return usuarioServico.retornarUsuarios();
	}

	@GetMapping("/{usuarioId}")
	public Optional<Usuario> recuperUsuarioId(@PathVariable String usuarioId){
		return usuarioServico.recuperarUsuariId(usuarioId);
	}


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
