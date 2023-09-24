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

@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {
        /*Aqui vai ser um pouco dif pq o cadastrar retorna o codigo 201 e ele tem um tratamento diferenciado*/
        repository.save(new Medico(dados));
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
