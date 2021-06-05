package service;

import dto.LoginDTO;
import dto.UsuarioDTO;
import model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import repository.UsuarioRepository;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioService(){
        super();
    }
    

    public UsuarioDTO criarUsuario(Usuario usuario){
        if(this.usuarioRepository.findById(usuario.getEmail()).isEmpty()){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }

        this.usuarioRepository.save(usuario);
        
        return new UsuarioDTO(usuario.getEmail(), usuario.getPrimeiroNome(), usuario.getUltimoNome());
        
    }

    public boolean validarSenhaDoUsuario(LoginDTO login) {
        Optional<Usuario> optionalUsuario = this.usuarioRepository.findById(login.getEmail());
        if(optionalUsuario.isPresent() && optionalUsuario.get().getSenhaUsuario().equals(login.getSenha()))
            return true;
        return false;
    }
}
