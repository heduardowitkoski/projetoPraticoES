import java.util.ArrayList;
import java.util.List;

public class Historico {
    private Discente discente;
    private List<RequisitoIntegralizacao> requisitos;
    private List<RequisitoIntegralizacao> requisitosConcluidos = new ArrayList<>();

    public Historico(Discente discente, List<RequisitoIntegralizacao> requisitos) {
        this.discente = discente;
        this.requisitos = requisitos;
    }

    public Discente getDiscente() {
        return discente;
    }

    public List<RequisitoIntegralizacao> getRequisitos() {
        return requisitos;
    }

    public List<RequisitoIntegralizacao> getRequisitosConcluidos() {
        return requisitosConcluidos;
    }

    public void adicionarRequisito(RequisitoIntegralizacao requisito) {
        requisitosConcluidos.add(requisito);
    }

    public void listarRequisitosFormatado() {
        System.out.println("\n===== Histórico de " + discente.getNome() + " =====");
        for (RequisitoIntegralizacao r : requisitos) {
            String status = requisitosConcluidos.contains(r) ? "✅ Concluído" : "❌ Não concluído";
            System.out.println(r + " - " + status);
        }
    }

    public void atualizarRequisitos(List<RequisitoIntegralizacao> novosRequisitos) {
        this.requisitos = novosRequisitos;
    }
}
