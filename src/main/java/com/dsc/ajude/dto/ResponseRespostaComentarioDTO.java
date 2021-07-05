package com.dsc.ajude.dto;

import com.dsc.ajude.modelos.Comentario;
import lombok.Data;

import java.util.Objects;

@Data
public class ResponseRespostaComentarioDTO {

    private Long comentarioId;
    private String conteudoDaMensagem;
    private UsuarioDTO dono;
    private ResponseRespostaComentarioDTO respostasUsuarios;
    private Long idCampanha;

    public ResponseRespostaComentarioDTO(Comentario comentario){
        if(Objects.nonNull(comentario)){
            this.comentarioId = comentario.getComentarioId();
            this.conteudoDaMensagem = comentario.getConteudoDaMensagem();
            this.dono = new UsuarioDTO(comentario.getUsuario());
            if(Objects.nonNull(comentario.getRespostasUsuarios())){
                this.respostasUsuarios = new ResponseRespostaComentarioDTO(comentario.getRespostasUsuarios());
            }else{
                this.respostasUsuarios = null;
            }
            this.idCampanha = comentario.getCampanha() == null ? null : comentario.getCampanha().getCampanhaId();
        }

    }

//    public static List<ResponseRespostaComentarioDTO> convertRespostasComentario(List<Comentario> comentario){
//        List<ResponseRespostaComentarioDTO> response = new ArrayList<>();
//
//        for(Comentario c : comentario){
//            response.add(new ResponseRespostaComentarioDTO(c));
//        }
//
//        return response;
//    }
}
