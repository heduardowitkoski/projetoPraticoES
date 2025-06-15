import java.util.*;

public class GestaoAcademica {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Historico> historicos = new HashMap<>();

        // Leitura do curso
        Curso curso = LeitorDeCursoTXT.lerCursoDeArquivo("curso.txt");
        if (curso == null) {
            System.out.println("Erro ao carregar o curso.");
            return;
        }

        System.out.println("Curso carregado: " + curso.getNome());

        // Menu principal
        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1 - Cadastrar discente");
            System.out.println("2 - Inserir componente concluída");
            System.out.println("3 - Verificar histórico");
            System.out.println("4 - Sair");
            System.out.print("Escolha uma opção: ");

            String opcao = scanner.nextLine().trim();

            switch (opcao) {
                case "1": {
                    System.out.print("Nome do discente: ");
                    String nome = scanner.nextLine();
                    System.out.print("Matrícula do discente: ");
                    String matricula = scanner.nextLine();

                    if (historicos.containsKey(matricula)) {
                        System.out.println("Já existe um discente com essa matrícula.");
                    } else {
                        Discente novo = new Discente(nome, matricula);
                        historicos.put(matricula, new Historico(novo));
                        System.out.println("Discente cadastrado com sucesso.");
                    }
                    break;
                }

                case "2": {
                    System.out.print("Digite a matrícula do discente: ");
                    String matricula = scanner.nextLine();

                    Historico historico = historicos.get(matricula);
                    if (historico == null) {
                        System.out.println("Discente não encontrado.");
                        break;
                    }

                    System.out.println("\nComponentes curriculares disponíveis:");
                    int i = 1;
                    for (AtividadeCurricular ac : curso.getRequisitosIntegralizacao()) {
                        System.out.println(i++ + ". " + ac);
                    }

                    System.out.print("Número da atividade concluída: ");
                    String entrada = scanner.nextLine().trim();

                    if (!entrada.matches("\\d+")) {
                        System.out.println("Entrada inválida.");
                        break;
                    }

                    int escolha = Integer.parseInt(entrada);
                    if (escolha < 1 || escolha > curso.getRequisitosIntegralizacao().size()) {
                        System.out.println("Número inválido.");
                    } else {
                        AtividadeCurricular ac = curso.getRequisitosIntegralizacao().get(escolha - 1);
                        if (historico.atividadeConcluida(ac.getNome())) {
                            System.out.println("Essa atividade já foi registrada.");
                        } else {
                            historico.adicionarAtividade(ac);
                            System.out.println("Atividade adicionada ao histórico.");
                        }
                    }
                    break;
                }

                case "3": {
                    System.out.print("Digite a matrícula do discente: ");
                    String matricula = scanner.nextLine();

                    Historico historico = historicos.get(matricula);
                    if (historico == null) {
                        System.out.println("Discente não encontrado.");
                        break;
                    }

                    historico.listarAtividades();

                    System.out.println("\nAtividades obrigatórias faltantes:");
                    for (AtividadeCurricular ac : curso.getRequisitosIntegralizacao()) {
                        if (ac.isObrigatoria() && !historico.atividadeConcluida(ac.getNome())) {
                            System.out.println("- " + ac);
                        }
                    }
                    break;
                }

                case "4":
                    System.out.println("Encerrando o programa.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}
