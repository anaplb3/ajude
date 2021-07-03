package com.dsc.ajude.servico;

import com.dsc.ajude.dto.LikeDTO;
import com.dsc.ajude.excecoes.PermissaoNegadaExcecao;
import com.dsc.ajude.excecoes.RecursoNaoEncontradoExcecao;
import com.dsc.ajude.modelos.Campanha;
import com.dsc.ajude.modelos.Like;
import com.dsc.ajude.repositorios.CampanhaRepositorio;
import com.dsc.ajude.repositorios.LikeRepositorio;
import com.dsc.ajude.repositorios.UsuarioRepositorio;
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
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private UsuarioServico usuarioServico;


    public Campanha adicionarLike(LikeDTO likeDTO) throws PermissaoNegadaExcecao, RecursoNaoEncontradoExcecao, ServletException {
        Optional<Campanha> retornarCampanha = campanhaRepositorio.findById(likeDTO.getIdCampanha());

        if (retornarCampanha.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }

        Campanha campanha = retornarCampanha.get();

        List<Like> likes = campanha.getLikes();

        if (usuarioJaRealizouLike(likes, likeDTO.getEmailUsuario())) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }

        return adicionandoLikeACampanha(campanha, likes, likeDTO.getEmailUsuario());

    }

    public Campanha removerLike(LikeDTO likeDTO, String header) throws ServletException, PermissaoNegadaExcecao {
        if(!this.usuarioServico.usuarioTemPermissao(header, likeDTO.getEmailUsuario())) {
            throw new PermissaoNegadaExcecao(HttpStatus.NOT_FOUND);
        }

        Optional<Campanha> retornaCampanha = this.campanhaRepositorio.findById(likeDTO.getIdCampanha());

        if (retornaCampanha.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }

        Campanha campanha = retornaCampanha.get();
        List<Like> likes = campanha.getLikes();

        if (!usuarioJaRealizouLike(likes, likeDTO.getEmailUsuario())) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }

        return this.removeLikeDaCampanha(campanha, likes, likeDTO.getEmailUsuario());
    }

    private Campanha removeLikeDaCampanha(Campanha campanha, List<Like> likes, String email) {

        Like likeASerRemovido = null;

        for (Like like : likes) {
            if (like.getUsuario().getEmail().equals(email)) {
                likeASerRemovido = like;
            }
        }

        if (likeASerRemovido != null) {
            likes.remove(likeASerRemovido);
        }
        campanha.setLikes(likes);
        return this.campanhaRepositorio.save(campanha);
    }


    private Campanha adicionandoLikeACampanha(Campanha campanha, List<Like> likes, String email) {
        Like like = new Like();
        like.setUsuario(usuarioRepositorio.getById(email));


        likes.add(this.likeRepositorio.save(like));

        campanha.setLikes(likes);

        return this.campanhaRepositorio.save(campanha);
    }

    private boolean usuarioJaRealizouLike(List<Like> likes, String email) {
        for (Like like : likes) {
            if (like.getUsuario().getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }


}
