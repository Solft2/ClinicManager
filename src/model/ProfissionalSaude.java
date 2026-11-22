package model;

public abstract class ProfissionalSaude extends Pessoa {

    private boolean status;
    private String dataContratacao;
    private String registroDoProfissional;

    public ProfissionalSaude(String nome, String telefone, String email,
                             boolean status, String dataContratacao,
                             String registroDoProfissional) {

        super(nome, telefone, email);

        this.status = status;
        this.dataContratacao = dataContratacao;
        this.registroDoProfissional = registroDoProfissional;
    }

    public boolean getStatus() {
        return status;
    }

    public String getDataContratacao() {
        return dataContratacao;
    }

    public String getRegistroDoProfissional() {
        return registroDoProfissional;
    }

    public void ativar() {
        this.status = true;
    }

    public void desativar() {
        this.status = false;
    }

    public abstract void consultarAgenda();

    public abstract void realizarAtendimento();
}