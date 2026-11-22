
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import model.Paciente;
import model.ProfissionalSaude;
import model.Medico;
import model.Enfermeiro;

import repository.PacienteRepository;
import repository.ProfissionalSaudeRepository;

public class Main {

    public static void main(String[] args) {

        try {
            Scanner scanner = new Scanner(System.in);

            PacienteRepository pacienteRepository = new PacienteRepository();
            ProfissionalSaudeRepository profissionalRepository =
                    new ProfissionalSaudeRepository();

            int opcao;

            do {
                System.out.println("\n===== CLINIC MANAGER =====");
                System.out.println("1 - Gerenciar pacientes");
                System.out.println("2 - Gerenciar profissionais");
                System.out.println("0 - Sair");
                System.out.print("Escolha uma opção: ");

                opcao = lerInteiro(scanner);

                switch (opcao) {

                    case 1:
                        menuPacientes(scanner, pacienteRepository);
                        break;

                    case 2:
                        menuProfissionais(scanner, profissionalRepository);
                        break;

                    case 0:
                        System.out.println("Encerrando o sistema...");
                        break;

                    default:
                        System.out.println("Opção inválida!");
                }

            } while (opcao != 0);

            scanner.close();

        } catch (IOException e) {
            System.out.println("Erro ao iniciar o sistema: " + e.getMessage());
        }
    }
    private static int lerInteiro(Scanner scanner) {

        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // ==========================================
    // CRUD DE PACIENTES
    // ==========================================

    private static void menuPacientes(
            Scanner scanner,
            PacienteRepository repository) {

        int opcao;

        do {
            System.out.println("\n===== GERENCIAR PACIENTES =====");
            System.out.println("1 - Cadastrar paciente");
            System.out.println("2 - Listar pacientes");
            System.out.println("3 - Buscar paciente");
            System.out.println("4 - Atualizar paciente");
            System.out.println("5 - Remover paciente");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");

            opcao = lerInteiro(scanner);

            switch (opcao) {

                case 1:
                    cadastrarPaciente(scanner, repository);
                    break;

                case 2:
                    listarPacientes(repository);
                    break;

                case 3:
                    buscarPaciente(scanner, repository);
                    break;

                case 4:
                    atualizarPaciente(scanner, repository);
                    break;

                case 5:
                    removerPaciente(scanner, repository);
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }

    // CADASTRAR PACIENTE
    private static void cadastrarPaciente(
            Scanner scanner,
            PacienteRepository repository) {

        System.out.println("\n--- CADASTRAR PACIENTE ---");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Prontuário: ");
        String prontuario = scanner.nextLine();

        System.out.print("Data de nascimento: ");
        String dataNascimento = scanner.nextLine();

        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();

        Paciente novoPaciente = new Paciente(
                nome,
                telefone,
                email,
                prontuario,
                dataNascimento,
                endereco
        );

        try {
    if (repository.cadastrar(novoPaciente)) {
        System.out.println("Paciente cadastrado com sucesso!");
    } else {
        System.out.println("Já existe um paciente com esse prontuário.");
    }

} catch (IOException e) {
    System.out.println("Erro ao cadastrar paciente: " + e.getMessage());
}
    }

    // LISTAR PACIENTES
    private static void listarPacientes(
            PacienteRepository repository) {

        System.out.println("\n--- LISTA DE PACIENTES ---");

        List<Paciente> pacientes = repository.listarTodos();

        if (pacientes.isEmpty()) {
            System.out.println("Nenhum paciente cadastrado.");
            return;
        }

        for (Paciente p : pacientes) {

            System.out.println("\n--------------------");
            System.out.println("Nome: " + p.getNome());
            System.out.println("Prontuário: " + p.getProntuario());
            System.out.println("Telefone: " + p.getTelefone());
            System.out.println("Email: " + p.getEmail());
            System.out.println("Nascimento: " + p.getDataNascimento());
            System.out.println("Endereço: " + p.getEndereco());
        }
    }

    // BUSCAR PACIENTE
    private static void buscarPaciente(
            Scanner scanner,
            PacienteRepository repository) {

        System.out.println("\n--- BUSCAR PACIENTE ---");

        System.out.print("Informe o prontuário: ");
        String prontuario = scanner.nextLine();

        Paciente encontrado = repository.buscarPorProntuario(prontuario);

        if (encontrado == null) {
            System.out.println("Paciente não encontrado.");
            return;
        }

        System.out.println("\nNome: " + encontrado.getNome());
        System.out.println("Prontuário: " + encontrado.getProntuario());
        System.out.println("Telefone: " + encontrado.getTelefone());
        System.out.println("Email: " + encontrado.getEmail());
        System.out.println("Nascimento: " + encontrado.getDataNascimento());
        System.out.println("Endereço: " + encontrado.getEndereco());
    }

    // ATUALIZAR PACIENTE
    private static void atualizarPaciente(
            Scanner scanner,
            PacienteRepository repository) {

        System.out.println("\n--- ATUALIZAR PACIENTE ---");

        System.out.print("Informe o prontuário do paciente: ");
        String prontuario = scanner.nextLine();

        Paciente existente = repository.buscarPorProntuario(prontuario);

        if (existente == null) {
            System.out.println("Paciente não encontrado.");
            return;
        }

        System.out.print("Novo nome: ");
        String nome = scanner.nextLine();

        System.out.print("Novo telefone: ");
        String telefone = scanner.nextLine();

        System.out.print("Novo email: ");
        String email = scanner.nextLine();

        System.out.print("Nova data de nascimento: ");
        String dataNascimento = scanner.nextLine();

        System.out.print("Novo endereço: ");
        String endereco = scanner.nextLine();

        Paciente atualizado = new Paciente(
                nome,
                telefone,
                email,
                prontuario,
                dataNascimento,
                endereco
        );

        try {
          if (repository.atualizar(prontuario, atualizado)) {
            System.out.println("Paciente atualizado com sucesso!");
        } else {
        System.out.println("Não foi possível atualizar o paciente.");
        }

} catch (IOException e) {
    System.out.println("Erro ao atualizar paciente: " + e.getMessage());
}
    }

    // REMOVER PACIENTE
    private static void removerPaciente(
            Scanner scanner,
            PacienteRepository repository) {

        System.out.println("\n--- REMOVER PACIENTE ---");

        System.out.print("Informe o prontuário: ");
        String prontuario = scanner.nextLine();

        System.out.print("Confirma a remoção? (s/n): ");
        String confirmacao = scanner.nextLine();

        if (confirmacao.equalsIgnoreCase("s")) {

      try {
        if (repository.remover(prontuario)) {
            System.out.println("Paciente removido com sucesso!");
        } else {
            System.out.println("Paciente não encontrado.");
        }
    } catch (IOException e) {
        System.out.println("Erro ao remover paciente: " + e.getMessage());
    }

    } else {
    System.out.println("Remoção cancelada.");
    }
            }

    // ==========================================
    // CRUD DE PROFISSIONAIS DE SAÚDE
    // ==========================================

    private static void menuProfissionais(
            Scanner scanner,
            ProfissionalSaudeRepository repository) {

        int opcao;

        do {
            System.out.println("\n===== GERENCIAR PROFISSIONAIS =====");
            System.out.println("1 - Cadastrar profissional");
            System.out.println("2 - Listar profissionais");
            System.out.println("3 - Buscar profissional");
            System.out.println("4 - Atualizar profissional");
            System.out.println("5 - Remover profissional");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");

            opcao = lerInteiro(scanner);

            switch (opcao) {

                case 1:
                    cadastrarProfissional(scanner, repository);
                    break;

                case 2:
                    listarProfissionais(repository);
                    break;

                case 3:
                    buscarProfissional(scanner, repository);
                    break;

                case 4:
                    atualizarProfissional(scanner, repository);
                    break;

                case 5:
                    removerProfissional(scanner, repository);
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }

    // CADASTRAR PROFISSIONAL
    private static void cadastrarProfissional(
            Scanner scanner,
            ProfissionalSaudeRepository repository) {

        System.out.println("\n--- CADASTRAR PROFISSIONAL ---");
        System.out.println("1 - Médico");
        System.out.println("2 - Enfermeiro");
        System.out.print("Escolha o tipo: ");

        int tipo = lerInteiro(scanner);

        if (tipo != 1 && tipo != 2) {
            System.out.println("Tipo inválido!");
            return;
        }

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Data de contratação: ");
        String data = scanner.nextLine();

        System.out.print("Registro profissional: ");
        String registro = scanner.nextLine();

        System.out.print("Profissional está ativo? (s/n): ");
        boolean status = scanner.nextLine().equalsIgnoreCase("s");

        ProfissionalSaude profissional;

        if (tipo == 1) {

            System.out.print("Especialidade: ");
            String especialidade = scanner.nextLine();

            profissional = new Medico(
                    nome, telefone, email, status,
                    data, registro, especialidade
            );

        } else {

            System.out.print("Setor: ");
            String setor = scanner.nextLine();

            profissional = new Enfermeiro(
                    nome, telefone, email, status,
                    data, registro, setor
            );
        }

        if (repository.cadastrar(profissional)) {
            System.out.println("Profissional cadastrado com sucesso!");
        } else {
            System.out.println("Já existe um profissional com esse registro!");
        }
    }

    // LISTAR PROFISSIONAIS
    private static void listarProfissionais(
            ProfissionalSaudeRepository repository) {

        System.out.println("\n--- LISTA DE PROFISSIONAIS ---");

        List<ProfissionalSaude> profissionais = repository.listarTodos();

        if (profissionais.isEmpty()) {
            System.out.println("Nenhum profissional cadastrado.");
            return;
        }

        for (ProfissionalSaude p : profissionais) {

            System.out.println("\n--------------------");
            System.out.println("Nome: " + p.getNome());
            System.out.println("Telefone: " + p.getTelefone());
            System.out.println("Email: " + p.getEmail());
            System.out.println("Registro: " + p.getRegistroDoProfissional());
            System.out.println("Data de contratação: " + p.getDataContratacao());
            System.out.println("Status: " + (p.getStatus() ? "Ativo" : "Inativo"));

            if (p instanceof Medico) {

                System.out.println("Tipo: Médico");
                System.out.println("Especialidade: " +
                        ((Medico) p).getEspecialidade());

            } else if (p instanceof Enfermeiro) {

                System.out.println("Tipo: Enfermeiro");
                System.out.println("Setor: " +
                        ((Enfermeiro) p).getSetor());
            }
        }
    }

    // BUSCAR PROFISSIONAL
    private static void buscarProfissional(
            Scanner scanner,
            ProfissionalSaudeRepository repository) {

        System.out.println("\n--- BUSCAR PROFISSIONAL ---");

        System.out.print("Informe o registro profissional: ");
        String registro = scanner.nextLine();

        ProfissionalSaude p = repository.buscarPorRegistro(registro);

        if (p == null) {
            System.out.println("Profissional não encontrado.");
            return;
        }

        System.out.println("\nNome: " + p.getNome());
        System.out.println("Telefone: " + p.getTelefone());
        System.out.println("Email: " + p.getEmail());
        System.out.println("Registro: " + p.getRegistroDoProfissional());
        System.out.println("Data de contratação: " + p.getDataContratacao());
        System.out.println("Status: " + (p.getStatus() ? "Ativo" : "Inativo"));

        if (p instanceof Medico) {

            System.out.println("Tipo: Médico");
            System.out.println("Especialidade: " +
                    ((Medico) p).getEspecialidade());

        } else if (p instanceof Enfermeiro) {

            System.out.println("Tipo: Enfermeiro");
            System.out.println("Setor: " +
                    ((Enfermeiro) p).getSetor());
        }
    }

    // ATUALIZAR PROFISSIONAL
    private static void atualizarProfissional(
            Scanner scanner,
            ProfissionalSaudeRepository repository) {

        System.out.println("\n--- ATUALIZAR PROFISSIONAL ---");

        System.out.print("Registro do profissional: ");
        String registro = scanner.nextLine();

        ProfissionalSaude existente = repository.buscarPorRegistro(registro);

        if (existente == null) {
            System.out.println("Profissional não encontrado.");
            return;
        }

        System.out.print("Novo nome: ");
        String nome = scanner.nextLine();

        System.out.print("Novo telefone: ");
        String telefone = scanner.nextLine();

        System.out.print("Novo email: ");
        String email = scanner.nextLine();

        System.out.print("Está ativo? (s/n): ");
        boolean status = scanner.nextLine().equalsIgnoreCase("s");

        ProfissionalSaude atualizado;

        if (existente instanceof Medico) {

            System.out.print("Nova especialidade: ");
            String especialidade = scanner.nextLine();

            atualizado = new Medico(
                    nome, telefone, email, status,
                    existente.getDataContratacao(),
                    existente.getRegistroDoProfissional(),
                    especialidade
            );

        } else {

            System.out.print("Novo setor: ");
            String setor = scanner.nextLine();

            atualizado = new Enfermeiro(
                    nome, telefone, email, status,
                    existente.getDataContratacao(),
                    existente.getRegistroDoProfissional(),
                    setor
            );
        }

        if (repository.atualizar(registro, atualizado)) {
            System.out.println("Profissional atualizado com sucesso!");
        } else {
            System.out.println("Não foi possível atualizar o profissional.");
        }
    }

    // REMOVER PROFISSIONAL
    private static void removerProfissional(
            Scanner scanner,
            ProfissionalSaudeRepository repository) {

        System.out.println("\n--- REMOVER PROFISSIONAL ---");

        System.out.print("Registro do profissional: ");
        String registro = scanner.nextLine();

        System.out.print("Confirma a remoção? (s/n): ");
        String confirmacao = scanner.nextLine();

        if (confirmacao.equalsIgnoreCase("s")) {

            if (repository.remover(registro)) {
                System.out.println("Profissional removido com sucesso!");
            } else {
                System.out.println("Profissional não encontrado.");
            }

        } else {
            System.out.println("Remoção cancelada.");
        }
    }
}
