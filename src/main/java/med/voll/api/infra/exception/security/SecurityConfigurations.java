package med.voll.api.infra.exception.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.concurrent.ExecutionException;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        return http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().build();
    }

    /*Agora esta classe tem o método authenticationManager que sabe criar o objeto AuthenticationManager. Vai ficar dando erro
    * pois este método pode lançar uma exception*/
    @Bean /*Como to ensinando para o Spring como se injeta esse objeto, tem que ter anotação @Bean*/
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /*PasswordEncoder -> Classe que representa o algotirimo de hash de senha.*/
    /*ensinando ao spring que as senhas armazenadas são em Bcrypt*/
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();/*classe do proprio spring que conseguimos instanciar como uma class normal*/
    }
}

/* O @BEAN SERVE PARA EXPORTAR UMA CLASSE PARA O SPRING, FAZENDO COM QUE ELE CONSIGA CARREGÁ-LA E REALIZE A SUA INJEÇÃO
DE DEPENDÊNCIA EM OUTRAS CLASSES*/