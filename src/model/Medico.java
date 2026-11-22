package model;

public class Medico extends ProfissionalSaude {

    private String especialidade;

    public Medico(String nome, String telefone, String email,
                  boolean status, String dataContratacao,
                  String registroDoProfissional, String especialidade) {

        super(nome, telefone, email, status, dataContratacao,
              registroDoProfissional);

        this.especialidade = especialidade;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    @Override
    public void consultarAgenda() {
        System.out.println("Consultando agenda do médico: " + getNome());
    }

    @Override
    public void realizarAtendimento() {
        if (!getStatus()) {
            System.out.println("Médico inativo. Não pode realizar atendimentos.");
            return;
        }

        System.out.println("Médico " + getNome() + " realizando atendimento.");
    }
}