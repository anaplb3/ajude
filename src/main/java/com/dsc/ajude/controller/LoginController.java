package com.dsc.ajude.controller;

import com.dsc.ajude.dto.LoginDTO;
import com.dsc.ajude.dto.RespostaLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dsc.ajude.service.JwtService;

import javax.servlet.ServletException;

@RestController
@RequestMapping("/v1/api")
public class LoginController {
    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<RespostaLoginDTO> realizarLogin(@RequestBody LoginDTO login) throws ServletException {
        return new ResponseEntity<>(jwtService.autenticacao(login), HttpStatus.OK);
    }
}
