package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.usuario.DatosAutenticacao;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.infra.exception.security.TokenService;
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

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;/*tomar cuidado para não importar a propria classe do spring TokenService*/

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DatosAutenticacao dados){

        var token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication = manager.authenticate(token); /*esse objt representa o usuario que tem a autenticação*/

        return ResponseEntity.ok(tokenService.gerarToken((Usuario) authentication.getPrincipal()));/*authentication.getPrincipal() -> Para pegar o user atual logado
        vai dar erro porque esse authentication.getPrincipal() devolve um OBJECT e então fazemos um CASTING*/


    }
}
