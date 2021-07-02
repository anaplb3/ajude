package com.dsc.ajude.servico;

import com.dsc.ajude.dto.LoginDTO;
import com.dsc.ajude.dto.UsuarioDTO;
import com.dsc.ajude.excecoes.PermissaoNegadaExcecao;
import com.dsc.ajude.modelos.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import com.dsc.ajude.repositorios.UsuarioRepositorio;

import javax.servlet.ServletException;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServico {

    @Autowired
    private UsuarioRepositorio usuarioRepository;

    @Autowired
    private JwtServico jwtServico;

    public UsuarioServico(){
        super();
    }
    

    public UsuarioDTO criarUsuario(Usuario usuario) {
        if(this.usuarioRepository.findById(usuario.getEmail()).isPresent()){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Email já cadastrado!");
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
        String subject = jwtServico.getSujeitoDoToken(authorizationHeader);
        Optional<Usuario> usuario = usuarioRepository.findById(subject);
        return usuario.isPresent() && usuario.get().getEmail().equals(email);
    }


    public boolean usuarioEstaAutenticado(String authHeader) throws PermissaoNegadaExcecao {
        try {
            String subject = jwtServico.getSujeitoDoToken(authHeader);
            if (usuarioRepository.findById(subject).isPresent()) {
                return true;
            } else {
                throw new PermissaoNegadaExcecao();
            }
        } catch (ServletException e) {
            throw new PermissaoNegadaExcecao();
        }

    }

    public List<Usuario> retornarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> recuperarUsuariId(String usuarioId) {
        return usuarioRepository.findById(usuarioId);
    }
}
