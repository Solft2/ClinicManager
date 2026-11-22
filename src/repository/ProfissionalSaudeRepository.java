
package repository;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import model.ProfissionalSaude;
import model.Medico;
import model.Enfermeiro;

public class ProfissionalSaudeRepository {

    private List<ProfissionalSaude> profissionais = new ArrayList<>();

    private final String arquivo = "dados/profissionais.txt";

    public ProfissionalSaudeRepository() {
        carregarArquivo();
    }

    // CADASTRAR
    public boolean cadastrar(ProfissionalSaude profissional) {

        if (buscarPorRegistro(profissional.getRegistroDoProfissional()) != null) {
            return false;
        }

        profissionais.add(profissional);
        salvarArquivo();
        return true;
    }

    // LISTAR
    public List<ProfissionalSaude> listarTodos() {
        return new ArrayList<>(profissionais);
    }

    // BUSCAR
    public ProfissionalSaude buscarPorRegistro(String registro) {

        for (ProfissionalSaude p : profissionais) {
            if (p.getRegistroDoProfissional().equalsIgnoreCase(registro)) {
                return p;
            }
        }

        return null;
    }

    // ATUALIZAR
    public boolean atualizar(String registro, ProfissionalSaude atualizado) {

        ProfissionalSaude existente = buscarPorRegistro(registro);

        if (existente == null) {
            return false;
        }

        // Impede que o novo registro seja igual ao de outro profissional.
        ProfissionalSaude profissionalComMesmoRegistro =
                buscarPorRegistro(atualizado.getRegistroDoProfissional());

        if (profissionalComMesmoRegistro != null &&
                profissionalComMesmoRegistro != existente) {
            return false;
        }

        existente.atualizarDados(
                atualizado.getNome(),
                atualizado.getTelefone(),
                atualizado.getEmail()
        );

        existente.desativar();

        if (atualizado.getStatus()) {
            existente.ativar();
        }

        // Atualiza os dados específicos da classe.
        if (existente instanceof Medico && atualizado instanceof Medico) {

            Medico medicoExistente = (Medico) existente;
            Medico medicoAtualizado = (Medico) atualizado;

            medicoExistente.setEspecialidade(medicoAtualizado.getEspecialidade());

        } else if (existente instanceof Enfermeiro &&
                   atualizado instanceof Enfermeiro) {

            Enfermeiro enfermeiroExistente = (Enfermeiro) existente;
            Enfermeiro enfermeiroAtualizado = (Enfermeiro) atualizado;

            enfermeiroExistente.setSetor(enfermeiroAtualizado.getSetor());

        } else {
            return false;
        }

        salvarArquivo();
        return true;
    }

    // REMOVER
    public boolean remover(String registro) {

        ProfissionalSaude profissional = buscarPorRegistro(registro);

        if (profissional == null) {
            return false;
        }

        profissionais.remove(profissional);
        salvarArquivo();
        return true;
    }

    // SALVAR NO ARQUIVO
    private void salvarArquivo() {

        File pasta = new File("dados");

        if (!pasta.exists()) {
            pasta.mkdirs();
        }

        try (BufferedWriter escritor =
                new BufferedWriter(new FileWriter(arquivo))) {

            for (ProfissionalSaude p : profissionais) {

                String tipo;
                String dadoEspecifico;

                if (p instanceof Medico) {
                    tipo = "MEDICO";
                    dadoEspecifico = ((Medico) p).getEspecialidade();

                } else if (p instanceof Enfermeiro) {
                    tipo = "ENFERMEIRO";
                    dadoEspecifico = ((Enfermeiro) p).getSetor();

                } else {
                    continue;
                }

                String linha = tipo + ";" +
                        p.getNome() + ";" +
                        p.getTelefone() + ";" +
                        p.getEmail() + ";" +
                        p.getStatus() + ";" +
                        p.getDataContratacao() + ";" +
                        p.getRegistroDoProfissional() + ";" +
                        dadoEspecifico;

                escritor.write(linha);
                escritor.newLine();
            }

        } catch (IOException e) {
            System.out.println("Erro ao salvar profissionais: " + e.getMessage());
        }
    }

    // CARREGAR DO ARQUIVO
    private void carregarArquivo() {

        File arquivoDados = new File(arquivo);

        if (!arquivoDados.exists()) {
            return;
        }

        try (BufferedReader leitor =
                new BufferedReader(new FileReader(arquivo))) {

            String linha;

            while ((linha = leitor.readLine()) != null) {

                String[] dados = linha.split(";", -1);

                if (dados.length != 8) {
                    continue;
                }

                String tipo = dados[0];
                String nome = dados[1];
                String telefone = dados[2];
                String email = dados[3];
                boolean status = Boolean.parseBoolean(dados[4]);
                String dataContratacao = dados[5];
                String registro = dados[6];
                String dadoEspecifico = dados[7];

                ProfissionalSaude profissional;

                if (tipo.equals("MEDICO")) {

                    profissional = new Medico(
                            nome, telefone, email, status,
                            dataContratacao, registro, dadoEspecifico
                    );

                } else if (tipo.equals("ENFERMEIRO")) {

                    profissional = new Enfermeiro(
                            nome, telefone, email, status,
                            dataContratacao, registro, dadoEspecifico
                    );

                } else {
                    continue;
                }

                if (buscarPorRegistro(registro) == null) {
                    profissionais.add(profissional);
                }
            }

        } catch (IOException e) {
            System.out.println("Erro ao carregar profissionais: " + e.getMessage());
        }
    }
}
