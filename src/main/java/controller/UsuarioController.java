package controller;

import dto.LoginDTO;
import dto.RespostaLoginDTO;
import dto.UsuarioDTO;
import model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.client.HttpClientErrorException;
import service.JwtService;
import service.UsuarioService;

import javax.servlet.ServletException;

@RestController
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
	
	@GetMapping("/")
	private ResponseEntity<String> verificarSaude() {
		return new ResponseEntity<String>("Server is running!", HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<RespostaLoginDTO> realizarLogin(@RequestBody LoginDTO login) throws ServletException {
		return new ResponseEntity<RespostaLoginDTO>(jwtService.autenticacao(login), HttpStatus.OK);
	}

	@PostMapping("/usuarios")
	public ResponseEntity<UsuarioDTO> criarUsuario(@RequestBody Usuario usuario){
		try {
			UsuarioDTO userCriado = this.uruarioService.criarUsuario(usuario);
			return new ResponseEntity<UsuarioDTO>(userCriado, HttpStatus.OK);

		} catch (HttpClientErrorException errorUsuario) {
			return new ResponseEntity<UsuarioDTO>(errorUsuario.getStatusCode());
		}
	}
	
	
}
