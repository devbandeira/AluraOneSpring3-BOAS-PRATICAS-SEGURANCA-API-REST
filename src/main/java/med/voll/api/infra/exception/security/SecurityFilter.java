package med.voll.api.infra.exception.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var tokenJWT = recuperarToken(request);

        if (tokenJWT != null) {
            var subject = tokenService.getsubject(tokenJWT);
            /*dizendo pro spring que a pessoa ta autenticada, porque eu ja checkei o token e considere que ela talogada*/
            /*carregando o user completo*/
            var usuario = repository.findByLogin(subject);/*passei o subject pq guardei o login da pessoa*/

            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);/*Aqui ele recebe como parametro um objeto
            authentication. força a autenticação*/
        }

        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        /*mudando o IF se não ele vai ver que o cabeçalho Authorization é Null e dará erro 403,
        * por isso foi colocado return null se ele for null*/
        if(authorizationHeader != null){
            return authorizationHeader.replace("Bearer ", "");
        }

        return null;

    }

}





/*Lembrando que estamos criando um API Rest e por padrão API Rest são sem estado, não guardando estado do user que está
* logado, então usando o JWT neste caso para autenticar*/









