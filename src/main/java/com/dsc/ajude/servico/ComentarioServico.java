package com.dsc.ajude.servico;

import java.util.Objects;
import java.util.Optional;

import com.dsc.ajude.excecoes.PermissaoNegadaExcecao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.dsc.ajude.dto.AdicionarComentarioDTO;
import com.dsc.ajude.excecoes.RecursoNaoEncontradoExcecao;
import com.dsc.ajude.modelos.Campanha;
import com.dsc.ajude.modelos.Comentario;
import com.dsc.ajude.modelos.Usuario;
import com.dsc.ajude.repositorios.CampanhaRepositorio;
import com.dsc.ajude.repositorios.ComentarioRepositorio;
import com.dsc.ajude.repositorios.UsuarioRepositorio;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.ServletException;

@Service
public class ComentarioServico {
	
	@Autowired
	private ComentarioRepositorio comentarioRepositorio;
	
	@Autowired
	private CampanhaRepositorio campanhaRepositorio;
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Autowired
	private UsuarioServico usuarioServico;
	
	
	public AdicionarComentarioDTO adicionarComentario(AdicionarComentarioDTO comentarioAdicionado) throws RecursoNaoEncontradoExcecao {
		Usuario donoComentario = this.usuarioRepositorio.getById(comentarioAdicionado.getEmailDoUsuario());
		Optional<Campanha> campanha = this.campanhaRepositorio.findById(comentarioAdicionado.getIdCampanha());
		
		if(donoComentario == null || !campanha.isPresent()) {
			throw new RecursoNaoEncontradoExcecao();
		}
		
		Comentario novoComentario = new Comentario();
		novoComentario.setConteudoDaMensagem(comentarioAdicionado.getCorpoDaMensagem());
		novoComentario.setUsuario(donoComentario);
		
		if(Objects.isNull(comentarioAdicionado.getIdCampanha())) {
			Comentario comentarioParaComentario = comentarioRepositorio.findById(comentarioAdicionado.getIdComentario()).get(); 
			comentarioParaComentario.setRespostasUsuarios(novoComentario);
			comentarioRepositorio.save(comentarioParaComentario);
		} else if (Objects.nonNull(comentarioAdicionado.getIdComentario())) {
			novoComentario.setCampanhaReferencia(campanha.get());
	
		}
		
		comentarioRepositorio.save(novoComentario);
		
		return comentarioAdicionado;
	}

	public void removerComentario(long idComentario, String authorizationHeader, String email) throws ServletException, PermissaoNegadaExcecao {
		if(!this.usuarioServico.usuarioTemPermissao(authorizationHeader, email)){
			throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
		}

		Optional<Comentario> comentarioASerDeletado = comentarioRepositorio.findById(idComentario);

		if(comentarioASerDeletado.isEmpty()){
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		}

		comentarioRepositorio.deleteById(idComentario);

	}

}
