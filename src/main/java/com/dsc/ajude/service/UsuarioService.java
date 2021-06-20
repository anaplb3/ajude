package com.dsc.ajude.service;

import com.dsc.ajude.dto.LoginDTO;
import com.dsc.ajude.dto.UsuarioDTO;
import com.dsc.ajude.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import com.dsc.ajude.repository.UsuarioRepository;

import javax.servlet.ServletException;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    public UsuarioService(){
        super();
    }
    

    public UsuarioDTO criarUsuario(Usuario usuario){
        if(this.usuarioRepository.findById(usuario.getEmail()).isEmpty()){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        } else {
            this.usuarioRepository.save(usuario);
            
            return new UsuarioDTO(usuario.getEmail(), usuario.getPrimeiroNome(), usuario.getUltimoNome());
        }

    }

    public boolean validarSenhaDoUsuario(LoginDTO login) {
        Optional<Usuario> optionalUsuario = this.usuarioRepository.findById(login.getEmail());
        return optionalUsuario.isPresent() && optionalUsuario.get().getSenhaUsuario().equals(login.getSenha());
    }

    public boolean usuarioTemPermissao(String authorizationHeader, String email) throws ServletException {
        String subject = jwtService.getSujeitoDoToken(authorizationHeader);
        Optional<Usuario> optUsuario = usuarioRepository.findById(subject);
        return optUsuario.isPresent() && optUsuario.get().getEmail().equals(email);
    }
}
