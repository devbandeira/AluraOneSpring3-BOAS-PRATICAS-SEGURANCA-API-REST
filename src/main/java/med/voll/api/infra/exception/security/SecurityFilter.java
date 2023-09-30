package med.voll.api.infra.exception.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        /*logica para recuperar o TOKEN*/
        /*O envio de um token é realizado em um cabeçalho HTTP, chamado Authorization, enviado pelo front end*/

        var tokenJWT = recuperarToken(request); /*passo o request pq o cabeçalho ta associado a ele*/

        System.out.println(tokenJWT);/*para ver se vamos conseguir recuperar o token. R.: Bearer é o prefixo padrão utilizado
        para token JWT, posso mudar o prefixo no insomminia*/

        /* ********************* */

        filterChain.doFilter(request, response);
    }

    /*método criado somente para recuperar o token*/
    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization"); /*Authorization -> String paga pegar o Authorization do cabeçalho*/
        if(authorizationHeader == null){
            throw  new RuntimeException("Token JWT não enviado no cabeçado Authorization");
        }
        return authorizationHeader.replace("Bearer ", "");/*Tirando o prefixo bearer do cabeçalho*/
    }

}















