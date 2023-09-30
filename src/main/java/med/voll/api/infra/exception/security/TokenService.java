package med.voll.api.infra.exception.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import med.voll.api.domain.usuario.Usuario;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    public String gerarToken (Usuario usuario) {
        try {
            var algoritmo = Algorithm.HMAC256("12345678");
            return JWT.create()
                    .withIssuer("API Voll.med")/*quem ta gerando esse TOKEN*/
                    .withSubject(usuario.getLogin())/*Como é STATELESS, estamos dizendo quem é a pessoa relacionada com esse TOKEN*/
                    .withClaim("id", usuario.getId())/*Passando mais informações se eu quiser. Posso chamar varias vezes como se fosse KEY: VALUE*/
                    .withExpiresAt(dataExpiracao())/*criando método e passando um tempo para expiração do token*/
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw  new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    /*devolvendo um Instant que é do JAVA 8 api de datas*/
    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of(("-03:00")));
        /*
        LocalDateTime.now() -> Pegando data e hora atual
        * plusHours(2) -> Passando 2 hroas como parametro
        * toInstant() -> converter para instant
        * ZoneOffset.of(("-03:00") -> Passando um obj para passar o fuso horário local
        * */
    }
}


/*Qnd geramos um TOKEN é ideal que ele tenha uma data de validade*/












