import java.util.ArrayList;
import java.util.List;

public class Historico {
    private Discente discente;
    private List<RequisitoIntegralizacao> requisitosConcluidos;

    public Historico(Discente discente) {
        this.discente = discente;
        this.requisitosConcluidos = new ArrayList<>();
    }

    public void adicionarRequisito(RequisitoIntegralizacao requisito) {
        requisito.concluir();
        requisitosConcluidos.add(requisito);
    }

    public boolean requisitoConcluido(String nome) {
        return requisitosConcluidos.stream().anyMatch(r -> r.getNome().equalsIgnoreCase(nome));
    }

    public List<RequisitoIntegralizacao> getRequisitosConcluidos() {
        return requisitosConcluidos;
    }

    public void listarRequisitos() {
        System.out.println("Histórico de " + discente.getNome() + ":");
        for (RequisitoIntegralizacao r : requisitosConcluidos) {
            System.out.println("- " + r.getNome());
        }
    }
}
