package med.voll.api.infra;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {
    /*metodo para lidar com o EntityNotFoundException*/
    /*Faço a anotação @ExceptionHandler e dentro dela passo a classe especifica*/
    /*Agora o spring sabe que qualquer controller do nosos projeto for lançado um EntityNotFoundException
    * usará esse método*/
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404(){
        return ResponseEntity.notFound().build();
    }
    /*Dessa forma o código fica isolado, não preciso ficar fazendo tryCatch dentro dos controllers,
    * os controllers não vão perceber que tem uma classe externa fazendo esse tratamento dos errosg*/
}
