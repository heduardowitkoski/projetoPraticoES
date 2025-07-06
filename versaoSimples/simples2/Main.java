import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Historico historico;

    public static void main(String[] args) {
        Curso curso = LeitorDeCursoJSON.lerCursoDeArquivo("curso.json");

        if (curso == null) {
            System.out.println("Erro ao carregar o curso. Verifique o arquivo JSON.");
            return;
        }

        historico = PersistenciaHistorico.carregar(curso.getRequisitos());

        Scanner scanner = new Scanner(System.in);
        boolean executando = true;

        while (executando) {
            System.out.println("\n=== SISTEMA DE HISTÓRICO ACADÊMICO ===");
            System.out.println("1 - Visualizar requisitos");
            System.out.println("2 - Marcar requisito como concluído");
            System.out.println("3 - Ver meu histórico");
            System.out.println("4 - Salvar e sair");
            System.out.print("Escolha: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    mostrarRequisitos(curso);
                    break;
                case "2":
                    marcarRequisito(curso, scanner);
                    break;
                case "3":
                    historico.listarRequisitosFormatado();
                    break;
                case "4":
                    PersistenciaHistorico.salvar(historico);
                    executando = false;
                    break;
                default:
                    System.out.println(">>> Opção inválida.");
            }
        }

        scanner.close();
    }

    private static void mostrarRequisitos(Curso curso) {
        System.out.println("\n--- Requisitos do curso ---");
        int i = 1;
        for (RequisitoIntegralizacao r : curso.getRequisitos()) {
            if (r instanceof RequisitoOptativas) {
                RequisitoOptativas opt = (RequisitoOptativas) r;
                System.out.println(i++ + " - " + r.getNome() + " " + (r.isConcluido() ? "✅" : "❌"));
                for (AtividadeCurricular ac : opt.getOptativas()) {
                    System.out.println("     -> " + i++ + " - " + ac); // mostra com indentação e índice real
                }
            } else {
                System.out.println(i++ + " - " + r + " " + (r.isConcluido() ? "✅" : "❌"));
            }
        }
    }

    private static void marcarRequisito(Curso curso, Scanner scanner) {
        // Gerar lista linear de todos os requisitos reais (inclusive optativas internas)
        List<RequisitoIntegralizacao> todos = new ArrayList<>();
        for (RequisitoIntegralizacao r : curso.getRequisitos()) {
            todos.add(r);
            if (r instanceof RequisitoOptativas) {
                todos.addAll(((RequisitoOptativas) r).getOptativas());
            }
        }

        // Mostrar lista com índices únicos, sem duplicação
        System.out.println("\n--- Marcar requisito ---");
        for (int i = 0; i < todos.size(); i++) {
            RequisitoIntegralizacao r = todos.get(i);
            System.out.println((i + 1) + " - " + r);
        }

        System.out.print("Número do requisito a marcar como concluído: ");
        try {
            int escolha = Integer.parseInt(scanner.nextLine());
            if (escolha < 1 || escolha > todos.size()) {
                System.out.println(">>> Número inválido.");
                return;
            }

            RequisitoIntegralizacao r = todos.get(escolha - 1);
            if (!historico.getRequisitosConcluidos().contains(r)) {
                historico.adicionarRequisito(r);
                r.concluir();
                System.out.println(">>> Requisito marcado como concluído.");
            } else {
                System.out.println(">>> Já concluído.");
            }
        } catch (NumberFormatException e) {
            System.out.println(">>> Entrada inválida.");
        }
    }

}
