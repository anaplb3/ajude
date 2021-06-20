package com.dsc.ajude.service;

import com.dsc.ajude.exceptions.DeadlineInvalidoException;
import com.dsc.ajude.exceptions.PermissaoNegada;
import com.dsc.ajude.exceptions.RecursoNaoEncontradoException;
import com.dsc.ajude.model.Campanha;
import com.dsc.ajude.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dsc.ajude.repository.CampanhaRepository;

import javax.servlet.ServletException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CampanhaService {

    @Autowired
    private CampanhaRepository campanhaRepository;
    @Autowired
    private UsuarioService usuarioService;

    public Campanha addCampanha(Campanha campanha, String authHeader) throws DeadlineInvalidoException, PermissaoNegada {
        try {
           if (usuarioService.usuarioTemPermissao(authHeader, campanha.getDono().getEmail())) {
                campanha.setStatus(Status.ATIVA);
                if (campanha.getDeadline().before(new Date())) {
                    throw new DeadlineInvalidoException();
                }
                campanhaRepository.save(campanha);
                return campanha;
            } else {
               throw new PermissaoNegada();
           }
        } catch (ServletException e) {
            throw new PermissaoNegada();
        }

    }

    public Campanha encerraCampanha(long id, String authHeader) throws RecursoNaoEncontradoException, PermissaoNegada {
        Optional<Campanha> campanha = campanhaRepository.findById(id);
        if (campanha.isPresent()) {
            try {
                if (usuarioService.usuarioTemPermissao(authHeader, campanha.get().getDono().getEmail())) {
                    campanha.get().setStatus(Status.ENCERRADA);
                    campanhaRepository.save(campanha.get());
                    return campanha.get();
                } else {
                    throw new PermissaoNegada();
                }
            } catch (ServletException e) {
                throw new PermissaoNegada();
            }


        } else {
            throw new RecursoNaoEncontradoException();
        }
    }

    public List<Campanha> getCampanhaPorSubstring(String substring, boolean todosOsResultados) {
        return campanhaRepository.findByTitleLike(substring, todosOsResultados ? null : Status.ATIVA.ordinal());
    }

    public Campanha getCampanhaPorId(long id, String authHeader) throws RecursoNaoEncontradoException, PermissaoNegada {
        Optional<Campanha> campanha = campanhaRepository.findById(id);
        if (campanha.isPresent()) {
            try {
                if (usuarioService.usuarioTemPermissao(authHeader, campanha.get().getDono().getEmail())) {
                    return campanha.get();
                } else {
                    throw new PermissaoNegada();
                }
            } catch (ServletException e) {
                throw new PermissaoNegada();
            }

        } else {
            throw new RecursoNaoEncontradoException();
        }

    }
}
