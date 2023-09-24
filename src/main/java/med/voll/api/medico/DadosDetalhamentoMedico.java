package med.voll.api.medico;


import med.voll.api.endereco.Endereco;

/*Esse record não vai receber um OBJ MEDICO e sim as informações do Medico*/
public record DadosDetalhamentoMedico(Long id, String nome, String email, String crm, String telefone, Especialidade especialidade, Endereco endereco) {
/*Como lá no Controller medico, no metodo atualizar to passando médico, vou criar um construtor que recebe
* médico e preencha esses dados vindo do controller.*/

    public DadosDetalhamentoMedico(Medico medico){
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getTelefone(), medico.getEspecialidade(), medico.getEndereco());
    }
}
