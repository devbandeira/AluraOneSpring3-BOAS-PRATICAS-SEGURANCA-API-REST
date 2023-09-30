package med.voll.api.infra.exception.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import med.voll.api.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    /*Tenho que falar onde o spring vai pegar essa minha secret, como ele vai ler esse atributo?
    * se eu não fizer isso, terei o meu secret Null. essa secret esta no application.properties*/
    @Value("${api.security.token.secret}")
    private String secret;/*Essa senha como é uma config do nosso projeto, pode ficar no application.properties e consigo ler uma
    propriedade que ta declarada dentro do application de uma classe JAVA */

    public String gerarToken (Usuario usuario) {
        System.out.println(secret);/*para ver se ta lendo o default 12345678, qnd eu fazer a a requisição*/
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API Voll.med")
                    .withSubject(usuario.getLogin())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw  new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }


    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of(("-03:00")));

    }
}












