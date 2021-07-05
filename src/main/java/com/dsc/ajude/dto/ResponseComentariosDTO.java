package com.dsc.ajude.dto;

import com.dsc.ajude.modelos.Comentario;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseComentariosDTO {
    private Long comentarioId;
    private String conteudoDaMensagem;
    private UsuarioDTO dono;
    private ResponseRespostaComentarioDTO respostasUsuarios;
    private Long idCampanha;

    public ResponseComentariosDTO(Comentario comentario){
        this.comentarioId = comentario.getComentarioId();
        this.conteudoDaMensagem = comentario.getConteudoDaMensagem();
        this.dono = new UsuarioDTO(comentario.getUsuario());
        this.respostasUsuarios = new ResponseRespostaComentarioDTO(comentario.getRespostasUsuarios());
        this.idCampanha = comentario.getCampanha().getCampanhaId();
    }

    public static List<ResponseComentariosDTO> convertComentarios(List<Comentario> comentario){
        List<ResponseComentariosDTO> response = new ArrayList<>();

        for(Comentario c : comentario){
            response.add(new ResponseComentariosDTO(c));
        }

        return response;
    }
}
