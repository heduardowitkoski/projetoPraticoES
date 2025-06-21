import java.util.*;

public class GestaoAcademica {
    private static Map<Discente, Historico> discentes = new HashMap<>();
    private static Curso curso;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Inicializa o curso primeiro
        inicializarCurso();

        // Agora carrega os discentes, usando os requisitos do curso já carregado
        discentes = PersistenciaDados.carregarDiscentes(curso.getRequisitos());
        for (Map.Entry<Discente, Historico> entry : discentes.entrySet()) {
            Historico historico = entry.getValue();
            historico.atualizarRequisitos(curso.getRequisitos());
        }

        boolean executando = true;
        while (executando) {
            System.out.println("\n===== Sistema de Gestão Acadêmica =====");
            System.out.println("1 - Login como Discente");
            System.out.println("2 - Login como Administrador");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    loginDiscente(scanner);
                    break;
                case "2":
                    menuAdmin(scanner);
                    break;
                case "0":
                    executando = false;
                    System.out.println("Encerrando...");
                    PersistenciaDados.salvarDiscentes(discentes);
                    break;
                default:
                    System.out.println(">>> Opção inválida.");
            }
        }
        scanner.close();
    }


    private static void inicializarCurso() {
        curso = LeitorDeCursoJSON.lerCursoDeArquivo("curso.json");
        if (curso == null) {
            System.out.println("Erro ao carregar o curso. Encerrando...");
            System.exit(1);
        }
    }

    // ===============================
    // ========== ADMIN ==============
    // ===============================
    private static void menuAdmin(Scanner scanner) {
        boolean executando = true;
        while (executando) {
            System.out.println("\n===== Menu Administrador =====");
            System.out.println("1 - Cadastrar discente");
            System.out.println("2 - Listar discentes");
            System.out.println("3 - Gerar relatório de um discente");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    cadastrarDiscente(scanner);
                    break;
                case "2":
                    listarDiscentes();
                    break;
                case "3":
                    gerarRelatorioDeDiscente(scanner);
                    break;
                case "0":
                    executando = false;
                    break;
                default:
                    System.out.println(">>> Opção inválida.");
            }
        }
    }

    private static void cadastrarDiscente(Scanner scanner) {
        System.out.print("Nome do discente: ");
        String nome = scanner.nextLine();

        System.out.print("Matrícula: ");
        String matricula = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Discente discente = new Discente(nome, matricula, senha);
        Historico historico = new Historico(discente, curso.getRequisitos());
        discentes.put(discente, historico);



        System.out.println(">>> Discente cadastrado com sucesso.");
        Log.registrar("Admin cadastrou discente " + matricula);
    }

    private static void listarDiscentes() {
        System.out.println("\n===== Lista de Discentes =====");
        if (discentes.isEmpty()) {
            System.out.println("Nenhum discente cadastrado.");
        } else {
            discentes.keySet().forEach(System.out::println);
        }
    }

    private static void gerarRelatorioDeDiscente(Scanner scanner) {
        Discente discente = selecionarDiscente(scanner);
        if (discente != null) {
            Relatorio.gerar(discentes.get(discente));
        }
    }

    private static Discente selecionarDiscente(Scanner scanner) {
        if (discentes.isEmpty()) {
            System.out.println("Nenhum discente cadastrado.");
            return null;
        }

        List<Discente> lista = new ArrayList<>(discentes.keySet());
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + " - " + lista.get(i));
        }

        System.out.print("Escolha o número do discente: ");
        try {
            int escolha = Integer.parseInt(scanner.nextLine());
            if (escolha < 1 || escolha > lista.size()) {
                System.out.println(">>> Opção inválida.");
                return null;
            }
            return lista.get(escolha - 1);
        } catch (NumberFormatException e) {
            System.out.println(">>> Entrada inválida.");
            return null;
        }
    }

    // ===============================
    // ========= DISCENTE ============
    // ===============================
    private static void loginDiscente(Scanner scanner) {
        System.out.print("Matrícula: ");
        String matricula = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Discente discente = discentes.keySet().stream()
                .filter(d -> d.getMatricula().equals(matricula) && senha.equals(d.getSenha()))
                .findFirst()
                .orElse(null);

        if (discente == null) {
            System.out.println(">>> Matrícula ou senha incorretos.");
            return;
        }

        menuDiscente(scanner, discente);
    }


    private static void menuDiscente(Scanner scanner, Discente discente) {
        Historico historico = discentes.get(discente);

        boolean executando = true;
        while (executando) {
            System.out.println("\n===== Menu Discente =====");
            System.out.println("Discente: " + discente.getNome());
            System.out.println("1 - Visualizar requisitos do curso");
            System.out.println("2 - Marcar requisito como concluído");
            System.out.println("3 - Ver meu histórico");
            System.out.println("4 - Gerar meu relatório");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    visualizarRequisitos();
                    break;
                case "2":
                    marcarRequisitoConcluido(scanner, historico);
                    break;
                case "3":
                    historico.listarRequisitosFormatado();
                    break;
                case "4":
                    Relatorio.gerar(historico);
                    break;
                case "0":
                    executando = false;
                    break;
                default:
                    System.out.println(">>> Opção inválida.");
            }
        }
    }

    private static void visualizarRequisitos() {
        System.out.println("\n===== Requisitos do Curso =====");
        for (int i = 0; i < curso.getRequisitos().size(); i++) {
            RequisitoIntegralizacao r = curso.getRequisitos().get(i);
            System.out.println((i + 1) + " - " + r);
        }
    }

    private static void marcarRequisitoConcluido(Scanner scanner, Historico historico) {
        visualizarRequisitos();
        System.out.print("Informe o número do requisito concluído: ");
        try {
            int escolha = Integer.parseInt(scanner.nextLine());
            if (escolha < 1 || escolha > curso.getRequisitos().size()) {
                System.out.println(">>> Opção inválida.");
                return;
            }
            RequisitoIntegralizacao requisito = curso.getRequisitos().get(escolha - 1);

            if (historico.getRequisitosConcluidos().contains(requisito)) {
                System.out.println(">>> Este requisito já está concluído.");
            } else {
                historico.adicionarRequisito(requisito);
                System.out.println(">>> Requisito concluído com sucesso.");
            }
        } catch (NumberFormatException e) {
            System.out.println(">>> Entrada inválida.");
        }
    }
}
