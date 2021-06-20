package com.dsc.ajude.controladores;

import com.dsc.ajude.dto.CampanhaDTO;
import com.dsc.ajude.excecoes.DataInvalidaExcecao;
import com.dsc.ajude.excecoes.PermissaoNegadaExcecao;
import com.dsc.ajude.excecoes.RecursoNaoEncontradoExcecao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.dsc.ajude.servico.CampanhaServico;

@RestController
@RequestMapping("/v1/api/auth/campanhas")
public class CampanhaControlador {

    @Autowired
    CampanhaServico campanhaServico;
    private final String AUTH = "Authorization";

    @PostMapping("")
    private ResponseEntity<?> addCampanha(@RequestBody CampanhaDTO campanha, @RequestHeader(AUTH) String header) {
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

}
