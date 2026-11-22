
package model;

public class Enfermeiro extends ProfissionalSaude {

    private String setor;

    public Enfermeiro(String nome, String telefone, String email,
                      boolean status, String dataContratacao,
                      String registroDoProfissional, String setor) {

        super(nome, telefone, email, status, dataContratacao,
              registroDoProfissional);

        this.setor = setor;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    @Override
    public void consultarAgenda() {
        System.out.println("Consultando agenda do enfermeiro: " + getNome());
    }

    @Override
    public void realizarAtendimento() {
        if (!getStatus()) {
            System.out.println("Enfermeiro inativo. Não pode realizar atendimentos.");
            return;
        }

        System.out.println("Enfermeiro " + getNome() + " realizando atendimento.");
    }
}
