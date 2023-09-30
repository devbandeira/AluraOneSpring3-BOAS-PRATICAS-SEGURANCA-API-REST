package med.voll.api.infra.exception.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService; /*TokenService -> Do nosso projeto*/

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var tokenJWT = recuperarToken(request); /*recuperei o token do cabeçalho*/
        /*Validando o TOKEN e recuperando um SUBJECT que tem Login relacionado a esse token*/
        var subject = tokenService.getsubject(tokenJWT);

        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader == null){
            throw  new RuntimeException("Token JWT não enviado no cabeçado Authorization");
        }
        return authorizationHeader.replace("Bearer ", "");
    }

}















