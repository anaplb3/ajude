package controller;

import exceptions.DeadlineInvalidoException;
import exceptions.PermissaoNegada;
import exceptions.RecursoNaoEncontradoException;
import model.Campanha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.CampanhaService;

import javax.servlet.ServletException;

@RestController
@RequestMapping("/v1/api/auth/campanhas")
public class CampanhaController {

    @Autowired
    CampanhaService campanhaService;
    private final String AUTH = "Authorization";

    @PostMapping("")
    private ResponseEntity<?> addCampanha(@RequestBody Campanha campanha, @RequestHeader(AUTH) String header) {
        try {
            return new ResponseEntity<>(campanhaService.addCampanha(campanha, header), HttpStatus.CREATED);
        } catch (PermissaoNegada e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (DeadlineInvalidoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deletaCampanha(@PathVariable long id, @RequestHeader(AUTH) String header) {
        try {
            return new ResponseEntity<>(campanhaService.encerraCampanha(id, header), HttpStatus.OK);
        } catch (RecursoNaoEncontradoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (PermissaoNegada e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getCampanha(@PathVariable long id, @RequestHeader(AUTH) String header) {
        try {
            return new ResponseEntity<>(campanhaService.getCampanhaPorId(id, header), HttpStatus.OK);
        } catch (RecursoNaoEncontradoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (PermissaoNegada e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

}