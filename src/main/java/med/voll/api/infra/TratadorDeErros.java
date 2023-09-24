package med.voll.api.infra;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404(){
        return ResponseEntity.notFound().build();
    }

    /*tratando erro 400 -> Indica que o servidor não conseguiu processar  uma requisição por erro
    * de validação  nos dados  enviados pelo cliente*/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex){
        /*Aqui diferente do 404 eu preciso saber quais erros ocorreram, então preciso pegar a exception que ocorreu
        * Pq na exception vou ter acesso a quais campos foram invalidos (não enviados).
        * Na assinatura do método posso receber a exception que foi lançada que tem que ser a mesma assinatura
        * do parâmetro anotado no @ExceptionHandler()*/
        /*Esse OBJETO ex tem uma lista com os campos que deram erro vou jogar em uma variavel
        * erros*/

        var erros = ex.getFieldErrors();//devolve uma lista contendo cada um dos erros
        /*Para mandar o erro armazedno na variavel pro corpo, uso o body().BUILD(). Build eu vou tirar pq já estou mandando um body que ja devolve um OBJ respondeEntity*/
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErrosValidacao::new).toList());/*só dessa forma (return ResponseEntity.badRequest().build();)não retorna um corpo na resposta*/

        /*como quero enviar somente o campo que deu erro e a mensagem, vou ter que fazer um DTO e
        * personalizar o meu var erros = ...*/

        /* return ResponseEntity.badRequest().body(erros); -> Dentro do body eu não vou passar uma lista
        * de erros, vou ter que converter em uma lista de DadosErroValidacao, nesse caso vou usar
        * body(erros.strem.map(DadosErrosValidacao::new)) utilizando recursos do JAVA 8 para converter.
        * Estou dizendo ERROS me da um STREAM e MAPEIA cada OBJETO FIEDL erro para DadosErrosValicao*/
    }

    /*Vou criar esse DTO aqui dentro do tratador de erros, ja que só vou usar aqui.*/

    private record DadosErrosValidacao(String campo, String mensagem){

        /*Como no meu MAP vai dar erro, aqui no meu DTO tenho que dar um construtor que recebe o OBJETO
        * FIELD ERRO*/
        public DadosErrosValidacao(FieldError error){
            this(error.getField(), error.getDefaultMessage());/*Field retorna o campo - DefaultMsg retorna a msg*/
        }

    }

}
