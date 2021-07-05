package com.dsc.ajude.servico;

import com.dsc.ajude.dto.BuscarCampanhasAtivasDTO;
import com.dsc.ajude.dto.CampanhaDTO;
import com.dsc.ajude.dto.CampanhaSubstringDTO;
import com.dsc.ajude.dto.ResponseCampanhaDTO;
import com.dsc.ajude.excecoes.DataInvalidaExcecao;
import com.dsc.ajude.excecoes.PermissaoNegadaExcecao;
import com.dsc.ajude.excecoes.RecursoNaoEncontradoExcecao;
import com.dsc.ajude.modelos.Campanha;
import com.dsc.ajude.modelos.OrdenarCampanhasAtivas;
import com.dsc.ajude.modelos.Status;
import com.dsc.ajude.modelos.Usuario;
import com.dsc.ajude.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.dsc.ajude.repositorios.CampanhaRepositorio;

import javax.servlet.ServletException;
import javax.transaction.Transactional;
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

    @Transactional
    public Long addCampanha(CampanhaDTO campanha, String authHeader) throws DataInvalidaExcecao, PermissaoNegadaExcecao {
        try {
            Usuario donoDaCampanha = usuarioRepositorio.getById(campanha.getEmailUsuario());
            usuarioServico.usuarioTemPermissao(authHeader, donoDaCampanha.getEmail());
            if (campanha.getDeadline().isBefore(LocalDateTime.now().toLocalDate())) {
                throw new DataInvalidaExcecao();
            }
            return campanhaRepositorio.save(campanha.campanhaDTOParaCampanha(donoDaCampanha, Status.ATIVA)).getCampanhaId();
        } catch (ServletException e) {
            throw new PermissaoNegadaExcecao();
        }

    }

    @Transactional
    public ResponseCampanhaDTO encerraCampanha(long id, String authHeader) throws RecursoNaoEncontradoExcecao, PermissaoNegadaExcecao {
        Campanha campanha = campanhaRepositorio.findByCampanhaIdAndStatus(id, Status.ATIVA);
        if (campanha != null) {
            try {
                usuarioServico.usuarioTemPermissao(authHeader, campanha.getDono().getEmail());
                campanha.setStatus(Status.ENCERRADA);
                campanhaRepositorio.save(campanha);
                return new ResponseCampanhaDTO(campanha);
            } catch (ServletException e) {
                throw new PermissaoNegadaExcecao();
            }
        } else {
            throw new RecursoNaoEncontradoExcecao();
        }
    }

    public List<Campanha> getCampanhaPorSubstring(CampanhaSubstringDTO campanhaSubstringDTO) {
        return campanhaRepositorio.findByNomeLike(campanhaSubstringDTO.getSubstring().toLowerCase(), campanhaSubstringDTO.getTodosOsResultados() ?
                Arrays.asList(Status.ATIVA, Status.ENCERRADA, Status.VENCIDA, Status.CONCLUIDA) : Collections.singletonList(Status.ATIVA));
    }

    public Campanha getCampanhaPorId(long id, String authHeader) throws RecursoNaoEncontradoExcecao, PermissaoNegadaExcecao {
        Campanha campanha = campanhaRepositorio.findByCampanhaIdAndStatus(id, Status.ATIVA);
        if (campanha != null) {
            if (usuarioServico.usuarioEstaAutenticado(authHeader)) {
                return campanha;
            }
        } else {
            throw new RecursoNaoEncontradoExcecao();
        }
        return null;
    }

    @Transactional
    public List<Campanha> campanhasAtivas(BuscarCampanhasAtivasDTO params, String authHeader) throws DataInvalidaExcecao, PermissaoNegadaExcecao {
        List<Campanha> campanhas = new ArrayList<>();

        if(Objects.isNull(params.getOrdenadoPor()) || params.getOrdenadoPor().equals(OrdenarCampanhasAtivas.VALOR_RESTANTE)){
            campanhas = this.campanhaRepositorio.findAllByMetaRestante(Status.ATIVA);

        }else if(params.getOrdenadoPor().equals(OrdenarCampanhasAtivas.LIKES)){
            campanhas = this.campanhaRepositorio.findAllByLikes(Status.ATIVA);

        }else if(params.getOrdenadoPor().equals(OrdenarCampanhasAtivas.DATA_VENCIMENTO)){
            campanhas = this.campanhaRepositorio.findAllByDeadLine(Status.ATIVA);
        }

        return campanhas;
    }



}
