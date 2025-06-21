import java.util.List;

public class AtividadeCurricular implements RequisitoIntegralizacao {
    private String codigo;
    private String nome;
    private int cargaHoraria;
    private String area;
    private int periodoSugerido;
    private List<String> preRequisitos;
    private boolean obrigatoria;
    private boolean concluido = false;

    public AtividadeCurricular(String codigo, String nome, int cargaHoraria, String area, int periodoSugerido, List<String> preRequisitos, boolean obrigatoria) {
        this.codigo = codigo;
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.area = area;
        this.periodoSugerido = periodoSugerido;
        this.preRequisitos = preRequisitos;
        this.obrigatoria = obrigatoria;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public String getArea() {
        return area;
    }

    public int getPeriodoSugerido() {
        return periodoSugerido;
    }

    public List<String> getPreRequisitos() {
        return preRequisitos;
    }

    public boolean isObrigatoria() {
        return obrigatoria;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public boolean isConcluido() {
        return concluido;
    }

    @Override
    public void concluir() {
        this.concluido = true;
    }

    @Override
    public String toString() {
        return nome + " (" + codigo + ") - " + cargaHoraria + "h | " + area + " | Período: " + periodoSugerido +
                (obrigatoria ? " [Obrigatória]" : " [Optativa]") + (concluido ? " ✅" : " ❌");
    }
}
