package com.dsc.ajude.servico;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsc.ajude.dto.AdicionarComentarioDTO;
import com.dsc.ajude.excecoes.RecursoNaoEncontradoExcecao;
import com.dsc.ajude.modelos.Campanha;
import com.dsc.ajude.modelos.Comentario;
import com.dsc.ajude.modelos.Usuario;
import com.dsc.ajude.repositorios.CampanhaRepositorio;
import com.dsc.ajude.repositorios.ComentarioRepositorio;
import com.dsc.ajude.repositorios.UsuarioRepositorio;

@Service
public class ComentarioServico {
	
	@Autowired
	private ComentarioRepositorio comentarioRepositorio;
	
	@Autowired
	private CampanhaRepositorio campanhaRepositorio;
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	
	public AdicionarComentarioDTO adicionarComentario(AdicionarComentarioDTO comentarioAdicionado) throws RecursoNaoEncontradoExcecao {
		Usuario donoComentario = this.usuarioRepositorio.getById(comentarioAdicionado.getEmailDoUsuario());
		Optional<Campanha> campanha = this.campanhaRepositorio.findById(comentarioAdicionado.getIdCampanha());
		
		if(donoComentario == null || !campanha.isPresent()) {
			throw new RecursoNaoEncontradoExcecao();
		}
		
		Comentario novoComentario = new Comentario();
		novoComentario.setConteudoDaMensagem(corpoDaMensagem);
		novoComentario.setUsuario(donoDoComentario);
		
		if(Objects.nonNull(comentarioAdicionado.getIdCampanha())) {
			Comentario comentarioParaComentario = comentarioRepositorio.findById(comentarioAdicionado.getIdComentario()).get(); 
			comentarioParaComentario.setRespostasUsuarios(novoComentario);
			comentarioRepositorio.save(comentarioParaComentario);
			
		} else if (Objects.nonNull(comentarioAdicionado.getIdComentario())) {
			novoComentario.setCampanhaReferencia(campanha);
	
		}
		
		comentarioRepositorio.save(novoComentario);
		
		return comentarioAdicionado;
	}

}
