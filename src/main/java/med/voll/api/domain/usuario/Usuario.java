package med.voll.api.domain.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
/*Criando a ENTIDADE USUARIO aqui para poder começar a trabalhar com o Spring Security. Logo vou criar ele
* no meu DB também, utilizando MIGRATIONS V5__create-table-usuario.sql*/
@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String senha;/*Aqui qnd eu salvar, não vou salvar a senha normal. Vou usar um RASH para salvar*/
}
