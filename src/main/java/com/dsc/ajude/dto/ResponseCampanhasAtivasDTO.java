package com.dsc.ajude.dto;

import com.dsc.ajude.modelos.Campanha;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseCampanhasAtivasDTO {

    private Long idCampanha;
    private String nomeCurto;
    private BigDecimal valorRestante;
    private LocalDate deadline;
    private Integer quantidadeLikes;


    public ResponseCampanhasAtivasDTO(Campanha campanha){
        this.idCampanha = campanha.getCampanhaId();
        this.nomeCurto = campanha.getNome();
        this.valorRestante = campanha.getValorRestanteMeta();
        this.deadline = campanha.getDeadline();
        this.quantidadeLikes = campanha.getQuantidadeLikes();
    }

    public static List<ResponseCampanhasAtivasDTO> convertAll(List<Campanha> campanhas){
        List<ResponseCampanhasAtivasDTO> response = new ArrayList<>();
        for(Campanha c : campanhas){
            response.add(new ResponseCampanhasAtivasDTO(c));
        }
        return response;

    }


}
