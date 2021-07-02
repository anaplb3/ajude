package com.dsc.ajude.servico;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.dsc.ajude.excecoes.PermissaoNegadaExcecao;
import com.dsc.ajude.modelos.Status;
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
import javax.transaction.Transactional;

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

	private final String AUTH = "Authorization";
	
	@Transactional
	public AdicionarComentarioDTO adicionarComentario(AdicionarComentarioDTO comentarioAdicionado) throws RecursoNaoEncontradoExcecao, PermissaoNegadaExcecao {
		Usuario donoComentario = this.usuarioRepositorio.getById(comentarioAdicionado.getEmailDoUsuario());
		if (donoComentario == null) {
			throw new PermissaoNegadaExcecao();
		}
		Campanha campanha = this.campanhaRepositorio.getById(comentarioAdicionado.getIdCampanha());
		if (campanha == null) {
			throw new RecursoNaoEncontradoExcecao();
		}
		Comentario novoComentario = new Comentario();
		novoComentario.setConteudoDaMensagem(comentarioAdicionado.getCorpoDaMensagem());
		novoComentario.setUsuario(donoComentario);
		novoComentario.setCampanha(campanha);

		comentarioRepositorio.save(novoComentario);

		return comentarioAdicionado;
	}

	@Transactional
	public AdicionarComentarioDTO adicionarRespostaComentario(AdicionarComentarioDTO respostaComentario) throws RecursoNaoEncontradoExcecao, PermissaoNegadaExcecao {
		Usuario donoComentario = this.usuarioRepositorio.getById(respostaComentario.getEmailDoUsuario());
		if (donoComentario == null) {
			throw new PermissaoNegadaExcecao();
		}

		if (comentarioRepositorio.findById(respostaComentario.getIdComentario()).isEmpty()) {
			throw new RecursoNaoEncontradoExcecao();
		}

		Comentario comentarioExistente = comentarioRepositorio.findById(respostaComentario.getIdComentario()).get();

		Campanha campanhaAssociada = comentarioExistente.getCampanha();

		if(Objects.nonNull(comentarioExistente.getRespostasUsuarios())){
			// Já existe resposta associada
			throw new PermissaoNegadaExcecao("Já existe uma resposta para esse comentário");
		}

		Comentario resposta = new Comentario();
		resposta.setConteudoDaMensagem(respostaComentario.getCorpoDaMensagem());
		resposta.setUsuario(donoComentario);
		resposta.setCampanha(campanhaAssociada);

		comentarioExistente.setRespostasUsuarios(comentarioRepositorio.save(resposta));

		comentarioRepositorio.save(comentarioExistente);

		return respostaComentario;
	}

	public Comentario removerComentario(long idComentario, String authorizationHeader) throws RecursoNaoEncontradoExcecao, PermissaoNegadaExcecao {
		Optional<Comentario> comentarioASerDeletado = comentarioRepositorio.findById(idComentario);

		if (comentarioASerDeletado.isPresent()) {
			try {
				if (usuarioServico.usuarioTemPermissao(authorizationHeader, comentarioASerDeletado.get().getUsuario().getEmail())) {
					comentarioRepositorio.deleteById(idComentario);
					return comentarioASerDeletado.get();
				} else {
					throw new PermissaoNegadaExcecao();
				}

			} catch (ServletException e) {
				throw new PermissaoNegadaExcecao();
			}

		} else {
			throw new RecursoNaoEncontradoExcecao();
		}

	}


}
