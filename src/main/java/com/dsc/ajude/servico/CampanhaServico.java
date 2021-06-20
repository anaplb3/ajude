package com.dsc.ajude.servico;

import com.dsc.ajude.dto.CampanhaDTO;
import com.dsc.ajude.excecoes.DataInvalidaExcecao;
import com.dsc.ajude.excecoes.PermissaoNegadaExcecao;
import com.dsc.ajude.excecoes.RecursoNaoEncontradoExcecao;
import com.dsc.ajude.modelos.Campanha;
import com.dsc.ajude.modelos.Status;
import com.dsc.ajude.modelos.Usuario;
import com.dsc.ajude.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dsc.ajude.repositorios.CampanhaRepositorio;

import javax.servlet.ServletException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CampanhaServico {

    @Autowired
    private CampanhaRepositorio campanhaRepositorio;
    @Autowired
    private UsuarioServico usuarioServico;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public CampanhaDTO addCampanha(CampanhaDTO campanha, String authHeader) throws DataInvalidaExcecao, PermissaoNegadaExcecao {
        try {
            Usuario donoDaCampanha = usuarioRepositorio.getById(campanha.getEmailUsuario());
           if (usuarioServico.usuarioTemPermissao(authHeader, donoDaCampanha.getEmail())) {
                if (campanha.getDeadline().isBefore(LocalDateTime.now().toLocalDate())) {
                    throw new DataInvalidaExcecao();
                }
                campanhaRepositorio.save(campanha.campanhaDTOParaCampanha(donoDaCampanha, Status.ATIVA));
                return campanha;
            } else {
               throw new PermissaoNegadaExcecao();
           }
        } catch (ServletException e) {
            throw new PermissaoNegadaExcecao();
        }

    }

    public Campanha encerraCampanha(long id, String authHeader) throws RecursoNaoEncontradoExcecao, PermissaoNegadaExcecao {
        Optional<Campanha> campanha = campanhaRepositorio.findById(id);
        if (campanha.isPresent()) {
            try {
                if (usuarioServico.usuarioTemPermissao(authHeader, campanha.get().getDono().getEmail())) {
                    campanha.get().setStatus(Status.ENCERRADA);
                    campanhaRepositorio.save(campanha.get());
                    return campanha.get();
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

    public List<Campanha> getCampanhaPorSubstring(String substring, boolean todosOsResultados) {
        return campanhaRepositorio.findByTitleLike(substring, todosOsResultados ? null : Status.ATIVA.ordinal());
    }

    public Campanha getCampanhaPorId(long id, String authHeader) throws RecursoNaoEncontradoExcecao, PermissaoNegadaExcecao {
        Optional<Campanha> campanha = campanhaRepositorio.findById(id);
        if (campanha.isPresent()) {
            try {
                if (usuarioServico.usuarioTemPermissao(authHeader, campanha.get().getDono().getEmail())) {
                    return campanha.get();
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
