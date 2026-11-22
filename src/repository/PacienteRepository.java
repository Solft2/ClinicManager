
package repository;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import model.Paciente;

public class PacienteRepository {

    private List<Paciente> pacientes = new ArrayList<>();

    private final Path arquivo = Path.of("dados", "pacientes.txt");

    // Construtor
    public PacienteRepository() throws IOException {
        Files.createDirectories(arquivo.getParent());

        if (!Files.exists(arquivo)) {
            Files.createFile(arquivo);
        }

        carregarArquivo();
    }

    // CREATE - Cadastrar paciente
    public boolean cadastrar(Paciente paciente) throws IOException {

        if (buscarPorProntuario(paciente.getProntuario()) != null) {
            return false;
        }

        pacientes.add(paciente);

        try {
            salvarArquivo();
        } catch (IOException e) {
            pacientes.remove(paciente);
            throw e;
        }

        return true;
    }

    // READ - Listar todos os pacientes
    public List<Paciente> listarTodos() {
        return new ArrayList<>(pacientes);
    }

    // READ - Buscar paciente pelo prontuario
    public Paciente buscarPorProntuario(String prontuario) {

        for (Paciente paciente : pacientes) {

            if (paciente.getProntuario().equals(prontuario)) {
                return paciente;
            }
        }

        return null;
    }

    // UPDATE - Atualizar paciente
    public boolean atualizar(String prontuario, Paciente novosDados)
            throws IOException {

        Paciente paciente = buscarPorProntuario(prontuario);

        if (paciente == null) {
            return false;
        }

        Paciente dadosAntigos = new Paciente(
            paciente.getNome(),
            paciente.getTelefone(),
            paciente.getEmail(),
            paciente.getProntuario(),
            paciente.getDataNascimento(),
            paciente.getEndereco()
        );

        paciente.atualizarDadosPaciente(
            novosDados.getNome(),
            novosDados.getTelefone(),
            novosDados.getEmail(),
            novosDados.getDataNascimento(),
            novosDados.getEndereco()
        );

        try {
            salvarArquivo();
        } catch (IOException e) {

            paciente.atualizarDadosPaciente(
                dadosAntigos.getNome(),
                dadosAntigos.getTelefone(),
                dadosAntigos.getEmail(),
                dadosAntigos.getDataNascimento(),
                dadosAntigos.getEndereco()
            );

            throw e;
        }

        return true;
    }

    // DELETE - Remover paciente
    public boolean remover(String prontuario) throws IOException {

        Paciente paciente = buscarPorProntuario(prontuario);

        if (paciente == null) {
            return false;
        }

        int indice = pacientes.indexOf(paciente);

        pacientes.remove(paciente);

        try {
            salvarArquivo();
        } catch (IOException e) {
            pacientes.add(indice, paciente);
            throw e;
        }

        return true;
    }

    // Salvar todos os pacientes no arquivo
    private void salvarArquivo() throws IOException {

        try (var escritor = Files.newBufferedWriter(
                arquivo,
                StandardOpenOption.TRUNCATE_EXISTING)) {

            for (Paciente paciente : pacientes) {

                String linha = paciente.getNome() + ";" +
                        paciente.getProntuario() + ";" +
                        paciente.getTelefone() + ";" +
                        paciente.getEmail() + ";" +
                        paciente.getDataNascimento() + ";" +
                        paciente.getEndereco();

                escritor.write(linha);
                escritor.newLine();
            }
        }
    }

    // Carregar pacientes do arquivo
    private void carregarArquivo() throws IOException {

        try (var leitor = Files.newBufferedReader(arquivo)) {

            String linha;
            int numeroLinha = 0;

            while ((linha = leitor.readLine()) != null) {

                numeroLinha++;

                String[] dados = linha.split(";", -1);

                if (dados.length != 6) {
                    throw new IOException(
                        "Dados inválidos na linha " + numeroLinha
                    );
                }

                Paciente paciente = new Paciente(
                    dados[0],
                    dados[2],
                    dados[3],
                    dados[1],
                    dados[4],
                    dados[5]
                );

                pacientes.add(paciente);
            }
        }
    }
}
