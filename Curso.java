import java.util.List;

public class Curso {
    private String nome;
    private List<AtividadeCurricular> requisitosIntegralizacao;

    public Curso(String nome, List<AtividadeCurricular> requisitosIntegralizacao) {
        this.nome = nome;
        this.requisitosIntegralizacao = requisitosIntegralizacao;
    }

    public String getNome() {
        return nome;
    }

    public List<AtividadeCurricular> getRequisitosIntegralizacao() {
        return requisitosIntegralizacao;
    }
}
