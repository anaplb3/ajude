package com.dsc.ajude.servico;

import com.dsc.ajude.excecoes.PermissaoNegadaExcecao;
import com.dsc.ajude.excecoes.RecursoNaoEncontradoExcecao;
import com.dsc.ajude.modelos.Campanha;
import com.dsc.ajude.modelos.Like;
import com.dsc.ajude.repositorios.CampanhaRepositorio;
import com.dsc.ajude.repositorios.LikeRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.ServletException;
import java.util.List;
import java.util.Optional;

@Service
public class LikeServico {

    @Autowired
    private LikeRepositorio likeRepositorio;

    @Autowired
    private CampanhaRepositorio campanhaRepositorio;

    @Autowired
    private UsuarioServico usuarioServico;


    public Campanha adicionarLike(long idCampanha, String email, String header) throws PermissaoNegadaExcecao, RecursoNaoEncontradoExcecao, ServletException {

        if(!this.usuarioServico.usuarioTemPermissao(header, email)){
            throw new PermissaoNegadaExcecao(HttpStatus.NOT_FOUND);
        }

        Optional<Campanha> retornarCampanha = campanhaRepositorio.findById(idCampanha);

        if(retornarCampanha.isPresent()){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }

        Campanha campanha = retornarCampanha.get();

        List<Like> likes = campanha.getLikes();

        boolean realizouLike = usuarioRealizouLike(likes, email);

        if(realizouLike){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }

        return adicionandoLikeACampanha(campanha, likes, email);

    }

    public Campanha removerLike(long idCampanhaASerRemovida, String email, String header) throws ServletException, PermissaoNegadaExcecao {
        if(!this.usuarioServico.usuarioTemPermissao(header, email)) {
            throw new PermissaoNegadaExcecao(HttpStatus.NOT_FOUND);
        }

        Optional<Campanha> retornaCampanha = this.campanhaRepositorio.findById(idCampanhaASerRemovida);

        if(retornaCampanha.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }

        Campanha campanha = retornaCampanha.get();

        List<Like> likes = campanha.getLikes();

        boolean realizouLike = usuarioRealizouLike(likes, email);

        if(!realizouLike){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }

        return this.removeLikeDaCampanha(campanha, likes, email);
    }

    private Campanha removeLikeDaCampanha(Campanha campanha, List<Like> likes, String email) {

        Like likeASerRemovido = null;

        for(Like like : likes) {
            if(like.getUsuario().equals(email)) {
                likeASerRemovido = like;
            }
        }

        if(likeASerRemovido != null) {
            likes.remove(likeASerRemovido);
        }
        campanha.setLikes(likes);
        return this.campanhaRepositorio.save(campanha);
    }


    private Campanha adicionandoLikeACampanha(Campanha campanha, List<Like> likes, String email) {
        Like like = new Like(email);

        this.likeRepositorio.save(like);

        likes.add(like);

        campanha.setLikes(likes);

        return this.campanhaRepositorio.save(campanha);
    }

    private boolean usuarioRealizouLike(List<Like> likes, String email) {
        boolean realizouLike = false;

        for(Like like : likes) {
            if (like.getUsuario().equals(email)) {
                realizouLike = true;
            }
        }
        return realizouLike;
    }


}
