import java.util.List;

public class Curso {
    private String nome;
    private List<RequisitoIntegralizacao> requisitos;

    public Curso(String nome, List<RequisitoIntegralizacao> requisitos) {
        this.nome = nome;
        this.requisitos = requisitos;
    }

    public String getNome() {
        return nome;
    }

    public List<RequisitoIntegralizacao> getRequisitos() {
        return requisitos;
    }
}
