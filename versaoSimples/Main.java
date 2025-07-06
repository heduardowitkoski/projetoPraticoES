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
            System.out.println(i++ + " - " + r);
        }
    }

    private static void marcarRequisito(Curso curso, Scanner scanner) {
        mostrarRequisitos(curso);
        System.out.print("Número do requisito a marcar como concluído: ");
        try {
            int escolha = Integer.parseInt(scanner.nextLine());
            if (escolha < 1 || escolha > curso.getRequisitos().size()) {
                System.out.println(">>> Número inválido.");
                return;
            }
            RequisitoIntegralizacao r = curso.getRequisitos().get(escolha - 1);
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
