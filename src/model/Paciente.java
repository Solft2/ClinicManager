
package model;

public class Paciente extends Pessoa {

    private String prontuario;
    private String dataNascimento;
    private String endereco;

    public Paciente(String nome, String telefone, String email,
                    String prontuario, String dataNascimento, String endereco) {

        super(nome, telefone, email);

        this.prontuario = prontuario;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
    }

    public String getProntuario() {
        return prontuario;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public String getEndereco() {
        return endereco;
    }

    public void atualizarDadosPaciente(String nome, String telefone,
                                       String email, String dataNascimento,
                                       String endereco) {

        super.atualizarDados(nome, telefone, email);

        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
    }
}
