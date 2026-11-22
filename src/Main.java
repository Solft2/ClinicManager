
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import model.Paciente;
import repository.PacienteRepository;

public class Main {

    public static void main(String[] args) {

        try {
            Scanner scanner = new Scanner(System.in);
            PacienteRepository repository = new PacienteRepository();

            int opcao;

            do {
                System.out.println("\n===== CLINIC MANAGER =====");
                System.out.println("1 - Cadastrar paciente");
                System.out.println("2 - Listar pacientes");
                System.out.println("3 - Buscar paciente");
                System.out.println("4 - Atualizar paciente");
                System.out.println("5 - Remover paciente");
                System.out.println("0 - Sair");
                System.out.print("Escolha uma opção: ");

                try {
                    opcao = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    opcao = -1;
                }

                switch (opcao) {

                    case 1:
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
                            nome, telefone, email,
                            prontuario, dataNascimento, endereco
                        );

                        if (repository.cadastrar(novoPaciente)) {
                            System.out.println("Paciente cadastrado com sucesso!");
                        } else {
                            System.out.println("Já existe um paciente com esse prontuário.");
                        }
                        break;

                    case 2:
                        System.out.println("\n--- LISTA DE PACIENTES ---");

                        List<Paciente> pacientes = repository.listarTodos();

                        if (pacientes.isEmpty()) {
                            System.out.println("Nenhum paciente cadastrado.");
                        } else {
                            for (Paciente p : pacientes) {
                                System.out.println("\nNome: " + p.getNome());
                                System.out.println("Prontuário: " + p.getProntuario());
                                System.out.println("Telefone: " + p.getTelefone());
                                System.out.println("Email: " + p.getEmail());
                                System.out.println("Nascimento: " + p.getDataNascimento());
                                System.out.println("Endereço: " + p.getEndereco());
                            }
                        }
                        break;

                    case 3:
                        System.out.println("\n--- BUSCAR PACIENTE ---");

                        System.out.print("Informe o prontuário: ");
                        String prontuarioBusca = scanner.nextLine();

                        Paciente encontrado = repository.buscarPorProntuario(prontuarioBusca);

                        if (encontrado != null) {
                            System.out.println("Nome: " + encontrado.getNome());
                            System.out.println("Prontuário: " + encontrado.getProntuario());
                            System.out.println("Telefone: " + encontrado.getTelefone());
                            System.out.println("Email: " + encontrado.getEmail());
                            System.out.println("Nascimento: " + encontrado.getDataNascimento());
                            System.out.println("Endereço: " + encontrado.getEndereco());
                        } else {
                            System.out.println("Paciente não encontrado.");
                        }
                        break;

                    case 4:
                        System.out.println("\n--- ATUALIZAR PACIENTE ---");

                        System.out.print("Informe o prontuário do paciente: ");
                        String prontuarioAtualizar = scanner.nextLine();

                        System.out.print("Novo nome: ");
                        String novoNome = scanner.nextLine();

                        System.out.print("Novo telefone: ");
                        String novoTelefone = scanner.nextLine();

                        System.out.print("Novo email: ");
                        String novoEmail = scanner.nextLine();

                        System.out.print("Nova data de nascimento: ");
                        String novaDataNascimento = scanner.nextLine();

                        System.out.print("Novo endereço: ");
                        String novoEndereco = scanner.nextLine();

                        Paciente pacienteAtualizado = new Paciente(
                        novoNome,
                        novoTelefone,
                        novoEmail,
                        prontuarioAtualizar,
                        novaDataNascimento,
                        novoEndereco
                       );

                        if (repository.atualizar(prontuarioAtualizar, pacienteAtualizado)) {
                            System.out.println("Paciente atualizado com sucesso!");
                        } else {
                            System.out.println("Paciente não encontrado.");
                        }
                        break;

                    case 5:
                        System.out.println("\n--- REMOVER PACIENTE ---");

                        System.out.print("Informe o prontuário: ");
                        String prontuarioRemover = scanner.nextLine();

                        if (repository.remover(prontuarioRemover)) {
                            System.out.println("Paciente removido com sucesso!");
                        } else {
                            System.out.println("Paciente não encontrado.");
                        }
                        break;

                    case 0:
                        System.out.println("Encerrando o sistema...");
                        break;

                    default:
                        System.out.println("Opção inválida!");
                        break;
                }

            } while (opcao != 0);

            scanner.close();

        } catch (IOException e) {
            System.out.println("Erro ao iniciar o sistema: " + e.getMessage());
        }
    }
}
