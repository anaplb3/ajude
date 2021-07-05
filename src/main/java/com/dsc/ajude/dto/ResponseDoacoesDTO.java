package com.dsc.ajude.dto;

import com.dsc.ajude.modelos.Campanha;
import com.dsc.ajude.modelos.Doacao;
import com.dsc.ajude.modelos.Like;
import com.dsc.ajude.modelos.Usuario;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseDoacoesDTO {

    private Long idDoacao;
    private BigDecimal quantiaDoada;
    private LocalDate dataDaDoacao;
    private UsuarioDTO dono;
    private Long idCampanha;

    public ResponseDoacoesDTO(Doacao doacao){
        this.idDoacao = doacao.getDoacaoId();
        this.quantiaDoada = doacao.getQuantiaDoada();
        this.dataDaDoacao = doacao.getDataDaDoacao();
        this.dono = new UsuarioDTO(doacao.getUsuario());
        this.idCampanha = doacao.getCampanha().getCampanhaId();
    }

    public static List<ResponseDoacoesDTO> convertDoacoes(List<Doacao> doacoes){
        List<ResponseDoacoesDTO> response = new ArrayList<>();

        for(Doacao d : doacoes){
            response.add(new ResponseDoacoesDTO(d));
        }

        return response;
    }
}
