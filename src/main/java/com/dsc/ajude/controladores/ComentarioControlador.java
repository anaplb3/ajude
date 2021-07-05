package com.dsc.ajude.controladores;

import com.dsc.ajude.dto.AdicionarRespostaComentarioDTO;
import com.dsc.ajude.excecoes.PermissaoNegadaExcecao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dsc.ajude.dto.AdicionarComentarioDTO;
import com.dsc.ajude.excecoes.RecursoNaoEncontradoExcecao;
import com.dsc.ajude.servico.ComentarioServico;

import javax.servlet.ServletException;
import javax.validation.Valid;

@RestController
@RequestMapping("/v1/api/auth/comentarios")
public class ComentarioControlador {
	
	@Autowired
	private ComentarioServico comentarioServico;

	private final String AUTH = "Authorization";

	@PostMapping("")
	public ResponseEntity<?> adicionarComentario(@Valid @RequestBody AdicionarComentarioDTO adicionarComentario) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(comentarioServico.adicionarComentario(adicionarComentario));
		} catch (RecursoNaoEncontradoExcecao e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}  catch (PermissaoNegadaExcecao e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

	@PostMapping("/resposta")
	public ResponseEntity<?> adicionarRespostaComentario(@Valid @RequestBody AdicionarRespostaComentarioDTO adicionarResposta) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(comentarioServico.adicionarRespostaComentario(adicionarResposta));
		} catch (RecursoNaoEncontradoExcecao e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}  catch (PermissaoNegadaExcecao e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletarComentario(@PathVariable long id, @RequestHeader(AUTH) String header){
		try{
			return ResponseEntity.status(HttpStatus.OK).body(comentarioServico.removerComentario(id, header));
		} catch (RecursoNaoEncontradoExcecao erro) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro.getMessage());
		} catch (PermissaoNegadaExcecao erroDePermissao){
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erroDePermissao.getMessage());
		}
	}



	
}
