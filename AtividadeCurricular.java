// Representa uma atividade obrigatória no curso, como definida no PPC.
public class AtividadeCurricular {
    private String nome;
    private int cargaHoraria;
    private boolean obrigatoria;

    public AtividadeCurricular(String nome, int cargaHoraria, boolean obrigatoria) {
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.obrigatoria = obrigatoria;
    }

    public String getNome() {
        return nome;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public boolean isObrigatoria() {
        return obrigatoria;
    }

    @Override
    public String toString() {
        return nome + " (" + cargaHoraria + "h)" + (obrigatoria ? " [Obrigatória]" : " [Opcional]");
    }
}
