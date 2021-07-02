package com.dsc.ajude.controladores;

import com.dsc.ajude.excecoes.PermissaoNegadaExcecao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dsc.ajude.dto.AdicionarComentarioDTO;
import com.dsc.ajude.excecoes.RecursoNaoEncontradoExcecao;
import com.dsc.ajude.servico.ComentarioServico;

import javax.servlet.ServletException;

@RestController
@RequestMapping("/v1/api/comentarios")
public class ComentarioControlador {
	
	@Autowired
	private ComentarioServico comentarioServico;

	private final String AUTH = "Authorization";

	@PostMapping("")
	public ResponseEntity<?> adicionarComentario(@RequestBody AdicionarComentarioDTO adicionarComentario) {
		try {
			return new ResponseEntity<>(comentarioServico.adicionarComentario(adicionarComentario), HttpStatus.OK);
		} catch (RecursoNaoEncontradoExcecao e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}  catch (PermissaoNegadaExcecao e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("/resposta")
	public ResponseEntity<?> adicionarRespostaComentario(@RequestBody AdicionarComentarioDTO adicionarComentario) {
		try {
			return new ResponseEntity<>(comentarioServico.adicionarRespostaComentario(adicionarComentario), HttpStatus.OK);
		} catch (RecursoNaoEncontradoExcecao e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}  catch (PermissaoNegadaExcecao e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletarComentario(@PathVariable long id, @RequestHeader(AUTH) String header){
		try{
			return new ResponseEntity<>(comentarioServico.removerComentario(id, header), HttpStatus.OK);
		} catch (RecursoNaoEncontradoExcecao erro) {
			return new ResponseEntity<>(erro.getMessage(), HttpStatus.NOT_FOUND);
		} catch (PermissaoNegadaExcecao erroDePermissao){
			return new ResponseEntity<>(erroDePermissao.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}



	
}
