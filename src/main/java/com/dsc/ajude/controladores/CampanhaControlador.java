package com.dsc.ajude.controladores;

import com.dsc.ajude.dto.CampanhaDTO;
import com.dsc.ajude.dto.CampanhaSubstringDTO;
import com.dsc.ajude.dto.LikeDTO;
import com.dsc.ajude.excecoes.DataInvalidaExcecao;
import com.dsc.ajude.excecoes.PermissaoNegadaExcecao;
import com.dsc.ajude.excecoes.RecursoNaoEncontradoExcecao;
import com.dsc.ajude.modelos.Campanha;
import com.dsc.ajude.servico.LikeServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.dsc.ajude.servico.CampanhaServico;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.ServletException;
import java.util.List;

@RestController
@RequestMapping("/v1/api/auth/campanhas")
public class CampanhaControlador {

    @Autowired
    CampanhaServico campanhaServico;

    @Autowired
    private LikeServico likeServico;
    private final String AUTH = "Authorization";

    @PostMapping("")
    private ResponseEntity<?> addCampanha(@RequestBody CampanhaDTO campanha, @RequestHeader(AUTH) String header)  {
        try {
            return new ResponseEntity<>(campanhaServico.addCampanha(campanha, header), HttpStatus.CREATED);
        } catch (PermissaoNegadaExcecao e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (DataInvalidaExcecao e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deletaCampanha(@PathVariable long id, @RequestHeader(AUTH) String header) {
        try {
            return new ResponseEntity<>(campanhaServico.encerraCampanha(id, header), HttpStatus.OK);
        } catch (RecursoNaoEncontradoExcecao e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (PermissaoNegadaExcecao e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getCampanha(@PathVariable long id, @RequestHeader(AUTH) String header) {
        try {
            return new ResponseEntity<>(campanhaServico.getCampanhaPorId(id, header), HttpStatus.OK);
        } catch (RecursoNaoEncontradoExcecao e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (PermissaoNegadaExcecao e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/substring")
    private ResponseEntity<?> getCampanhaPorSubstring(@RequestBody CampanhaSubstringDTO campanhaSubstringDTO) {
        List<Campanha> campanhas = campanhaServico.getCampanhaPorSubstring(campanhaSubstringDTO);
        HttpStatus httpStatus = campanhas == null || campanhas.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(campanhas, httpStatus);
    }

    @PostMapping("/likes")
    public ResponseEntity<?> adicionarLike(@RequestBody LikeDTO likeDTO)  {
        try {
            return new ResponseEntity<>(likeServico.adicionarLike(likeDTO), HttpStatus.OK);

        } catch (RecursoNaoEncontradoExcecao e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        } catch (PermissaoNegadaExcecao | HttpClientErrorException permissaoNegadaExcecao) {
            return new ResponseEntity<>(permissaoNegadaExcecao.getMessage(), HttpStatus.UNAUTHORIZED);

        } catch (ServletException e) {
            return null;
        }
    }

    @DeleteMapping("/likes")
    public ResponseEntity<?> removerLike(@RequestBody LikeDTO likeDTO, @RequestHeader(AUTH) String header){
        try {
            return new ResponseEntity<>(likeServico.removerLike(likeDTO, header), HttpStatus.OK);
        } catch (ServletException e) {
            return null;

        } catch (PermissaoNegadaExcecao permissaoNegadaExcecao) {
            return new ResponseEntity<>(permissaoNegadaExcecao.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

}
