package com.dsc.ajude.servico;

import com.dsc.ajude.dto.CampanhaDTO;
import com.dsc.ajude.dto.CampanhaSubstringDTO;
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
import java.util.*;

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
            usuarioServico.usuarioTemPermissao(authHeader, donoDaCampanha.getEmail());
            if (campanha.getDeadline().isBefore(LocalDateTime.now().toLocalDate())) {
                throw new DataInvalidaExcecao();
            }
            campanhaRepositorio.save(campanha.campanhaDTOParaCampanha(donoDaCampanha, Status.ATIVA));
            return campanha;
        } catch (ServletException e) {
            throw new PermissaoNegadaExcecao();
        }

    }

    public Campanha encerraCampanha(long id, String authHeader) throws RecursoNaoEncontradoExcecao, PermissaoNegadaExcecao {
        Optional<Campanha> campanha = campanhaRepositorio.findById(id);
        if (campanha.isPresent()) {
            try {
                usuarioServico.usuarioTemPermissao(authHeader, campanha.get().getDono().getEmail());
                campanha.get().setStatus(Status.ENCERRADA);
                campanhaRepositorio.save(campanha.get());
                return campanha.get();
            } catch (ServletException e) {
                throw new PermissaoNegadaExcecao();
            }
        } else {
            throw new RecursoNaoEncontradoExcecao();
        }
    }

    public List<Campanha> getCampanhaPorSubstring(CampanhaSubstringDTO campanhaSubstringDTO) {
        return campanhaRepositorio.findByNomeLike(campanhaSubstringDTO.getSubstring().toLowerCase(), campanhaSubstringDTO.isTodosOsResultados() ?
                Arrays.asList(Status.ATIVA, Status.ENCERRADA, Status.VENCIDA, Status.CONCLUIDA) : Collections.singletonList(Status.ATIVA));
    }

    public Campanha getCampanhaPorId(long id, String authHeader) throws RecursoNaoEncontradoExcecao, PermissaoNegadaExcecao {
        Optional<Campanha> campanha = campanhaRepositorio.findById(id);
        if (campanha.isPresent()) {
            if (usuarioServico.usuarioEstaAutenticado(authHeader)) {
                return campanha.get();
            }
        } else {
            throw new RecursoNaoEncontradoExcecao();
        }
        return null;
    }
}
