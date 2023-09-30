package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.usuario.DatosAutenticacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    /*  **********AutenticationManager******** */
    @Autowired
    private AuthenticationManager manager;/* N conseguiu fazer injeção -Na hora de carregar nossa AutenticacaoController, ele não encontrou o
     AuthenticationManager, pq ele nãoo sabe */

    /*Dados que vão ser enviados pelo meu FRONT END, recebemos como parametro da fn. Aqui vou fazendo umDTO
    * para receber estes dados, temos que ensinar ele a Inicializar o AuthenticationManager, la na class securityConfigurations
    * */
    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DatosAutenticacao dados){
        /*Depois que recebi meu DTO como parametro, preciso consultar meu DB, disparar o processo de autenticacao
        *O processo de autenticacao ta na classe AutenticacoaService, precisamos chamar o método loadUserByUsername(),
        * pois este método vai utilizar o REPOSITORY e fazer a consulta no BD. Porém no SRPING SECURITY não chamar a classe
        * " AutenticacoaService " diretamente. Temos outra classe do Spring e ela é quem vai chamar por baixo dos panos a
        * AutenticacoaService(). O nome desta classe do Spring é **********AutenticationManager******** vou usar ela dentro do controller*/

        /*converte nosso dto para um UsernamePasswordAuthenticationToken()*/
        var token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha()); /*Temos que criar uma classe que o spring espera, não a nossa classe. Temos o nosso DTO e o Spring tem o DTO dele gerado pelo UsernamePasswordAuthenticatonToke*/
        var authentication = manager.authenticate(token); /* manager.authenticate(token) ele devolve um objeto que representa o usuario autenticado no sistema*/

        return ResponseEntity.ok().build();


    }
}
