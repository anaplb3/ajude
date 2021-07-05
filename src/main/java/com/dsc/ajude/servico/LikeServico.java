package com.dsc.ajude.servico;

import com.dsc.ajude.dto.LikeDTO;
import com.dsc.ajude.dto.ResponseCampanhaDTO;
import com.dsc.ajude.excecoes.PermissaoNegadaExcecao;
import com.dsc.ajude.excecoes.RecursoNaoEncontradoExcecao;
import com.dsc.ajude.modelos.Campanha;
import com.dsc.ajude.modelos.Like;
import com.dsc.ajude.modelos.Status;
import com.dsc.ajude.repositorios.CampanhaRepositorio;
import com.dsc.ajude.repositorios.LikeRepositorio;
import com.dsc.ajude.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.ServletException;
import javax.transaction.Transactional;
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


    public ResponseCampanhaDTO adicionarLike(LikeDTO likeDTO) throws PermissaoNegadaExcecao, RecursoNaoEncontradoExcecao, ServletException {
        Campanha campanha = campanhaRepositorio.findByCampanhaIdAndStatus(likeDTO.getIdCampanha(), Status.ATIVA);

        if (campanha == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }


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
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Usuário já deu like!");
        }

        return this.removeLikeDaCampanha(campanha, likes, likeDTO.getEmailUsuario());
    }

    @Transactional
    public Campanha removeLikeDaCampanha(Campanha campanha, List<Like> likes, String email) {

        Like likeASerRemovido = null;

        for (Like like : likes) {
            if (like.getUsuario().getEmail().equals(email)) {
                likeASerRemovido = like;
            }
        }

        if (likeASerRemovido != null) {
            likes.remove(likeASerRemovido);
            likeRepositorio.deleteById(likeASerRemovido.getId());
        }
        campanha.setLikes(likes);
        campanha.setQuantidadeLikes(likes.size());

        return this.campanhaRepositorio.save(campanha);
    }


    @Transactional
    public ResponseCampanhaDTO adicionandoLikeACampanha(Campanha campanha, List<Like> likes, String email) {
        Like like = new Like();
        like.setUsuario(usuarioRepositorio.getById(email));
        like.setCampanha(campanha);


        likes.add(this.likeRepositorio.save(like));

        campanha.setLikes(likes);
        campanha.setQuantidadeLikes(likes.size());

        this.campanhaRepositorio.save(campanha);
        return new ResponseCampanhaDTO(campanha);
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
