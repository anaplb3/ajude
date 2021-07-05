package com.dsc.ajude.controladores;

import com.dsc.ajude.dto.*;
import com.dsc.ajude.excecoes.DataInvalidaExcecao;
import com.dsc.ajude.excecoes.PermissaoNegadaExcecao;
import com.dsc.ajude.excecoes.RecursoNaoEncontradoExcecao;
import com.dsc.ajude.modelos.Campanha;
import com.dsc.ajude.servico.DoacaoServico;
import com.dsc.ajude.servico.LikeServico;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.dsc.ajude.servico.CampanhaServico;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.ServletException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/api/auth/campanhas")
public class CampanhaControlador {

    @Autowired
    CampanhaServico campanhaServico;

    @Autowired
    private  DoacaoServico doacaoServico;

    @Autowired
    private LikeServico likeServico;
    private final String AUTH = "Authorization";

    @ApiOperation("Adicionar uma campanha")
    @PostMapping("")
    private ResponseEntity<?> addCampanha(@Valid @RequestBody CampanhaDTO campanha, @RequestHeader(AUTH) String header)  {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(campanhaServico.addCampanha(campanha, header));
        } catch (PermissaoNegadaExcecao e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (DataInvalidaExcecao e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deletaCampanha(@PathVariable long id, @RequestHeader(AUTH) String header) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(campanhaServico.encerraCampanha(id, header));
        } catch (RecursoNaoEncontradoExcecao e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (PermissaoNegadaExcecao e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getCampanha(@PathVariable long id, @RequestHeader(AUTH) String header) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseCampanhaDTO(campanhaServico.getCampanhaPorId(id, header)));
        } catch (RecursoNaoEncontradoExcecao e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (PermissaoNegadaExcecao e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/ativas")
    private ResponseEntity<?> campanhasAtivas(@RequestBody BuscarCampanhasAtivasDTO ordenarPor, @RequestHeader(AUTH) String header)  {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseCampanhasAtivasDTO.convertAll(campanhaServico.campanhasAtivas(ordenarPor, header)));
        } catch (PermissaoNegadaExcecao e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (DataInvalidaExcecao e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/substring")
    private ResponseEntity<?> getCampanhaPorSubstring(@Valid @RequestBody CampanhaSubstringDTO campanhaSubstringDTO) {
        List<Campanha> campanhas = campanhaServico.getCampanhaPorSubstring(campanhaSubstringDTO);
        HttpStatus httpStatus = campanhas == null || campanhas.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(campanhas);
    }

    @PostMapping("/likes")
    public ResponseEntity<?> adicionarLike(@Valid @RequestBody LikeDTO likeDTO)  {
        try {
            return new ResponseEntity<>(likeServico.adicionarLike(likeDTO), HttpStatus.OK);

        } catch (RecursoNaoEncontradoExcecao e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (PermissaoNegadaExcecao | HttpClientErrorException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (ServletException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/likes")
    public ResponseEntity<?> removerLike(@Valid @RequestBody LikeDTO likeDTO, @RequestHeader(AUTH) String header){
        try {
            return new ResponseEntity<>(likeServico.removerLike(likeDTO, header), HttpStatus.OK);
        } catch (ServletException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

        } catch (PermissaoNegadaExcecao | HttpClientErrorException permissaoNegadaExcecao) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(permissaoNegadaExcecao.getMessage());
        }
    }

    @PostMapping("/doacao")
    public ResponseEntity<?> adicionarDoacao(@Valid @RequestBody DoacaoDTO doacaoDTO)  {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.doacaoServico.adicionarDoacao(doacaoDTO));
        } catch (RecursoNaoEncontradoExcecao e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (PermissaoNegadaExcecao | HttpClientErrorException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (ServletException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
