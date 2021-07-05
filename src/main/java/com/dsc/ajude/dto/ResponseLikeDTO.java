package com.dsc.ajude.dto;

import com.dsc.ajude.modelos.Like;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseLikeDTO {

    private Long id;
    private UsuarioDTO dono;
    private Long idCampanha;

    public ResponseLikeDTO(Like like){
        this.id = like.getId();
        this.dono = new UsuarioDTO(like.getUsuario());
        this.idCampanha = like.getCampanha().getCampanhaId();
    }

    public static List<ResponseLikeDTO> convertLikes(List<Like> likes){
        List<ResponseLikeDTO> response = new ArrayList<>();

        for(Like l : likes){
           response.add(new ResponseLikeDTO(l));
        }

        return response;
    }

}
