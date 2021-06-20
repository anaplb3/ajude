package com.dsc.ajude.controladores;

import com.dsc.ajude.dto.LoginDTO;
import com.dsc.ajude.dto.RespostaLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dsc.ajude.servico.JwtServico;

import javax.servlet.ServletException;

@RestController
@RequestMapping("/v1/api/login")
public class LoginControlador {
    @Autowired
    private JwtServico jwtServico;

    @PostMapping("")
    public ResponseEntity<RespostaLoginDTO> realizarLogin(@RequestBody LoginDTO login) throws ServletException {
        return new ResponseEntity<>(jwtServico.autenticacao(login), HttpStatus.OK);
    }
}
