package med.voll.api.domain.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service/*Spring essa class é um componente, carrega pra mim, que é do tipo serviço. Ele precisa saber que é
uma class de SERVIÇO DE AUTENTICAÇÃO e ele não faz isso automatico. ENTÃO VOU IMPLEMENTAR UMA INTERFACE*/
public class AutenticacaoService implements UserDetailsService {
    /*OBS -> Não vamos injetar essa classe em nenhum controller, o Spring a encontra sozinha e chama na hora
     * da autenticação. Desde que tenha a anotação @Service e implemente UserDetailsService*/

    /*loadUserByUsername é o método que o Spring vai chamar sempre que o user fizer login no nosso project
    *. SEMPRE QUE FIZER LOGIN NO NOSSO SISTEMA O SPRING VAI BUSCAR ESTA CLASSE PQ ELA IMPLEMENTA O
    * UserDetailsService e ele vai chamar o método loadUserByUsername, passando o "username" que foi
    * digitado lá na minha área de login. E neste método precisamos devolver alguma coisa, ou seja, tenho que
    * acessar o banco de dado, então..............................
    * VOU INJETAR AQUI O MEU REPOSITORY COMO UMA DEPENDENCIA AQUI.  */
    @Autowired/* *********************Posso usar essa anotação ou usar o esquema do LOMBOK, gerar um construtor com esse atributo, as das formas são formas de fazer a injeção de dependencia, da na mesma*/
    private UsuarioRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /*Então no nosso Repository, vou precisar ter um método que acha no BD por login e retornar isso.
        * E passo o username para ele.*/
        return repository.findByLogin(username);
    }

}
