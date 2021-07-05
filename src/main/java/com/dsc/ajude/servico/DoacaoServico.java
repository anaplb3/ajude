package com.dsc.ajude.servico;

import com.dsc.ajude.dto.DoacaoDTO;
import com.dsc.ajude.excecoes.PermissaoNegadaExcecao;
import com.dsc.ajude.excecoes.RecursoNaoEncontradoExcecao;
import com.dsc.ajude.modelos.Campanha;
import com.dsc.ajude.modelos.Doacao;
import com.dsc.ajude.modelos.Status;
import com.dsc.ajude.modelos.Usuario;
import com.dsc.ajude.repositorios.CampanhaRepositorio;
import com.dsc.ajude.repositorios.DoacaoRepositorio;
import com.dsc.ajude.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.ServletException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class DoacaoServico {

    @Autowired
    private DoacaoRepositorio doacaoRepositorio;

    @Autowired
    private CampanhaRepositorio campanhaRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public BigDecimal adicionarDoacao(DoacaoDTO doacaoDTO) throws PermissaoNegadaExcecao, RecursoNaoEncontradoExcecao, ServletException {
        Campanha campanha = campanhaRepositorio.findByCampanhaIdAndStatus(doacaoDTO.getIdCampanha(), Status.ATIVA);
        Usuario doador = usuarioRepositorio.getById(doacaoDTO.getEmailUsuario());

        if (campanha == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }


        Doacao doacaoRealizada = new Doacao();
        doacaoRealizada.setDataDaDoacao(LocalDate.now());
        doacaoRealizada.setCampanha(campanha);
        doacaoRealizada.setUsuario(doador);
        doacaoRealizada.setQuantiaDoada(doacaoDTO.getValorDoado());

        List<Doacao> doacoes = campanha.getDoacoes();
        doacoes.add(this.doacaoRepositorio.save(doacaoRealizada));
        campanha.setDoacoes(doacoes);

        BigDecimal valorRestante = campanha.getValorRestanteMeta().subtract(doacaoDTO.getValorDoado());

        campanha.setValorRestanteMeta(valorRestante);

        this.campanhaRepositorio.save(campanha);
        return valorRestante;

    }
}
