package med.voll.api.infra.exception.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration/*Para o spring saber que tipo de classe é*/
@EnableWebSecurity/*Para indicar ao spring que vamos personalizar configs de segurança.*/
public class SecurityConfigurations {
    /*Primeira config que queremos fazer é config do processdo de autenticação que já não é mais STATEFULL e sim STATELESS
    *  */
    /*Retorno do tipo (SecurityFilterChain) do proprio spring para configurar coisas relacionadas a autenticação e autorização*/
    @Bean/*Sem ela o Spring n vai ler o método -> @Bean serve para expor o retorno do método, ou algum que obj que posso injetar em algum controler ou alguma classe service */
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        /*Esse http quem vai retornar é o Spring e ele tem alguns métodos para utilizarmos*/
        return http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().build();
        /*csrf() -> Desabilita a proteção contra Cross-Site Request Forgery.
        * Desabilitado porque vamos trabalhar com autenticação via token e o token já é uma proteção para esse tipo de ataque
        * */
        /*Cofigura a autenticação para ser STATELESS  ----
        * sessionManagement() -> É para mostrar como vai ser o gerenciamento da sessão.
        * sessionCreationPolicy() -> Politica de criação de sessão, e aqui passamos como parâmetro
        * um OBJ da propria classe   (SessionCreationPolicy.STATELESS)
        * Na sequencia .and().build() -> para ele criar o objeto SecurityFilterChain.
        *
        *
        * OBS.: O csrf() -> vai dar um erro, pois pede o try catch, pois ele lança uma exception.
        * Nesse caso eu não vou tratar, vou lançar isso throws Exception
        *
        * Com esse método (.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().build();)
        * to dizendo que: "O spring quero desabilitar o seu processo de autenticação que você me da um formulário e a autenticação
        * é STATEFULL e quero STATELESS porque aqui é uma APIRest.
        * */


    }
}
/*OBS.: A partir de agora o Spring security não vai ter mais o comportamento padrão de fornecer uma tela de login e bloquear
* todas as requisições. Não mais desenhará uma tela de login e senha, pois quem fará isso será o frontend e ele não vai mais
* bloquear as URLs, nós que temos que configurar como vai ser o processo de autenticação e autorização.*/