import java.util.Scanner;

public class EstagioSupervisionado implements RequisitoIntegralizacao {
    private boolean concluido = false;
    public String empresa;
    public String dataInicio;
    public String dataTermino;

    @Override
    public String getNome() {
        return "Estágio Curricular Supervisionado";
    }

    @Override
    public boolean isConcluido() {
        return concluido;
    }

    // Apenas marca como concluído, sem entrada de dados
    @Override
    public void concluir() {
        this.concluido = true;
    }

    // Método usado somente ao interagir com o usuário
    public void concluirViaEntrada() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nome da empresa de estágio: ");
        empresa = scanner.nextLine();

        System.out.print("Data de início (ex: 01/03/2025): ");
        dataInicio = scanner.nextLine();

        System.out.print("Data de término prevista (ex: 01/09/2025): ");
        dataTermino = scanner.nextLine();

        this.concluido = true;
    }

    @Override
    public String toString() {
        if (concluido) {
            return getNome() + " ✅ [" + empresa + ", de " + dataInicio + " até " + dataTermino + "]";
        } else {
            return getNome() + " ❌";
        }
    }
}
