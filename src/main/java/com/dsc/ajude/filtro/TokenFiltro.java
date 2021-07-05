package com.dsc.ajude.filtro;

import com.dsc.ajude.servico.JwtServico;
import io.jsonwebtoken.*;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenFiltro extends GenericFilterBean {

    public final static int TOKEN_INDEX = 7;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        if (req.getRequestURI().equals("/v1/api/auth/campanhas/campanhasAtivas")) {
            chain.doFilter(request, response);
        }

        String header = req.getHeader("Authorization");

        if(header == null || !header.startsWith("Bearer")){
            throw new ServletException("Token inválido");
        }

        //Extraindo o token
        String token = header.substring(TOKEN_INDEX);

        try {
        	Jwts.parser().setSigningKey(JwtServico.TOKEN_KEY).parseClaimsJws(token).getBody();
        } catch(ExpiredJwtException | MalformedJwtException | PrematureJwtException | UnsupportedJwtException | IllegalArgumentException e){

            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());

            //Não é necessário a requisição ir adiante
            return;
        }

        chain.doFilter(request, response);
    }
}
