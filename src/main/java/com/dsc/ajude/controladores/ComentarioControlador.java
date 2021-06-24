package com.dsc.ajude.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dsc.ajude.dto.AdicionarComentarioDTO;
import com.dsc.ajude.excecoes.RecursoNaoEncontradoExcecao;
import com.dsc.ajude.servico.ComentarioServico;

@RestController
@RequestMapping("/v1/api/comentarios")
public class ComentarioControlador {
	
	@Autowired
	private ComentarioServico comentarioServico;
	
	@PostMapping("")
	public ResponseEntity<AdicionarComentarioDTO> adicionarComentario(@RequestBody AdicionarComentarioDTO adicionarComentario) {
		try {
			return new ResponseEntity<>(comentarioServico.adicionarComentario(adicionarComentario), HttpStatus.OK);
		} catch (RecursoNaoEncontradoExcecao e) {
			
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} 
	}

	
}
