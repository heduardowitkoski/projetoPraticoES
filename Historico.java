import java.util.ArrayList;
import java.util.List;

public class Historico {
    private Discente discente;
    private List<AtividadeCurricular> atividadesConcluidas;

    public Historico(Discente discente) {
        this.discente = discente;
        this.atividadesConcluidas = new ArrayList<>();
    }

    public void adicionarAtividade(AtividadeCurricular atividade) {
        atividadesConcluidas.add(atividade);
    }

    public boolean atividadeConcluida(String nome) {
        return atividadesConcluidas.stream().anyMatch(a -> a.getNome().equals(nome));
    }

    public void listarAtividades() {
        System.out.println("Histórico de " + discente.getNome() + ":");
        for (AtividadeCurricular a : atividadesConcluidas) {
            System.out.println("- " + a);
        }
    }
}
