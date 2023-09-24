package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder) {
        /*Aqui vai ser um pouco dif pq o cadastrar retorna o codigo 201 "created" e ele tem um tratamento diferenciado*/
        /*Devolve no corpo da resposta os dados do novo recurso/registro criado e tbm tem que devoler o
        * cabeçalho do protocolo HTTP (location) que é para mostrar o endereço de onde esse dado acabou de ser
        * cadastrado e possa ser localizado*/

        var medico = new Medico(dados);
        repository.save(medico);

        /*A URI tem que ser o endereço da nossa API. Ja temos uma classe no spring que encapsula o endereço
        * da URI sozinho. No método cadastrar vou declarar mais um parametro UriComponentsBuilder*/
        /*.path() para passar o complemento da url, pois nesse caso só vai criar o localhost:8080*/
        /*uso o buildAndExpand() e passo a URI que acabou de ser criado, o ID está na repository.save(new Medico(dados));
        * mas preciso conseguir pegar o new Medico que criei e passei, então vou joar dentro de uma variavel*/
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();/*toUri() para criar o objeto URI*/
        /*Spring vai criar o cabeçalho LOCATION automaticamente baseado na URI que eu pasasr como parametro
        * em seguida passo o body() para dizer qual a informação que quero devolver no corpo da resposta
        * e cria o obj respondeentity*/
        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));/*Vou usar o mesmo DTO que usei no atualizar*/
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
        return ResponseEntity.ok(page);/*Vai retornar o codigo 200 e junto vai vir o objeto de paginação*/
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
        /*Aqui n posso devolver 204*/
        /*Poderia devolver entidade médico, mas não devo devolver entidade JPA somente DTO*/
        /*Se eu devolvo DadosAtualizacaoMedico, ela está incompleta. Representa os dados da atualização
        * de médico, então vou criar outro DTO para representar os dados do médico ATT.*/
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    /*Para mudar o tipo de retorno. Usamos uma classe do spring ResponseEntity*/
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        medico.excluir();
        /*Agora que temos o retorno ResponseEntity, precisamos por um return*/
        /*Dará erro quando eu por o noContent() pq ele não retorna um Responseentity, então uso o build
        * noContent cria o objeto e ai sim o build constroi o obj responseentity*/
        return ResponseEntity.noContent().build();
    }


}
