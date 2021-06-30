package com.dsc.ajude.controladores;

import com.dsc.ajude.excecoes.PermissaoNegadaExcecao;
import com.dsc.ajude.excecoes.RecursoNaoEncontradoExcecao;
import com.dsc.ajude.modelos.Campanha;
import com.dsc.ajude.servico.LikeServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;

@RestController
public class LikeControlador {


    @Autowired
    private LikeServico likeServico;

    private final String AUTH = "Authorization";


    @PostMapping("")
    public ResponseEntity<?> adicionarLike(@PathVariable long id, @RequestParam String email, @RequestHeader(AUTH) String header) {
        try {
            return new ResponseEntity<Campanha>(likeServico.adicionarLike(id, email, header), HttpStatus.OK);

        } catch (RecursoNaoEncontradoExcecao e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (PermissaoNegadaExcecao permissaoNegadaExcecao) {
            return new ResponseEntity<>(permissaoNegadaExcecao.getMessage(), HttpStatus.UNAUTHORIZED);

        } catch (ServletException e) {
            return null;
        }
    }

    @DeleteMapping("")
    public ResponseEntity<?> removerLike(@PathVariable long id, @RequestParam String email, @RequestHeader(AUTH) String header){
        try {
            return new ResponseEntity<Campanha>(likeServico.removerLike(id, email, header), HttpStatus.OK);

        } catch (ServletException e) {
            return null;

        } catch (PermissaoNegadaExcecao permissaoNegadaExcecao) {
            return new ResponseEntity<>(permissaoNegadaExcecao.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}