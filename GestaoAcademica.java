import java.util.*;

public class GestaoAcademica {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // =========================
        // Leitura do curso pelo JSON
        // =========================
        Curso curso = LeitorDeCursoJSON.lerCursoDeArquivo("curso.json");

        if (curso == null) {
            System.out.println("Falha ao carregar o curso.");
            return;
        }

        // =========================
        // Lista de discentes e históricos
        // =========================
        Map<Discente, Historico> discentes = new HashMap<>();

        int opcao = -1;

        do {
            System.out.println("\n===== Menu Principal =====");
            System.out.println("1 - Cadastrar discente");
            System.out.println("2 - Gerenciar discente");
            System.out.println("3 - Listar discentes");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1:
                    System.out.println("\n===== Cadastro de Discente =====");
                    System.out.print("Nome do discente: ");
                    String nome = scanner.nextLine();

                    System.out.print("Matrícula do discente: ");
                    String matricula = scanner.nextLine();

                    Discente discente = new Discente(nome, matricula);
                    Historico historico = new Historico(discente);
                    discentes.put(discente, historico);

                    System.out.println(">>> Discente cadastrado com sucesso.");
                    break;

                case 2:
                    if (discentes.isEmpty()) {
                        System.out.println(">>> Nenhum discente cadastrado.");
                        break;
                    }

                    Discente selecionado = selecionarDiscente(discentes, scanner);
                    if (selecionado == null) {
                        System.out.println(">>> Operação cancelada.");
                        break;
                    }

                    gerenciarDiscente(curso, selecionado, discentes.get(selecionado), scanner);
                    break;

                case 3:
                    System.out.println("\n===== Discentes Cadastrados =====");
                    if (discentes.isEmpty()) {
                        System.out.println(">>> Nenhum discente cadastrado.");
                    } else {
                        int i = 1;
                        for (Discente d : discentes.keySet()) {
                            System.out.println(i + " - " + d.getNome() + " | Matrícula: " + d.getMatricula());
                            i++;
                        }
                    }
                    break;

                case 0:
                    System.out.println("Encerrando...");
                    break;

                default:
                    System.out.println(">>> Opção inválida. Tente novamente.");
                    break;
            }

        } while (opcao != 0);

        scanner.close();
    }

    private static Discente selecionarDiscente(Map<Discente, Historico> discentes, Scanner scanner) {
        List<Discente> lista = new ArrayList<>(discentes.keySet());

        System.out.println("\n===== Selecionar Discente =====");
        for (int i = 0; i < lista.size(); i++) {
            Discente d = lista.get(i);
            System.out.println((i + 1) + " - " + d.getNome() + " | Matrícula: " + d.getMatricula());
        }
        System.out.print("Informe o número do discente (ou 0 para cancelar): ");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha == 0) {
            return null;
        }

        if (escolha < 1 || escolha > lista.size()) {
            System.out.println(">>> Opção inválida.");
            return null;
        }

        return lista.get(escolha - 1);
    }

    private static void gerenciarDiscente(Curso curso, Discente aluno, Historico historico, Scanner scanner) {
        int opcao = -1;

        do {
            System.out.println("\n===== Menu - Discente: " + aluno.getNome() + " =====");
            System.out.println("1 - Ver requisitos do curso");
            System.out.println("2 - Adicionar requisito concluído");
            System.out.println("3 - Ver histórico do discente");
            System.out.println("0 - Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1:
                    System.out.println("\n===== Requisitos do Curso =====");
                    int index = 1;
                    for (RequisitoIntegralizacao req : curso.getRequisitos()) {
                        System.out.println(index + " - " + req.getNome());
                        index++;
                    }
                    break;

                case 2:
                    System.out.println("\n===== Marcar requisito como concluído =====");
                    int idx = 1;
                    for (RequisitoIntegralizacao req : curso.getRequisitos()) {
                        System.out.println(idx + " - " + req.getNome());
                        idx++;
                    }
                    System.out.print("Informe o número do requisito concluído: ");
                    int escolha = scanner.nextInt();
                    scanner.nextLine();

                    if (escolha >= 1 && escolha <= curso.getRequisitos().size()) {
                        RequisitoIntegralizacao requisito = curso.getRequisitos().get(escolha - 1);
                        if (historico.getRequisitosConcluidos().contains(requisito)) {
                            System.out.println(">>> Este requisito já foi concluído.");
                        } else {
                            historico.adicionarRequisito(requisito);
                            System.out.println(">>> Requisito '" + requisito.getNome() + "' adicionado ao histórico.");
                        }
                    } else {
                        System.out.println(">>> Opção inválida.");
                    }
                    break;

                case 3:
                    System.out.println("\n===== Histórico do Discente =====");
                    historico.listarRequisitos();
                    break;

                case 0:
                    System.out.println(">>> Voltando ao menu principal...");
                    break;

                default:
                    System.out.println(">>> Opção inválida. Tente novamente.");
                    break;
            }
        } while (opcao != 0);
    }
}
