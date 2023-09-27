package med.voll.api.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

/*Após criar nosso repository, classe que faz os acessos/manipulações na nossa classe
* usuario. Temos que criar algumas classes para fazer as configs do security*/
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByLogin(String login);/*Método que vai fazer a consulta do user no DB. Que vai ser usado
    na nossa class de servico AutenticacaoService*/
}
