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

        // Cria o histórico com os requisitos do curso
        historico = new Historico(curso.getRequisitos());

        // Carrega o histórico salvo no arquivo, se existir
        PersistenciaHistorico.carregar(historico);

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
                case "1" -> mostrarRequisitos(curso);
                case "2" -> marcarRequisito(curso, scanner);
                case "3" -> historico.listarRequisitosFormatado();
                case "4" -> {
                    PersistenciaHistorico.salvar(historico);
                    executando = false;
                }
                default -> System.out.println(">>> Opção inválida.");
            }
        }

        scanner.close();
    }

    private static void mostrarRequisitos(Curso curso) {
        System.out.println("\n--- Requisitos do curso ---");
        int i = 1;
        for (RequisitoIntegralizacao r : curso.getRequisitos()) {
            if (r instanceof RequisitoOptativas opt) {
                System.out.println(i++ + " - " + r.getNome() + " " + (r.isConcluido() ? "✅" : "❌"));
                for (AtividadeCurricular ac : opt.getOptativas()) {
                    System.out.println("     -> " + i++ + " - " + ac);
                }
            } else {
                System.out.println(i++ + " - " + r + " " + (r.isConcluido() ? "✅" : "❌"));
            }
        }
    }

    private static void marcarRequisito(Curso curso, Scanner scanner) {
        System.out.println("\n--- Marcar requisito ---");
        int i = 1;

        // Lista que guarda os itens realmente selecionáveis (requisitos ou optativas internas)
        List<Object> itensSelecionaveis = new java.util.ArrayList<>();

        for (RequisitoIntegralizacao r : curso.getRequisitos()) {
            if (r instanceof RequisitoOptativas opt) {
                System.out.println(i + " - " + r.getNome() + " " + (r.isConcluido() ? "✅" : "❌"));
                itensSelecionaveis.add(r); // apenas para manter o índice correto
                i++;
                for (AtividadeCurricular ac : opt.getOptativas()) {
                    System.out.println("     -> " + i + " - " + ac + (ac.isConcluido() ? " ✅" : " ❌"));
                    itensSelecionaveis.add(ac);
                    i++;
                }
            } else {
                System.out.println(i + " - " + r + " " + (r.isConcluido() ? "✅" : "❌"));
                itensSelecionaveis.add(r);
                i++;
            }
        }

        System.out.print("Número do requisito a marcar como concluído: ");
        try {
            int escolha = Integer.parseInt(scanner.nextLine());
            if (escolha < 1 || escolha > itensSelecionaveis.size()) {
                System.out.println(">>> Número inválido.");
                return;
            }

            Object selecionado = itensSelecionaveis.get(escolha - 1);

            if (selecionado instanceof AtividadeCurricular ac) {
                if (ac.isConcluido()) {
                    System.out.println(">>> Já concluída.");
                } else {
                    ac.concluir();
                    historico.adicionarRequisito(ac);
                    System.out.println(">>> Atividade concluída com sucesso.");
                }
                return;
            }

            if (selecionado instanceof RequisitoIntegralizacao r) {

                // Submissões Acadêmicas
                if (r instanceof SubmissoesAcademicas sa) {
                    System.out.println("Deseja marcar qual item?");
                    System.out.println("1 - Submeter resumo");
                    System.out.println("2 - Submeter artigo");
                    String opcao = scanner.nextLine();

                    switch (opcao) {
                        case "1" -> {
                            if (sa.getResumosSubmetidos() >= 2) {
                                System.out.println(">>> Os dois resumos já foram submetidos.");
                            } else {
                                System.out.print("Digite o título do resumo: ");
                                String titulo = scanner.nextLine();
                                sa.submeterResumo(titulo);
                                System.out.println(">>> Resumo submetido com sucesso.");
                            }
                        }
                        case "2" -> {
                            if (sa.isArtigoSubmetido()) {
                                System.out.println(">>> O artigo já foi submetido.");
                            } else {
                                System.out.print("Digite o título do artigo: ");
                                String titulo = scanner.nextLine();
                                sa.submeterArtigo(titulo);
                                System.out.println(">>> Artigo submetido com sucesso.");
                            }
                        }
                        default -> System.out.println(">>> Opção inválida.");
                    }

                    if (!historico.getRequisitosConcluidos().contains(sa) && sa.isConcluido()) {
                        historico.adicionarRequisito(sa);
                    }

                    return;
                }

                // Estágio Supervisionado
                if (r instanceof EstagioSupervisionado es) {
                    if (!es.isConcluido()) {
                        es.concluirViaEntrada();  // <-- usa o novo método com Scanner
                        historico.adicionarRequisito(es);
                        System.out.println(">>> Estágio registrado.");
                    } else {
                        System.out.println(">>> Já concluído.");
                    }
                    return;
                }

                // UNIPAMPA Cidadã
                if (r instanceof UnipampaCidada uc) {
                    if (!uc.isConcluido()) {
                        uc.concluir();
                        historico.adicionarRequisito(uc);
                        System.out.println(">>> Atividade registrada.");
                    } else {
                        System.out.println(">>> Já concluído.");
                    }
                    return;
                }

                // Projeto Final
                if (r instanceof ProjetoFinal pf) {
                    System.out.println("Deseja concluir qual etapa?");
                    System.out.println("1 - Etapa I");
                    System.out.println("2 - Etapa II");
                    String etapa = scanner.nextLine();

                    if (etapa.equals("1") && !pf.isEtapa1Concluida()) {
                        pf.concluirEtapa1();
                        System.out.println(">>> Etapa I concluída.");
                    } else if (etapa.equals("2") && !pf.isEtapa2Concluida()) {
                        pf.concluirEtapa2();
                        System.out.println(">>> Etapa II concluída.");
                    } else {
                        System.out.println(">>> Etapa já concluída ou inválida.");
                    }

                    if (pf.isConcluido() && !historico.getRequisitosConcluidos().contains(pf)) {
                        historico.adicionarRequisito(pf);
                    }
                    return;
                }

                // Prática Extensionista
                if (r instanceof PraticaExtensionista pe) {
                    System.out.println("Deseja concluir qual etapa?");
                    System.out.println("1 - Etapa I");
                    System.out.println("2 - Etapa II");
                    String etapa = scanner.nextLine();

                    if (etapa.equals("1") && !pe.isEtapa1Concluida()) {
                        pe.concluirEtapa1();
                        System.out.println(">>> Etapa I concluída.");
                    } else if (etapa.equals("2") && !pe.isEtapa2Concluida()) {
                        pe.concluirEtapa2();
                        System.out.println(">>> Etapa II concluída.");
                    } else {
                        System.out.println(">>> Etapa já concluída ou inválida.");
                    }

                    if (pe.isConcluido() && !historico.getRequisitosConcluidos().contains(pe)) {
                        historico.adicionarRequisito(pe);
                    }
                    return;
                }

                // Requisitos comuns
                if (!historico.getRequisitosConcluidos().contains(r)) {
                    r.concluir();
                    historico.adicionarRequisito(r);
                    System.out.println(">>> Requisito marcado como concluído.");
                } else {
                    System.out.println(">>> Já concluído.");
                }
            }

        } catch (NumberFormatException e) {
            System.out.println(">>> Entrada inválida.");
        }
    }
}
