import java.util.ArrayList;
import java.util.List;

public class Historico {
    private List<RequisitoIntegralizacao> requisitos;
    private List<RequisitoIntegralizacao> concluidos;

    public Historico(List<RequisitoIntegralizacao> requisitos) {
        this.requisitos = requisitos;
        this.concluidos = new ArrayList<>();
    }

    public void adicionarRequisito(RequisitoIntegralizacao r) {
        concluidos.add(r);
    }

    public List<RequisitoIntegralizacao> getRequisitosConcluidos() {
        return concluidos;
    }

    public void listarRequisitosFormatado() {
        System.out.println("\n--- Histórico ---");
        for (RequisitoIntegralizacao r : requisitos) {
            String status = concluidos.contains(r) ? "✅" : "❌";
            System.out.println(r + " - " + status);
        }
    }
}
