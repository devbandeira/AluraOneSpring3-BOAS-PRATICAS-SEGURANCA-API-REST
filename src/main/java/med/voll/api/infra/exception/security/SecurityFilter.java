package med.voll.api.infra.exception.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component/*n é serviço, controller, repositorio, configuração.*/
/*nesse cenário onde a classe não tem um tipo especifico usamos o @Component, é utilizado para que o Spring carregue
* uma classe/componente genérico*/
public class SecurityFilter extends OncePerRequestFilter {
    /*Estamos extendendo a classe OncePerRequestFilter do SPring e ela implementa a interface Filter do jakarta.*/

    /*Quando chegar uma requisição na api o spring sabe desse filter, e ele chama o método doFilterInternal, que vai ser executado
    * apenas uma vez por REQUEST*/
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /*Até aqui ele intercepta, chama este filtro, porém não faz mais nada além disso.
        * Preciso dizer o que ele vai chamar como próximo, para seguir o fluxo da requisição
        * */
        /*Este método recebe 3 parâmetros
        * request -> Pega coisas da requisição
        * response -> Enviar coisas na resposta
        * filterChain -> Representa a cadeira de filtros que existem na aplicação.
        *
        * para eu chamar o próximo, devo usar ele.*/

        filterChain.doFilter(request, response);/*para continuar o fluxo da requisição e ele encaminhar par ao proximo
        filtro o request e o responde*/

    }


}















